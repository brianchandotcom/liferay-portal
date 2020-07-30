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
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class TypedPropertiesTest {

	@Test
	public void testClear() {
		TypedProperties typedProperties = new TypedProperties();

		typedProperties.put("testKey1", 1);
		typedProperties.put("testKey2", 2);

		Assert.assertEquals(2, typedProperties.size());

		typedProperties.clear();

		Assert.assertTrue(typedProperties.isEmpty());
	}

	@Test
	public void testIterator() {
		TypedProperties typedProperties = new TypedProperties();

		typedProperties.put("testKey", 1);

		Set<Map.Entry<String, Object>> set = typedProperties.entrySet();

		Assert.assertEquals(set.toString(), 1, set.size());

		Iterator<Map.Entry<String, Object>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();

			Assert.assertEquals("testKey", entry.getKey());
			Assert.assertEquals(1, entry.getValue());

			entry.setValue(2);

			Assert.assertEquals(2, typedProperties.get("testKey"));

			Assert.assertEquals(2, entry.getValue());

			iterator.remove();
		}

		Assert.assertTrue(typedProperties.isEmpty());
	}

	@Test
	public void testLoadAndStoreSubstitution() throws IOException {
		TypedProperties typedProperties = new TypedProperties();

		String systemKey = "testSystemKey";

		String systemValue = "testSystemValue";

		String oldSystemValue = System.getProperty(systemKey);

		System.setProperty(systemKey, systemValue);

		String line = StringBundler.concat("testKey = \"${", systemKey, "}\"");

		try (StringReader stringReader = new StringReader(line)) {
			typedProperties.load(stringReader);

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

		try (StringWriter stringWriter = new StringWriter()) {
			typedProperties.save(stringWriter);

			Assert.assertEquals(
				line.concat(StringPool.NEW_LINE), stringWriter.toString());
		}
	}

	@Test
	public void testLoadNontyped() throws IOException {
		TypedProperties typedProperties = new TypedProperties();

		try (StringReader stringReader = new StringReader(
				"testKey = \"testvalue\"")) {

			typedProperties.load(stringReader);
		}

		Assert.assertEquals("testvalue", typedProperties.get("testKey"));
	}

	@Test
	public void testLoadTyped() throws IOException {
		TypedProperties typedProperties = new TypedProperties();

		try (StringReader stringReader = new StringReader("testKey = I\"1\"")) {
			typedProperties.load(stringReader);
		}

		Assert.assertEquals(1, typedProperties.get("testKey"));
	}

	@Test
	public void testRemove() {
		TypedProperties typedProperties = new TypedProperties();

		typedProperties.put("testKey", "testValue");

		Assert.assertEquals("testValue", typedProperties.get("testKey"));

		typedProperties.remove("testKey");

		Assert.assertEquals(null, typedProperties.get("testKey"));
	}

	@Test
	public void testStoreNontyped() throws IOException {
		TypedProperties typedProperties = new TypedProperties();

		try (StringReader stringReader = new StringReader(
				"testKey = \"testValue\"")) {

			typedProperties.load(stringReader);
		}

		try (StringWriter stringWriter = new StringWriter()) {
			typedProperties.save(stringWriter);

			Assert.assertEquals(
				"testKey = \"testValue\"\n", stringWriter.toString());
		}
	}

	@Test
	public void testStoreTyped() throws IOException {
		TypedProperties typedProperties = new TypedProperties();

		try (StringReader stringReader = new StringReader("testKey = I\"1\"")) {
			typedProperties.load(stringReader);
		}

		try (StringWriter stringWriter = new StringWriter()) {
			typedProperties.save(stringWriter);

			Assert.assertEquals("testKey = I\"1\"\n", stringWriter.toString());
		}
	}

	@Test
	public void testWriteNontyped() {
		TypedProperties typedProperties = new TypedProperties();

		typedProperties.put("testKey", "testValue");

		Assert.assertEquals("testValue", typedProperties.get("testKey"));
	}

	@Test
	public void testWriteTyped() {
		TypedProperties typedProperties = new TypedProperties();

		typedProperties.put("testKey", 1);

		Assert.assertEquals(1, typedProperties.get("testKey"));
	}

}