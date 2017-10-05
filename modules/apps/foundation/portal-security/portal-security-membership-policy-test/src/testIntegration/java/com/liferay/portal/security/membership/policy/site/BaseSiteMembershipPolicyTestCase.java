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

package com.liferay.portal.security.membership.policy.site;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.security.membership.policy.BaseMembershipPolicyTestCase;

import org.junit.After;

/**
 * @author Roberto Díaz
 * @author Raymond Augé
 */
public abstract class BaseSiteMembershipPolicyTestCase
	extends BaseMembershipPolicyTestCase {

	public static long[] getForbiddenGroupIds() {
		return _forbiddenGroupIds;
	}

	public static long[] getForbiddenRoleIds() {
		return _forbiddenRoleIds;
	}

	public static long[] getRequiredGroupIds() {
		return _requiredGroupIds;
	}

	public static long[] getRequiredRoleIds() {
		return _requiredRoleIds;
	}

	public static long[] getStandardGroupIds() {
		return _standardGroupIds;
	}

	public static long[] getStandardRoleIds() {
		return _standardRoleIds;
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_forbiddenGroupIds = new long[2];
		_forbiddenRoleIds = new long[2];
		_requiredGroupIds = new long[2];
		_requiredRoleIds = new long[2];
		_standardGroupIds = new long[2];
		_standardRoleIds = new long[2];
	}

	protected long[] addForbiddenGroups() throws Exception {
		Group forbiddenGroup1 = GroupTestUtil.addGroup();

		_forbiddenGroupIds[0] = forbiddenGroup1.getGroupId();

		Group forbiddenGroup2 = GroupTestUtil.addGroup();

		_forbiddenGroupIds[1] = forbiddenGroup2.getGroupId();

		return _forbiddenGroupIds;
	}

	protected long[] addForbiddenRoles() throws Exception {
		_forbiddenRoleIds[0] = RoleTestUtil.addGroupRole(group.getGroupId());
		_forbiddenRoleIds[1] = RoleTestUtil.addGroupRole(group.getGroupId());

		return _forbiddenRoleIds;
	}

	protected long[] addRequiredGroups() throws Exception {
		Group requiredGroup1 = GroupTestUtil.addGroup();

		_requiredGroupIds[0] = requiredGroup1.getGroupId();

		Group requiredGroup2 = GroupTestUtil.addGroup();

		_requiredGroupIds[1] = requiredGroup2.getGroupId();

		return _requiredGroupIds;
	}

	protected long[] addRequiredRoles() throws Exception {
		_requiredRoleIds[0] = RoleTestUtil.addGroupRole(group.getGroupId());
		_requiredRoleIds[1] = RoleTestUtil.addGroupRole(group.getGroupId());

		return _requiredRoleIds;
	}

	protected long[] addStandardGroups() throws Exception {
		Group standardGroup1 = GroupTestUtil.addGroup();

		_standardGroupIds[0] = standardGroup1.getGroupId();

		Group standardGroup2 = GroupTestUtil.addGroup();

		_standardGroupIds[1] = standardGroup2.getGroupId();

		return _standardGroupIds;
	}

	protected long[] addStandardRoles() throws Exception {
		_standardRoleIds[0] = RoleTestUtil.addGroupRole(group.getGroupId());
		_standardRoleIds[1] = RoleTestUtil.addGroupRole(group.getGroupId());

		return _standardRoleIds;
	}

	private static long[] _forbiddenGroupIds = new long[2];
	private static long[] _forbiddenRoleIds = new long[2];
	private static long[] _requiredGroupIds = new long[2];
	private static long[] _requiredRoleIds = new long[2];
	private static long[] _standardGroupIds = new long[2];
	private static long[] _standardRoleIds = new long[2];

}