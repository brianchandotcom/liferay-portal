/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shinn Lok
 */
public class ServletRequestUtil {

	public static String getOriginalURL(HttpServletRequest httpServletRequest) {
		StringBuilder sb = new StringBuilder();

		sb.append(_getScheme(httpServletRequest));
		sb.append("://");
		sb.append(_getServerName(httpServletRequest));

		int serverPort = _getServerPort(httpServletRequest);

		if (serverPort > 0) {
			sb.append(":");
			sb.append(serverPort);
		}

		return sb.toString();
	}

	private static String _getScheme(HttpServletRequest httpServletRequest) {
		String forwardedProtocol = httpServletRequest.getHeader(
			"X-Forwarded-Proto");

		if (forwardedProtocol != null) {
			return forwardedProtocol;
		}

		return httpServletRequest.getScheme();
	}

	private static String _getServerName(
		HttpServletRequest httpServletRequest) {

		String forwardedHost = httpServletRequest.getHeader("X-Forwarded-Host");

		if (forwardedHost != null) {
			return forwardedHost;
		}

		return httpServletRequest.getServerName();
	}

	private static int _getServerPort(HttpServletRequest httpServletRequest) {
		int serverPort = 0;

		String forwardedPort = httpServletRequest.getHeader("X-Forwarded-Port");

		if (forwardedPort != null) {
			serverPort = GetterUtil.getInteger(forwardedPort);
		}
		else {
			serverPort = httpServletRequest.getServerPort();
		}

		if ((serverPort == Http.HTTP_PORT) || (serverPort == Http.HTTPS_PORT)) {
			return -1;
		}

		return serverPort;
	}

}