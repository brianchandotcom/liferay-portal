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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.security.membershippolicy.bundle.usergroupmembershippolicyfactoryimpl.TestUserGroupMembershipPolicy;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class UserGroupMembershipPolicyFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule(
				"bundle.usergroupmembershippolicyfactoryimpl"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testCheckMembership() throws Exception {
		_atomicState.reset();

		long[] array = {1, 2, 3};

		UserGroupMembershipPolicyUtil.checkMembership(array, array, array);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetUserGroupMembershipPolicy() {
		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		Class<?> clazz = userGroupMembershipPolicy.getClass();

		Assert.assertEquals(
			TestUserGroupMembershipPolicy.class.getName(), clazz.getName());
	}

	@Test
	public void testGetUserGroupMembershipPolicyFactory() {
		UserGroupMembershipPolicyFactory userGroupMembershipPolicyFactory =
			UserGroupMembershipPolicyFactoryUtil.
				getUserGroupMembershipPolicyFactory();

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			userGroupMembershipPolicyFactory.getUserGroupMembershipPolicy();

		Class<?> clazz = userGroupMembershipPolicy.getClass();

		Assert.assertEquals(
			TestUserGroupMembershipPolicy.class.getName(), clazz.getName());
	}

	@Test
	public void testIsMembershipAllowed() throws Exception {
		Assert.assertTrue(
			UserGroupMembershipPolicyUtil.isMembershipAllowed(1, 1));
		Assert.assertFalse(
			UserGroupMembershipPolicyUtil.isMembershipAllowed(2, 2));
	}

	@Test
	public void testIsMembershipRequired() throws Exception {
		Assert.assertTrue(
			UserGroupMembershipPolicyUtil.isMembershipRequired(1, 1));
		Assert.assertFalse(
			UserGroupMembershipPolicyUtil.isMembershipRequired(2, 2));
	}

	@Test
	public void testPropagateMembership() throws Exception {
		_atomicState.reset();

		long[] array = {1, 2, 3};

		UserGroupMembershipPolicyUtil.propagateMembership(array, array, array);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy1() throws Exception {
		_atomicState.reset();

		UserGroupMembershipPolicyUtil.verifyPolicy();

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy2() throws Exception {
		_atomicState.reset();

		UserGroupMembershipPolicyUtil.verifyPolicy(null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy3() throws Exception {
		_atomicState.reset();

		UserGroupMembershipPolicyUtil.verifyPolicy(null, null, null);

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}