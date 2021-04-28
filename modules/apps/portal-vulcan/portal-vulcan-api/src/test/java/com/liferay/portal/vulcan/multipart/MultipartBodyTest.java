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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class MultipartBodyTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetBinaryFile() {
		BinaryFile binaryFile = new BinaryFile(
			"contentType", "fileName", null, 0);

		MultipartBody multipartBody = MultipartBody.of(
			Collections.singletonMap("file", binaryFile), __ -> _objectMapper,
			Collections.emptyMap());

		MatcherAssert.assertThat(
			multipartBody.getBinaryFile("file"), Is.is(binaryFile));
		MatcherAssert.assertThat(
			multipartBody.getBinaryFile("null"),
			Is.is(CoreMatchers.nullValue()));
	}

	@Test
	public void testGetValueAsInstance() throws IOException {

		// With object mapper

		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), __ -> _objectMapper,
			Collections.singletonMap(
				"key",
				JSONUtil.put(
					"list", Arrays.asList(1, 2, 3)
				).put(
					"number", 42
				).put(
					"string", "Hello"
				).toString()));

		TestClass testClass = multipartBody.getValueAsInstance(
			"key", TestClass.class);

		MatcherAssert.assertThat(testClass.list, Matchers.contains(1, 2, 3));
		MatcherAssert.assertThat(testClass.number, Is.is(42L));
		MatcherAssert.assertThat(testClass.string, Is.is("Hello"));
		MatcherAssert.assertThat(
			testClass.testClass, Is.is(CoreMatchers.nullValue()));

		try {
			multipartBody.getValueAsInstance("null", TestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception exception) {
			MatcherAssert.assertThat(
				exception,
				Is.is(CoreMatchers.instanceOf(BadRequestException.class)));
			MatcherAssert.assertThat(
				exception.getMessage(),
				Is.is("Missing JSON property with the key: null"));
		}

		// Without object mapper

		multipartBody = MultipartBody.of(
			Collections.emptyMap(), __ -> null,
			Collections.singletonMap("key", "value"));

		try {
			multipartBody.getValueAsInstance("key", TestClass.class);

			throw new AssertionError();
		}
		catch (Exception exception) {
			MatcherAssert.assertThat(
				exception,
				Is.is(
					CoreMatchers.instanceOf(
						InternalServerErrorException.class)));

			String expectedMessage =
				"Unable to get object mapper for class " +
					TestClass.class.getName();

			MatcherAssert.assertThat(
				exception.getMessage(), Is.is(expectedMessage));
		}
	}

	@Test
	public void testGetValueAsInstanceOptional() throws IOException {

		// Present optional

		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), __ -> _objectMapper,
			Collections.singletonMap(
				"key",
				JSONUtil.put(
					"list", Arrays.asList(1, 2, 3)
				).put(
					"number", 42
				).put(
					"string", "Hello"
				).toString()));

		Optional<TestClass> testClassOptional =
			multipartBody.getValueAsInstanceOptional("key", TestClass.class);

		MatcherAssert.assertThat(testClassOptional.isPresent(), Is.is(true));

		TestClass testClass = testClassOptional.get();

		MatcherAssert.assertThat(testClass.list, Matchers.contains(1, 2, 3));
		MatcherAssert.assertThat(testClass.number, Is.is(42L));
		MatcherAssert.assertThat(testClass.string, Is.is("Hello"));
		MatcherAssert.assertThat(
			testClass.testClass, Is.is(CoreMatchers.nullValue()));

		// Null optional

		testClassOptional = multipartBody.getValueAsInstanceOptional(
			"null", TestClass.class);

		MatcherAssert.assertThat(testClassOptional.isPresent(), Is.is(false));

		// Incorrect JSON

		multipartBody = MultipartBody.of(
			Collections.emptyMap(), __ -> _objectMapper,
			Collections.singletonMap(
				"key",
				JSONUtil.put(
					"number", 42
				).put(
					"string", "Hello"
				).put(
					"wrongKey", Arrays.asList(1, 2, 3)
				).toString()));

		try {
			multipartBody.getValueAsInstance("key", TestClass.class);

			throw new AssertionError();
		}
		catch (Exception exception) {
			MatcherAssert.assertThat(
				exception,
				Is.is(
					CoreMatchers.instanceOf(
						UnrecognizedPropertyException.class)));
		}
	}

	@Test
	public void testGetValueAsString() {
		MultipartBody multipartBody = MultipartBody.of(
			Collections.emptyMap(), __ -> _objectMapper,
			Collections.singletonMap("key", "value"));

		MatcherAssert.assertThat(
			multipartBody.getValueAsString("key"), Is.is("value"));
		MatcherAssert.assertThat(
			multipartBody.getValueAsString("null"),
			Is.is(CoreMatchers.nullValue()));
	}

	public static class TestClass {

		public List<Integer> list;
		public Long number;
		public String string;
		public TestClass testClass;

	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}