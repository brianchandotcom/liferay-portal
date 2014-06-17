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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchUserGroupRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.UserGroupRoleLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.UserGroupRolePK;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class UserGroupRoleLocalServiceImpl
	extends UserGroupRoleLocalServiceBaseImpl {

	@Override
	public List<UserGroupRole> addUserGroupRoles(
		long userId, long groupId, long[] roleIds) {

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		for (long roleId : roleIds) {
			UserGroupRole userGroupRole = addUserGroupRole(
				userId, groupId, roleId);

			userGroupRoles.add(userGroupRole);
		}

		Group group = groupPersistence.fetchByPrimaryKey(groupId);

		if (group.isRegularSite()) {
			groupPersistence.addUser(groupId, userId);
		}

		PermissionCacheUtil.clearCache();

		return userGroupRoles;
	}

	@Override
	public List<UserGroupRole> addUserGroupRoles(
		long[] userIds, long groupId, long roleId) {

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		for (long userId : userIds) {
			UserGroupRole userGroupRole = addUserGroupRole(
				userId, groupId, roleId);

			userGroupRoles.add(userGroupRole);
		}

		Group group = groupPersistence.fetchByPrimaryKey(groupId);

		if (group.isRegularSite()) {
			groupPersistence.addUsers(groupId, userIds);
		}

		PermissionCacheUtil.clearCache();

		return userGroupRoles;
	}

	@Override
	public UserGroupRole deleteUserGroupRole(UserGroupRole userGroupRole) {
		userGroupRolePersistence.remove(userGroupRole);

		PermissionCacheUtil.clearCache();

		return userGroupRole;
	}

	@Override
	public void deleteUserGroupRoles(
		long userId, long groupId, long[] roleIds) {

		for (long roleId : roleIds) {
			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			try {
				userGroupRolePersistence.remove(userGroupRolePK);
			}
			catch (NoSuchUserGroupRoleException nsugre) {
			}
		}

		PermissionCacheUtil.clearCache();
	}

	@Override
	public void deleteUserGroupRoles(long userId, long[] groupIds) {
		for (long groupId : groupIds) {
			userGroupRolePersistence.removeByU_G(userId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	@Override
	public void deleteUserGroupRoles(long[] userIds, long groupId) {
		for (long userId : userIds) {
			userGroupRolePersistence.removeByU_G(userId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	@Override
	public void deleteUserGroupRoles(
		long[] userIds, long groupId, int roleType) {

		List<Role> roles = rolePersistence.findByT_S(
			roleType, StringPool.BLANK);

		for (long userId : userIds) {
			for (Role role : roles) {
				UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
					userId, groupId, role.getRoleId());

				try {
					userGroupRolePersistence.remove(userGroupRolePK);
				}
				catch (NoSuchUserGroupRoleException nsugre) {
				}
			}
		}

		PermissionCacheUtil.clearCache();
	}

	@Override
	public void deleteUserGroupRoles(
		long[] userIds, long groupId, long roleId) {

		for (long userId : userIds) {
			UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

			try {
				userGroupRolePersistence.remove(pk);
			}
			catch (NoSuchUserGroupRoleException nsugre) {
			}
		}

		PermissionCacheUtil.clearCache();
	}

	@Override
	public void deleteUserGroupRolesByGroupId(long groupId) {
		userGroupRolePersistence.removeByGroupId(groupId);

		PermissionCacheUtil.clearCache();
	}

	@Override
	public void deleteUserGroupRolesByRoleId(long roleId) {
		userGroupRolePersistence.removeByRoleId(roleId);

		PermissionCacheUtil.clearCache();
	}

	@Override
	public void deleteUserGroupRolesByUserId(long userId) {
		userGroupRolePersistence.removeByUserId(userId);

		PermissionCacheUtil.clearCache();
	}

	@Override
	public List<UserGroupRole> getUserGroupRoles(long userId) {
		return userGroupRolePersistence.findByUserId(userId);
	}

	@Override
	public List<UserGroupRole> getUserGroupRoles(long userId, long groupId) {
		return userGroupRolePersistence.findByU_G(userId, groupId);
	}

	@Override
	public List<UserGroupRole> getUserGroupRolesByGroup(long groupId) {
		return userGroupRolePersistence.findByGroupId(groupId);
	}

	@Override
	public List<UserGroupRole> getUserGroupRolesByGroupAndRole(
		long groupId, long roleId) {

		return userGroupRolePersistence.findByG_R(groupId, roleId);
	}

	@Override
	public List<UserGroupRole> getUserGroupRolesByUserUserGroupAndGroup(
		long userId, long groupId) {

		return userGroupRoleFinder.findByUserUserGroupGroupRole(
			userId, groupId);
	}

	@Override
	public boolean hasUserGroupRole(long userId, long groupId, long roleId) {
		return hasUserGroupRole(userId, groupId, roleId, false);
	}

	@Override
	public boolean hasUserGroupRole(
		long userId, long groupId, long roleId, boolean inherit) {

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, groupId, roleId);

		UserGroupRole userGroupRole =
			userGroupRolePersistence.fetchByPrimaryKey(userGroupRolePK);

		if (userGroupRole != null) {
			return true;
		}

		if (inherit) {
			if (roleFinder.countByU_G_R(userId, groupId, roleId) > 0) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasUserGroupRole(long userId, long groupId, String roleName)
		throws PortalException {

		return hasUserGroupRole(userId, groupId, roleName, false);
	}

	@Override
	public boolean hasUserGroupRole(
			long userId, long groupId, String roleName, boolean inherit)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		long companyId = user.getCompanyId();

		Role role = rolePersistence.fetchByC_N(companyId, roleName);

		if (role == null) {
			return false;
		}

		return hasUserGroupRole(userId, groupId, role.getRoleId(), inherit);
	}

	protected UserGroupRole addUserGroupRole(
		long userId, long groupId, long roleId) {

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, groupId, roleId);

		UserGroupRole userGroupRole =
			userGroupRolePersistence.fetchByPrimaryKey(userGroupRolePK);

		if (userGroupRole == null) {
			userGroupRole = userGroupRolePersistence.create(userGroupRolePK);

			userGroupRolePersistence.update(userGroupRole);
		}

		return userGroupRole;
	}

}