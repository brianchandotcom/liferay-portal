/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class RolePermissionUtil_IW {
	public static RolePermissionUtil_IW getInstance() {
		return _instance;
	}

	public static void check(
		PermissionChecker permissionChecker, long roleId, String actionId)
		throws PrincipalException {
		RolePermissionUtil.check(permissionChecker, roleId,
			actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, long roleId,
		String actionId) {
		return RolePermissionUtil.contains(permissionChecker,
			groupId, roleId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long roleId, String actionId) {
		return RolePermissionUtil.contains(permissionChecker, 0, roleId, actionId);
	}

	private RolePermissionUtil_IW() {
	}

	private static RolePermissionUtil_IW _instance = new RolePermissionUtil_IW();

}
