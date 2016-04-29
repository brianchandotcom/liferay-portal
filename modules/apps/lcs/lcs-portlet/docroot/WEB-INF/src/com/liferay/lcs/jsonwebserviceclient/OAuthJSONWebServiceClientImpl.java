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

package com.liferay.lcs.jsonwebserviceclient;

import com.liferay.lcs.oauth.OAuthUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceClientImpl;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * @author Igor Beslic
 */
public class OAuthJSONWebServiceClientImpl extends JSONWebServiceClientImpl {

	@Override
	public void resetHttpClient() {
	}

	public void setAccessSecret(String accessSecret) {
		_accessSecret = accessSecret;
	}

	public void setAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	public void testOAuthRequest() throws JSONWebServiceInvocationException {
		Map<String, String> parameters = new HashMap<>();

		parameters.put("clazz", "com.liferay.portal.model.User");

		String json = doGet(_URL_CLASSNAME_FETCH_CLASS_NAME_ID, parameters);

		if (json.contains("exception\":\"")) {
			int exceptionMessageStart = json.indexOf("exception\":\"") + 12;

			int exceptionMessageEnd = json.indexOf("\"", exceptionMessageStart);

			throw new JSONWebServiceInvocationException(
				json.substring(exceptionMessageStart, exceptionMessageEnd));
		}
	}

	@Override
	protected String execute(HttpRequestBase httpRequestBase)
		throws JSONWebServiceTransportException {

		try {
			if ((_accessToken == null) && (_accessSecret == null)) {
				throw new JSONWebServiceTransportException.
					AuthenticationFailure("OAuth credentials are not set");
			}

			OAuthService oAuthService = OAuthUtil.getOAuthService();

			Verb verb = Verb.valueOf(
				StringUtil.toUpperCase(httpRequestBase.getMethod()));

			String requestURL = OAuthUtil.buildURL(
				getHostName(), getHostPort(), getProtocol(),
				String.valueOf(httpRequestBase.getURI()));

			OAuthRequest oAuthRequest = new OAuthRequest(verb, requestURL);

			if (httpRequestBase instanceof HttpPost) {
				HttpEntity httpEntity = ((HttpPost)httpRequestBase).getEntity();

				BufferedReader bufferedReader = null;
				String line = null;
				StringBundler sb = new StringBundler();

				try {
					bufferedReader = new BufferedReader(
						new InputStreamReader(httpEntity.getContent()));

					while ((line = bufferedReader.readLine()) != null) {
						sb.append(line);
					}
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
				finally {
					if (bufferedReader != null) {
						try {
							bufferedReader.close();
						}
						catch (IOException ioe) {
							throw new RuntimeException(ioe);
						}
					}
				}

				Scanner contentScanner = new Scanner(sb.toString());

				contentScanner.useDelimiter("&");

				while (contentScanner.hasNext()) {
					Scanner keyValueScanner = new Scanner(
						contentScanner.next());

					if (!keyValueScanner.hasNext()) {
						continue;
					}

					keyValueScanner.useDelimiter("=");

					String key = keyValueScanner.next();

					String value = "";

					if (keyValueScanner.hasNext()) {
						value = keyValueScanner.next();
					}

					oAuthRequest.addQuerystringParameter(key, value);
				}
			}

			oAuthService.signRequest(
				new Token(_accessToken, _accessSecret), oAuthRequest);

			Response response = oAuthRequest.send();

			if (response.getCode() == HttpServletResponse.SC_UNAUTHORIZED) {
				String value = response.getHeader("WWW-Authenticate");

				throw new JSONWebServiceTransportException.
					AuthenticationFailure(value);
			}

			int responseCode = response.getCode();

			if (responseCode == HttpServletResponse.SC_OK) {
				return response.getBody();
			}
			else if ((responseCode == HttpServletResponse.SC_BAD_REQUEST) ||
					 (responseCode == HttpServletResponse.SC_FORBIDDEN) ||
					 (responseCode == HttpServletResponse.SC_NOT_ACCEPTABLE) ||
					 (responseCode == HttpServletResponse.SC_NOT_FOUND)) {

				return response.getBody();
			}
			else {
				return "{\"exception\":\"Server returned " + responseCode +
					".\"}";
			}
		}
		finally {
			httpRequestBase.releaseConnection();
		}
	}

	private static final String _URL_CLASSNAME = "/api/jsonws/classname/";

	private static final String _URL_CLASSNAME_FETCH_CLASS_NAME_ID =
		_URL_CLASSNAME + "fetch-class-name-id";

	private String _accessSecret;
	private String _accessToken;

}