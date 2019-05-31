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

import com.liferay.talend.runtime.apio.form.Property;

import java.io.IOException;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Zoltán Takács
 */
public class ApioApiDocumentationTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		String json = read("SampleApiDocumentation.json");

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(json);

		_apioApiDocumentation = new ApioApiDocumentation(jsonNode);
	}

	@Test
	public void testGetDescription() {
		String description = _apioApiDocumentation.getDescription();

		Assert.assertThat(
			description,
			equalTo(
				"This API allows developers to try a Hypermedia API without " +
					"creating one"));
	}

	@Test
	public void testGetSupportedClasses1() {
		List<ApioApiDocumentation.SupportedClass> supportedClasses =
			_apioApiDocumentation.getSupportedClasses();

		Assert.assertThat(supportedClasses.size(), equalTo(5));

		Stream<ApioApiDocumentation.SupportedClass> supportedClassStream =
			supportedClasses.stream();

		ApioApiDocumentation.SupportedClass supportedClass =
			supportedClassStream.filter(
				clazz -> "Comment".equals(clazz.getName())
			).findFirst(
			).orElseThrow(
				() -> new AssertionError(
					"Unable to find 'Commerce' supported class")
			);

		List<Property> supportedProperties =
			supportedClass.getSupportedProperties();

		Stream<Property> propertyStream = supportedProperties.stream();

		List<String> propertyNames = propertyStream.map(
			Property::getName
		).collect(
			Collectors.toList()
		);

		Assert.assertThat(supportedProperties.size(), equalTo(4));
		Assert.assertThat(
			propertyNames,
			hasItems("dateCreated", "dateModified", "text", "author"));
	}

	@Test
	public void testGetSupportedClasses2() {
		List<ApioApiDocumentation.SupportedClass> supportedClasses =
			_apioApiDocumentation.getSupportedClasses();

		Assert.assertThat(supportedClasses.size(), equalTo(5));

		Stream<ApioApiDocumentation.SupportedClass> supportedClassStream =
			supportedClasses.stream();

		ApioApiDocumentation.SupportedClass supportedClass =
			supportedClassStream.filter(
				clazz -> "BlogPosting".equals(clazz.getName())
			).findFirst(
			).orElseThrow(
				() -> new AssertionError(
					"Unable to find 'BlogPosting' supported class")
			);

		List<Property> supportedProperties =
			supportedClass.getSupportedProperties();

		Stream<Property> propertyStream = supportedProperties.stream();

		List<String> propertyNames = propertyStream.map(
			Property::getName
		).collect(
			Collectors.toList()
		);

		Assert.assertThat(supportedProperties.size(), equalTo(8));
		Assert.assertThat(
			propertyNames,
			hasItems(
				"dateCreated", "dateModified", "alternativeHeadline",
				"articleBody", "fileFormat", "headline", "creator", "comment"));
	}

	@Test
	public void testGetTitle() {
		String title = _apioApiDocumentation.getTitle();

		Assert.assertThat(title, equalTo("Apio Sample API"));
	}

	@Test
	public void testWrongType() throws Exception {
		expectedException.expect(IOException.class);
		expectedException.expectMessage(
			"The type of the given resource is not an instance of " +
				"ApiDocumentation");

		String json = read("SampleResource.json");

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(json);

		new ApioApiDocumentation(jsonNode);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected static String read(String fileName) throws Exception {
		Class<?> clazz = ApioResourceCollectionTest.class;

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes);
	}

	private static ApioApiDocumentation _apioApiDocumentation;

}