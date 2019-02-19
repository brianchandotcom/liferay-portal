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
		JSONFactoryImpl jsonFactoryImpl = new JSONFactoryImpl();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(jsonFactoryImpl);
	}

	@Test
	public void testGetBinaryFileReturnsBinaryFileIfPresent() {
		BinaryFile binaryFile = new BinaryFile(
			"contentType", "fileName", null, 0);

		MultipartBody multipartBody = MultipartBody.of(
			Collections.singletonMap("file", binaryFile),
			Collections.emptyMap(), __ -> _objectMapper);

		assertThat(multipartBody.getBinaryFile("file"), is(binaryFile));
		assertThat(multipartBody.getBinaryFile("null"), is(nullValue()));
	}

	@Test
	public void testGetJSONObjectValueReturnsCorrectValue() throws IOException {
		String json = JSONUtil.put(
			"string", "Hello"
		).put(
			"number", 42
		).put(
			"list", Arrays.asList(1, 2, 3)
		).toString();

		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.singletonMap("key", json),
			__ -> _objectMapper);

		JSONTestClass jsonTestClass = multipartBody.getJSONObjectValue(
			"key", JSONTestClass.class);

		assertThat(jsonTestClass.string, is("Hello"));
		assertThat(jsonTestClass.number, is(42L));
		assertThat(jsonTestClass.list, contains(1, 2, 3));
		assertThat(jsonTestClass.testClass, is(nullValue()));
	}

	@Test
	public void testGetJSONObjectValueThrowsBadRequestIfNullValue() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.emptyMap(),
			__ -> _objectMapper);

		try {
			multipartBody.getJSONObjectValue("key", JSONTestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			assertThat(e, is(instanceOf(BadRequestException.class)));
			assertThat(e.getMessage(), is("Missing JSON field with key {key}"));
		}
	}

	@Test
	public void testGetJSONObjectValueThrowsInternalServerErrorIfNullMapper() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.singletonMap("key", "value"),
			__ -> null);

		try {
			multipartBody.getJSONObjectValue("key", JSONTestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			assertThat(e, is(instanceOf(InternalServerErrorException.class)));

			String expectedMessage =
				"Unable to find ObjectMapper for class class " +
					JSONTestClass.class.getName();

			assertThat(e.getMessage(), is(expectedMessage));
		}
	}

	@Test
	public void testGetStringValueReturnsStringIfPresent() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.singletonMap("key", "value"),
			__ -> _objectMapper);

		assertThat(multipartBody.getStringValue("key"), is("value"));
		assertThat(multipartBody.getStringValue("null"), is(nullValue()));
	}

	public static class JSONTestClass {

		public List<Integer> list;
		public Long number;
		public String string;
		public JSONTestClass testClass;

	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}