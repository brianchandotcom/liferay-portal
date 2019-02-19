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

package com.liferay.portal.vulcan.util;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import java.util.List;

import javax.ws.rs.BadRequestException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class JSONUtilTest {

	@Test
	public void testReadValueFromNestedThrowsBadRequestIfInvalidField() {
		String json =
			"{\"string\": \"Hello\", \"number\": 42, \"list\": [1, 2, 3], " +
				"\"testClass\": {\"number\": \"hello\"}}";

		try {
			JSONUtil.readValueFrom(json, JSONTestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			String expectedMessage =
				"Unable to match field {testClass.number} with value {hello} " +
					"to Long";

			assertThat(e, is(instanceOf(BadRequestException.class)));
			assertThat(e.getMessage(), is(expectedMessage));
		}
	}

	@Test
	public void testReadValueFromReturnsCorrectValue() {
		String json =
			"{\"string\": \"Hello\", \"number\": 42, \"list\": [1, 2, 3]}";

		JSONTestClass jsonTestClass = JSONUtil.readValueFrom(
			json, JSONTestClass.class);

		assertThat(jsonTestClass.string, is("Hello"));
		assertThat(jsonTestClass.number, is(42L));
		assertThat(jsonTestClass.list, contains(1, 2, 3));
		assertThat(jsonTestClass.testClass, is(nullValue()));
	}

	@Test
	public void testReadValueFromThrowsBadRequestIfMissingField() {
		try {
			JSONUtil.readValueFrom(
				"{\"number\": \"hello\"}", JSONTestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			String expectedMessage =
				"Unable to match field {number} with value {hello} to Long";

			assertThat(e, is(instanceOf(BadRequestException.class)));
			assertThat(e.getMessage(), is(expectedMessage));
		}
	}

	@Test
	public void testReadValueFromThrowsBadRequestIfNotJSONValue() {
		try {
			JSONUtil.readValueFrom("hello", JSONTestClass.class);

			throw new AssertionError("Should thrown exception");
		}
		catch (Exception e) {
			assertThat(e, is(instanceOf(BadRequestException.class)));
			assertThat(e.getMessage(), is("Input is not a valid JSON"));
		}
	}

	public static class JSONTestClass {

		public List<Integer> list;
		public Long number;
		public String string;
		public JSONTestClass testClass;

	}

}