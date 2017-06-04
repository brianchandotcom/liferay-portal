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

package com.liferay.portal.security.membership.policy.usergroup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicyUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.security.membership.policy.usergroup.BaseUserGroupMembershipPolicyTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class UserGroupMembershipPolicyBasicTest
	extends BaseUserGroupMembershipPolicyTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIsMembershipAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] standardUserGroupIds = addStandardUserGroups();

		Assert.assertTrue(
			UserGroupMembershipPolicyUtil.isMembershipAllowed(
				userIds[0], standardUserGroupIds[0]));
	}

	@Test
	public void testIsMembershipNotAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] forbiddenUserGroupIds = addForbiddenUserGroups();

		Assert.assertFalse(
			UserGroupMembershipPolicyUtil.isMembershipAllowed(
				userIds[0], forbiddenUserGroupIds[0]));
	}

	@Test
	public void testIsMembershipNotRequired() throws Exception {
		long[] userIds = addUsers();
		long[] standardUserGroupIds = addStandardUserGroups();

		Assert.assertFalse(
			UserGroupMembershipPolicyUtil.isMembershipRequired(
				userIds[0], standardUserGroupIds[0]));
	}

	@Test
	public void testIsMembershipRequired() throws Exception {
		long[] userIds = addUsers();
		long[] requiredUserGroupIds = addRequiredUserGroups();

		Assert.assertTrue(
			UserGroupMembershipPolicyUtil.isMembershipRequired(
				userIds[0], requiredUserGroupIds[0]));
	}

}