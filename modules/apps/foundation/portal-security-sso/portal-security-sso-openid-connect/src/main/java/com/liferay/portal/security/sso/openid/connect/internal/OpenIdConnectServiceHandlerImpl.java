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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderMetadataFactory;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectUserInfoProcessor;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.GeneralException;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.RefreshTokenGrant;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.token.Tokens;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 */
@Component(immediate = true, service = OpenIdConnectServiceHandler.class)
public class OpenIdConnectServiceHandlerImpl
	implements OpenIdConnectServiceHandler {

	@Override
	public boolean hasValidOpenIdConnectSession(HttpSession httpSession)
		throws PortalException {

		OpenIdConnectSession openIdConnectSession =
			(OpenIdConnectSession)httpSession.getAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

		boolean validAccessToken = hasValidAccessToken(openIdConnectSession);

		if (!validAccessToken) {
			return refreshAuthToken(openIdConnectSession);
		}
		else {
			return true;
		}
	}

	@Override
	public String processAuthenticationResponse(
			ThemeDisplay themeDisplay, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		try {
			AuthenticationResponse authenticationResponse =
				AuthenticationResponseParser.parse(
					new URI(themeDisplay.getURLCurrent()));

			if (authenticationResponse instanceof AuthenticationErrorResponse) {
				AuthenticationErrorResponse authenticationerrorresponse =
					(AuthenticationErrorResponse)authenticationResponse;

				ErrorObject errorObject =
					authenticationerrorresponse.getErrorObject();

				throw new OpenIdConnectServiceException.
					AuthenticationErrorException(errorObject.toString());
			}

			HttpSession httpSession = httpServletRequest.getSession();

			OpenIdConnectSession openIdConnectSession =
				(OpenIdConnectSession)httpSession.getAttribute(
					OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

			if (Validator.isNull(openIdConnectSession)) {
				throw new OpenIdConnectServiceException.
					AuthenticationErrorException(
						"No existing OpenId Connect Session Found");
			}

			AuthenticationSuccessResponse authenticationSuccessResponse =
				(AuthenticationSuccessResponse)authenticationResponse;

			validateState(
				openIdConnectSession.getState(),
				authenticationSuccessResponse.getState());

			String openIdConnectProviderName =
				openIdConnectSession.getOpenIdProviderName();

			OpenIdConnectProvider openIdConnectProvider =
				_openIdConnectProviderRegistry.getOpenIdConnectProvider(
					openIdConnectProviderName);

			if (openIdConnectProvider == null) {
				throw new OpenIdConnectServiceException.ProviderException(
					"Unable to get OpenId Connect provider with name " +
						openIdConnectProviderName);
			}

			OpenIdConnectProviderMetadataFactory
				openIdConnectProviderMetadataFactory =
					openIdConnectProvider.
						getOpenIdConnectProviderMetadataFactory();

			OIDCProviderMetadata oidcProviderMetadata =
				openIdConnectProviderMetadataFactory.getOIDCProviderMetadata();

			OIDCClientInformation oidcClientInformation =
				getOIDCClientInformation(
					openIdConnectProvider, oidcProviderMetadata);

			AuthorizationCode authorizationCode =
				authenticationSuccessResponse.getAuthorizationCode();

			OIDCTokenResponse oidcTokenResponse = requestIdToken(
				oidcClientInformation, httpServletRequest, authorizationCode,
				oidcProviderMetadata);

			validateIdToken(
				oidcClientInformation, openIdConnectSession.getNonce(),
				oidcProviderMetadata, oidcTokenResponse);

			Tokens tokens = oidcTokenResponse.getTokens();

			UserInfo userInfo = requestUserInfo(
				tokens.getAccessToken(), oidcProviderMetadata);

			long userId = _openIdConnectUserInfoProcessor.processUserInfo(
				userInfo, themeDisplay.getCompanyId());

			httpSession.setAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_LOGIN, userId);

			openIdConnectSession.setUserInfo(userInfo);

			updateSession(
				openIdConnectSession, tokens, System.currentTimeMillis());

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
			String openIdConnectProviderName,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		OpenIdConnectProvider openIdConnectProvider =
			_openIdConnectProviderRegistry.getOpenIdConnectProvider(
				openIdConnectProviderName);

		if (openIdConnectProvider == null) {
			throw new SystemException(
				"Unable to get OpenId Connect provider with name " +
					openIdConnectProviderName);
		}

		try {
			State state = new State();
			Nonce nonce = new Nonce();
			Scope scope = Scope.parse(openIdConnectProvider.getScopes());

			OpenIdConnectProviderMetadataFactory
				openIdConnectProviderMetadataFactory =
					openIdConnectProvider.
						getOpenIdConnectProviderMetadataFactory();

			OIDCProviderMetadata oidcProviderMetadata =
				openIdConnectProviderMetadataFactory.getOIDCProviderMetadata();

			OIDCClientInformation oidcClientInformation =
				getOIDCClientInformation(
					openIdConnectProvider, oidcProviderMetadata);

			AuthenticationRequest authenticationRequest =
				new AuthenticationRequest(
					oidcProviderMetadata.getAuthorizationEndpointURI(),
					new ResponseType(ResponseType.Value.CODE), scope,
					oidcClientInformation.getID(),
					createRedirectURI(httpServletRequest), state, nonce);

			URI authenticationRequestURI = authenticationRequest.toURI();

			httpServletResponse.sendRedirect(
				authenticationRequestURI.toString());

			OpenIdConnectSession openIdConnectSession =
				new OpenIdConnectSession(
					openIdConnectProviderName, nonce, state);

			HttpSession httpSession = httpServletRequest.getSession();

			httpSession.setAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION,
				openIdConnectSession);
		}
		catch (IOException | URISyntaxException e) {
			throw new SystemException(
				"Unable to communicate with OpenId Connect provider", e);
		}
	}

	protected URI createRedirectURI(HttpServletRequest httpServletRequest)
		throws PortalException, URISyntaxException {

		StringBundler sb = new StringBundler(5);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sb.append(
			_portal.getLayoutFriendlyURL(
				themeDisplay.getLayout(), themeDisplay));

		sb.append(StringPool.SLASH);
		sb.append(StringPool.DASH);
		sb.append(OpenIdConnectWebKeys.OPEN_ID_CONNECT_RESPONSE_ACTION_NAME);

		URI uri = new URI(sb.toString());

		return uri;
	}

	protected OIDCClientInformation getOIDCClientInformation(
			OpenIdConnectProvider openIdConnectProvider,
			OIDCProviderMetadata oidcProviderMetadata)
		throws OpenIdConnectServiceException {

		ClientID clientID = new ClientID(openIdConnectProvider.getClientId());

		OIDCClientMetadata oidcClientMetadata = new OIDCClientMetadata();

		List<JWEAlgorithm> jweAlgorithms =
			oidcProviderMetadata.getIDTokenJWEAlgs();

		if (ListUtil.isNotEmpty(jweAlgorithms)) {
			oidcClientMetadata.setIDTokenJWEAlg(jweAlgorithms.get(0));
		}

		List<JWSAlgorithm> jwsAlgorithms =
			oidcProviderMetadata.getIDTokenJWSAlgs();

		if (ListUtil.isNotEmpty(jwsAlgorithms)) {
			oidcClientMetadata.setIDTokenJWSAlg(jwsAlgorithms.get(0));
		}

		oidcClientMetadata.setJWKSetURI(oidcProviderMetadata.getJWKSetURI());

		Secret secret = new Secret(openIdConnectProvider.getClientSecret());

		return new OIDCClientInformation(
			clientID, new Date(), oidcClientMetadata, secret);
	}

	protected boolean hasValidAccessToken(
		OpenIdConnectSession openIdConnectSession) {

		AccessToken accessToken = openIdConnectSession.getAccessToken();

		if (Validator.isNull(accessToken)) {
			return false;
		}

		long lifetime = accessToken.getLifetime() * Time.SECOND;
		long currentTime = System.currentTimeMillis();
		long loginTime = openIdConnectSession.getLoginTime();

		if ((currentTime - loginTime) < lifetime) {
			return true;
		}

		return false;
	}

	protected boolean refreshAuthToken(
			OpenIdConnectSession openIdConnectSession)
		throws OpenIdConnectServiceException {

		synchronized (openIdConnectSession) {
			if (hasValidAccessToken(openIdConnectSession)) {
				return true;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"User session auth token is invalid, attempting to use " +
						"refresh token to obtain a valid auth token.");
			}

			RefreshToken refreshToken = openIdConnectSession.getRefreshToken();

			if (refreshToken == null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Cannot refresh auth token, no refresh token " +
							"supplied.");
				}

				return false;
			}

			String openIdConnectProviderName =
				openIdConnectSession.getOpenIdProviderName();

			OpenIdConnectProvider openIdConnectProvider =
				_openIdConnectProviderRegistry.getOpenIdConnectProvider(
					openIdConnectProviderName);

			if (openIdConnectProvider == null) {
				throw new OpenIdConnectServiceException.ProviderException(
					"Unable to get OpenId Connect provider with name " +
						openIdConnectProviderName);
			}

			OpenIdConnectProviderMetadataFactory
				openIdConnectProviderMetadataFactory =
					openIdConnectProvider.
						getOpenIdConnectProviderMetadataFactory();

			OIDCProviderMetadata oidcProviderMetadata =
				openIdConnectProviderMetadataFactory.getOIDCProviderMetadata();

			OIDCTokenResponse oidcTokenResponse = requestRefreshToken(
				refreshToken, openIdConnectProvider, oidcProviderMetadata);

			Tokens tokens = oidcTokenResponse.getOIDCTokens();

			updateSession(
				openIdConnectSession, tokens, System.currentTimeMillis());

			return true;
		}
	}

	protected OIDCTokenResponse requestIdToken(
			OIDCClientInformation oidcClientInformation,
			HttpServletRequest httpServletRequest,
			AuthorizationCode authorizationCode,
			OIDCProviderMetadata oidcProviderMetadata)
		throws IOException, ParseException,
			PortalException, URISyntaxException {

		URI redirectURI = createRedirectURI(httpServletRequest);

		TokenRequest tokenRequest = new TokenRequest(
			oidcProviderMetadata.getTokenEndpointURI(),
			new ClientSecretBasic(
				oidcClientInformation.getID(),
				oidcClientInformation.getSecret()),
			new AuthorizationCodeGrant(authorizationCode, redirectURI));

		HTTPRequest httpRequest = tokenRequest.toHTTPRequest();

		HTTPResponse httpResponse = httpRequest.send();

		TokenResponse tokenResponse = OIDCTokenResponseParser.parse(
			httpResponse);

		if (tokenResponse instanceof TokenErrorResponse) {
			TokenErrorResponse tokenErrorResponse =
				(TokenErrorResponse)tokenResponse;

			ErrorObject errorObject = tokenErrorResponse.getErrorObject();

			throw new OpenIdConnectServiceException.TokenErrorException(
				errorObject.toString());
		}

		return (OIDCTokenResponse)tokenResponse;
	}

	protected OIDCTokenResponse requestRefreshToken(
			RefreshToken refreshToken,
			OpenIdConnectProvider openIdConnectProvider,
			OIDCProviderMetadata oidcProviderMetadata)
		throws OpenIdConnectServiceException {

		try {
			AuthorizationGrant refreshTokenGrant = new RefreshTokenGrant(
				refreshToken);

			ClientID clientID = new ClientID(
				openIdConnectProvider.getClientId());
			Secret clientSecret = new Secret(
				openIdConnectProvider.getClientSecret());

			ClientAuthentication clientAuthentication = new ClientSecretBasic(
				clientID, clientSecret);

			URI tokenEndpoint = oidcProviderMetadata.getTokenEndpointURI();

			TokenRequest tokenRequest = new TokenRequest(
				tokenEndpoint, clientAuthentication, refreshTokenGrant);

			HTTPRequest httpRequest = tokenRequest.toHTTPRequest();

			HTTPResponse httpResponse = httpRequest.send();

			TokenResponse tokenResponse = OIDCTokenResponseParser.parse(
				httpResponse);

			if (tokenResponse instanceof TokenErrorResponse) {
				TokenErrorResponse tokenErrorResponse =
					(TokenErrorResponse)tokenResponse;

				ErrorObject errorObject = tokenErrorResponse.getErrorObject();

				throw new OpenIdConnectServiceException.TokenErrorException(
					errorObject.toString());
			}

			return (OIDCTokenResponse)tokenResponse;
		}
		catch (IOException | ParseException e) {
			throw new OpenIdConnectServiceException.
				AuthenticationErrorException(
					"Unable to process refresh request", e);
		}
	}

	protected UserInfo requestUserInfo(
			AccessToken accessToken, OIDCProviderMetadata oidcProviderMetadata)
		throws IOException, ParseException, PortalException {

		UserInfoRequest userInfoRequest = new UserInfoRequest(
			oidcProviderMetadata.getUserInfoEndpointURI(),
			(BearerAccessToken)accessToken);

		HTTPRequest httpRequest = userInfoRequest.toHTTPRequest();

		HTTPResponse httpResponse = httpRequest.send();

		UserInfoResponse userInfoResponse = UserInfoResponse.parse(
			httpResponse);

		if (userInfoResponse instanceof UserInfoErrorResponse) {
			UserInfoErrorResponse userInfoErrorResponse =
				(UserInfoErrorResponse)userInfoResponse;

			ErrorObject errorObject = userInfoErrorResponse.getErrorObject();

			throw new OpenIdConnectServiceException.UserInfoErrorException(
				errorObject.toString());
		}

		UserInfoSuccessResponse userInfoSuccessResponse =
			(UserInfoSuccessResponse)userInfoResponse;

		return userInfoSuccessResponse.getUserInfo();
	}

	protected void updateSession(
		OpenIdConnectSession session, Tokens tokens, long loginTime) {

		session.setAccessToken(tokens.getAccessToken());
		session.setRefreshToken(tokens.getRefreshToken());
		session.setLoginTime(loginTime);
	}

	protected IDTokenClaimsSet validateIdToken(
			OIDCClientInformation oidcClientInformation, Nonce nonce,
			OIDCProviderMetadata oidcProviderMetadata,
			OIDCTokenResponse oidcTokenResponse)
		throws BadJOSEException, GeneralException, JOSEException,
			   MalformedURLException, OpenIdConnectServiceException {

		IDTokenValidator idTokenValidator = IDTokenValidator.create(
			oidcProviderMetadata, oidcClientInformation, null);

		OIDCTokens oidcTokens = oidcTokenResponse.getOIDCTokens();

		return idTokenValidator.validate(oidcTokens.getIDToken(), nonce);
	}

	protected void validateState(State requestedState, State state)
		throws OpenIdConnectServiceException {

		if (!state.equals(requestedState)) {
			throw new OpenIdConnectServiceException.
				AuthenticationErrorException("Invalid state");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectServiceHandlerImpl.class);

	@Reference
	private OpenIdConnectProviderRegistry _openIdConnectProviderRegistry;

	@Reference
	private OpenIdConnectUserInfoProcessor _openIdConnectUserInfoProcessor;

	@Reference
	private Portal _portal;

}