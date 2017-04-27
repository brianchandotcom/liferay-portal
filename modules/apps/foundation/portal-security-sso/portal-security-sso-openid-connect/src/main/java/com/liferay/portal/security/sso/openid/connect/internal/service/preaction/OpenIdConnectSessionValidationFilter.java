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

package com.liferay.portal.security.sso.openid.connect.internal.service.preaction;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.TryFinallyFilter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
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
		"servlet-filter-name=Open Id Connect Session Validation Filter",
		"url-pattern=/*"
	},
	service = Filter.class
)
public class OpenIdConnectSessionValidationFilter
	extends BasePortalFilter implements TryFinallyFilter {

	@Override
	public void doFilterFinally(
			HttpServletRequest request, HttpServletResponse response,
			Object object)
		throws Exception {

		HttpSession httpSession = request.getSession();

		boolean endSession = false;

		OpenIdConnectSession openIdConnectSession =
			(OpenIdConnectSession)httpSession.getAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

		if (Validator.isNotNull(openIdConnectSession) &&
			Validator.isNotNull(openIdConnectSession.getAccessToken())) {

			try {
				if (!_openIdConnectServiceHandler.hasValidOpenIdConnectSession(
						httpSession)) {

					endSession = true;
				}
			}
			catch (PortalException pe) {
				_log.error("Unable to validate OpenIdSession: ", pe);

				endSession = true;
			}
		}

		if (endSession) {
			httpSession.invalidate();
		}
	}

	@Override
	public Object doFilterTry(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectSessionValidationFilter.class);

	@Reference
	private OpenIdConnectServiceHandler _openIdConnectServiceHandler;

}