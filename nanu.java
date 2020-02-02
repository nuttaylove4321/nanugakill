package com.mycompany.myapp;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.os.*;
import android.content.*;
import android.content.pm.*;
import android.app.*;
import android.support.annotation.*;
import java.io.*;
import android.preference.*;
import java.net.*;
import android.util.*;
import android.net.*;
import android.net.NetworkInfo.State;
import android.support.v7.app.AlertDialog;
import com.sdsmdg.tastytoast.*;
import android.view.View.*;


public class MainActivity extends AppCompatActivity 
{ 

	private DrawerLayout drawerLayout;
    private Toolbar toolbar;
	
	private Context context = this;
	private int update;
	
	private String readLine;
	private String version = "1";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		initNavigationDrawer();
		findViewById(R.id.appBar).bringToFront();
		Button button = (Button) findViewById(R.id.update);
		button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1) {
					new Check_update().execute("https://pastebin.com/raw/gLt2Yqq8");
				}
		});
	}
	
	public class Check_update extends AsyncTask<String, Void, File>
	{

		@Override
		protected File doInBackground(String[] link_php)
		{
			SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			InputStream inputStream = null;
			Throwable th;
			try {
				InputStream openStream = new URL(link_php[0]).openStream();
				try {
					readLine = new BufferedReader(new InputStreamReader(openStream)).readLine();
					if (readLine != null) {
						update = Integer.valueOf(readLine).intValue() | 0;
					}
					if (openStream != null) {
						try {
							openStream.close();
						} catch (IOException e) {
						}
					}
					if (update > defaultSharedPreferences.getInt("update", Integer.valueOf(version).intValue() | 0)) {
						runOnUiThread(new Runnable() {
								public void run() {
									AlertDialog.Builder build = new AlertDialog.Builder(context);
									build.setTitle("ตรวจพบการอัพเดท");
									build.setMessage("พบการอัพเดทเซิร์ฟเวิร์เวอร์ชั่นใหม่ " + readLine);
									build.setPositiveButton("ยกเลิก", null);
									build.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface p1, int p2) {
												final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
												runOnUiThread(new Runnable() {
														public void run() {
															TastyToast.makeText(getApplicationContext(), "สำเร็จ", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
															defaultSharedPreferences.edit().putInt("update", update).apply();
														}
													});
												
											}
										});
									build.show();
								}
							});
						return null;
					}
					runOnUiThread(new Runnable() {
							public void run() {
								TastyToast.makeText(getApplicationContext(), "เวอร์ชั่นล่าสุดแล้ว", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
							}
						});
					return null;
				} catch (Exception e2) {
					inputStream = openStream;
					try {
						runOnUiThread(new Runnable() {
								public void run() {
									TastyToast.makeText(getApplicationContext(), "ไม่สารถมารถตรวจสอบการอัพเดทได้", TastyToast.LENGTH_LONG, TastyToast.ERROR);
								}
							});
						return null;
					} catch (Throwable th2) {
						openStream = inputStream;
						th = th2;
						if (openStream != null) {
							try {
								openStream.close();
							} catch (IOException e4) {
							}
						}
						throw th;
					}
				} catch (Throwable th3) {
					th = th3;
					if (openStream != null) {
						openStream.close();
					}
					throw th;
				}
			} catch (Exception e5) {
				runOnUiThread(new Runnable() {
						public void run() {
							TastyToast.makeText(getApplicationContext(), "ไม่สารถมารถตรวจสอบการอัพเดทได้", TastyToast.LENGTH_LONG, TastyToast.ERROR);
						}
					});
				if (inputStream != null) {
					try
					{
						inputStream.close();
					} catch (IOException e) {
					}
					return null;
				}
				return null;
			} catch (Throwable th4) {
				th = th4;
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
					}
				} try {
					throw th;
				} catch (Throwable e) {
				}
			}
			return null;
		}
	}

	public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(MenuItem menuItem) {

					int id = menuItem.getItemId();

					switch (id){
						case R.id.item:
							break;
						case R.id.item2:
							break;

					}
					drawerLayout.closeDrawers();
					return true;
				}
			});
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        tv_email.setText("NaNu-VPN");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

}
	
