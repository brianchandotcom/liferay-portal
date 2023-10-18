/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.kernel.util;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class UserInitialsGeneratorUtil {

	public static String getInitials(
		Locale locale, String firstName, String middleName, String lastName) {

		UserInitialsGenerator userInitialsGenerator =
			_userInitialsGeneratorSnapshot.get();

		return userInitialsGenerator.getInitials(
			locale, firstName, middleName, lastName);
	}

	public static String getInitials(User user) {
		UserInitialsGenerator userInitialsGenerator =
			_userInitialsGeneratorSnapshot.get();

		return userInitialsGenerator.getInitials(user);
	}

	private static final Snapshot<UserInitialsGenerator>
		_userInitialsGeneratorSnapshot = new Snapshot<>(
			UserInitialsGeneratorUtil.class, UserInitialsGenerator.class);

}