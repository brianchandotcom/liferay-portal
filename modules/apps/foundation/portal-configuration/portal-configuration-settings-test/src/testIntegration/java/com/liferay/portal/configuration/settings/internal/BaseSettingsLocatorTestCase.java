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

package com.liferay.portal.configuration.settings.internal;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.util.ConfigurationScopedPidUtil;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.osgi.util.test.OSGiServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;

/**
 * @author Drew Brokke
 */
public abstract class BaseSettingsLocatorTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			BaseSettingsLocatorTestCase.class);

		bundleContext = bundle.getBundleContext();

		companyId = TestPropsValues.getCompanyId();
		groupId = TestPropsValues.getGroupId();
		portletId = RandomTestUtil.randomString();
	}

	@After
	public void tearDown() throws Exception {
		for (Configuration configuration : _configurations) {
			configuration.delete();
		}

		_configurations.clear();
	}

	protected String getValueFromSettings() throws Exception {
		if (settingsLocator == null) {
			return null;
		}

		Settings settings = settingsLocator.getSettings();

		return settings.getValue(SettingsLocatorTestConstants.TEST_KEY, null);
	}

	protected String saveConfiguration() throws Exception {
		return _saveConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);
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
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				companyId, ownerId, ownerType, plid, portletId, null,
				String.format(
					_portletPreferenceFormat,
					SettingsLocatorTestConstants.TEST_KEY, value)));

		return value;
	}

	protected String saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope scope, String scopePrimKey)
		throws Exception {

		return _saveConfiguration(
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID, scope,
				scopePrimKey));
	}

	protected BundleContext bundleContext;
	protected long companyId;
	protected long groupId;
	protected String portletId;
	protected SettingsLocator settingsLocator;

	private String _saveConfiguration(String configurationPid)
		throws Exception {

		Configuration configuration = OSGiServiceUtil.callService(
			bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				configurationPid, StringPool.QUESTION));

		_configurations.add(configuration);

		String value = RandomTestUtil.randomString();

		Dictionary<String, String> properties = new HashMapDictionary<>();

		properties.put(SettingsLocatorTestConstants.TEST_KEY, value);

		configuration.update(properties);

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ManagedService managedService = props -> {
			if (props == null) {
				return;
			}

			String testValue = (String)props.get(
				SettingsLocatorTestConstants.TEST_KEY);

			if (testValue.equals(value)) {
				countDownLatch.countDown();
			}

		};

		Dictionary<String, Object> managedServiceProperties =
			new HashMapDictionary<>();

		managedServiceProperties.put(Constants.SERVICE_PID, configurationPid);

		ServiceRegistration managedServiceServiceRegistration =
			bundleContext.registerService(
				ManagedService.class, managedService, managedServiceProperties);

		countDownLatch.await();

		managedServiceServiceRegistration.unregister();

		return value;
	}

	private static final String _portletPreferenceFormat =
		"<portlet-preferences><preference><name>%s</name><value>%s</value>" +
			"</preference></portlet-preferences>";

	private ServiceRegistration<ConfigurationBeanDeclaration>
		_configurationBeanDeclarationServiceRegistration;
	private final List<Configuration> _configurations = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<PortletPreferences> _portletPreferencesList =
		new ArrayList<>();

}