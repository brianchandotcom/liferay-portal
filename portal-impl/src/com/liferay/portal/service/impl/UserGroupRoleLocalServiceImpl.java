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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.service.base.UserGroupRoleLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class UserGroupRoleLocalServiceImpl
	extends UserGroupRoleLocalServiceBaseImpl {

	@Override
	public UserGroupRole addUserGroupRole(
		long userId, long groupId, long roleId) {

		UserGroupRole userGroupRole = userGroupRolePersistence.fetchByU_G_R(
			userId, groupId, roleId);

		if (userGroupRole == null) {
			userGroupRole = userGroupRolePersistence.create(
				counterLocalService.increment(UserGroupRole.class.getName()));

			userGroupRole.setUserId(userId);
			userGroupRole.setGroupId(groupId);
			userGroupRole.setRoleId(roleId);

			userGroupRole = userGroupRolePersistence.update(userGroupRole);
		}

		return userGroupRole;
	}

	@Override
	public List<UserGroupRole> addUserGroupRoles(
		long userId, long groupId, long[] roleIds) {

		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		for (long roleId : roleIds) {
			UserGroupRole userGroupRole = addUserGroupRole(
				userId, groupId, roleId);

			userGroupRoles.add(userGroupRole);
		}

		Group group = groupPersistence.fetchByPrimaryKey(groupId);

		if (group.isRegularSite()) {
			groupPersistence.addUser(groupId, userId);
		}

		return userGroupRoles;
	}

	@Override
	public List<UserGroupRole> addUserGroupRoles(
		long[] userIds, long groupId, long roleId) {

		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		for (long userId : userIds) {
			UserGroupRole userGroupRole = addUserGroupRole(
				userId, groupId, roleId);

			userGroupRoles.add(userGroupRole);
		}

		Group group = groupPersistence.fetchByPrimaryKey(groupId);

		if (group.isRegularSite()) {
			groupPersistence.addUsers(groupId, userIds);
		}

		return userGroupRoles;
	}

	@Override
	public void deleteUserGroupRoles(long groupId, int roleType) {
		List<UserGroupRole> userGroupRoles =
			userGroupRoleFinder.findByGroupRoleType(groupId, roleType);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			userGroupRolePersistence.removeByG_R(
				groupId, userGroupRole.getRoleId());
		}
	}

	@Override
	public void deleteUserGroupRoles(
		long userId, long groupId, long[] roleIds) {

		for (long roleId : roleIds) {
			UserGroupRole userGroupRole = userGroupRolePersistence.fetchByU_G_R(
				userId, groupId, roleId);

			if (userGroupRole != null) {
				userGroupRolePersistence.remove(userGroupRole);
			}
		}
	}

	@Override
	public void deleteUserGroupRoles(long userId, long[] groupIds) {
		for (long groupId : groupIds) {
			userGroupRolePersistence.removeByU_G(userId, groupId);
		}
	}

	@Override
	public void deleteUserGroupRoles(long[] userIds, long groupId) {
		for (long userId : userIds) {
			userGroupRolePersistence.removeByU_G(userId, groupId);
		}
	}

	@Override
	public void deleteUserGroupRoles(
		long[] userIds, long groupId, int roleType) {

		List<Role> roles = rolePersistence.findByT_S(
			roleType, StringPool.BLANK);

		for (long userId : userIds) {
			for (Role role : roles) {
				UserGroupRole userGroupRole =
					userGroupRolePersistence.fetchByU_G_R(
						userId, groupId, role.getRoleId());

				if (userGroupRole != null) {
					userGroupRolePersistence.remove(userGroupRole);
				}
			}
		}
	}

	@Override
	public void deleteUserGroupRoles(
		long[] userIds, long groupId, long roleId) {

		for (long userId : userIds) {
			UserGroupRole userGroupRole = userGroupRolePersistence.fetchByU_G_R(
				userId, groupId, roleId);

			if (userGroupRole != null) {
				userGroupRolePersistence.remove(userGroupRole);
			}
		}
	}

	@Override
	public void deleteUserGroupRolesByGroupId(long groupId) {
		userGroupRolePersistence.removeByGroupId(groupId);
	}

	@Override
	public void deleteUserGroupRolesByRoleId(long roleId) {
		userGroupRolePersistence.removeByRoleId(roleId);
	}

	@Override
	public void deleteUserGroupRolesByUserId(long userId) {
		userGroupRolePersistence.removeByUserId(userId);
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
	public List<UserGroupRole> getUserGroupRoles(
		long userId, long groupId, int start, int end) {

		return userGroupRolePersistence.findByU_G(userId, groupId, start, end);
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
	public int getUserGroupRolesCount(long userId, long groupId) {
		return userGroupRolePersistence.countByU_G(userId, groupId);
	}

	@Override
	public boolean hasUserGroupRole(long userId, long groupId, long roleId) {
		return hasUserGroupRole(userId, groupId, roleId, false);
	}

	@Override
	public boolean hasUserGroupRole(
		long userId, long groupId, long roleId, boolean inherit) {

		int count = userGroupRolePersistence.countByU_G_R(
			userId, groupId, roleId);

		if (count > 0) {
			return true;
		}

		if (inherit && (roleFinder.countByU_G_R(userId, groupId, roleId) > 0)) {
			return true;
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

		Role role = rolePersistence.fetchByC_N(user.getCompanyId(), roleName);

		if (role == null) {
			return false;
		}

		return hasUserGroupRole(userId, groupId, role.getRoleId(), inherit);
	}

}