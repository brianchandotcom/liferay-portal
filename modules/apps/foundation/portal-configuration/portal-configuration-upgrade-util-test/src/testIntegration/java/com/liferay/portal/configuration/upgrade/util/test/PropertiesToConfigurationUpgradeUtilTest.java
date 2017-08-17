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

package com.liferay.portal.configuration.upgrade.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.upgrade.util.PropertiesToConfigurationUpgradeKey;
import com.liferay.portal.configuration.upgrade.util.PropertiesToConfigurationUpgradeUtil;
import com.liferay.portal.configuration.upgrade.util.PropertyDataType;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnsafeConsumer;
import com.liferay.portal.osgi.util.test.OSGiServiceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;

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
public class PropertiesToConfigurationUpgradeUtilTest {

	@Before
	public void setUp() throws Exception {
		_configurationMethodName = RandomTestUtil.randomString();
		_configurationPid = RandomTestUtil.randomString();
		_portletPreferences = PrefsPropsUtil.getPreferences();
		_propertyKey = RandomTestUtil.randomString();

		_preferencesKeys.add(_propertyKey);
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfiguration(_configurationPid);

		Iterator<String> iterator = _preferencesKeys.iterator();

		while (iterator.hasNext()) {
			_portletPreferences.reset(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testUpgradeBooleanValue() throws Exception {
		_assert(RandomTestUtil.randomBoolean(), PropertyDataType.BOOLEAN);
	}

	@Test
	public void testUpgradeDoubleValue() throws Exception {
		_assert(RandomTestUtil.randomDouble(), PropertyDataType.DOUBLE);
	}

	@Test
	public void testUpgradeIntValue() throws Exception {
		_assert(RandomTestUtil.randomInt(), PropertyDataType.INT);
	}

	@Test
	public void testUpgradeShortValue() throws Exception {
		_assert(
			(short)RandomTestUtil.randomInt(1, Short.MAX_VALUE),
			PropertyDataType.SHORT);
	}

	@Test
	public void testUpgradeStringArrayValue() throws Exception {
		_assert(RandomTestUtil.randomStrings(5), PropertyDataType.STRING_ARRAY);
	}

	@Test
	public void testUpgradeStringValue() throws Exception {
		_assert(RandomTestUtil.randomString(), PropertyDataType.STRING);
	}

	@Test
	public void testWillNotAddNonexistentValues() throws Exception {
		_portletPreferences.setValue(
			_propertyKey, RandomTestUtil.randomString());

		_portletPreferences.store();

		String nonexistentPropertyKey = RandomTestUtil.randomString();

		PropertiesToConfigurationUpgradeKey[] upgradeKeys = {
			new PropertiesToConfigurationUpgradeKey(
				_propertyKey, _configurationMethodName),
			new PropertiesToConfigurationUpgradeKey(
				nonexistentPropertyKey, null, PropertyDataType.BOOLEAN),
			new PropertiesToConfigurationUpgradeKey(
				nonexistentPropertyKey, null, PropertyDataType.DOUBLE),
			new PropertiesToConfigurationUpgradeKey(
				nonexistentPropertyKey, null, PropertyDataType.INT),
			new PropertiesToConfigurationUpgradeKey(
				nonexistentPropertyKey, null, PropertyDataType.LONG),
			new PropertiesToConfigurationUpgradeKey(
				nonexistentPropertyKey, null, PropertyDataType.SHORT),
			new PropertiesToConfigurationUpgradeKey(
				nonexistentPropertyKey, null, PropertyDataType.STRING),
			new PropertiesToConfigurationUpgradeKey(
				nonexistentPropertyKey, null, PropertyDataType.STRING_ARRAY)
		};

		PropertiesToConfigurationUpgradeUtil.upgradePropertiesToConfiguration(
			_portletPreferences, _getTestConfiguration(), upgradeKeys);

		Configuration testConfiguration = _getTestConfiguration();

		Dictionary properties = testConfiguration.getProperties();

		Assert.assertNull(properties.get(nonexistentPropertyKey));
	}

	@Test
	public void testWillPreservePreexistingConfigurationValues()
		throws Exception {

		String preexistingConfigKey = RandomTestUtil.randomString();
		String preexistingConfigValue = RandomTestUtil.randomString();
		String propertyValue = RandomTestUtil.randomString();

		_portletPreferences.setValue(_propertyKey, propertyValue);

		_portletPreferences.store();

		Configuration testConfiguration = _getTestConfiguration();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(preexistingConfigKey, preexistingConfigValue);

		testConfiguration.update(properties);

		PropertiesToConfigurationUpgradeKey[] upgradeKeys = {
			new PropertiesToConfigurationUpgradeKey(
				_propertyKey, _configurationMethodName)
		};

		PropertiesToConfigurationUpgradeUtil.upgradePropertiesToConfiguration(
			_portletPreferences, testConfiguration, upgradeKeys);

		testConfiguration = _getTestConfiguration();

		properties = testConfiguration.getProperties();

		Assert.assertEquals(
			propertyValue, properties.get(_configurationMethodName));
		Assert.assertEquals(
			preexistingConfigValue, properties.get(preexistingConfigKey));
	}

	@Test
	public void testWillRemoveAnEmptyConfiguration() throws Exception {
		String dummyConfigurationMethodName = RandomTestUtil.randomString();
		String nonexistentPropertyKey = RandomTestUtil.randomString();

		PropertiesToConfigurationUpgradeKey[] upgradeKeys = {
			new PropertiesToConfigurationUpgradeKey(
				nonexistentPropertyKey, dummyConfigurationMethodName)
		};

		PropertiesToConfigurationUpgradeUtil.upgradePropertiesToConfiguration(
			_portletPreferences, _getTestConfiguration(), upgradeKeys);

		_callPersistenceManager(
			persistenceManager -> Assert.assertFalse(
				persistenceManager.exists(_configurationPid)));
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

	private void _assert(Object value, PropertyDataType dataType)
		throws Exception {

		if (dataType != PropertyDataType.STRING_ARRAY) {
			_portletPreferences.setValue(_propertyKey, String.valueOf(value));
		}
		else {
			_portletPreferences.setValue(
				_propertyKey,
				ArrayUtil.toString((String[])value, StringPool.BLANK));
		}

		_portletPreferences.store();

		PropertiesToConfigurationUpgradeKey[] upgradeKeys = {
			new PropertiesToConfigurationUpgradeKey(
				_propertyKey, _configurationMethodName, dataType)
		};

		PropertiesToConfigurationUpgradeUtil.upgradePropertiesToConfiguration(
			_portletPreferences, _getTestConfiguration(), upgradeKeys);

		String defaultValue = RandomTestUtil.randomString();

		Assert.assertEquals(
			defaultValue,
			PrefsPropsUtil.getString(
				_portletPreferences, _propertyKey, defaultValue));

		Configuration testConfiguration = _getTestConfiguration();

		Dictionary properties = testConfiguration.getProperties();

		if (dataType != PropertyDataType.STRING_ARRAY) {
			Assert.assertEquals(
				value, properties.get(_configurationMethodName));
		}
		else {
			Assert.assertArrayEquals(
				(String[])value,
				(String[])properties.get(_configurationMethodName));
		}
	}

	private Configuration _getTestConfiguration() throws IOException {
		return _getConfiguration(_configurationPid, StringPool.QUESTION);
	}

	private static final BundleContext _bundleContext;
	private static final List<String> _preferencesKeys = new ArrayList<>();

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PropertiesToConfigurationUpgradeUtilTest.class);

		if (bundle == null) {
			_bundleContext = null;
		}
		else {
			_bundleContext = bundle.getBundleContext();
		}
	}

	private String _configurationMethodName;
	private String _configurationPid;
	private PortletPreferences _portletPreferences;
	private String _propertyKey;

}