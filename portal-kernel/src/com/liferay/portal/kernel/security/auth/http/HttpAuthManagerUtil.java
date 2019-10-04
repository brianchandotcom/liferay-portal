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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

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

		_getHttpAuthManager().generateChallenge(
			httpServletRequest, httpServletResponse, httpAuthorizationHeader);
	}

	public static long getBasicUserId(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getHttpAuthManager().getBasicUserId(httpServletRequest);
	}

	public static long getDigestUserId(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getHttpAuthManager().getDigestUserId(httpServletRequest);
	}

	public static long getUserId(
			HttpServletRequest httpServletRequest,
			HttpAuthorizationHeader httpAuthorizationHeader)
		throws PortalException {

		return _getHttpAuthManager().getUserId(
			httpServletRequest, httpAuthorizationHeader);
	}

	public static HttpAuthorizationHeader parse(
		HttpServletRequest httpServletRequest) {

		return _getHttpAuthManager().parse(httpServletRequest);
	}

	private static HttpAuthManager _getHttpAuthManager() {
		return _httpAuthManagerUtil._serviceTracker.getService();
	}

	private HttpAuthManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(HttpAuthManager.class);

		_serviceTracker.open();
	}

	private static final HttpAuthManagerUtil _httpAuthManagerUtil =
		new HttpAuthManagerUtil();

	private final ServiceTracker<?, HttpAuthManager> _serviceTracker;

}