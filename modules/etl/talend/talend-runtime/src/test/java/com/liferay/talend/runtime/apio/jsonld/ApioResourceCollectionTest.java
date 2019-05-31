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

import com.liferay.talend.runtime.apio.operation.Operation;

import java.io.IOException;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Zoltán Takács
 */
public class ApioResourceCollectionTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		String json = read("SampleResourceCollection.json");

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(json);

		_apioJsonLDResource = new ApioResourceCollection(jsonNode);
	}

	@Test
	public void testGetContextVocabulary() {
		JsonNode jsonNode = _apioJsonLDResource.getContextJsonNode();

		String vocabulary = _apioJsonLDResource.getVocabulary(jsonNode);

		Assert.assertThat(vocabulary, equalTo("http://schema.org/"));
	}

	@Test
	public void testGetResourceActualPage() {
		String actualPage = _apioJsonLDResource.getResourceActualPage();

		Assert.assertThat(actualPage, equalTo(_DEFAULT_PAGE));
	}

	@Test
	public void testGetResourceCollectionType() {
		String resourceType = _apioJsonLDResource.getResourceCollectionType();

		Assert.assertThat(resourceType, equalTo("Person"));
	}

	@Test
	public void testGetResourceElementFieldNames() {
		List<String> fieldNames =
			_apioJsonLDResource.getResourceElementFieldNames();

		Assert.assertThat(fieldNames, hasItems("familyName", "givenName"));
	}

	@Test
	public void testGetResourceFirstPage() {
		String firstPage = _apioJsonLDResource.getResourceFirstPage();

		Assert.assertThat(firstPage, equalTo(_DEFAULT_PAGE));
	}

	@Test
	public void testGetResourceLastPage() {
		String lastPage = _apioJsonLDResource.getResourceLastPage();

		Assert.assertThat(lastPage, equalTo(_DEFAULT_PAGE));
	}

	@Test
	public void testGetResourceNextPage() {
		String nextPage = _apioJsonLDResource.getResourceNextPage();

		Assert.assertThat(nextPage, equalTo(_EMPTY));
	}

	@Test
	public void testGetResourceOperations() {
		List<Operation> operations =
			_apioJsonLDResource.getResourceOperations();

		Assert.assertThat(operations.size(), equalTo(1));

		Operation operation = operations.get(0);

		String method = operation.getMethod();
		String expects = operation.getExpects();

		Assert.assertThat(method, equalTo("POST"));
		Assert.assertThat(
			expects, equalTo("https://apiosample.wedeploy.io/f/c/people"));
	}

	@Test
	public void testGetResourcePreviousPage() {
		String previousPage = _apioJsonLDResource.getResourcePreviousPage();

		Assert.assertThat(previousPage, equalTo(_EMPTY));
	}

	@Test
	public void testIsSingleModel() {
		Assert.assertThat(_apioJsonLDResource.isSingleModel(), is(false));
	}

	@Test
	public void testNumberOfItems() {
		int numberOfItems = _apioJsonLDResource.getNumberOfItems();

		Assert.assertThat(numberOfItems, equalTo(10));
	}

	@Test
	public void testTotalItems() {
		int totalItems = _apioJsonLDResource.getTotalItems();

		Assert.assertThat(totalItems, equalTo(10));
	}

	@Test
	public void testWrongType() throws Exception {
		expectedException.expect(IOException.class);
		expectedException.expectMessage(
			"The type of the given resource is not a Collection");

		String json = read("SampleResource.json");

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(json);

		new ApioResourceCollection(jsonNode);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected static String read(String fileName) throws Exception {
		Class<?> clazz = ApioResourceCollectionTest.class;

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes);
	}

	private static final String _DEFAULT_PAGE =
		"https://apiosample.wedeploy.io/p/people?page=1&per_page=30";

	private static final String _EMPTY = "";

	private static ApioResourceCollection _apioJsonLDResource;

}