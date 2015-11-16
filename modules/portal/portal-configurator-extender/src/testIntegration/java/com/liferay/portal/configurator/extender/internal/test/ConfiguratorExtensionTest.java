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

package com.liferay.portal.configurator.extender.internal.test;

import com.liferay.portal.configurator.extender.Configuration;
import com.liferay.portal.configurator.extender.internal.ConfiguratorExtension;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;

import org.arquillian.liferay.deploymentscenario.annotations.BndFile;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
@BndFile("test-bnd.bnd")
@RunWith(Arquillian.class)
public class ConfiguratorExtensionTest {

	@Before
	public void setUp() throws InvalidSyntaxException, IOException {
		_dictionary1.put("key", "value");
		_dictionary2.put("key", "value2");

		_serviceReference = _bundleContext.getServiceReference(
			ConfigurationAdmin.class);

		_configurationAdmin = _bundleContext.getService(_serviceReference);

		org.osgi.service.cm.Configuration[] configurations =
			_configurationAdmin.listConfigurations(null);

		if (configurations != null) {
			for (org.osgi.service.cm.Configuration configuration :
					configurations) {

				configuration.delete();
			}
		}

		configurations = _configurationAdmin.listConfigurations(null);

		Assert.assertNull(configurations);
	}

	@After
	public void tearDown() throws Exception {
		for (ConfiguratorExtension configurationExtension :
				_configurationExtensions) {

			configurationExtension.destroy();
		}

		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testFactoryConfiguration() throws Exception {
		_startExtension(
			"aBundle",
			new Configuration("test.factory.pid", "default", _dictionary1));

		org.osgi.service.cm.Configuration[] osgiconfigurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(1, osgiconfigurations.length);

		Dictionary<String, Object> dictionary =
			osgiconfigurations[0].getProperties();

		Assert.assertEquals("value", dictionary.get("key"));
	}

	@Test
	public void testFactoryConfigurationCreatesAnother() throws Exception {
		org.osgi.service.cm.Configuration osgiConfiguration =
			_configurationAdmin.createFactoryConfiguration(
				"test.factory.pid", null);

		osgiConfiguration.update(_dictionary1);

		_startExtension(
			"aBundle",
			new Configuration("test.factory.pid", "default", _dictionary2));

		org.osgi.service.cm.Configuration[] configurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(2, configurations.length);

		Assert.assertNotNull(
			_configurationAdmin.listConfigurations("(key=value)"));

		Assert.assertNotNull(
			_configurationAdmin.listConfigurations("(key=value2)"));
	}

	@Test
	public void testFactoryConfigurationWithClashingFactoryPid()
		throws Exception {

		_startExtension(
			"aBundle",
			new Configuration("test.factory.pid", "default", _dictionary1),
			new Configuration("test.factory.pid", "default2", _dictionary1));

		org.osgi.service.cm.Configuration[] osgiConfigurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(2, osgiConfigurations.length);
	}

	@Test
	public void testFactoryConfigurationWithClashingMultipleContents()
		throws Exception {

		_startExtension(
			"aBundle",
			new Configuration("test.factory.pid", "default", _dictionary1),
			new Configuration("test.factory.pid", "default", _dictionary2));

		org.osgi.service.cm.Configuration[] osgiConfigurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(1, osgiConfigurations.length);

		Dictionary<String, Object> properties =
			osgiConfigurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testFactoryConfigurationWithClashingMultipleContentsAndDifferentNamespaces()
		throws Exception {

		_startExtension(
			"aBundle",
			new Configuration("test.factory.pid", "default", _dictionary1));

		_startExtension(
			"otherBundle",
			new Configuration("test.factory.pid", "default", _dictionary1));

		org.osgi.service.cm.Configuration[] osgiConfigurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(2, osgiConfigurations.length);
	}

	@Test
	public void testSingleConfiguration() throws Exception {
		_startExtension(
			"aBundle",
			new Configuration("test.factory.pid", "default", _dictionary1));

		org.osgi.service.cm.Configuration[] osgiConfigurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(1, osgiConfigurations.length);
	}

	@Test
	public void testSingleConfigurationRespectsExistingConfiguration()
		throws Exception {

		org.osgi.service.cm.Configuration osgiConfiguration =
			_configurationAdmin.getConfiguration("test.pid", null);

		osgiConfiguration.update(_dictionary1);

		_startExtension("aBundle", new Configuration("test.pid", _dictionary2));

		org.osgi.service.cm.Configuration[] osgiConfigurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(1, osgiConfigurations.length);

		Dictionary<String, Object> properties =
			osgiConfigurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithClashingMultipleContents()
		throws Exception {

		_startExtension(
			"aBundle", new Configuration("test.pid", _dictionary1),
			new Configuration("test.pid", _dictionary2));

		org.osgi.service.cm.Configuration[] configurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithClashingMultipleContentsAndDifferentNamespaces()
		throws Exception {

		_startExtension("aBundle", new Configuration("test.pid", _dictionary1));

		_startExtension(
			"otherBundle", new Configuration("test.pid", _dictionary2));

		org.osgi.service.cm.Configuration[] configurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithMultipleContents() throws Exception {
		_startExtension(
			"aBundle", new Configuration("test.pid", _dictionary1),
			new Configuration("test.pid2", _dictionary2));

		org.osgi.service.cm.Configuration[] configurations =
			_configurationAdmin.listConfigurations(null);

		Assert.assertEquals(2, configurations.length);
	}

	private void _startExtension(
			String namespace, Configuration... configurations)
		throws Exception {

		ConfiguratorExtension configuratorExtension = new ConfiguratorExtension(
			_configurationAdmin, namespace, configurations);

		configuratorExtension.start();

		_configurationExtensions.add(configuratorExtension);
	}

	@ArquillianResource
	private BundleContext _bundleContext;

	private ConfigurationAdmin _configurationAdmin;
	private final Collection<ConfiguratorExtension> _configurationExtensions =
		new ArrayList<>();
	private final Dictionary<String, Object> _dictionary1 =
		new HashMapDictionary<>();
	private final Dictionary<String, Object> _dictionary2 =
		new HashMapDictionary<>();
	private ServiceReference<ConfigurationAdmin> _serviceReference;

}