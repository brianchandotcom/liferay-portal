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

package com.liferay.portal.configuration.test.util.test;

import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Test;

import org.osgi.service.cm.Configuration;

/**
 * @author Drew Brokke
 */
public class ConfigurationTestUtilTest
	extends BaseConfigurationTestUtilTestCase {

	@Test
	public void testDeleteConfiguration() throws Exception {
		getConfiguration();

		Assert.assertTrue(testConfigurationExists());

		ConfigurationTestUtil.deleteConfiguration(configurationPid);

		Assert.assertFalse(testConfigurationExists());

		Configuration configuration = getConfiguration();

		Assert.assertTrue(testConfigurationExists());

		ConfigurationTestUtil.deleteConfiguration(configuration);

		Assert.assertFalse(testConfigurationExists());
	}

	@Test
	public void testSaveConfiguration() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		String value1 = RandomTestUtil.randomString();

		properties.put(_TEST_KEY, value1);

		ConfigurationTestUtil.saveConfiguration(configurationPid, properties);

		Configuration configuration = _assertConfigurationValue(value1);

		String value2 = RandomTestUtil.randomString();

		properties.put(_TEST_KEY, value2);

		ConfigurationTestUtil.saveConfiguration(configuration, properties);

		_assertConfigurationValue(value2);
	}

	private Configuration _assertConfigurationValue(String value)
		throws Exception {

		Assert.assertTrue(testConfigurationExists());

		Configuration configuration = getConfiguration();

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertEquals(value, properties.get(_TEST_KEY));

		return configuration;
	}

	private static final String _TEST_KEY =
		"ConfigurationTestUtilTest_TEST_KEY";

}