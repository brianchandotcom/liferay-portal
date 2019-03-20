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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.Serializable;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class ConfigurationProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private static ConfigurationProvider _configurationProvider;

	@Before
	public void setUp() throws Exception {
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
		long companyId = RandomTestUtil.randomLong();

		_createFactoryConfiguration(
			_PID, ExtendedObjectClassDefinition.Scope.COMPANY, companyId);

		Assert.assertEquals(1, _getExistingFactoryConfigurationsCount(_PID));

		_configurationProvider.deleteCompanyConfiguration(
			TestConfiguration.class, companyId);

		Assert.assertEquals(0, _getExistingConfigurationCount(_PID));
	}

	@Test
	public void testDeleteGroupConfiguration() throws Exception {
		long groupId = RandomTestUtil.randomLong();

		_createFactoryConfiguration(
			_PID, ExtendedObjectClassDefinition.Scope.GROUP, groupId);

		Assert.assertEquals(1, _getExistingFactoryConfigurationsCount(_PID));

		_configurationProvider.deleteGroupConfiguration(
			TestConfiguration.class, groupId);

		Assert.assertEquals(0, _getExistingFactoryConfigurationsCount(_PID));
	}

	@Test
	public void testDeletePortletInstanceConfiguration() throws Exception {
		String portletInstanceId = RandomTestUtil.randomString();

		_createFactoryConfiguration(
			_PID, ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
			portletInstanceId);

		Assert.assertEquals(1, _getExistingFactoryConfigurationsCount(_PID));

		_configurationProvider.deletePortletInstanceConfiguration(
			TestConfiguration.class, portletInstanceId);

		Assert.assertEquals(0, _getExistingFactoryConfigurationsCount(_PID));
	}

	@Test
	public void testDeleteSystemConfiguration() throws Exception {
		_createConfiguration(_PID);

		Assert.assertEquals(1, _getExistingConfigurationCount(_PID));

		_configurationProvider.deleteSystemConfiguration(
			TestConfiguration.class);

		Assert.assertEquals(0, _getExistingConfigurationCount(_PID));
	}

	@Test
	public void testSaveCompanyConfiguration() throws Exception {
		_properties.put("key1", "companyValue1");
		_properties.put("key2", "companyValue2");

		long companyId = RandomTestUtil.randomLong();

		_configurationProvider.saveCompanyConfiguration(
			TestConfiguration.class, companyId, _properties);

		_configuration = _getFactoryConfiguration(_PID);

		assertFactoryPropertyValues(
			_properties, _configuration.getProperties(),
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			companyId);
	}

	@Test
	public void testSaveGroupConfiguration() throws Exception {
		_properties.put("key1", "groupValue1");
		_properties.put("key2", "groupValue2");

		long groupId = RandomTestUtil.randomLong();

		_configurationProvider.saveGroupConfiguration(
			TestConfiguration.class, groupId, _properties);

		_configuration = _getFactoryConfiguration(_PID);

		assertFactoryPropertyValues(
			_properties, _configuration.getProperties(),
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
			groupId);
	}

	@Test
	public void testSavePortletInstanceConfiguration() throws Exception {
		_properties.put("key1", "portletInstanceValue1");
		_properties.put("key2", "portletInstanceValue2");

		String portletInstanceId = RandomTestUtil.randomString();

		_configurationProvider.savePortletInstanceConfiguration(
			TestConfiguration.class, portletInstanceId, _properties);

		_configuration = _getFactoryConfiguration(_PID);

		assertFactoryPropertyValues(
			_properties, _configuration.getProperties(),
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPropertyKey(),
			portletInstanceId);
	}

	protected void assertFactoryPropertyValues(
		Dictionary<String, Object> properties,
		Dictionary<String, Object> configurationProperties, String expectedKey,
		Object expectedValue) {

		Assert.assertEquals(
			expectedValue, configurationProperties.get(expectedKey));

		assertPropertyValues(properties, configurationProperties);
	}

	@Test
	public void testSaveSystemConfiguration() throws Exception {
		_properties.put("key1", "systemValue1");
		_properties.put("key2", "systemValue2");

		_configurationProvider.saveSystemConfiguration(
			TestConfiguration.class, _properties);

		_configuration = _getConfiguration(_PID);

		assertPropertyValues(_properties, _configuration.getProperties());
	}

	private int _getExistingConfigurationCount(String pid, String propertyName)
		throws Exception {

		Configuration[] configurations = _getConfigurations(pid, propertyName);

		if (configurations == null) {
			return 0;
		}

		return configurations.length;
	}

	private Configuration[] _getConfigurations(String pid, String propertyName)
		throws Exception {

		String pidFilter = StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, propertyName, StringPool.EQUAL, pid,
			StringPool.CLOSE_PARENTHESIS);

		return _configurationAdmin.listConfigurations(pidFilter);
	}

	private int _getExistingConfigurationCount(String pid) throws Exception {
		return _getExistingConfigurationCount(pid, Constants.SERVICE_PID);
	}

	private int _getExistingFactoryConfigurationsCount(String pid)
		throws Exception {

		return _getExistingConfigurationCount(
			pid + ".scoped", "service.factoryPid");
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

	private Configuration _getConfiguration(String pid) throws IOException {
		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private Configuration _getFactoryConfiguration(String factoryPid)
		throws Exception {

		Configuration[] configurations = _getConfigurations(
			factoryPid + ".scoped", "service.factoryPid");

		if (configurations != null) {
			return configurations[0];
		}

		return null;
	}

	private void _createFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK)
		throws Exception {

		_properties.put(scope.getPropertyKey(), scopePK);

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				factoryPid + ".scoped", StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(configuration, _properties);
	}

	private void _createConfiguration(String pid) throws Exception {
		_properties.put("key1", "value1");
		_properties.put("key2", "value2");

		Configuration configuration = _getConfiguration(pid);

		ConfigurationTestUtil.saveConfiguration(configuration, _properties);
	}

	private Configuration _configuration;
	private Dictionary<String, Object> _properties;

	private static final String _PID = "test.pid";

	@Meta.OCD(id = "test.pid")
	private interface TestConfiguration {

		@Meta.AD
		public String key1();

		@Meta.AD
		public String key2();

	}

}