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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;

import java.util.Collections;
import java.util.Set;

/**
 * @author Sergio González
 */
public class DefaultMembershipPolicy implements MembershipPolicy {

	public Set<Group> getForbiddenGroups(User user) {
		return Collections.emptySet();
	}

	public Set<Organization> getForbiddenOrganizations(User user) {
		return Collections.emptySet();
	}

	public Set<Role> getForbiddenRoles(Group group, User user) {
		return Collections.emptySet();
	}

	public Set<Role> getForbiddenRoles(Organization organization, User user) {
		return Collections.emptySet();
	}

	public Set<Role> getForbiddenRoles(User user) {
		return Collections.emptySet();
	}

	public Set<UserGroup> getForbiddenUserGroups(User user) {
		return Collections.emptySet();
	}

	public Set<Group> getMandatoryGroups(User user) {
		return Collections.emptySet();
	}

	public Set<Organization> getMandatoryOrganizations(User user) {
		return Collections.emptySet();
	}

	public Set<Role> getMandatoryRoles(Group group, User user) {
		return Collections.emptySet();
	}

	public Set<Role> getMandatoryRoles(Organization organization, User user) {
		return Collections.emptySet();
	}

	public Set<Role> getMandatoryRoles(User user) {
		return Collections.emptySet();
	}

	public Set<UserGroup> getMandatoryUserGroups(User user) {
		return Collections.emptySet();
	}

	public boolean isApplicableUser(User user) {
		return false;
	}

	public boolean isMembershipAllowed(Group group, Role role, User user) {
		return true;
	}

	public boolean isMembershipAllowed(Group group, User user) {
		return true;
	}

	public boolean isMembershipAllowed(
		Organization organization, Role role, User user) {

		return true;
	}

	public boolean isMembershipAllowed(Organization organization, User user) {
		return true;
	}

	public boolean isMembershipAllowed(Role role, User user) {
		return true;
	}

	public boolean isMembershipAllowed(UserGroup userGroup, User user) {
		return true;
	}

	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, Group group, Role role,
			User user)
		throws PortalException, SystemException {

		if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(
					group.getClassPK());

			return isMembershipProtected(
				permissionChecker, organization, role, user);
		}

		if (permissionChecker.isGroupOwner(group.getGroupId())) {
			return false;
		}

		String roleName = role.getName();

		if (!roleName.equals(RoleConstants.SITE_ADMINISTRATOR) &&
			!roleName.equals(RoleConstants.SITE_OWNER)) {

			return false;
		}

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(), role.getRoleId())) {

			return true;
		}

		return false;
	}

	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, Group group, User user)
		throws PortalException, SystemException {

		if (permissionChecker.isGroupOwner(group.getGroupId())) {
			return false;
		}

		Role siteAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(),
				siteAdministratorRole.getRoleId())) {

			return true;
		}

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_OWNER);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), group.getGroupId(),
				siteOwnerRole.getRoleId())) {

			return true;
		}

		return false;
	}

	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, Organization organization,
			Role role, User user)
		throws SystemException {

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

	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, Organization organization,
			User user)
		throws PortalException, SystemException {

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

}