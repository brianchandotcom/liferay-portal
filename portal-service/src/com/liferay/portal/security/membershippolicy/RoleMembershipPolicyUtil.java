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
import com.liferay.portal.model.Role;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Roberto Díaz
 */

public class RoleMembershipPolicyUtil {

	public static void checkAddRoles(long[] userIds, long[] rolesIds)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkAddRoles(userIds, rolesIds);
	}

	public static void checkRemoveRoles(long[] userIds, long[] rolesIds)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkRemoveRoles(userIds, rolesIds);
	}

	public static boolean isRoleAllowed(long userId, long roleId)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isRoleAllowed(userId, roleId);
	}

	public static boolean isRoleRequired(long userId, long roleId)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isRoleRequired(userId, roleId);
	}

	public static void propagateAddRoles(long[] userIds, long[] roleIds)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		for (long roleId : roleIds) {
			membershipPolicy.propagateAddRoles(userIds, roleId);
		}
	}

	public static void propagateRemoveRoles(long[] userIds, long[] roleIds)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		for (long roleId : roleIds) {
			membershipPolicy.propagateRemoveRoles(userIds, roleId);
		}
	}

	public static void verifyPolicy() throws PortalException, SystemException {
		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy();
	}

	public static void verifyPolicy(Role role)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy(role);
	}

	public static void verifyUpdatePolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyUpdatePolicy(
			role, oldRole, oldExpandoAttributes);
	}

}