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

import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Test;

import org.osgi.service.cm.Configuration;

/**
 * @author Drew Brokke
 */
public class ConfigurationTemporarySwapperTest
	extends BaseConfigurationTestUtilTestCase {

	@Test
	public void testWillCleanUpConfiguration() throws Exception {
		Assert.assertFalse(
			String.valueOf(persistenceManager.load(configurationPid)),
			testConfigurationExists());

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					configurationPid, new HashMapDictionary<>())) {

			Assert.assertTrue(testConfigurationExists());
		}

		Assert.assertFalse(
			String.valueOf(persistenceManager.load(configurationPid)),
			testConfigurationExists());
	}

	@Test
	public void testWillPreservePreviouslySavedProperties() throws Exception {
		String testKey = "permissionTermsLimit";
		Integer valueToPreserve = 250;
		int temporaryValue = 300;

		Dictionary<String, Object> temporaryValues = new HashMapDictionary<>();

		temporaryValues.put(testKey, valueToPreserve);

		Configuration testConfiguration = getConfiguration();

		testConfiguration.update(temporaryValues);

		temporaryValues.put(testKey, temporaryValue);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					configurationPid, temporaryValues)) {
		}

		Assert.assertTrue(testConfigurationExists());

		testConfiguration = getConfiguration();

		Assert.assertEquals(4, testConfiguration.getChangeCount());

		Dictionary<String, Object> testProperties =
			testConfiguration.getProperties();

		Assert.assertNotNull(testProperties);
		Assert.assertSame(valueToPreserve, testProperties.get(testKey));
	}

	@Test
	public void testWillUpdateConfigurationValues() throws Exception {
		String testKey = "permissionTermsLimit";
		Integer testValue = 300;

		Dictionary<String, Object> temporaryValues = new HashMapDictionary<>();

		temporaryValues.put(testKey, testValue);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					configurationPid, temporaryValues)) {

			Configuration testConfiguration = getConfiguration();

			Assert.assertEquals(2, testConfiguration.getChangeCount());

			Dictionary<String, Object> testProperties =
				testConfiguration.getProperties();

			Assert.assertNotNull(testProperties);
			Assert.assertSame(testValue, testProperties.get(testKey));
		}
	}

}