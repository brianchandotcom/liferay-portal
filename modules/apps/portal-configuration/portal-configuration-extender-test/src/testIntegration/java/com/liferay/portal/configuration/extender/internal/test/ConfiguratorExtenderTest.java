/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.extender.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

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

		String symbolicName = "com.liferay.portal.configuration.extender";

		_bundle = BundleUtil.getBundle(bundle.getBundleContext(), symbolicName);

		Assert.assertNotNull(
			"Unable to find bundle with symbolic name: " + symbolicName,
			_bundle);
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfigurations("(service.pid=test.pid)");
		_deleteConfigurations("(service.pid=test.pid2)");
		_deleteConfigurations("(service.factoryPid=test.factory.pid)");
		_deleteConfigurations(
			"(service.factoryPid=" + _JAR_BUNDLE_SERVICE_FACTORY_PID + ")");
	}

	@Test
	public void testConfiguringEndpointsAndExtendersProgrammatically()
		throws Exception {

		Bundle bundle = _installJAR(_bundle.getBundleContext());

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					"(service.factoryPid=" + _JAR_BUNDLE_SERVICE_FACTORY_PID +
						")");

			Dictionary<String, Object> properties =
				configurations[0].getProperties();

			Assert.assertEquals(
				_JAR_BUNDLE_SERVICE_PROPERTY_VALUE,
				properties.get(_JAR_BUNDLE_SERVICE_PROPERTY_KEY));
		}
		finally {
			bundle.uninstall();
		}
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
			UnsafeSupplier<Dictionary<?, ?>, IOException>
				propertyUnsafeSupplier)
		throws Exception {

		Class<?> clazz = _bundle.loadClass(
			"com.liferay.portal.configuration.extender.internal." +
				"NamedConfigurationContent");

		Constructor<?> constructor = clazz.getDeclaredConstructor(
			String.class, String.class, UnsafeSupplier.class);

		return constructor.newInstance(factoryPid, pid, propertyUnsafeSupplier);
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

	private Bundle _installJAR(BundleContext bundleContext) throws Exception {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			Manifest manifest = new Manifest();

			Attributes attributes = manifest.getMainAttributes();

			attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
			attributes.put(new Attributes.Name("Bundle-ManifestVersion"), "2");
			attributes.put(
				new Attributes.Name("Bundle-Name"),
				"Liferay Foo Bundle for Testing");
			attributes.put(
				new Attributes.Name("Bundle-SymbolicName"),
				_JAR_BUNDLE_SYMBOLIC_NAME);
			attributes.put(new Attributes.Name("Bundle-Version"), "1.0.0");
			attributes.put(
				new Attributes.Name("Liferay-Configuration-Path"),
				"/" + _JAR_BUNDLE_CONFIGURATION_PATH);

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream, manifest)) {

				JarEntry jarEntry = new JarEntry(
					_JAR_BUNDLE_CONFIGURATION_PATH + "/");

				jarOutputStream.putNextEntry(jarEntry);

				jarOutputStream.closeEntry();

				jarEntry = new JarEntry(
					_JAR_BUNDLE_CONFIGURATION_PATH + "/" +
						_JAR_BUNDLE_SERVICE_PROPERTIES_FILE);

				jarOutputStream.putNextEntry(jarEntry);

				String value =
					_JAR_BUNDLE_SERVICE_PROPERTY_KEY + "=" +
						_JAR_BUNDLE_SERVICE_PROPERTY_VALUE;

				jarOutputStream.write(value.getBytes());

				jarOutputStream.closeEntry();
			}

			try (UnsyncByteArrayInputStream unsyncByteArrayInputStream =
					new UnsyncByteArrayInputStream(
						unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
						unsyncByteArrayOutputStream.size())) {

				Bundle bundle = bundleContext.installBundle(
					"testLocation", unsyncByteArrayInputStream);

				bundle.start();

				return bundle;
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

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				name, LoggerTestUtil.ERROR)) {

			ReflectionTestUtil.invoke(
				clazz, "_process",
				new Class<?>[] {
					ConfigurationAdmin.class, String.class, Collection.class
				},
				_configurationAdmin, namespace,
				Arrays.asList(namedConfigurationContents));
		}
	}

	private static final String _JAR_BUNDLE_CONFIGURATION_PATH =
		"configuration";

	private static final String _JAR_BUNDLE_SERVICE_FACTORY_PID =
		"com.liferay.portal.remote.cxf.common.configuration." +
			"CXFEndpointPublisherConfiguration";

	private static final String _JAR_BUNDLE_SERVICE_PROPERTIES_FILE =
		_JAR_BUNDLE_SERVICE_FACTORY_PID + "-test.properties";

	private static final String _JAR_BUNDLE_SERVICE_PROPERTY_KEY =
		"contextPath";

	private static final String _JAR_BUNDLE_SERVICE_PROPERTY_VALUE = "/test-ws";

	private static final String _JAR_BUNDLE_SYMBOLIC_NAME =
		"com.liferay.foo.bundle.for.testing.symbolic.name";

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private Bundle _bundle;

	private static class TestProperties<K, V> extends Hashtable<K, V> {

		private TestProperties(K key, V value) {
			put(key, value);
		}

	}

}