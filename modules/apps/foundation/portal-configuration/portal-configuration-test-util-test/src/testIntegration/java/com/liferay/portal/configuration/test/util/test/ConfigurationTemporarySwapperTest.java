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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapperException;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.util.test.OSGiServiceUtil;

import java.io.IOException;

import java.util.Dictionary;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class ConfigurationTemporarySwapperTest {

	@Before
	public void setUp() throws Exception {
		_deleteConfiguration(_CONFIGURATION_PID);
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfiguration(_pid);
	}

	@Test
	public void testWillCleanUpConfiguration() throws Exception {
		_pid = _CONFIGURATION_PID;

		_callPersistenceManager(
			persistenceManager -> {
				try (ConfigurationTemporarySwapper
						configurationTemporarySwapper =
							new ConfigurationTemporarySwapper(
								SearchPermissionChecker.class, _pid,
								new HashMapDictionary<>())) {

					Assert.assertTrue(persistenceManager.exists(_pid));
				}

				Assert.assertFalse(
					String.valueOf(persistenceManager.load(_pid)),
					persistenceManager.exists(_pid));
			});
	}

	@Test(
		expected = ConfigurationTemporarySwapperException.MustFindService.class
	)
	public void testWillFailIfNoServiceFound() throws Exception {
		_pid = StringUtil.randomString(20);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					HasNoImplementation.class, _pid,
					new HashMapDictionary<>())) {

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
					SearchPermissionChecker.class, _pid,
					new HashMapDictionary<>())) {

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
					PrefsProps.class, _pid, new HashMapDictionary<>())) {

			Assert.fail();
		}
	}

	@Test
	public void testWillPreservePreviouslySavedProperties() throws Exception {
		_pid = _CONFIGURATION_PID;

		String testKey = "permissionTermsLimit";
		Integer valueToPreserve = 250;
		int temporaryValue = 300;

		Dictionary<String, Object> temporaryValues = new HashMapDictionary<>();

		temporaryValues.put(testKey, valueToPreserve);

		Configuration testConfiguration = _getConfiguration(
			_pid, StringPool.QUESTION);

		testConfiguration.update(temporaryValues);

		_callPersistenceManager(
			persistenceManager -> {
				temporaryValues.put(testKey, temporaryValue);

				try (ConfigurationTemporarySwapper
						configurationTemporarySwapper =
							new ConfigurationTemporarySwapper(
								SearchPermissionChecker.class, _pid,
								temporaryValues)) {
				}

				Assert.assertTrue(persistenceManager.exists(_pid));
			});

		testConfiguration = _getConfiguration(_pid, StringPool.QUESTION);

		Assert.assertEquals(4, testConfiguration.getChangeCount());

		Dictionary<String, Object> testProperties =
			testConfiguration.getProperties();

		Assert.assertNotNull(testProperties);
		Assert.assertSame(valueToPreserve, testProperties.get(testKey));
	}

	@Test
	public void testWillUpdateConfigurationValues() throws Exception {
		_pid = _CONFIGURATION_PID;

		String testKey = "permissionTermsLimit";
		Integer testValue = 300;

		Dictionary<String, Object> temporaryValues = new HashMapDictionary<>();

		temporaryValues.put(testKey, testValue);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					SearchPermissionChecker.class, _pid, temporaryValues)) {

			Configuration testConfiguration = _getConfiguration(
				_pid, StringPool.QUESTION);

			Assert.assertEquals(2, testConfiguration.getChangeCount());

			Dictionary<String, Object> testProperties =
				testConfiguration.getProperties();

			Assert.assertNotNull(testProperties);
			Assert.assertSame(testValue, testProperties.get(testKey));
		}
	}

	private static <E extends Throwable> void _callPersistenceManager(
			UnsafeConsumer<PersistenceManager, E> unsafeConsumer)
		throws E {

		OSGiServiceUtil.callService(
			_bundleContext, PersistenceManager.class,
			persistenceManager -> {
				unsafeConsumer.accept(persistenceManager);

				return null;
			});
	}

	private static void _deleteConfiguration(String pid) throws IOException {
		_callPersistenceManager(
			persistenceManager -> {
				if (persistenceManager.exists(pid)) {
					Configuration configuration = _getConfiguration(pid);

					configuration.delete();
				}
			});
	}

	private static Configuration _getConfiguration(String pid)
		throws IOException {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(pid));
	}

	private static Configuration _getConfiguration(String pid, String location)
		throws IOException {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, location));
	}

	private static final String _CONFIGURATION_PID =
		"com.liferay.portal.search.configuration." +
			"SearchPermissionCheckerConfiguration";

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationTemporarySwapperTest.class);

		if (bundle == null) {
			_bundleContext = null;
		}
		else {
			_bundleContext = bundle.getBundleContext();
		}
	}

	private String _pid;

	private interface HasNoImplementation {
	}

}