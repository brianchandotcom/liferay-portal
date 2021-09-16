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

package com.liferay.portal.kernel.security.auth.tunnel;

import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public class TunnelAuthenticationManagerUtil {

	public static long getUserId(HttpServletRequest httpServletRequest)
		throws AuthException {

		return _tunnelAuthenticationManager.getUserId(httpServletRequest);
	}

	public static void setCredentials(
			String login, HttpURLConnection httpURLConnection)
		throws Exception {

		_tunnelAuthenticationManager.setCredentials(login, httpURLConnection);
	}

	private TunnelAuthenticationManagerUtil() {
	}

	private static volatile TunnelAuthenticationManager
		_tunnelAuthenticationManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				TunnelAuthenticationManager.class,
				TunnelAuthenticationManagerUtil.class,
				"_tunnelAuthenticationManager", false, true);

}