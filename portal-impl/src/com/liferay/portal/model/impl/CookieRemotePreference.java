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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.RemotePreference;

import javax.servlet.http.Cookie;

/**
 * @author Carlos Sierra Andrés
 */
public class CookieRemotePreference implements RemotePreference {

	public CookieRemotePreference(Cookie cookie) {
		_cookie = cookie;

		String cookieName = cookie.getName();

		_name = cookieName.substring(
			CookieKeys.REMOTE_PREFERENCE_PREFIX.length());
	}

	public Cookie getCookie() {
		return _cookie;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getValue() {
		return _cookie.getValue();
	}

	private final Cookie _cookie;
	private final String _name;

}