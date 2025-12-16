/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.configuration.client.dto.v1_0.InstanceConfiguration;
import com.liferay.headless.admin.configuration.client.problem.Problem;
import com.liferay.headless.admin.configuration.test.configuration.TestConfiguration;
import com.liferay.headless.admin.configuration.test.configuration.TestFactoryConfiguration;
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
public class InstanceConfigurationResourceTest
	extends BaseInstanceConfigurationResourceTestCase {

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

	@Override
	@Test
	public void testGetInstanceConfiguration() throws Exception {
		super.testGetInstanceConfiguration();

		_testGetInstanceConfigurationFromConfigurationScreen();
		_testGetInstanceConfigurationWithPasswordKey();
	}

	@Override
	@Test
	public void testPostInstanceConfiguration() throws Exception {
		super.testPostInstanceConfiguration();

		InstanceConfiguration randomInstanceConfiguration =
			_randomInstanceConfigurationFromConfigurationScreen();

		InstanceConfiguration postInstanceConfiguration =
			testPostInstanceConfiguration_addInstanceConfiguration(
				randomInstanceConfiguration);

		assertEquals(randomInstanceConfiguration, postInstanceConfiguration);
		assertValid(postInstanceConfiguration);
	}

	@Override
	@Test
	public void testPutInstanceConfiguration() throws Exception {
		super.testPutInstanceConfiguration();

		InstanceConfiguration postInstanceConfiguration =
			instanceConfigurationResource.postInstanceConfiguration(
				_randomInstanceConfigurationFromConfigurationScreen());

		InstanceConfiguration randomInstanceConfiguration =
			_randomInstanceConfigurationFromConfigurationScreen();

		InstanceConfiguration putInstanceConfiguration =
			instanceConfigurationResource.putInstanceConfiguration(
				postInstanceConfiguration.getExternalReferenceCode(),
				randomInstanceConfiguration);

		assertEquals(randomInstanceConfiguration, putInstanceConfiguration);
		assertValid(putInstanceConfiguration);

		InstanceConfiguration getInstanceConfiguration =
			instanceConfigurationResource.getInstanceConfiguration(
				putInstanceConfiguration.getExternalReferenceCode());

		assertEquals(randomInstanceConfiguration, getInstanceConfiguration);
		assertValid(getInstanceConfiguration);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"properties"};
	}

	@Override
	protected InstanceConfiguration randomInstanceConfiguration()
		throws Exception {

		return new InstanceConfiguration() {
			{
				externalReferenceCode =
					ConfigurationTestUtil.TEST_FACTORY_CONFIGURATION_PID;
				properties =
					ConfigurationTestUtil.
						getRandomTestFactoryConfigurationProperties(
							"companyWebId", testCompany.getWebId());
			}
		};
	}

	@Override
	protected InstanceConfiguration
			testGetInstanceConfiguration_addInstanceConfiguration()
		throws Exception {

		return instanceConfigurationResource.postInstanceConfiguration(
			new InstanceConfiguration() {
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
	protected InstanceConfiguration
			testGetInstanceConfigurationsPage_addInstanceConfiguration(
				InstanceConfiguration instanceConfiguration)
		throws Exception {

		return instanceConfigurationResource.postInstanceConfiguration(
			instanceConfiguration);
	}

	@Override
	protected InstanceConfiguration
			testPostInstanceConfiguration_addInstanceConfiguration(
				InstanceConfiguration instanceConfiguration)
		throws Exception {

		return instanceConfigurationResource.postInstanceConfiguration(
			instanceConfiguration);
	}

	@Override
	protected InstanceConfiguration
			testPutInstanceConfiguration_addInstanceConfiguration()
		throws Exception {

		return instanceConfigurationResource.postInstanceConfiguration(
			randomInstanceConfiguration());
	}

	private InstanceConfiguration
			_randomInstanceConfigurationFromConfigurationScreen()
		throws Exception {

		return new InstanceConfiguration() {
			{
				externalReferenceCode = "company-configuration-key";
				properties =
					ConfigurationTestUtil.
						getRandomConfigurationScreenProperties(
							"companyWebId", testCompany.getWebId());
			}
		};
	}

	private void _testGetInstanceConfigurationFromConfigurationScreen()
		throws Exception {

		InstanceConfiguration instanceConfiguration =
			_randomInstanceConfigurationFromConfigurationScreen();

		try {
			instanceConfigurationResource.getInstanceConfiguration(
				instanceConfiguration.getExternalReferenceCode());
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}

		InstanceConfiguration postInstanceConfiguration =
			instanceConfigurationResource.postInstanceConfiguration(
				instanceConfiguration);

		InstanceConfiguration getInstanceConfiguration =
			instanceConfigurationResource.getInstanceConfiguration(
				postInstanceConfiguration.getExternalReferenceCode());

		assertEquals(postInstanceConfiguration, getInstanceConfiguration);
		assertValid(getInstanceConfiguration);
	}

	private void _testGetInstanceConfigurationWithPasswordKey()
		throws Exception {

		PropsUtil.set(
			PropsKeys.MODULE_FRAMEWORK_EXPORT_PASSWORD_ATTRIBUTES, "true");

		InstanceConfiguration instanceConfiguration =
			testGetInstanceConfiguration_addInstanceConfiguration();

		instanceConfiguration =
			instanceConfigurationResource.getInstanceConfiguration(
				instanceConfiguration.getExternalReferenceCode());

		Map<String, Object> properties = instanceConfiguration.getProperties();

		Assert.assertNotNull(properties.get("passwordStringKey"));

		PropsUtil.set(
			PropsKeys.MODULE_FRAMEWORK_EXPORT_PASSWORD_ATTRIBUTES, "false");

		instanceConfiguration =
			instanceConfigurationResource.getInstanceConfiguration(
				instanceConfiguration.getExternalReferenceCode());

		properties = instanceConfiguration.getProperties();

		Assert.assertNull(properties.get("passwordStringKey"));
	}

	private static final List<SafeCloseable> _safeCloseables =
		new ArrayList<>();

	@Inject
	private static SettingsLocatorHelper _settingsLocatorHelper;

}