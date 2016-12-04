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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectUserInfoProcessor;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.GeneralException;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.Tokens;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.UserInfoErrorResponse;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientInformation;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Thuong Dinh
 */
@Component(immediate = true, service = OpenIdConnectServiceHandler.class)
public class OpenIdConnectServiceHandlerImpl
	implements OpenIdConnectServiceHandler {

	@Override
	public String processAuthenticationResponse(
			ThemeDisplay themeDisplay, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws PortalException {

		String requestURL = themeDisplay.getURLCurrent();

		try {
			HttpServletRequest httpServletRequest = getOriginalServletRequest(
				actionRequest);

			HttpSession httpSession = httpServletRequest.getSession();

			AuthenticationResponse authResp =
				AuthenticationResponseParser.parse(new URI(requestURL));

			if (authResp instanceof AuthenticationErrorResponse) {
				ErrorObject error =
					((AuthenticationErrorResponse)authResp).getErrorObject();

				throw new OpenIdConnectServiceException.
					AuthenticationErrorException(
						"Error with code " + error.toString());
			}

			AuthenticationSuccessResponse successResponse =
				(AuthenticationSuccessResponse)authResp;

			/* * Check the state! The state in the received
			 * authentication response must match the state specified in the
			 * previous outgoing authentication request.
			 */
			if (!verifyState(successResponse.getState(), httpSession)) {
				throw new OpenIdConnectServiceException.
					AuthenticationErrorException(
						"Wrong State code from authentication step");
			}

			AuthorizationCode authCode = successResponse.getAuthorizationCode();

			// Request Token

			String providerName = (String)httpSession.getAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_PROVIDER_NAME);

			OpenIdConnectProvider openIdConnectProvider =
				_openIdConnectProviderRegistry.getOpenIdConnectProvider(
					providerName);

			OIDCProviderMetadata oidcProviderMetadata =
				initOIDCProviderMetadata(openIdConnectProvider);

			OIDCTokenResponse tokenResponse = requestToken(
				providerName, oidcProviderMetadata, authCode,
				httpServletRequest);

			validateToken(
				providerName, tokenResponse, actionRequest,
				oidcProviderMetadata);

			Tokens tokens = tokenResponse.getTokens();

			AccessToken accessToken = tokens.getAccessToken();

			// Request User Information

			UserInfo userInfo = requestUserInfo(
				oidcProviderMetadata, accessToken);

			long userId = _openIdConnectUserInfoProcessor.processUserInfo(
				userInfo, themeDisplay);

			httpSession.setAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_LOGIN, userId);

			return null;
		}
		catch (BadJOSEException | GeneralException | IOException |
			   JOSEException | URISyntaxException e) {

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			throw new SystemException(e);
		}
	}

	@Override
	public void requestAuthentication(
			String providerName, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws PortalException {

		HttpServletRequest httpServletRequest = getOriginalServletRequest(
			actionRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		HttpSession httpSession = httpServletRequest.getSession();

		OpenIdConnectProvider openIdConnectProvider =
			_openIdConnectProviderRegistry.getOpenIdConnectProvider(
				providerName);

		// Generate random state string for pairing the
		// httpServletResponse to the request

		State state = new State();

		// Generate nonce

		Nonce nonce = new Nonce();

		// Specify scope

		Scope scope = Scope.parse(_OPENID_CONNECT_SCOPE);

		// Compose the request

		// Store state to httpSession to verify later

		httpSession.setAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_PROVIDER_NAME, providerName);

		httpSession.setAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_STATE, state);

		httpSession.setAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_NONCE, nonce);

		try {
			OIDCProviderMetadata oidcProviderMetadata =
				initOIDCProviderMetadata(openIdConnectProvider);

			OIDCClientInformation oidcClientInformation =
				getOIDCClientInformation(providerName, oidcProviderMetadata);

			URI redirectURI = createRedirectURI(httpServletRequest);

			ResponseType responseType = new ResponseType(
				ResponseType.Value.CODE);

			AuthenticationRequest authenticationRequest =
				new AuthenticationRequest(
					oidcProviderMetadata.getAuthorizationEndpointURI(),
					responseType, scope, oidcClientInformation.getID(),
					redirectURI, state, nonce);

			URI authenticationRequestURI = authenticationRequest.toURI();

			httpServletResponse.sendRedirect(
				authenticationRequestURI.toString());
		}
		catch (IOException | ParseException | URISyntaxException e) {
			throw new SystemException(
				"Unable to communicate with OpenId Connect Provider", e);
		}
	}

	protected URI createRedirectURI(HttpServletRequest httpServletRequest)
		throws PortalException, URISyntaxException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler friendlyURL = new StringBundler(5);

		friendlyURL.append(
			_portal.getLayoutFriendlyURL(
				themeDisplay.getLayout(), themeDisplay));

		friendlyURL.append(StringPool.SLASH);
		friendlyURL.append(StringPool.DASH);
		friendlyURL.append(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_RESPONSE_ACTION_NAME);

		URI uri = new URI(friendlyURL.toString());

		return uri;
	}

	protected OIDCClientInformation getOIDCClientInformation(
			String providerName, OIDCProviderMetadata oidcProviderMetadata)
		throws OpenIdConnectServiceException {

		OpenIdConnectProvider openIdConnectProvider =
			_openIdConnectProviderRegistry.getOpenIdConnectProvider(
				providerName);

		if (Validator.isNull(openIdConnectProvider)) {
			_log.error("No provider configured: " + providerName);

			throw new OpenIdConnectServiceException.
				MissingClientInformationException("Missing Client Information");
		}

		ClientID clientId = new ClientID(openIdConnectProvider.getClientId());
		Secret clientSecret = new Secret(
			openIdConnectProvider.getClientSecret());

		OIDCClientMetadata oidcClientMetadata = new OIDCClientMetadata();

		oidcClientMetadata.setJWKSetURI(oidcProviderMetadata.getJWKSetURI());

		List<JWSAlgorithm> jwsAlgorithms =
			oidcProviderMetadata.getIDTokenJWSAlgs();

		if (ListUtil.isNotEmpty(jwsAlgorithms)) {
			oidcClientMetadata.setIDTokenJWSAlg(jwsAlgorithms.get(0));
		}

		List<JWEAlgorithm> jweAlgorithms =
			oidcProviderMetadata.getIDTokenJWEAlgs();

		if (ListUtil.isNotEmpty(jweAlgorithms)) {
			oidcClientMetadata.setIDTokenJWEAlg(jweAlgorithms.get(0));
		}

		return new OIDCClientInformation(
			clientId, new Date(), oidcClientMetadata, clientSecret);
	}

	protected HttpServletRequest getOriginalServletRequest(
		ActionRequest actionRequest) {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		return httpServletRequest;
	}

	protected OIDCProviderMetadata initOIDCProviderMetadata(
			OpenIdConnectProvider openIdConnectProvider)
		throws IOException, ParseException, PortalException,
			URISyntaxException {

		OIDCProviderMetadata oidcProviderMetadata = null;

		InputStream discoveryEndpointStream = null;

		String discoveryEndpoint = openIdConnectProvider.getDiscoveryEndPoint();

		try {
			if (Validator.isNull(discoveryEndpoint)) {
				Issuer issuer = new Issuer(
					openIdConnectProvider.getIssuerUrl());

				List<SubjectType> subjectTypes = new ArrayList<>();

				for (String subjectType :
						openIdConnectProvider.getSubjectTypes()) {

					subjectTypes.add(SubjectType.parse(subjectType));
				}

				URI jwkSetURI = new URI(openIdConnectProvider.getJwksUri());

				oidcProviderMetadata = new OIDCProviderMetadata(
					issuer, subjectTypes, jwkSetURI);

				URI authorizationEndPointURI = new URI(
					openIdConnectProvider.getAuthorizationEndPoint());

				oidcProviderMetadata.setAuthorizationEndpointURI(
					authorizationEndPointURI);

				URI tokenEndpointURI = new URI(
					openIdConnectProvider.getTokenEndPoint());

				oidcProviderMetadata.setTokenEndpointURI(tokenEndpointURI);

				URI userInfoEndpointURI = new URI(
					openIdConnectProvider.getUserInfoEndPoint());

				oidcProviderMetadata.setUserInfoEndpointURI(
					userInfoEndpointURI);
			}
			else {
				URI discoveryEndpointURI = new URI(
					openIdConnectProvider.getDiscoveryEndPoint());

				URL discoveryEndpointURL = discoveryEndpointURI.toURL();

				discoveryEndpointStream = discoveryEndpointURL.openStream();

				String providerInfo = null;

				try (Scanner scanner = new Scanner(discoveryEndpointStream)) {
					Scanner delimiterScanner = scanner.useDelimiter(
						_DISCOVERY_END_POINT_DELIMITER);

					if (delimiterScanner.hasNext()) {
						providerInfo = scanner.next();
					}
					else {
						providerInfo = StringPool.BLANK;
					}
				}

				oidcProviderMetadata = OIDCProviderMetadata.parse(providerInfo);
			}
		}
		finally {
			if (Validator.isNotNull(discoveryEndpointStream)) {
				StreamUtil.cleanUp(discoveryEndpointStream);
			}
		}

		return oidcProviderMetadata;
	}

	protected OIDCTokenResponse requestToken(
			String providerName, OIDCProviderMetadata oidcProviderMetadata,
			AuthorizationCode authorizationCode,
			HttpServletRequest httpServletRequest)
		throws IOException, ParseException,
			PortalException, URISyntaxException {

		OIDCClientInformation oidcClientInformation = getOIDCClientInformation(
			providerName, oidcProviderMetadata);

		URI redirectURI = createRedirectURI(httpServletRequest);

		TokenRequest tokenRequest = new TokenRequest(
			oidcProviderMetadata.getTokenEndpointURI(),
			new ClientSecretBasic(
				oidcClientInformation.getID(),
				oidcClientInformation.getSecret()),
			new AuthorizationCodeGrant(authorizationCode, redirectURI));

		HTTPResponse httpResponse = tokenRequest.toHTTPRequest().send();

		// Parse and check response

		TokenResponse tokenResponse = OIDCTokenResponseParser.parse(
			httpResponse);

		if (tokenResponse instanceof TokenErrorResponse) {
			ErrorObject error =
				((TokenErrorResponse)tokenResponse).getErrorObject();

			throw new OpenIdConnectServiceException.TokenErrorException(
				"Error with code " + error.toString());
		}

		return (OIDCTokenResponse)tokenResponse;
	}

	protected UserInfo requestUserInfo(
			OIDCProviderMetadata providerMetadata, AccessToken accessToken)
		throws IOException, ParseException, PortalException {

		UserInfoRequest userInfoRequest = new UserInfoRequest(
			providerMetadata.getUserInfoEndpointURI(),
			(BearerAccessToken)accessToken);

		HTTPRequest httpRequest = userInfoRequest.toHTTPRequest();

		HTTPResponse httpResponse = httpRequest.send();

		UserInfoResponse userInfoResponse = UserInfoResponse.parse(
			httpResponse);

		if (userInfoResponse instanceof UserInfoErrorResponse) {
			ErrorObject error =
				((UserInfoErrorResponse)userInfoResponse).getErrorObject();

			throw new OpenIdConnectServiceException.UserInfoErrorException(
				"Error with code " + error.toString());
		}

		UserInfoSuccessResponse successResponse =
			(UserInfoSuccessResponse)userInfoResponse;

		return successResponse.getUserInfo();
	}

	protected IDTokenClaimsSet validateToken(
			String providerName, OIDCTokenResponse tokenResponse,
			ActionRequest actionRequest,
			OIDCProviderMetadata oidcProviderMetadata)
		throws BadJOSEException, GeneralException, JOSEException,
			MalformedURLException, OpenIdConnectServiceException {

		OIDCClientInformation oidcClientInformation = getOIDCClientInformation(
			providerName, oidcProviderMetadata);

		HttpServletRequest httpServletRequest = getOriginalServletRequest(
			actionRequest);

		Nonce nonce = (Nonce)httpServletRequest.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_NONCE);

		IDTokenValidator idTokenValidator = IDTokenValidator.create(
			oidcProviderMetadata, oidcClientInformation, null);

		OIDCTokens oidcTokens = tokenResponse.getOIDCTokens();

		JWT jwtToken = oidcTokens.getIDToken();

		IDTokenClaimsSet idTokenClaimsSet = idTokenValidator.validate(
			jwtToken, nonce);

		return idTokenClaimsSet;
	}

	protected boolean verifyState(State state, HttpSession session) {
		State authState = (State)session.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_STATE);

		if (Validator.isNull(authState)) {
			return false;
		}

		return state.equals(authState);
	}

	private static final String _DISCOVERY_END_POINT_DELIMITER = "\\A";

	private static final String _OPENID_CONNECT_SCOPE = "openid email profile";

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectServiceHandlerImpl.class);

	@Reference
	private OpenIdConnectProviderRegistry _openIdConnectProviderRegistry;

	@Reference
	private OpenIdConnectUserInfoProcessor _openIdConnectUserInfoProcessor;

	@Reference
	private Portal _portal;

}