/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.http;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.CharPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.ByteBuffer;

/**
 * @author Tina Tian
 */
public class HttpUtil {

	public static HttpResponse doGet(URL url, String csrfToken)
		throws Exception {

		HttpURLConnection httpURLConnection = null;

		long startTime = System.currentTimeMillis();

		try {
			httpURLConnection = _openConnection(url);

			if (csrfToken != null) {
				httpURLConnection.addRequestProperty("X-Csrf-Token", csrfToken);
			}

			httpURLConnection.setRequestMethod("GET");

			return _readConnection(httpURLConnection);
		}
		catch (IOException ioException) {
			_consumeErrorConnection(httpURLConnection);

			throw new IOException(
				"Wait time : " + (System.currentTimeMillis() - startTime) +
					"ms",
				ioException);
		}
		finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}

	public static HttpResponse doPost(
			URL url, String[][] parameters, String csrfToken, String content)
		throws Exception {

		long startTime = System.currentTimeMillis();

		HttpURLConnection httpURLConnection = null;

		try {
			httpURLConnection = _openConnection(url);

			if (csrfToken != null) {
				httpURLConnection.addRequestProperty("X-Csrf-Token", csrfToken);
			}

			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);

			PrintWriter printWriter = new PrintWriter(
				httpURLConnection.getOutputStream());

			if (parameters != null) {
				boolean first = true;

				for (String[] parameter : parameters) {
					if (first) {
						first = false;
					}
					else {
						printWriter.print(CharPool.AMPERSAND);
					}

					printWriter.print(parameter[0]);
					printWriter.print(CharPool.EQUAL);
					printWriter.print(URLEncoder.encode(parameter[1], "UTF-8"));
				}
			}

			if (content != null) {
				printWriter.print(content);
			}

			printWriter.close();

			return _readConnection(httpURLConnection);
		}
		catch (IOException ioException) {
			_consumeErrorConnection(httpURLConnection);

			throw new IOException(
				"Wait time : " + (System.currentTimeMillis() - startTime) +
					"ms",
				ioException);
		}
		finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}

	private static ByteBuffer _collect(InputStream inputStream)
		throws Exception {

		byte[] byteBuffer = _byteBuffer.get();

		int offset = 0;

		int length = -1;

		int left = byteBuffer.length;

		while ((length = inputStream.read(byteBuffer, offset, left)) != -1) {
			left -= length;
			offset += length;

			if (left == 0) {
				int newLength = byteBuffer.length * 6 / 5;

				byte[] newBuffer = new byte[newLength];

				System.arraycopy(
					byteBuffer, 0, newBuffer, 0, byteBuffer.length);

				left = newLength - byteBuffer.length;

				byteBuffer = newBuffer;

				_byteBuffer.set(byteBuffer);
			}
		}

		inputStream.close();

		return ByteBuffer.wrap(byteBuffer, 0, offset);
	}

	private static void _consumeErrorConnection(
			HttpURLConnection httpURLConnection)
		throws IOException {

		if (httpURLConnection == null) {
			return;
		}

		try (InputStream inputStream = httpURLConnection.getErrorStream()) {
			if (inputStream != null) {
				while (inputStream.read() != -1);
			}
		}
		catch (IOException ioException) {
			throw new IOException(
				"Failed to consume error connection data", ioException);
		}
	}

	private static HttpURLConnection _openConnection(URL url)
		throws IOException {

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setConnectTimeout(0);
		httpURLConnection.setReadTimeout(0);

		return httpURLConnection;
	}

	private static HttpResponse _readConnection(
			HttpURLConnection httpURLConnection)
		throws Exception {

		long startTime = System.currentTimeMillis();

		httpURLConnection.connect();

		ByteBuffer byteBuffer = _collect(httpURLConnection.getInputStream());

		return new HttpResponse(
			httpURLConnection.getResponseCode(),
			httpURLConnection.getResponseMessage(),
			httpURLConnection.getHeaderFields(),
			new String(byteBuffer.array(), 0, byteBuffer.limit(), "utf-8"),
			System.currentTimeMillis() - startTime);
	}

	private static final ThreadLocal<byte[]> _byteBuffer =
		CentralizedThreadLocal.withInitial(() -> new byte[8192]);

	static {
		HttpURLConnection.setFollowRedirects(false);

		CookieHandler.setDefault(
			new CookieManager(
				new ThreadLocalCookieStore(), CookiePolicy.ACCEPT_ALL));
	}

}