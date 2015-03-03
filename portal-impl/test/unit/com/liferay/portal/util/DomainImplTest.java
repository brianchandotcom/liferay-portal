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

package com.liferay.portal.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yuxing Wu
 */
public class DomainImplTest {

	@Test
	public void testGetDomain() {
		Assert.assertNull(_domainImpl.getDomain(null));

		Assert.assertEquals("127.0.0.1", _domainImpl.getDomain("127.0.0.1"));

		Assert.assertNull(_domainImpl.getDomain("com"));

		Assert.assertEquals(
			".liferay.com", _domainImpl.getDomain("liferay.com"));

		Assert.assertEquals(
			".liferay.com", _domainImpl.getDomain("www.liferay.com"));

		Assert.assertEquals(
			".cdn.liferay.com", _domainImpl.getDomain("www.cdn.liferay.com"));

		Assert.assertEquals(
			".liferay.qld.gov.au", _domainImpl.getDomain("liferay.qld.gov.au"));

		Assert.assertEquals(
			".liferay.qld.gov.au",
			_domainImpl.getDomain("www.liferay.qld.gov.au"));

		Assert.assertEquals(
			".cdn.liferay.qld.gov.au",
			_domainImpl.getDomain("www.cdn.liferay.qld.gov.au"));

		Assert.assertNull(_domainImpl.getDomain("localhost"));

		Assert.assertEquals(".test", _domainImpl.getDomain("liferay.test"));

		Assert.assertEquals(
			".liferay.test", _domainImpl.getDomain("www.liferay.test"));
	}

	private final DomainImpl _domainImpl = new DomainImpl();

}