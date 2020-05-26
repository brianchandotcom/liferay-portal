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
import com.liferay.portal.kernel.util.MapUtil;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ConfigurationHandlerTest {

	@Test
	public void testWriteArray() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(
				_TEST_KEY, new String[] {"testValue1", "testValue2"}),
			_TEST_KEY.concat(
				"=[ \\\r\n  \"testValue1\", \\\r\n  \"testValue2\", \\\r\n  " +
					"]\r\n"));
	}

	@Test
	public void testWriteBoolean() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, true),
			_TEST_KEY.concat("=B\"true\"\r\n"));
	}

	@Test
	public void testWriteByte() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, (byte)10),
			_TEST_KEY.concat("=X\"10\"\r\n"));
	}

	@Test
	public void testWriteChar() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, 'c'),
			_TEST_KEY.concat("=C\"c\"\r\n"));
	}

	@Test
	public void testWriteCollection() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(
				_TEST_KEY, Arrays.asList("testValue1", "testValue2")),
			_TEST_KEY.concat(
				"=( \\\r\n  \"testValue1\", \\\r\n  \"testValue2\", " +
					"\\\r\n)\r\n"));
	}

	@Test
	public void testWriteDouble() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, 10.1D),
			StringBundler.concat(
				_TEST_KEY, "=D\"", Double.doubleToLongBits(10.1D), "\"\r\n"));
	}

	@Test
	public void testWriteEmptyCollection() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, Collections.emptyList()),
			_TEST_KEY.concat("=( \\\r\n)\r\n"));
	}

	@Test
	public void testWriteFloat() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, 10.1F),
			StringBundler.concat(
				_TEST_KEY, "=F\"", Float.floatToRawIntBits(10.1F), "\"\r\n"));
	}

	@Test
	public void testWriteInteger() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, 10),
			_TEST_KEY.concat("=I\"10\"\r\n"));
	}

	@Test
	public void testWriteLong() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, 10L),
			_TEST_KEY.concat("=L\"10\"\r\n"));
	}

	@Test
	public void testWriteShort() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, (short)10),
			_TEST_KEY.concat("=S\"10\"\r\n"));
	}

	@Test
	public void testWriteString() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, "testValue"),
			_TEST_KEY.concat("=\"testValue\"\r\n"));
	}

	@Test
	public void testWriteStringEscapedValues() throws IOException {
		_assertProperties(
			MapUtil.singletonDictionary(_TEST_KEY, "${testValue=\"}"),
			_TEST_KEY.concat("=\"$\\{testValue\\=\\\"\\}\"\r\n"));
	}

	private void _assertProperties(
			Dictionary<String, Object> properties, String line)
		throws IOException {

		try (OutputStream outputStream = new UnsyncByteArrayOutputStream()) {
			ConfigurationHandler.write(outputStream, properties);

			Assert.assertEquals(line, outputStream.toString());
		}
	}

	private static final String _TEST_KEY = "testKey";

}