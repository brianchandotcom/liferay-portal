/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class SecurePropertiesTest {

	public static Properties properties = null;

	@BeforeClass
	public static void setUpClass() {
		File propertiesFile = new File(
			"src/test/resources/dependencies/SecurePropertiesTest" +
				"/build.properties");

		try {
			properties = JenkinsResultsParserUtil.toProperties(
				"file://" + propertiesFile.getCanonicalPath());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Test
	public void testCircularPropertyReference() {
		try {
			properties.getProperty("test.property.circular.reference1");
		}
		catch (IllegalStateException illegalStateException) {
			String expectedMessage = JenkinsResultsParserUtil.combine(
				"Found circular property reference chain:\n",
				"test.property.circular.reference1 -> ",
				"test.property.circular.reference2 -> ",
				"test.property.circular.reference3 -> ",
				"test.property.circular.reference1");

			Assert.assertEquals(
				expectedMessage, illegalStateException.getMessage());
		}
	}

	@Test
	public void testMultiplePropertyReference() {
		Assert.assertEquals(
			"value1,value2,value3",
			properties.getProperty("test.property.references1"));
		Assert.assertEquals(
			"value1,value2,value3",
			properties.getProperty("test.property.references2"));
		Assert.assertEquals(
			"value1,value2,value3",
			properties.getProperty("test.property.references3"));
	}

	@Test
	public void testPropertyReference() {
		Assert.assertEquals(
			"value1", properties.getProperty("test.property.reference1"));
		Assert.assertEquals(
			"value1", properties.getProperty("test.property.reference2"));
		Assert.assertEquals(
			"value1", properties.getProperty("test.property.reference3"));
	}

}