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

package com.liferay.mule.internal.values;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;

/**
 * @author Matija Petanjek
 */
public class ClassNameValueProviderTest {

	@Before
	public void setUp() throws IOException {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/mule/internal/metadata/oas-fragment-metadata-types." +
				"json");

		ObjectMapper objectMapper = new ObjectMapper();

		oasJsonNode = objectMapper.readTree(inputStream);
	}

	@Test
	public void testGetClassNameValues() {
		ClassNameValueProvider classNameValueProvider =
			new ClassNameValueProvider();

		Set<Value> values = classNameValueProvider.getClassNameValues(
			oasJsonNode);

		Assert.assertEquals(values.toString(), 2, values.size());

		Value value = ValueBuilder.newValue(
			"com.liferay.headless.v1_0.Entity"
		).build();

		Assert.assertTrue(values.contains(value));

		value = ValueBuilder.newValue(
			"com.liferay.headless.v1_0.NestedEntity"
		).build();

		Assert.assertTrue(values.contains(value));
	}

	private JsonNode oasJsonNode;

}