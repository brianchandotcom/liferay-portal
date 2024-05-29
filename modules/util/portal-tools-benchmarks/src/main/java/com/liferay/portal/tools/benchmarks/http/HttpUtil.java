/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.http;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author Dante Wang
 * @author Tina Tian
 */
public class HttpUtil {

	public static HttpResponse doGet(URL url, String csrfToken)
		throws Exception {

		return _execute(url, csrfToken, "GET", null);
	}

	public static HttpResponse doPost(
			URL url, String[][] parameters, String csrfToken, String content)
		throws Exception {

		return _execute(
			url, csrfToken, "POST",
			httpURLConnection -> {
				httpURLConnection.setDoOutput(true);

				try (PrintWriter printWriter = new PrintWriter(
						httpURLConnection.getOutputStream())) {

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
							printWriter.print(
								URLEncoder.encode(
									parameter[1], StringPool.UTF8));
						}
					}

					if (content != null) {
						printWriter.print(content);
					}
				}
			});
	}

	private static HttpResponse _execute(
			URL url, String csrfToken, String httpMethod,
			UnsafeConsumer<HttpURLConnection, Exception> unsafeConsumer)
		throws Exception {

		HttpURLConnection httpURLConnection = null;

		try {
			httpURLConnection = (HttpURLConnection)url.openConnection();

			httpURLConnection.setConnectTimeout(0);
			httpURLConnection.setReadTimeout(0);

			if (csrfToken != null) {
				httpURLConnection.addRequestProperty("X-Csrf-Token", csrfToken);
			}

			httpURLConnection.setRequestMethod(httpMethod);

			if (unsafeConsumer != null) {
				unsafeConsumer.accept(httpURLConnection);
			}

			long startTime = System.currentTimeMillis();

			httpURLConnection.connect();

			ByteBuffer byteBuffer = _read(httpURLConnection.getInputStream());

			return new HttpResponse(
				httpURLConnection.getResponseCode(),
				httpURLConnection.getResponseMessage(),
				httpURLConnection.getHeaderFields(),
				new String(
					byteBuffer.array(), 0, byteBuffer.limit(),
					StandardCharsets.UTF_8),
				System.currentTimeMillis() - startTime);
		}
		catch (IOException ioException1) {
			if (httpURLConnection == null) {
				throw ioException1;
			}

			try (InputStream inputStream = httpURLConnection.getErrorStream()) {
				if (inputStream != null) {
					while (inputStream.read() != -1);
				}
			}
			catch (IOException ioException2) {
				throw new IOException(
					"Failed to consume error connection data", ioException2);
			}

			throw ioException1;
		}
		finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}

	private static ByteBuffer _read(InputStream inputStream) throws Exception {
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

	private static final ThreadLocal<byte[]> _byteBuffer =
		CentralizedThreadLocal.withInitial(() -> new byte[8192]);

	static {
		HttpURLConnection.setFollowRedirects(false);
	}

}