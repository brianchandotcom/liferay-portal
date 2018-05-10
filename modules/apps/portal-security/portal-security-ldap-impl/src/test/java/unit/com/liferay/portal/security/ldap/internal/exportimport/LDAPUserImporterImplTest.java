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

package com.liferay.portal.security.ldap.internal.exportimport;

import javax.naming.InvalidNameException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jorge Díaz
 */
public class LDAPUserImporterImplTest {

	@Test
	public void testEscapeValue() {
		Assert.assertEquals("test\\\\ test", escapeValue("test\\ test"));
		Assert.assertEquals("test\\\\\"test", escapeValue("test\\\"test"));
		Assert.assertEquals("test\\\\#test", escapeValue("test\\#test"));
		Assert.assertEquals("test\\\\+test", escapeValue("test\\+test"));
		Assert.assertEquals("test\\\\,test", escapeValue("test\\,test"));
		Assert.assertEquals("test\\\\;test", escapeValue("test\\;test"));
		Assert.assertEquals("test\\\\<test", escapeValue("test\\<test"));
		Assert.assertEquals("test\\\\=test", escapeValue("test\\=test"));
		Assert.assertEquals("test\\\\>test", escapeValue("test\\>test"));
		Assert.assertEquals("test\\\\\\\\test", escapeValue("test\\\\test"));
	}

	protected String escapeValue(String query) {
		return _ldapUserImporterImpl.escapeValue(query);
	}

	protected String normalizeLdapName(String name)
		throws InvalidNameException {

		return _ldapUserImporterImpl.normalizeLdapName(name);
	}

	private static final LDAPUserImporterImpl _ldapUserImporterImpl =
		new LDAPUserImporterImpl();

}