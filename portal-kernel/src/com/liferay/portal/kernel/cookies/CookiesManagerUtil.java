/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cookies;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tamas Molnar
 */
public class CookiesManagerUtil {

	public static boolean addCookie(
		Cookie cookie, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _cookiesManager.addCookie(
			cookie, httpServletRequest, httpServletResponse);
	}

	public static boolean addCookie(
		Cookie cookie, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean secure) {

		return _cookiesManager.addCookie(
			cookie, httpServletRequest, httpServletResponse, secure);
	}

	public static boolean addCookie(
		int consentType, Cookie cookie, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _cookiesManager.addCookie(
			consentType, cookie, httpServletRequest, httpServletResponse);
	}

	public static boolean addCookie(
		int consentType, Cookie cookie, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean secure) {

		return _cookiesManager.addCookie(
			consentType, cookie, httpServletRequest, httpServletResponse,
			secure);
	}

	public static boolean addSupportCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _cookiesManager.addSupportCookie(
			httpServletRequest, httpServletResponse);
	}

	public static boolean deleteCookies(
		String domain, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String... cookieNames) {

		return _cookiesManager.deleteCookies(
			domain, httpServletRequest, httpServletResponse, cookieNames);
	}

	public static String getCookieValue(
		String cookieName, HttpServletRequest httpServletRequest) {

		return _cookiesManager.getCookieValue(cookieName, httpServletRequest);
	}

	public static String getCookieValue(
		String cookieName, HttpServletRequest httpServletRequest,
		boolean toUpperCase) {

		return _cookiesManager.getCookieValue(
			cookieName, httpServletRequest, toUpperCase);
	}

	public static String getDomain(HttpServletRequest httpServletRequest) {
		return _cookiesManager.getDomain(httpServletRequest);
	}

	public static String getDomain(String host) {
		return _cookiesManager.getDomain(host);
	}

	public static boolean hasConsentType(
		int consentType, HttpServletRequest httpServletRequest) {

		return _cookiesManager.hasConsentType(consentType, httpServletRequest);
	}

	public static boolean hasSessionId(HttpServletRequest httpServletRequest) {
		return _cookiesManager.hasSessionId(httpServletRequest);
	}

	public static boolean isEncodedCookie(String cookieName) {
		return _cookiesManager.isEncodedCookie(cookieName);
	}

	public static void validateSupportCookie(
			HttpServletRequest httpServletRequest)
		throws UnsupportedCookieException {

		_cookiesManager.validateSupportCookie(httpServletRequest);
	}

	private static volatile CookiesManager _cookiesManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			CookiesManager.class, CookiesManagerUtil.class, "_cookiesManager",
			false);

}