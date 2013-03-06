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
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class OrganizationMembershipPolicyUtil {

	public static void checkMembership(
			long[] userIds, long[] addOrganizationIds,
			long[] removeOrganizationIds)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkMembership(
			userIds, addOrganizationIds, removeOrganizationIds);
	}

	public static void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkRoles(addUserGroupRoles, removeUserGroupRoles);
	}

	public static boolean isMembershipAllowed(long userId, long organizationId)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipAllowed(userId, organizationId);
	}

	public static boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipProtected(
			permissionChecker, userId, organizationId);
	}

	public static boolean isMembershipRequired(long userId, long organizationId)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipRequired(userId, organizationId);
	}

	public static boolean isRoleAllowed(
			long userId, long organizationId, long roleId)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isRoleAllowed(userId, organizationId, roleId);
	}

	public static boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId, long roleId)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isRoleProtected(
			permissionChecker, userId, organizationId, roleId);
	}

	public static boolean isRoleRequired(
			long userId, long organizationId, long roleId)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isRoleRequired(userId, organizationId, roleId);
	}

	public static void propagateMembership(
			long[] userIds, long[] addOrganizationIds,
			long[] removeOrganizationIds)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.propagateMembership(
			userIds, addOrganizationIds, removeOrganizationIds);
	}

	public static void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.propagateRoles(
			addUserGroupRoles, removeUserGroupRoles);
	}

	public static void verifyPolicy() throws PortalException, SystemException {
		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy();
	}

	public static void verifyPolicy(Organization organization)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy(organization);
	}

	public static void verifyPolicy(
			Organization organization, Organization oldOrganization,
			List<AssetCategory> oldAssetCategories, List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy(
			organization, oldOrganization, oldAssetCategories, oldAssetTags,
			oldExpandoAttributes);
	}

	public static void verifyPolicy(Role role)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy(role);
	}

	public static void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy(role, oldRole, oldExpandoAttributes);
	}

}