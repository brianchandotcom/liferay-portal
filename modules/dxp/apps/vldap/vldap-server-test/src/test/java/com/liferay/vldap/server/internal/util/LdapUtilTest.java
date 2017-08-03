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

package com.liferay.vldap.server.internal.util;

import com.liferay.portal.kernel.model.Company;

import org.apache.directory.api.ldap.model.name.Dn;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Jonathan McCann
 */
public class LdapUtilTest extends PowerMockito {

	@Test
	public void testBuildName() {
		Company company = mock(Company.class);

		when(
			company.getWebId()
		).thenReturn(
			"liferay.com"
		);

		String name = LdapUtil.buildName(null, "Liferay", company);

		Assert.assertEquals("ou=liferay.com,o=Liferay", name);

		name = LdapUtil.buildName("testName", "Liferay", company);

		Assert.assertEquals("cn=testName,ou=liferay.com,o=Liferay", name);

		name = LdapUtil.buildName("testName", "Liferay", company, "Guest");

		Assert.assertEquals(
			"cn=testName,ou=Guest,ou=liferay.com,o=Liferay", name);

		name = LdapUtil.buildName("testName", "Liferay", company, "ou=Guest");

		Assert.assertEquals(
			"cn=testName,ou=Guest,ou=liferay.com,o=Liferay", name);
	}

	@Test
	public void testEscape() {
		String name = LdapUtil.escape("Liferay");

		Assert.assertEquals("Liferay", name);

		name = LdapUtil.escape("o=Liferay");

		Assert.assertEquals("o=Liferay", name);

		name = LdapUtil.escape("Liferay,Test");

		Assert.assertEquals("Liferay\\,Test", name);

		name = LdapUtil.escape("o=Liferay,Test");

		Assert.assertEquals("o=Liferay\\,Test", name);

		name = LdapUtil.escape("o=Liferay\\Test");

		Assert.assertEquals("o=Liferay\\\\Test", name);

		name = LdapUtil.escape("o=Liferay#Test");

		Assert.assertEquals("o=Liferay\\#Test", name);

		name = LdapUtil.escape("o=Liferay+Test");

		Assert.assertEquals("o=Liferay\\+Test", name);

		name = LdapUtil.escape("o=Liferay<Test");

		Assert.assertEquals("o=Liferay\\<Test", name);

		name = LdapUtil.escape("o=Liferay>Test");

		Assert.assertEquals("o=Liferay\\>Test", name);

		name = LdapUtil.escape("o=Liferay;Test");

		Assert.assertEquals("o=Liferay\\;Test", name);

		name = LdapUtil.escape("o=Liferay\"Test");

		Assert.assertEquals("o=Liferay\\\"Test", name);

		name = LdapUtil.escape("o=Liferay=Test");

		Assert.assertEquals("o=Liferay\\=Test", name);
	}

	@Test
	public void testGetRdnType() throws Exception {
		Dn dn = new Dn("cn=test,ou=liferay.com,o=Liferay");

		String rdnType = LdapUtil.getRdnType(dn, -1);

		Assert.assertEquals(null, rdnType);

		rdnType = LdapUtil.getRdnType(dn, 0);

		Assert.assertEquals("o", rdnType);

		rdnType = LdapUtil.getRdnType(dn, 1);

		Assert.assertEquals("ou", rdnType);

		rdnType = LdapUtil.getRdnType(dn, 2);

		Assert.assertEquals("cn", rdnType);

		rdnType = LdapUtil.getRdnType(dn, 3);

		Assert.assertEquals(null, rdnType);
	}

	@Test
	public void testGetRdnValue() throws Exception {
		Dn dn = new Dn("cn=test,ou=liferay.com,o=Liferay");

		String rdnType = LdapUtil.getRdnValue(dn, -1);

		Assert.assertEquals(null, rdnType);

		rdnType = LdapUtil.getRdnValue(dn, 0);

		Assert.assertEquals("Liferay", rdnType);

		rdnType = LdapUtil.getRdnValue(dn, 1);

		Assert.assertEquals("liferay.com", rdnType);

		rdnType = LdapUtil.getRdnValue(dn, 2);

		Assert.assertEquals("test", rdnType);

		rdnType = LdapUtil.getRdnValue(dn, 3);

		Assert.assertEquals(null, rdnType);
	}

}