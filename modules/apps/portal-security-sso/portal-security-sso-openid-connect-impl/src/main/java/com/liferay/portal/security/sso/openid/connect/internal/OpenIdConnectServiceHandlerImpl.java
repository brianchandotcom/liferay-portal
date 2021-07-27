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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectFlowState;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.security.sso.openid.connect.internal.provider.OpenIdConnectSessionProviderImpl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ErrorObject;
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
			_getOpenIdConnectSessionImpl(httpSession);

		if (!_hasValidAccessToken(openIdConnectSessionImpl)) {
			try {
				return _refreshAuthToken(openIdConnectSessionImpl);
			}
			catch (OpenIdConnectServiceException
						openIdConnectServiceException) {

				_log.error(
					"Unable to refresh auth token: " +
						openIdConnectServiceException.getMessage(),
					openIdConnectServiceException);

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

		HttpSession httpSession = httpServletRequest.getSession();

		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			_getOpenIdConnectSessionImpl(httpSession);

		if (!OpenIdConnectFlowState.AUTH_REQUESTED.equals(
				openIdConnectSessionImpl.getOpenIdConnectFlowState())) {

			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat(
					"OpenId Connect login flow is not in the ",
					OpenIdConnectFlowState.AUTH_REQUESTED, " state: ",
					openIdConnectSessionImpl.getOpenIdConnectFlowState()));
		}

		AuthenticationSuccessResponse authenticationSuccessResponse =
			_getAuthenticationSuccessResponse(httpServletRequest);

		_validateState(
			openIdConnectSessionImpl.getState(),
			authenticationSuccessResponse.getState());

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			openIdConnectProvider =
				_openIdConnectProviderRegistry.findOpenIdConnectProvider(
					_portal.getCompanyId(httpServletRequest),
					openIdConnectSessionImpl.getOpenIdProviderName());

		OIDCProviderMetadata oidcProviderMetadata =
			openIdConnectProvider.getOIDCProviderMetadata();

		OIDCClientInformation oidcClientInformation = _getOIDCClientInformation(
			openIdConnectProvider);

		URI redirectURI = _getLoginRedirectURI(httpServletRequest);

		Tokens tokens = _requestIdToken(
			authenticationSuccessResponse, oidcClientInformation,
			oidcProviderMetadata, redirectURI,
			openIdConnectSessionImpl.getNonce(),
			openIdConnectProvider.geTokenConnectionTimeout());

		_updateSessionTokens(
			openIdConnectSessionImpl, tokens, System.currentTimeMillis(),
			false);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		_processUserInfo(
			_portal.getCompanyId(httpServletRequest), openIdConnectSessionImpl,
			oidcProviderMetadata, serviceContext.getPathMain(),
			serviceContext.getPortalURL());

		openIdConnectSessionImpl.setOpenIdConnectFlowState(
			OpenIdConnectFlowState.AUTH_COMPLETE);

		_openIdConnectSessionProviderImpl.setOpenIdConnectSession(
			httpSession, openIdConnectSessionImpl);
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
					_portal.getCompanyId(httpServletRequest),
					openIdConnectProviderName);

		HttpSession httpSession = httpServletRequest.getSession();

		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			_getOpenIdConnectSessionImpl(
				httpSession, openIdConnectProviderName);

		if (openIdConnectSessionImpl == null) {
			openIdConnectSessionImpl = new OpenIdConnectSessionImpl(
				openIdConnectProviderName, new Nonce(), new State());
		}

		URI authenticationRequestURI = _getAuthenticationRequestURI(
			_getLoginRedirectURI(httpServletRequest), openIdConnectProvider,
			openIdConnectSessionImpl.getNonce(),
			openIdConnectSessionImpl.getState(),
			Scope.parse(openIdConnectProvider.getScopes()));

		try {
			httpServletResponse.sendRedirect(
				authenticationRequestURI.toString());

			openIdConnectSessionImpl.setOpenIdConnectFlowState(
				OpenIdConnectFlowState.AUTH_REQUESTED);

			_openIdConnectSessionProviderImpl.setOpenIdConnectSession(
				httpSession, openIdConnectSessionImpl);
		}
		catch (IOException ioException) {
			throw new SystemException(
				StringBundler.concat(
					"Unable to send user to OpenId Connect service ",
					authenticationRequestURI.toString(), ": ",
					ioException.getMessage()),
				ioException);
		}
	}

	private URI _getAuthenticationRequestURI(
			URI loginRedirectURI,
			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
				openIdConnectProvider,
			Nonce nonce, State state, Scope scope)
		throws OpenIdConnectServiceException.ProviderException {

		OIDCProviderMetadata oidcProviderMetadata =
			openIdConnectProvider.getOIDCProviderMetadata();

		OIDCClientInformation oidcClientInformation = _getOIDCClientInformation(
			openIdConnectProvider);

		ResponseType responseType = new ResponseType(ResponseType.Value.CODE);

		AuthenticationRequest authenticationRequest = new AuthenticationRequest(
			oidcProviderMetadata.getAuthorizationEndpointURI(), responseType,
			scope, oidcClientInformation.getID(), loginRedirectURI, state,
			nonce);

		return authenticationRequest.toURI();
	}

	private AuthenticationSuccessResponse _getAuthenticationSuccessResponse(
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
		catch (ParseException | URISyntaxException exception) {
			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat(
					"Unable to process response from ", requestURL.toString(),
					": ", exception.getMessage()),
				exception);
		}
	}

	private URI _getLoginRedirectURI(HttpServletRequest httpServletRequest) {
		try {
			StringBundler sb = new StringBundler(3);

			sb.append(_portal.getPortalURL(httpServletRequest));
			sb.append(_portal.getPathContext());
			sb.append(OpenIdConnectConstants.REDIRECT_URL_PATTERN);

			return new URI(sb.toString());
		}
		catch (URISyntaxException uriSyntaxException) {
			throw new SystemException(
				"Unable to generate OpenId Connect login redirect URI: " +
					uriSyntaxException.getMessage(),
				uriSyntaxException);
		}
	}

	private OIDCClientInformation _getOIDCClientInformation(
		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			openIdConnectProvider) {

		ClientID clientID = new ClientID(openIdConnectProvider.getClientId());

		Secret secret = new Secret(openIdConnectProvider.getClientSecret());

		return new OIDCClientInformation(
			clientID, new Date(), openIdConnectProvider.getOIDCClientMetadata(),
			secret);
	}

	private OpenIdConnectSessionImpl _getOpenIdConnectSessionImpl(
			HttpSession httpSession)
		throws OpenIdConnectServiceException.NoOpenIdConnectSessionException {

		OpenIdConnectSessionImpl openIdConnectSessionImpl =
			_getOpenIdConnectSessionImpl(httpSession, null);

		if (openIdConnectSessionImpl == null) {
			throw new OpenIdConnectServiceException.
				NoOpenIdConnectSessionException(
					"HTTP session does contain an OpenId Connect session");
		}

		return openIdConnectSessionImpl;
	}

	private OpenIdConnectSessionImpl _getOpenIdConnectSessionImpl(
		HttpSession httpSession, String expectedProviderName) {

		Object openIdConnectSessionObject =
			_openIdConnectSessionProviderImpl.getOpenIdConnectSession(
				httpSession);

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

	private boolean _hasValidAccessToken(
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

	private void _processUserInfo(
			long companyId, OpenIdConnectSessionImpl openIdConnectSessionImpl,
			OIDCProviderMetadata oidcProviderMetadata, String mainPath,
			String portalURL)
		throws PortalException {

		UserInfo userInfo = _requestUserInfo(
			openIdConnectSessionImpl.getAccessToken(), oidcProviderMetadata);

		long userId = _openIdConnectUserInfoProcessor.processUserInfo(
			userInfo, companyId, mainPath, portalURL);

		openIdConnectSessionImpl.setLoginUserId(userId);

		openIdConnectSessionImpl.setUserInfoJSONObject(userInfo.toJSONObject());
	}

	private boolean _refreshAuthToken(
			OpenIdConnectSessionImpl openIdConnectSessionImpl)
		throws OpenIdConnectServiceException {

		if (_hasValidAccessToken(openIdConnectSessionImpl)) {
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
					CompanyThreadLocal.getCompanyId(),
					openIdConnectProviderName);

		OIDCProviderMetadata oidcProviderMetadata =
			openIdConnectProvider.getOIDCProviderMetadata();

		Tokens tokens = _requestRefreshToken(
			refreshToken, _getOIDCClientInformation(openIdConnectProvider),
			oidcProviderMetadata,
			openIdConnectProvider.geTokenConnectionTimeout());

		_updateSessionTokens(
			openIdConnectSessionImpl, tokens, System.currentTimeMillis(), true);

		return true;
	}

	private Tokens _requestIdToken(
			AuthenticationSuccessResponse authenticationSuccessResponse,
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata, URI redirectURI,
			Nonce nonce, int tokenConnectionTimeout)
		throws OpenIdConnectServiceException.TokenException {

		AuthorizationGrant authorizationCodeGrant = new AuthorizationCodeGrant(
			authenticationSuccessResponse.getAuthorizationCode(), redirectURI);

		return _requestTokens(
			oidcClientInformation, oidcProviderMetadata, nonce,
			authorizationCodeGrant, tokenConnectionTimeout);
	}

	private Tokens _requestRefreshToken(
			RefreshToken refreshToken,
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata,
			int tokenConnectionTimeout)
		throws OpenIdConnectServiceException {

		AuthorizationGrant refreshTokenGrant = new RefreshTokenGrant(
			refreshToken);

		return _requestTokens(
			oidcClientInformation, oidcProviderMetadata, null,
			refreshTokenGrant, tokenConnectionTimeout);
	}

	private Tokens _requestTokens(
			OIDCClientInformation oidcClientInformation,
			OIDCProviderMetadata oidcProviderMetadata, Nonce nonce,
			AuthorizationGrant authorizationCodeGrant,
			int tokenConnectionTimeout)
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

			_validateToken(
				oidcClientInformation, nonce, oidcProviderMetadata,
				oidcTokenResponse, tokenConnectionTimeout);

			return oidcTokenResponse.getTokens();
		}
		catch (IOException ioException) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat(
					"Unable to get tokens from ", tokenEndpoint, ": ",
					ioException.getMessage()),
				ioException);
		}
		catch (ParseException parseException) {
			throw new OpenIdConnectServiceException.TokenException(
				StringBundler.concat(
					"Unable to parse tokens response from ", tokenEndpoint,
					": ", parseException.getMessage()),
				parseException);
		}
	}

	private UserInfo _requestUserInfo(
			AccessToken accessToken, OIDCProviderMetadata oidcProviderMetadata)
		throws OpenIdConnectServiceException.UserInfoException {

		UserInfoRequest userInfoRequest = new UserInfoRequest(
			oidcProviderMetadata.getUserInfoEndpointURI(),
			(BearerAccessToken)accessToken);

		HTTPRequest httpRequest = userInfoRequest.toHTTPRequest();

		httpRequest.setAccept(
			"text/html, image/gif, image/jpeg, */*; q=0.2, */*; q=0.2");

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

			UserInfo userInfo = userInfoSuccessResponse.getUserInfo();

			if (userInfo != null) {
				return userInfo;
			}

			JWT userInfoJWT = userInfoSuccessResponse.getUserInfoJWT();

			return new UserInfo(userInfoJWT.getJWTClaimsSet());
		}
		catch (IOException ioException) {
			throw new OpenIdConnectServiceException.UserInfoException(
				StringBundler.concat(
					"Unable to get user information from ",
					oidcProviderMetadata.getUserInfoEndpointURI(), ": ",
					ioException.getMessage()),
				ioException);
		}
		catch (java.text.ParseException | ParseException exception) {
			throw new OpenIdConnectServiceException.UserInfoException(
				StringBundler.concat(
					"Unable to parse user information response from ",
					oidcProviderMetadata.getUserInfoEndpointURI(), ": ",
					exception.getMessage()),
				exception);
		}
	}

	private void _updateSessionTokens(
		OpenIdConnectSessionImpl openIdConnectSessionImpl, Tokens tokens,
		long loginTime, boolean exchangeRefreshToken) {

		openIdConnectSessionImpl.setAccessToken(tokens.getAccessToken());

		if (!exchangeRefreshToken || (tokens.getRefreshToken() != null)) {
			openIdConnectSessionImpl.setRefreshToken(tokens.getRefreshToken());
		}

		openIdConnectSessionImpl.setLoginTime(loginTime);
	}

	private void _validateState(State requestedState, State state)
		throws OpenIdConnectServiceException {

		if (!state.equals(requestedState)) {
			throw new OpenIdConnectServiceException.AuthenticationException(
				StringBundler.concat(
					"Requested value \"", requestedState.getValue(),
					"\" and approved state \"", state.getValue(),
					"\" do not match"));
		}
	}

	private IDTokenClaimsSet _validateToken(
			OIDCClientInformation oidcClientInformation, Nonce nonce,
			OIDCProviderMetadata oidcProviderMetadata,
			OIDCTokenResponse oidcTokenResponse, int tokenConnectionTimeout)
		throws OpenIdConnectServiceException.TokenException {

		try {
			OIDCClientMetadata oidcClientMetadata =
				oidcClientInformation.getOIDCMetadata();

			URI jwkSetURI = oidcProviderMetadata.getJWKSetURI();

			IDTokenValidator idTokenValidator = new IDTokenValidator(
				oidcProviderMetadata.getIssuer(), oidcClientInformation.getID(),
				oidcClientMetadata.getIDTokenJWSAlg(), jwkSetURI.toURL(),
				new DefaultResourceRetriever(
					tokenConnectionTimeout, tokenConnectionTimeout));

			OIDCTokens oidcTokens = oidcTokenResponse.getOIDCTokens();

			return idTokenValidator.validate(oidcTokens.getIDToken(), nonce);
		}
		catch (BadJOSEException | JOSEException exception) {
			throw new OpenIdConnectServiceException.TokenException(
				"Unable to validate tokens: " + exception.getMessage(),
				exception);
		}
		catch (MalformedURLException malformedURLException) {
			throw new OpenIdConnectServiceException.TokenException(
				"Invalid JSON web key URL: " +
					malformedURLException.getMessage(),
				malformedURLException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectServiceHandlerImpl.class);

	@Reference
	private OpenIdConnectProviderRegistry
		<OIDCClientMetadata, OIDCProviderMetadata>
			_openIdConnectProviderRegistry;

	@Reference
	private OpenIdConnectSessionProviderImpl _openIdConnectSessionProviderImpl;

	@Reference
	private OpenIdConnectUserInfoProcessor _openIdConnectUserInfoProcessor;

	@Reference
	private Portal _portal;

}