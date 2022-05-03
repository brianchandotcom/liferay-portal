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

package com.liferay.portal.kernel.cookies;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tamas Molnar
 */
public class CookiesManagerUtil {

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

	public static List<ConsentCookieType> getOptionalConsentCookieTypes(
			long groupId)
		throws Exception {

		return _cookiesManager.getOptionalConsentCookieTypes(groupId);
	}

	public static List<ConsentCookieType> getRequiredConsentCookieTypes(
			long groupId)
		throws Exception {

		return _cookiesManager.getRequiredConsentCookieTypes(groupId);
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