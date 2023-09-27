/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membership.policy.role.samples;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.membershippolicy.BaseRoleMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.kernel.security.membershippolicy.RoleMembershipPolicy;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.security.membership.policy.role.BaseRoleMembershipPolicyTestCase;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "service.ranking:Integer=1", service = RoleMembershipPolicy.class
)
public class TestRoleMembershipPolicy extends BaseRoleMembershipPolicy {

	@Override
	public void checkRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException {

		for (long forbiddenRoleId :
				BaseRoleMembershipPolicyTestCase.getForbiddenRoleIds()) {

			if (forbiddenRoleId == 0) {
				continue;
			}

			if (ArrayUtil.contains(addRoleIds, forbiddenRoleId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.ROLE_MEMBERSHIP_NOT_ALLOWED);
			}
		}

		for (long requiredRoleId :
				BaseRoleMembershipPolicyTestCase.getRequiredRoleIds()) {

			if (requiredRoleId == 0) {
				continue;
			}

			if (ArrayUtil.contains(removeRoleIds, requiredRoleId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.ROLE_MEMBERSHIP_REQUIRED);
			}
		}
	}

	@Override
	public void propagateRoles(
		long[] userIds, long[] addRoleIds, long[] removeRoleIds) {

		BaseRoleMembershipPolicyTestCase.setPropagateRoles(true);
	}

	@Override
	public void verifyPolicy() {
		BaseRoleMembershipPolicyTestCase.setVerify(true);
	}

	@Override
	public void verifyPolicy(Role role) {
		verifyPolicy();
	}

	@Override
	public void verifyPolicy(
		Role role, Role oldRole,
		Map<String, Serializable> oldExpandoAttributes) {

		Assert.assertNotNull(role);
		Assert.assertNotNull(oldRole);
		Assert.assertNotNull(oldExpandoAttributes);

		verifyPolicy(role);
	}

}