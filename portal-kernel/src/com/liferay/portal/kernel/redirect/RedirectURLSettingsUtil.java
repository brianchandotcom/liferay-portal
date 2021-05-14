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

package com.liferay.portal.kernel.redirect;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Drew Brokke
 */
public class RedirectURLSettingsUtil {

	public static String[] getAllowedDomains(long companyId) {
		return GetterUtil.getStringValues(
			_redirectURLSettings.getAllowedDomains(companyId));
	}

	public static String[] getAllowedIPs(long companyId) {
		return GetterUtil.getStringValues(
			_redirectURLSettings.getAllowedIPs(companyId),
			new String[] {"127.0.0.1", "SERVER_IP"});
	}

	public static String getSecurityMode(long companyId) {
		return GetterUtil.getString(
			_redirectURLSettings.getSecurityMode(companyId), "ip");
	}

	private static volatile RedirectURLSettings _redirectURLSettings =
		ServiceProxyFactory.newServiceTrackedInstance(
			RedirectURLSettings.class, RedirectURLSettingsUtil.class,
			"_redirectURLSettings", false);

}