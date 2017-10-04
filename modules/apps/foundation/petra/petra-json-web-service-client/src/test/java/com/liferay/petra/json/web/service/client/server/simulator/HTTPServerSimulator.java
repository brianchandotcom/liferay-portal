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

package com.liferay.petra.json.web.service.client.server.simulator;

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