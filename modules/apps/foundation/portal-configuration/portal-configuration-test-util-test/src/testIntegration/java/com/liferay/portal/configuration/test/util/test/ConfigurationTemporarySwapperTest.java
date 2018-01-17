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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class ConfigurationTemporarySwapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_configurationPid = "TestPID_" + RandomTestUtil.randomString();
	}

	@After
	public void tearDown() throws Exception {
		if (_persistenceManager.exists(_configurationPid)) {
			Configuration configuration = _getConfiguration();

			configuration.delete();
		}
	}

	@Test
	public void testWillCleanUpConfiguration() throws Exception {
		Assert.assertFalse(
			String.valueOf(_persistenceManager.load(_configurationPid)),
			_persistenceManager.exists(_configurationPid));

		try (ConfigurationTemporarySwapper
				configurationTemporarySwapper =
					new ConfigurationTemporarySwapper(
						_configurationPid, new HashMapDictionary<>())) {

			Assert.assertTrue(_persistenceManager.exists(_configurationPid));
		}

		Assert.assertFalse(
			String.valueOf(_persistenceManager.load(_configurationPid)),
			_persistenceManager.exists(_configurationPid));
	}

	@Test
	public void testWillPreservePreviouslySavedProperties() throws Exception {
		String testKey = "permissionTermsLimit";
		Integer valueToPreserve = 250;
		int temporaryValue = 300;

		Dictionary<String, Object> temporaryValues = new HashMapDictionary<>();

		temporaryValues.put(testKey, valueToPreserve);

		Configuration testConfiguration = _getConfiguration();

		testConfiguration.update(temporaryValues);

		temporaryValues.put(testKey, temporaryValue);

		try (ConfigurationTemporarySwapper
				configurationTemporarySwapper =
					new ConfigurationTemporarySwapper(
						_configurationPid, temporaryValues)) {
		}

		Assert.assertTrue(_persistenceManager.exists(_configurationPid));

		testConfiguration = _getConfiguration();

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
					_configurationPid, temporaryValues)) {

			Configuration testConfiguration = _getConfiguration();

			Assert.assertEquals(2, testConfiguration.getChangeCount());

			Dictionary<String, Object> testProperties =
				testConfiguration.getProperties();

			Assert.assertNotNull(testProperties);
			Assert.assertSame(testValue, testProperties.get(testKey));
		}
	}

	private Configuration _getConfiguration() throws Exception {
		return _configurationAdmin.getConfiguration(
			_configurationPid, StringPool.QUESTION);
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private static PersistenceManager _persistenceManager;

	private String _configurationPid;

}