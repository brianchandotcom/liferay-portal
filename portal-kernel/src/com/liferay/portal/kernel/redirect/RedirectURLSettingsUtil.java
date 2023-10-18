/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.redirect;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Drew Brokke
 */
public class RedirectURLSettingsUtil {

	public static String[] getAllowedDomains(long companyId) {
		RedirectURLSettings redirectURLSettings =
			_redirectURLSettingsSnapshot.get();

		return GetterUtil.getStringValues(
			redirectURLSettings.getAllowedDomains(companyId));
	}

	public static String[] getAllowedIPs(long companyId) {
		RedirectURLSettings redirectURLSettings =
			_redirectURLSettingsSnapshot.get();

		return GetterUtil.getStringValues(
			redirectURLSettings.getAllowedIPs(companyId),
			new String[] {"127.0.0.1", "SERVER_IP"});
	}

	public static String getSecurityMode(long companyId) {
		RedirectURLSettings redirectURLSettings =
			_redirectURLSettingsSnapshot.get();

		return GetterUtil.getString(
			redirectURLSettings.getSecurityMode(companyId), "ip");
	}

	private static final Snapshot<RedirectURLSettings>
		_redirectURLSettingsSnapshot = new Snapshot<>(
			RedirectURLSettingsUtil.class, RedirectURLSettings.class);

}