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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.membershippolicy.samples.TestUserGroupMembershipPolicy;
import com.liferay.portal.util.test.UserGroupTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

/**
 * @author Roberto Díaz
 */
public abstract class BaseUserGroupMembershipPolicyTestCase
	extends BaseMembershipPolicyTestCase {

	public static long[] getForbiddenUserGroupIds() {
		return _forbiddenUserGroupIds;
	}

	public static long[] getRequiredUserGroupIds() {
		return _requiredUserGroupIds;
	}

	public static long[] getStandardUserGroupIds() {
		return _standardUserGroupIds;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1);

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			UserGroupMembershipPolicy.class,
			new TestUserGroupMembershipPolicy(), properties);

		serviceRegistrations.add(serviceRegistration);

		userGroup = UserGroupTestUtil.addUserGroup();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_forbiddenUserGroupIds = new long[2];
		_requiredUserGroupIds = new long[2];
		_standardUserGroupIds = new long[2];
	}

	protected long[] addForbiddenUserGroups() throws Exception {
		UserGroup forbiddenUserGroup1 = UserGroupTestUtil.addUserGroup();

		_forbiddenUserGroupIds[0] = forbiddenUserGroup1.getUserGroupId();

		UserGroup forbiddenUserGroup2 = UserGroupTestUtil.addUserGroup();

		_forbiddenUserGroupIds[1] = forbiddenUserGroup2.getUserGroupId();

		return _forbiddenUserGroupIds;
	}

	protected long[] addRequiredUserGroups() throws Exception {
		UserGroup requiredUserGroup1 = UserGroupTestUtil.addUserGroup();

		_requiredUserGroupIds[0] = requiredUserGroup1.getUserGroupId();

		UserGroup requiredUserGroup2 = UserGroupTestUtil.addUserGroup();

		_requiredUserGroupIds[1] = requiredUserGroup2.getUserGroupId();

		return _requiredUserGroupIds;
	}

	protected long[] addStandardUserGroups() throws Exception {
		UserGroup standardUserGroup1 = UserGroupTestUtil.addUserGroup();

		_standardUserGroupIds[0] = standardUserGroup1.getUserGroupId();

		UserGroup standardUserGroup2 = UserGroupTestUtil.addUserGroup();

		_standardUserGroupIds[1] = standardUserGroup2.getUserGroupId();

		return _standardUserGroupIds;
	}

	@DeleteAfterTestRun
	protected UserGroup userGroup;

	private static long[] _forbiddenUserGroupIds = new long[2];
	private static long[] _requiredUserGroupIds = new long[2];
	private static long[] _standardUserGroupIds = new long[2];

}