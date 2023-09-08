/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupPermissionUtil_IW {
	public static UserGroupPermissionUtil_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long userGroupId, java.lang.String actionId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		UserGroupPermissionUtil.check(permissionChecker, userGroupId, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long userGroupId, java.lang.String actionId) {
		return UserGroupPermissionUtil.contains(permissionChecker, userGroupId,
			actionId);
	}

	private UserGroupPermissionUtil_IW() {
	}

	private static UserGroupPermissionUtil_IW _instance = new UserGroupPermissionUtil_IW();
}