/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class UserGroupRolePermissionImpl implements UserGroupRolePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, group, role)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, roleId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException, SystemException {

		long groupId = group.getGroupId();
		long roleId = role.getRoleId();

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return false;
		}
		else if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) &&
				 !group.isOrganization()) {

			return false;
		}

		if (!permissionChecker.isCompanyAdmin() &&
			!permissionChecker.isGroupOwner(groupId)) {

			String roleName = role.getName();

			if (roleName.equals(
					RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.ORGANIZATION_OWNER) ||
				roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.SITE_OWNER)) {

				return false;
			}
		}

		if (permissionChecker.isGroupOwner(groupId) ||
			GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.ASSIGN_USER_ROLES) ||
			OrganizationPermissionUtil.contains(
				permissionChecker, group.getOrganizationId(),
				ActionKeys.ASSIGN_USER_ROLES) ||
			RolePermissionUtil.contains(
				permissionChecker, groupId, roleId,
				ActionKeys.ASSIGN_MEMBERS)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Role role = RoleLocalServiceUtil.getRole(roleId);

		return contains(permissionChecker, group, role);
	}

}