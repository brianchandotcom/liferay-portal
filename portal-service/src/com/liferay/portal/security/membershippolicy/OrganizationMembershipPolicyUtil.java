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
import com.liferay.portal.model.RoleConstants;
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

	public static void checkAddMembership(
			long[] userIds, long[] organizationIds)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkAddMembership(userIds, organizationIds);
	}

	public static void checkAddRoles(
			long[] userIds, long[] organizationIds, long[] rolesIds)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkAddRoles(userIds, organizationIds, rolesIds);
	}

	public static void checkRemoveMembership(
			long[] userIds, long[] organizationIds)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkRemoveMembership(userIds, organizationIds);
	}

	public static void checkRemoveRoles(
			long[] userIds, long[] organizationIds, long[] rolesIds)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkRemoveRoles(userIds, organizationIds, rolesIds);
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

	public static void propagateAddMembership(
			long[] userIds, long[] organizationIds)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		for (long organizationId : organizationIds) {
			membershipPolicy.propagateAddMembership(userIds, organizationId);
		}
	}

	public static void propagateAddRoles(List<UserGroupRole> userGroupRoles)
		throws PortalException, SystemException {

		for (UserGroupRole userGroupRole : userGroupRoles) {
			if (userGroupRole.getRole().getType() !=
					RoleConstants.TYPE_ORGANIZATION) {

				userGroupRoles.remove(userGroupRole);
			}
		}

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.propagateAddRoles(userGroupRoles);
	}

	public static void propagateRemoveMembership(
			long[] userIds, long[] organizationIds)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		for (long organizationId : organizationIds) {
			membershipPolicy.propagateRemoveMembership(userIds, organizationId);
		}
	}

	public static void propagateRemoveRoles(
			long userId, long organizationId, long roleId)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.propagateRemoveRole(userId, organizationId, roleId);
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

	public static void verifyPolicy(Role role)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy(role);
	}

	public static void verifyUpdatePolicy(
			Organization organization, Organization oldOrganization,
			List<AssetCategory> oldAssetCategories, List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {

		OrganizationMembershipPolicy membershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyUpdatePolicy(
			organization, oldOrganization, oldAssetCategories, oldAssetTags,
			oldExpandoAttributes);
	}

}