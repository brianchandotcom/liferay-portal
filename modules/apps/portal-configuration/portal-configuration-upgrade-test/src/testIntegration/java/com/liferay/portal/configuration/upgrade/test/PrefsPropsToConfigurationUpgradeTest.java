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

package com.liferay.portal.configuration.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgrade;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeItem;
import com.liferay.portal.configuration.upgrade.PrefsPropsValueType;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
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
public class PrefsPropsToConfigurationUpgradeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_portletPreferences = PrefsPropsUtil.getPreferences();

		_prefsPropsNames.add(_PREFS_PROPS_NAME);
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfiguration(_CONFIGURATION_PID);

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
			_PREFS_PROPS_NAME, RandomTestUtil.randomString());

		_portletPreferences.store();

		String nullValuePrefsPropsName = RandomTestUtil.randomString();

		PrefsPropsToConfigurationUpgradeItem[]
			prefsPropsToConfigurationUpgradeItems = {
				new PrefsPropsToConfigurationUpgradeItem(
					_PREFS_PROPS_NAME, _CONFIGURATION_METHOD_NAME),
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

		_prefsPropsToConfigurationUpgrade.upgradePrefsPropsToConfiguration(
			_portletPreferences, _getConfiguration(),
			prefsPropsToConfigurationUpgradeItems);

		Configuration configuration = _getConfiguration();

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertNotNull(properties.get(_CONFIGURATION_METHOD_NAME));
		Assert.assertNull(properties.get(_PREFS_PROPS_NAME));
		Assert.assertNull(properties.get(nullValuePrefsPropsName));
	}

	@Test
	public void testUpgradeWillPreservePreexistingConfigurationValues()
		throws Exception {

		String prefsPropsValue = RandomTestUtil.randomString();

		_portletPreferences.setValue(_PREFS_PROPS_NAME, prefsPropsValue);

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
					_PREFS_PROPS_NAME, _CONFIGURATION_METHOD_NAME)
			};

		_prefsPropsToConfigurationUpgrade.upgradePrefsPropsToConfiguration(
			_portletPreferences, configuration,
			prefsPropsToConfigurationUpgradeItems);

		configuration = _getConfiguration();

		properties = configuration.getProperties();

		Assert.assertEquals(
			prefsPropsValue, properties.get(_CONFIGURATION_METHOD_NAME));
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

		_prefsPropsToConfigurationUpgrade.upgradePrefsPropsToConfiguration(
			_portletPreferences, _getConfiguration(),
			prefsPropsToConfigurationUpgradeItems);

		_callPersistenceManager(
			persistenceManager -> Assert.assertFalse(
				persistenceManager.exists(_CONFIGURATION_PID)));
	}

	private static <E extends Throwable> void _callPersistenceManager(
			UnsafeConsumer<PersistenceManager, E> unsafeConsumer)
		throws E {

		OSGiServiceUtil.callService(
			_BUNDLE_CONTEXT, PersistenceManager.class,
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
			_BUNDLE_CONTEXT, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				configurationPid));
	}

	private static Configuration _getConfiguration(
			String configurationPid, String location)
		throws IOException {

		return OSGiServiceUtil.callService(
			_BUNDLE_CONTEXT, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				configurationPid, location));
	}

	private void _assert(Object value, PrefsPropsValueType prefsPropsValueType)
		throws Exception {

		if (prefsPropsValueType != PrefsPropsValueType.STRING_ARRAY) {
			_portletPreferences.setValue(
				_PREFS_PROPS_NAME, String.valueOf(value));
		}
		else {
			_portletPreferences.setValue(
				_PREFS_PROPS_NAME,
				ArrayUtil.toString((String[])value, StringPool.BLANK));
		}

		_portletPreferences.store();

		PrefsPropsToConfigurationUpgradeItem[]
			prefsPropsToConfigurationUpgradeItems = {
				new PrefsPropsToConfigurationUpgradeItem(
					_PREFS_PROPS_NAME, prefsPropsValueType,
					_CONFIGURATION_METHOD_NAME)
			};

		_prefsPropsToConfigurationUpgrade.upgradePrefsPropsToConfiguration(
			_portletPreferences, _getConfiguration(),
			prefsPropsToConfigurationUpgradeItems);

		String defaultValue = RandomTestUtil.randomString();

		Assert.assertEquals(
			defaultValue,
			PrefsPropsUtil.getString(
				_portletPreferences, _PREFS_PROPS_NAME, defaultValue));

		Configuration configuration = _getConfiguration();

		Dictionary<String, Object> properties = configuration.getProperties();

		if (prefsPropsValueType != PrefsPropsValueType.STRING_ARRAY) {
			Assert.assertEquals(
				value, properties.get(_CONFIGURATION_METHOD_NAME));
		}
		else {
			Assert.assertArrayEquals(
				(String[])value,
				(String[])properties.get(_CONFIGURATION_METHOD_NAME));
		}
	}

	private Configuration _getConfiguration() throws IOException {
		return _getConfiguration(_CONFIGURATION_PID, StringPool.QUESTION);
	}

	private static final BundleContext _BUNDLE_CONTEXT;

	private static final String _CONFIGURATION_METHOD_NAME =
		RandomTestUtil.randomString();

	private static final String _CONFIGURATION_PID =
		RandomTestUtil.randomString();

	private static final String _PREFS_PROPS_NAME =
		RandomTestUtil.randomString();

	@Inject
	private static PrefsPropsToConfigurationUpgrade
		_prefsPropsToConfigurationUpgrade;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PrefsPropsToConfigurationUpgradeTest.class);

		if (bundle == null) {
			_BUNDLE_CONTEXT = null;
		}
		else {
			_BUNDLE_CONTEXT = bundle.getBundleContext();
		}
	}

	private PortletPreferences _portletPreferences;
	private final List<String> _prefsPropsNames = new ArrayList<>();

}