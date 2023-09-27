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