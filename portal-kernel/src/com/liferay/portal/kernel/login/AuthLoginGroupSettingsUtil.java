/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.login;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Erick Monteiro
 */
public class AuthLoginGroupSettingsUtil {

	public static boolean isPromptEnabled(long groupId) {
		return GetterUtil.getBoolean(
			_authLoginGroupSettings.isPromptEnabled(groupId));
	}

	private static volatile AuthLoginGroupSettings _authLoginGroupSettings =
		ServiceProxyFactory.newServiceTrackedInstance(
			AuthLoginGroupSettings.class, AuthLoginGroupSettingsUtil.class,
			"_authLoginGroupSettings", false);

}