/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class UserGroupRolePermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException {

		getUserGroupRolePermission().check(permissionChecker, group, role);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException {

		getUserGroupRolePermission().check(permissionChecker, groupId, roleId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException {

		return getUserGroupRolePermission().contains(
			permissionChecker, group, role);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException {

		return getUserGroupRolePermission().contains(
			permissionChecker, groupId, roleId);
	}

	public static UserGroupRolePermission getUserGroupRolePermission() {
		return _userGroupRolePermission;
	}

	public void setUserGroupRolePermission(
		UserGroupRolePermission userGroupRolePermission) {

		_userGroupRolePermission = userGroupRolePermission;
	}

	private static UserGroupRolePermission _userGroupRolePermission;

}