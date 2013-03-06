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
 * @author Sergio González
 */
public class RoleMembershipPolicyUtil {

	public static void checkRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.checkRoles(userIds, addRoleIds, removeRoleIds);
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

	public static void propagateRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.propagateRoles(userIds, addRoleIds, removeRoleIds);
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

	public static void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {

		RoleMembershipPolicy membershipPolicy =
			RoleMembershipPolicyFactoryUtil.getMembershipPolicy();

		membershipPolicy.verifyPolicy(role, oldRole, oldExpandoAttributes);
	}

}