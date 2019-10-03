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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectFlowState;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.minidev.json.JSONObject;

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
		throws OpenIdConnectServiceException.NoOpenIdConnectSessionException {

		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			getOpenIdConnectSessionImpl(httpSession);

		if (!hasValidAccessToken(openIdConnectSessionImpl)) {
			try {
				return refreshAuthToken(openIdConnectSessionImpl);
			}
			catch (OpenIdConnectServiceException oicse) {
				_log.error(
					"Unable to refresh auth token: " + oicse.getMessage(),
					oicse);

				return false;
			}
		}

		return true;
	}

	@Override
	public void processAuthenticationResponse(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		AuthenticationSuccessResponse authenticationSuccessResponse =
			getAuthenticationSuccessResponse(httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			getOpenIdConnectSessionImpl(httpSession);

		if (!OpenIdConnectFlowState.AUTH_REQUESTED.equals(
				openIdConnectSessionImpl.getOpenIdConnectFlowState())) {

			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat(
					"OpenId Connect login flow is not in the ",
					OpenIdConnectFlowState.AUTH_REQUESTED, " state: ",
					openIdConnectSessionImpl.getOpenIdConnectFlowState()));
		}

		validateState(
			openIdConnectSessionImpl.getState(),
			authenticationSuccessResponse.getState());

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			openIdConnectProvider =
				_openIdConnectProviderRegistry.findOpenIdConnectProvider(
					openIdConnectSessionImpl.getOpenIdProviderName());

		OIDCProviderMetadata oidcProviderMetadata =
			openIdConnectProvider.getOIDCProviderMetadata();

		OIDCClientInformation oidcClientInformation = getOIDCClientInformation(
			openIdConnectProvider);

		URI redirectURI = getLoginRedirectURI(httpServletRequest);

		Tokens tokens = requestIdToken(
			authenticationSuccessResponse, oidcClientInformation,
			oidcProviderMetadata, redirectURI,
			openIdConnectSessionImpl.getNonce());

		updateSessionTokens(
			openIdConnectSessionImpl, tokens, System.currentTimeMillis());

		processUserInfo(
			_portal.getCompanyId(httpServletRequest), openIdConnectSessionImpl,
			oidcProviderMetadata);

		openIdConnectSessionImpl.setOpenIdConnectFlowState(
			OpenIdConnectFlowState.AUTH_COMPLETE);
	}

	@Override
	public void requestAuthentication(
			String openIdConnectProviderName,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			openIdConnectProvider =
				_openIdConnectProviderRegistry.findOpenIdConnectProvider(
					openIdConnectProviderName);

		HttpSession httpSession = httpServletRequest.getSession();

		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			getOpenIdConnectSessionImpl(httpSession, openIdConnectProviderName);

		if (openIdConnectSessionImpl == null) {
			openIdConnectSessionImpl = createAndSetOpenIdConnectSession(
				httpSession, openIdConnectProviderName);
		}

		URI authenticationRequestURI = getAuthenticationRequestURI(
			getLoginRedirectURI(httpServletRequest), openIdConnectProvider,
			openIdConnectSessionImpl.getNonce(),
			openIdConnectSessionImpl.getState(),
			Scope.parse(openIdConnectProvider.getScopes()));

		try {
			httpServletResponse.sendRedirect(
				authenticationRequestURI.toString());

			openIdConnectSessionImpl.setOpenIdConnectFlowState(
				OpenIdConnectFlowState.AUTH_REQUESTED);
		}
		catch (IOException ioe) {
			throw new SystemException(
				StringBundler.concat(
					"Unable to send user to OpenId Connect service ",
					authenticationRequestURI.toString(), ": ",
					ioe.getMessage()),
				ioe);
		}
	}

	protected OpenIdConnectSessionImpl createAndSetOpenIdConnectSession(
		HttpSession httpSession, String openIdConnectProviderName) {

		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			new OpenIdConnectSessionImpl(
				openIdConnectProviderName, new Nonce(), new State());

		httpSession.setAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION,
			openIdConnectSessionImpl);

		return openIdConnectSessionImpl;
	}

	protected URI getAuthenticationRequestURI(
			URI loginRedirectURI,
			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
				openIdConnectProvider,
			Nonce nonce, State state, Scope scope)
		throws OpenIdConnectServiceException.ProviderException {

		OIDCProviderMetadata oidcProviderMetadata =
			openIdConnectProvider.getOIDCProviderMetadata();

		OIDCClientInformation oidcClientInformation = getOIDCClientInformation(
			openIdConnectProvider);

		ResponseType responseType = new ResponseType(ResponseType.Value.CODE);

		AuthenticationRequest authenticationRequest = new AuthenticationRequest(
			oidcProviderMetadata.getAuthorizationEndpointURI(), responseType,
			scope, oidcClientInformation.getID(), loginRedirectURI, state,
			nonce);

		return authenticationRequest.toURI();
	}

	protected AuthenticationSuccessResponse getAuthenticationSuccessResponse(
			HttpServletRequest httpServletRequest)
		throws OpenIdConnectServiceException.AuthenticationException {

		StringBuffer requestURL = httpServletRequest.getRequestURL();

		if (Validator.isNotNull(httpServletRequest.getQueryString())) {
			requestURL.append(StringPool.QUESTION);
			requestURL.append(httpServletRequest.getQueryString());
		}

		try {
			URI requestURI = new URI(requestURL.toString());

			AuthenticationResponse authenticationResponse =
				AuthenticationResponseParser.parse(requestURI);

			if (authenticationResponse instanceof AuthenticationErrorResponse) {
				AuthenticationErrorResponse authenticationErrorResponse =
					(AuthenticationErrorResponse)authenticationResponse;

				ErrorObject errorObject =
					authenticationErrorResponse.getErrorObject();

				JSONObject jsonObject = errorObject.toJSONObject();

				throw new OpenIdConnectServiceException.AuthenticationException(
					jsonObject.toString());
			}

			return (AuthenticationSuccessResponse)authenticationResponse;
		}
		catch (ParseException | URISyntaxException e) {
			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat(
					"Unable to process response from ", requestURL.toString(),
					": ", e.getMessage()),
				e);
		}
	}

	protected URI getLoginRedirectURI(HttpServletRequest httpServletRequest) {
		try {
			StringBundler sb = new StringBundler(3);

			sb.append(_portal.getPortalURL(httpServletRequest));
			sb.append(_portal.getPathContext());
			sb.append(OpenIdConnectConstants.REDIRECT_URL_PATTERN);

			return new URI(sb.toString());
		}
		catch (URISyntaxException urise) {
			throw new SystemException(
				"Unable to generate OpenId Connect login redirect URI: " +
					urise.getMessage(),
				urise);
		}
	}

	protected OIDCClientInformation getOIDCClientInformation(
		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			openIdConnectProvider) {

		ClientID clientID = new ClientID(openIdConnectProvider.getClientId());

		Secret secret = new Secret(openIdConnectProvider.getClientSecret());

		return new OIDCClientInformation(
			clientID, new Date(), openIdConnectProvider.getOIDCClientMetadata(),
			secret);
	}

	protected OpenIdConnectSessionImpl getOpenIdConnectSessionImpl(
			HttpSession httpSession)
		throws OpenIdConnectServiceException.NoOpenIdConnectSessionException {

		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			getOpenIdConnectSessionImpl(httpSession, null);

		if (openIdConnectSessionImpl == null) {
			throw new OpenIdConnectServiceException.
				NoOpenIdConnectSessionException(
					"HTTP session does contain an OpenId Connect session");
		}

		return openIdConnectSessionImpl;
	}

	protected OpenIdConnectSessionImpl getOpenIdConnectSessionImpl(
		HttpSession httpSession, String expectedProviderName) {

		Object openIdConnectSessionObject = httpSession.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

		if (openIdConnectSessionObject instanceof OpenIdConnectSessionImpl) {
			OpenIdConnectSessionImpl openIdConnectSessionImpl =
				(OpenIdConnectSessionImpl)openIdConnectSessionObject;

			if (Validator.isNull(expectedProviderName) ||
				expectedProviderName.equals(
					openIdConnectSessionImpl.getOpenIdProviderName())) {

				return openIdConnectSessionImpl;
			}
		}

		return null;
	}

	protected boolean hasValidAccessToken(
		OpenIdConnectSessionImpl openIdConnectSessionImpl) {

		AccessToken accessToken = openIdConnectSessionImpl.getAccessToken();

		if (accessToken == null) {
			return false;
		}

		long currentTime = System.currentTimeMillis();
		long lifetime = accessToken.getLifetime() * Time.SECOND;
		long loginTime = openIdConnectSessionImpl.getLoginTime();

		if ((currentTime - loginTime) < lifetime) {
			return true;
		}

		return false;
	}

	protected void processUserInfo(
			long companyId, OpenIdConnectSessionImpl openIdConnectSessionImpl,
			OIDCProviderMetadata oidcProviderMetadata)
		throws PortalException {

		UserInfo userInfo = requestUserInfo(
			openIdConnectSessionImpl.getAccessToken(), oidcProviderMetadata);

		long userId = _openIdConnectUserInfoProcessor.processUserInfo(
			userInfo, companyId);

		openIdConnectSessionImpl.setLoginUserId(userId);

		openIdConnectSessionImpl.setUserInfo(userInfo);
	}

	protected boolean refreshAuthToken(
			OpenIdConnectSessionImpl openIdConnectSessionImpl)
		throws OpenIdConnectServiceException {

		if (hasValidAccessToken(openIdConnectSessionImpl)) {
			return true;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"User session auth token is invalid, attempting to use " +
					"refresh token to obtain a valid auth token");
		}

		RefreshToken refreshToken = openIdConnectSessionImpl.getRefreshToken();

		if (refreshToken == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to refresh auth token because no refresh token " +
						"is supplied");
			}

			return false;
		}

		String openIdConnectProviderName =
			openIdConnectSessionImpl.getOpenIdProviderName();

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			openIdConnectProvider =
				_openIdConnectProviderRegistry.findOpenIdConnectProvider(
					openIdConnectProviderName);

		OIDCProviderMetadata oidcProviderMetadata =
			openIdConnectProvider.getOIDCProviderMetadata();

		Tokens tokens = requestRefreshToken(
			refreshToken, getOIDCClientInformation(openIdConnectProvider),
			oidcProviderMetadata, openIdConnectSessionImpl.getNonce());

		updateSessionTokens(
			openIdConnectSessionImpl, tokens, System.currentTimeMillis());

		return true;
	}

	protected Tokens requestIdToken(
			AuthenticationSuccessResponse authenticationSuccessResponse,
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata, URI redirectURI,
			Nonce nonce)
		throws OpenIdConnectServiceException.TokenException {

		AuthorizationGrant authorizationCodeGrant = new AuthorizationCodeGrant(
			authenticationSuccessResponse.getAuthorizationCode(), redirectURI);

		return requestTokens(
			oidcClientInformation, oidcProviderMetadata, nonce,
			authorizationCodeGrant);
	}

	protected Tokens requestRefreshToken(
			RefreshToken refreshToken,
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata, Nonce nonce)
		throws OpenIdConnectServiceException {

		AuthorizationGrant refreshTokenGrant = new RefreshTokenGrant(
			refreshToken);

		return requestTokens(
			oidcClientInformation, oidcProviderMetadata, nonce,
			refreshTokenGrant);
	}

	protected Tokens requestTokens(
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata, Nonce nonce,
			AuthorizationGrant authorizationCodeGrant)
		throws OpenIdConnectServiceException.TokenException {

		ClientAuthentication clientAuthentication = new ClientSecretBasic(
			oidcClientInformation.getID(), oidcClientInformation.getSecret());

		URI tokenEndpoint = oidcProviderMetadata.getTokenEndpointURI();

		TokenRequest tokenRequest = new TokenRequest(
			tokenEndpoint, clientAuthentication, authorizationCodeGrant);

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

			validateToken(
				oidcClientInformation, nonce, oidcProviderMetadata,
				oidcTokenResponse);

			return oidcTokenResponse.getTokens();
		}
		catch (IOException ioe) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat(
					"Unable to get tokens from ", tokenEndpoint, ": ",
					ioe.getMessage()),
				ioe);
		}
		catch (ParseException pe) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat(
					"Unable to parse tokens response from ", tokenEndpoint,
					": ", pe.getMessage()),
				pe);
		}
	}

	protected UserInfo requestUserInfo(
			AccessToken accessToken, OIDCProviderMetadata oidcProviderMetadata)
		throws OpenIdConnectServiceException.UserInfoException {

		UserInfoRequest userInfoRequest = new UserInfoRequest(
			oidcProviderMetadata.getUserInfoEndpointURI(),
			(BearerAccessToken)accessToken);

		HTTPRequest httpRequest = userInfoRequest.toHTTPRequest();

		try {
			HTTPResponse httpResponse = httpRequest.send();

			UserInfoResponse userInfoResponse = UserInfoResponse.parse(
				httpResponse);

			if (userInfoResponse instanceof UserInfoErrorResponse) {
				UserInfoErrorResponse userInfoErrorResponse =
					(UserInfoErrorResponse)userInfoResponse;

				ErrorObject errorObject =
					userInfoErrorResponse.getErrorObject();

				JSONObject jsonObject = errorObject.toJSONObject();

				throw new OpenIdConnectServiceException.UserInfoException(
					jsonObject.toString());
			}

			UserInfoSuccessResponse userInfoSuccessResponse =
				(UserInfoSuccessResponse)userInfoResponse;

			return userInfoSuccessResponse.getUserInfo();
		}
		catch (IOException ioe) {
			throw new OpenIdConnectServiceException.UserInfoException(
				StringBundler.concat(
					"Unable to get user information from ",
					oidcProviderMetadata.getUserInfoEndpointURI(), ": ",
					ioe.getMessage()),
				ioe);
		}
		catch (ParseException pe) {
			throw new OpenIdConnectServiceException.UserInfoException(
				StringBundler.concat(
					"Unable to parse user information response from ",
					oidcProviderMetadata.getUserInfoEndpointURI(), ": ",
					pe.getMessage()),
				pe);
		}
	}

	protected void updateSessionTokens(
		OpenIdConnectSessionImpl openIdConnectSessionImpl, Tokens tokens,
		long loginTime) {

		openIdConnectSessionImpl.setAccessToken(tokens.getAccessToken());
		openIdConnectSessionImpl.setRefreshToken(tokens.getRefreshToken());
		openIdConnectSessionImpl.setLoginTime(loginTime);
	}

	protected void validateState(State requestedState, State state)
		throws OpenIdConnectServiceException {

		if (!state.equals(requestedState)) {
			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat(
					"Requested value \"", requestedState.getValue(),
					"\" and approved state \"", state.getValue(),
					"\" do not match"));
		}
	}

	protected IDTokenClaimsSet validateToken(
			OIDCClientInformation oidcClientInformation, Nonce nonce,
			OIDCProviderMetadata oidcProviderMetadata,
			OIDCTokenResponse oidcTokenResponse)
		throws OpenIdConnectServiceException.TokenException {

		try {
			IDTokenValidator idTokenValidator = IDTokenValidator.create(
				oidcProviderMetadata, oidcClientInformation, null);

			OIDCTokens oidcTokens = oidcTokenResponse.getOIDCTokens();

			return idTokenValidator.validate(oidcTokens.getIDToken(), nonce);
		}
		catch (GeneralException ge) {
			throw new OpenIdConnectServiceException.TokenException(
				"Unable to instantiate token validator: " + ge.getMessage(),
				ge);
		}
		catch (BadJOSEException | JOSEException e) {
			throw new OpenIdConnectServiceException.TokenException(
				"Unable to validate tokens: " + e.getMessage(), e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectServiceHandlerImpl.class);

	@Reference
	private OpenIdConnectProviderRegistry
		<OIDCClientMetadata, OIDCProviderMetadata>
			_openIdConnectProviderRegistry;

	@Reference
	private OpenIdConnectUserInfoProcessor _openIdConnectUserInfoProcessor;

	@Reference
	private Portal _portal;

}