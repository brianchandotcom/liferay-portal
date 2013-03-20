/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.UserTestUtil;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public abstract class BaseSiteMembershipPolicyTestCase {

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

	public static long[] getUserIds() {
		return _userIds;
	}

	public static boolean isPropagateMembership() {
		return _propagateMembership;
	}

	public static boolean isPropagateRoles() {
		return _propagateRoles;
	}

	public static boolean isVerify() {
		return _verify;
	}

	public static void setPropagateMembership(boolean propagateMembership) {
		_propagateMembership = propagateMembership;
	}

	public static void setPropagateRoles(boolean propagateRoles) {
		_propagateRoles = propagateRoles;
	}

	public static void setVerify(boolean verify) {
		_verify = verify;
	}

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		group = GroupTestUtil.addGroup();
	}

	protected long[] addForbiddenGroups() throws Exception {
		Group forbiddenGroup1 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());

		_forbiddenGroupIds[0] = forbiddenGroup1.getGroupId();

		Group forbiddenGroup2 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());

		_forbiddenGroupIds[1] = forbiddenGroup2.getGroupId();

		return _forbiddenGroupIds;
	}

	protected long[] addForbiddenRoles() throws Exception {
		_forbiddenRoleIds[0] = RoleTestUtil.addGroupRole(group.getGroupId());
		_forbiddenRoleIds[1] = RoleTestUtil.addGroupRole(group.getGroupId());

		return _forbiddenRoleIds;
	}

	protected long[] addRequiredGroups() throws Exception {
		Group requiredGroup1 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());

		_requiredGroupIds[0] = requiredGroup1.getGroupId();

		Group requiredGroup2 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());

		_requiredGroupIds[1] = requiredGroup2.getGroupId();

		return _requiredGroupIds;
	}

	protected long[] addRequiredRoles() throws Exception {
		_requiredRoleIds[0] = RoleTestUtil.addGroupRole(group.getGroupId());
		_requiredRoleIds[1] = RoleTestUtil.addGroupRole(group.getGroupId());

		return _requiredRoleIds;
	}

	protected long[] addStandardGroups() throws Exception {
		Group standardGroup1 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());

		_standardGroupIds[0] = standardGroup1.getGroupId();

		Group standardGroup2 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());

		_standardGroupIds[1] = standardGroup2.getGroupId();

		return _standardGroupIds;
	}

	protected long[] addStandardRoles() throws Exception {
		_standardRoleIds[0] = RoleTestUtil.addGroupRole(group.getGroupId());
		_standardRoleIds[1] = RoleTestUtil.addGroupRole(group.getGroupId());

		return _standardRoleIds;
	}

	protected long[] addUsers() throws Exception {
		User user1= UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		_userIds[0] = user1.getUserId();

		User user2 = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		_userIds[1] = user2.getUserId();

		return _userIds;
	}

	protected static Group group;

	private static long[] _forbiddenGroupIds = new long[2];
	private static long[] _forbiddenRoleIds = new long[2];
	private static boolean _propagateMembership;
	private static boolean _propagateRoles;
	private static long[] _requiredGroupIds = new long[2];
	private static long[] _requiredRoleIds = new long[2];
	private static long[] _standardGroupIds = new long[2];
	private static long[] _standardRoleIds = new long[2];
	private static long[] _userIds = new long[2];
	private static boolean _verify;

}