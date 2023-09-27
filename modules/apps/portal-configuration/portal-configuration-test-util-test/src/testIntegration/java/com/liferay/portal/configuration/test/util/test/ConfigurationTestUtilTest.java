/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.test.util.test;

import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

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
		String value1 = RandomTestUtil.randomString();

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				_TEST_KEY, value1
			).build();

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