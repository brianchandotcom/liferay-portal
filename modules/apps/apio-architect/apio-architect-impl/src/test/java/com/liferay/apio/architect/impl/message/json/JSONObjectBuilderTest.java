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

package com.liferay.apio.architect.impl.message.json;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.util.Arrays;

import org.json.JSONException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class JSONObjectBuilderTest {

	@Test
	public void testInvokingAddAllOnAnArrayValueCreatesAValidJsonArray()
		throws JSONException {

		JSONObjectBuilder.ArrayValueStep arrayValueStep =
			_jsonObjectBuilder.field(
				"array"
			).arrayValue();

		arrayValueStep.addAllBooleans(Arrays.asList(false, true));
		arrayValueStep.addAllNumbers(Arrays.asList(21, 42));
		arrayValueStep.addAllStrings(Arrays.asList("api", "apio"));

		String expected = "{'array': [false, true, 21, 42, 'api', 'apio']}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingAddConsumerCreatesAValidJsonArray()
		throws JSONException {

		_jsonObjectBuilder.field(
			"array"
		).arrayValue(
		).add(
			jsonObjectBuilder -> jsonObjectBuilder.field(
				"solution"
			).numberValue(
				42
			)
		);

		String expected = "{'array': [{'solution': 42}]}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingAddJsonObjectBuilderCreatesAValidJsonArray()
		throws JSONException {

		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilder();

		jsonObjectBuilder.field(
			"solution"
		).numberValue(
			42
		);

		_jsonObjectBuilder.field(
			"array"
		).arrayValue(
		).add(
			jsonObjectBuilder
		);

		String expected = "{'array': [{'solution': 42}]}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingAddOnAnArrayValueCreatesAValidJsonArray()
		throws JSONException {

		JSONObjectBuilder.ArrayValueStep arrayValueStep =
			_jsonObjectBuilder.field(
				"array"
			).arrayValue();

		arrayValueStep.addBoolean(true);
		arrayValueStep.addNumber(42);
		arrayValueStep.addString("apio");

		String expected = "{'array': [true, 42, 'apio']}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingAddVarargConsumersCreatesAValidJsonArray()
		throws JSONException {

		_jsonObjectBuilder.field(
			"array"
		).arrayValue(
		).add(
			jsonObjectBuilder -> jsonObjectBuilder.field(
				"solution"
			).numberValue(
				42
			),
			jsonObjectBuilder -> jsonObjectBuilder.field(
				"solution"
			).numberValue(
				42
			)
		);

		String expected = "{'array': [{'solution': 42}, {'solution': 42}]}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingArrayValueCreatesAJsonArray() throws JSONException {
		_jsonObjectBuilder.field(
			"array"
		).arrayValue();

		String expected = "{'array': []}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingArrayValueWithConsumersCreatesAValidJsonArray()
		throws JSONException {

		_jsonObjectBuilder.field(
			"array"
		).arrayValue(
			arrayBuilder -> arrayBuilder.addString("first"),
			arrayBuilder -> arrayBuilder.addString("second")
		);

		String expected = "{'array': ['first', 'second']}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingBooleanValueCreatesABoolean() throws JSONException {
		_jsonObjectBuilder.field(
			"solution"
		).booleanValue(
			true
		);

		String expected = "{'solution': true}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFalseIfElseConditionCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.ifElseCondition(
			false, builder -> builder.field("true"),
			builder -> builder.field("solution")
		).numberValue(
			42
		);

		String expected = "{'solution': 42}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFieldAndFalseIfConditionCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.field(
			"first"
		).ifCondition(
			false, builder -> builder.field("solution")
		).numberValue(
			42
		);

		String expected = "{'first': 42}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFieldAndFalseIfElseConditionCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.field(
			"first"
		).ifElseCondition(
			false, builder -> builder.field("true"),
			builder -> builder.field("solution")
		).numberValue(
			42
		);

		String expected = "{'first': {'solution': 42}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFieldAndNestedPrefixedFieldCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.field(
			"solution"
		).nestedPrefixedField(
			"prefix", "first", "second", "third"
		).numberValue(
			42
		);

		String expected =
			"{'solution': {'prefix': {'first': {'prefix': {'second': {" +
				"'prefix': {'third': 42}}}}}}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFieldAndNestedSuffixedFieldCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.field(
			"solution"
		).nestedSuffixedField(
			"suffix", "first", "second", "third"
		).numberValue(
			42
		);

		String expected =
			"{'solution': {'first': {'suffix': {'second': {'suffix': {" +
				"'third': {'suffix': 42}}}}}}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFieldAndTrueIfConditionCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.field(
			"first"
		).ifCondition(
			true, builder -> builder.field("solution")
		).numberValue(
			42
		);

		String expected = "{'first': {'solution': 42}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFieldAndTrueIfElseConditionCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.field(
			"first"
		).ifElseCondition(
			true, builder -> builder.field("solution"),
			builder -> builder.field("false")
		).numberValue(
			42
		);

		String expected = "{'first': {'solution': 42}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFieldCreatesACorrectField() throws JSONException {
		_jsonObjectBuilder.field(
			"solution"
		).numberValue(
			42
		);

		String expected = "{'solution': 42}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingFieldsWithConsumersCreatesAValidJsonObject()
		throws JSONException {

		_jsonObjectBuilder.field(
			"object"
		).fields(
			builder -> builder.field(
				"first"
			).numberValue(
				42
			),
			builder -> builder.field(
				"second"
			).numberValue(
				2018
			)
		);

		String expected = "{'object': {'first': 42, 'second': 2018}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingNestedFieldCreatesACorrectNestedField()
		throws JSONException {

		_jsonObjectBuilder.nestedField(
			"the", "solution"
		).numberValue(
			42
		);

		String expected = "{'the': {'solution': 42}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingNestedPrefixedFieldCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.nestedPrefixedField(
			"prefix", "first", "second", "third"
		).numberValue(
			42
		);

		String expected =
			"{'prefix': {'first': {'prefix': {'second': {'prefix': {" +
				"'third': 42}}}}}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingNestedSuffixedFieldCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.nestedSuffixedField(
			"suffix", "first", "second", "third"
		).numberValue(
			42
		);

		String expected =
			"{'first': {'suffix': {'second': {'suffix': {'third': {" +
				"'suffix': 42}}}}}}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingNumberValueCreatesANumber() throws JSONException {
		_jsonObjectBuilder.field(
			"solution"
		).numberValue(
			42
		);

		String expected = "{'solution': 42}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingStringValueCreatesAString() throws JSONException {
		_jsonObjectBuilder.field(
			"solution"
		).stringValue(
			"forty-two"
		);

		String expected = "{'solution': 'forty-two'}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	@Test
	public void testInvokingTrueIfElseConditionCreatesACorrectField()
		throws JSONException {

		_jsonObjectBuilder.ifElseCondition(
			true, builder -> builder.field("solution"),
			builder -> builder.field("false")
		).numberValue(
			42
		);

		String expected = "{'solution': 42}";

		assertEquals(expected, _jsonObjectBuilder.build(), true);
	}

	private final JSONObjectBuilder _jsonObjectBuilder =
		new JSONObjectBuilder();

}