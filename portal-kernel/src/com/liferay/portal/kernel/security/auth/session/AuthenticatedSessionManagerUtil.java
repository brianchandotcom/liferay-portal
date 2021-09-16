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

package com.liferay.portal.kernel.security.auth.session;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Tomas Polesovsky
 */
public class AuthenticatedSessionManagerUtil {

	public static AuthenticatedSessionManager getAuthenticatedSessionManager() {
		return _authenticatedSessionManager;
	}

	public static long getAuthenticatedUserId(
			HttpServletRequest httpServletRequest, String login,
			String password, String authType)
		throws PortalException {

		return _authenticatedSessionManager.getAuthenticatedUserId(
			httpServletRequest, login, password, authType);
	}

	public static void login(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String login,
			String password, boolean rememberMe, String authType)
		throws Exception {

		_authenticatedSessionManager.login(
			httpServletRequest, httpServletResponse, login, password,
			rememberMe, authType);
	}

	public static void logout(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_authenticatedSessionManager.logout(
			httpServletRequest, httpServletResponse);
	}

	public static HttpSession renewSession(
			HttpServletRequest httpServletRequest, HttpSession httpSession)
		throws Exception {

		return _authenticatedSessionManager.renewSession(
			httpServletRequest, httpSession);
	}

	public static void signOutSimultaneousLogins(long userId) throws Exception {
		_authenticatedSessionManager.signOutSimultaneousLogins(userId);
	}

	private AuthenticatedSessionManagerUtil() {
	}

	private static volatile AuthenticatedSessionManager
		_authenticatedSessionManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				AuthenticatedSessionManager.class,
				AuthenticatedSessionManagerUtil.class,
				"_authenticatedSessionManager", false, true);

}