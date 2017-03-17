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

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class PropertiesTest {

	@Test
	public void testEscapedProperties() throws Exception {
		File propertiesFile = temporaryFolder.newFile("test.properties");

		Properties properties = new Properties();

		properties.setProperty(
			"upgrade.processes.master",
			"com.liferay.portal.upgrade.UpgradeProcess_6_1_1\\," +
				"com.liferay.portal.upgrade.UpgradeProcess_6_2_0");

		String expectedValue = properties.getProperty(
			"upgrade.processes.master");

		properties.store(propertiesFile);

		properties = new Properties();

		properties.load(propertiesFile);

		Assert.assertEquals(
			expectedValue, properties.getProperty("upgrade.processes.master"));
	}

	@Test
	public void testLoadProperties() throws Exception {
		Properties properties = new Properties();

		properties.load(
			PropertiesTest.class.getResourceAsStream(
				"dependencies/test.properties"));

		Assert.assertEquals(
			"false", properties.getProperty("index.on.upgrade"));

		StringBuilder sb = new StringBuilder();

		sb.append("\\\n");
		sb.append("com.liferay.portal.upgrade.UpgradeProcess_6_1_1\\,\\\n");
		sb.append("com.liferay.portal.upgrade.UpgradeProcess_6_2_0\\,\\\n");
		sb.append("com.liferay.portal.upgrade.UpgradeProcess_7_0_0\\,\\\n");
		sb.append("com.liferay.portal.upgrade.UpgradeProcess_7_0_1\\,\\\n");
		sb.append("com.liferay.portal.upgrade.UpgradeProcess_7_0_3");

		Assert.assertEquals(
			properties.getProperty("upgrade.processes.master"), sb.toString());
	}

	@Test
	public void testWindowsPathProperties() throws Exception {
		File propertiesFile = temporaryFolder.newFile("test.properties");

		Properties properties = new Properties();

		properties.setProperty("liferay.home", "c:\\liferay\\");

		properties.store(propertiesFile);

		properties = new Properties();

		properties.load(propertiesFile);

		String actualValue = properties.getProperty("liferay.home");

		Assert.assertEquals("c:/liferay/", actualValue);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

}