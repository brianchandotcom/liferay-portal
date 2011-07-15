/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.RoleServiceBaseImpl;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class RoleServiceImpl extends RoleServiceBaseImpl {

	public Role addRole(
			String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int type)
		throws PortalException, SystemException {

		PortalPermissionUtil.check(getPermissionChecker(), ActionKeys.ADD_ROLE);

		User user = getUser();

		return roleLocalService.addRole(
			user.getUserId(), user.getCompanyId(), name, titleMap,
			descriptionMap, type);
	}

	public void addUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		checkUserRolesPermission(userId, roleIds);

		roleLocalService.addUserRoles(userId, roleIds);
	}

	public void deleteRole(long roleId)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.DELETE);

		roleLocalService.deleteRole(roleId);
	}

	public List<Role> getGroupRoles(long groupId) throws SystemException {
		return roleLocalService.getGroupRoles(groupId);
	}

	public Role getRole(long roleId)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.VIEW);

		return roleLocalService.getRole(roleId);
	}

	public Role getRole(long companyId, String name)
		throws PortalException, SystemException {

		Role role = roleLocalService.getRole(companyId, name);

		RolePermissionUtil.check(
			getPermissionChecker(), role.getRoleId(), ActionKeys.VIEW);

		return role;
	}

	public List<Role> getUserGroupGroupRoles(long userId, long groupId)
		throws SystemException {

		return roleLocalService.getUserGroupGroupRoles(userId, groupId);
	}

	public List<Role> getUserGroupRoles(long userId, long groupId)
		throws SystemException {

		return roleLocalService.getUserGroupRoles(userId, groupId);
	}

	public List<Role> getUserRelatedRoles(long userId, List<Group> groups)
		throws SystemException {

		return roleLocalService.getUserRelatedRoles(userId, groups);
	}

	public List<Role> getUserRoles(long userId) throws SystemException {
		return roleLocalService.getUserRoles(userId);
	}

	public boolean hasUserRole(
			long userId, long companyId, String name, boolean inherited)
		throws PortalException, SystemException {

		return roleLocalService.hasUserRole(userId, companyId, name, inherited);
	}

	public boolean hasUserRoles(
			long userId, long companyId, String[] names, boolean inherited)
		throws PortalException, SystemException {

		return roleLocalService.hasUserRoles(
			userId, companyId, names, inherited);
	}

	public void unsetUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		checkUserRolesPermission(userId, roleIds);

		roleLocalService.unsetUserRoles(userId, roleIds);
	}

	public Role updateRole(
			long roleId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String subtype)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		return roleLocalService.updateRole(
			roleId, name, titleMap, descriptionMap, subtype);
	}

	protected void checkUserRolesPermission(long userId, long[] roleIds)
		throws PortalException {

		for (int i = 0; i < roleIds.length; i++) {
			RolePermissionUtil.check(
				getPermissionChecker(), roleIds[i], ActionKeys.ASSIGN_MEMBERS);
		}
	}

}