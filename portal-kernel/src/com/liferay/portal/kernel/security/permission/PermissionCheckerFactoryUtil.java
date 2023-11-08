/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerFactoryUtil {

	public static PermissionChecker create(User user) {
		PermissionCheckerFactory permissionCheckerFactory =
			_permissionCheckerFactorySnapshot.get();

		return permissionCheckerFactory.create(user);
	}

	public static PermissionCheckerFactory getPermissionCheckerFactory() {
		return _permissionCheckerFactorySnapshot.get();
	}

	private PermissionCheckerFactoryUtil() {
	}

	private static final Snapshot<PermissionCheckerFactory>
		_permissionCheckerFactorySnapshot = new Snapshot<>(
			PermissionCheckerFactoryUtil.class, PermissionCheckerFactory.class,
			null, true);

}