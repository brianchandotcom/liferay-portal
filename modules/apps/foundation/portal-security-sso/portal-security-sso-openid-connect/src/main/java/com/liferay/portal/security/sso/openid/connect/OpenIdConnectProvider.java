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
 * @author Edward C. Han
 */
public class OpenIdConnectProvider {

	public OpenIdConnectProvider(
		String name, String clientId, String clientSecret, String scopes,
		OpenIdConnectProviderMetadataFactory
			openIdConnectProviderMetadataFactory) {

		_name = name;
		_clientId = clientId;
		_clientSecret = clientSecret;
		_scopes = scopes;
		_openIdConnectProviderMetadataFactory =
			openIdConnectProviderMetadataFactory;
	}

	public String getClientId() {
		return _clientId;
	}

	public String getClientSecret() {
		return _clientSecret;
	}

	public String getName() {
		return _name;
	}

	public OpenIdConnectProviderMetadataFactory
		getOpenIdConnectProviderMetadataFactory() {

		return _openIdConnectProviderMetadataFactory;
	}

	public String getScopes() {
		return _scopes;
	}

	private final String _clientId;
	private final String _clientSecret;
	private final String _name;
	private final OpenIdConnectProviderMetadataFactory
		_openIdConnectProviderMetadataFactory;
	private final String _scopes;

}