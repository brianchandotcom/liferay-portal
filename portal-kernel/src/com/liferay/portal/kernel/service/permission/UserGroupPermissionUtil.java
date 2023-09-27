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
public class UserGroupPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long userGroupId,
			String actionId)
		throws PrincipalException {

		_userGroupPermission.check(permissionChecker, userGroupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long userGroupId,
		String actionId) {

		return _userGroupPermission.contains(
			permissionChecker, userGroupId, actionId);
	}

	public static UserGroupPermission getUserGroupPermission() {
		return _userGroupPermission;
	}

	public void setUserGroupPermission(
		UserGroupPermission userGroupPermission) {

		_userGroupPermission = userGroupPermission;
	}

	private static UserGroupPermission _userGroupPermission;

}