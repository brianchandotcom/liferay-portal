/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

/**
 * @author Brian Wing Shun Chan
 */
public class RolePermissionUtil_IW {
	public static RolePermissionUtil_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		RolePermissionUtil.check(permissionChecker, roleId, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long groupId, long roleId, java.lang.String actionId) {
		return RolePermissionUtil.contains(permissionChecker, groupId, roleId,
			actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long roleId, java.lang.String actionId) {
		return RolePermissionUtil.contains(permissionChecker, roleId, actionId);
	}

	private RolePermissionUtil_IW() {
	}

	private static RolePermissionUtil_IW _instance = new RolePermissionUtil_IW();
}