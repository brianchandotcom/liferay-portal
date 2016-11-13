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

package com.liferay.portal.security.sso.openid.connect;

/**
 * @author Thuong Dinh
 */
public class OpenIdConnectProvider {

	public OpenIdConnectProvider(String name) {
		_name = name;
	}

	public String getAuthorizationEndPoint() {
		return _authorizationEndPoint;
	}

	public String getClientId() {
		return _clientId;
	}

	public String getClientSecret() {
		return _clientSecret;
	}

	public String getDiscoveryEndPoint() {
		return _discoveryEndPoint;
	}

	public String getIssuerUrl() {
		return _issuerUrl;
	}

	public String getJwksUri() {
		return _jwksUri;
	}

	public String getName() {
		return _name;
	}

	public String[] getSubjectTypes() {
		return _subjectTypes;
	}

	public String getTokenEndPoint() {
		return _tokenEndPoint;
	}

	public String getUserInfoEndPoint() {
		return _userInfoEndPoint;
	}

	public void setAuthorizationEndPoint(String authorizationEndPoint) {
		_authorizationEndPoint = authorizationEndPoint;
	}

	public void setClientId(String clientId) {
		_clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		_clientSecret = clientSecret;
	}

	public void setDiscoveryEndPoint(String discoveryEndPoint) {
		_discoveryEndPoint = discoveryEndPoint;
	}

	public void setIssuerUrl(String issuerUrl) {
		_issuerUrl = issuerUrl;
	}

	public void setJwksUri(String jwksUri) {
		_jwksUri = jwksUri;
	}

	public void setSubjectTypes(String[] subjectTypes) {
		_subjectTypes = subjectTypes;
	}

	public void setTokenEndPoint(String tokenEndPoint) {
		_tokenEndPoint = tokenEndPoint;
	}

	public void setUserInfoEndPoint(String userInfoEndPoint) {
		_userInfoEndPoint = userInfoEndPoint;
	}

	private String _authorizationEndPoint;
	private String _clientId;
	private String _clientSecret;
	private String _discoveryEndPoint;
	private String _issuerUrl;
	private String _jwksUri;
	private final String _name;
	private String[] _subjectTypes;
	private String _tokenEndPoint;
	private String _userInfoEndPoint;

}