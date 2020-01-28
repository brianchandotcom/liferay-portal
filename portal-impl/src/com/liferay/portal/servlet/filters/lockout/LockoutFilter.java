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

package com.liferay.portal.servlet.filters.lockout;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Norbert Kocsis
 */
public class LockoutFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		if (_isLockout(httpServletRequest)) {
			AuthenticatedSessionManagerUtil.logout(
				httpServletRequest, httpServletResponse);

			if (StringUtil.equals(
					httpServletRequest.getMethod(), HttpMethods.GET)) {

				httpServletResponse.sendRedirect(
					PortalUtil.getCurrentCompleteURL(httpServletRequest));
			}
			else {
				httpServletResponse.sendRedirect(
					PortalUtil.getPortalURL(httpServletRequest));
			}
		}
		else {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
	}

	private boolean _isLockout(HttpServletRequest httpServletRequest) {
		try {
			User user = PortalUtil.getUser(httpServletRequest);

			if ((user != null) && user.isLockout()) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(LockoutFilter.class);

}