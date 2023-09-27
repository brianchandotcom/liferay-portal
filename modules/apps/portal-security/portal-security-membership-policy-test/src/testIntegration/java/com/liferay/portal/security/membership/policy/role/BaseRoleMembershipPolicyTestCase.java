/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membership.policy.role;

import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.security.membership.policy.BaseMembershipPolicyTestCase;

import org.junit.After;

/**
 * @author Roberto Díaz
 */
public abstract class BaseRoleMembershipPolicyTestCase
	extends BaseMembershipPolicyTestCase {

	public static long[] getForbiddenRoleIds() {
		return _forbiddenRoleIds;
	}

	public static long[] getRequiredRoleIds() {
		return _requiredRoleIds;
	}

	public static long[] getStandardRoleIds() {
		return _standardRoleIds;
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_forbiddenRoleIds = new long[2];
		_requiredRoleIds = new long[2];
		_standardRoleIds = new long[2];
	}

	protected long[] addForbiddenRoles() throws Exception {
		_forbiddenRoleIds[0] = RoleTestUtil.addRegularRole(group.getGroupId());
		_forbiddenRoleIds[1] = RoleTestUtil.addRegularRole(group.getGroupId());

		return _forbiddenRoleIds;
	}

	protected long[] addRequiredRoles() throws Exception {
		_requiredRoleIds[0] = RoleTestUtil.addRegularRole(group.getGroupId());
		_requiredRoleIds[1] = RoleTestUtil.addRegularRole(group.getGroupId());

		return _requiredRoleIds;
	}

	protected long[] addStandardRoles() throws Exception {
		_standardRoleIds[0] = RoleTestUtil.addRegularRole(group.getGroupId());
		_standardRoleIds[1] = RoleTestUtil.addRegularRole(group.getGroupId());

		return _standardRoleIds;
	}

	private static long[] _forbiddenRoleIds = new long[2];
	private static long[] _requiredRoleIds = new long[2];
	private static long[] _standardRoleIds = new long[2];

}