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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class ConfiguratorExtenderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(ConfiguratorExtenderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String symbolicName = "com.liferay.portal.configuration.extender";

		for (Bundle curBundle : bundleContext.getBundles()) {
			if (symbolicName.equals(curBundle.getSymbolicName())) {
				_bundle = curBundle;

				break;
			}
		}

		Assert.assertNotNull(
			"Unable to find bundle with symbolic name: " + symbolicName,
			_bundle);
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfigurations("(service.pid=test.pid)");
		_deleteConfigurations("(service.pid=test.pid2)");
		_deleteConfigurations("(service.factoryPid=test.factory.pid)");
	}

	@Test
	public void testExceptionInSupplierDoesNotStopExtension() throws Exception {
		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				null, "test.pid",
				() -> {
					throw new RuntimeException("This should be handled");
				}),
			_createNamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=test.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);
	}

	@Test
	public void testFactoryConfiguration() throws Exception {
		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=test.factory.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);
	}

	@Test
	public void testFactoryConfigurationCreatesAnother() throws Exception {
		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				"test.factory.pid", StringPool.QUESTION);

		configuration.update(new TestProperties<>("key", "value"));

		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=test.factory.pid)");

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

		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")),
			_createNamedConfigurationContent(
				"test.factory.pid", "default2",
				() -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=test.factory.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 2, configurations.length);
	}

	@Test
	public void testFactoryConfigurationWithClashingMultipleContents()
		throws Exception {

		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")),
			_createNamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=test.factory.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testFactoryConfigurationWithClashingMultipleContentsAndDifferentNamespaces()
		throws Exception {

		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")));

		_processConfigurations(
			"otherBundle",
			_createNamedConfigurationContent(
				"test.factory.pid", "default",
				() -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=test.factory.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 2, configurations.length);
	}

	@Test
	public void testSingleConfiguration() throws Exception {
		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=test.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);
	}

	@Test
	public void testSingleConfigurationRespectsExistingConfiguration()
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			"test.pid", StringPool.QUESTION);

		configuration.update(new TestProperties<>("key", "value"));

		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=test.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithClashingMultipleContents()
		throws Exception {

		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")),
			_createNamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=test.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithClashingMultipleContentsAndDifferentNamespaces()
		throws Exception {

		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")));

		_processConfigurations(
			"otherBundle",
			_createNamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value2")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=test.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testSingleConfigurationWithMultipleContents() throws Exception {
		_processConfigurations(
			"aBundle",
			_createNamedConfigurationContent(
				null, "test.pid", () -> new TestProperties<>("key", "value")),
			_createNamedConfigurationContent(
				null, "test.pid2", () -> new TestProperties<>("key", "value")));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=test.pid)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		configurations = _configurationAdmin.listConfigurations(
			"(service.pid=test.pid2)");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);
	}

	private Object _createNamedConfigurationContent(
			String factoryPid, String pid,
			UnsafeSupplier<Dictionary<?, ?>, IOException> propertySupplier)
		throws Exception {

		Class<?> clazz = _bundle.loadClass(
			"com.liferay.portal.configuration.extender.internal." +
				"NamedConfigurationContent");

		Constructor<?> constructor = clazz.getDeclaredConstructor(
			String.class, String.class, UnsafeSupplier.class);

		return constructor.newInstance(factoryPid, pid, propertySupplier);
	}

	private void _deleteConfigurations(String filter) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filter);

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				configuration.delete();
			}
		}
	}

	private void _processConfigurations(
			String namespace, Object... namedConfigurationContents)
		throws Exception {

		String name =
			"com.liferay.portal.configuration.extender.internal." +
				"ConfiguratorExtender";

		Class<?> clazz = _bundle.loadClass(name);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(name, Level.ERROR)) {

			ReflectionTestUtil.invoke(
				clazz, "_process",
				new Class<?>[] {
					ConfigurationAdmin.class, String.class, Collection.class
				},
				_configurationAdmin, namespace,
				Arrays.asList(namedConfigurationContents));
		}
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private Bundle _bundle;

	private static class TestProperties<K, V> extends Hashtable<K, V> {

		private TestProperties(K key, V value) {
			put(key, value);
		}

	}

}