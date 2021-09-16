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

package com.liferay.portal.kernel.security.auth.http;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 */
public class HttpAuthManagerUtil {

	public static void generateChallenge(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		HttpAuthorizationHeader httpAuthorizationHeader) {

		_httpAuthManager.generateChallenge(
			httpServletRequest, httpServletResponse, httpAuthorizationHeader);
	}

	public static long getBasicUserId(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _httpAuthManager.getBasicUserId(httpServletRequest);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public static long getDigestUserId(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _httpAuthManager.getDigestUserId(httpServletRequest);
	}

	public static long getUserId(
			HttpServletRequest httpServletRequest,
			HttpAuthorizationHeader httpAuthorizationHeader)
		throws PortalException {

		return _httpAuthManager.getUserId(
			httpServletRequest, httpAuthorizationHeader);
	}

	public static HttpAuthorizationHeader parse(
		HttpServletRequest httpServletRequest) {

		return _httpAuthManager.parse(httpServletRequest);
	}

	private HttpAuthManagerUtil() {
	}

	private static volatile HttpAuthManager _httpAuthManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			HttpAuthManager.class, HttpAuthManagerUtil.class,
			"_httpAuthManager", false, true);

}