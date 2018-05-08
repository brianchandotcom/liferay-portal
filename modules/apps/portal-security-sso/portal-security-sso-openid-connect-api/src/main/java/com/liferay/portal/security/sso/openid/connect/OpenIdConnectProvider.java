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

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 */
public class OpenIdConnectProvider {

	public OpenIdConnectProvider(
		String name, String clientId, String clientSecret, String scopes,
		OpenIdConnectMetadataFactory openIdConnectMetadataFactory) {

		_name = name;
		_clientId = clientId;
		_clientSecret = clientSecret;
		_scopes = scopes;
		_openIdConnectMetadataFactory = openIdConnectMetadataFactory;
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

	public OIDCClientMetadata getOIDCClientMetadata() {
		return _openIdConnectMetadataFactory.getOIDCClientMetadata();
	}

	public OIDCProviderMetadata getOIDCProviderMetadata()
		throws OpenIdConnectServiceException.ProviderException {

		return _openIdConnectMetadataFactory.getOIDCProviderMetadata();
	}

	public OpenIdConnectMetadataFactory getOpenIdConnectMetadataFactory() {
		return _openIdConnectMetadataFactory;
	}

	public String getScopes() {
		return _scopes;
	}

	private final String _clientId;
	private final String _clientSecret;
	private final String _name;
	private final OpenIdConnectMetadataFactory _openIdConnectMetadataFactory;
	private final String _scopes;

}