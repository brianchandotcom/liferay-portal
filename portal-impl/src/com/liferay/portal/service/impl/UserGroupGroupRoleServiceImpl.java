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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.base.UserGroupGroupRoleServiceBaseImpl;
import com.liferay.portal.service.permission.UserGroupRolePermissionUtil;

/**
 * @author Brett Swaim
 * @author Akos Thurzo
 */
public class UserGroupGroupRoleServiceImpl
	extends UserGroupGroupRoleServiceBaseImpl {

	@Override
	public void addUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		checkPermission(groupId, roleIds);

		userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroupId, groupId, roleIds);
	}

	@Override
	public void addUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws PortalException, SystemException {

		checkPermission(groupId, new long[] {roleId});

		userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroupIds, groupId, roleId);
	}

	@Override
	public void deleteUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		checkPermission(groupId, roleIds);

		userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupId, groupId, roleIds);
	}

	@Override
	public void deleteUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws PortalException, SystemException {

		checkPermission(groupId, new long[] {roleId});

		userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupIds, groupId, roleId);
	}

	protected void checkPermission(long groupId, long[] roleIds)
		throws PortalException, SystemException {

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);
		}
	}

}