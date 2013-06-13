/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.license.util;

import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import java.io.InputStream;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LicenseUtilTest {

	@Test
	public void testMacAddressAIX() throws Exception {
		InputStream processInputStream = getInputStream("aix.txt");

		Set<String> macAddresses = LicenseUtil.getMacAddresses(
			"aix", processInputStream);

		Assert.assertEquals(macAddresses.size(), 2);
		Assert.assertTrue(macAddresses.contains("66:da:90:6b:f1:17"));
		Assert.assertTrue(macAddresses.contains("66:da:90:6b:f1:18"));
	}

	@Test
	public void testMacAddressUbuntu() throws Exception {
		InputStream processInputStream = getInputStream("ubuntu.txt");

		Set<String> macAddresses = LicenseUtil.getMacAddresses(
			"linux", processInputStream);

		Assert.assertEquals(macAddresses.size(), 2);
		Assert.assertTrue(macAddresses.contains("5c:26:0a:33:b3:d5"));
		Assert.assertTrue(macAddresses.contains("00:24:d7:82:96:f4"));
	}

	@Test
	public void testMacAddressWindows() throws Exception {
		InputStream processInputStream = getInputStream("windows.txt");

		Set<String> macAddresses = LicenseUtil.getMacAddresses(
			"windows", processInputStream);

		Assert.assertEquals(macAddresses.size(), 3);
		Assert.assertTrue(macAddresses.contains("08:00:27:62:4c:9d"));
		Assert.assertTrue(macAddresses.contains("08:00:27:c0:ab:91"));
		Assert.assertTrue(macAddresses.contains("00:ff:b0:3b:1f:e7"));
	}

	protected InputStream getInputStream(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return inputStream;
	}

}