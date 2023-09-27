/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.internal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.definition.ConfigurationDDMFormDeclaration;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class ConfigurationDDMFormDeclarationUtilTest {

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(
			ConfigurationDDMFormDeclarationUtilTest.class);

		_bundleContext = _bundle.getBundleContext();

		_configuration = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.createFactoryConfiguration(
				"test.pid", StringPool.QUESTION));

		ConfigurationDDMFormDeclaration configurationDDMFormDeclaration =
			() -> TestConfigurationForm.class;

		_serviceRegistration = _registerConfigurationDDMFormDeclaration(
			configurationDDMFormDeclaration, _configuration.getPid());

		Bundle bundle = BundleUtil.getBundle(
			_bundleContext, "com.liferay.configuration.admin.web");

		if (bundle == null) {
			throw new IllegalStateException(
				"com.liferay.configuration.admin.web bundle not found");
		}

		Class<?> clazz = bundle.loadClass(
			"com.liferay.configuration.admin.web.internal.util." +
				"ConfigurationDDMFormDeclarationUtil");

		_method = clazz.getDeclaredMethod(
			"getConfigurationDDMFormClass", String.class);
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testGetConfigurationFormClassFromPid() throws Exception {
		Assert.assertEquals(
			TestConfigurationForm.class,
			_method.invoke(null, _configuration.getPid()));
	}

	private ServiceRegistration<ConfigurationDDMFormDeclaration>
		_registerConfigurationDDMFormDeclaration(
			ConfigurationDDMFormDeclaration configurationDDMFormDeclaration,
			String configurationPid) {

		return _bundleContext.registerService(
			ConfigurationDDMFormDeclaration.class,
			configurationDDMFormDeclaration,
			HashMapDictionaryBuilder.<String, Object>put(
				"configurationPid", configurationPid
			).build());
	}

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private Configuration _configuration;
	private Method _method;
	private ServiceRegistration<ConfigurationDDMFormDeclaration>
		_serviceRegistration;

	private class TestConfigurationForm {
	}

}