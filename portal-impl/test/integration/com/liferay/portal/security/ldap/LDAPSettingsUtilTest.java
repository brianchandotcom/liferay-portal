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

import java.util.Properties;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
* @author Peter Fellwock
*/
public class LDAPSettingsUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.ldapsettingsutil"));

	@Test
	public void testGetAuthSearchFilter() {
		try {
			Assert.assertEquals(
				"(companyId=1)", LDAPSettingsUtil.getAuthSearchFilter(
					1, 1, "test@liferay-test.com", "test-ip", "test"));
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetContactExpandoMappings() {
		try {
			Properties properties = LDAPSettingsUtil.getContactExpandoMappings(
				1, 1);

			Assert.assertEquals(properties.get("ldapServerId"), "1");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetContactMappings() {
		try {
			Properties properties = LDAPSettingsUtil .getContactMappings(1, 1);

			Assert.assertEquals(properties.get("ldapServerId"), "1");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetGroupMappings() {
		try {
			Properties properties = LDAPSettingsUtil .getGroupMappings(1, 1);

			Assert.assertEquals(properties.get("ldapServerId"), "1");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetPreferredLDAPServerId() {
		long ldapServerId = LDAPSettingsUtil.getPreferredLDAPServerId(
			1, "test");

		Assert.assertEquals(ldapServerId, 1234567890);
	}

	@Test
	public void testGetPropertyPostfix() {
		String postfix = LDAPSettingsUtil.getPropertyPostfix(1);

		Assert.assertEquals(postfix, "liferay.ldap");
	}

	@Test
	public void testGetUserExpandoMappings() {
		try {
			Properties properties = LDAPSettingsUtil.getUserExpandoMappings(
				1, 1);

			Assert.assertEquals(properties.get("ldapServerId"), "1");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetUserMappings() {
		try {
			Properties properties = LDAPSettingsUtil .getUserMappings(1, 1);

			Assert.assertEquals(properties.get("ldapServerId"), "1");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testIsExportEnabled() {
		Assert.assertTrue(LDAPSettingsUtil.isExportEnabled(1));
		Assert.assertFalse(LDAPSettingsUtil.isExportEnabled(2));
	}

	@Test
	public void testIsExportGroupEnabled() {
		Assert.assertTrue(LDAPSettingsUtil.isExportGroupEnabled(1));
		Assert.assertFalse(LDAPSettingsUtil.isExportGroupEnabled(2));
	}

	@Test
	public void testIsImportEnabled() {
		Assert.assertTrue(LDAPSettingsUtil.isImportEnabled(1));
		Assert.assertFalse(LDAPSettingsUtil.isImportEnabled(2));
	}

	@Test
	public void testIsImportOnStartup() {
		Assert.assertTrue(LDAPSettingsUtil.isImportOnStartup(1));
		Assert.assertFalse(LDAPSettingsUtil.isImportOnStartup(2));
	}

	@Test
	public void testIsPasswordPolicyEnabled() {
		Assert.assertTrue(LDAPSettingsUtil.isPasswordPolicyEnabled(1));
		Assert.assertFalse(LDAPSettingsUtil.isPasswordPolicyEnabled(2));
	}

}