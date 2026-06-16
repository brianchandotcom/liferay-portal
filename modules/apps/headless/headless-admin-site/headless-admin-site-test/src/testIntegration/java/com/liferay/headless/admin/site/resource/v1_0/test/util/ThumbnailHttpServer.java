/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.InetSocketAddress;

/**
 * @author Javier Moral
 */
public class ThumbnailHttpServer {

	public static ThumbnailHttpServer start(Class<?> clazz) throws Exception {
		HttpServer httpServer = HttpServer.create(
			new InetSocketAddress("127.0.0.1", 0), 0);

		byte[] thumbnail1Bytes = _getBytes(clazz, "thumbnail1.png");
		byte[] thumbnail2Bytes = _getBytes(clazz, "thumbnail2.png");

		httpServer.createContext(
			"/thumbnail1.png",
			httpExchange -> _writeBytes(thumbnail1Bytes, httpExchange));
		httpServer.createContext(
			"/thumbnail2.png",
			httpExchange -> _writeBytes(thumbnail2Bytes, httpExchange));

		httpServer.start();

		return new ThumbnailHttpServer(
			httpServer, thumbnail1Bytes, thumbnail2Bytes);
	}

	public String getThumbnail1Base64() {
		return _thumbnail1Base64;
	}

	public byte[] getThumbnail1Bytes() {
		return _thumbnail1Bytes;
	}

	public String getThumbnail1URL() {
		return _thumbnail1URL;
	}

	public String getThumbnail2Base64() {
		return _thumbnail2Base64;
	}

	public byte[] getThumbnail2Bytes() {
		return _thumbnail2Bytes;
	}

	public String getThumbnail2URL() {
		return _thumbnail2URL;
	}

	public void stop() {
		_httpServer.stop(0);
	}

	private static byte[] _getBytes(Class<?> clazz, String fileName)
		throws Exception {

		try (InputStream inputStream = clazz.getResourceAsStream(
				"dependencies/" + fileName)) {

			return StreamUtil.toByteArray(inputStream);
		}
	}

	private static void _writeBytes(byte[] bytes, HttpExchange httpExchange)
		throws IOException {

		Headers responseHeaders = httpExchange.getResponseHeaders();

		responseHeaders.set("Content-Type", ContentTypes.IMAGE_PNG);

		httpExchange.sendResponseHeaders(200, bytes.length);

		try (OutputStream outputStream = httpExchange.getResponseBody()) {
			outputStream.write(bytes);
		}
	}

	private ThumbnailHttpServer(
		HttpServer httpServer, byte[] thumbnail1Bytes, byte[] thumbnail2Bytes) {

		_httpServer = httpServer;
		_thumbnail1Bytes = thumbnail1Bytes;
		_thumbnail2Bytes = thumbnail2Bytes;

		_thumbnail1Base64 = Base64.encode(thumbnail1Bytes);
		_thumbnail2Base64 = Base64.encode(thumbnail2Bytes);

		InetSocketAddress inetSocketAddress = httpServer.getAddress();

		String baseURL = "http://127.0.0.1:" + inetSocketAddress.getPort();

		_thumbnail1URL = baseURL + "/thumbnail1.png";
		_thumbnail2URL = baseURL + "/thumbnail2.png";
	}

	private final HttpServer _httpServer;
	private final String _thumbnail1Base64;
	private final byte[] _thumbnail1Bytes;
	private final String _thumbnail1URL;
	private final String _thumbnail2Base64;
	private final byte[] _thumbnail2Bytes;
	private final String _thumbnail2URL;

}