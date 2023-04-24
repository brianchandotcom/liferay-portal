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
public class OAuth2Credentials implements Credentials {

	public static final String TYPE = "OAuth 2 Authentication";

	@Override
	public void clearPasswords() {
		_oAuthRefreshToken = null;
	}

	@JsonProperty("oAuthAuthorizationURL")
	public String getOAuthAuthorizationURL() {
		return _oAuthAuthorizationURL;
	}

	@JsonIgnore
	public String getOAuthCallbackURL() {
		return _oAuthCallbackURL;
	}

	@JsonProperty("oAuthClientId")
	public String getOAuthClientId() {
		return _oAuthClientId;
	}

	@JsonProperty("oAuthClientSecret")
	public String getOAuthClientSecret() {
		return _oAuthClientSecret;
	}

	@JsonIgnore
	public String getOAuthCode() {
		return _oAuthCode;
	}

	@JsonProperty("oAuthOwner")
	public OAuthOwner getOAuthOwner() {
		return _oAuthOwner;
	}

	@JsonProperty("oAuthRefreshToken")
	public String getOAuthRefreshToken() {
		return _oAuthRefreshToken;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public void setOAuthAuthorizationURL(String oAuthAuthorizationURL) {
		_oAuthAuthorizationURL = oAuthAuthorizationURL;
	}

	public void setOAuthCallbackURL(String oAuthCallbackURL) {
		_oAuthCallbackURL = oAuthCallbackURL;
	}

	public void setOAuthClientId(String oAuthClientId) {
		_oAuthClientId = oAuthClientId;
	}

	public void setOAuthClientSecret(String oAuthClientSecret) {
		_oAuthClientSecret = oAuthClientSecret;
	}

	public void setOAuthCode(String oAuthCode) {
		_oAuthCode = oAuthCode;
	}

	public void setOAuthOwner(OAuthOwner oAuthOwner) {
		_oAuthOwner = oAuthOwner;
	}

	public void setOAuthRefreshToken(String oAuthRefreshToken) {
		_oAuthRefreshToken = oAuthRefreshToken;
	}

	private String _oAuthAuthorizationURL;
	private String _oAuthCallbackURL;
	private String _oAuthClientId;
	private String _oAuthClientSecret;
	private String _oAuthCode;
	private OAuthOwner _oAuthOwner;
	private String _oAuthRefreshToken;

}