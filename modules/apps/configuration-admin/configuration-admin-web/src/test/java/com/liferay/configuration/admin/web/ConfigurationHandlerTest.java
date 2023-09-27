/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.Collections;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ConfigurationHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testWriteArray() throws IOException {
		_assertProperties(
			new String[] {"testValue1", "testValue2"},
			"[ \\\r\n  \"testValue1\", \\\r\n  \"testValue2\", \\\r\n  ]");
	}

	@Test
	public void testWriteBoolean() throws IOException {
		_assertProperties(true, "B\"true\"");
	}

	@Test
	public void testWriteByte() throws IOException {
		_assertProperties((byte)10, "X\"10\"");
	}

	@Test
	public void testWriteChar() throws IOException {
		_assertProperties('c', "C\"c\"");
	}

	@Test
	public void testWriteCollection() throws IOException {
		_assertProperties(
			Arrays.asList("testValue1", "testValue2"),
			"( \\\r\n  \"testValue1\", \\\r\n  \"testValue2\", \\\r\n)");
	}

	@Test
	public void testWriteDouble() throws IOException {
		_assertProperties(
			10.1D,
			StringBundler.concat("D\"", Double.doubleToLongBits(10.1D), "\""));
	}

	@Test
	public void testWriteEmptyCollection() throws IOException {
		_assertProperties(Collections.emptyList(), "( \\\r\n)");
	}

	@Test
	public void testWriteFloat() throws IOException {
		_assertProperties(
			10.1F,
			StringBundler.concat("F\"", Float.floatToRawIntBits(10.1F), "\""));
	}

	@Test
	public void testWriteInteger() throws IOException {
		_assertProperties(10, "I\"10\"");
	}

	@Test
	public void testWriteLong() throws IOException {
		_assertProperties(10L, "L\"10\"");
	}

	@Test
	public void testWriteShort() throws IOException {
		_assertProperties((short)10, "S\"10\"");
	}

	@Test
	public void testWriteString() throws IOException {
		_assertProperties("testValue", "\"testValue\"");
	}

	@Test
	public void testWriteStringEscapedValues() throws IOException {
		_assertProperties("${testValue=\"}", "\"$\\{testValue\\=\\\"\\}\"");
	}

	private void _assertProperties(Object inputValue, String outputValue)
		throws IOException {

		try (OutputStream outputStream = new UnsyncByteArrayOutputStream()) {
			ConfigurationHandler.write(
				outputStream,
				MapUtil.singletonDictionary(_TEST_KEY, inputValue));

			Assert.assertEquals(
				StringBundler.concat(_TEST_KEY, "=", outputValue, "\r\n"),
				outputStream.toString());
		}
	}

	private static final String _TEST_KEY = "testKey";

}