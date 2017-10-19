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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.configuration.upgrade.util.PrefsPropsToConfigurationUpgradeItem;
import com.liferay.portal.configuration.upgrade.util.PrefsPropsToConfigurationUpgradeUtil;
import com.liferay.portal.configuration.upgrade.util.PrefsPropsValueType;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.osgi.util.test.OSGiServiceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
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
public class PrefsPropsToConfigurationUpgradeUtilTest {

	@Before
	public void setUp() throws Exception {
		_portletPreferences = PrefsPropsUtil.getPreferences();

		_prefsPropsNames.add(_prefsPropsName);
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfiguration(_configurationPid);

		for (String prefsPropsName : _prefsPropsNames) {
			_portletPreferences.reset(prefsPropsName);
		}
	}

	@Test
	public void testUpgradeBooleanValue() throws Exception {
		_assert(RandomTestUtil.randomBoolean(), PrefsPropsValueType.BOOLEAN);
	}

	@Test
	public void testUpgradeDoubleValue() throws Exception {
		_assert(RandomTestUtil.randomDouble(), PrefsPropsValueType.DOUBLE);
	}

	@Test
	public void testUpgradeIntValue() throws Exception {
		_assert(RandomTestUtil.randomInt(), PrefsPropsValueType.INT);
	}

	@Test
	public void testUpgradeShortValue() throws Exception {
		_assert(
			(short)RandomTestUtil.randomInt(1, Short.MAX_VALUE),
			PrefsPropsValueType.SHORT);
	}

	@Test
	public void testUpgradeStringArrayValue() throws Exception {
		_assert(
			RandomTestUtil.randomStrings(5), PrefsPropsValueType.STRING_ARRAY);
	}

	@Test
	public void testUpgradeStringValue() throws Exception {
		_assert(RandomTestUtil.randomString(), PrefsPropsValueType.STRING);
	}

	@Test
	public void testUpgradeWillNotAddNullValues() throws Exception {
		_portletPreferences.setValue(
			_prefsPropsName, RandomTestUtil.randomString());

		_portletPreferences.store();

		String nullValuePrefsPropsName = RandomTestUtil.randomString();

		PrefsPropsToConfigurationUpgradeItem[]
			prefsPropsToConfigurationUpgradeItems = {
				new PrefsPropsToConfigurationUpgradeItem(
					_prefsPropsName, _configurationMethodName),
				new PrefsPropsToConfigurationUpgradeItem(
					nullValuePrefsPropsName, PrefsPropsValueType.BOOLEAN, null),
				new PrefsPropsToConfigurationUpgradeItem(
					nullValuePrefsPropsName, PrefsPropsValueType.DOUBLE, null),
				new PrefsPropsToConfigurationUpgradeItem(
					nullValuePrefsPropsName, PrefsPropsValueType.INT, null),
				new PrefsPropsToConfigurationUpgradeItem(
					nullValuePrefsPropsName, PrefsPropsValueType.LONG, null),
				new PrefsPropsToConfigurationUpgradeItem(
					nullValuePrefsPropsName, PrefsPropsValueType.SHORT, null),
				new PrefsPropsToConfigurationUpgradeItem(
					nullValuePrefsPropsName, PrefsPropsValueType.STRING, null),
				new PrefsPropsToConfigurationUpgradeItem(
					nullValuePrefsPropsName, PrefsPropsValueType.STRING_ARRAY,
					null)
			};

		PrefsPropsToConfigurationUpgradeUtil.upgradePrefsPropsToConfiguration(
			_portletPreferences, _getConfiguration(),
			prefsPropsToConfigurationUpgradeItems);

		Configuration configuration = _getConfiguration();

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertNotNull(properties.get(_configurationMethodName));
		Assert.assertNull(properties.get(_prefsPropsName));
		Assert.assertNull(properties.get(nullValuePrefsPropsName));
	}

	@Test
	public void testUpgradeWillPreservePreexistingConfigurationValues()
		throws Exception {

		String prefsPropsValue = RandomTestUtil.randomString();

		_portletPreferences.setValue(_prefsPropsName, prefsPropsValue);

		_portletPreferences.store();

		Configuration configuration = _getConfiguration();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		String preexistingConfigurationMethodName =
			RandomTestUtil.randomString();
		String preexistingConfigurationValue = RandomTestUtil.randomString();

		properties.put(
			preexistingConfigurationMethodName, preexistingConfigurationValue);

		configuration.update(properties);

		PrefsPropsToConfigurationUpgradeItem[]
			prefsPropsToConfigurationUpgradeItems = {
				new PrefsPropsToConfigurationUpgradeItem(
					_prefsPropsName, _configurationMethodName)
			};

		PrefsPropsToConfigurationUpgradeUtil.upgradePrefsPropsToConfiguration(
			_portletPreferences, configuration,
			prefsPropsToConfigurationUpgradeItems);

		configuration = _getConfiguration();

		properties = configuration.getProperties();

		Assert.assertEquals(
			prefsPropsValue, properties.get(_configurationMethodName));
		Assert.assertEquals(
			preexistingConfigurationValue,
			properties.get(preexistingConfigurationMethodName));
	}

	@Test
	public void testUpgradeWillRemoveEmptyConfiguration() throws Exception {
		PrefsPropsToConfigurationUpgradeItem[]
			prefsPropsToConfigurationUpgradeItems = {
				new PrefsPropsToConfigurationUpgradeItem(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString())
			};

		PrefsPropsToConfigurationUpgradeUtil.upgradePrefsPropsToConfiguration(
			_portletPreferences, _getConfiguration(),
			prefsPropsToConfigurationUpgradeItems);

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

	private static void _deleteConfiguration(String configurationPid)
		throws IOException {

		_callPersistenceManager(
			persistenceManager -> {
				if (persistenceManager.exists(configurationPid)) {
					Configuration configuration = _getConfiguration(
						configurationPid);

					configuration.delete();
				}
			});
	}

	private static Configuration _getConfiguration(String configurationPid)
		throws IOException {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				configurationPid));
	}

	private static Configuration _getConfiguration(
			String configurationPid, String location)
		throws IOException {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				configurationPid, location));
	}

	private void _assert(Object value, PrefsPropsValueType prefsPropsValueType)
		throws Exception {

		if (prefsPropsValueType != PrefsPropsValueType.STRING_ARRAY) {
			_portletPreferences.setValue(
				_prefsPropsName, String.valueOf(value));
		}
		else {
			_portletPreferences.setValue(
				_prefsPropsName,
				ArrayUtil.toString((String[])value, StringPool.BLANK));
		}

		_portletPreferences.store();

		PrefsPropsToConfigurationUpgradeItem[]
			prefsPropsToConfigurationUpgradeItems = {
				new PrefsPropsToConfigurationUpgradeItem(
					_prefsPropsName, prefsPropsValueType,
					_configurationMethodName)
			};

		PrefsPropsToConfigurationUpgradeUtil.upgradePrefsPropsToConfiguration(
			_portletPreferences, _getConfiguration(),
			prefsPropsToConfigurationUpgradeItems);

		String defaultValue = RandomTestUtil.randomString();

		Assert.assertEquals(
			defaultValue,
			PrefsPropsUtil.getString(
				_portletPreferences, _prefsPropsName, defaultValue));

		Configuration configuration = _getConfiguration();

		Dictionary<String, Object> properties = configuration.getProperties();

		if (prefsPropsValueType != PrefsPropsValueType.STRING_ARRAY) {
			Assert.assertEquals(
				value, properties.get(_configurationMethodName));
		}
		else {
			Assert.assertArrayEquals(
				(String[])value,
				(String[])properties.get(_configurationMethodName));
		}
	}

	private Configuration _getConfiguration() throws IOException {
		return _getConfiguration(_configurationPid, StringPool.QUESTION);
	}

	private static final BundleContext _bundleContext;
	private static final String _configurationMethodName =
		RandomTestUtil.randomString();
	private static final String _configurationPid =
		RandomTestUtil.randomString();
	private static final String _prefsPropsName = RandomTestUtil.randomString();

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PrefsPropsToConfigurationUpgradeUtilTest.class);

		if (bundle == null) {
			_bundleContext = null;
		}
		else {
			_bundleContext = bundle.getBundleContext();
		}
	}

	private PortletPreferences _portletPreferences;
	private final List<String> _prefsPropsNames = new ArrayList<>();

}