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
import com.liferay.portal.kernel.model.UserGroup;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class UserGroupMembershipPolicyUtil {

	public static void checkMembership(
			long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds)
		throws PortalException {

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		userGroupMembershipPolicy.checkMembership(
			userIds, addUserGroupIds, removeUserGroupIds);
	}

	public static boolean isMembershipAllowed(long userId, long userGroupId)
		throws PortalException {

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		return userGroupMembershipPolicy.isMembershipAllowed(
			userId, userGroupId);
	}

	public static boolean isMembershipRequired(long userId, long userGroupId)
		throws PortalException {

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		return userGroupMembershipPolicy.isMembershipRequired(
			userId, userGroupId);
	}

	public static void propagateMembership(
			long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds)
		throws PortalException {

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		userGroupMembershipPolicy.propagateMembership(
			userIds, addUserGroupIds, removeUserGroupIds);
	}

	public static void verifyPolicy() throws PortalException {
		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		userGroupMembershipPolicy.verifyPolicy();
	}

	public static void verifyPolicy(UserGroup userGroup)
		throws PortalException {

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		userGroupMembershipPolicy.verifyPolicy(userGroup);
	}

	public static void verifyPolicy(
			UserGroup userGroup, UserGroup oldUserGroup,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException {

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		userGroupMembershipPolicy.verifyPolicy(
			userGroup, oldUserGroup, oldExpandoAttributes);
	}

}