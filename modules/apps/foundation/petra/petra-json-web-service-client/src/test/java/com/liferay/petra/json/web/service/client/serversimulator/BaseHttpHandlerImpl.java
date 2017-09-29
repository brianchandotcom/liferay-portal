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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URI;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class BaseHttpHandlerImpl implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Map<String, String> parameters = getParameters(httpExchange);

		int responseHTTPStatus = 400;

		if (parameters.containsKey(
				SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS)) {

			responseHTTPStatus = Integer.parseInt(
				parameters.get(
					SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS));

			if (responseHTTPStatus == 400) {
				httpExchange.sendResponseHeaders(responseHTTPStatus, -1);

				return;
			}
		}

		boolean returnParamsInJSON = false;

		if (parameters.containsKey(
				SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON)) {

			returnParamsInJSON = Boolean.parseBoolean(
				parameters.get(
					SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON));
		}

		String requestMethod = httpExchange.getRequestMethod();

		if ((responseHTTPStatus == 204) || (responseHTTPStatus == 401) ||
			(responseHTTPStatus == 405)) {

			if (returnParamsInJSON && requestMethod.equals("GET")) {
				httpExchange.sendResponseHeaders(400, -1);
			}
			else {
				httpExchange.sendResponseHeaders(responseHTTPStatus, -1);
			}
		}

		String responseBody = getResponseBody(httpExchange);

		if ((responseHTTPStatus != 202) && returnParamsInJSON) {
			responseBody = getResponseBody(parameters, httpExchange);
		}

		Headers responseHeaders = httpExchange.getResponseHeaders();

		responseHeaders.add("Content-Type", getAccept(httpExchange));

		httpExchange.sendResponseHeaders(responseHTTPStatus, 0);

		OutputStream outputStream = httpExchange.getResponseBody();

		outputStream.write(responseBody.getBytes());

		outputStream.flush();

		outputStream.close();
	}

	protected String getAccept(HttpExchange httpExchange) {
		Headers requestHeaders = httpExchange.getRequestHeaders();

		for (Map.Entry<String, List<String>> headerEntry :
				requestHeaders.entrySet()) {

			String headerKey = headerEntry.getKey();

			if (!headerKey.equals("Accept")) {
				continue;
			}

			List<String> headerValues = headerEntry.getValue();

			if (headerValues.size() != 1) {
				throw new UnsupportedOperationException(
					"Only one Content-Type header value allowed");
			}

			return headerValues.get(0);
		}

		return "plain/text";
	}

	protected String getAsJSON(Map<String, String> parameters) {
		StringBuffer sb = new StringBuffer();

		sb.append("{");

		for (Map.Entry parameterEntry : parameters.entrySet()) {
			sb.append("\"");
			sb.append(parameterEntry.getKey());
			sb.append("\":\"");
			sb.append(parameterEntry.getValue());
			sb.append("\",");
		}

		sb.setLength(sb.length() - 1);

		sb.append("}");

		return sb.toString();
	}

	protected String getAsPlainText(Map<String, String> parameters) {
		StringBuffer sb = new StringBuffer();

		for (Map.Entry parameterEntry : parameters.entrySet()) {
			sb.append(parameterEntry.getKey());
			sb.append("=");
			sb.append(parameterEntry.getValue());
			sb.append("\n");
		}

		sb.setLength(sb.length() - 1);

		return sb.toString();
	}

	protected String getBody(HttpExchange httpExchange) throws IOException {
		InputStream requestBody = httpExchange.getRequestBody();

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(requestBody));

		StringBuffer sb = new StringBuffer();
		String line;

		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}

		return sb.toString();
	}

	protected Map<String, String> getParameters(HttpExchange httpExchange)
		throws IOException {

		String body = getBody(httpExchange);
		String query = getQuery(httpExchange);

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.putAll(_parseParameters(body));
		parameters.putAll(_parseParameters(query));

		return parameters;
	}

	protected String getQuery(HttpExchange httpExchange) throws IOException {
		URI requestURI = httpExchange.getRequestURI();

		String requestURIString = requestURI.toString();

		if (!requestURIString.contains("?")) {
			return null;
		}

		return requestURIString.substring(requestURIString.indexOf("?") + 1);
	}

	protected String getResponseBody(HttpExchange httpExchange) {
		String contentType = getAccept(httpExchange);

		if ("application/json".equals(contentType)) {
			return SimulatorConstants.RESPONSE_SUCCESS_IN_JSON;
		}

		return SimulatorConstants.RESPONSE_SUCCESS_IN_PLAIN_TXT;
	}

	protected String getResponseBody(
		Map<String, String> parameters, HttpExchange httpExchange) {

		String acceptContentType = getAccept(httpExchange);

		if ("application/json".equals(acceptContentType)) {
			return getAsJSON(parameters);
		}

		return getAsPlainText(parameters);
	}

	protected boolean hasHeaderValue(
		String header, String value, HttpExchange httpExchange) {

		Headers requestHeaders = httpExchange.getRequestHeaders();

		for (Map.Entry<String, List<String>> headerEntry :
				requestHeaders.entrySet()) {

			String headerKey = headerEntry.getKey();

			if (!headerKey.equals(header)) {
				continue;
			}

			for (String headerValue : headerEntry.getValue()) {
				if (headerValue.equals(value)) {
					return true;
				}
			}
		}

		return false;
	}

	private Map<String, String> _parseParameters(
		String parameterEntriesString) {

		if (parameterEntriesString == null) {
			return Collections.emptyMap();
		}

		parameterEntriesString = parameterEntriesString.trim();

		if (parameterEntriesString.length() == 0) {
			return Collections.emptyMap();
		}

		Map<String, String> parameters = new HashMap<String, String>();

		String[] parameterEntries = parameterEntriesString.split("&");

		for (String parameterEntry : parameterEntries) {
			String[] parameterKeyValues = parameterEntry.split("=");

			parameters.put(parameterKeyValues[0], parameterKeyValues[1]);
		}

		return parameters;
	}

}