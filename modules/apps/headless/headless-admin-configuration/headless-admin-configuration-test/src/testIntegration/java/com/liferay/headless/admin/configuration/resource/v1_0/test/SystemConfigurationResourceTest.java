/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.configuration.client.dto.v1_0.SystemConfiguration;
import com.liferay.headless.admin.configuration.sample.configuration.TestConfiguration;
import com.liferay.headless.admin.configuration.sample.configuration.TestFactoryConfiguration;
import com.liferay.headless.admin.configuration.test.util.ConfigurationTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Thiago Buarque
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-65399"), @FeatureFlag("LPS-155284")}
)
@RunWith(Arquillian.class)
public class SystemConfigurationResourceTest
	extends BaseSystemConfigurationResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		_safeCloseables.add(
			ReflectionTestUtil.invoke(
				_settingsLocatorHelper, "_registerConfigurationBeanClass",
				new Class<?>[] {Class.class}, TestConfiguration.class));
		_safeCloseables.add(
			ReflectionTestUtil.invoke(
				_settingsLocatorHelper, "_registerConfigurationBeanClass",
				new Class<?>[] {Class.class}, TestFactoryConfiguration.class));
	}

	@AfterClass
	public static void tearDownClass() {
		for (SafeCloseable safeCloseable : _safeCloseables) {
			safeCloseable.close();
		}
	}

	@Test
	public void testGetSystemConfigurationWithPasswordField() throws Exception {
		PropsUtil.set(
			PropsKeys.MODULE_FRAMEWORK_EXPORT_PASSWORD_ATTRIBUTES, "true");

		SystemConfiguration systemConfiguration =
			testGetSystemConfiguration_addSystemConfiguration();

		systemConfiguration =
			systemConfigurationResource.getSystemConfiguration(
				systemConfiguration.getExternalReferenceCode());

		Map<String, Object> properties = systemConfiguration.getProperties();

		Assert.assertNotNull(properties.get("passwordStringKey"));

		PropsUtil.set(
			PropsKeys.MODULE_FRAMEWORK_EXPORT_PASSWORD_ATTRIBUTES, "false");

		systemConfiguration =
			systemConfigurationResource.getSystemConfiguration(
				systemConfiguration.getExternalReferenceCode());

		properties = systemConfiguration.getProperties();

		Assert.assertNull(properties.get("passwordStringKey"));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"properties"};
	}

	@Override
	protected SystemConfiguration randomSystemConfiguration() throws Exception {
		return new SystemConfiguration() {
			{
				externalReferenceCode =
					ConfigurationTestUtil.TEST_FACTORY_CONFIGURATION_PID;
				properties =
					ConfigurationTestUtil.
						getRandomTestFactoryConfigurationProperties(null, null);
			}
		};
	}

	@Override
	protected SystemConfiguration
			testGetSystemConfiguration_addSystemConfiguration()
		throws Exception {

		return systemConfigurationResource.postSystemConfiguration(
			new SystemConfiguration() {
				{
					externalReferenceCode =
						ConfigurationTestUtil.TEST_CONFIGURATION_PID;
					properties =
						ConfigurationTestUtil.
							getRandomTestConfigurationProperties(
								"companyWebId", testCompany.getWebId());
				}
			});
	}

	@Override
	protected SystemConfiguration
			testGetSystemConfigurationsPage_addSystemConfiguration(
				SystemConfiguration systemConfiguration)
		throws Exception {

		return systemConfigurationResource.postSystemConfiguration(
			systemConfiguration);
	}

	@Override
	protected SystemConfiguration
			testPostSystemConfiguration_addSystemConfiguration(
				SystemConfiguration systemConfiguration)
		throws Exception {

		return systemConfigurationResource.postSystemConfiguration(
			systemConfiguration);
	}

	@Override
	protected SystemConfiguration
			testPutSystemConfiguration_addSystemConfiguration()
		throws Exception {

		return systemConfigurationResource.postSystemConfiguration(
			randomSystemConfiguration());
	}

	private static final List<SafeCloseable> _safeCloseables =
		new ArrayList<>();

	@Inject
	private static SettingsLocatorHelper _settingsLocatorHelper;

}