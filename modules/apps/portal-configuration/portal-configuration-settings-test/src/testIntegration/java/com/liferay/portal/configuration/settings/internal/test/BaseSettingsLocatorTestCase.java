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

package com.liferay.portal.configuration.settings.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public abstract class BaseSettingsLocatorTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		companyId = TestPropsValues.getCompanyId();
		groupId = TestPropsValues.getGroupId();
	}

	@After
	public void tearDown() throws Exception {
		for (String configurationPid : _configurationPids) {
			ConfigurationTestUtil.deleteConfiguration(configurationPid);
		}

		_configurationPids.clear();

		for (String configurationPid : _factoryConfigurationPids) {
			ConfigurationTestUtil.deleteFactoryConfiguration(
				configurationPid,
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID +
					".scoped");
		}

		_factoryConfigurationPids.clear();
	}

	protected void deleteFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK, String propertyKey,
			Serializable propertyValue)
		throws Exception {

		Configuration configuration = getFactoryConfiguration(
			factoryPid, scope, scopePK, propertyKey, propertyValue);

		if (configuration != null) {
			ConfigurationTestUtil.deleteConfiguration(configuration);
		}
	}

	protected Configuration getFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK, String propertyKey,
			Serializable propertyValue)
		throws ConfigurationException {

		try {
			String filterString = StringBundler.concat(
				"(&",
				getPropertyFilterString(
					"service.factoryPid", factoryPid + ".scoped"),
				getPropertyFilterString(scope.getPropertyKey(), scopePK),
				getPropertyFilterString(propertyKey, propertyValue), ")");

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(filterString);

			if (configurations != null) {
				return configurations[0];
			}

			return null;
		}
		catch (InvalidSyntaxException | IOException e) {
			throw new ConfigurationException(
				"Unable to retrieve factory configuration " + factoryPid, e);
		}
	}

	protected String getPropertyFilterString(String key, Serializable value) {
		if (Validator.isNull(key) || Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, key, StringPool.EQUAL, value,
			StringPool.CLOSE_PARENTHESIS);
	}

	protected String getSettingsValue() throws Exception {
		if (settingsLocator == null) {
			return null;
		}

		Settings settings = settingsLocator.getSettings();

		return settings.getValue(SettingsLocatorTestConstants.TEST_KEY, null);
	}

	protected String saveConfiguration() throws Exception {
		return saveConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);
	}

	protected String saveConfiguration(String configurationPid)
		throws Exception {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		String value = RandomTestUtil.randomString();

		properties.put(SettingsLocatorTestConstants.TEST_KEY, value);

		ConfigurationTestUtil.saveConfiguration(configurationPid, properties);

		_configurationPids.add(configurationPid);

		return value;
	}

	protected String saveFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK)
		throws Exception {

		return saveFactoryConfiguration(factoryPid, scope, scopePK, null, null);
	}

	protected String saveFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK, String propertyKey,
			Serializable propertyValue)
		throws Exception {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(scope.getPropertyKey(), scopePK);

		if (Validator.isNotNull(propertyKey) &&
			Validator.isNotNull(propertyValue)) {

			properties.put(propertyKey, propertyValue);
		}

		String value = RandomTestUtil.randomString();

		properties.put(SettingsLocatorTestConstants.TEST_KEY, value);

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			factoryPid + ".scoped", properties);

		_factoryConfigurationPids.add(pid);

		return value;
	}

	protected String savePortletPreferences(long ownerId, int ownerType)
		throws Exception {

		return savePortletPreferences(
			ownerId, ownerType, portletId, PortletKeys.PREFS_PLID_SHARED);
	}

	protected String savePortletPreferences(
			long ownerId, int ownerType, String portletId, long plid)
		throws Exception {

		String value = RandomTestUtil.randomString();

		_portletPreferencesList.add(
			_portletPreferencesLocalService.addPortletPreferences(
				companyId, ownerId, ownerType, plid, portletId, null,
				String.format(
					SettingsLocatorTestConstants.PORTLET_PREFERENCES_FORMAT,
					SettingsLocatorTestConstants.TEST_KEY, value)));

		return value;
	}

	protected String saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope scope, Serializable scopePK)
		throws Exception {

		return saveFactoryConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID, scope,
			scopePK);
	}

	protected static long companyId;
	protected static long groupId;

	protected final String portletId = RandomTestUtil.randomString();
	protected SettingsLocator settingsLocator;

	private static final Set<String> _configurationPids = new HashSet<>();
	private static final Set<String> _factoryConfigurationPids =
		new HashSet<>();

	@Inject
	private static PortletPreferencesLocalService
		_portletPreferencesLocalService;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@DeleteAfterTestRun
	private final List<PortletPreferences> _portletPreferencesList =
		new ArrayList<>();

}