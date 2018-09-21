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

package com.liferay.portal.servlet.filters.password.modified;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Marta Medio
 */
public class PasswordModifiedFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (_isPasswordModified(request)) {
			AuthenticatedSessionManagerUtil.logout(request, response);

			String redirect = PortalUtil.getCurrentCompleteURL(request);

			if (!StringUtil.equals(request.getMethod(), HttpMethods.GET)) {
				redirect = PortalUtil.getPortalURL(request);
			}

			response.sendRedirect(redirect);
		}
		else {
			filterChain.doFilter(request, response);
		}
	}

	private boolean _isPasswordModified(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return false;
		}

		try {
			User user = PortalUtil.getUser(request);

			if ((user == null) || user.isDefaultUser()) {
				return false;
			}

			Date userModifiedPasswordDate = user.getPasswordModifiedDate();

			if (userModifiedPasswordDate == null) {
				return false;
			}

			boolean sessionOlderThanPassword = false;

			if (session.getCreationTime() <
					userModifiedPasswordDate.getTime()) {

				sessionOlderThanPassword = true;
			}

			return sessionOlderThanPassword;
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PasswordModifiedFilter.class);

}