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
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyFactory;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyFactoryUtil;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.membershippolicy.bundle.sitemembershippolicyfactoryimpl.TestGroup;
import com.liferay.portal.security.membershippolicy.bundle.sitemembershippolicyfactoryimpl.TestSiteMembershipPolicy;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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
			new LiferayIntegrationTestRule(),
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

		long[] array = {1, 2, 3};

		try {
			SiteMembershipPolicyUtil.checkMembership(array, array, array);
		}
		catch (PortalException pe) {
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
		catch (PortalException pe) {
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
	public void testIsMembershipAllowed() throws Exception {
		Assert.assertTrue(SiteMembershipPolicyUtil.isMembershipAllowed(1, 1));
		Assert.assertFalse(SiteMembershipPolicyUtil.isMembershipAllowed(2, 2));
	}

	@Test
	public void testisMembershipProtected() throws Exception {
		Assert.assertTrue(
			SiteMembershipPolicyUtil.isMembershipProtected(null, 1, 1));
		Assert.assertFalse(
			SiteMembershipPolicyUtil.isMembershipProtected(null, 2, 2));
	}

	@Test
	public void testIsMembershipRequired() throws Exception {
		Assert.assertTrue(SiteMembershipPolicyUtil.isMembershipRequired(1, 1));
		Assert.assertFalse(SiteMembershipPolicyUtil.isMembershipRequired(2, 2));
	}

	@Test
	public void testIsRoleAllowed() throws Exception {
		Assert.assertTrue(SiteMembershipPolicyUtil.isRoleAllowed(1, 1, 1));
		Assert.assertFalse(SiteMembershipPolicyUtil.isRoleAllowed(2, 2, 2));
	}

	@Test
	public void testIsRoleProtected() throws Exception {
		Assert.assertTrue(
			SiteMembershipPolicyUtil.isRoleProtected(null, 1, 1, 1));
		Assert.assertFalse(
			SiteMembershipPolicyUtil.isRoleProtected(null, 2, 2, 2));
	}

	@Test
	public void testIsRoleRequired() throws Exception {
		Assert.assertTrue(SiteMembershipPolicyUtil.isRoleRequired(1, 1, 1));
		Assert.assertFalse(SiteMembershipPolicyUtil.isRoleRequired(2, 2, 2));
	}

	@Test
	public void testPropagateMembership() throws Exception {
		_atomicState.reset();

		long[] array = {1, 2, 3};

		SiteMembershipPolicyUtil.propagateMembership(array, array, array);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testPropagateRoles() throws Exception {
		_atomicState.reset();

		SiteMembershipPolicyUtil.propagateRoles(null, null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy1() throws Exception {
		_atomicState.reset();

		SiteMembershipPolicyUtil.verifyPolicy();

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy2() throws Exception {
		_atomicState.reset();

		SiteMembershipPolicyUtil.verifyPolicy(new TestGroup());

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy3() throws Exception {
		_atomicState.reset();

		SiteMembershipPolicyUtil.verifyPolicy(
			new TestGroup(), new TestGroup(), new ArrayList<AssetCategory>(),
			new ArrayList<AssetTag>(), new HashMap<String, Serializable>(),
			new UnicodeProperties());

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy4() throws Exception {
		_atomicState.reset();

		SiteMembershipPolicyUtil.verifyPolicy(new RoleImpl());

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testVerifyPolicy5() throws Exception {
		_atomicState.reset();

		SiteMembershipPolicyUtil.verifyPolicy(
			new RoleImpl(), new RoleImpl(),
			new HashMap<String, Serializable>());

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}