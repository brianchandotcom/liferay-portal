/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth.http;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

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

		HttpAuthManager httpAuthManager = _httpAuthManagerSnapshot.get();

		httpAuthManager.generateChallenge(
			httpServletRequest, httpServletResponse, httpAuthorizationHeader);
	}

	public static long getBasicUserId(HttpServletRequest httpServletRequest)
		throws PortalException {

		HttpAuthManager httpAuthManager = _httpAuthManagerSnapshot.get();

		return httpAuthManager.getBasicUserId(httpServletRequest);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public static long getDigestUserId(HttpServletRequest httpServletRequest)
		throws PortalException {

		HttpAuthManager httpAuthManager = _httpAuthManagerSnapshot.get();

		return httpAuthManager.getDigestUserId(httpServletRequest);
	}

	public static long getUserId(
			HttpServletRequest httpServletRequest,
			HttpAuthorizationHeader httpAuthorizationHeader)
		throws PortalException {

		HttpAuthManager httpAuthManager = _httpAuthManagerSnapshot.get();

		return httpAuthManager.getUserId(
			httpServletRequest, httpAuthorizationHeader);
	}

	public static HttpAuthorizationHeader parse(
		HttpServletRequest httpServletRequest) {

		HttpAuthManager httpAuthManager = _httpAuthManagerSnapshot.get();

		return httpAuthManager.parse(httpServletRequest);
	}

	private HttpAuthManagerUtil() {
	}

	private static final Snapshot<HttpAuthManager> _httpAuthManagerSnapshot =
		new Snapshot<>(HttpAuthManagerUtil.class, HttpAuthManager.class);

}