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

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.RefreshTokenGrant;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;

import net.minidev.json.JSONObject;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 * @author Arthur Chan
 */
public class OpenIdConnectTokenRequestUtil {

	public static OIDCTokens request(
			AuthenticationSuccessResponse authenticationSuccessResponse,
			Nonce nonce,
			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
				openIdConnectProvider,
			URI redirectURI)
		throws OpenIdConnectServiceException.ProviderException,
			   OpenIdConnectServiceException.TokenException {

		AuthorizationGrant authorizationCodeGrant = new AuthorizationCodeGrant(
			authenticationSuccessResponse.getAuthorizationCode(), redirectURI);

		return _requestOIDCTokens(
			authorizationCodeGrant, nonce, openIdConnectProvider);
	}

	public static OIDCTokens request(
			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
				openIdConnectProvider,
			RefreshToken refreshToken)
		throws OpenIdConnectServiceException {

		AuthorizationGrant refreshTokenGrant = new RefreshTokenGrant(
			refreshToken);

		return _requestOIDCTokens(
			refreshTokenGrant, null, openIdConnectProvider);
	}

	private static OIDCTokens _requestOIDCTokens(
			AuthorizationGrant authorizationCodeGrant, Nonce nonce,
			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
				openIdConnectProvider)
		throws OpenIdConnectServiceException.ProviderException,
			   OpenIdConnectServiceException.TokenException {

		OIDCProviderMetadata oidcProviderMetadata =
			openIdConnectProvider.getOIDCProviderMetadata();

		URI uri = oidcProviderMetadata.getTokenEndpointURI();

		ClientID clientID = new ClientID(openIdConnectProvider.getClientId());
		Secret secret = new Secret(openIdConnectProvider.getClientSecret());

		TokenRequest tokenRequest = new TokenRequest(
			uri, new ClientSecretBasic(clientID, secret),
			authorizationCodeGrant);

		HTTPRequest httpRequest = tokenRequest.toHTTPRequest();

		try {
			HTTPResponse httpResponse = httpRequest.send();

			TokenResponse tokenResponse = OIDCTokenResponseParser.parse(
				httpResponse);

			if (tokenResponse instanceof TokenErrorResponse) {
				TokenErrorResponse tokenErrorResponse =
					(TokenErrorResponse)tokenResponse;

				ErrorObject errorObject = tokenErrorResponse.getErrorObject();

				JSONObject jsonObject = errorObject.toJSONObject();

				throw new OpenIdConnectServiceException.TokenException(
					jsonObject.toString());
			}

			OIDCTokenResponse oidcTokenResponse =
				(OIDCTokenResponse)tokenResponse;

			OIDCTokens oidcTokens = oidcTokenResponse.getOIDCTokens();

			_validate(
				clientID, secret, nonce,
				openIdConnectProvider.getOIDCClientMetadata(),
				oidcProviderMetadata, oidcTokens,
				openIdConnectProvider.getTokenConnectionTimeout());

			return oidcTokens;
		}
		catch (IOException ioException) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat(
					"Unable to get tokens from ", uri, ": ",
					ioException.getMessage()),
				ioException);
		}
		catch (ParseException parseException) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat(
					"Unable to parse tokens response from ", uri, ": ",
					parseException.getMessage()),
				parseException);
		}
	}

	private static IDTokenClaimsSet _validate(
			ClientID clientID, Secret clientSecret, Nonce nonce,
			OIDCClientMetadata oidcClientMetadata,
			OIDCProviderMetadata oidcProviderMetadata, OIDCTokens oidcTokens,
			int tokenConnectionTimeout)
		throws OpenIdConnectServiceException.TokenException {

		IDTokenValidator idTokenValidator = null;

		if (JWSAlgorithm.Family.HMAC_SHA.contains(
				oidcClientMetadata.getIDTokenJWSAlg())) {

			idTokenValidator = new IDTokenValidator(
				oidcProviderMetadata.getIssuer(), clientID,
				oidcClientMetadata.getIDTokenJWSAlg(), clientSecret);
		}
		else {
			URI uri = oidcProviderMetadata.getJWKSetURI();

			try {
				idTokenValidator = new IDTokenValidator(
					oidcProviderMetadata.getIssuer(), clientID,
					oidcClientMetadata.getIDTokenJWSAlg(), uri.toURL(),
					new DefaultResourceRetriever(
						tokenConnectionTimeout, tokenConnectionTimeout));
			}
			catch (MalformedURLException malformedURLException) {
				throw new OpenIdConnectServiceException.TokenException(
					"Invalid JSON web key URL: " +
						malformedURLException.getMessage(),
					malformedURLException);
			}
		}

		try {
			return idTokenValidator.validate(oidcTokens.getIDToken(), nonce);
		}
		catch (BadJOSEException | JOSEException exception) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat(
					"Unable to validate tokens for client \"", clientID, "\": ",
					exception.getMessage()),
				exception);
		}
	}

}