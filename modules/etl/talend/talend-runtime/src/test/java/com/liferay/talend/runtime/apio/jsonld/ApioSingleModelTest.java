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
import static org.hamcrest.CoreMatchers.is;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.talend.runtime.apio.operation.Operation;

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
public class ApioSingleModelTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		String json = read("SampleResource.json");

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(json);

		_apioJsonLDResource = new ApioSingleModel(jsonNode);
	}

	@Test
	public void testGetResourceOperations() {
		List<Operation> operations =
			_apioJsonLDResource.getResourceOperations();

		Assert.assertThat(operations.size(), equalTo(2));

		Operation operation = operations.get(0);

		String method = operation.getMethod();
		String expects = operation.getExpects();

		Assert.assertThat(method, equalTo("DELETE"));
		Assert.assertThat(expects, is(_EMPTY_STRING));

		operation = operations.get(1);

		method = operation.getMethod();
		expects = operation.getExpects();

		Assert.assertThat(method, equalTo("PUT"));
		Assert.assertThat(
			expects, equalTo("https://apiosample.wedeploy.io/f/u/people"));
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

	private static final String _EMPTY_STRING = "";

	private static ApioSingleModel _apioJsonLDResource;

}