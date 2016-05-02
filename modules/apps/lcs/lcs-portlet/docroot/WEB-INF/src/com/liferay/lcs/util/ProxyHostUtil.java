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
		return PortletPropsValues.PROXY_HOST_LOGIN;
	}

	public static String getProxyHostName() {
		return System.getProperty("http.proxyHost");
	}

	public static String getProxyHostPassword() {
		return PortletPropsValues.PROXY_HOST_PASSWORD;
	}

	public static int getProxyHostPort() {
		return GetterUtil.getInteger(System.getProperty("http.proxyPort"));
	}

	static {
		if (Validator.isNull(System.getProperty("http.proxyHost")) &&
			Validator.isNotNull(PortletPropsValues.PROXY_HOST_NAME)) {

			System.setProperty(
				"http.proxyHost", PortletPropsValues.PROXY_HOST_NAME);
		}

		if (Validator.isNull(System.getProperty("http.proxyPort")) &&
			(PortletPropsValues.PROXY_HOST_PORT != 0)) {

			System.setProperty(
				"http.proxyPort",
				String.valueOf(PortletPropsValues.PROXY_HOST_PORT));
		}
	}

}