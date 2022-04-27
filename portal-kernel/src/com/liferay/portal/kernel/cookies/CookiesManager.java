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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Tamas Molnar
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface CookiesManager {

	public boolean addCookie(
		int consentType, Cookie cookie, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	public boolean addCookie(
		int consentType, Cookie cookie, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean secure);

	public boolean addSupportCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	public boolean deleteCookies(
		String domain, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String... cookieNames);

	public String getCookieValue(
		String cookieName, HttpServletRequest httpServletRequest);

	public String getCookieValue(
		String cookieName, HttpServletRequest httpServletRequest,
		boolean toUpperCase);

	public String getDomain(HttpServletRequest httpServletRequest);

	public String getDomain(String host);

	public String[] getOptionalCookieNames();

	public String[] getRequiredCookieNames();

	public boolean hasConsentType(
		int consentType, HttpServletRequest httpServletRequest);

	public boolean hasSessionId(HttpServletRequest httpServletRequest);

	public boolean isEncodedCookie(String cookieName);

	public void validateSupportCookie(HttpServletRequest httpServletRequest)
		throws UnsupportedCookieException;

}