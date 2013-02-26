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
public class SiteMembershipPolicyUtil {

	public static void checkAddMembership(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.checkAddMembership(userIds, groupIds);
	}

	public static void checkAddRoles(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.checkAddRoles(userIds, groupIds, roleIds);
	}

	public static void checkRemoveMembership(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.checkRemoveMembership(userIds, groupIds);
	}

	public static void checkRemoveRoles(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.checkRemoveRoles(userIds, groupIds, roleIds);
	}

	public static boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		return siteMembershipPolicy.isMembershipAllowed(userId, groupId);
	}

	public static boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		return siteMembershipPolicy.isMembershipProtected(
			permissionChecker, userId, groupId);
	}

	public static boolean isMembershipRequired(long userId, long groupId)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		return siteMembershipPolicy.isMembershipRequired(userId, groupId);
	}

	public static boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		return siteMembershipPolicy.isRoleAllowed(userId, groupId, roleId);
	}

	public static boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		return siteMembershipPolicy.isRoleProtected(
			permissionChecker, userId, groupId, roleId);
	}

	public static boolean isRoleRequired(long userId, long groupId, long roleId)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		return siteMembershipPolicy.isRoleRequired(userId, groupId, roleId);
	}

	public static void propagateAddMembership(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		for (long groupId : groupIds) {
			siteMembershipPolicy.propagateAddMembership(userIds, groupId);
		}
	}

	public static void propagateAddRoles(List<UserGroupRole> userGroupRoles)
		throws PortalException, SystemException {

		for (UserGroupRole userGroupRole : userGroupRoles) {
			if (userGroupRole.getRole().getType() != RoleConstants.TYPE_SITE) {
				userGroupRoles.remove(userGroupRole);
			}
		}

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.propagateAddRoles(userGroupRoles);
	}

	public static void propagateRemoveMembership(
			long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		for (long groupId : groupIds) {
			siteMembershipPolicy.propagateRemoveMembership(userIds, groupId);
		}
	}

	public static void propagateRemoveRoles(
			long userId, long groupId, long roleId)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.propagateRemoveRoles(userId, groupId, roleId);
	}

	public static void verifyPolicy() throws PortalException, SystemException {
		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.verifyPolicy();
	}

	public static void verifyPolicy(Group group)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.verifyPolicy(group);
	}

	public static void verifyPolicy(Role role)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.verifyPolicy(role);
	}

	public static void verifyUpdatePolicy(
			Group group, Group oldGroup,
			List<AssetCategory> oldAssetCatergories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.verifyUpdatePolicy(
			group, oldGroup, oldAssetCatergories, oldAssetTags,
			oldExpandoAttributes);
	}

	public static void verifyUpdatePolicy(
			Group group, Group oldGroup, String oldTypeSettings)
		throws PortalException, SystemException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getMembershipPolicy();

		siteMembershipPolicy.verifyUpdatePolicy(
			group, oldGroup, oldTypeSettings);
	}

}