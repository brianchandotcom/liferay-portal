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

package com.liferay.portal.security.sso.openid.connect.internal.service.filter;

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnect;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectAuthenticationHandler;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.internal.exception.StrangersNotAllowedException;
import com.liferay.portal.security.sso.openid.connect.internal.session.manager.OfflineOpenIdConnectSessionManager;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration",
	immediate = true,
	property = {
		"before-filter=Auto Login Filter", "servlet-context-name=",
		"servlet-filter-name=SSO OpenId Connect Authentication Filter",
		"url-pattern=" + OpenIdConnectConstants.REDIRECT_URL_PATTERN
	},
	service = {Filter.class, OpenIdConnectAuthenticationFilter.class}
)
public class OpenIdConnectAuthenticationFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _openIdConnect.isEnabled(
			_portal.getCompanyId(httpServletRequest));
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		HttpSession httpSession = httpServletRequest.getSession(false);

		if (httpSession == null) {
			return;
		}

		if (_offlineOpenIdConnectSessionManager.isOpenIdConnectSession(
				httpSession)) {

			if (_log.isDebugEnabled()) {
				_log.debug("User has already been logged in");
			}

			return;
		}

		try {
			_openIdConnectAuthenticationHandler.processAuthenticationResponse(
				httpServletRequest, httpServletResponse);
		}
		catch (StrangersNotAllowedException |
			   UserEmailAddressException.MustNotUseCompanyMx exception) {

			Class<?> clazz = exception.getClass();

			httpSession.removeAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

			sendError(
				clazz.getSimpleName(), httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to process OpenID Connect authentication response: " +
					exception.getMessage(),
				exception);

			httpSession.removeAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

			_portal.sendError(
				exception, httpServletRequest, httpServletResponse);
		}

		String actionURL = (String)httpSession.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_ACTION_URL);

		if (actionURL != null) {
			httpServletResponse.sendRedirect(actionURL);
		}

		processFilter(
			OpenIdConnectAuthenticationFilter.class.getName(),
			httpServletRequest, httpServletResponse, filterChain);
	}

	protected void sendError(
			String error, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		HttpSession session = httpServletRequest.getSession(false);

		if (session == null) {
			return;
		}

		String actionURL = (String)session.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_ACTION_URL);

		if (actionURL == null) {
			return;
		}

		actionURL = _http.addParameter(actionURL, "error", error);

		httpServletResponse.sendRedirect(actionURL);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectAuthenticationFilter.class);

	@Reference
	private Http _http;

	@Reference
	private OfflineOpenIdConnectSessionManager
		_offlineOpenIdConnectSessionManager;

	@Reference
	private OpenIdConnect _openIdConnect;

	@Reference
	private OpenIdConnectAuthenticationHandler
		_openIdConnectAuthenticationHandler;

	@Reference
	private Portal _portal;

}