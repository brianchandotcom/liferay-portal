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

package com.liferay.site.initializer.testray.extra.java.function.http;

import com.liferay.petra.http.invoker.HttpInvoker;
import com.liferay.site.initializer.testray.extra.java.function.util.PropsValues;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

/**
 * @author José Abelenda
 */
public class HttpUtil {

	public static JSONObject invoke(
			String body, String objectName, Map<String, String> headers,
			Map<String, String> parameters, HttpInvoker.HttpMethod method)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = invokeHttpResponse(
			body, objectName, headers, parameters, method);

		String content = httpResponse.getContent();

		if ((httpResponse.getStatusCode() / 100) != 2) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response content: " + content);
			_logger.log(
				Level.WARNING,
				"HTTP response message: " + httpResponse.getMessage());
			_logger.log(
				Level.WARNING,
				"HTTP response status code: " + httpResponse.getStatusCode());

			throw new Exception();
		}

		_logger.fine("HTTP response content: " + content);
		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return new JSONObject(content);
		}
		catch (Exception exception) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				exception);

			throw exception;
		}
	}

	public static HttpInvoker.HttpResponse invokeHttpResponse(
			String body, String objectName, Map<String, String> headers,
			Map<String, String> parameters, HttpInvoker.HttpMethod method)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(body, "application/json");

		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpInvoker.header(entry.getKey(), entry.getValue());
			}
		}

		if (parameters != null) {
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}
		}

		httpInvoker.httpMethod(method);

		httpInvoker.path(PropsValues.TESTRAY_BASE_URL + objectName);

		httpInvoker.userNameAndPassword(
			PropsValues.TESTRAY_USER + ":" + PropsValues.TESTRAY_PASSWORD);

		return httpInvoker.invoke();
	}

	public static JSONObject post(
			String body, String objectName, Map<String, String> headers,
			Map<String, String> parameters)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = postHttpResponse(
			body, objectName, headers, parameters);

		String content = httpResponse.getContent();

		if ((httpResponse.getStatusCode() / 100) != 2) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response content: " + content);
			_logger.log(
				Level.WARNING,
				"HTTP response message: " + httpResponse.getMessage());
			_logger.log(
				Level.WARNING,
				"HTTP response status code: " + httpResponse.getStatusCode());

			throw new Exception();
		}

		_logger.fine("HTTP response content: " + content);
		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return new JSONObject(content);
		}
		catch (Exception exception) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				exception);

			throw exception;
		}
	}

	public static HttpInvoker.HttpResponse postHttpResponse(
			String body, String objectName, Map<String, String> headers,
			Map<String, String> parameters)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(body, "application/json");

		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpInvoker.header(entry.getKey(), entry.getValue());
			}
		}

		if (parameters != null) {
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}
		}

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(PropsValues.TESTRAY_BASE_URL + objectName);

		httpInvoker.userNameAndPassword(
			PropsValues.TESTRAY_USER + ":" + PropsValues.TESTRAY_PASSWORD);

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		HttpUtil.class.getName());

}