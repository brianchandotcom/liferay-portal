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

package com.liferay.sharepoint.repository.internal.oauth2.capability;

import com.liferay.document.library.repository.authorization.capability.AuthorizationCapability;
import com.liferay.document.library.repository.authorization.capability.AuthorizationException;
import com.liferay.document.library.repository.authorization.oauth2.OAuth2AuthorizationException;
import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.document.library.repository.authorization.oauth2.TokenStore;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharepoint.repository.internal.oauth2.RequestState;
import com.liferay.sharepoint.repository.internal.oauth2.SharepointOAuth2AuthorizationServer;
import com.liferay.sharepoint.repository.internal.oauth2.configuration.SharepointOAuth2Configuration;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class SharepointOAuth2AuthorizationCapability
	implements AuthorizationCapability {

	public SharepointOAuth2AuthorizationCapability(
		TokenStore tokenStore,
		SharepointOAuth2Configuration sharepointOAuth2Configuration,
		SharepointOAuth2AuthorizationServer
			sharepointOAuth2AuthorizationServer) {

		_tokenStore = tokenStore;
		_sharepointOAuth2Configuration = sharepointOAuth2Configuration;
		_sharepointOAuth2AuthorizationServer =
			sharepointOAuth2AuthorizationServer;
	}

	@Override
	public void authorize(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, PortalException {

		_authorize(PortalUtil.getOriginalServletRequest(request), response);
	}

	@Override
	public void authorize(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, PortalException {

		authorize(
			PortalUtil.getHttpServletRequest(portletRequest),
			PortalUtil.getHttpServletResponse(portletResponse));
	}

	@Override
	public boolean hasCustomRedirectFlow(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, PortalException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		if (_receivedAuthorizationGrant(request)) {
			return true;
		}

		Token token = _tokenStore.get(
			_sharepointOAuth2Configuration.name(),
			PortalUtil.getUserId(
				PortalUtil.getHttpServletRequest(portletRequest)));

		if (token == null) {
			return true;
		}
		else if (token.isExpired()) {
			if (Validator.isNotNull(token.getRefreshToken())) {
				return false;
			}
			else {
				return true;
			}
		}

		return false;
	}

	private void _authorize(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, PortalException {

		_validateRequest(request);

		if (_receivedAuthorizationGrant(request)) {
			_requestAccessToken(request, response);
		}
		else {
			Token token = _tokenStore.get(
				_sharepointOAuth2Configuration.name(),
				PortalUtil.getUserId(request));

			if (token == null) {
				_requestAuthorizationGrant(request, response);
			}
			else if (token.isExpired()) {
				if (Validator.isNotNull(token.getRefreshToken())) {
					_refreshAccessToken(token, request);
				}
				else {
					_requestAccessToken(request, response);
				}
			}
		}
	}

	private String _getGrantURL(HttpServletRequest request, String state) {
		String url =
			_sharepointOAuth2Configuration.authorizationGrantEndpoint();

		url = HttpUtil.addParameter(
			url, "client_id", _sharepointOAuth2Configuration.clientId());

		url = HttpUtil.addParameter(
			url, "redirect_uri", _getRedirectURI(request));
		url = HttpUtil.addParameter(url, "response_type", "code");
		url = HttpUtil.addParameter(
			url, "scope", _sharepointOAuth2Configuration.scope());
		url = HttpUtil.addParameter(url, "state", state);

		return url;
	}

	private String _getRedirectURI(HttpServletRequest request) {
		return PortalUtil.getAbsoluteURL(
			request,
			PortalUtil.getPathMain() + "/document_library/sharepoint/oauth2");
	}

	private boolean _receivedAuthorizationGrant(HttpServletRequest request) {
		String code = ParamUtil.getString(request, "code");

		if (Validator.isNull(code)) {
			return false;
		}

		return true;
	}

	private void _refreshAccessToken(Token token, HttpServletRequest request)
		throws IOException, PortalException {

		long userId = PortalUtil.getUserId(request);

		try {
			Token freshToken =
				_sharepointOAuth2AuthorizationServer.refreshAccessToken(token);

			_tokenStore.save(
				_sharepointOAuth2Configuration.name(), userId, freshToken);
		}
		catch (AuthorizationException ae) {
			_tokenStore.delete(_sharepointOAuth2Configuration.name(), userId);

			throw ae;
		}
	}

	private void _requestAccessToken(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, PortalException {

		RequestState requestState = RequestState.get(request);

		requestState.validate(ParamUtil.getString(request, "state"));

		String code = ParamUtil.getString(request, "code");

		Token accessToken =
			_sharepointOAuth2AuthorizationServer.requestAccessToken(
				code, _getRedirectURI(request));

		long userId = PortalUtil.getUserId(request);

		_tokenStore.save(
			_sharepointOAuth2Configuration.name(), userId, accessToken);

		requestState.restore(request, response);
	}

	private void _requestAuthorizationGrant(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String state = StringUtil.randomString(5);

		RequestState.save(request, state);

		response.sendRedirect(_getGrantURL(request, state));
	}

	private void _validateRequest(HttpServletRequest request)
		throws AuthorizationException {

		String error = ParamUtil.getString(request, "error");

		if (Validator.isNotNull(error)) {
			String description = ParamUtil.getString(
				request, "error_description");

			if (Validator.isNull(description)) {
				description = error;
			}

			throw OAuth2AuthorizationException.getErrorException(
				error, description);
		}
	}

	private final SharepointOAuth2AuthorizationServer
		_sharepointOAuth2AuthorizationServer;
	private final SharepointOAuth2Configuration _sharepointOAuth2Configuration;
	private final TokenStore _tokenStore;

}