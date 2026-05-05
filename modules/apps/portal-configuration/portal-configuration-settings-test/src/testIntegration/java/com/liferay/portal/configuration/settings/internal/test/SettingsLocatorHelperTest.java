/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.settings.internal.test;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.configuration.settings.internal.samples.TestRequiredConfiguration;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import java.util.Map;

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

		String companyValue = saveFactoryConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId, null, null,
			SettingsLocatorTestConstants.TEST_KEY,
			RandomTestUtil.randomString());

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
			TestPropsValues.getCompanyId(), testKey, testValue1,
			SettingsLocatorTestConstants.TEST_KEY,
			RandomTestUtil.randomString());

		saveFactoryConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
			ExtendedObjectClassDefinition.Scope.COMPANY,
			TestPropsValues.getCompanyId(), testKey, testValue2,
			SettingsLocatorTestConstants.TEST_KEY,
			RandomTestUtil.randomString());

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

		String groupValue = saveFactoryConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
			ExtendedObjectClassDefinition.Scope.GROUP, groupId, null, null,
			SettingsLocatorTestConstants.TEST_KEY,
			RandomTestUtil.randomString());

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

		String portletInstanceValue = saveFactoryConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
			portletInstanceKey, null, null,
			SettingsLocatorTestConstants.TEST_KEY,
			RandomTestUtil.randomString());

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

		String systemValue = saveConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
			SettingsLocatorTestConstants.TEST_KEY,
			RandomTestUtil.randomString());

		settings = _settingsLocatorHelper.getConfigurationBeanSettings(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		Assert.assertEquals(
			systemValue,
			settings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testRegisterConfigurationBeanClassWithRequiredConfiguration()
		throws Exception {

		Map<String, Settings> configurationBeanSettings =
			ReflectionTestUtil.getFieldValue(
				_settingsLocatorHelper, "_configurationBeanSettings");

		Assert.assertNull(
			configurationBeanSettings.get(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID));

		try (SafeCloseable safeCloseable = ReflectionTestUtil.invoke(
				_settingsLocatorHelper, "_registerConfigurationBeanClass",
				new Class<?>[] {Class.class},
				TestRequiredConfiguration.class)) {

			// Configuration with key

			Assert.assertNull(
				configurationBeanSettings.get(
					SettingsLocatorTestConstants.
						TEST_REQUIRED_CONFIGURATION_PID));

			saveConfiguration(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID,
				SettingsLocatorTestConstants.TEST_KEY,
				RandomTestUtil.randomString());

			Assert.assertNull(
				configurationBeanSettings.get(
					SettingsLocatorTestConstants.
						TEST_REQUIRED_CONFIGURATION_PID));

			// Configuration with required key

			String systemValue = saveConfiguration(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID,
				SettingsLocatorTestConstants.TEST_REQUIRED_KEY,
				RandomTestUtil.randomString());

			Settings settings = configurationBeanSettings.get(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID);

			Assert.assertEquals(
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
				settings.getValue(SettingsLocatorTestConstants.TEST_KEY, null));
			Assert.assertEquals(
				systemValue,
				settings.getValue(
					SettingsLocatorTestConstants.TEST_REQUIRED_KEY, null));

			// Existing configuration with key

			saveConfiguration(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID,
				SettingsLocatorTestConstants.TEST_KEY,
				RandomTestUtil.randomString());

			settings = configurationBeanSettings.get(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID);

			Assert.assertEquals(
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
				settings.getValue(SettingsLocatorTestConstants.TEST_KEY, null));
			Assert.assertEquals(
				systemValue,
				settings.getValue(
					SettingsLocatorTestConstants.TEST_REQUIRED_KEY, null));

			// Existing configuration with required key

			systemValue = saveConfiguration(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID,
				SettingsLocatorTestConstants.TEST_REQUIRED_KEY,
				RandomTestUtil.randomString());

			settings = configurationBeanSettings.get(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID);

			Assert.assertEquals(
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
				settings.getValue(SettingsLocatorTestConstants.TEST_KEY, null));
			Assert.assertEquals(
				systemValue,
				settings.getValue(
					SettingsLocatorTestConstants.TEST_REQUIRED_KEY, null));
		}

		Assert.assertNull(
			configurationBeanSettings.get(
				SettingsLocatorTestConstants.TEST_REQUIRED_CONFIGURATION_PID));
	}

	@Inject
	private SettingsLocatorHelper _settingsLocatorHelper;

}