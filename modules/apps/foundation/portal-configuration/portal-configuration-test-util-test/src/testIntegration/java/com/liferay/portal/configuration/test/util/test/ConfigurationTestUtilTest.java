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
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assert;
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
public class ConfigurationTestUtilTest {

	@After
	public void tearDown() throws Exception {
		if (_testConfigurationExists()) {
			Configuration configuration = _getConfiguration();

			configuration.delete();
		}
	}

	@Test
	public void testDeleteConfiguration() throws Exception {
		_getConfiguration();

		Assert.assertTrue(_testConfigurationExists());

		ConfigurationTestUtil.deleteConfiguration(_pid);

		Assert.assertFalse(_testConfigurationExists());

		Configuration configuration = _getConfiguration();

		Assert.assertTrue(_testConfigurationExists());

		ConfigurationTestUtil.deleteConfiguration(configuration);

		Assert.assertFalse(_testConfigurationExists());
	}

	@Test
	public void testSaveConfiguration() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		String value1 = RandomTestUtil.randomString();

		properties.put(_TEST_KEY, value1);

		ConfigurationTestUtil.saveConfiguration(_pid, properties);

		Configuration configuration = _assertConfigurationValue(value1);

		String value2 = RandomTestUtil.randomString();

		properties.put(_TEST_KEY, value2);

		ConfigurationTestUtil.saveConfiguration(configuration, properties);

		_assertConfigurationValue(value2);
	}

	private Configuration _assertConfigurationValue(String value)
		throws Exception {

		Assert.assertTrue(_testConfigurationExists());

		Configuration configuration = _getConfiguration();

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertEquals(value, properties.get(_TEST_KEY));

		return configuration;
	}

	private Configuration _getConfiguration() throws Exception {
		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				_pid, StringPool.QUESTION));
	}

	private boolean _testConfigurationExists() {
		return OSGiServiceUtil.callService(
			_bundleContext, PersistenceManager.class,
			persistenceManager -> persistenceManager.exists(_pid));
	}

	private static final String _TEST_KEY =
		"ConfigurationTestUtilTest_TEST_KEY";

	private static final BundleContext _bundleContext;
	private static final String _pid =
		"TestPID_" + RandomTestUtil.randomString();

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

	private final List<Configuration> _configurations = new ArrayList<>();

}