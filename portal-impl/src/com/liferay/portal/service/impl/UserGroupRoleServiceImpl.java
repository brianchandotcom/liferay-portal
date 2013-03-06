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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicyUtil;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.service.base.UserGroupRoleServiceBaseImpl;
import com.liferay.portal.service.permission.UserGroupRolePermissionUtil;
import com.liferay.portal.service.persistence.UserGroupRolePK;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleServiceImpl extends UserGroupRoleServiceBaseImpl {

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();
		List<UserGroupRole> userOrganizationRoles =
			new ArrayList<UserGroupRole>();

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);

			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole =
				userGroupRoleLocalService.createUserGroupRole(userGroupRolePK);

			Role role = roleLocalService.getRole(roleId);

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				userOrganizationRoles.add(userGroupRole);
			}
			else if (role.getType() == RoleConstants.TYPE_SITE) {
				userGroupRoles.add(userGroupRole);
			}
		}

		if (!userGroupRoles.isEmpty()) {
			SiteMembershipPolicyUtil.checkRoles(userGroupRoles, null);
		}

		if (!userOrganizationRoles.isEmpty()) {
			OrganizationMembershipPolicyUtil.checkRoles(
				userOrganizationRoles, null);
		}

		userGroupRoleLocalService.addUserGroupRoles(userId, groupId, roleIds);

		if (!userGroupRoles.isEmpty()) {
			SiteMembershipPolicyUtil.propagateRoles(userGroupRoles, null);
		}

		if (!userOrganizationRoles.isEmpty()) {
			OrganizationMembershipPolicyUtil.propagateRoles(
				userOrganizationRoles, null);
		}
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		for (long userId : userIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);

			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole =
				userGroupRoleLocalService.createUserGroupRole(userGroupRolePK);

			userGroupRoles.add(userGroupRole);
		}

		if (userGroupRoles.isEmpty()) {
			return;
		}

		Role role = roleLocalService.getRole(roleId);

		if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			OrganizationMembershipPolicyUtil.checkRoles(userGroupRoles, null);
		}
		else if (role.getType() == RoleConstants.TYPE_SITE) {
			SiteMembershipPolicyUtil.checkRoles(userGroupRoles, null);
		}

		userGroupRoleLocalService.addUserGroupRoles(userIds, groupId, roleId);

		if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			OrganizationMembershipPolicyUtil.propagateRoles(
				userGroupRoles, null);
		}
		else if (role.getType() == RoleConstants.TYPE_SITE) {
			SiteMembershipPolicyUtil.propagateRoles(userGroupRoles, null);
		}
	}

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		List<UserGroupRole> filteredUserGroupRoles =
			new ArrayList<UserGroupRole>();
		List<UserGroupRole> filteredUserOrganizationRoles =
			new ArrayList<UserGroupRole>();

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);

			Role role = roleLocalService.getRole(roleId);

			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole =
				userGroupRoleLocalService.createUserGroupRole(userGroupRolePK);

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				Group group = groupLocalService.getGroup(groupId);

				if (!OrganizationMembershipPolicyUtil.isRoleProtected(
						getPermissionChecker(), userId,
						group.getOrganizationId(), roleId)) {

					filteredUserOrganizationRoles.add(userGroupRole);
				}
			}
			else if ((role.getType() == RoleConstants.TYPE_SITE) &&
					 !SiteMembershipPolicyUtil.isRoleProtected(
						getPermissionChecker(), userId, groupId, roleId)) {

					filteredUserGroupRoles.add(userGroupRole);
			}
		}

		if (filteredUserGroupRoles.isEmpty() &&
			filteredUserOrganizationRoles.isEmpty()) {

			return;
		}

		if (!filteredUserGroupRoles.isEmpty()) {
			SiteMembershipPolicyUtil.checkRoles(null, filteredUserGroupRoles);
		}

		if (!filteredUserOrganizationRoles.isEmpty()) {
			OrganizationMembershipPolicyUtil.checkRoles(
				null, filteredUserGroupRoles);
		}

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, groupId, roleIds);

		if (!filteredUserGroupRoles.isEmpty()) {
			SiteMembershipPolicyUtil.propagateRoles(
				null, filteredUserGroupRoles);
		}

		if (!filteredUserOrganizationRoles.isEmpty()) {
			OrganizationMembershipPolicyUtil.propagateRoles(
				null, filteredUserOrganizationRoles);
		}
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		List<UserGroupRole> filteredUserGroupRoles =
			new ArrayList<UserGroupRole>();

		Role role = roleLocalService.getRole(roleId);

		for (long userId : userIds) {
			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole =
				userGroupRoleLocalService.createUserGroupRole(userGroupRolePK);

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				Group group = groupLocalService.getGroup(groupId);

				if (!OrganizationMembershipPolicyUtil.isRoleProtected(
						getPermissionChecker(), userId,
						group.getOrganizationId(), roleId)) {

					filteredUserGroupRoles.add(userGroupRole);
				}
			}
			else if ((role.getType() == RoleConstants.TYPE_SITE) &&
					 (!SiteMembershipPolicyUtil.isRoleProtected(
						getPermissionChecker(), userId, groupId, roleId))) {

					filteredUserGroupRoles.add(userGroupRole);
			}
		}

		if (filteredUserGroupRoles.isEmpty()) {
			return;
		}

		if (role.getType() == RoleConstants.TYPE_SITE) {
			SiteMembershipPolicyUtil.checkRoles(null, filteredUserGroupRoles);
		}
		else if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			OrganizationMembershipPolicyUtil.checkRoles(
				null, filteredUserGroupRoles);
		}

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, groupId, roleId);

		if (role.getType() == RoleConstants.TYPE_SITE) {
			SiteMembershipPolicyUtil.propagateRoles(
				null, filteredUserGroupRoles);
		}
		else if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			OrganizationMembershipPolicyUtil.propagateRoles(
				null, filteredUserGroupRoles);
		}
	}

}