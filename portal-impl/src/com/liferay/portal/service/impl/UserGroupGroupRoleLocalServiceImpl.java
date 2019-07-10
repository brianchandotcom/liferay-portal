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

import com.liferay.portal.kernel.exception.NoSuchUserGroupGroupRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRolePK;
import com.liferay.portal.service.base.UserGroupGroupRoleLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brett Swaim
 */
public class UserGroupGroupRoleLocalServiceImpl
	extends UserGroupGroupRoleLocalServiceBaseImpl {

	@Override
	public void addUserGroupGroupRoles(
		long userGroupId, long groupId, long[] roleIds) {

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
	}

	@Override
	public void addUserGroupGroupRoles(
		long[] userGroupIds, long groupId, long roleId) {

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
	}

	@Override
	public void deleteUserGroupGroupRoles(long groupId, int roleType) {
		List<UserGroupGroupRole> userGroupGroupRoles =
			userGroupGroupRoleFinder.findByGroupRoleType(groupId, roleType);

		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			userGroupGroupRolePersistence.removeByG_R(
				groupId, userGroupGroupRole.getRoleId());
		}
	}

	@Override
	public void deleteUserGroupGroupRoles(
		long userGroupId, long groupId, long[] roleIds) {

		for (long roleId : roleIds) {
			UserGroupGroupRolePK pk = new UserGroupGroupRolePK(
				userGroupId, groupId, roleId);

			try {
				userGroupGroupRolePersistence.remove(pk);
			}
			catch (NoSuchUserGroupGroupRoleException nsuggre) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsuggre, nsuggre);
				}
			}
		}
	}

	@Override
	public void deleteUserGroupGroupRoles(long userGroupId, long[] groupIds) {
		for (long groupId : groupIds) {
			userGroupGroupRolePersistence.removeByU_G(userGroupId, groupId);
		}
	}

	@Override
	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId) {
		for (long userGroupId : userGroupIds) {
			userGroupGroupRolePersistence.removeByU_G(userGroupId, groupId);
		}
	}

	@Override
	public void deleteUserGroupGroupRoles(
		long[] userGroupIds, long groupId, long roleId) {

		for (long userGroupId : userGroupIds) {
			UserGroupGroupRolePK pk = new UserGroupGroupRolePK(
				userGroupId, groupId, roleId);

			try {
				userGroupGroupRolePersistence.remove(pk);
			}
			catch (NoSuchUserGroupGroupRoleException nsuggre) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsuggre, nsuggre);
				}
			}
		}
	}

	@Override
	public void deleteUserGroupGroupRolesByGroupId(long groupId) {
		userGroupGroupRolePersistence.removeByGroupId(groupId);
	}

	@Override
	public void deleteUserGroupGroupRolesByRoleId(long roleId) {
		userGroupGroupRolePersistence.removeByRoleId(roleId);
	}

	@Override
	public void deleteUserGroupGroupRolesByUserGroupId(long userGroupId) {
		userGroupGroupRolePersistence.removeByUserGroupId(userGroupId);
	}

	@Override
	public List<UserGroupGroupRole> getUserGroupGroupRoles(long userGroupId) {
		return userGroupGroupRolePersistence.findByUserGroupId(userGroupId);
	}

	@Override
	public List<UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId, long groupId) {

		return userGroupGroupRolePersistence.findByU_G(userGroupId, groupId);
	}

	@Override
	public List<UserGroupGroupRole> getUserGroupGroupRolesByGroupAndRole(
		long groupId, long roleId) {

		return userGroupGroupRolePersistence.findByG_R(groupId, roleId);
	}

	@Override
	public List<UserGroupGroupRole> getUserGroupGroupRolesByUser(long userId) {
		return userGroupGroupRoleFinder.findByUserGroupsUsers(userId);
	}

	@Override
	public List<UserGroupGroupRole> getUserGroupGroupRolesByUser(
		long userId, long groupId) {

		return userGroupGroupRoleFinder.findByUserGroupsUsers(userId, groupId);
	}

	@Override
	public boolean hasUserGroupGroupRole(
		long userGroupId, long groupId, long roleId) {

		UserGroupGroupRolePK pk = new UserGroupGroupRolePK(
			userGroupId, groupId, roleId);

		UserGroupGroupRole userGroupGroupRole =
			userGroupGroupRolePersistence.fetchByPrimaryKey(pk);

		if (userGroupGroupRole != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasUserGroupGroupRole(
			long userGroupId, long groupId, String roleName)
		throws PortalException {

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		long companyId = userGroup.getCompanyId();

		Role role = rolePersistence.findByC_N(companyId, roleName);

		long roleId = role.getRoleId();

		return hasUserGroupGroupRole(userGroupId, groupId, roleId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupGroupRoleLocalServiceImpl.class);

}