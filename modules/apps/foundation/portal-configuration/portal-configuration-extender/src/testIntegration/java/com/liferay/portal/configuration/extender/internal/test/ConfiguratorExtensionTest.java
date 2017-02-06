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
import com.liferay.portal.configuration.extender.ConfigurationDescription;
import com.liferay.portal.configuration.extender.ConfigurationDescriptionFactory;
import com.liferay.portal.configuration.extender.FactoryConfigurationDescription;
import com.liferay.portal.configuration.extender.NamedConfigurationContent;
import com.liferay.portal.configuration.extender.SingleConfigurationDescription;
import com.liferay.portal.configuration.extender.internal.ConfiguratorExtension;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Supplier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	public void tearDown() throws Exception {
		for (ConfiguratorExtension configurationExtension :
				_configurationExtensions) {

			configurationExtension.destroy();
		}

		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testExceptionInSupplierDoesNotStopExtension() throws Exception {
		ConfiguratorExtension configuratorExtension = new ConfiguratorExtension(
			_configurationAdmin, new Logger(_bundleContext), "aBundle",
			Arrays.<NamedConfigurationContent>asList(
				new StringSingleNamedConfigurationContent(
					"test.pid", "key=value")),
			Arrays.asList(
				new ConfigurationDescriptionFactory() {

					@Override
					public ConfigurationDescription create(
						NamedConfigurationContent namedConfigurationContent) {

						return new SingleConfigurationDescription(
							"exception",
							new Supplier<Dictionary<String, Object>>() {

								@Override
								public Dictionary<String, Object> get() {
									throw new RuntimeException(
										"This should be handled");
								}

							});
					}

				},
				new StringConfigurationDescriptionFactory()));

		configuratorExtension.start();

		_configurationExtensions.add(configuratorExtension);

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);
	}

	@Test
	public void testFactoryConfiguration() throws Exception {
		_startExtension(
			"aBundle",
			new StringFactoryNamedConfigurationContent(
				"test.factory.pid", "default", "key=value"));

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

		configuration.update(
			new Hashtable<String, Object>() {
				{
					put("key", "value");
				}
			});

		_startExtension(
			"aBundle",
			new StringFactoryNamedConfigurationContent(
				"test.factory.pid", "default", "key=value2"));

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
			new StringFactoryNamedConfigurationContent(
				"test.factory.pid", "default", "key=value"),
			new StringFactoryNamedConfigurationContent(
				"test.factory.pid", "default2", "key=value"));

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
			new StringFactoryNamedConfigurationContent(
				"test.factory.pid", "default", "key=value"),
			new StringFactoryNamedConfigurationContent(
				"test.factory.pid", "default", "key=value2"));

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
			new StringFactoryNamedConfigurationContent(
				"test.factory.pid", "default", "key=value"));

		_startExtension(
			"otherBundle",
			new StringFactoryNamedConfigurationContent(
				"test.factory.pid", "default", "key=value"));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			null);

		Assert.assertEquals(
			Arrays.toString(configurations), 2, configurations.length);
	}

	@Test
	public void testSingleConfiguration() throws Exception {
		_startExtension(
			"aBundle",
			new StringSingleNamedConfigurationContent("test.pid", "key=value"));

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

		configuration.update(
			new Hashtable<String, Object>() {
				{
					put("key", "value");
				}
			});

		_startExtension(
			"aBundle",
			new StringSingleNamedConfigurationContent(
				"test.pid", "key=value2"));

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
			new StringSingleNamedConfigurationContent("test.pid", "key=value"),
			new StringSingleNamedConfigurationContent(
				"test.pid", "key=value2"));

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
			new StringSingleNamedConfigurationContent("test.pid", "key=value"));

		_startExtension(
			"otherBundle",
			new StringSingleNamedConfigurationContent(
				"test.pid", "key=value2"));

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
			new StringSingleNamedConfigurationContent("test.pid", "key=value"),
			new StringSingleNamedConfigurationContent(
				"test.pid2", "key=value"));

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
			Arrays.asList(namedConfigurationContents),
			Arrays.<ConfigurationDescriptionFactory>asList(
				new StringConfigurationDescriptionFactory()));

		configuratorExtension.start();

		_configurationExtensions.add(configuratorExtension);
	}

	@ArquillianResource
	private BundleContext _bundleContext;

	private ConfigurationAdmin _configurationAdmin;
	private final Collection<ConfiguratorExtension> _configurationExtensions =
		new ArrayList<>();
	private ServiceReference<ConfigurationAdmin> _serviceReference;

	private static class StringConfigurationDescriptionFactory
		implements ConfigurationDescriptionFactory {

		@Override
		public ConfigurationDescription create(
			NamedConfigurationContent namedConfigurationContent) {

			if (namedConfigurationContent instanceof
					StringSingleNamedConfigurationContent) {

				final StringSingleNamedConfigurationContent ssncc =
					(StringSingleNamedConfigurationContent)
						namedConfigurationContent;

				return new SingleConfigurationDescription(
					ssncc._pid,
					new Supplier<Dictionary<String, Object>>() {

						@Override
						public Dictionary<String, Object> get() {
							try {
								Dictionary<?, ?> properties =
									PropertiesUtil.load(
										ssncc.getInputStream(), "UTF-8");

								return (Dictionary<String, Object>)properties;
							}
							catch (IOException ioe) {
								throw new RuntimeException(ioe);
							}
						}

					});
			}
			else {
				final StringFactoryNamedConfigurationContent sfncc =
					(StringFactoryNamedConfigurationContent)
						namedConfigurationContent;

				return new FactoryConfigurationDescription(
					sfncc._factoryPid, sfncc._pid,
					new Supplier<Dictionary<String, Object>>() {

						@Override
						public Dictionary<String, Object> get() {
							try {
								Dictionary<?, ?> properties =
									PropertiesUtil.load(
										sfncc.getInputStream(), "UTF-8");

								return (Dictionary<String, Object>)properties;
							}
							catch (IOException ioe) {
								throw new RuntimeException(ioe);
							}
						}

					});
			}
		}

	}

	private static class StringFactoryNamedConfigurationContent
		implements NamedConfigurationContent {

		public StringFactoryNamedConfigurationContent(
			String factoryPid, String pid, String content) {

			_factoryPid = factoryPid;
			_pid = pid;
			_content = content;
		}

		@Override
		public InputStream getInputStream() {
			return new ByteArrayInputStream(
				_content.getBytes(Charset.forName("UTF-8")));
		}

		@Override
		public String getName() {
			return _pid;
		}

		private final String _content;
		private final String _factoryPid;
		private final String _pid;

	}

	private static class StringSingleNamedConfigurationContent
		implements NamedConfigurationContent {

		public StringSingleNamedConfigurationContent(
			String pid, String content) {

			_pid = pid;
			_content = content;
		}

		@Override
		public InputStream getInputStream() {
			return new ByteArrayInputStream(
				_content.getBytes(Charset.forName("UTF-8")));
		}

		@Override
		public String getName() {
			return _pid;
		}

		private final String _content;
		private final String _pid;

	}

}