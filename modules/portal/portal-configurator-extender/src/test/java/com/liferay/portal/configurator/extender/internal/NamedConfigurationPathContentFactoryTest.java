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

import com.liferay.portal.configurator.extender.BundleStorage;
import com.liferay.portal.configurator.extender.NamedConfigurationContent;
import com.liferay.portal.configurator.extender.NamedConfigurationContentFactory;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.net.URI;
import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Carlos Sierra Andrés
 */
public class NamedConfigurationPathContentFactoryTest {

	@Before
	public void setUp() throws IOException {
		_headers = new Hashtable<>();

		_headers.put(
			"Bundle-SymbolicName",
			"com.liferay.portal.configuration.extender.test");
		_headers.put("ConfigurationPath", "/configs");

		temporaryFolder.create();

		temporaryFolder.newFolder("configs");

		_file = temporaryFolder.newFile(
			"/configs/com.liferay.test.aConfigFile");

		write(_file, "key=value\nanotherKey=anotherValue");
	}

	@After
	public void tearDown() {
		temporaryFolder.delete();
	}

	@Test
	public void testCreate() throws IOException {
		URI uri = _file.toURI();

		BundleStorage bundleStorage = new DummyBundleStorage(
			100, "aLocation", _headers, Arrays.asList(uri.toURL()));

		NamedConfigurationContentFactory namedConfigurationContentFactory =
			new NamedConfigurationPathContentFactory();

		List<NamedConfigurationContent> namedConfigurationContents =
			namedConfigurationContentFactory.create(bundleStorage);

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

		write(file, "key2=value2\nanotherKey2=anotherValue2");

		URI uri2 = file.toURI();

		BundleStorage bundleStorage = new DummyBundleStorage(
			100, "aLocation", _headers,
			Arrays.asList(uri1.toURL(), uri2.toURL()));

		NamedConfigurationContentFactory namedConfigurationContentFactory =
			new NamedConfigurationPathContentFactory();

		List<NamedConfigurationContent> namedConfigurationContents =
			namedConfigurationContentFactory.create(bundleStorage);

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
	public void testCreateWithNestedDirectory() throws IOException {
		URI uri1 = _file.toURI();

		temporaryFolder.newFolder("configs", "nested");

		File file = temporaryFolder.newFile(
			"/configs/nested/com.liferay.test.anotherConfigFile");

		write(file, "key2=value2\nanotherKey2=anotherValue2");

		URI uri2 = file.toURI();

		BundleStorage bundleStorage = new DummyBundleStorage(
			100, "aLocation", _headers,
			Arrays.asList(uri1.toURL(), uri2.toURL()));

		NamedConfigurationContentFactory namedConfigurationContentFactory =
			new NamedConfigurationPathContentFactory();

		List<NamedConfigurationContent> namedConfigurationContents =
			namedConfigurationContentFactory.create(bundleStorage);

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

	protected void write(File file, String content) {
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

	private static class DummyBundleStorage implements BundleStorage {

		public DummyBundleStorage(
			long bundleId, String location, Dictionary<String, String> headers,
			List<URL> entries) {

			_bundleId = bundleId;
			_location = location;
			_headers = headers;
			_entries = entries;
		}

		@Override
		public Enumeration<URL> findEntries(
			String root, String pattern, boolean recurse) {

			return Collections.enumeration(_entries);
		}

		@Override
		public long getBundleId() {
			return _bundleId;
		}

		@Override
		public URL getEntry(String name) {
			return null;
		}

		@Override
		public Enumeration<String> getEntryPaths(String name) {
			return null;
		}

		@Override
		public Dictionary<String, String> getHeaders() {
			return _headers;
		}

		@Override
		public String getLocation() {
			return _location;
		}

		@Override
		public URL getResource(String name) {
			return null;
		}

		@Override
		public Enumeration<URL> getResources(String name) {
			return null;
		}

		@Override
		public String getSymbolicName() {
			return _headers.get("Bundle-SymbolicName");
		}

		private final long _bundleId;
		private final List<URL> _entries;
		private final Dictionary<String, String> _headers;
		private final String _location;

	}

}