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

package com.liferay.portal.configuration.extender.internal;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.net.URI;

import java.util.Hashtable;

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
		_headers.put("Liferay-Configuration-Path", "/configs");

		temporaryFolder.create();

		temporaryFolder.newFolder("configs");

		_file = temporaryFolder.newFile(
			"/configs/com.liferay.test.aConfigFile.properties");

		write(_file, "key=value\nanotherKey=anotherValue");
	}

	@After
	public void tearDown() {
		temporaryFolder.delete();
	}

	@Test
	public void testCreate() throws IOException {
		URI uri = _file.toURI();

		NamedConfigurationContentFactory namedConfigurationContentFactory =
			new NamedConfigurationPathContentFactory();

		NamedConfigurationContent namedConfigurationContent =
			namedConfigurationContentFactory.create(uri.toURL());

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
			"/configs/com.liferay.test.anotherConfigFile.properties");

		write(file, "key2=value2\nanotherKey2=anotherValue2");

		URI uri2 = file.toURI();

		NamedConfigurationContentFactory namedConfigurationContentFactory =
			new NamedConfigurationPathContentFactory();

		NamedConfigurationContent namedConfigurationContent =
			namedConfigurationContentFactory.create(uri1.toURL());

		Assert.assertEquals(
			"com.liferay.test.aConfigFile",
			namedConfigurationContent.getName());
		Assert.assertEquals(
			"key=value\nanotherKey=anotherValue",
			StringUtil.read(namedConfigurationContent.getInputStream()));

		namedConfigurationContent = namedConfigurationContentFactory.create(
			uri2.toURL());

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
			"/configs/nested/com.liferay.test.anotherConfigFile.properties");

		write(file, "key2=value2\nanotherKey2=anotherValue2");

		URI uri2 = file.toURI();

		NamedConfigurationContentFactory namedConfigurationContentFactory =
			new NamedConfigurationPathContentFactory();

		NamedConfigurationContent namedConfigurationContent =
			namedConfigurationContentFactory.create(uri1.toURL());

		Assert.assertEquals(
			"com.liferay.test.aConfigFile",
			namedConfigurationContent.getName());
		Assert.assertEquals(
			"key=value\nanotherKey=anotherValue",
			StringUtil.read(namedConfigurationContent.getInputStream()));

		namedConfigurationContent = namedConfigurationContentFactory.create(
			uri2.toURL());

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

}