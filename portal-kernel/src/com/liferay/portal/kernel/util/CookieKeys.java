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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.CookieNotSupportedException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Minhchau Dang
 */
public class CookieKeys {

	public static final String COMMERCE_CONTINUE_AS_GUEST =
		"COMMERCE_CONTINUE_AS_GUEST";

	public static final String COMPANY_ID = "COMPANY_ID";

	public static final String COOKIE_SUPPORT = "COOKIE_SUPPORT";

	public static final String GUEST_LANGUAGE_ID = "GUEST_LANGUAGE_ID";

	public static final String ID = "ID";

	public static final String JSESSIONID = "JSESSIONID";

	public static final String LOGIN = "LOGIN";

	public static final int MAX_AGE = (int)(Time.YEAR / 1000);

	public static final String PASSWORD = "PASSWORD";

	public static final String REMEMBER_ME = "REMEMBER_ME";

	public static final String REMOTE_PREFERENCE_PREFIX = "REMOTE_PREFERENCE_";

	public static final String USER_UUID = "USER_UUID";

	public static void addCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Cookie cookie) {

		addCookie(
			httpServletRequest, httpServletResponse, cookie,
			PortalUtil.isSecure(httpServletRequest));
	}

	public static void addCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Cookie cookie,
		boolean secure) {

		if (!_SESSION_ENABLE_PERSISTENT_COOKIES) {
			return;
		}

		// LEP-5175

		String name = cookie.getName();

		String originalValue = cookie.getValue();

		String encodedValue = originalValue;

		if (isEncodedCookie(name)) {
			encodedValue = UnicodeFormatter.bytesToHex(
				originalValue.getBytes());

			if (_log.isDebugEnabled()) {
				_log.debug("Add encoded cookie " + name);
				_log.debug("Original value " + originalValue);
				_log.debug("Hex encoded value " + encodedValue);
			}
		}

		cookie.setSecure(secure);
		cookie.setValue(encodedValue);
		cookie.setVersion(0);

		httpServletResponse.addCookie(cookie);

		if (httpServletRequest != null) {
			Map<String, Cookie> cookieMap = _getCookieMap(httpServletRequest);

			cookieMap.put(StringUtil.toUpperCase(name), cookie);
		}
	}

	public static void addSupportCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		Cookie cookieSupportCookie = new Cookie(COOKIE_SUPPORT, "true");

		cookieSupportCookie.setPath(StringPool.SLASH);
		cookieSupportCookie.setMaxAge(MAX_AGE);

		addCookie(
			null, httpServletResponse, cookieSupportCookie,
			httpServletRequest.isSecure());
	}

	public static void deleteCookies(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String domain,
		String... cookieNames) {

		if (!_SESSION_ENABLE_PERSISTENT_COOKIES) {
			return;
		}

		Map<String, Cookie> cookieMap = _getCookieMap(httpServletRequest);

		for (String cookieName : cookieNames) {
			Cookie cookie = cookieMap.remove(
				StringUtil.toUpperCase(cookieName));

			if (cookie != null) {
				if (domain != null) {
					cookie.setDomain(domain);
				}

				cookie.setMaxAge(0);
				cookie.setPath(StringPool.SLASH);
				cookie.setValue(StringPool.BLANK);

				httpServletResponse.addCookie(cookie);
			}
		}
	}

	public static String getCookie(
		HttpServletRequest httpServletRequest, String name) {

		return getCookie(httpServletRequest, name, true);
	}

	public static String getCookie(
		HttpServletRequest httpServletRequest, String name,
		boolean toUpperCase) {

		if (!_SESSION_ENABLE_PERSISTENT_COOKIES) {
			return null;
		}

		String value = _get(httpServletRequest, name, toUpperCase);

		if ((value == null) || !isEncodedCookie(name)) {
			return value;
		}

		try {
			String encodedValue = value;

			String originalValue = new String(
				UnicodeFormatter.hexToBytes(encodedValue));

			if (_log.isDebugEnabled()) {
				_log.debug("Get encoded cookie " + name);
				_log.debug("Hex encoded value " + encodedValue);
				_log.debug("Original value " + originalValue);
			}

			return originalValue;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return value;
		}
	}

	public static String getDomain(HttpServletRequest httpServletRequest) {

		// See LEP-4602 and	LEP-4618.

		if (Validator.isNotNull(_SESSION_COOKIE_DOMAIN)) {
			return _SESSION_COOKIE_DOMAIN;
		}

		if (_SESSION_COOKIE_USE_FULL_HOSTNAME) {
			return StringPool.BLANK;
		}

		return getDomain(httpServletRequest.getServerName());
	}

	public static String getDomain(String host) {

		// See LEP-4602 and LEP-4645.

		if (host == null) {
			return null;
		}

		// See LEP-5595.

		if (Validator.isIPAddress(host)) {
			return host;
		}

		int x = host.lastIndexOf(CharPool.PERIOD);

		if (x <= 0) {
			return null;
		}

		int y = host.lastIndexOf(CharPool.PERIOD, x - 1);

		if (y <= 0) {
			return StringPool.PERIOD + host;
		}

		int z = host.lastIndexOf(CharPool.PERIOD, y - 1);

		String domain = null;

		if (z <= 0) {
			domain = host.substring(y);
		}
		else {
			domain = host.substring(z);
		}

		return domain;
	}

	public static boolean hasSessionId(HttpServletRequest httpServletRequest) {
		String jsessionid = getCookie(httpServletRequest, JSESSIONID, false);

		if (jsessionid != null) {
			return true;
		}

		return false;
	}

	public static boolean isEncodedCookie(String name) {
		if (name.equals(ID) || name.equals(LOGIN) || name.equals(PASSWORD) ||
			name.equals(USER_UUID)) {

			return true;
		}

		return false;
	}

	public static void validateSupportCookie(
			HttpServletRequest httpServletRequest)
		throws CookieNotSupportedException {

		if (_SESSION_ENABLE_PERSISTENT_COOKIES &&
			_SESSION_TEST_COOKIE_SUPPORT) {

			String cookieSupport = getCookie(
				httpServletRequest, COOKIE_SUPPORT, false);

			if (Validator.isNull(cookieSupport)) {
				throw new CookieNotSupportedException();
			}
		}
	}

	private static String _get(
		HttpServletRequest httpServletRequest, String name,
		boolean toUpperCase) {

		Map<String, Cookie> cookieMap = _getCookieMap(httpServletRequest);

		if (toUpperCase) {
			name = StringUtil.toUpperCase(name);
		}

		Cookie cookie = cookieMap.get(name);

		if (cookie == null) {
			return null;
		}

		return cookie.getValue();
	}

	private static Map<String, Cookie> _getCookieMap(
		HttpServletRequest httpServletRequest) {

		Map<String, Cookie> cookieMap =
			(Map<String, Cookie>)httpServletRequest.getAttribute(
				CookieKeys.class.getName());

		if (cookieMap != null) {
			return cookieMap;
		}

		Cookie[] cookies = httpServletRequest.getCookies();

		if (cookies == null) {
			cookieMap = new HashMap<>();
		}
		else {
			cookieMap = new HashMap<>(cookies.length * 4 / 3);

			for (Cookie cookie : cookies) {
				String cookieName = GetterUtil.getString(cookie.getName());

				cookieName = StringUtil.toUpperCase(cookieName);

				cookieMap.put(cookieName, cookie);
			}
		}

		httpServletRequest.setAttribute(CookieKeys.class.getName(), cookieMap);

		return cookieMap;
	}

	private static final String _SESSION_COOKIE_DOMAIN = PropsUtil.get(
		PropsKeys.SESSION_COOKIE_DOMAIN);

	private static final boolean _SESSION_COOKIE_USE_FULL_HOSTNAME =
		GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.SESSION_COOKIE_USE_FULL_HOSTNAME,
				new Filter(ServerDetector.getServerId())));

	private static final boolean _SESSION_ENABLE_PERSISTENT_COOKIES =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SESSION_ENABLE_PERSISTENT_COOKIES));

	private static final boolean _SESSION_TEST_COOKIE_SUPPORT =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SESSION_TEST_COOKIE_SUPPORT));

	private static final Log _log = LogFactoryUtil.getLog(CookieKeys.class);

}