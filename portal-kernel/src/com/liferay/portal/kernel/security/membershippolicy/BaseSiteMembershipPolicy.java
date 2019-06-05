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

package com.liferay.portal.kernel.security.membershippolicy;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.UserGroupRolePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseSiteMembershipPolicy implements SiteMembershipPolicy {

	@Override
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {
	}

	@Override
	public boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException {

		try {
			checkMembership(new long[] {userId}, new long[] {groupId}, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException {

		if (permissionChecker.isGroupOwner(groupId)) {
			return false;
		}

		Role siteAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, siteAdministratorRole.getRoleId())) {

			return true;
		}

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_OWNER);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, siteOwnerRole.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMembershipRequired(long userId, long groupId)
		throws PortalException {

		try {
			checkMembership(new long[] {userId}, null, new long[] {groupId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException {

		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, groupId, roleId);

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.createUserGroupRole(userGroupRolePK);

		userGroupRoles.add(userGroupRole);

		try {
			checkRoles(userGroupRoles, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException {

		if (permissionChecker.isGroupOwner(groupId)) {
			return false;
		}

		Role role = RoleLocalServiceUtil.getRole(roleId);

		String roleName = role.getName();

		if (!roleName.equals(RoleConstants.SITE_ADMINISTRATOR) &&
			!roleName.equals(RoleConstants.SITE_OWNER)) {

			return false;
		}

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, roleId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleRequired(long userId, long groupId, long roleId) {
		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, groupId, roleId);

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.createUserGroupRole(userGroupRolePK);

		userGroupRoles.add(userGroupRole);

		try {
			checkRoles(null, userGroupRoles);
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	@Override
	public void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {
	}

	@Override
	public void verifyPolicy() throws PortalException {
		ActionableDynamicQuery groupActionableDynamicQuery =
			GroupLocalServiceUtil.getActionableDynamicQuery();

		groupActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("site");

				dynamicQuery.add(property.eq(true));
			});
		groupActionableDynamicQuery.setPerformActionMethod(
			(Group group) -> {
				verifyPolicy(group);

				ActionableDynamicQuery userGroupRoleActionableDynamicQuery =
					UserGroupRoleLocalServiceUtil.getActionableDynamicQuery();

				userGroupRoleActionableDynamicQuery.setGroupId(
					group.getGroupId());
				userGroupRoleActionableDynamicQuery.setPerformActionMethod(
					(UserGroupRole userGroupRole) -> verifyPolicy(
						userGroupRole.getRole()));

				userGroupRoleActionableDynamicQuery.performActions();
			});

		groupActionableDynamicQuery.performActions();
	}

	@Override
	public void verifyPolicy(Group group) throws PortalException {
		verifyPolicy(group, null, null, null, null, null);
	}

	@Override
	public void verifyPolicy(Role role) {
	}

	@Override
	public void verifyPolicy(
		Role role, Role oldRole,
		Map<String, Serializable> oldExpandoAttributes) {
	}

}