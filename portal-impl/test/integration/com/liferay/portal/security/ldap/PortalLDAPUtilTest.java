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
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicStateUtil;

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
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.portalldaputil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicStateUtil = new AtomicStateUtil();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicStateUtil.close();
	}

	@Test
	public void testGetContex2t() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getContext(
				1, "providerURL", "principal", "credentials");
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetContext() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getContext(1, 1);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetGroup() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getGroup(1, 1, "groupName");
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetGroupAttributes1() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getGroupAttributes(
				1, 1, null, "fullDistinguishedName");
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetGroupAttributes2() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getGroupAttributes(
				1, 1, null, "fullDistinguishedName", false);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetGroups1() {
		try {
			byte[] byteArray = PortalLDAPUtil.getGroups(
				1, 1, null, new byte[1], 1, null);

			Assert.assertEquals(byteArray.length, 1);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetGroups2() {
		try {
			byte[] byteArray = PortalLDAPUtil.getGroups(
				2, null, new byte[2], 2, "baseDN", "groupFilter", null);

			Assert.assertEquals(byteArray.length, 2);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetGroups3() {
		try {
			byte[] byteArray = PortalLDAPUtil.getGroups(
				3, 3, null, new byte[3], 3, new String[3], null);

			Assert.assertEquals(byteArray.length, 3);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetGroups4() {
		try {
			byte[] byteArray = PortalLDAPUtil.getGroups(
				4, null, new byte[4], 4, "baseDN", "groupFilter", new String[4],
				null);

			Assert.assertEquals(byteArray.length, 4);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetGroupsDN() {
		try {
			String groupsDN = PortalLDAPUtil.getGroupsDN(1, 1);

			Assert.assertEquals("1:1", groupsDN);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetLdapServerId() {
		try {
			long ldapServiceId = PortalLDAPUtil.getLdapServerId(
				1, "screeName", "emailAddress");

			Assert.assertEquals(1234567890, ldapServiceId);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetMultivaluedAttribute() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getMultivaluedAttribute(
				1, null, "baseDN", "filter", null);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetNameInNamespace() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getNameInNamespace(1, 1, null);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetUser() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getUser(1, 1, "screenName", "emailAddress");
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetUser2() {
		_atomicStateUtil.resetState();

		try {
			PortalLDAPUtil.getUser(1, 1, "screenName", "emailAddress", false);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testGetUsers1() {
		try {
			byte[] byteArray = PortalLDAPUtil.getUsers(
				1, 1, null, new byte[1], 1, null);

			Assert.assertEquals(byteArray.length, 1);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetUsers2() {
		try {
			byte[] byteArray = PortalLDAPUtil.getUsers(
				2, null, new byte[2], 2, "baseDN", "userFilter", null);

			Assert.assertEquals(byteArray.length, 2);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetUsers3() {
		try {
			byte[] byteArray = PortalLDAPUtil.getUsers(
				3, 3, null, new byte[3], 3, new String[3], null);

			Assert.assertEquals(byteArray.length, 3);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetUsers4() {
		try {
			byte[] byteArray = PortalLDAPUtil.getUsers(
				4, null, new byte[4], 4, "baseDN", "userFilter", new String[4],
				null);

			Assert.assertEquals(byteArray.length, 4);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetUsersDN() {
		try {
			String usersDN = PortalLDAPUtil.getUsersDN(1, 1);

			Assert.assertEquals("1:1", usersDN);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testHasUser() {
		try {
			Assert.assertTrue(
				PortalLDAPUtil.hasUser(1, 1, "test", "test@liferay-test.com"));

			Assert.assertFalse(
				PortalLDAPUtil.hasUser(2, 1, "test", "test@liferay-test.com"));
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsGroupMember() {
		try {
			Assert.assertTrue(
				PortalLDAPUtil.isGroupMember(1, 1, "testGroup", "testUser"));

			Assert.assertFalse(
				PortalLDAPUtil.isGroupMember(2, 1, "testGroup", "testUser"));
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsUserGroupMember() {
		try {
			Assert.assertTrue(
				PortalLDAPUtil.isUserGroupMember(
					1, 1, "testGroup", "testUser"));

			Assert.assertFalse(
				PortalLDAPUtil.isUserGroupMember(
					2, 1, "testGroup", "testUser"));
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testSearchLDAP() {
		try {
			byte[] byteArray = PortalLDAPUtil.searchLDAP(
				1, null, new byte[1], 1, "baseDN", "filter", new String[1],
				null);

			Assert.assertEquals(byteArray.length, 1);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	private static AtomicStateUtil _atomicStateUtil;

}