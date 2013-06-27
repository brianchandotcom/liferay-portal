/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Set;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class AuthTokenWhitelistUtil {

	public static AuthTokenWhitelist getAuthTokenWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		return _authTokenWhitelist;
	}

	public static Set<String> getWhitelist() {
		return getAuthTokenWhitelist().getWhitelist();
	}

	public static Set<String> getWhitelistActions() {
		return getAuthTokenWhitelist().getWhitelistActions();
	}

	public static Set<String> resetWhitelist() {
		return getAuthTokenWhitelist().resetWhitelist();
	}

	public static Set<String> resetWhitelistActions() {
		return getAuthTokenWhitelist().resetWhitelistActions();
	}

	public void setAuthTokenWhitelist(AuthTokenWhitelist authTokenWhitelist) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_authTokenWhitelist = authTokenWhitelist;
	}

	private static AuthTokenWhitelist _authTokenWhitelist;

}