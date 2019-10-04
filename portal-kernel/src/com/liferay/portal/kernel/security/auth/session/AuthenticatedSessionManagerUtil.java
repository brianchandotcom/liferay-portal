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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Tomas Polesovsky
 */
public class AuthenticatedSessionManagerUtil {

	public static AuthenticatedSessionManager getAuthenticatedSessionManager() {
		return _authenticatedSessionManagerUtil._serviceTracker.getService();
	}

	public static long getAuthenticatedUserId(
			HttpServletRequest httpServletRequest, String login,
			String password, String authType)
		throws PortalException {

		return getAuthenticatedSessionManager().getAuthenticatedUserId(
			httpServletRequest, login, password, authType);
	}

	public static void login(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String login,
			String password, boolean rememberMe, String authType)
		throws Exception {

		getAuthenticatedSessionManager().login(
			httpServletRequest, httpServletResponse, login, password,
			rememberMe, authType);
	}

	public static void logout(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		getAuthenticatedSessionManager().logout(
			httpServletRequest, httpServletResponse);
	}

	public static HttpSession renewSession(
			HttpServletRequest httpServletRequest, HttpSession session)
		throws Exception {

		return getAuthenticatedSessionManager().renewSession(
			httpServletRequest, session);
	}

	public static void signOutSimultaneousLogins(long userId) throws Exception {
		getAuthenticatedSessionManager().signOutSimultaneousLogins(userId);
	}

	private AuthenticatedSessionManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			AuthenticatedSessionManager.class);

		_serviceTracker.open();
	}

	private static final AuthenticatedSessionManagerUtil
		_authenticatedSessionManagerUtil =
			new AuthenticatedSessionManagerUtil();

	private final ServiceTracker<?, AuthenticatedSessionManager>
		_serviceTracker;

}