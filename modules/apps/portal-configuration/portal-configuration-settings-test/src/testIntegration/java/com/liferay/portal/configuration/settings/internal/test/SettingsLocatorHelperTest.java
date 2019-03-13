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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class SettingsLocatorHelperTest extends BaseSettingsLocatorTestCase {

	@Test
	public void testGetCompanyScopedConfigurationSettings() throws Exception {
		Settings companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				null);

		Assert.assertNull(companySettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, companySettings);

		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			companySettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));

		String companyValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId);

		companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, companySettings);

		Assert.assertEquals(
			companyValue,
			companySettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetCompanyScopedFactoryConfigurationSettings()
		throws Exception {

		String testKey = "factoryAlternateKey";
		String testValue1 = RandomTestUtil.randomString();
		String testValue2 = RandomTestUtil.randomString();

		// Adds two configurations to the same scope

		saveFactoryConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
			ExtendedObjectClassDefinition.Scope.COMPANY,
			TestPropsValues.getCompanyId(), testKey, testValue1);

		saveFactoryConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
			ExtendedObjectClassDefinition.Scope.COMPANY,
			TestPropsValues.getCompanyId(), testKey, testValue2);

		Settings companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				TestPropsValues.getCompanyId(),
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID, null);

		// Asserts that the second one (most recently added) is returned

		Assert.assertEquals(
			testValue2, companySettings.getValue(testKey, null));

		// Delete the most recent one

		deleteFactoryConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
			ExtendedObjectClassDefinition.Scope.COMPANY,
			TestPropsValues.getCompanyId(), testKey, testValue2);

		companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				TestPropsValues.getCompanyId(),
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID, null);

		// Asserts that the first one is returned

		Assert.assertEquals(
			testValue1, companySettings.getValue(testKey, null));
	}

	@Test
	public void testGetGroupScopedConfigurationSettings() throws Exception {
		Settings groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				null);

		Assert.assertNull(groupSettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, groupSettings);

		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			groupSettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));

		String groupValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.GROUP, groupId);

		groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, groupSettings);

		Assert.assertEquals(
			groupValue,
			groupSettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetPortletInstanceScopedConfigurationSettings()
		throws Exception {

		String portletInstanceKey =
			portletId + "_INSTANCE_" + RandomTestUtil.randomString();

		Settings portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID, null);

		Assert.assertNull(portletInstanceSettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, portletInstanceSettings);

		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			portletInstanceSettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));

		String portletInstanceValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
			portletInstanceKey);

		portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, portletInstanceSettings);

		Assert.assertEquals(
			portletInstanceValue,
			portletInstanceSettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetSystemScopedConfigurationSettings() throws Exception {
		Settings settings = _settingsLocatorHelper.getConfigurationBeanSettings(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			settings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));

		String systemValue = saveConfiguration();

		settings = _settingsLocatorHelper.getConfigurationBeanSettings(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		Assert.assertEquals(
			systemValue,
			settings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	@Inject
	private static SettingsLocatorHelper _settingsLocatorHelper;

}