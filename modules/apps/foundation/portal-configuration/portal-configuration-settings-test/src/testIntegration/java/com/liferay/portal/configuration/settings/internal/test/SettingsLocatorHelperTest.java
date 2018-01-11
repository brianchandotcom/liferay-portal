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
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.util.ConfigurationScopedPidUtil;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorHelperTestConstants;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
@RunWith(Arquillian.class)
public class SettingsLocatorHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(
			SettingsLocatorHelperTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() throws Exception {
		for (Configuration configuration : _configurations) {
			configuration.delete();
		}

		_configurations.clear();
	}

	@Test
	public void testGetCompanyScopedConfigurationSettings() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Settings companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				null);

		Assert.assertNull(companySettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID);

		companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, companySettings);

		Assert.assertEquals(
			SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE,
			companySettings.getValue(
				SettingsLocatorHelperTestConstants.TEST_KEY,
				SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE));

		String companyValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY,
			String.valueOf(companyId));

		companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, companySettings);

		Assert.assertEquals(
			companyValue,
			companySettings.getValue(
				SettingsLocatorHelperTestConstants.TEST_KEY,
				SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetGroupScopedConfigurationSettings() throws Exception {
		long groupId = TestPropsValues.getGroupId();

		Settings groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				null);

		Assert.assertNull(groupSettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID);

		groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, groupSettings);

		Assert.assertEquals(
			SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE,
			groupSettings.getValue(
				SettingsLocatorHelperTestConstants.TEST_KEY,
				SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE));

		String groupValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.GROUP, String.valueOf(groupId));

		groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, groupSettings);

		Assert.assertEquals(
			groupValue,
			groupSettings.getValue(
				SettingsLocatorHelperTestConstants.TEST_KEY,
				SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetPortletInstanceScopedConfigurationSettings()
		throws Exception {

		String portletId = RandomTestUtil.randomString();

		String portletInstanceKey =
			portletId + "_INSTANCE_" + RandomTestUtil.randomString();

		Settings portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				null);

		Assert.assertNull(portletInstanceSettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID);

		portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, portletInstanceSettings);

		Assert.assertEquals(
			SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE,
			portletInstanceSettings.getValue(
				SettingsLocatorHelperTestConstants.TEST_KEY,
				SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE));

		String companyValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
			portletInstanceKey);

		portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, portletInstanceSettings);

		Assert.assertEquals(
			companyValue,
			portletInstanceSettings.getValue(
				SettingsLocatorHelperTestConstants.TEST_KEY,
				SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetSystemScopedConfigurationSettings() throws Exception {
		Settings settings = _settingsLocatorHelper.getConfigurationBeanSettings(
			SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID);

		Assert.assertEquals(
			SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE,
			settings.getValue(
				SettingsLocatorHelperTestConstants.TEST_KEY,
				SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE));

		String systemValue = saveConfiguration();

		settings = _settingsLocatorHelper.getConfigurationBeanSettings(
			SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID);

		Assert.assertEquals(
			systemValue,
			settings.getValue(
				SettingsLocatorHelperTestConstants.TEST_KEY,
				SettingsLocatorHelperTestConstants.TEST_DEFAULT_VALUE));
	}

	protected String saveConfiguration() throws Exception {
		return _saveConfiguration(
			SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID);
	}

	protected String saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope scope, String scopePrimKey)
		throws Exception {

		return _saveConfiguration(
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				SettingsLocatorHelperTestConstants.TEST_CONFIGURATION_PID,
				scope, scopePrimKey));
	}

	private String _saveConfiguration(String configurationPid)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			configurationPid, StringPool.QUESTION);

		_configurations.add(configuration);

		String value = RandomTestUtil.randomString();

		Dictionary<String, String> properties = new HashMapDictionary<>();

		properties.put(SettingsLocatorHelperTestConstants.TEST_KEY, value);

		configuration.update(properties);

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ManagedService managedService = props -> {
			if (props == null) {
				return;
			}

			String testValue = (String)props.get(
				SettingsLocatorHelperTestConstants.TEST_KEY);

			if (testValue.equals(value)) {
				countDownLatch.countDown();
			}

		};

		Dictionary<String, Object> managedServiceProperties =
			new HashMapDictionary<>();

		managedServiceProperties.put(Constants.SERVICE_PID, configurationPid);

		ServiceRegistration managedServiceServiceRegistration =
			_bundleContext.registerService(
				ManagedService.class, managedService, managedServiceProperties);

		countDownLatch.await();

		managedServiceServiceRegistration.unregister();

		return value;
	}

	private BundleContext _bundleContext;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private final List<Configuration> _configurations = new ArrayList<>();

	@Inject
	private SettingsLocatorHelper _settingsLocatorHelper;

}