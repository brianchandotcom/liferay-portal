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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class RoleMembershipPolicyUtil {

	public static void checkRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException {

		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		roleMembershipPolicy.checkRoles(userIds, addRoleIds, removeRoleIds);
	}

	public static boolean isRoleAllowed(long userId, long roleId)
		throws PortalException {

		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		return roleMembershipPolicy.isRoleAllowed(userId, roleId);
	}

	public static boolean isRoleRequired(long userId, long roleId)
		throws PortalException {

		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		return roleMembershipPolicy.isRoleRequired(userId, roleId);
	}

	public static void propagateRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException {

		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		roleMembershipPolicy.propagateRoles(userIds, addRoleIds, removeRoleIds);
	}

	public static void verifyPolicy() throws PortalException {
		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		roleMembershipPolicy.verifyPolicy();
	}

	public static void verifyPolicy(Role role) throws PortalException {
		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		roleMembershipPolicy.verifyPolicy(role);
	}

	public static void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException {

		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		roleMembershipPolicy.verifyPolicy(role, oldRole, oldExpandoAttributes);
	}

}