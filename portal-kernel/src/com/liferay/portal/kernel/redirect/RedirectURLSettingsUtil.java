/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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