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

package com.liferay.petra.json.web.service.client.serversimulator;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Igor Beslic
 */
public class BadRequestHttpHandlerImpl implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Headers responseHeaders = httpExchange.getResponseHeaders();

		responseHeaders.add("Content-Type", "application/json");

		httpExchange.sendResponseHeaders(400, 0);

		String responseBody =
			"{\"message\":\"No context found for request\", " +
				"\"success\":\"false\"}";

		OutputStream outputStream = httpExchange.getResponseBody();

		outputStream.write(responseBody.getBytes());

		outputStream.flush();

		outputStream.close();
	}

}