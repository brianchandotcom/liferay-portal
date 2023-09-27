/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.test.util.test;

import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

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

		Dictionary<String, Object> temporaryValues =
			HashMapDictionaryBuilder.<String, Object>put(
				testKey, valueToPreserve
			).build();

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

		Dictionary<String, Object> temporaryValues =
			HashMapDictionaryBuilder.<String, Object>put(
				testKey, testValue
			).build();

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