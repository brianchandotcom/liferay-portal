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
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapperException;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class ConfigurationTemporarySwapperTest {

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationTemporarySwapperTest.class);

		_configurationAdminServiceTracker = ServiceTrackerFactory.open(
			bundle, ConfigurationAdmin.class);

		_configurationAdmin = _configurationAdminServiceTracker.waitForService(
			5000);

		_persistenceManagerServiceTracker = ServiceTrackerFactory.open(
			bundle, PersistenceManager.class);

		_persistenceManager = _persistenceManagerServiceTracker.waitForService(
			5000);

		if (_persistenceManager.exists(_SEARCH_CONFIGURATION_PID)) {
			Configuration configuration = _configurationAdmin.getConfiguration(
				_SEARCH_CONFIGURATION_PID);

			configuration.delete();
		}
	}

	@After
	public void tearDown() throws Exception {
		if (_persistenceManager.exists(_pid)) {
			Configuration configuration = _configurationAdmin.getConfiguration(
				_pid);

			configuration.delete();
		}

		_configurationAdminServiceTracker.close();

		_persistenceManagerServiceTracker.close();
	}

	@Test
	public void testWillCleanUpConfiguration() throws Exception {
		_pid = _SEARCH_CONFIGURATION_PID;

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					ConfigurationTemporarySwapperTest.class,
					SearchPermissionChecker.class, _pid,
					new HashMapDictionary<String, Object>())) {

			Assert.assertTrue(_persistenceManager.exists(_pid));
		}

		Assert.assertFalse(_persistenceManager.exists(_pid));
	}

	@Test(
		expected = ConfigurationTemporarySwapperException.MustFindService.class
	)
	public void testWillFailIfNoServiceFound() throws Exception {
		_pid = StringUtil.randomString(20);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					ConfigurationTemporarySwapperTest.class,
					HasNoImplementation.class, _pid,
					new HashMapDictionary<String, Object>())) {

			Assert.fail();
		}
	}

	@Test(
		expected =
			ConfigurationTemporarySwapperException.
				ServiceMustConsumeConfiguration.class
	)
	public void testWillFailIfServiceDoesNotConsumeConfiguration()
		throws Exception {

		_pid = StringUtil.randomString(20);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					ConfigurationTemporarySwapperTest.class,
					SearchPermissionChecker.class, _pid,
					new HashMapDictionary<String, Object>())) {

			Assert.fail();
		}
	}

	@Test(
		expected =
			ConfigurationTemporarySwapperException.ServiceMustHaveBundle.class
	)
	public void testWillFailIfServiceDoesNotHaveABundle() throws Exception {
		_pid = StringUtil.randomString(20);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					ConfigurationTemporarySwapperTest.class, PrefsProps.class,
					_pid, new HashMapDictionary<String, Object>())) {

			Assert.fail();
		}
	}

	@Test
	public void testWillPreservePreviouslySavedProperties() throws Exception {
		_pid = _SEARCH_CONFIGURATION_PID;

		String testKey = "permissionTermsLimit";
		int valueToPreserve = 250;
		int temporaryValue = 300;

		Dictionary<String, Object> temporaryValues = new HashMapDictionary();

		temporaryValues.put(testKey, valueToPreserve);

		Configuration testConfiguration = _configurationAdmin.getConfiguration(
			_pid, StringPool.QUESTION);

		testConfiguration.update(temporaryValues);

		_persistenceManager.load(_pid);

		temporaryValues.put(testKey, temporaryValue);

		Dictionary<String, Object> testProperties;

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					ConfigurationTemporarySwapperTest.class,
					SearchPermissionChecker.class, _pid, temporaryValues)) {
		}

		Assert.assertTrue(_persistenceManager.exists(_pid));

		testConfiguration = _configurationAdmin.getConfiguration(
			_pid, StringPool.QUESTION);

		Assert.assertEquals(4, testConfiguration.getChangeCount());

		testProperties = testConfiguration.getProperties();

		Assert.assertNotNull(testProperties);

		Assert.assertEquals(valueToPreserve, testProperties.get(testKey));
	}

	@Test
	public void testWillUpdateConfigurationValues() throws Exception {
		_pid = _SEARCH_CONFIGURATION_PID;

		String testKey = "permissionTermsLimit";
		int testValue = 300;

		Dictionary<String, Object> temporaryValues = new HashMapDictionary<>();

		temporaryValues.put(testKey, testValue);

		Configuration testConfiguration;
		Dictionary<String, Object> testProperties;

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					ConfigurationTemporarySwapperTest.class,
					SearchPermissionChecker.class, _pid, temporaryValues)) {

			testConfiguration = _configurationAdmin.getConfiguration(
				_pid, StringPool.QUESTION);

			Assert.assertEquals(2, testConfiguration.getChangeCount());

			testProperties = testConfiguration.getProperties();

			Assert.assertNotNull(testProperties);

			Assert.assertNotNull(testProperties.get(testKey));

			Assert.assertEquals(testValue, testProperties.get(testKey));
		}
	}

	private static final String _SEARCH_CONFIGURATION_PID =
		"com.liferay.portal.search.configuration." +
			"SearchPermissionCheckerConfiguration";

	private ConfigurationAdmin _configurationAdmin;
	private ServiceTracker<ConfigurationAdmin, ConfigurationAdmin>
		_configurationAdminServiceTracker;
	private PersistenceManager _persistenceManager;
	private ServiceTracker<PersistenceManager, PersistenceManager>
		_persistenceManagerServiceTracker;
	private String _pid;

	private interface HasNoImplementation {
	}

}