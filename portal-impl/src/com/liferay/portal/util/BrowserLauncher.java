/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Brian Wing Shun Chan
 */
public class BrowserLauncher implements Runnable {

	@Override
	public void run() {
		if (Validator.isNull(PropsValues.BROWSER_LAUNCHER_URL)) {
			return;
		}

		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(3000);
			}
			catch (InterruptedException ie) {
			}

			try {
				URL url = new URL(PropsValues.BROWSER_LAUNCHER_URL);

				HttpURLConnection urlc =
					(HttpURLConnection)url.openConnection();

				int responseCode = urlc.getResponseCode();

				if (responseCode == HttpURLConnection.HTTP_OK) {
					try {
						launchBrowser();
					}
					catch (Exception e2) {
					}

					break;
				}
			}
			catch (Exception e1) {
			}
		}
	}

	protected void launchBrowser() throws Exception {
		Runtime runtime = Runtime.getRuntime();

		if (OSDetector.isApple()) {
			launchBrowserApple(runtime);
		}
		else if (OSDetector.isWindows()) {
			launchBrowserWindows(runtime);
		}
		else {
			launchBrowserUnix(runtime);
		}
	}

	protected void launchBrowserApple(Runtime runtime) throws Exception {
		runtime.exec("open " + PropsValues.BROWSER_LAUNCHER_URL);
	}

	protected void launchBrowserUnix(Runtime runtime) throws Exception {
		if (_BROWSERS.length == 0) {
			runtime.exec(new String[] {"sh", "-c", StringPool.BLANK});
		}

		StringBundler sb = new StringBundler(_BROWSERS.length * 5 - 1);

		for (int i = 0; i < _BROWSERS.length; i++) {
			if (i != 0) {
				sb.append(" || ");
			}

			sb.append(_BROWSERS[i]);
			sb.append(" \"");
			sb.append(PropsValues.BROWSER_LAUNCHER_URL);
			sb.append("\" ");
		}

		runtime.exec(new String[] {"sh", "-c", sb.toString()});
	}

	protected void launchBrowserWindows(Runtime runtime) throws Exception {
		runtime.exec("cmd.exe /c start " + PropsValues.BROWSER_LAUNCHER_URL);
	}

	/**
	 * Order matters. See LPS-48525.
	 */
	private static final String[] _BROWSERS =
		{"xdg-open", "firefox", "mozilla", "konqueror", "opera"};

}