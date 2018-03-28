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

package com.liferay.talend.runtime.apio.jsonld;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Zoltán Takács
 */
public class ApioUtilsTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		String json = read("SampleSingleModelResource.json");

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(json);

		_apioJsonLDResource = new ApioSingleModel(jsonNode);
	}

	@Test
	public void testGetTypeCoercionTermKeys() {
		JsonNode contextJsonNode = _apioJsonLDResource.getContextJsonNode();

		List<String> typeCoercionTermKeys = ApioUtils.getTypeCoercionTermKeys(
			contextJsonNode);

		Assert.assertThat(typeCoercionTermKeys.size(), equalTo(2));

		Assert.assertThat(
			typeCoercionTermKeys, hasItems("blogPosts", "folder"));
	}

	@Test
	public void testIsSingleModel() {
		Assert.assertThat(_apioJsonLDResource.isSingleModel(), is(true));
	}

	protected static String read(String fileName) throws Exception {
		Class<?> clazz = ApioResourceCollectionTest.class;

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes);
	}

	private static ApioSingleModel _apioJsonLDResource;

}