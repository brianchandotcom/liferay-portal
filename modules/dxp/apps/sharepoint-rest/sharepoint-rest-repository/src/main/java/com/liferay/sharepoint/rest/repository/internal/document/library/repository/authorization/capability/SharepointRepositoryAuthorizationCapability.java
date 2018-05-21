/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.capability;

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
import com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.SharepointRepositoryRequestState;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.SharepointRepositoryTokenBroker;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class SharepointRepositoryAuthorizationCapability
	implements AuthorizationCapability {

	public SharepointRepositoryAuthorizationCapability(
		TokenStore tokenStore,
		SharepointRepositoryConfiguration sharepointRepositoryConfiguration,
		SharepointRepositoryTokenBroker sharepointOAuth2AuthorizationServer) {

		_tokenStore = tokenStore;
		_sharepointRepositoryOAuth2Configuration =
			sharepointRepositoryConfiguration;
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

		if (_hasAuthorizationGrant(request)) {
			return true;
		}

		Token token = _tokenStore.get(
			_sharepointRepositoryOAuth2Configuration.name(),
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

		if (_hasAuthorizationGrant(request)) {
			_requestAccessToken(request, response);
		}
		else {
			Token token = _tokenStore.get(
				_sharepointRepositoryOAuth2Configuration.name(),
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
			_sharepointRepositoryOAuth2Configuration.
				authorizationGrantEndpoint();

		url = HttpUtil.addParameter(
			url, "client_id",
			_sharepointRepositoryOAuth2Configuration.clientId());

		url = HttpUtil.addParameter(
			url, "redirect_uri", _getRedirectURI(request));
		url = HttpUtil.addParameter(url, "response_type", "code");
		url = HttpUtil.addParameter(
			url, "scope", _sharepointRepositoryOAuth2Configuration.scope());
		url = HttpUtil.addParameter(url, "state", state);

		return url;
	}

	private String _getRedirectURI(HttpServletRequest request) {
		return PortalUtil.getAbsoluteURL(
			request,
			PortalUtil.getPathMain() + "/document_library/sharepoint/oauth2");
	}

	private boolean _hasAuthorizationGrant(HttpServletRequest request) {
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
				_sharepointRepositoryOAuth2Configuration.name(), userId,
				freshToken);
		}
		catch (AuthorizationException ae) {
			_tokenStore.delete(
				_sharepointRepositoryOAuth2Configuration.name(), userId);

			throw ae;
		}
	}

	private void _requestAccessToken(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, PortalException {

		SharepointRepositoryRequestState sharepointRepositoryRequestState =
			SharepointRepositoryRequestState.get(request);

		sharepointRepositoryRequestState.validate(
			ParamUtil.getString(request, "state"));

		long userId = PortalUtil.getUserId(request);

		String code = ParamUtil.getString(request, "code");

		Token accessToken =
			_sharepointOAuth2AuthorizationServer.requestAccessToken(
				code, _getRedirectURI(request));

		_tokenStore.save(
			_sharepointRepositoryOAuth2Configuration.name(), userId,
			accessToken);

		sharepointRepositoryRequestState.restore(request, response);
	}

	private void _requestAuthorizationGrant(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String state = StringUtil.randomString(5);

		SharepointRepositoryRequestState.save(request, state);

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

	private final SharepointRepositoryTokenBroker
		_sharepointOAuth2AuthorizationServer;
	private final SharepointRepositoryConfiguration
		_sharepointRepositoryOAuth2Configuration;
	private final TokenStore _tokenStore;

}