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

package com.liferay.portal.configurator.extender.internal;

import com.liferay.portal.configurator.extender.NamedConfigurationContent;
import com.liferay.portal.configurator.extender.NamedConfigurationContentFactory;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.osgi.framework.Bundle;

/**
 * @author Carlos Sierra Andrés
 */
public class NamedConfigurationPathContentFactoryTest {

	@Before
	public void setUp() throws IOException {
		temporaryFolder.create();

		_headers = new Hashtable<>();

		_headers.put(
			"Bundle-SymbolicName",
			"com.liferay.portal.configuration.extender.test");
		_headers.put("ConfigurationPath", "/configs");

		temporaryFolder.newFolder("configs");

		_file = temporaryFolder.newFile(
			"/configs/com.liferay.test.aConfigFile");

		_writeToFile(_file, "key=value\nanotherKey=anotherValue");
	}

	@After
	public void tearDown() {
		temporaryFolder.delete();
	}

	@Test
	public void testCreate() throws IOException {
		URI uri = _file.toURI();

		Bundle bundle = _createDummyBundle(
			100, "aLocation", _headers, Arrays.asList(uri.toURL()));

		NamedConfigurationContentFactory namedConfigurationContentFactory =
			new NamedConfigurationPathContentFactory();

		List<NamedConfigurationContent> namedConfigurationContents =
			namedConfigurationContentFactory.create(bundle);

		Assert.assertEquals(1, namedConfigurationContents.size());

		NamedConfigurationContent namedConfigurationContent =
			namedConfigurationContents.get(0);

		Assert.assertEquals(
			"com.liferay.test.aConfigFile",
			namedConfigurationContent.getName());

		Assert.assertEquals(
			"key=value\nanotherKey=anotherValue",
			StringUtil.read(namedConfigurationContent.getInputStream()));
	}

	@Test
	public void testCreateWithMultipleFiles() throws IOException {
		URI uri1 = _file.toURI();

		File file = temporaryFolder.newFile(
			"/configs/com.liferay.test.anotherConfigFile");

		_writeToFile(file, "key2=value2\nanotherKey2=anotherValue2");

		URI uri2 = file.toURI();

		Bundle bundle = _createDummyBundle(
			100, "aLocation", _headers,
			Arrays.asList(uri1.toURL(), uri2.toURL()));

		NamedConfigurationContentFactory contentFactory =
			new NamedConfigurationPathContentFactory();

		List<NamedConfigurationContent> namedConfigurationContents =
			contentFactory.create(bundle);

		Assert.assertEquals(2, namedConfigurationContents.size());

		NamedConfigurationContent namedConfigurationContent =
			namedConfigurationContents.get(0);

		Assert.assertEquals(
			"com.liferay.test.aConfigFile",
			namedConfigurationContent.getName());

		Assert.assertEquals(
			"key=value\nanotherKey=anotherValue",
			StringUtil.read(namedConfigurationContent.getInputStream()));

		namedConfigurationContent = namedConfigurationContents.get(1);

		Assert.assertEquals(
			"com.liferay.test.anotherConfigFile",
			namedConfigurationContent.getName());

		Assert.assertEquals(
			"key2=value2\nanotherKey2=anotherValue2",
			StringUtil.read(namedConfigurationContent.getInputStream()));
	}

	@Test
	public void testCreateWithNestedDir() throws IOException {
		URI uri1 = _file.toURI();

		temporaryFolder.newFolder("configs", "nested");

		File file = temporaryFolder.newFile(
			"/configs/nested/com.liferay.test.anotherConfigFile");

		_writeToFile(file, "key2=value2\nanotherKey2=anotherValue2");

		URI uri2 = file.toURI();

		Bundle bundle = _createDummyBundle(
			100, "aLocation", _headers,
			Arrays.asList(uri1.toURL(), uri2.toURL()));

		NamedConfigurationContentFactory contentFactory =
			new NamedConfigurationPathContentFactory();

		List<NamedConfigurationContent> namedConfigurationContents =
			contentFactory.create(bundle);

		Assert.assertEquals(2, namedConfigurationContents.size());

		NamedConfigurationContent namedConfigurationContent =
			namedConfigurationContents.get(0);

		Assert.assertEquals(
			"com.liferay.test.aConfigFile",
			namedConfigurationContent.getName());

		Assert.assertEquals(
			"key=value\nanotherKey=anotherValue",
			StringUtil.read(namedConfigurationContent.getInputStream()));

		namedConfigurationContent = namedConfigurationContents.get(1);

		Assert.assertEquals(
			"com.liferay.test.anotherConfigFile",
			namedConfigurationContent.getName());

		Assert.assertEquals(
			"key2=value2\nanotherKey2=anotherValue2",
			StringUtil.read(namedConfigurationContent.getInputStream()));
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static Bundle _createDummyBundle(
		final long bundleId, final String location,
		final Dictionary<String, String> headers, final List<URL> findEntries) {

		return (Bundle)ProxyUtil.newProxyInstance(
			Bundle.class.getClassLoader(), new Class<?>[] {Bundle.class},
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws ReflectiveOperationException {

					if (method.equals(
							Bundle.class.getMethod(
								"findEntries", String.class, String.class,
								boolean.class))) {

						return Collections.enumeration(findEntries);
					}

					if (method.equals(Bundle.class.getMethod("getBundleId"))) {
						return bundleId;
					}

					if (method.equals(Bundle.class.getMethod("getHeaders"))) {
						return headers;
					}

					if (method.equals(Bundle.class.getMethod("getLocation"))) {
						return location;
					}

					if (method.equals(
							Bundle.class.getMethod("getSymbolicName"))) {

						return headers.get("Bundle-SymbolicName");
					}

					return null;
				}

			});
	}

	private void _writeToFile(File file, String content) {
		try (Writer writer = new FileWriter(file)) {
			writer.write(content);

			writer.flush();

			writer.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private File _file;
	private Hashtable<String, String> _headers;

}