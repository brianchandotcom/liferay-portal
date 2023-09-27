/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerFactoryUtil {

	public static PermissionChecker create(User user) {
		return _permissionCheckerFactory.create(user);
	}

	public static PermissionCheckerFactory getPermissionCheckerFactory() {
		return _permissionCheckerFactory;
	}

	private PermissionCheckerFactoryUtil() {
	}

	private static volatile PermissionCheckerFactory _permissionCheckerFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			PermissionCheckerFactory.class, PermissionCheckerFactoryUtil.class,
			"_permissionCheckerFactory", false, true);

}