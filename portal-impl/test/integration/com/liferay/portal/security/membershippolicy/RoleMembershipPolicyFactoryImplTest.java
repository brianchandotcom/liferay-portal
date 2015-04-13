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
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.membershippolicy.bundle.rolemembershippolicyfactoryimpl.TestRoleMembershipPolicy;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import java.io.Serializable;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class RoleMembershipPolicyFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.rolemembershippolicyfactoryimpl"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testCheckRoles() {
		_atomicState.reset();

		long[] testData = {1, 2, 3};

		try {
			RoleMembershipPolicyUtil.checkRoles(testData, testData, testData);
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetRoleMembershipPolicy() {
		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		Class<?> clazz = roleMembershipPolicy.getClass();

		Assert.assertEquals(
			TestRoleMembershipPolicy.class.getName(), clazz.getName());
	}

	@Test
	public void testGetRoleMembershipPolicyFactory() {
		RoleMembershipPolicyFactory roleMembershipPolicyFactory =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicyFactory();

		RoleMembershipPolicy roleMembershipPolicy =
			roleMembershipPolicyFactory.getRoleMembershipPolicy();

		Class<?> clazz = roleMembershipPolicy.getClass();

		Assert.assertEquals(
			TestRoleMembershipPolicy.class.getName(), clazz.getName());
	}

	@Test
	public void testIsRoleAllowed() {
		try {
			boolean value = RoleMembershipPolicyUtil.isRoleAllowed(1, 1);

			Assert.assertTrue(value);

			value = RoleMembershipPolicyUtil.isRoleAllowed(2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsRoleRequired() {
		try {
			boolean value = RoleMembershipPolicyUtil.isRoleRequired(1, 1);

			Assert.assertTrue(value);

			value = RoleMembershipPolicyUtil.isRoleRequired(2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testPropagateRoles() {
		_atomicState.reset();

		long[] testData = {1, 2, 3};

		try {
			RoleMembershipPolicyUtil.propagateRoles(
				testData, testData, testData);
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
			RoleMembershipPolicyUtil.verifyPolicy(new RoleImpl());
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
			RoleMembershipPolicyUtil.verifyPolicy(
				new RoleImpl(), new RoleImpl(),
				new HashMap<String, Serializable>());
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}