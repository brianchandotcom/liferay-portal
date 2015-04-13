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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.membershippolicy.bundle.sitemembershippolicyfactoryimpl.TestGroup;
import com.liferay.portal.security.membershippolicy.bundle.sitemembershippolicyfactoryimpl.TestSiteMembershipPolicy;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.ArrayList;
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
public class SiteMembershipPolicyFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.sitemembershippolicyfactoryimpl"));

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
			SiteMembershipPolicyUtil.checkMembership(
				testData, testData, testData);
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testCheckRoles() {
		_atomicState.reset();

		try {
			SiteMembershipPolicyUtil.checkRoles(null, null);
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetSiteMembershipPolicy() {
		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		Class<?> clazz = siteMembershipPolicy.getClass();

		Assert.assertEquals(
			TestSiteMembershipPolicy.class.getName(), clazz.getName());
	}

	@Test
	public void testGetSiteMembershipPolicyFactory() {
		SiteMembershipPolicyFactory siteMembershipPolicyFactory =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicyFactory();

		SiteMembershipPolicy siteMembershipPolicy =
			siteMembershipPolicyFactory.getSiteMembershipPolicy();

		Class<?> clazz = siteMembershipPolicy.getClass();

		Assert.assertEquals(
			TestSiteMembershipPolicy.class.getName(), clazz.getName());
	}

	@Test
	public void testIsMembershipAllowed() {
		try {
			boolean value = SiteMembershipPolicyUtil.isMembershipAllowed(1, 1);

			Assert.assertTrue(value);

			value = SiteMembershipPolicyUtil.isMembershipAllowed(2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testisMembershipProtected() {
		try {
			boolean value = SiteMembershipPolicyUtil.isMembershipProtected(
				null, 1, 1);

			Assert.assertTrue(value);

			value = SiteMembershipPolicyUtil.isMembershipProtected(null, 2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsMembershipRequired() {
		try {
			boolean value = SiteMembershipPolicyUtil.isMembershipRequired(1, 1);

			Assert.assertTrue(value);

			value = SiteMembershipPolicyUtil.isMembershipRequired(2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsRoleAllowed() {
		try {
			boolean value = SiteMembershipPolicyUtil.isRoleAllowed(1, 1, 1);

			Assert.assertTrue(value);

			value = SiteMembershipPolicyUtil.isRoleAllowed(2, 2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsRoleProtected() {
		try {
			boolean value = SiteMembershipPolicyUtil.isRoleProtected(
				null, 1, 1, 1);

			Assert.assertTrue(value);

			value = SiteMembershipPolicyUtil.isRoleProtected(null, 2, 2, 2);

			Assert.assertFalse(value);
		}
		catch (PortalException e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsRoleRequired() {
		try {
			boolean value = SiteMembershipPolicyUtil.isRoleRequired(1, 1, 1);

			Assert.assertTrue(value);

			value = SiteMembershipPolicyUtil.isRoleRequired(2, 2, 2);

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
			SiteMembershipPolicyUtil.propagateMembership(
				testData, testData, testData);
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testPropagateRolesp() {
		_atomicState.reset();

		try {
			SiteMembershipPolicyUtil.propagateRoles(null, null);
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
			SiteMembershipPolicyUtil.verifyPolicy();
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
			SiteMembershipPolicyUtil.verifyPolicy(new TestGroup());
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
			SiteMembershipPolicyUtil.verifyPolicy(null, null, null);
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy4() {
		_atomicState.reset();

		try {
			SiteMembershipPolicyUtil.verifyPolicy(
				new TestGroup(), new TestGroup(),
				new ArrayList<AssetCategory>(), new ArrayList<AssetTag>(),
				new HashMap<String, Serializable>(), new UnicodeProperties());
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy5() {
		_atomicState.reset();

		try {
			SiteMembershipPolicyUtil.verifyPolicy(new RoleImpl());
		}
		catch (PortalException e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy6() {
		_atomicState.reset();

		try {
			SiteMembershipPolicyUtil.verifyPolicy(
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