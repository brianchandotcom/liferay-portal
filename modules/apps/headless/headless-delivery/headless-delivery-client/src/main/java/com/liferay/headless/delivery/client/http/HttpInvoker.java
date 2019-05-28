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

package com.liferay.headless.delivery.client.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class HttpInvoker {

	public static HttpInvoker newHttpInvoker() {
		_updateHttpURLConnectionClass();

		return new HttpInvoker();
	}

	public HttpInvoker body(String body, String contentType) {
		_body = body;
		_contentType = contentType;

		return this;
	}

	public HttpInvoker header(String name, String value) {
		_headers.put(name, value);

		return this;
	}

	public HttpInvoker httpMethod(HttpMethod httpMethod) {
		_httpMethod = httpMethod;

		return this;
	}

	public HttpResponse invoke() throws IOException {
		HttpResponse httpResponse = new HttpResponse();

		HttpURLConnection httpURLConnection = _openHttpURLConnection();

		httpResponse.setContent(_readResponse(httpURLConnection));
		httpResponse.setMessage(httpURLConnection.getResponseMessage());
		httpResponse.setStatusCode(httpURLConnection.getResponseCode());

		httpURLConnection.disconnect();

		return httpResponse;
	}

	public HttpInvoker multipart() {
		_contentType =
			"multipart/form-data; charset=utf-8; boundary=__MULTIPART_BOUNDARY__";
		_multipartBoundary = "__MULTIPART_BOUNDARY__";

		return this;
	}

	public HttpInvoker parameter(String name, String value) {
		return parameter(name, new String[] {value});
	}

	public HttpInvoker parameter(String name, String[] values) {
		String[] oldValues = _parameters.get(name);

		if (oldValues != null) {
			String[] newValues = new String[oldValues.length + values.length];

			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			System.arraycopy(
				values, 0, newValues, oldValues.length, values.length);

			_parameters.put(name, newValues);
		}
		else {
			_parameters.put(name, values);
		}

		return this;
	}

	public HttpInvoker part(String name, File file) {
		_files.put(name, file);

		return this;
	}

	public HttpInvoker part(String name, String value) {
		_parts.put(name, value);

		return this;
	}

	public HttpInvoker path(String path, Object... values) {
		for (int i = 0; (values != null) && (i < values.length); i++) {
			path = path.replaceFirst("\\{.*?\\}", String.valueOf(values[i]));
		}

		_path = path;

		return this;
	}

	public HttpInvoker userNameAndPassword(String userNameAndPassword)
		throws IOException {

		Base64.Encoder encoder = Base64.getEncoder();

		_encodedUserNameAndPassword = new String(
			encoder.encode(userNameAndPassword.getBytes("UTF-8")), "UTF-8");

		return this;
	}

	public enum HttpMethod {

		DELETE, GET, PATCH, POST, PUT

	}

	public class HttpResponse {

		public String getContent() {
			return _content;
		}

		public String getMessage() {
			return _message;
		}

		public int getStatusCode() {
			return _statusCode;
		}

		public void setContent(String content) {
			_content = content;
		}

		public void setMessage(String message) {
			_message = message;
		}

		public void setStatusCode(int statusCode) {
			_statusCode = statusCode;
		}

		private String _content;
		private String _message;
		private int _statusCode;

	}

	private static void _updateHttpURLConnectionClass() {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField(
				"methods");

			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");

			modifiersField.setAccessible(true);
			modifiersField.setInt(
				methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			Set<String> methodsFieldValue = new LinkedHashSet<>(
				Arrays.asList((String[])methodsField.get(null)));

			if (methodsFieldValue.contains("PATCH")) {
				return;
			}

			methodsFieldValue.add("PATCH");

			methodsField.set(null, methodsFieldValue.toArray(new String[0]));
		}
		catch (IllegalAccessException | NoSuchFieldException e) {
			_logger.warning("Unable to update HttpURLConnection class");
		}
	}

	private HttpInvoker() {
	}

	private void _appendPart(
			OutputStream outputStream, PrintWriter printWriter, String key,
			Object value)
		throws IOException {

		printWriter.append("\r\n--");
		printWriter.append(_multipartBoundary);
		printWriter.append("\r\nContent-Disposition: form-data; name=\"");
		printWriter.append(key);
		printWriter.append("\";");

		if (value instanceof File) {
			File file = (File)value;

			printWriter.append(" filename=\"");
			printWriter.append(file.getName());
			printWriter.append("\"\r\nContent-Type: ");
			printWriter.append(
				URLConnection.guessContentTypeFromName(file.getName()));
			printWriter.append("\r\n\r\n");

			printWriter.flush();

			byte[] buffer = new byte[4096];
			FileInputStream fileInputStream = new FileInputStream(file);
			int read = -1;

			while ((read = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
			}

			outputStream.flush();

			fileInputStream.close();
		}
		else {
			printWriter.append("\r\n\r\n");
			printWriter.append(value.toString());
		}

		printWriter.append("\r\n");
	}

	private String _getQueryString() throws IOException {
		StringBuilder sb = new StringBuilder();

		Set<Map.Entry<String, String[]>> set = _parameters.entrySet();

		Iterator<Map.Entry<String, String[]>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = iterator.next();

			String[] values = entry.getValue();

			for (int i = 0; i < values.length; i++) {
				String name = URLEncoder.encode(entry.getKey(), "UTF-8");

				sb.append(name);

				sb.append("=");

				String value = URLEncoder.encode(values[i], "UTF-8");

				sb.append(value);

				if ((i + 1) < values.length) {
					sb.append("&");
				}
			}

			if (iterator.hasNext()) {
				sb.append("&");
			}
		}

		return sb.toString();
	}

	private HttpURLConnection _openHttpURLConnection() throws IOException {
		String urlString = _path;

		String queryString = _getQueryString();

		if (queryString.length() > 0) {
			if (!urlString.contains("?")) {
				urlString += "?";
			}

			urlString += queryString;
		}

		URL url = new URL(urlString);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setRequestMethod(_httpMethod.name());

		if (_encodedUserNameAndPassword != null) {
			httpURLConnection.setRequestProperty(
				"Authorization", "Basic " + _encodedUserNameAndPassword);
		}

		if (_contentType != null) {
			httpURLConnection.setRequestProperty("Content-Type", _contentType);
		}

		for (Map.Entry<String, String> header : _headers.entrySet()) {
			httpURLConnection.setRequestProperty(
				header.getKey(), header.getValue());
		}

		_writeBody(httpURLConnection);

		return httpURLConnection;
	}

	private String _readResponse(HttpURLConnection httpURLConnection)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		int responseCode = httpURLConnection.getResponseCode();

		InputStream inputStream = null;

		if (responseCode > 299) {
			inputStream = httpURLConnection.getErrorStream();
		}
		else {
			inputStream = httpURLConnection.getInputStream();
		}

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(inputStream));

		while (true) {
			String line = bufferedReader.readLine();

			if (line == null) {
				break;
			}

			sb.append(line);
		}

		bufferedReader.close();

		return sb.toString();
	}

	private void _writeBody(HttpURLConnection httpURLConnection)
		throws IOException {

		if ((_body == null) && _files.isEmpty() && _parts.isEmpty()) {
			return;
		}

		if ((_httpMethod == HttpMethod.DELETE) ||
			(_httpMethod == HttpMethod.GET)) {

			throw new IllegalArgumentException(
				"HTTP method " + _httpMethod + " must not contain a body");
		}

		httpURLConnection.setDoOutput(true);

		OutputStream outputStream = httpURLConnection.getOutputStream();

		try (PrintWriter printWriter = new PrintWriter(
				new OutputStreamWriter(outputStream, "UTF-8"), true)) {

			if (_contentType.startsWith("multipart/form-data")) {
				for (Map.Entry<String, String> entry : _parts.entrySet()) {
					_appendPart(
						outputStream, printWriter, entry.getKey(),
						entry.getValue());
				}

				for (Map.Entry<String, File> entry : _files.entrySet()) {
					_appendPart(
						outputStream, printWriter, entry.getKey(),
						entry.getValue());
				}

				printWriter.append("--" + _multipartBoundary + "--");

				printWriter.flush();

				outputStream.flush();
			}
			else {
				printWriter.append(_body);

				printWriter.flush();
			}
		}
	}

	private static final Logger _logger = Logger.getLogger(
		HttpInvoker.class.getName());

	private String _body;
	private String _contentType;
	private String _encodedUserNameAndPassword;
	private Map<String, File> _files = new LinkedHashMap<>();
	private Map<String, String> _headers = new LinkedHashMap<>();
	private HttpMethod _httpMethod = HttpMethod.GET;
	private String _multipartBoundary;
	private Map<String, String[]> _parameters = new LinkedHashMap<>();
	private Map<String, String> _parts = new LinkedHashMap<>();
	private String _path;

}