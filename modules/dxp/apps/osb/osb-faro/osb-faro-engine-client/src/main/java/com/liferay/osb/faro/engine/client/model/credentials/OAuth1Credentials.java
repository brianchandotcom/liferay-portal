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