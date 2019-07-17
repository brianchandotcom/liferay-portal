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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnect;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectFlowState;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;

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
	immediate = true,
	property = {
		"servlet-context-name=",
		"servlet-filter-name=OpenId Connect Session Validation Filter",
		"url-pattern=/*"
	},
	service = Filter.class
)
public class OpenIdConnectSessionValidationFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _openIdConnect.isEnabled(
			_portal.getCompanyId(httpServletRequest));
	}

	protected boolean checkEndSession(HttpSession httpSession)
		throws Exception {

		boolean endSession = false;

		OpenIdConnectSession openIdConnectSession =
			(OpenIdConnectSession)httpSession.getAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

		if (openIdConnectSession == null) {
			return endSession;
		}

		OpenIdConnectFlowState openIdConnectFlowState =
			openIdConnectSession.getOpenIdConnectFlowState();

		if (!OpenIdConnectFlowState.AUTH_COMPLETE.equals(
				openIdConnectFlowState) &&
			!OpenIdConnectFlowState.PORTAL_AUTH_COMPLETE.equals(
				openIdConnectFlowState)) {

			return endSession;
		}

		try {
			if (!_openIdConnectServiceHandler.hasValidOpenIdConnectSession(
					httpSession)) {

				endSession = true;
			}
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to validate OpenId Connect session: " + pe.getMessage(),
				pe);

			endSession = true;
		}

		return endSession;
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

		if ((httpSession != null) && checkEndSession(httpSession)) {
			httpSession.invalidate();

			httpServletResponse.sendRedirect(
				_portal.getHomeURL(httpServletRequest));

			return;
		}

		processFilter(
			OpenIdConnectSessionValidationFilter.class.getName(),
			httpServletRequest, httpServletResponse, filterChain);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectSessionValidationFilter.class);

	@Reference
	private OpenIdConnect _openIdConnect;

	@Reference
	private OpenIdConnectServiceHandler _openIdConnectServiceHandler;

	@Reference
	private Portal _portal;

}