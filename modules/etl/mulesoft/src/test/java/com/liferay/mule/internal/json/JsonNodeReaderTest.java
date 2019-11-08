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

package com.liferay.mule.internal.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Matija Petanjek
 */
public class JsonNodeReaderTest {

	@Before
	public void setUp() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"example.json");

		_jsonNode = objectMapper.readTree(inputStream);
	}

	@Test
	public void testFetchDescendantJsonNode() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		JsonNode jsonNode = jsonNodeReader.fetchDescendantJsonNode(
			_jsonNode, "fieldName2>nestedFieldName1>nestedFieldNameWith/");

		Assert.assertEquals("nestedValue1", jsonNode.textValue());
	}

	@Test
	public void testFetchDescendantJsonNodeWithNonexistentField() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		JsonNode jsonNode = jsonNodeReader.fetchDescendantJsonNode(
			_jsonNode, "fieldName3>nestedFieldName1>nestedFieldNameWith/");

		Assert.assertTrue(jsonNode instanceof NullNode);
	}

	@Test
	public void testFetchDescendantJsonNodeWithNonexistentTopLevelField() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		JsonNode jsonNode = jsonNodeReader.fetchDescendantJsonNode(
			_jsonNode, "fieldName3");

		Assert.assertTrue(jsonNode instanceof NullNode);
	}

	@Test
	public void testFetchDescendantJsonNodeWithTopLevelField() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		JsonNode jsonNode = jsonNodeReader.fetchDescendantJsonNode(
			_jsonNode, "fieldName1");

		Assert.assertEquals("value1", jsonNode.textValue());
	}

	@Test
	public void testGetDescendantJsonNode() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		JsonNode jsonNode = jsonNodeReader.getDescendantJsonNode(
			_jsonNode, "fieldName2>nestedFieldName1>nestedFieldNameWith/");

		Assert.assertEquals("nestedValue1", jsonNode.textValue());
	}

	@Test(expected = NullPointerException.class)
	public void testGetDescendantJsonNodeWithNonexistentField() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		jsonNodeReader.getDescendantJsonNode(
			_jsonNode, "fieldName3>nestedFieldName1");
	}

	@Test(expected = NullPointerException.class)
	public void testGetDescendantJsonNodeWithNonexistentTopLevelField() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		jsonNodeReader.getDescendantJsonNode(_jsonNode, "fieldName3");
	}

	@Test
	public void testGetDescendantJsonNodeWithTopLevelField() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		JsonNode jsonNode = jsonNodeReader.getDescendantJsonNode(
			_jsonNode, "fieldName1");

		Assert.assertEquals("value1", jsonNode.textValue());
	}

	@Test
	public void testHasPath() {
		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		Assert.assertTrue(
			jsonNodeReader.hasPath(
				_jsonNode, "fieldName2>nestedFieldName1>nestedFieldNameWith/"));

		Assert.assertFalse(
			jsonNodeReader.hasPath(
				_jsonNode, "fieldName1>nestedFieldName1>nestedFieldNameWith/"));
	}

	private JsonNode _jsonNode;

}