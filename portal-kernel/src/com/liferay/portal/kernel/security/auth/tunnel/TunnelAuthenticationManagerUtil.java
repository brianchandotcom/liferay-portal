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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public class TunnelAuthenticationManagerUtil {

	public static long getUserId(HttpServletRequest httpServletRequest)
		throws AuthException {

		return _getTunnelManagerUtil().getUserId(httpServletRequest);
	}

	public static void setCredentials(
			String login, HttpURLConnection httpURLConnection)
		throws Exception {

		_getTunnelManagerUtil().setCredentials(login, httpURLConnection);
	}

	private static TunnelAuthenticationManager _getTunnelManagerUtil() {
		return _tunnelAuthenticationManagerUtil._serviceTracker.getService();
	}

	private TunnelAuthenticationManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TunnelAuthenticationManager.class);

		_serviceTracker.open();
	}

	private static final TunnelAuthenticationManagerUtil
		_tunnelAuthenticationManagerUtil =
			new TunnelAuthenticationManagerUtil();

	private final ServiceTracker<?, TunnelAuthenticationManager>
		_serviceTracker;

}