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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.exporter.ConfigurationExporter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Pei-Jung Lan
 */
public class ConfigurationExportImportTest {

	@Before
	public void setUp() {
		_dictionary = new Hashtable<>();
	}

	@Test
	public void testExportImportBlankString() throws Exception {
		String blankStringKey = "blankStringKey";
		String blankStringValue = StringPool.BLANK;

		_dictionary.put(blankStringKey, blankStringValue);

		Dictionary<String, Object> dictionary = _exportImportProperties(
			_dictionary);

		Assert.assertEquals(_dictionary, dictionary);
	}

	@Test
	public void testExportImportBoolean() throws Exception {
		String booleanKey = "booleanKey";

		_dictionary.put(booleanKey, true);

		Dictionary<String, Object> dictionary = _exportImportProperties(
			_dictionary);

		Assert.assertEquals(_dictionary, dictionary);
	}

	@Test
	public void testExportImportString() throws Exception {
		String stringKey = "stringKey";
		String stringValue = "stringValue";

		_dictionary.put(stringKey, stringValue);

		Dictionary<String, Object> dictionary = _exportImportProperties(
			_dictionary);

		Assert.assertEquals(_dictionary, dictionary);
	}

	@Test
	public void testExportImportStringArray() throws Exception {
		String arrayKey = "arrayKey";
		String[] arrayValues = {"value1", "value2", "value3"};

		_dictionary.put(arrayKey, arrayValues);

		Dictionary<String, Object> dictionary = _exportImportProperties(
			_dictionary);

		Assert.assertEquals(dictionary.toString(), 1, dictionary.size());
		Assert.assertArrayEquals(
			arrayValues, (String[])dictionary.get(arrayKey));
	}

	@SuppressWarnings("unchecked")
	private Dictionary<String, Object> _exportImportProperties(
			Dictionary<String, Object> dictionary)
		throws Exception {

		byte[] bytes = ConfigurationExporter.getPropertiesAsBytes(dictionary);

		return ConfigurationHandler.read(new UnsyncByteArrayInputStream(bytes));
	}

	private Dictionary<String, Object> _dictionary;

}