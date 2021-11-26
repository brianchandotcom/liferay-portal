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

import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.ClientSecretJWT;
import com.nimbusds.oauth2.sdk.auth.ClientSecretPost;
import com.nimbusds.oauth2.sdk.auth.PrivateKeyJWT;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 * @author Arthur Chan
 */
public class OpenIdConnectProviderImpl
	implements OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata> {

	public OpenIdConnectProviderImpl(
		String name, String clientId, String clientSecret,
		String configurationPid, String scopes,
		OpenIdConnectMetadataFactory openIdConnectMetadataFactory,
		int tokenConnectionTimeout, String clientAuthenticationPrivateKey) {

		// TODO LPS-139642

		_name = name;
		_clientId = clientId;
		_clientSecret = clientSecret;
		_configurationPid = configurationPid;
		_scopes = scopes;
		_openIdConnectMetadataFactory = openIdConnectMetadataFactory;
		_tokenConnectionTimeout = tokenConnectionTimeout;
		_clientAuthenticationPrivateKey = clientAuthenticationPrivateKey;
	}

	public ClientAuthentication getClientAuthentication() throws Exception {
		OIDCProviderMetadata oidcProviderMetadata = getOIDCProviderMetadata();

		OIDCClientMetadata oidcClientMetadata = getOIDCClientMetadata();

		ClientAuthenticationMethod clientAuthenticationMethod =
			oidcClientMetadata.getTokenEndpointAuthMethod();

		ClientID clientID = new ClientID(_clientId);

		Secret secret = new Secret(_clientSecret);

		if (clientAuthenticationMethod.equals(
				ClientAuthenticationMethod.CLIENT_SECRET_BASIC)) {

			return new ClientSecretBasic(clientID, secret);
		}
		else if (clientAuthenticationMethod.equals(
					ClientAuthenticationMethod.CLIENT_SECRET_POST)) {

			return new ClientSecretPost(clientID, secret);
		}
		else if (clientAuthenticationMethod.equals(
					ClientAuthenticationMethod.CLIENT_SECRET_JWT)) {

			return new ClientSecretJWT(
				clientID, oidcProviderMetadata.getTokenEndpointURI(),
				oidcClientMetadata.getTokenEndpointAuthJWSAlg(), secret);
		}
		else if (clientAuthenticationMethod.equals(
					ClientAuthenticationMethod.PRIVATE_KEY_JWT)) {

			if (JWSAlgorithm.Family.EC.contains(
					oidcClientMetadata.getTokenEndpointAuthJWSAlg())) {

				return new PrivateKeyJWT(
					clientID, oidcProviderMetadata.getTokenEndpointURI(),
					oidcClientMetadata.getTokenEndpointAuthJWSAlg(),
					(ECPrivateKey)_convertToPrivateKey(
						KeyFactory.getInstance("EC"),
						_clientAuthenticationPrivateKey),
					null, null);
			}

			return new PrivateKeyJWT(
				clientID, oidcProviderMetadata.getTokenEndpointURI(),
				oidcClientMetadata.getTokenEndpointAuthJWSAlg(),
				(RSAPrivateKey)_convertToPrivateKey(
					KeyFactory.getInstance("RSA"),
					_clientAuthenticationPrivateKey),
				null, null);
		}

		throw new IllegalArgumentException(
			"Unsupported Client Authentication type");
	}

	@Override
	public String getClientId() {
		return _clientId;
	}

	@Override
	public String getClientSecret() {
		return _clientSecret;
	}

	public String getConfigurationPid() {
		return _configurationPid;
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

	@Override
	public int getTokenConnectionTimeout() {
		return _tokenConnectionTimeout;
	}

	private PrivateKey _convertToPrivateKey(
			KeyFactory keyFactory, String clientAuthenticationPrivateKey)
		throws Exception {

		byte[] privateKeyBytes = Base64.decode(clientAuthenticationPrivateKey);

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);

		return keyFactory.generatePrivate(spec);
	}

	private final String _clientAuthenticationPrivateKey;
	private final String _clientId;
	private final String _clientSecret;
	private final String _configurationPid;
	private final String _name;
	private final OpenIdConnectMetadataFactory _openIdConnectMetadataFactory;
	private final String _scopes;
	private final int _tokenConnectionTimeout;

}