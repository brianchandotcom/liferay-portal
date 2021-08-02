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
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.internal.auto.login.OpenIdConnectAutoLogin;
import com.liferay.portal.security.sso.openid.connect.internal.provider.OpenIdConnectSessionProviderImpl;
import com.liferay.portal.security.sso.openid.connect.internal.session.manager.OfflineOpenIdConnectSessionManager;
import com.liferay.portal.security.sso.openid.connect.internal.util.OpenIdConnectTokenRequestUtil;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.UserInfoErrorResponse;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.minidev.json.JSONObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 * @author Arthur Chan
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

		OpenIdConnectAuthenticationSession openIdConnectAuthenticationSession =
			(OpenIdConnectAuthenticationSession)httpSession.getAttribute(
				OpenIdConnectAuthenticationSession.SESSION);

		httpSession.removeAttribute(OpenIdConnectAuthenticationSession.SESSION);

		if (openIdConnectAuthenticationSession == null) {
			throw new OpenIdConnectServiceException.AuthenticationException(
				"OpenId Connect authentication was not requested or removed");
		}

		AuthenticationSuccessResponse authenticationSuccessResponse =
			_getAuthenticationSuccessResponse(httpServletRequest);

		_validateState(
			openIdConnectAuthenticationSession.getState(),
			authenticationSuccessResponse.getState());

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			openIdConnectProvider =
				_openIdConnectProviderRegistry.findOpenIdConnectProvider(
					_portal.getCompanyId(httpServletRequest),
					openIdConnectAuthenticationSession.getProviderName());

		OIDCTokens oidcTokens = OpenIdConnectTokenRequestUtil.request(
			authenticationSuccessResponse,
			openIdConnectAuthenticationSession.getNonce(),
			openIdConnectProvider, _getLoginRedirectURI(httpServletRequest));

		UserInfo userInfo = _requestUserInfo(
			oidcTokens.getAccessToken(),
			openIdConnectProvider.getOIDCProviderMetadata());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		long userId = _openIdConnectUserInfoProcessor.processUserInfo(
			userInfo, _portal.getCompanyId(httpServletRequest),
			serviceContext.getPathMain(), serviceContext.getPortalURL());

		httpSession.setAttribute(OpenIdConnectAutoLogin.USER_ID, userId);

		httpSession.setAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION_ID,
			_offlineOpenIdConnectSessionManager.startOpenIdConnectSession(
				oidcTokens,
				openIdConnectAuthenticationSession.getProviderName()));
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

		Long openIdConnectSessionId = (Long)httpSession.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION_ID);

		if (openIdConnectSessionId != null) {
			_offlineOpenIdConnectSessionManager.endOpenIdConnectSession(
				openIdConnectSessionId);

			httpSession.removeAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION_ID);
		}

		Nonce nonce = new Nonce();

		State state = new State();

		URI authenticationRequestURI = _getAuthenticationRequestURI(
			_getLoginRedirectURI(httpServletRequest), nonce,
			openIdConnectProvider,
			Scope.parse(openIdConnectProvider.getScopes()), state);

		try {
			httpServletResponse.sendRedirect(
				authenticationRequestURI.toString());

			httpSession.setAttribute(
				OpenIdConnectAuthenticationSession.SESSION,
				new OpenIdConnectAuthenticationSession(
					openIdConnectProviderName, nonce, state));
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
			URI loginRedirectURI, Nonce nonce,
			OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
				openIdConnectProvider,
			Scope scope, State state)
		throws OpenIdConnectServiceException.ProviderException {

		OIDCProviderMetadata oidcProviderMetadata =
			openIdConnectProvider.getOIDCProviderMetadata();

		ResponseType responseType = new ResponseType(ResponseType.Value.CODE);

		AuthenticationRequest authenticationRequest = new AuthenticationRequest(
			oidcProviderMetadata.getAuthorizationEndpointURI(), responseType,
			scope, new ClientID(openIdConnectProvider.getClientId()),
			loginRedirectURI, state, nonce);

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

		OIDCTokens oidcTokens = OpenIdConnectTokenRequestUtil.request(
			_openIdConnectProviderRegistry.findOpenIdConnectProvider(
				CompanyThreadLocal.getCompanyId(),
				openIdConnectSessionImpl.getOpenIdProviderName()),
			refreshToken);

		OfflineOpenIdConnectSessionManager.extendOpenIdConnectSession(
			System.currentTimeMillis(), oidcTokens, openIdConnectSessionImpl);

		return true;
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

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectServiceHandlerImpl.class);

	@Reference
	private OfflineOpenIdConnectSessionManager
		_offlineOpenIdConnectSessionManager;

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