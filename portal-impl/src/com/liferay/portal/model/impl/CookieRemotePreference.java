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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.model.UserRemotePreference;

import javax.servlet.http.Cookie;

/**
 * @author Carlos Sierra Andrés
 */
public class CookieRemotePreference implements UserRemotePreference {

	public CookieRemotePreference(Cookie cookie) {
		_cookie = cookie;

		String cookieName = cookie.getName();

		_name = cookieName.substring(
			CookieKeys.REMOTE_PREFERENCES_PREFIX.length());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CookieRemotePreference)) {
			return false;
		}

		CookieRemotePreference remotePreference = (CookieRemotePreference)obj;

		return _cookie.equals(remotePreference._cookie);
	}

	public Cookie getCookie() {
		return (Cookie)_cookie.clone();
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getValue() {
		return _cookie.getValue();
	}

	@Override
	public int hashCode() {
		return _cookie.hashCode();
	}

	private final Cookie _cookie;
	private final String _name;

}