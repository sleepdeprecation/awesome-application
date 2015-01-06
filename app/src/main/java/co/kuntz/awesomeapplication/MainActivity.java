package co.kuntz.awesomeapplication;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends ActionBarActivity {
    public static final String TAG = MainActivity.class.getName();

    private AsyncTask<View, Void, Void> backgroundTask = new AsyncTask<View, Void, Void>() {
        protected Void doInBackground(View... views) {
            while (true) {
                Log.d(TAG, "Changing background color");
                final Random random = new Random();
                final View view = views[0];

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                        view.refreshDrawableState();
                    }
                });


                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    TextView text = (TextView) getWindow().getDecorView().findViewById(R.id.text);
                    text.setText("Something terrible happened!");
                    break;
                }
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Starting application");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        View view = getWindow().getDecorView().findViewById(R.id.main_layout);
        backgroundTask.execute(view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundTask.cancel(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundTask.cancel(true);
    }
}