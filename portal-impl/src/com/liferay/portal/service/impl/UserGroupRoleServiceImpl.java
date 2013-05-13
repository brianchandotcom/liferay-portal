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

	@Override
	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		List<UserGroupRole> organizationUserGroupRoles =
			new ArrayList<UserGroupRole>();
		List<UserGroupRole> siteUserGroupRoles = new ArrayList<UserGroupRole>();

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);

			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole = userGroupRolePersistence.create(
				userGroupRolePK);

			Role role = rolePersistence.findByPrimaryKey(roleId);

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				organizationUserGroupRoles.add(userGroupRole);
			}
			else if (role.getType() == RoleConstants.TYPE_SITE) {
				siteUserGroupRoles.add(userGroupRole);
			}
		}

		if (!siteUserGroupRoles.isEmpty()) {
			SiteMembershipPolicyUtil.checkRoles(siteUserGroupRoles, null);
		}

		if (!organizationUserGroupRoles.isEmpty()) {
			OrganizationMembershipPolicyUtil.checkRoles(
				organizationUserGroupRoles, null);
		}

		userGroupRoleLocalService.addUserGroupRoles(userId, groupId, roleIds);

		if (!siteUserGroupRoles.isEmpty()) {
			SiteMembershipPolicyUtil.propagateRoles(siteUserGroupRoles, null);
		}

		if (!organizationUserGroupRoles.isEmpty()) {
			OrganizationMembershipPolicyUtil.propagateRoles(
				organizationUserGroupRoles, null);
		}
	}

	@Override
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

			UserGroupRole userGroupRole = userGroupRolePersistence.create(
				userGroupRolePK);

			userGroupRoles.add(userGroupRole);
		}

		if (userGroupRoles.isEmpty()) {
			return;
		}

		Role role = rolePersistence.findByPrimaryKey(roleId);

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

	@Override
	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		List<UserGroupRole> filteredOrganizationUserGroupRoles =
			new ArrayList<UserGroupRole>();
		List<UserGroupRole> filteredSiteUserGroupRoles =
			new ArrayList<UserGroupRole>();

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);

			Role role = roleLocalService.getRole(roleId);

			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole = userGroupRolePersistence.create(
				userGroupRolePK);

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				Group group = groupPersistence.findByPrimaryKey(groupId);

				if (!OrganizationMembershipPolicyUtil.isRoleProtected(
						getPermissionChecker(), userId,
						group.getOrganizationId(), roleId)) {

					filteredOrganizationUserGroupRoles.add(userGroupRole);
				}
			}
			else if ((role.getType() == RoleConstants.TYPE_SITE) &&
					 !SiteMembershipPolicyUtil.isRoleProtected(
						getPermissionChecker(), userId, groupId, roleId)) {

					filteredSiteUserGroupRoles.add(userGroupRole);
			}
		}

		if (filteredOrganizationUserGroupRoles.isEmpty() &&
			filteredSiteUserGroupRoles.isEmpty()) {

			return;
		}

		if (!filteredOrganizationUserGroupRoles.isEmpty()) {
			OrganizationMembershipPolicyUtil.checkRoles(
				null, filteredOrganizationUserGroupRoles);
		}

		if (!filteredSiteUserGroupRoles.isEmpty()) {
			SiteMembershipPolicyUtil.checkRoles(
				null, filteredSiteUserGroupRoles);
		}

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, groupId, roleIds);

		if (!filteredOrganizationUserGroupRoles.isEmpty()) {
			OrganizationMembershipPolicyUtil.propagateRoles(
				null, filteredOrganizationUserGroupRoles);
		}

		if (!filteredSiteUserGroupRoles.isEmpty()) {
			SiteMembershipPolicyUtil.propagateRoles(
				null, filteredSiteUserGroupRoles);
		}
	}

	@Override
	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		List<UserGroupRole> filteredUserGroupRoles =
			new ArrayList<UserGroupRole>();

		Role role = rolePersistence.findByPrimaryKey(roleId);

		for (long userId : userIds) {
			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupId, roleId);

			UserGroupRole userGroupRole = userGroupRolePersistence.create(
				userGroupRolePK);

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				Group group = groupPersistence.findByPrimaryKey(groupId);

				if (!OrganizationMembershipPolicyUtil.isRoleProtected(
						getPermissionChecker(), userId,
						group.getOrganizationId(), roleId)) {

					filteredUserGroupRoles.add(userGroupRole);
				}
			}
			else if ((role.getType() == RoleConstants.TYPE_SITE) &&
					 !SiteMembershipPolicyUtil.isRoleProtected(
						 getPermissionChecker(), userId, groupId, roleId)) {

					filteredUserGroupRoles.add(userGroupRole);
			}
		}

		if (filteredUserGroupRoles.isEmpty()) {
			return;
		}

		if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			OrganizationMembershipPolicyUtil.checkRoles(
				null, filteredUserGroupRoles);
		}
		else if (role.getType() == RoleConstants.TYPE_SITE) {
			SiteMembershipPolicyUtil.checkRoles(null, filteredUserGroupRoles);
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