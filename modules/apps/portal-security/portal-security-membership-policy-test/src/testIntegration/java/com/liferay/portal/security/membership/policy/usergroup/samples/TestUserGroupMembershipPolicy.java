/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = "service.ranking:Integer=" + Integer.MAX_VALUE,
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