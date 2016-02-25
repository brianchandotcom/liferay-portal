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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class SiteMembershipPolicyUtil {

	public static void checkMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.checkMembership(
			userIds, addGroupIds, removeGroupIds);
	}

	public static void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.checkRoles(
			addUserGroupRoles, removeUserGroupRoles);
	}

	public static boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		return siteMembershipPolicy.isMembershipAllowed(userId, groupId);
	}

	public static boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		return siteMembershipPolicy.isMembershipProtected(
			permissionChecker, userId, groupId);
	}

	public static boolean isMembershipRequired(long userId, long groupId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		return siteMembershipPolicy.isMembershipRequired(userId, groupId);
	}

	public static boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		return siteMembershipPolicy.isRoleAllowed(userId, groupId, roleId);
	}

	public static boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		return siteMembershipPolicy.isRoleProtected(
			permissionChecker, userId, groupId, roleId);
	}

	public static boolean isRoleRequired(long userId, long groupId, long roleId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		return siteMembershipPolicy.isRoleRequired(userId, groupId, roleId);
	}

	public static void propagateMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.propagateMembership(
			userIds, addGroupIds, removeGroupIds);
	}

	public static void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.propagateRoles(
			addUserGroupRoles, removeUserGroupRoles);
	}

	public static void verifyPolicy() throws PortalException {
		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.verifyPolicy();
	}

	public static void verifyPolicy(Group group) throws PortalException {
		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.verifyPolicy(group);
	}

	public static void verifyPolicy(
			Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes,
			UnicodeProperties oldTypeSettingsProperties)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.verifyPolicy(
			group, oldGroup, oldAssetCategories, oldAssetTags,
			oldExpandoAttributes, oldTypeSettingsProperties);
	}

	public static void verifyPolicy(Role role) throws PortalException {
		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.verifyPolicy(role);
	}

	public static void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.verifyPolicy(role, oldRole, oldExpandoAttributes);
	}

}