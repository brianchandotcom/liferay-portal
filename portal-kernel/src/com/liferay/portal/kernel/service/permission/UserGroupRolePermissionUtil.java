/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class UserGroupRolePermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException {

		if (!contains(permissionChecker, group, role)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, roleId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException {

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return false;
		}
		else if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) &&
				 !group.isOrganization()) {

			return false;
		}

		if (!permissionChecker.isCompanyAdmin() &&
			!permissionChecker.isGroupOwner(group.getGroupId())) {

			String roleName = role.getName();

			if (roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.ORGANIZATION_OWNER) ||
				roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.SITE_OWNER)) {

				return false;
			}
		}

		if (permissionChecker.isGroupOwner(group.getGroupId()) ||
			GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.ASSIGN_USER_ROLES) ||
			OrganizationPermissionUtil.contains(
				permissionChecker, group.getOrganizationId(),
				ActionKeys.ASSIGN_USER_ROLES) ||
			RolePermissionUtil.contains(
				permissionChecker, group.getGroupId(), role.getRoleId(),
				ActionKeys.ASSIGN_MEMBERS)) {

			return true;
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException {

		return contains(
			permissionChecker, GroupLocalServiceUtil.getGroup(groupId),
			RoleLocalServiceUtil.getRole(roleId));
	}

}