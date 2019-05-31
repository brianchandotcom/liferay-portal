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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Zoltán Takács
 */
public class ApioEntryPointTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		String json = read("SampleEntryPoint.json");

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(json);

		_apioEntryPoint = new ApioEntryPoint(jsonNode);
	}

	@Test
	public void testGetRootEndpointMap1() {
		Map<String, String> rootEndpointMap =
			_apioEntryPoint.getRootEndpointMap();

		Assert.assertThat(rootEndpointMap.size(), equalTo(2));
		Assert.assertThat(
			rootEndpointMap.values(), hasItems("BlogPosting", "Person"));
	}

	@Test
	public void testGetRootEndpointMap2() {
		Map<String, String> rootEndpointMap =
			_apioEntryPoint.getRootEndpointMap();

		Assert.assertThat(rootEndpointMap.size(), equalTo(2));
		Assert.assertThat(
			rootEndpointMap.keySet(),
			hasItems(
				"http://localhost:9000/p/blog-postings",
				"http://localhost:9000/p/people"));
	}

	@Test
	public void testWrongType() throws Exception {
		expectedException.expect(IOException.class);
		expectedException.expectMessage(
			"The given resource is not an entry point");

		String json = read("SampleResource.json");

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(json);

		new ApioEntryPoint(jsonNode);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected static String read(String fileName) throws Exception {
		Class<?> clazz = ApioResourceCollectionTest.class;

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes);
	}

	private static ApioEntryPoint _apioEntryPoint;

}