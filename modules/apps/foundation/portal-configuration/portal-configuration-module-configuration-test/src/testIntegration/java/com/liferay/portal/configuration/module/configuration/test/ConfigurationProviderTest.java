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

package com.liferay.portal.configuration.module.configuration.test;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.osgi.util.test.OSGiServiceUtil;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class ConfigurationProviderTest {

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(ConfigurationProviderTest.class);

		_bundleContext = _bundle.getBundleContext();

		_properties = new Hashtable<>();
	}

	@After
	public void tearDown() throws Exception {
		if (_configuration != null) {
			_configuration.delete();
		}
	}

	@Test
	public void testDeleteCompanyConfiguration() throws Exception {
		String scopedPid = _pid + "__COMPANY__12345";

		_createConfiguration(scopedPid);

		Assert.assertEquals(1, _getExistingConfigurationCount(scopedPid));

		ConfigurationProviderUtil.deleteCompanyConfiguration(
			TestConfiguration.class, 12345);

		Assert.assertEquals(0, _getExistingConfigurationCount(scopedPid));
	}

	@Test
	public void testDeleteGroupConfiguration() throws Exception {
		String scopedPid = _pid + "__GROUP__12345";

		_createConfiguration(scopedPid);

		Assert.assertEquals(1, _getExistingConfigurationCount(scopedPid));

		ConfigurationProviderUtil.deleteGroupConfiguration(
			TestConfiguration.class, 12345);

		Assert.assertEquals(0, _getExistingConfigurationCount(scopedPid));
	}

	@Test
	public void testDeletePortletInstanceConfiguration() throws Exception {
		String scopedPid = _pid + "__PORTLET_INSTANCE__12345";

		_createConfiguration(scopedPid);

		Assert.assertEquals(1, _getExistingConfigurationCount(scopedPid));

		ConfigurationProviderUtil.deletePortletInstanceConfiguration(
			TestConfiguration.class, "12345");

		Assert.assertEquals(0, _getExistingConfigurationCount(scopedPid));
	}

	@Test
	public void testDeleteSystemConfiguration() throws Exception {
		_createConfiguration(_pid);

		Assert.assertEquals(1, _getExistingConfigurationCount(_pid));

		ConfigurationProviderUtil.deleteSystemConfiguration(
			TestConfiguration.class);

		Assert.assertEquals(0, _getExistingConfigurationCount(_pid));
	}

	@Test
	public void testSaveCompanyConfiguration() throws Exception {
		_properties.put("key1", "companyValue1");
		_properties.put("key2", "companyValue2");

		ConfigurationProviderUtil.saveCompanyConfiguration(
			TestConfiguration.class, 12345, _properties);

		String scopedPid = _pid + "__COMPANY__12345";

		_configuration = _getConfiguration(scopedPid, StringPool.QUESTION);

		assertPropertyValues(_properties, _configuration.getProperties());
	}

	@Test
	public void testSaveGroupConfiguration() throws Exception {
		_properties.put("key1", "groupValue1");
		_properties.put("key2", "groupValue2");

		ConfigurationProviderUtil.saveGroupConfiguration(
			TestConfiguration.class, 12345, _properties);

		String scopedPid = _pid + "__GROUP__12345";

		_configuration = _getConfiguration(scopedPid, StringPool.QUESTION);

		assertPropertyValues(_properties, _configuration.getProperties());
	}

	@Test
	public void testSavePortletInstanceConfiguration() throws Exception {
		_properties.put("key1", "portletInstanceValue1");
		_properties.put("key2", "portletInstanceValue2");

		ConfigurationProviderUtil.savePortletInstanceConfiguration(
			TestConfiguration.class, "12345", _properties);

		String scopedPid = _pid + "__PORTLET_INSTANCE__12345";

		_configuration = _getConfiguration(scopedPid, StringPool.QUESTION);

		assertPropertyValues(_properties, _configuration.getProperties());
	}

	@Test
	public void testSaveSystemConfiguration() throws Exception {
		_properties.put("key1", "systemValue1");
		_properties.put("key2", "systemValue2");

		ConfigurationProviderUtil.saveSystemConfiguration(
			TestConfiguration.class, _properties);

		_configuration = _getConfiguration(_pid, StringPool.QUESTION);

		assertPropertyValues(_properties, _configuration.getProperties());
	}

	private int _getExistingConfigurationCount(String pid) throws Exception {
		StringBundler pidFilter = new StringBundler(5);

		pidFilter.append(StringPool.OPEN_PARENTHESIS);
		pidFilter.append(Constants.SERVICE_PID);
		pidFilter.append(StringPool.EQUAL);
		pidFilter.append(pid);
		pidFilter.append(StringPool.CLOSE_PARENTHESIS);

		Configuration[] configurations = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.listConfigurations(
				pidFilter.toString()));

		if (configurations == null) {
			return 0;
		}

		return configurations.length;
	}

	protected void assertPropertyValues(
		Dictionary<String, Object> properties,
		Dictionary<String, Object> configurationProperties) {

		Assert.assertNotNull(configurationProperties);

		for (Enumeration keys = properties.keys(); keys.hasMoreElements();) {
			String key = (String)keys.nextElement();

			Assert.assertEquals(
				properties.get(key), configurationProperties.get(key));
		}
	}

	private Configuration _getConfiguration(String pid, String location)
		throws IOException {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, location));
	}

	private void _createConfiguration(String pid) throws Exception {
		_properties.put("key1", "value1");
		_properties.put("key2", "value2");

		Configuration configuration = _getConfiguration(
			pid, StringPool.QUESTION);

		configuration.update(_properties);
	}

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private Configuration _configuration;
	private Dictionary<String, Object> _properties;

	private static final String _pid = "test.pid";

	@Meta.OCD(id = "test.pid")
	private interface TestConfiguration {

		@Meta.AD
		public String key1();

		@Meta.AD
		public String key2();

	}

}