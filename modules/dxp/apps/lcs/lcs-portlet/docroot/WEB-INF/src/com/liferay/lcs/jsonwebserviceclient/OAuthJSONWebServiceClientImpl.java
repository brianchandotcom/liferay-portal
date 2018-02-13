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
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthException;

import org.apache.http.client.methods.HttpRequestBase;

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

	public void testOAuthRequest()
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

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
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		if ((_accessToken == null) && (_accessSecret == null)) {
			throw new JSONWebServiceTransportException.AuthenticationFailure(
				"OAuth credentials are not set");
		}

		OAuthConsumer oAuthConsumer = OAuthUtil.getOAuthConsumer(
			_accessToken, _accessSecret);

		String requestURL = OAuthUtil.buildURL(
			getHostName(), getHostPort(), getProtocol(),
			String.valueOf(httpRequestBase.getURI()));

		httpRequestBase.setURI(URI.create(requestURL));

		try {
			oAuthConsumer.sign(httpRequestBase);
		}
		catch (OAuthException oae) {
			throw new JSONWebServiceTransportException.CommunicationFailure(
				"Unable to sign HTTP request", oae);
		}

		return super.execute(httpRequestBase);
	}

	private static final String _URL_CLASSNAME = "/api/jsonws/classname/";

	private static final String _URL_CLASSNAME_FETCH_CLASS_NAME_ID =
		_URL_CLASSNAME + "fetch-class-name-id";

	private String _accessSecret;
	private String _accessToken;

}