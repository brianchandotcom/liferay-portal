/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Amos Fong
 */
public class AuthTokenUtil {

	public static void check(HttpServletRequest request)
		throws PortalException {

		getAuthToken().check(request);
	}

	public static AuthToken getAuthToken() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenUtil.class);

		return _authToken;
	}

	public static String getToken(HttpServletRequest request) {
		return getAuthToken().getToken(request);
	}

	public static String getToken(
		HttpServletRequest request, long plid, String portletId) {

		return getAuthToken().getToken(request, plid, portletId);
	}

	public void setAuthToken(AuthToken authToken) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_authToken = authToken;
	}

	private static AuthToken _authToken;

}