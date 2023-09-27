/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.UserGroupPermission;

/**
 * @author Charles May
 */
public class UserGroupPermissionImpl implements UserGroupPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long userGroupId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, userGroupId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, UserGroup.class.getName(), userGroupId,
				actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long userGroupId,
		String actionId) {

		UserGroup userGroup = UserGroupLocalServiceUtil.fetchUserGroup(
			userGroupId);

		if ((userGroup != null) &&
			permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), UserGroup.class.getName(),
				userGroupId, userGroup.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, UserGroup.class.getName(), userGroupId, actionId);
	}

}