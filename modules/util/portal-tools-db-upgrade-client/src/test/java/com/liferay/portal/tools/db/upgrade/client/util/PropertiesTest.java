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

package com.liferay.portal.tools.db.upgrade.client.util;

import java.io.File;

import java.nio.file.Files;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author David Truong
 */
public class PropertiesTest {

	@Before
	public void setUp() throws Exception {
		_propertiesFile = new File("p.properties");

		_propertiesFile.createNewFile();
	}

	@After
	public void tearDown() throws Exception {
		if (_propertiesFile.exists()) {
			_propertiesFile.delete();
		}
	}

	@Test
	public void testEscapedProperties() throws Exception {
		Properties properties = new Properties();

		properties.setProperty(
			"upgrade.processes.master",
			"com.liferay.portal.upgrade.UpgradeProcess_6_1_1\\," +
				"com.liferay.portal.upgrade.UpgradeProcess_6_2_0");

		String originalValue = properties.getProperty(
			"upgrade.processes.master");

		properties.store(_propertiesFile);

		properties = new Properties();

		properties.load(_propertiesFile);

		Assert.assertEquals(
			originalValue, properties.getProperty("upgrade.processes.master"));
	}

	@Test
	public void testLoadProperties() throws Exception {
		StringBuilder propertiesSB = new StringBuilder();

		propertiesSB.append("index.on.upgrade=false\n");
		propertiesSB.append("upgrade.processes.master=\\\n");
		propertiesSB.append(
			"\t\tcom.liferay.portal.upgrade.UpgradeProcess_6_1_1\\,\\\n");
		propertiesSB.append(
			"\t\tcom.liferay.portal.upgrade.UpgradeProcess_6_2_0\\,\\\n");
		propertiesSB.append(
			"\t\tcom.liferay.portal.upgrade.UpgradeProcess_7_0_0\\,\\\n");
		propertiesSB.append(
			"\t\tcom.liferay.portal.upgrade.UpgradeProcess_7_0_1\\,\\\n");
		propertiesSB.append(
			"\t\tcom.liferay.portal.upgrade.UpgradeProcess_7_0_3\n");

		String propertiesString = propertiesSB.toString();

		Files.write(_propertiesFile.toPath(), propertiesString.getBytes());

		Properties properties = new Properties();

		properties.load(_propertiesFile);

		Assert.assertEquals(
			"false", properties.getProperty("index.on.upgrade"));
	}

	@Test
	public void testWindowsPathProperties() throws Exception {
		Properties properties = new Properties();

		properties.load(_propertiesFile);

		properties.setProperty("liferay.home", "c:\\liferay\\");

		String originalValue = properties.getProperty("liferay.home");

		properties.store(_propertiesFile);

		properties = new Properties();

		properties.load(_propertiesFile);

		String savedValue = properties.getProperty("liferay.home");

		Assert.assertNotEquals(originalValue, savedValue);

		Assert.assertEquals("c:/liferay/", savedValue);
	}

	private static File _propertiesFile;

}