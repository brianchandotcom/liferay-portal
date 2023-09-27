/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.server.simulator;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

import java.net.InetSocketAddress;

/**
 * @author Igor Beslic
 */
public class HTTPServerSimulator {

	public static final String HOST_ADDRESS = "localhost";

	public static final int HOST_PORT = 9999;

	public static void start() throws IOException {
		if (_httpServerSimulator != null) {
			throw new UnsupportedOperationException(
				"HTTP server is already running");
		}

		_httpServerSimulator = new HTTPServerSimulator();
	}

	public static void stop() {
		_httpServer.stop(0);

		_httpServer = null;
		_httpServerSimulator = null;
	}

	private HTTPServerSimulator() throws IOException {
		_httpServer = HttpServer.create(new InetSocketAddress(HOST_PORT), 0);

		HttpHandler httpHandler = new BaseHttpHandlerImpl();

		_httpServer.createContext("/", new BadRequestHttpHandlerImpl());
		_httpServer.createContext("/testDelete/", httpHandler);
		_httpServer.createContext("/testGet/", httpHandler);
		_httpServer.createContext("/testPost/", httpHandler);
		_httpServer.createContext("/testPut/", httpHandler);

		_httpServer.start();
	}

	private static HttpServer _httpServer;
	private static HTTPServerSimulator _httpServerSimulator;

}