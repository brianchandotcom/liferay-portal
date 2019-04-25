/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.client.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class HttpInvoker {

	public static HttpInvoker newHttpInvoker() {
		HttpInvoker httpInvoker = new HttpInvoker();

		return httpInvoker;
	}

	public HttpInvoker body(String body, String contentType) {
		_body = body;
		_contentType = contentType;

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
		httpResponse.setStatus(httpURLConnection.getResponseCode());

		httpURLConnection.disconnect();

		return httpResponse;
	}

	public HttpInvoker parameter(String name, String value) {
		_parameters.put(name, value);

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

		public int getStatus() {
			return _status;
		}

		public void setContent(String content) {
			_content = content;
		}

		public void setMessage(String message) {
			_message = message;
		}

		public void setStatus(int status) {
			_status = status;
		}

		private String _content;
		private String _message;
		private int _status;

	}

	private HttpInvoker() {
	}

	private String _getQueryString() throws IOException {
		StringBuilder sb = new StringBuilder();

		Set<Map.Entry<String, String>> set = _parameters.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			String name = URLEncoder.encode(entry.getKey(), "UTF-8");

			sb.append(name);

			sb.append("=");

			String value = URLEncoder.encode(entry.getValue(), "UTF-8");

			sb.append(value);

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

		if (_body == null) {
			return;
		}

		if ((_httpMethod == HttpMethod.DELETE) ||
			(_httpMethod == HttpMethod.GET)) {

			throw new IllegalArgumentException(
				"HTTP method " + _httpMethod + " must not contain a body");
		}

		httpURLConnection.setDoOutput(true);

		DataOutputStream dataOutputStream = new DataOutputStream(
			httpURLConnection.getOutputStream());

		dataOutputStream.writeBytes(_body);

		dataOutputStream.flush();

		dataOutputStream.close();
	}

	private String _body;
	private String _contentType;
	private String _encodedUserNameAndPassword;
	private Map<String, File> _files = new LinkedHashMap<>();
	private HttpMethod _httpMethod = HttpMethod.GET;
	private Map<String, String> _parameters = new LinkedHashMap<>();
	private Map<String, String> _parts = new LinkedHashMap<>();
	private String _path;

}