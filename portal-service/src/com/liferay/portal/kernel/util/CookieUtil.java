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

package com.liferay.portal.kernel.util;

import javax.servlet.http.Cookie;

/**
 * @author Shuyang Zhou
 */
public class CookieUtil {

	public static String toString(Cookie cookie) {
		StringBundler sb = new StringBundler(17);

		sb.append("{comment=");
		sb.append(cookie.getComment());
		sb.append(", domain=");
		sb.append(cookie.getDomain());
		sb.append(", maxAge=");
		sb.append(cookie.getMaxAge());
		sb.append(", name=");
		sb.append(cookie.getName());
		sb.append(", path=");
		sb.append(cookie.getPath());
		sb.append(", secure=");
		sb.append(cookie.getSecure());
		sb.append(", value=");
		sb.append(cookie.getValue());
		sb.append(", version=");
		sb.append(cookie.getVersion());
		sb.append("}");

		return sb.toString();
	}

}