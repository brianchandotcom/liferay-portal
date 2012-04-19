/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URL;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @author Alexander Chow
 */
public class BaseJsonClientTestCase {

	public static final String URL_JSONWS = "/api/secure/jsonws";

	@SuppressWarnings("unchecked")
	public void checkException(String response) throws Exception {
		String exceptionString = readResponse(response, "exception", true);

		if (Validator.isNull(exceptionString)) {
			return;
		}

		Exception exception = null;

		try {
			int index = exceptionString.indexOf(": ");

			String className = exceptionString.substring(0, index);

			String message = exceptionString.substring(index + 2);

			Class<Exception> clazz = (Class<Exception>)Class.forName(className);

			exception = clazz.getDeclaredConstructor(String.class).newInstance(
				message);
		}
		catch (Exception e) {
		}

		if (exception != null) {
			throw exception;
		}
		else {
			throw new Exception(exceptionString);
		}
	}

	public String executeHttpRequest(HttpRequest httpRequest) throws Exception {
		return executeHttpRequest(
			TestPropsValues.getLogin(), TestPropsValues.USER_PASSWORD,
			httpRequest);
	}

	public String executeHttpRequest(
			String login, String password, HttpRequest httpRequest)
		throws Exception {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		URL url = new URL(TestPropsValues.PORTAL_URL);

		HttpHost host = new HttpHost(
			url.getHost(), url.getPort(), url.getProtocol());

		CredentialsProvider credentialsProvider =
			httpClient.getCredentialsProvider();

		credentialsProvider.setCredentials(
			new AuthScope(url.getHost(), url.getPort()),
			new UsernamePasswordCredentials(login, password));

		BasicAuthCache authCache = new BasicAuthCache();

		BasicScheme basicAuth = new BasicScheme();

		authCache.put(host, basicAuth);

		BasicHttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.AUTH_CACHE, authCache);

		return httpClient.execute(
			host, httpRequest, new StringHandler(), localContext);
	}

	public ContentBody getByteArrayBody(
			byte[] bytes, String mimeType, String fileName)
		throws Exception {

		return new ByteArrayBody(bytes, mimeType, fileName);
	}

	public MultipartEntity getMultipartEntity(String[] names, Object[] values)
		throws Exception {

		MultipartEntity multipartEntity = new MultipartEntity();

		for (int i = 0; i < names.length; i++) {
			multipartEntity.addPart(names[i], getStringBody(values[i]));
		}

		return multipartEntity;
	}

	public ContentBody getStringBody(Object value) throws Exception {
		return new StringBody(String.valueOf(value), Charset.defaultCharset());
	}

	public String readResponse(
		String response, String variable, boolean string) {

		variable = "\"" + variable + "\"";

		int beginIndex = response.indexOf(variable);

		if (beginIndex == -1) {
			return null;
		}

		beginIndex += variable.length() + 1;

		if (string) {
			beginIndex++;

			int endIndex = response.indexOf("\",", beginIndex);

			if (endIndex == -1) {
				endIndex = response.length() - 2;
			}

			return response.substring(beginIndex, endIndex);
		}
		else {
			return response.substring(
				beginIndex, response.indexOf(",", beginIndex));
		}
	}

	private class StringHandler implements ResponseHandler<String> {

		public String handleResponse(HttpResponse httpResponse)
			throws HttpResponseException, IOException{

			checkStatusCode(httpResponse.getStatusLine());

			HttpEntity entity = httpResponse.getEntity();

			if (entity == null) {
				return null;
			}

			String response = EntityUtils.toString(entity);

			return response;
		}

		protected void checkStatusCode(StatusLine statusLine)
			throws HttpResponseException {

			if (statusLine.getStatusCode() != 200) {
				throw new HttpResponseException(
					statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
		}

	}

}