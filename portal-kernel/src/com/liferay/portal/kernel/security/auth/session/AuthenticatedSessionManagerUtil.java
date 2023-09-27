/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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