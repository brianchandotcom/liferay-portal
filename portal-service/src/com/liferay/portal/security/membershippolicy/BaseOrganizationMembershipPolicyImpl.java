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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.List;

/**
 * @author Roberto Díaz
 */
public abstract class BaseOrganizationMembershipPolicyImpl
	implements OrganizationMembershipPolicy {

	public boolean isMembershipAllowed(long userId, long organizationId)
		throws PortalException, SystemException {

		try {
			checkAddMembership(new long[]{userId}, new long[]{organizationId});
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId)
		throws PortalException, SystemException {

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);
		User user = UserLocalServiceUtil.getUser(userId);

		Group group = organization.getGroup();

		if (permissionChecker.isOrganizationOwner(group.getOrganizationId())) {
			return false;
		}

		Role organizationAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(),
				organizationAdministratorRole.getRoleId())) {

			return true;
		}

		Role organizationOwnerRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(),
				organizationOwnerRole.getRoleId())) {

			return true;
		}

		return false;
	}

	public boolean isMembershipRequired(long userId, long organizationId)
		throws PortalException, SystemException {

		try {
			checkRemoveMembership(
				new long[]{userId}, new long[]{organizationId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	public boolean isRoleAllowed(long userId, long organizationId, long roleId)
		throws PortalException, SystemException {

		try {
			checkAddRoles(
				new long[] {userId}, new long[] {organizationId},
				new long[] {roleId});
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId, long roleId)
		throws PortalException, SystemException {

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);
		Role role = RoleLocalServiceUtil.getRole(roleId);
		User user = UserLocalServiceUtil.getUser(userId);

		Group group = organization.getGroup();

		if (permissionChecker.isOrganizationOwner(group.getOrganizationId())) {
			return false;
		}

		String roleName = role.getName();

		if (!roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) &&
			!roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

			return false;
		}

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(), role.getRoleId())) {

			return true;
		}

		return false;
	}

	public boolean isRoleRequired(long userId, long organizationId, long roleId)
		throws PortalException, SystemException {

		try {
			checkRemoveRoles(
				new long[] {userId}, new long[] {organizationId},
				new long[] {roleId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	public void verifyPolicy() throws PortalException, SystemException {
		int start = 0;
		int end = SEARCH_INTERVAL;
		int total = OrganizationLocalServiceUtil.getOrganizationsCount();

		while (start <= total) {
			List<Organization> organizations =
				OrganizationLocalServiceUtil.getOrganizations(start, end);

			for (Organization organization : organizations) {
				verifyPolicy(organization);

				List<UserGroupRole> userGroupRoles =
					UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroup(
						organization.getGroupId());

				for (UserGroupRole userGroupRole : userGroupRoles) {
					verifyPolicy(userGroupRole.getRole());
				}
			}

			start = end;
			end += end;
		}
	}

}