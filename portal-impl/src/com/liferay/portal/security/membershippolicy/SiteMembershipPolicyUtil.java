/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membershippolicy;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicy;
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
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.checkMembership(
			userIds, addGroupIds, removeGroupIds);
	}

	public static void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.checkRoles(
			addUserGroupRoles, removeUserGroupRoles);
	}

	public static boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		return siteMembershipPolicy.isMembershipAllowed(userId, groupId);
	}

	public static boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		return siteMembershipPolicy.isMembershipProtected(
			permissionChecker, userId, groupId);
	}

	public static boolean isMembershipRequired(long userId, long groupId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		return siteMembershipPolicy.isMembershipRequired(userId, groupId);
	}

	public static boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		return siteMembershipPolicy.isRoleAllowed(userId, groupId, roleId);
	}

	public static boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		return siteMembershipPolicy.isRoleProtected(
			permissionChecker, userId, groupId, roleId);
	}

	public static boolean isRoleRequired(long userId, long groupId, long roleId)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		return siteMembershipPolicy.isRoleRequired(userId, groupId, roleId);
	}

	public static void propagateMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.propagateMembership(
			userIds, addGroupIds, removeGroupIds);
	}

	public static void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.propagateRoles(
			addUserGroupRoles, removeUserGroupRoles);
	}

	public static void verifyPolicy() throws PortalException {
		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.verifyPolicy();
	}

	public static void verifyPolicy(Group group) throws PortalException {
		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.verifyPolicy(group);
	}

	public static void verifyPolicy(
			Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.verifyPolicy(
			group, oldGroup, oldAssetCategories, oldAssetTags,
			oldExpandoAttributes, oldTypeSettingsUnicodeProperties);
	}

	public static void verifyPolicy(Role role) throws PortalException {
		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.verifyPolicy(role);
	}

	public static void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException {

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicySnapshot.get();

		siteMembershipPolicy.verifyPolicy(role, oldRole, oldExpandoAttributes);
	}

	private static final Snapshot<SiteMembershipPolicy>
		_siteMembershipPolicySnapshot = new Snapshot<>(
			SiteMembershipPolicyUtil.class, SiteMembershipPolicy.class, null,
			true);

}