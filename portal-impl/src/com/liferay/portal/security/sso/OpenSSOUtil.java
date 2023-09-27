/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso;

import com.liferay.portal.kernel.security.sso.OpenSSO;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * See https://issues.liferay.com/browse/LEP-5943.
 * </p>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class OpenSSOUtil {

	public static Map<String, String> getAttributes(
		HttpServletRequest httpServletRequest, String serviceUrl) {

		return _openSSO.getAttributes(httpServletRequest, serviceUrl);
	}

	public static String getSubjectId(
		HttpServletRequest httpServletRequest, String serviceUrl) {

		return _openSSO.getSubjectId(httpServletRequest, serviceUrl);
	}

	public static boolean isAuthenticated(
			HttpServletRequest httpServletRequest, String serviceUrl)
		throws IOException {

		return _openSSO.isAuthenticated(httpServletRequest, serviceUrl);
	}

	public static boolean isValidServiceUrl(String serviceUrl) {
		return _openSSO.isValidServiceUrl(serviceUrl);
	}

	public static boolean isValidUrl(String url) {
		return _openSSO.isValidUrl(url);
	}

	public static boolean isValidUrls(String[] urls) {
		return _openSSO.isValidUrls(urls);
	}

	private OpenSSOUtil() {
	}

	private static volatile OpenSSO _openSSO =
		ServiceProxyFactory.newServiceTrackedInstance(
			OpenSSO.class, OpenSSOUtil.class, "_openSSO", false, true);

}