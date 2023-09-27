/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientInformation;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONObject;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 * @author Arthur Chan
 */
public class OpenIdConnectTokenRequestUtil {

	public static OIDCTokens request(
			AuthenticationSuccessResponse authenticationSuccessResponse,
			CodeVerifier codeVerifier, Nonce nonce,
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata, URI redirectURI,
			String tokenRequestParametersJSON)
		throws Exception {

		AuthorizationGrant authorizationCodeGrant = new AuthorizationCodeGrant(
			authenticationSuccessResponse.getAuthorizationCode(), redirectURI,
			codeVerifier);

		return _requestOIDCTokens(
			authorizationCodeGrant, nonce, oidcClientInformation,
			oidcProviderMetadata,
			JSONObjectUtils.parse(tokenRequestParametersJSON));
	}

	public static OIDCTokens request(
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata,
			RefreshToken refreshToken, String tokenRequestParametersJSON)
		throws Exception {

		AuthorizationGrant refreshTokenGrant = new RefreshTokenGrant(
			refreshToken);

		return _requestOIDCTokens(
			refreshTokenGrant, null, oidcClientInformation,
			oidcProviderMetadata,
			JSONObjectUtils.parse(tokenRequestParametersJSON));
	}

	private static OIDCTokens _requestOIDCTokens(
			AuthorizationGrant authorizationCodeGrant, Nonce nonce,
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata,
			JSONObject tokenRequestParametersJSONObject)
		throws Exception {

		URI uri = oidcProviderMetadata.getTokenEndpointURI();

		ClientID clientID = oidcClientInformation.getID();
		Secret secret = oidcClientInformation.getSecret();

		Map<String, List<String>> customRequestParametersMap = new HashMap<>();

		OpenIdConnectRequestParametersUtil.consumeCustomRequestParameters(
			(key, values) -> customRequestParametersMap.put(
				key, Arrays.asList(values)),
			tokenRequestParametersJSONObject);

		TokenRequest tokenRequest = new TokenRequest(
			uri, new ClientSecretBasic(clientID, secret),
			authorizationCodeGrant, null,
			Arrays.asList(
				OpenIdConnectRequestParametersUtil.getResourceURIs(
					tokenRequestParametersJSONObject)),
			customRequestParametersMap);

		HTTPRequest httpRequest = tokenRequest.toHTTPRequest();

		if (_log.isDebugEnabled()) {
			_log.debug("Query: " + httpRequest.getQuery());
		}

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
				oidcClientInformation.getOIDCMetadata(), oidcProviderMetadata,
				oidcTokens);

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
			OIDCProviderMetadata oidcProviderMetadata, OIDCTokens oidcTokens)
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
					new DefaultResourceRetriever(1000, 1000));
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

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectTokenRequestUtil.class);

}