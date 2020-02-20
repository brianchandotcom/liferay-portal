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
		SharepointRepositoryConfiguration
			sharepointRepositoryOAuth2Configuration,
		SharepointRepositoryTokenBroker sharepointOAuth2AuthorizationServer) {

		_tokenStore = tokenStore;
		_sharepointRepositoryOAuth2Configuration =
			sharepointRepositoryOAuth2Configuration;
		_sharepointOAuth2AuthorizationServer =
			sharepointOAuth2AuthorizationServer;
	}

	@Override
	public void authorize(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, PortalException {

		_authorize(
			PortalUtil.getOriginalServletRequest(httpServletRequest),
			httpServletResponse);
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

		if (_hasAuthorizationGrant(
				PortalUtil.getHttpServletRequest(portletRequest))) {

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

			return true;
		}

		return false;
	}

	private void _authorize(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, PortalException {

		_validateRequest(httpServletRequest);

		if (_hasAuthorizationGrant(httpServletRequest)) {
			_requestAccessToken(httpServletRequest, httpServletResponse);
		}
		else {
			Token token = _tokenStore.get(
				_sharepointRepositoryOAuth2Configuration.name(),
				PortalUtil.getUserId(httpServletRequest));

			if (token == null) {
				_requestAuthorizationGrant(
					httpServletRequest, httpServletResponse);
			}
			else if (token.isExpired()) {
				if (Validator.isNotNull(token.getRefreshToken())) {
					_refreshAccessToken(token, httpServletRequest);
				}
				else {
					_requestAccessToken(
						httpServletRequest, httpServletResponse);
				}
			}
		}
	}

	private String _getGrantURL(
		HttpServletRequest httpServletRequest, String state) {

		String url =
			_sharepointRepositoryOAuth2Configuration.
				authorizationGrantEndpoint();

		url = HttpUtil.addParameter(
			url, "client_id",
			_sharepointRepositoryOAuth2Configuration.clientId());

		url = HttpUtil.addParameter(
			url, "redirect_uri", _getRedirectURI(httpServletRequest));
		url = HttpUtil.addParameter(url, "response_type", "code");
		url = HttpUtil.addParameter(
			url, "scope", _sharepointRepositoryOAuth2Configuration.scope());
		url = HttpUtil.addParameter(url, "state", state);

		return url;
	}

	private String _getRedirectURI(HttpServletRequest httpServletRequest) {
		return PortalUtil.getAbsoluteURL(
			httpServletRequest,
			PortalUtil.getPathMain() + "/document_library/sharepoint/oauth2");
	}

	private boolean _hasAuthorizationGrant(
		HttpServletRequest httpServletRequest) {

		String code = ParamUtil.getString(httpServletRequest, "code");

		if (Validator.isNull(code)) {
			return false;
		}

		return true;
	}

	private void _refreshAccessToken(
			Token token, HttpServletRequest httpServletRequest)
		throws IOException, PortalException {

		long userId = PortalUtil.getUserId(httpServletRequest);

		try {
			Token freshToken =
				_sharepointOAuth2AuthorizationServer.refreshAccessToken(token);

			_tokenStore.save(
				_sharepointRepositoryOAuth2Configuration.name(), userId,
				freshToken);
		}
		catch (AuthorizationException authorizationException) {
			_tokenStore.delete(
				_sharepointRepositoryOAuth2Configuration.name(), userId);

			throw authorizationException;
		}
	}

	private void _requestAccessToken(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, PortalException {

		SharepointRepositoryRequestState sharepointRepositoryRequestState =
			SharepointRepositoryRequestState.get(httpServletRequest);

		sharepointRepositoryRequestState.validate(
			ParamUtil.getString(httpServletRequest, "state"));

		long userId = PortalUtil.getUserId(httpServletRequest);

		String code = ParamUtil.getString(httpServletRequest, "code");

		Token accessToken =
			_sharepointOAuth2AuthorizationServer.requestAccessToken(
				code, _getRedirectURI(httpServletRequest));

		_tokenStore.save(
			_sharepointRepositoryOAuth2Configuration.name(), userId,
			accessToken);

		sharepointRepositoryRequestState.restore(
			httpServletRequest, httpServletResponse);
	}

	private void _requestAuthorizationGrant(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String state = StringUtil.randomString(5);

		SharepointRepositoryRequestState.save(httpServletRequest, state);

		httpServletResponse.sendRedirect(
			_getGrantURL(httpServletRequest, state));
	}

	private void _validateRequest(HttpServletRequest httpServletRequest)
		throws AuthorizationException {

		String error = ParamUtil.getString(httpServletRequest, "error");

		if (Validator.isNotNull(error)) {
			String description = ParamUtil.getString(
				httpServletRequest, "error_description");

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