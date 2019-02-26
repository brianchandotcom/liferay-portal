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

package com.liferay.portal.vulcan.multipart;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class MultipartBodyTest {

	@Before
	public void setUp() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetBinaryFile() {
		BinaryFile binaryFile = new BinaryFile(
			"contentType", "fileName", null, 0);

		MultipartBody multipartBody = MultipartBody.of(
			Collections.singletonMap("file", binaryFile), __ -> _objectMapper,
			Collections.emptyMap());

		assertThat(multipartBody.getBinaryFile("file"), is(binaryFile));
		assertThat(multipartBody.getBinaryFile("null"), is(nullValue()));
	}

	@Test
	public void testGetValueAsString() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), __ -> _objectMapper,
			Collections.singletonMap("key", "value"));

		assertThat(multipartBody.getValueAsString("key"), is("value"));
		assertThat(multipartBody.getValueAsString("null"), is(nullValue()));
	}

	@Test
	public void testGgetValueAsInstance() throws IOException {

		// With object mapper

		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), __ -> _objectMapper,
			Collections.singletonMap(
				"key",
				JSONUtil.put(
					"string", "Hello"
				).put(
					"number", 42
				).put(
					"list", Arrays.asList(1, 2, 3)
				).toString()));

		TestClass testClass = multipartBody.getValueAsInstance(
			"key", TestClass.class);

		assertThat(testClass.list, contains(1, 2, 3));
		assertThat(testClass.number, is(42L));
		assertThat(testClass.string, is("Hello"));
		assertThat(testClass.testClass, is(nullValue()));

		try {
			multipartBody.getValueAsInstance("null", TestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			assertThat(e, is(instanceOf(BadRequestException.class)));
			assertThat(
				e.getMessage(), is("Missing JSON property with the key: null"));
		}

		// Without object mapper

		multipartBody = MultipartBody.of(
			Collections.emptyMap(), __ -> null,
			Collections.singletonMap("key", "value"));

		try {
			multipartBody.getValueAsInstance("key", TestClass.class);

			throw new AssertionError();
		}
		catch (Exception e) {
			assertThat(e, is(instanceOf(InternalServerErrorException.class)));

			String expectedMessage =
				"Unable to get object mapper for class " +
					TestClass.class.getName();

			assertThat(e.getMessage(), is(expectedMessage));
		}
	}

	public static class TestClass {

		public List<Integer> list;
		public Long number;
		public String string;
		public TestClass testClass;

	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}