/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model.credentials;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.faro.engine.client.model.Credentials;

/**
 * @author Matthew Kong
 */
public class OAuth1Credentials implements Credentials {

	public static final String TYPE = "OAuth 1 Authentication";

	@Override
	public void clearPasswords() {
		_oAuthAccessSecret = null;
		_oAuthAccessToken = null;
	}

	@JsonProperty("oAuthAccessSecret")
	public String getOAuthAccessSecret() {
		return _oAuthAccessSecret;
	}

	@JsonProperty("oAuthAccessToken")
	public String getOAuthAccessToken() {
		return _oAuthAccessToken;
	}

	@JsonIgnore
	public String getOAuthAuthorizationURL() {
		return _oAuthAuthorizationURL;
	}

	@JsonProperty("oAuthConsumerKey")
	public String getOAuthConsumerKey() {
		return _oAuthConsumerKey;
	}

	@JsonProperty("oAuthConsumerSecret")
	public String getOAuthConsumerSecret() {
		return _oAuthConsumerSecret;
	}

	@JsonProperty("oAuthOwner")
	public OAuthOwner getOAuthOwner() {
		return _oAuthOwner;
	}

	@JsonIgnore
	public String getOAuthToken() {
		return _oAuthToken;
	}

	@JsonIgnore
	public String getOAuthTokenSecret() {
		return _oAuthTokenSecret;
	}

	@JsonIgnore
	public String getOAuthVerifier() {
		return _oAuthVerifier;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public void setOAuthAccessSecret(String oAuthAccessSecret) {
		_oAuthAccessSecret = oAuthAccessSecret;
	}

	public void setOAuthAccessToken(String oAuthAccessToken) {
		_oAuthAccessToken = oAuthAccessToken;
	}

	public void setOAuthAuthorizationURL(String oAuthAuthorizationURL) {
		_oAuthAuthorizationURL = oAuthAuthorizationURL;
	}

	public void setOAuthConsumerKey(String oAuthConsumerKey) {
		_oAuthConsumerKey = oAuthConsumerKey;
	}

	public void setOAuthConsumerSecret(String oAuthConsumerSecret) {
		_oAuthConsumerSecret = oAuthConsumerSecret;
	}

	public void setOAuthOwner(OAuthOwner oAuthOwner) {
		_oAuthOwner = oAuthOwner;
	}

	public void setOAuthToken(String oAuthToken) {
		_oAuthToken = oAuthToken;
	}

	public void setOAuthTokenSecret(String oAuthTokenSecret) {
		_oAuthTokenSecret = oAuthTokenSecret;
	}

	public void setOAuthVerifier(String oAuthVerifier) {
		_oAuthVerifier = oAuthVerifier;
	}

	private String _oAuthAccessSecret;
	private String _oAuthAccessToken;
	private String _oAuthAuthorizationURL;
	private String _oAuthConsumerKey;
	private String _oAuthConsumerSecret;
	private OAuthOwner _oAuthOwner;
	private String _oAuthToken;
	private String _oAuthTokenSecret;
	private String _oAuthVerifier;

}