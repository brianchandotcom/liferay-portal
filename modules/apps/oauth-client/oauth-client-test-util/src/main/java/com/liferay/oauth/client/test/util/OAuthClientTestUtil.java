/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.test.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.nio.charset.StandardCharsets;

import java.util.List;

import org.junit.Assert;

/**
 * @author Christian Moura
 */
public class OAuthClientTestUtil {

	public static void assertNoCookieWarnings(
			String path,
			UnsafeConsumer<String, Exception> serverURLUnsafeConsumer)
		throws Exception {

		try (HttpServerHandle httpServerHandle = startServer(
				"application/json",
				RandomTestUtil.randomString() +
					"=1; expires=Fri, 15 May 2027 18:46:54 GMT; path=/; " +
						"secure; SameSite=None; HttpOnly",
				path, "{}", 200);
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"org.apache.http.client.protocol.ResponseProcessCookies",
				LoggerTestUtil.WARN)) {

			serverURLUnsafeConsumer.accept(httpServerHandle.getURL());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	public static HttpServerHandle startServer(
			String contentType, String cookie, String path, String responseBody,
			int responseCode)
		throws IOException {

		HttpServer httpServer = HttpServer.create(
			new InetSocketAddress("127.0.0.1", 0), 0);

		httpServer.createContext(
			path,
			httpExchange -> {
				Headers responseHeaders = httpExchange.getResponseHeaders();

				if (contentType != null) {
					responseHeaders.add("Content-Type", contentType);
				}

				if (cookie != null) {
					responseHeaders.add("Set-Cookie", cookie);
				}

				byte[] bodyBytes = responseBody.getBytes(
					StandardCharsets.UTF_8);

				httpExchange.sendResponseHeaders(
					responseCode, bodyBytes.length);

				try (OutputStream outputStream =
						httpExchange.getResponseBody()) {

					outputStream.write(bodyBytes);
				}
			});

		httpServer.start();

		InetSocketAddress address = httpServer.getAddress();

		return new HttpServerHandle(
			httpServer, "http://127.0.0.1:" + address.getPort() + path);
	}

	public static class HttpServerHandle implements AutoCloseable {

		@Override
		public void close() {
			_httpServer.stop(0);
		}

		public String getURL() {
			return _url;
		}

		private HttpServerHandle(HttpServer httpServer, String url) {
			_httpServer = httpServer;
			_url = url;
		}

		private final HttpServer _httpServer;
		private final String _url;

	}

}