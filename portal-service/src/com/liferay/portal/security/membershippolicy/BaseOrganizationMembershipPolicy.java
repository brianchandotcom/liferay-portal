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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.persistence.OrganizationActionableDynamicQuery;
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
public abstract class BaseOrganizationMembershipPolicy
	implements OrganizationMembershipPolicy {

	@Override
	@SuppressWarnings("unused")
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException {
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isMembershipAllowed(long userId, long organizationId)
		throws PortalException, SystemException {

		try {
			checkMembership(
				new long[] {userId}, new long[] {organizationId}, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId)
		throws PortalException, SystemException {

		if (permissionChecker.isOrganizationOwner(organizationId)) {
			return false;
		}

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);

		Group group = organization.getGroup();

		Role organizationAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, group.getGroupId(),
				organizationAdministratorRole.getRoleId())) {

			return true;
		}

		Role organizationOwnerRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, group.getGroupId(),
				organizationOwnerRole.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isMembershipRequired(long userId, long organizationId)
		throws PortalException, SystemException {

		try {
			checkMembership(
				new long[] {userId}, null, new long[] {organizationId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleAllowed(long userId, long organizationId, long roleId)
		throws PortalException, SystemException {

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, organization.getGroupId(), roleId);

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
			PermissionChecker permissionChecker, long userId,
			long organizationId, long roleId)
		throws PortalException, SystemException {

		if (permissionChecker.isOrganizationOwner(organizationId)) {
			return false;
		}

		Role role = RoleLocalServiceUtil.getRole(roleId);

		String roleName = role.getName();

		if (!roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) &&
			!roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

			return false;
		}

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);

		Group group = organization.getGroup();

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, group.getGroupId(), role.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleRequired(long userId, long organizationId, long roleId)
		throws PortalException, SystemException {

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, organization.getGroupId(), roleId);

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
		List<UserGroupRole> removeUserGroupRoles) {
	}

	@Override
	public void verifyPolicy() throws PortalException, SystemException {
		ActionableDynamicQuery organizationActionableDynamicQuery =
			new OrganizationActionableDynamicQuery() {

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				Organization organization = (Organization)object;

				verifyPolicy(organization);

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
					organization.getGroupId());

				userGroupRoleActionableDynamicQuery.performActions();
			}

		};

		organizationActionableDynamicQuery.performActions();
	}

	@Override
	public void verifyPolicy(Organization organization)
		throws PortalException, SystemException {

		verifyPolicy(organization, null, null, null, null);
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