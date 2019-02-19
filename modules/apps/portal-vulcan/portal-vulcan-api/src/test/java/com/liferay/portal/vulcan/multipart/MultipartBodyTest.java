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

import java.util.Collections;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class MultipartBodyTest {

	@Test
	public void testGetBinaryFileReturnsBinaryFileIfPresent() {
		BinaryFile binaryFile = new BinaryFile(
			"contentType", "fileName", null, 0);

		MultipartBody multipartBody = MultipartBody.of(
			Collections.singletonMap("file", binaryFile),
			Collections.emptyMap());

		assertThat(multipartBody.getBinaryFile("file"), is(binaryFile));
		assertThat(multipartBody.getBinaryFile("null"), is(nullValue()));
	}

	@Test
	public void testGetJSONObjectValueNestedThrowsBadRequestIfInvalidField() {
		String json =
			"{\"string\": \"Hello\", \"number\": 42, \"list\": [1, 2, 3], " +
				"\"testClass\": {\"number\": \"hello\"}}";

		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.singletonMap("key", json));

		try {
			multipartBody.getJSONObjectValue("key", JSONTestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			String expectedMessage =
				"Error in field with key {key}: Unable to match field " +
					"{testClass.number} with value {hello} to Long";

			assertThat(e, is(instanceOf(BadRequestException.class)));
			assertThat(e.getMessage(), is(expectedMessage));
		}
	}

	@Test
	public void testGetJSONObjectValueReturnsCorrectValue() {
		String json =
			"{\"string\": \"Hello\", \"number\": 42, \"list\": [1, 2, 3]}";

		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.singletonMap("key", json));

		JSONTestClass jsonTestClass = multipartBody.getJSONObjectValue(
			"key", JSONTestClass.class);

		assertThat(jsonTestClass.string, is("Hello"));
		assertThat(jsonTestClass.number, is(42L));
		assertThat(jsonTestClass.list, contains(1, 2, 3));
		assertThat(jsonTestClass.testClass, is(nullValue()));
	}

	@Test
	public void testGetJSONObjectValueThrowsBadRequestIfMissingField() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(),
			Collections.singletonMap("key", "{\"number\": \"hello\"}"));

		try {
			multipartBody.getJSONObjectValue("key", JSONTestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			String expectedMessage =
				"Error in field with key {key}: Unable to match field " +
					"{number} with value {hello} to Long";

			assertThat(e, is(instanceOf(BadRequestException.class)));
			assertThat(e.getMessage(), is(expectedMessage));
		}
	}

	@Test
	public void testGetJSONObjectValueThrowsBadRequestIfNotJSONValue() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.singletonMap("key", "hello"));

		try {
			multipartBody.getJSONObjectValue("key", JSONTestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			assertThat(e, is(instanceOf(BadRequestException.class)));
			assertThat(
				e.getMessage(),
				is("Error in field with key {key}: Input is not a valid JSON"));
		}
	}

	@Test
	public void testGetJSONObjectValueThrowsBadRequestIfNullValue() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.emptyMap());

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
	public void testGetStringValueReturnsStringIfPresent() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), Collections.singletonMap("key", "value"));

		assertThat(multipartBody.getStringValue("key"), is("value"));
		assertThat(multipartBody.getStringValue("null"), is(nullValue()));
	}

	public static class JSONTestClass {

		public List<Integer> list;
		public Long number;
		public String string;
		public JSONTestClass testClass;

	}

}