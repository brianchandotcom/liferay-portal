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

package com.liferay.portal.domainname;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yuxing Wu
 */
public class DomainNameImplTest {

	@Test
	public void testGetCookieDomain() {
		Assert.assertNull(_domainNameImpl.getCookieDomain(null));

		Assert.assertEquals(
			"127.0.0.1", _domainNameImpl.getCookieDomain("127.0.0.1"));

		Assert.assertNull(_domainNameImpl.getCookieDomain("com"));

		Assert.assertEquals(
			".liferay.com", _domainNameImpl.getCookieDomain("liferay.com"));

		Assert.assertEquals(
			".liferay.com", _domainNameImpl.getCookieDomain("www.liferay.com"));

		Assert.assertEquals(
			".cdn.liferay.com",
			_domainNameImpl.getCookieDomain("www.cdn.liferay.com"));

		Assert.assertEquals(
			".liferay.qld.gov.au",
			_domainNameImpl.getCookieDomain("liferay.qld.gov.au"));

		Assert.assertEquals(
			".liferay.qld.gov.au",
			_domainNameImpl.getCookieDomain("www.liferay.qld.gov.au"));

		Assert.assertEquals(
			".cdn.liferay.qld.gov.au",
			_domainNameImpl.getCookieDomain("www.cdn.liferay.qld.gov.au"));

		Assert.assertNull(_domainNameImpl.getCookieDomain("localhost"));

		Assert.assertEquals(
			".liferay.test", _domainNameImpl.getCookieDomain("liferay.test"));

		Assert.assertEquals(
			".liferay.test",
			_domainNameImpl.getCookieDomain("www.liferay.test"));
	}

	private final DomainNameImpl _domainNameImpl = new DomainNameImpl();

}