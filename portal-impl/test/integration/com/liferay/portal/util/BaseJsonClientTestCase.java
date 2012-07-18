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

import java.lang.reflect.Constructor;

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

	public static final String URL_JSONWS = "/api/jsonws";

	@SuppressWarnings("unchecked")
	public void checkException(String responseContent) throws Exception {
		String exceptionString = parseResponseContent(
			responseContent, "exception", true);

		if (Validator.isNull(exceptionString)) {
			return;
		}

		Exception exception = null;

		try {
			int index = exceptionString.indexOf(": ");

			String className = exceptionString.substring(0, index);

			String message = exceptionString.substring(index + 2);

			Class<Exception> clazz = (Class<Exception>)Class.forName(className);

			Constructor<Exception> constructor = clazz.getDeclaredConstructor(
				String.class);

			exception = constructor.newInstance(message);
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

	public String executeRequest(HttpRequest request) throws Exception {
		return executeRequest(
			TestPropsValues.getLogin(), TestPropsValues.USER_PASSWORD, request);
	}

	public String executeRequest(
			String login, String password, HttpRequest request)
		throws Exception {

		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

		URL url = new URL(TestPropsValues.PORTAL_URL);

		HttpHost httpHost = new HttpHost(
			url.getHost(), url.getPort(), url.getProtocol());

		CredentialsProvider credentialsProvider =
			defaultHttpClient.getCredentialsProvider();

		credentialsProvider.setCredentials(
			new AuthScope(url.getHost(), url.getPort()),
			new UsernamePasswordCredentials(login, password));

		BasicAuthCache basicAuthCache = new BasicAuthCache();

		BasicScheme basicScheme = new BasicScheme();

		basicAuthCache.put(httpHost, basicScheme);

		BasicHttpContext basicHttpContext = new BasicHttpContext();

		basicHttpContext.setAttribute(ClientContext.AUTH_CACHE, basicAuthCache);

		return defaultHttpClient.execute(
			httpHost, request, new StringHandler(), basicHttpContext);
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

	public String parseResponseContent(
		String responseContent, String variable, boolean string) {

		variable = "\"" + variable + "\"";

		int beginIndex = responseContent.indexOf(variable);

		if (beginIndex == -1) {
			return null;
		}

		beginIndex += variable.length() + 1;

		int endIndex = -1;

		if (string) {
			beginIndex++;

			endIndex = responseContent.indexOf("\",", beginIndex);

			if (endIndex == -1) {
				endIndex = responseContent.length() - 2;
			}
		}
		else {
			endIndex = responseContent.indexOf(",", beginIndex);

			if (endIndex == -1) {
				endIndex = responseContent.length() - 1;
			}
		}

		return responseContent.substring(beginIndex, endIndex);
	}

	private class StringHandler implements ResponseHandler<String> {

		public String handleResponse(HttpResponse response)
			throws HttpResponseException, IOException {

			checkStatusCode(response.getStatusLine());

			HttpEntity httpEntity = response.getEntity();

			if (httpEntity == null) {
				return null;
			}

			return EntityUtils.toString(httpEntity);
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