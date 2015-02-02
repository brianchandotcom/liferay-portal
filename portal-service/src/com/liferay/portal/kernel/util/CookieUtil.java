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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.servlet.http.Cookie;

/**
 * @author Shuyang Zhou
 * @author Yuxing Wu
 */
public class CookieUtil {

	public static Cookie deserialize(byte[] bytes) {
		return getCookie().deserialize(bytes);
	}

	public static boolean equals(Cookie cookie1, Cookie cookie2) {
		return getCookie().equals(cookie1, cookie2);
	}

	public static com.liferay.portal.kernel.util.Cookie getCookie() {
		PortalRuntimePermission.checkGetBeanProperty(CookieUtil.class);

		return _cookie;
	}

	public static byte[] serialize(Cookie cookie) {
		return getCookie().serialize(cookie);
	}

	public static String toString(Cookie cookie) {
		return getCookie().toString(cookie);
	}

	public void setCookie(com.liferay.portal.kernel.util.Cookie cookie) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_cookie = cookie;
	}

	private static com.liferay.portal.kernel.util.Cookie _cookie;

}