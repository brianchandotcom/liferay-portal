/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchUserGroupGroupRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.UserGroupGroupRoleLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.UserGroupGroupRolePK;

import java.util.List;

/**
 * @author Brett Swaim
 */
public class UserGroupGroupRoleLocalServiceImpl
	extends UserGroupGroupRoleLocalServiceBaseImpl {

	public void addUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws SystemException {

		for (long roleId : roleIds) {
			UserGroupGroupRolePK pk = new UserGroupGroupRolePK(
				userGroupId, groupId, roleId);

			UserGroupGroupRole userGroupGroupRole =
				userGroupGroupRolePersistence.fetchByPrimaryKey(pk);

			if (userGroupGroupRole == null) {
				userGroupGroupRole = userGroupGroupRolePersistence.create(pk);

				userGroupGroupRolePersistence.update(userGroupGroupRole);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void addUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws SystemException {

		for (long userGroupId : userGroupIds) {
			UserGroupGroupRolePK pk = new UserGroupGroupRolePK(
				userGroupId, groupId, roleId);

			UserGroupGroupRole userGroupGroupRole =
				userGroupGroupRolePersistence.fetchByPrimaryKey(pk);

			if (userGroupGroupRole == null) {
				userGroupGroupRole = userGroupGroupRolePersistence.create(pk);

				userGroupGroupRolePersistence.update(userGroupGroupRole);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	@Override
	public UserGroupGroupRole deleteUserGroupGroupRole(
			UserGroupGroupRole userGroupGroupRole)
		throws SystemException {

		userGroupGroupRolePersistence.remove(userGroupGroupRole);

		PermissionCacheUtil.clearCache();

		return userGroupGroupRole;
	}

	public void deleteUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws SystemException {

		for (long roleId : roleIds) {
			UserGroupGroupRolePK pk = new UserGroupGroupRolePK(
				userGroupId, groupId, roleId);

			try {
				userGroupGroupRolePersistence.remove(pk);
			}
			catch (NoSuchUserGroupGroupRoleException nsuggre) {
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupGroupRoles(long userGroupId, long[] groupIds)
		throws SystemException {

		for (long groupId : groupIds) {
			userGroupGroupRolePersistence.removeByU_G(userGroupId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId)
		throws SystemException {

		for (long userGroupId : userGroupIds) {
			userGroupGroupRolePersistence.removeByU_G(userGroupId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws SystemException {

		for (long userGroupId : userGroupIds) {
			UserGroupGroupRolePK pk = new UserGroupGroupRolePK(
				userGroupId, groupId, roleId);

			try {
				userGroupGroupRolePersistence.remove(pk);
			}
			catch (NoSuchUserGroupGroupRoleException nsuggre) {
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupGroupRolesByGroupId(long groupId)
		throws SystemException {

		userGroupGroupRolePersistence.removeByGroupId(groupId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupGroupRolesByRoleId(long roleId)
		throws SystemException {

		userGroupGroupRolePersistence.removeByRoleId(roleId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupGroupRolesByUserGroupId(long userGroupId)
		throws SystemException {

		userGroupGroupRolePersistence.removeByUserGroupId(userGroupId);

		PermissionCacheUtil.clearCache();
	}

	public List<UserGroupGroupRole> getUserGroupGroupRoles(long userGroupId)
		throws SystemException {

		return userGroupGroupRolePersistence.findByUserGroupId(userGroupId);
	}

	public List<UserGroupGroupRole> getUserGroupGroupRoles(
			long userGroupId, long groupId)
		throws SystemException {

		return userGroupGroupRolePersistence.findByU_G(userGroupId, groupId);
	}

	public List<UserGroupGroupRole> getUserGroupGroupRolesByGroupAndRole(
			long groupId, long roleId)
		throws SystemException {

		return userGroupGroupRolePersistence.findByG_R(groupId, roleId);
	}

	public boolean hasUserGroupGroupRole(
			long userGroupId, long groupId, long roleId)
		throws SystemException {

		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(
			userGroupId, groupId, roleId);

		UserGroupGroupRole userGroupGroupRole =
			userGroupGroupRolePersistence.fetchByPrimaryKey(pk);

		if (userGroupGroupRole != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasUserGroupGroupRole(
			long userGroupId, long groupId, String roleName)
		throws PortalException, SystemException {

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		long companyId = userGroup.getCompanyId();

		Role role = rolePersistence.findByC_N(companyId, roleName);

		long roleId = role.getRoleId();

		return hasUserGroupGroupRole(userGroupId, groupId, roleId);
	}

}