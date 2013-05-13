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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.persistence.GroupActionableDynamicQuery;
import com.liferay.portal.service.persistence.UserGroupRoleActionableDynamicQuery;
import com.liferay.portal.service.persistence.UserGroupRolePK;

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
	@SuppressWarnings("unused")
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException {
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

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
	@SuppressWarnings("unused")
	public boolean isMembershipRequired(long userId, long groupId)
		throws PortalException, SystemException {

		try {
			checkMembership(new long[] {userId}, null, new long[] {groupId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException, SystemException {

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

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
		throws PortalException, SystemException {

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
		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

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
	@SuppressWarnings("unused")
	public void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException {
	}

	@Override
	public void verifyPolicy() throws PortalException, SystemException {
		ActionableDynamicQuery groupActionableDynamicQuery =
			new GroupActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property property = PropertyFactoryUtil.forName("site");

				dynamicQuery.add(property.eq(true));
			}

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				Group group = (Group)object;

				verifyPolicy(group);

				ActionableDynamicQuery userGroupRoleActionableDynamicQuery =
					new UserGroupRoleActionableDynamicQuery() {

					@Override
					protected void performAction(Object object)
						throws PortalException, SystemException {

						UserGroupRole userGroupRole = (UserGroupRole)object;

						verifyPolicy(userGroupRole.getRole());
					}

				};

				userGroupRoleActionableDynamicQuery.setGroupId(
					group.getGroupId());

				userGroupRoleActionableDynamicQuery.performActions();
			}

		};

		groupActionableDynamicQuery.performActions();
	}

	@Override
	public void verifyPolicy(Group group)
		throws PortalException, SystemException {

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