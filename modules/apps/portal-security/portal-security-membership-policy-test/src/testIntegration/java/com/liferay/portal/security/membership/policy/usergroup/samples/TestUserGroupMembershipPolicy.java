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

package com.liferay.portal.security.membership.policy.usergroup.samples;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.membershippolicy.BaseUserGroupMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicy;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.security.membership.policy.usergroup.BaseUserGroupMembershipPolicyTestCase;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = UserGroupMembershipPolicy.class
)
public class TestUserGroupMembershipPolicy
	extends BaseUserGroupMembershipPolicy {

	@Override
	public void checkMembership(
			long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds)
		throws PortalException {

		for (long forbiddenUserGroupId :
				BaseUserGroupMembershipPolicyTestCase.
					getForbiddenUserGroupIds()) {

			if (forbiddenUserGroupId == 0) {
				continue;
			}

			if (ArrayUtil.contains(addUserGroupIds, forbiddenUserGroupId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.
						USER_GROUP_MEMBERSHIP_NOT_ALLOWED);
			}
		}

		for (long requiredUserGroupId :
				BaseUserGroupMembershipPolicyTestCase.
					getRequiredUserGroupIds()) {

			if (requiredUserGroupId == 0) {
				continue;
			}

			if (ArrayUtil.contains(removeUserGroupIds, requiredUserGroupId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.USER_GROUP_MEMBERSHIP_REQUIRED);
			}
		}
	}

	@Override
	public void propagateMembership(
		long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds) {

		BaseUserGroupMembershipPolicyTestCase.setPropagateMembership(true);
	}

	@Override
	public void verifyPolicy() {
		BaseUserGroupMembershipPolicyTestCase.setVerify(true);
	}

	@Override
	public void verifyPolicy(UserGroup userGroup) {
		verifyPolicy();
	}

	@Override
	public void verifyPolicy(
		UserGroup userGroup, UserGroup oldUserGroup,
		Map<String, Serializable> oldExpandoAttributes) {

		Assert.assertNotNull(userGroup);
		Assert.assertNotNull(oldUserGroup);
		Assert.assertNotNull(oldExpandoAttributes);

		verifyPolicy(userGroup);
	}

}