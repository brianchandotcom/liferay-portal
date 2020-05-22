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

package com.liferay.configuration.admin.web;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ConfigurationHandlerTest {

	@Test
	public void testWriteArray() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, new String[] {"testValue1", "testValue2"});

		_assertProperties(
			properties,
			_TEST_KEY.concat(
				"=[ \\\r\n  \"testValue1\", \\\r\n  \"testValue2\", \\\r\n  " +
					"]\r\n"));
	}

	@Test
	public void testWriteBoolean() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, true);

		_assertProperties(properties, _TEST_KEY.concat("=B\"true\"\r\n"));
	}

	@Test
	public void testWriteByte() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, Byte.valueOf("10"));

		_assertProperties(properties, _TEST_KEY.concat("=X\"10\"\r\n"));
	}

	@Test
	public void testWriteChar() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, 'c');

		_assertProperties(properties, _TEST_KEY.concat("=C\"c\"\r\n"));
	}

	@Test
	public void testWriteCollection() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		List<String> list = new ArrayList<>();

		list.add("testValue1");
		list.add("testValue2");

		properties.put(_TEST_KEY, list);

		_assertProperties(
			properties,
			_TEST_KEY.concat(
				"=( \\\r\n  \"testValue1\", \\\r\n  \"testValue2\", " +
					"\\\r\n)\r\n"));
	}

	@Test
	public void testWriteDouble() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, 10.1D);

		_assertProperties(
			properties,
			StringBundler.concat(
				_TEST_KEY, "=D\"", Double.doubleToLongBits(10.1D), "\"\r\n"));
	}

	@Test
	public void testWriteEmptyCollection() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, new ArrayList());

		_assertProperties(properties, _TEST_KEY.concat("=( \\\r\n)\r\n"));
	}

	@Test
	public void testWriteFloat() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, 10.1F);

		_assertProperties(
			properties,
			StringBundler.concat(
				_TEST_KEY, "=F\"", Float.floatToRawIntBits(10.1F), "\"\r\n"));
	}

	@Test
	public void testWriteInteger() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, 10);

		_assertProperties(properties, _TEST_KEY.concat("=I\"10\"\r\n"));
	}

	@Test
	public void testWriteLong() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, 10L);

		_assertProperties(properties, _TEST_KEY.concat("=L\"10\"\r\n"));
	}

	@Test
	public void testWriteShort() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, (short)10);

		_assertProperties(properties, _TEST_KEY.concat("=S\"10\"\r\n"));
	}

	@Test
	public void testWriteString() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, "testValue");

		_assertProperties(properties, _TEST_KEY.concat("=\"testValue\"\r\n"));
	}

	@Test
	public void testWriteStringEscapedValues() throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(_TEST_KEY, "${testValue=\"}");

		_assertProperties(
			properties, _TEST_KEY.concat("=\"$\\{testValue\\=\\\"\\}\"\r\n"));
	}

	private void _assertProperties(
			Dictionary<String, Object> properties, String line)
		throws IOException {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			ConfigurationHandler.write(unsyncByteArrayOutputStream, properties);

			Assert.assertEquals(
				line, new String(unsyncByteArrayOutputStream.toByteArray()));
		}
	}

	private static final String _TEST_KEY = "testKey";

}