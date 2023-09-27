/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.exporter.ConfigurationExporter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pei-Jung Lan
 */
public class ConfigurationExportImportTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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