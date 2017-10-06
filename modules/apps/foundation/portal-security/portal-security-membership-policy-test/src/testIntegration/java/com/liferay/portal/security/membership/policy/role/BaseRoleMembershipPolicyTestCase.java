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