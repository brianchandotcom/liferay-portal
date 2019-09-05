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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 */
public class OpenIdConnectProviderImpl
	implements OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> {

	public OpenIdConnectProviderImpl(
		String name, String clientId, String clientSecret, String scopes,
		OpenIdConnectMetadataFactory openIdConnectMetadataFactory) {

		_name = name;
		_clientId = clientId;
		_clientSecret = clientSecret;
		_scopes = scopes;
		_openIdConnectMetadataFactory = openIdConnectMetadataFactory;
	}

	@Override
	public String getClientId() {
		return _clientId;
	}

	@Override
	public String getClientSecret() {
		return _clientSecret;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public OIDCClientMetadata getOIDCClientMetadata() {
		return _openIdConnectMetadataFactory.getOIDCClientMetadata();
	}

	@Override
	public OIDCProviderMetadata getOIDCProviderMetadata()
		throws OpenIdConnectServiceException.ProviderException {

		return _openIdConnectMetadataFactory.getOIDCProviderMetadata();
	}

	@Override
	public String getScopes() {
		return _scopes;
	}

	private final String _clientId;
	private final String _clientSecret;
	private final String _name;
	private final OpenIdConnectMetadataFactory _openIdConnectMetadataFactory;
	private final String _scopes;

}