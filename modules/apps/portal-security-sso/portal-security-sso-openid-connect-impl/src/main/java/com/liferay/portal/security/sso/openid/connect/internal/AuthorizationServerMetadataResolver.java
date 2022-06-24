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

import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.net.URL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	immediate = true, service = AuthorizationServerMetadataResolver.class
)
public class AuthorizationServerMetadataResolver {

	public OIDCProviderMetadata resolveOIDCProviderMetadata(
			String authServerWellKnownURI)
		throws Exception {

		if (authServerWellKnownURI.endsWith("local")) {
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				_oAuthClientASLocalMetadataLocalService.
					getOAuthClientASLocalMetadata(authServerWellKnownURI);

			return OIDCProviderMetadata.parse(
				oAuthClientASLocalMetadata.getMetadataJSON());
		}

		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.GET, new URL(authServerWellKnownURI));

		HTTPResponse httpResponse = httpRequest.send();

		if (httpResponse.getStatusCode() != HTTPResponse.SC_OK) {
			throw new OpenIdConnectServiceException.ProviderException(
				httpResponse.getStatusMessage());
		}

		return OIDCProviderMetadata.parse(httpResponse.getContent());
	}

	@Reference
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

}