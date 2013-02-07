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

import com.liferay.portal.NoSuchUserGroupRoleException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.auth.MembershipPolicyUtil;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.UserGroupRoleLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.UserGroupRolePK;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class UserGroupRoleLocalServiceImpl
	extends UserGroupRoleLocalServiceBaseImpl {

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws SystemException {

		for (long roleId : roleIds) {
			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole =
				userGroupRolePersistence.fetchByPrimaryKey(userGroupRolePK);

			if (userGroupRole == null) {
				userGroupRole = userGroupRolePersistence.create(
					userGroupRolePK);

				userGroupRolePersistence.update(userGroupRole);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws SystemException {

		for (long userId : userIds) {
			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole =
				userGroupRolePersistence.fetchByPrimaryKey(userGroupRolePK);

			if (userGroupRole == null) {
				userGroupRole = userGroupRolePersistence.create(
					userGroupRolePK);

				userGroupRolePersistence.update(userGroupRole);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void checkMembershipPolicy(User user)
		throws PortalException, SystemException {

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("inheritance", Boolean.FALSE);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put("usersGroups", user.getUserId());

		List<Group> groups = groupLocalService.search(
			user.getCompanyId(), groupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (Group group : groups) {
			Set<Role> mandatoryGroupRoles =
				MembershipPolicyUtil.getMandatoryRoles(group, user);

			for (Role role : mandatoryGroupRoles) {
				if (!hasUserGroupRole(
						user.getUserId(), group.getGroupId(), role.getRoleId(),
						false)) {

					addUserGroupRoles(
						user.getUserId(), group.getGroupId(),
						new long[] {role.getRoleId()});
				}
			}

			List<Role> userGroupRoles = roleLocalService.getUserGroupRoles(
				user.getUserId(), group.getGroupId());

			for (Role role : userGroupRoles) {
				if (!MembershipPolicyUtil.isMembershipAllowed(
						role, user, group)) {

					deleteUserGroupRoles(
						user.getUserId(), group.getGroupId(),
						new long[] {role.getRoleId()});
				}
			}
		}

		List<Organization> organizations =
			organizationLocalService.getUserOrganizations(user.getUserId());

		for (Organization organization : organizations) {
			Set<Role> mandatoryOrganizationRoles =
				MembershipPolicyUtil.getMandatoryRoles(organization, user);

			for (Role role : mandatoryOrganizationRoles) {
				if (!hasUserGroupRole(
						user.getUserId(), organization.getGroupId(),
						role.getRoleId(), false)) {

					addUserGroupRoles(
						user.getUserId(), organization.getGroupId(),
						new long[] {role.getRoleId()});
				}
			}

			List<Role> userOrganizationRoles =
				roleLocalService.getUserGroupRoles(
					user.getUserId(), organization.getGroupId());

			for (Role role : userOrganizationRoles) {
				if (!MembershipPolicyUtil.isMembershipAllowed(
						role, user, organization)) {

					deleteUserGroupRoles(
						user.getUserId(), organization.getGroupId(),
						new long[] {role.getRoleId()});
				}
			}
		}
	}

	@Override
	public UserGroupRole deleteUserGroupRole(UserGroupRole userGroupRole)
		throws SystemException {

		userGroupRolePersistence.remove(userGroupRole);

		PermissionCacheUtil.clearCache();

		return userGroupRole;
	}

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws SystemException {

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

	public void deleteUserGroupRoles(long userId, long[] groupIds)
		throws SystemException {

		for (long groupId : groupIds) {
			userGroupRolePersistence.removeByU_G(userId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId)
		throws SystemException {

		for (long userId : userIds) {
			userGroupRolePersistence.removeByU_G(userId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, int roleType)
		throws SystemException {

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

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws SystemException {

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

	public void deleteUserGroupRolesByGroupId(long groupId)
		throws SystemException {

		userGroupRolePersistence.removeByGroupId(groupId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRolesByRoleId(long roleId)
		throws SystemException {

		userGroupRolePersistence.removeByRoleId(roleId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRolesByUserId(long userId)
		throws SystemException {

		userGroupRolePersistence.removeByUserId(userId);

		PermissionCacheUtil.clearCache();
	}

	public List<UserGroupRole> getUserGroupRoles(long userId)
		throws SystemException {

		return userGroupRolePersistence.findByUserId(userId);
	}

	public List<UserGroupRole> getUserGroupRoles(long userId, long groupId)
		throws SystemException {

		return userGroupRolePersistence.findByU_G(userId, groupId);
	}

	public List<UserGroupRole> getUserGroupRolesByGroupAndRole(
			long groupId, long roleId)
		throws SystemException {

		return userGroupRolePersistence.findByG_R(groupId, roleId);
	}

	public List<UserGroupRole> getUserGroupRolesByUserUserGroupAndGroup(
			long userId, long groupId)
		throws SystemException {

		return userGroupRoleFinder.findByUserUserGroupGroupRole(
			userId, groupId);
	}

	public boolean hasUserGroupRole(long userId, long groupId, long roleId)
		throws SystemException {

		return hasUserGroupRole(userId, groupId, roleId, false);
	}

	public boolean hasUserGroupRole(
			long userId, long groupId, long roleId, boolean inherit)
		throws SystemException {

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

	public boolean hasUserGroupRole(long userId, long groupId, String roleName)
		throws PortalException, SystemException {

		return hasUserGroupRole(userId, groupId, roleName, false);
	}

	public boolean hasUserGroupRole(
			long userId, long groupId, String roleName, boolean inherit)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long companyId = user.getCompanyId();

		Role role = rolePersistence.findByC_N(companyId, roleName);

		long roleId = role.getRoleId();

		return hasUserGroupRole(userId, groupId, roleId, inherit);
	}

}