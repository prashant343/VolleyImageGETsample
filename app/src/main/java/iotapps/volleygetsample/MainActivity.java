package iotapps.volleygetsample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {

    Button volleygetbutton;
    TextView mTextView;
    Button volleypostbutton;
    Button volleyputbutton;
    Button volleydeletebutton;
    Button volleyimageloadbutton;

    ImageView imageView;

    public final static String url = "http://www.google.com";
    public final static String posturl = "http://posttestserver.com/post.php?dir=XYZ";
    //Guys Plz find diffrent server as i have created one for my self
    public final static String puturl = "http://XXXXX/XXXXXX/webapi/articles/41";
    public final static String deleteurl = "http://posttestserver.com/post.php?dir=XYZ";

    public final static String imageurl = "http://www.iotunbox.com/advertisement/adv1.jpg";
//http://www.iotunbox.com/advertisement/adv1.jpg
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volleygetbutton  = (Button) findViewById(R.id.button);
        volleypostbutton = (Button) findViewById(R.id.button2);
        volleyputbutton = (Button) findViewById(R.id.button3);
        volleydeletebutton = (Button) findViewById(R.id.button4);
        volleyimageloadbutton = (Button) findViewById(R.id.button5);

        imageView = (ImageView) findViewById(R.id.imageView);



        mTextView = (TextView)findViewById(R.id.mtextView);

        volleygetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volleygetfunc(url);


            }
        });


        volleypostbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volleypostfunc(posturl);
            }
        });

        volleyputbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volleyputfunc(puturl);

            }
        });

        volleydeletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volleypostfunc(deleteurl);
            }
        });


        volleyimageloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volleygetImagefunc(imageurl);

            }
        });

    }





    public void Volleygetfunc(String url)
    {
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+ response.substring(0,500));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


    public void volleygetImagefunc(String url)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        Toast.makeText(MainActivity.this,"Inside function",Toast.LENGTH_SHORT).show();

                final ImageRequest imageRequest=new ImageRequest (url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);

                    }
                },0,0, ImageView.ScaleType.CENTER_CROP,null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Some Thing Goes Wrong",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                });
        queue.add(imageRequest);
    }



    public void Volleypostfunc(String posturl)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("firstkey", "firstvalue");
            jsonBody.put("secondkey", "secondobject");


            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, posturl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {

                        responseString = String.valueOf(response.statusCode);

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Volleyputfunc(String puturl)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("heading","Amazon promotes ECHO in LA.-- Updated Third time");
            //jsonBody.put("secondkey", "secondobject");


            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, puturl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {

                        responseString = String.valueOf(response.statusCode);

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Volleydeletefunc(String deleteurl){

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, deleteurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {

                        responseString = String.valueOf(response.statusCode);

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);





    }





}
