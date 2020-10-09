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

package com.liferay.portal.file.install.internal.properties;

import com.liferay.petra.string.StringBundler;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.Arrays;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class TypedPropertiesTest {

	@Test
	public void testKeySet() throws IOException {
		TypedProperties typedProperties = _createTypedProperties(
			"testKey1 = \"testValue1\"\ntestKey2 = \"testValue2\"");

		Set<String> keys = typedProperties.keySet();

		Assert.assertEquals(keys.toString(), 2, keys.size());

		for (String key : keys) {
			Assert.assertNotEquals(null, typedProperties.get(key));
		}
	}

	@Test
	public void testLoadandSaveArray() throws IOException {
		String line = "testKey = [\"testValue1\", \"testValue2\"]";

		TypedProperties typedProperties = _createTypedProperties(line);

		Assert.assertArrayEquals(
			new String[] {"testValue1", "testValue2"},
			(String[])typedProperties.get("testKey"));

		_assertSave(typedProperties, line);
	}

	@Test
	public void testLoadandSaveCollection() throws IOException {
		String line = "testKey = (\"testValue1\", \"testValue2\")";

		TypedProperties typedProperties = _createTypedProperties(line);

		Assert.assertEquals(
			Arrays.asList("testValue1", "testValue2"),
			typedProperties.get("testKey"));

		_assertSave(typedProperties, line);
	}

	@Test
	public void testLoadandSaveComment() throws IOException {
		String line = "#comment\ntestKey = \"testValue\"";

		TypedProperties typedProperties = _createTypedProperties(line);

		Assert.assertEquals("testValue", typedProperties.get("testKey"));

		_assertSave(typedProperties, line);
	}

	@Test
	public void testLoadandSaveEscapedEquals() throws IOException {
		String line = "testKey = \"testValue\\=test\"";

		TypedProperties typedProperties = _createTypedProperties(line);

		Assert.assertEquals("testValue=test", typedProperties.get("testKey"));

		_assertSave(typedProperties, line);
	}

	@Test
	public void testLoadandSaveMultiline() throws IOException {
		String line =
			"testKey = [\\\n\t\"testValue1\",\\\n\t\"testValue2\"\\\n]";

		TypedProperties typedProperties = _createTypedProperties(line);

		Assert.assertArrayEquals(
			new String[] {"testValue1", "testValue2"},
			(String[])typedProperties.get("testKey"));

		_assertSave(typedProperties, line);
	}

	@Test
	public void testLoadAndSaveSubstitution() throws IOException {
		String systemKey = "testSystemKey";

		String line = StringBundler.concat("testKey = \"${", systemKey, "}\"");

		String systemValue = "testSystemValue";

		String oldSystemValue = System.getProperty(systemKey);

		System.setProperty(systemKey, systemValue);

		TypedProperties typedProperties = _createTypedProperties(line);

		try {
			Assert.assertEquals(
				"testSystemValue", typedProperties.get("testKey"));
		}
		finally {
			if (oldSystemValue == null) {
				System.clearProperty(systemKey);
			}
			else {
				System.setProperty(systemKey, oldSystemValue);
			}
		}

		_assertSave(typedProperties, line);
	}

	@Test
	public void testLoadandStoreMultilineString() throws IOException {
		String line = "testKey = \"testValue1,\\\n\ttestValue2\"";

		TypedProperties typedProperties = _createTypedProperties(line);

		Assert.assertEquals(
			"testValue1,testValue2", typedProperties.get("testKey"));

		_assertSave(typedProperties, line);
	}

	@Test
	public void testLoadNontyped() throws IOException {
		TypedProperties typedProperties = _createTypedProperties(
			"testKey = \"testValue\"");

		Assert.assertEquals("testValue", typedProperties.get("testKey"));
	}

	@Test
	public void testLoadPutandSaveMultiline() throws IOException {
		String line =
			"testKey = [\\\n\t\"testValue1\",\\\n\t\"testValue2\"\\\n]";

		TypedProperties typedProperties = _createTypedProperties(line);

		typedProperties.put(
			"testKey", new String[] {"testValue1", "testValue2"});

		_assertSave(typedProperties, line);
	}

	@Test
	public void testLoadTyped() throws IOException {
		TypedProperties typedProperties = _createTypedProperties(
			"testKey = I\"1\"");

		Assert.assertEquals(1, typedProperties.get("testKey"));
	}

	@Test
	public void testOverwriteNontypedWithNontyped() throws IOException {
		TypedProperties typedProperties = _createTypedProperties(
			"testKey = \"testValue1\"");

		Assert.assertEquals("testValue1", typedProperties.get("testKey"));

		typedProperties.put("testKey", "testValue2");

		Assert.assertEquals("testValue2", typedProperties.get("testKey"));
	}

	@Test
	public void testOverwriteNontypedWithTyped() throws IOException {
		TypedProperties typedProperties = _createTypedProperties(
			"testKey = \"1\"");

		Assert.assertEquals("1", typedProperties.get("testKey"));

		typedProperties.put("testKey", 1);

		Assert.assertEquals(1, typedProperties.get("testKey"));
	}

	@Test
	public void testOverwriteTypedWithNontyped() throws IOException {
		TypedProperties typedProperties = _createTypedProperties(
			"testKey = I\"1\"");

		Assert.assertEquals(1, typedProperties.get("testKey"));

		typedProperties.put("testKey", "1");

		Assert.assertEquals("1", typedProperties.get("testKey"));
	}

	@Test
	public void testOverwriteTypedWithTyped() throws IOException {
		TypedProperties typedProperties = _createTypedProperties(
			"testKey = I\"1\"");

		Assert.assertEquals(1, typedProperties.get("testKey"));

		typedProperties.put("testKey", 2);

		Assert.assertEquals(2, typedProperties.get("testKey"));
	}

	@Test
	public void testPutandSave() throws IOException {
		TypedProperties typedProperties = new TypedProperties();

		typedProperties.put("testKey", "testValue");

		_assertSave(typedProperties, "testKey = \"testValue\"");
	}

	@Test
	public void testRemove() throws IOException {
		TypedProperties typedProperties = _createTypedProperties(
			"testKey = \"testValue\"");

		Assert.assertEquals("testValue", typedProperties.get("testKey"));

		typedProperties.remove("testKey");

		Assert.assertEquals(null, typedProperties.get("testKey"));
	}

	@Test
	public void testSaveNontyped() throws IOException {
		String line = "testKey = \"testValue\"";

		TypedProperties typedProperties = _createTypedProperties(line);

		_assertSave(typedProperties, line);
	}

	@Test
	public void testSaveTyped() throws IOException {
		String line = "testKey = I\"1\"";

		TypedProperties typedProperties = _createTypedProperties(line);

		_assertSave(typedProperties, line);
	}

	@Test
	public void testWriteNontyped() throws IOException {
		TypedProperties typedProperties = new TypedProperties();

		typedProperties.put("testKey", "testValue");

		Assert.assertEquals("testValue", typedProperties.get("testKey"));
	}

	@Test
	public void testWriteTyped() throws IOException {
		TypedProperties typedProperties = new TypedProperties();

		typedProperties.put("testKey", 1);

		Assert.assertEquals(1, typedProperties.get("testKey"));
	}

	private void _assertSave(TypedProperties typedProperties, String expected)
		throws IOException {

		try (StringWriter stringWriter = new StringWriter()) {
			typedProperties.save(stringWriter);

			Assert.assertEquals(expected, stringWriter.toString());
		}
	}

	private TypedProperties _createTypedProperties(String string)
		throws IOException {

		TypedProperties typedProperties = new TypedProperties();

		try (StringReader stringReader = new StringReader(string)) {
			typedProperties.load(stringReader);
		}

		return typedProperties;
	}

}