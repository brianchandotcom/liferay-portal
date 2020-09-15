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

package com.liferay.poshi.runner.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Michael Hashimoto
 */
public class HttpRequestUtil {

	public static Map<String, String> addRequestHeaders(
		Map<String, String> requestHeaders, String header) {

		if (requestHeaders == null) {
			requestHeaders = new HashMap<>();
		}

		int i = header.indexOf("=");

		if (i == -1) {
			throw new IllegalArgumentException("Invalid header: " + header);
		}

		String key = header.substring(0, i);
		String value = header.substring(i + 1);

		requestHeaders.put(key, value);

		return requestHeaders;
	}

	public static HttpResponse get(
			HttpAuthorization httpAuthorizationHeader, Integer maxRetries,
			Map<String, String> requestHeaders, Integer retryPeriod,
			Integer timeout, String url)
		throws IOException {

		return request(
			httpAuthorizationHeader, maxRetries, null, requestHeaders, "GET",
			retryPeriod, timeout, url);
	}

	public static HttpResponse get(
			HttpAuthorization httpAuthorizationHeader,
			Map<String, String> requestHeaders, String url)
		throws IOException {

		return request(
			httpAuthorizationHeader, _MAX_RETRIES_DEFAULT, null, requestHeaders,
			"GET", _RETRY_PERIOD_DEFAULT, _TIMEOUT_DEFAULT, url);
	}

	public static HttpAuthorization getHttpAuthorization(
		String type, String value) {

		if (type.equals("basic")) {
			String[] tokens = value.split(":");

			return new BasicHttpAuthorization(tokens[0], tokens[1]);
		}
		else if (type.equals("token")) {
			return new TokenHttpAuthorization(value);
		}

		throw new IllegalArgumentException(
			"Unsupported authorization type: " + type);
	}

	public static String getResponseBody(HttpResponse httpResponse) {
		return httpResponse.getResponseBody();
	}

	public static String getResponseErrorMessage(HttpResponse httpResponse) {
		return httpResponse.getResponseErrorMessage();
	}

	public static String getStatusCode(HttpResponse httpResponse) {
		return httpResponse.getStatusCode();
	}

	public static HttpResponse request(
			HttpAuthorization httpAuthorizationHeader, Integer maxRetries,
			String requestBody, Map<String, String> requestHeaders,
			String requestMethod, Integer retryPeriod, Integer timeout,
			String url)
		throws IOException {

		url = _fixURL(url);

		int retryCount = 0;

		while (true) {
			try {
				System.out.println("Generating HTTP request for URL: " + url);

				URL urlObject = new URL(url);

				URLConnection urlConnection = urlObject.openConnection();

				if (!(urlConnection instanceof HttpURLConnection)) {
					throw new IllegalArgumentException(
						"Connection must be of type HTTP");
				}

				HttpURLConnection httpURLConnection =
					(HttpURLConnection)urlConnection;

				System.out.println("Setting request method: " + requestMethod);

				httpURLConnection.setRequestMethod(requestMethod);

				if (httpAuthorizationHeader != null) {
					System.out.println(
						"Setting authorization: " + httpAuthorizationHeader);

					httpURLConnection.setRequestProperty(
						"Authorization", httpAuthorizationHeader.toString());
				}

				if (requestHeaders != null) {
					for (Map.Entry<String, String> requestHeader :
							requestHeaders.entrySet()) {

						System.out.println(
							"Setting request header key \"" +
								requestHeader.getKey() + "\" with value \"" +
									requestHeader.getValue() + "\"");

						httpURLConnection.setRequestProperty(
							requestHeader.getKey(), requestHeader.getValue());
					}
				}

				if (requestBody != null) {
					if (requestMethod.equals("GET")) {
						throw new IllegalArgumentException(
							"Request method 'GET' cannot have a request body");
					}

					System.out.println("Setting request body: " + requestBody);

					if ((requestHeaders != null) &&
						requestHeaders.containsKey("Content-Type")) {

						String contentType = requestHeaders.get("Content-Type");

						if (contentType.equals("application/json")) {
							JSONUtil.toJSONObject(requestBody);
						}
					}
					else {
						requestBody = URLEncoder.encode(requestBody, "UTF-8");
					}

					httpURLConnection.setDoOutput(true);

					try (OutputStream outputStream =
							httpURLConnection.getOutputStream()) {

						outputStream.write(requestBody.getBytes("UTF-8"));

						outputStream.flush();
					}
				}

				if (timeout != 0) {
					urlConnection.setConnectTimeout(timeout);
					urlConnection.setReadTimeout(timeout);
				}

				int responseCode = httpURLConnection.getResponseCode();

				if ((responseCode >= 200) && (responseCode <= 399)) {
					try (InputStream inputStream =
							httpURLConnection.getInputStream()) {

						String body = _readInputStream(inputStream, false);

						return new HttpResponse(body, null, responseCode);
					}
				}

				try (InputStream errorInputStream =
						httpURLConnection.getErrorStream()) {

					String errorMessage = _readInputStream(
						errorInputStream, false);

					return new HttpResponse(null, errorMessage, responseCode);
				}
			}
			catch (IOException ioException) {
				retryCount++;

				if ((maxRetries >= 0) && (retryCount >= maxRetries)) {
					throw ioException;
				}

				System.out.println(
					"Retrying " + url + " in " + retryPeriod + " seconds");

				ExecUtil.sleep(1000 * retryPeriod);
			}
		}
	}

	public static HttpResponse request(
			HttpAuthorization httpAuthorizationHeader, String requestBody,
			Map<String, String> requestHeaders, String requestMethod,
			String url)
		throws IOException {

		return request(
			httpAuthorizationHeader, _MAX_RETRIES_DEFAULT, requestBody,
			requestHeaders, requestMethod, _RETRY_PERIOD_DEFAULT,
			_TIMEOUT_DEFAULT, url);
	}

	public static class BasicHttpAuthorization extends HttpAuthorization {

		public BasicHttpAuthorization(String password, String userName) {
			super(Type.BASIC);

			this.password = password;
			this.userName = userName;
		}

		@Override
		public String toString() {
			String authorization = StringUtil.combine(userName, ":", password);

			return StringUtil.combine(
				"Basic ", Base64.encodeBase64String(authorization.getBytes()));
		}

		protected String password;
		protected String userName;

	}

	public abstract static class HttpAuthorization {

		public Type getType() {
			return type;
		}

		public static enum Type {

			BASIC, TOKEN

		}

		protected HttpAuthorization(Type type) {
			this.type = type;
		}

		protected Type type;

	}

	public static class HttpResponse {

		public HttpResponse(String body, String errorMessage, int statusCode) {
			this.body = body;
			this.errorMessage = errorMessage;
			this.statusCode = String.valueOf(statusCode);
		}

		public String getResponseBody() {
			return body;
		}

		public String getResponseErrorMessage() {
			return errorMessage;
		}

		public String getStatusCode() {
			return statusCode;
		}

		protected String body;
		protected String errorMessage;
		protected String statusCode;

	}

	public static class TokenHttpAuthorization extends HttpAuthorization {

		public TokenHttpAuthorization(String token) {
			super(Type.TOKEN);

			this.token = token;
		}

		@Override
		public String toString() {
			return StringUtil.combine("token ", token);
		}

		protected String token;

	}

	private static String _fixURL(String url) {
		url = StringUtil.replace(url, " ", "%20");
		url = StringUtil.replace(url, "#", "%23");
		url = StringUtil.replace(url, "(", "%28");
		url = StringUtil.replace(url, ")", "%29");
		url = StringUtil.replace(url, "[", "%5B");
		url = StringUtil.replace(url, "]", "%5D");

		return url;
	}

	private static String _readInputStream(
			InputStream inputStream, boolean resetAfterReading)
		throws IOException {

		if (resetAfterReading && !inputStream.markSupported()) {
			Class<?> inputStreamClass = inputStream.getClass();

			System.out.println(
				"Unable to reset after reading input stream " +
					inputStreamClass.getName());
		}

		if (resetAfterReading && inputStream.markSupported()) {
			inputStream.mark(Integer.MAX_VALUE);
		}

		StringBuffer sb = new StringBuffer();

		byte[] bytes = new byte[1024];

		int size = inputStream.read(bytes);

		while (size > 0) {
			sb.append(new String(Arrays.copyOf(bytes, size)));

			size = inputStream.read(bytes);
		}

		if (resetAfterReading && inputStream.markSupported()) {
			inputStream.reset();
		}

		return sb.toString();
	}

	private static final Integer _MAX_RETRIES_DEFAULT = 3;

	private static final Integer _RETRY_PERIOD_DEFAULT = 5;

	private static final Integer _TIMEOUT_DEFAULT = 0;

}