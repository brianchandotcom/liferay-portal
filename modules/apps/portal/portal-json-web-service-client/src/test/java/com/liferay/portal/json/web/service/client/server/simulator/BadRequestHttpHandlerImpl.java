/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.server.simulator;

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