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

package com.liferay.configuration.admin.web.internal.test;

import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.configuration.admin.web.internal.exporter.ConfigurationExporter;
import com.liferay.petra.string.StringPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
@BndFile("src/testIntegration/resources/bnd.bnd")
@RunWith(Arquillian.class)
public class ConfigurationExportImportTest {

	@Before
	public void setUp() throws Exception {
		_serviceReference = _bundleContext.getServiceReference(
			ConfigurationAdmin.class);

		_configurationAdmin = _bundleContext.getService(_serviceReference);

		_configuration = _configurationAdmin.createFactoryConfiguration(
			"test.pid", StringPool.QUESTION);
	}

	@After
	public void tearDown() throws Exception {
		_file.delete();

		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testExportImportBlankString() throws Exception {
		String blankStringKey = "blankStringKey";
		String blankStringValue = StringPool.BLANK;

		_dictionary.put(blankStringKey, blankStringValue);

		exportImportProperties(_dictionary);

		Assert.assertEquals(
			blankStringValue,
			_configuration.getProperties().get(blankStringKey));
	}

	@Test
	public void testExportImportBoolean() throws Exception {
		String booleanKey = "booleanKey";

		_dictionary.put(booleanKey, true);

		exportImportProperties(_dictionary);

		Assert.assertTrue(
			(boolean)_configuration.getProperties().get(booleanKey));
	}

	@Test
	public void testExportImportString() throws Exception {
		String stringKey = "stringKey";
		String stringValue = "stringValue";

		_dictionary.put(stringKey, stringValue);

		exportImportProperties(_dictionary);

		Assert.assertEquals(
			stringValue, _configuration.getProperties().get(stringKey));
	}

	@Test
	public void testExportImportStringArray() throws Exception {
		String arrayKey = "arrayKey";
		String[] arrayValues = {"value1", "value2", "value3"};

		_dictionary.put(arrayKey, arrayValues);

		exportImportProperties(_dictionary);

		Assert.assertArrayEquals(
			arrayValues,
			(String[])_configuration.getProperties().get(arrayKey));
	}

	protected void exportImportProperties(Dictionary dictionary)
		throws Exception {

		byte[] bytes = ConfigurationExporter.getPropertiesAsBytes(dictionary);

		FileOutputStream fileOutputStream = new FileOutputStream(_file);

		fileOutputStream.write(bytes);

		Assert.assertEquals(bytes.length, _file.length());

		FileInputStream fileInputStream = new FileInputStream(_file);

		_configuration.update(ConfigurationHandler.read(fileInputStream));
	}

	@ArquillianResource
	private BundleContext _bundleContext;

	private Configuration _configuration;
	private ConfigurationAdmin _configurationAdmin;
	private final Dictionary _dictionary = new Hashtable<>();
	private final File _file = new File("test-configuration-export.config");
	private ServiceReference<ConfigurationAdmin> _serviceReference;

}