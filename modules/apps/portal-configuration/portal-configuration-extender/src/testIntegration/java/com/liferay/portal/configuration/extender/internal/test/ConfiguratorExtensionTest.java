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

package com.liferay.portal.configuration.extender.internal.test;

import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.petra.io.DummyOutputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.extender.internal.ConfiguratorExtension;
import com.liferay.portal.configuration.extender.internal.NamedConfigurationContent;

import java.io.IOException;
import java.io.PrintStream;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.utils.log.Logger;

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
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
@BndFile("test-bnd.bnd")
@RunWith(Arquillian.class)
public class ConfiguratorExtensionTest {

	@Before
	public void setUp() throws InvalidSyntaxException, IOException {
		_serviceReference = _bundleContext.getServiceReference(
			ConfigurationAdmin.class);

		_configurationAdmin = _bundleContext.getService(_serviceReference);

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				configuration.delete();
			}
		}

		configurations = _configurationAdmin.listConfigurations(null);

		Assert.assertNull(configurations);
	}

	@After
	public void tearDown() {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testExceptionInSupplierDoesNotStopExtension() throws Exception {
		ConfiguratorExtension configuratorExtension = new ConfiguratorExtension(
			_configurationAdmin, new Logger(_bundleContext), "aBundle",
			Arrays.asList(
				new NamedConfigurationContent(
					null, "test.pid",
					() -> {
						throw new RuntimeException("This should be handled");
					}),
				new NamedConfigurationContent(
					null, "test.pid",
					() -> new TestProperties<>("key", "value"))));

		PrintStream printStream = System.err;

		System.setErr(new PrintStream(new DummyOutputStream()));

		try {
			configuratorExtension.start();
		}
		finally {
			System.setErr(printStream);
		}

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);
	}

	@Test
	public void testFactoryConfiguration() throws Exception {
		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);
	}

	@Test
	public void testFactoryConfigurationCreatesAnother() throws Exception {
		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				"test.factory.pid", StringPool.QUESTION);

		configuration.update(new TestProperties<>("key", "value"));

		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 2, configurations.length);

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
			new NamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")),
			new NamedConfigurationContent(
				"test.factory.pid", "default2",
				() -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 2, configurations.length);
	}

	@Test
	public void testFactoryConfigurationWithClashingMultipleContents()
		throws Exception {

		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")),
			new NamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testFactoryConfigurationWithClashingMultipleContentsAndDifferentNamespaces()
		throws Exception {

		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")));

		_startExtension(
			"otherBundle",
			new NamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 2, configurations.length);
	}

	@Test
	public void testSingleConfiguration() throws Exception {
		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);
	}

	@Test
	public void testSingleConfigurationRespectsExistingConfiguration()
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			"test.pid", StringPool.QUESTION);

		configuration.update(new TestProperties<>("key", "value"));

		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithClashingMultipleContents()
		throws Exception {

		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")),
			new NamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithClashingMultipleContentsAndDifferentNamespaces()
		throws Exception {

		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")));

		_startExtension(
			"otherBundle",
			new NamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithMultipleContents() throws Exception {
		_startExtension(
			"aBundle",
			new NamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")),
			new NamedConfigurationContent(
				null, "test.pid2", () -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 2, configurations.length);
	}

	private void _startExtension(
			String namespace,
			NamedConfigurationContent... namedConfigurationContents)
		throws Exception {

		ConfiguratorExtension configuratorExtension = new ConfiguratorExtension(
			_configurationAdmin, new Logger(_bundleContext), namespace,
			Arrays.asList(namedConfigurationContents));

		configuratorExtension.start();
	}

	@ArquillianResource
	private BundleContext _bundleContext;

	private ConfigurationAdmin _configurationAdmin;
	private ServiceReference<ConfigurationAdmin> _serviceReference;

	private static class TestProperties<K, V> extends Hashtable<K, V> {

		private TestProperties(K key, V value) {
			put(key, value);
		}

	}

}