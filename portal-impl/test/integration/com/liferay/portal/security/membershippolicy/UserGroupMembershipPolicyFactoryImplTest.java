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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.security.membershippolicy.bundle.usergroupmembershippolicyfactoryimpl.TestUserGroupMembershipPolicy;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
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
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
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
	public void testCheckMembership() {
		_atomicState.reset();

		long[] testData = {1, 2, 3};

		try {
			UserGroupMembershipPolicyUtil.checkMembership(
				testData, testData, testData);
		}
		catch (PortalException e) {
			Assert.fail();
		}

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
	public void testIsMembershipAllowed() {
		try {
			boolean value = UserGroupMembershipPolicyUtil.isMembershipAllowed(
				1, 1);

			Assert.assertTrue(value);

			value = UserGroupMembershipPolicyUtil.isMembershipAllowed(2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsMembershipRequired() {
		try {
			boolean value = UserGroupMembershipPolicyUtil.isMembershipRequired(
				1, 1);

			Assert.assertTrue(value);

			value = UserGroupMembershipPolicyUtil.isMembershipRequired(2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testPropagateMembership() {
		_atomicState.reset();

		long[] testData = {1, 2, 3};

		try {
			UserGroupMembershipPolicyUtil.propagateMembership(
				testData, testData, testData);
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy1() {
		_atomicState.reset();

		try {
			UserGroupMembershipPolicyUtil.verifyPolicy();
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy2() {
		_atomicState.reset();

		try {
			UserGroupMembershipPolicyUtil.verifyPolicy(null);
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy3() {
		_atomicState.reset();

		try {
			UserGroupMembershipPolicyUtil.verifyPolicy(null, null, null);
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}