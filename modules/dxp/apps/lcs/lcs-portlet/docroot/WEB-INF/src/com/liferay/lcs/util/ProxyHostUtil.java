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

package com.liferay.lcs.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Ivica Cardic
 */
public class ProxyHostUtil {

	public static String getProxyHostLogin() {
		String proxyHostLogin = PortletPropsValues.PROXY_HOST_LOGIN;

		if (Validator.isNull(proxyHostLogin)) {
			proxyHostLogin = System.getProperty("http.proxyUser");
		}

		return proxyHostLogin;
	}

	public static String getProxyHostName() {
		String proxyHostName = PortletPropsValues.PROXY_HOST_NAME;

		if (Validator.isNull(proxyHostName)) {
			proxyHostName = System.getProperty("http.proxyHost");
		}

		return proxyHostName;
	}

	public static String getProxyHostPassword() {
		String proxyHostPassword = PortletPropsValues.PROXY_HOST_PASSWORD;

		if (Validator.isNull(proxyHostPassword)) {
			proxyHostPassword = System.getProperty("http.proxyPassword");
		}

		return proxyHostPassword;
	}

	public static int getProxyHostPort() {
		int proxyHostPort = PortletPropsValues.PROXY_HOST_PORT;

		if (proxyHostPort == 0) {
			proxyHostPort = GetterUtil.getInteger(
				System.getProperty("http.proxyPort"));
		}

		return proxyHostPort;
	}

}