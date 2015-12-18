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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
public class PortalLDAPUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.portalldaputil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testGetContex2t() throws Exception {
		_atomicState.reset();

		PortalLDAPUtil.getContext(1, "providerURL", "principal", "credentials");

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetContext() throws Exception {
		_atomicState.reset();

		PortalLDAPUtil.getContext(1, 1);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetGroup() throws Exception {
		_atomicState.reset();

		PortalLDAPUtil.getGroup(1, 1, "groupName");

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetGroupAttributes1() throws Exception {
		_atomicState.reset();

		PortalLDAPUtil.getGroupAttributes(1, 1, null, "fullDistinguishedName");

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetGroupAttributes2() throws Exception {
		_atomicState.reset();

		PortalLDAPUtil.getGroupAttributes(
			1, 1, null, "fullDistinguishedName", false);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetGroups1() throws Exception {
		byte[] bytes = PortalLDAPUtil.getGroups(
			1, 1, null, new byte[1], 1, null);

		Assert.assertEquals(bytes.length, 1);
	}

	@Test
	public void testGetGroups2() throws Exception {
		byte[] bytes = PortalLDAPUtil.getGroups(
			2, null, new byte[2], 2, "baseDN", "groupFilter", null);

		Assert.assertEquals(bytes.length, 2);
	}

	@Test
	public void testGetGroups3() throws Exception {
		byte[] bytes = PortalLDAPUtil.getGroups(
			3, 3, null, new byte[3], 3, new String[3], null);

		Assert.assertEquals(bytes.length, 3);
	}

	@Test
	public void testGetGroups4() throws Exception {
		byte[] bytes = PortalLDAPUtil.getGroups(
			4, null, new byte[4], 4, "baseDN", "groupFilter", new String[4],
			null);

		Assert.assertEquals(bytes.length, 4);
	}

	@Test
	public void testGetGroupsDN() throws Exception {
		String groupsDN = PortalLDAPUtil.getGroupsDN(1, 1);

		Assert.assertEquals("1:1", groupsDN);
	}

	@Test
	public void testGetLdapServerId() throws Exception {
		long ldapServiceId = PortalLDAPUtil.getLdapServerId(
			1, "screeName", "emailAddress");

		Assert.assertEquals(1234567890, ldapServiceId);
	}

	@Test
	public void testGetMultivaluedAttribute() throws Exception {
		_atomicState.reset();

		PortalLDAPUtil.getMultivaluedAttribute(
			1, null, "baseDN", "filter", null);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetNameInNamespace() throws Exception {
		String name = PortalLDAPUtil.getNameInNamespace(1, 1, null);

		Assert.assertEquals("1:1", name);
	}

	@Test
	public void testGetUser() throws Exception {
		_atomicState.reset();

		PortalLDAPUtil.getUser(1, 1, "screenName", "emailAddress");

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetUser2() throws Exception {
		_atomicState.reset();

		PortalLDAPUtil.getUser(1, 1, "screenName", "emailAddress", false);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetUsers1() throws Exception {
		byte[] bytes = PortalLDAPUtil.getUsers(
			1, 1, null, new byte[1], 1, null);

		Assert.assertEquals(bytes.length, 1);
	}

	@Test
	public void testGetUsers2() throws Exception {
		byte[] bytes = PortalLDAPUtil.getUsers(
			2, null, new byte[2], 2, "baseDN", "userFilter", null);

		Assert.assertEquals(bytes.length, 2);
	}

	@Test
	public void testGetUsers3() throws Exception {
		byte[] bytes = PortalLDAPUtil.getUsers(
			3, 3, null, new byte[3], 3, new String[3], null);

		Assert.assertEquals(bytes.length, 3);
	}

	@Test
	public void testGetUsers4() throws Exception {
		byte[] bytes = PortalLDAPUtil.getUsers(
			4, null, new byte[4], 4, "baseDN", "userFilter", new String[4],
			null);

		Assert.assertEquals(bytes.length, 4);
	}

	@Test
	public void testGetUsersDN() throws Exception {
		String usersDN = PortalLDAPUtil.getUsersDN(1, 1);

		Assert.assertEquals("1:1", usersDN);
	}

	@Test
	public void testHasUser() throws Exception {
		Assert.assertTrue(
			PortalLDAPUtil.hasUser(1, 1, "test", "test@liferay-test.com"));
		Assert.assertFalse(
			PortalLDAPUtil.hasUser(2, 1, "test", "test@liferay-test.com"));
	}

	@Test
	public void testIsGroupMember() throws Exception {
		Assert.assertTrue(
			PortalLDAPUtil.isGroupMember(1, 1, "testGroup", "testUser"));
		Assert.assertFalse(
			PortalLDAPUtil.isGroupMember(2, 1, "testGroup", "testUser"));
	}

	@Test
	public void testIsUserGroupMember() throws Exception {
		Assert.assertTrue(
			PortalLDAPUtil.isUserGroupMember(1, 1, "testGroup", "testUser"));
		Assert.assertFalse(
			PortalLDAPUtil.isUserGroupMember(2, 1, "testGroup", "testUser"));
	}

	@Test
	public void testSearchLDAP() throws Exception {
		byte[] bytes = PortalLDAPUtil.searchLDAP(
			1, null, new byte[1], 1, "baseDN", "filter", new String[1], null);

		Assert.assertEquals(bytes.length, 1);
	}

	private static AtomicState _atomicState;

}