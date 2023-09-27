/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.properties;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.ArrayList;

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
	public void testReadArray() throws IOException {
		Assert.assertArrayEquals(
			new String[] {"test1", "test2"},
			(String[])ConfigurationHandler.read(
				"[\\\r\n  \"test1\",\\\r\n  \"test2\",\\\r\n  ]"));
	}

	@Test
	public void testReadArrayNoLineBreaks() throws IOException {
		Assert.assertArrayEquals(
			new String[] {"test1", "test2"},
			(String[])ConfigurationHandler.read("[\"test1\", \"test2\"]"));
	}

	@Test
	public void testReadBoolean() throws IOException {
		Assert.assertTrue((boolean)ConfigurationHandler.read("B\"true\""));
		Assert.assertTrue((boolean)ConfigurationHandler.read("b\"true\""));
	}

	@Test
	public void testReadByte() throws IOException {
		Assert.assertEquals((byte)1, ConfigurationHandler.read("X\"1\""));
		Assert.assertEquals((byte)1, ConfigurationHandler.read("x\"1\""));
	}

	@Test
	public void testReadChar() throws IOException {
		Assert.assertEquals('A', ConfigurationHandler.read("C\"A\""));
		Assert.assertEquals('A', ConfigurationHandler.read("c\"A\""));
	}

	@Test
	public void testReadCollection() throws IOException {
		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("test1");
					add("test2");
				}
			},
			ConfigurationHandler.read(
				"(\\\r\n  \"test1\",\\\r\n  \"test2\",\\\r\n)"));
	}

	@Test
	public void testReadCollectionNoLineBreaks() throws IOException {
		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("test1");
					add("test2");
				}
			},
			ConfigurationHandler.read("(\"test1\", \"test2\",)"));
	}

	@Test
	public void testReadDouble() throws IOException {
		Assert.assertEquals(12.2D, ConfigurationHandler.read("D\"12.2\""));
		Assert.assertEquals(12.2D, ConfigurationHandler.read("d\"12.2\""));
		Assert.assertEquals(
			12.2D, ConfigurationHandler.read("D\"4623057607486498406\""));
	}

	@Test
	public void testReadEmptyCollection() throws IOException {
		Assert.assertEquals(
			new ArrayList<String>(), ConfigurationHandler.read("(\\\r\n)"));
	}

	@Test
	public void testReadFloat() throws IOException {
		Assert.assertEquals(12.2F, ConfigurationHandler.read("F\"12.2\""));
		Assert.assertEquals(12.2F, ConfigurationHandler.read("f\"12.2\""));
		Assert.assertEquals(
			12.2F, ConfigurationHandler.read("F\"1094923059\""));
	}

	@Test
	public void testReadInteger() throws IOException {
		Assert.assertEquals(20, ConfigurationHandler.read("I\"20\""));
		Assert.assertEquals(20, ConfigurationHandler.read("i\"20\""));
	}

	@Test
	public void testReadInvalidCollection() throws IOException {
		Assert.assertEquals(null, ConfigurationHandler.read("(test)"));
	}

	@Test
	public void testReadInvalidType() throws IOException {
		Assert.assertEquals(null, ConfigurationHandler.read("Z\"test\""));
	}

	@Test
	public void testReadLong() throws IOException {
		Assert.assertEquals(30L, ConfigurationHandler.read("L\"30\""));
		Assert.assertEquals(30L, ConfigurationHandler.read("l\"30\""));
	}

	@Test
	public void testReadShort() throws IOException {
		Assert.assertEquals((short)2, ConfigurationHandler.read("S\"2\""));
		Assert.assertEquals((short)2, ConfigurationHandler.read("s\"2\""));
	}

	@Test
	public void testReadSimpleString() throws IOException {
		Assert.assertEquals("test", ConfigurationHandler.read("\"test\""));
	}

	@Test
	public void testReadSpecial() throws IOException {
		Assert.assertEquals(
			"\b\t\n\f\r+a",
			ConfigurationHandler.read("\"\\b\\t\\n\\f\\r\\u002B\\a\""));
	}

	@Test
	public void testReadString() throws IOException {
		Assert.assertEquals("test", ConfigurationHandler.read("T\"test\""));
	}

	@Test
	public void testReadStringWithWhitespace() throws IOException {
		Assert.assertEquals(
			"test", ConfigurationHandler.read(" \r\n\r\"test\""));
	}

	@Test
	public void testReadTypedArray() throws IOException {
		Assert.assertArrayEquals(
			new Integer[] {1, 2},
			(Integer[])ConfigurationHandler.read(
				"I[\\\r\n  \"1\",\\\r\n  \"2\",\\\r\n  ]"));
	}

	@Test
	public void testReadTypedCollection() throws IOException {
		Assert.assertEquals(
			new ArrayList<Integer>() {
				{
					add(1);
					add(2);
				}
			},
			ConfigurationHandler.read("I(\\\r\n  \"1\",\\\r\n  \"2\",\\\r\n)"));
	}

	@Test
	public void testWriteArray() throws IOException {
		Assert.assertEquals(
			"[\\\r\n  \"test1\",\\\r\n  \"test2\"\\\r\n]",
			ConfigurationHandler.write(new String[] {"test1", "test2"}));
	}

	@Test
	public void testWriteBlank() throws IOException {
		Assert.assertEquals(
			"\"\"", ConfigurationHandler.write(StringPool.BLANK));
	}

	@Test
	public void testWriteBoolean() throws IOException {
		Assert.assertEquals("B\"true\"", ConfigurationHandler.write(true));
	}

	@Test
	public void testWriteByte() throws IOException {
		Assert.assertEquals(
			"X\"1\"", ConfigurationHandler.write(Byte.valueOf("1")));
	}

	@Test
	public void testWriteChar() throws IOException {
		Assert.assertEquals("C\"A\"", ConfigurationHandler.write('A'));
	}

	@Test
	public void testWriteCollection() throws IOException {
		Assert.assertEquals(
			"(\\\r\n  \"test1\",\\\r\n  \"test2\"\\\r\n)",
			ConfigurationHandler.write(
				new ArrayList<String>() {
					{
						add("test1");
						add("test2");
					}
				}));
	}

	@Test
	public void testWriteDouble() throws IOException {
		Assert.assertEquals("D\"12.2\"", ConfigurationHandler.write(12.2D));
	}

	@Test
	public void testWriteEmptyArray() throws IOException {
		Assert.assertEquals(
			"[\\\r\n]", ConfigurationHandler.write(new String[0]));
	}

	@Test
	public void testWriteEmptyCollection() throws IOException {
		Assert.assertEquals(
			"(\\\r\n)", ConfigurationHandler.write(new ArrayList()));
	}

	@Test
	public void testWriteEscaped() throws IOException {
		Assert.assertEquals(
			"\"\\{\"", ConfigurationHandler.write(StringPool.OPEN_CURLY_BRACE));
		Assert.assertEquals(
			"\"\\\\\"", ConfigurationHandler.write(StringPool.BACK_SLASH));
		Assert.assertEquals(
			"\"\\=\"", ConfigurationHandler.write(StringPool.EQUAL));
		Assert.assertEquals(
			"\"\\\"\"", ConfigurationHandler.write(StringPool.QUOTE));
		Assert.assertEquals(
			"\"\\ \"", ConfigurationHandler.write(StringPool.SPACE));
		Assert.assertEquals(
			"\"\\}\"",
			ConfigurationHandler.write(StringPool.CLOSE_CURLY_BRACE));
		Assert.assertEquals("\"\\b\"", ConfigurationHandler.write("\b"));
		Assert.assertEquals(
			"\"\\t\"", ConfigurationHandler.write(StringPool.TAB));
		Assert.assertEquals(
			"\"\\n\"", ConfigurationHandler.write(StringPool.NEW_LINE));
		Assert.assertEquals("\"\\f\"", ConfigurationHandler.write("\f"));
		Assert.assertEquals(
			"\"\\r\"", ConfigurationHandler.write(StringPool.RETURN));

		char c = CharPool.SPACE - 1;

		Assert.assertEquals("C\"\\u001f\"", ConfigurationHandler.write(c));
	}

	@Test
	public void testWriteFloat() throws IOException {
		Assert.assertEquals("F\"12.2\"", ConfigurationHandler.write(12.2F));
	}

	@Test
	public void testWriteInteger() throws IOException {
		Assert.assertEquals("I\"20\"", ConfigurationHandler.write(20));
	}

	@Test
	public void testWriteLong() throws IOException {
		Assert.assertEquals("L\"30\"", ConfigurationHandler.write(30L));
	}

	@Test
	public void testWriteObject() throws IOException {
		Assert.assertEquals(
			"\"testValue\"",
			ConfigurationHandler.write(
				new Object() {

					@Override
					public String toString() {
						return "testValue";
					}

				}));
	}

	@Test
	public void testWriteShort() throws IOException {
		Assert.assertEquals("S\"2\"", ConfigurationHandler.write((short)2));
	}

	@Test
	public void testWriteString() throws IOException {
		Assert.assertEquals("\"test\"", ConfigurationHandler.write("test"));
	}

	@Test
	public void testWriteTypedArray() throws IOException {
		Assert.assertEquals(
			"B[\\\r\n  \"true\"\\\r\n]",
			ConfigurationHandler.write(new Boolean[] {true}));
	}

	@Test
	public void testWriteTypedCollection() throws IOException {
		Assert.assertEquals(
			"I(\\\r\n  \"1\",\\\r\n  \"2\"\\\r\n)",
			ConfigurationHandler.write(
				new ArrayList<Integer>() {
					{
						add(1);
						add(2);
					}
				}));
	}

}