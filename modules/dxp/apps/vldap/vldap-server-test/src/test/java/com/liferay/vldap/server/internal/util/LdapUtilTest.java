/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.util;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.apache.directory.api.ldap.model.name.Dn;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jonathan McCann
 */
public class LdapUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuildName() {
		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getWebId()
		).thenReturn(
			"liferay.com"
		);

		Assert.assertEquals(
			"ou=liferay.com,o=Liferay",
			LdapUtil.buildName(null, "Liferay", company));
		Assert.assertEquals(
			"cn=testName,ou=liferay.com,o=Liferay",
			LdapUtil.buildName("testName", "Liferay", company));
		Assert.assertEquals(
			"cn=testName,ou=Guest,ou=liferay.com,o=Liferay",
			LdapUtil.buildName("testName", "Liferay", company, "Guest"));
		Assert.assertEquals(
			"cn=testName,ou=Guest,ou=liferay.com,o=Liferay",
			LdapUtil.buildName("testName", "Liferay", company, "ou=Guest"));
	}

	@Test
	public void testEscape() {
		Assert.assertEquals("Liferay", LdapUtil.escape("Liferay"));
		Assert.assertEquals("o=Liferay", LdapUtil.escape("o=Liferay"));
		Assert.assertEquals("Liferay\\,Test", LdapUtil.escape("Liferay,Test"));
		Assert.assertEquals(
			"o=Liferay\\,Test", LdapUtil.escape("o=Liferay,Test"));
		Assert.assertEquals(
			"o=Liferay\\\\Test", LdapUtil.escape("o=Liferay\\Test"));
		Assert.assertEquals(
			"o=Liferay\\#Test", LdapUtil.escape("o=Liferay#Test"));
		Assert.assertEquals(
			"o=Liferay\\+Test", LdapUtil.escape("o=Liferay+Test"));
		Assert.assertEquals(
			"o=Liferay\\<Test", LdapUtil.escape("o=Liferay<Test"));
		Assert.assertEquals(
			"o=Liferay\\>Test", LdapUtil.escape("o=Liferay>Test"));
		Assert.assertEquals(
			"o=Liferay\\;Test", LdapUtil.escape("o=Liferay;Test"));
		Assert.assertEquals(
			"o=Liferay\\\"Test", LdapUtil.escape("o=Liferay\"Test"));
		Assert.assertEquals(
			"o=Liferay\\=Test", LdapUtil.escape("o=Liferay=Test"));
	}

	@Test
	public void testGetRdnType() throws Exception {
		Dn dn = new Dn("cn=test,ou=liferay.com,o=Liferay");

		Assert.assertEquals(null, LdapUtil.getRdnType(dn, -1));
		Assert.assertEquals("o", LdapUtil.getRdnType(dn, 0));
		Assert.assertEquals("ou", LdapUtil.getRdnType(dn, 1));
		Assert.assertEquals("cn", LdapUtil.getRdnType(dn, 2));
		Assert.assertEquals(null, LdapUtil.getRdnType(dn, 3));
	}

	@Test
	public void testGetRdnValue() throws Exception {
		Dn dn = new Dn("cn=test,ou=liferay.com,o=Liferay");

		Assert.assertEquals(null, LdapUtil.getRdnValue(dn, -1));
		Assert.assertEquals("Liferay", LdapUtil.getRdnValue(dn, 0));
		Assert.assertEquals("liferay.com", LdapUtil.getRdnValue(dn, 1));
		Assert.assertEquals("test", LdapUtil.getRdnValue(dn, 2));
		Assert.assertEquals(null, LdapUtil.getRdnValue(dn, 3));
	}

}