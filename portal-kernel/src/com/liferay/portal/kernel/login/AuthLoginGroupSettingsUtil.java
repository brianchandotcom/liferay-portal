/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.login;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Erick Monteiro
 */
public class AuthLoginGroupSettingsUtil {

	public static boolean isPromptEnabled(long groupId) {
		AuthLoginGroupSettings authLoginGroupSettings =
			_authLoginGroupSettingsSnapshot.get();

		return authLoginGroupSettings.isPromptEnabled(groupId);
	}

	private static final Snapshot<AuthLoginGroupSettings>
		_authLoginGroupSettingsSnapshot = new Snapshot<>(
			AuthLoginGroupSettingsUtil.class, AuthLoginGroupSettings.class);

}