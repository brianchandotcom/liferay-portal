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

package com.liferay.headless.foundation.dto.v1_0.parsing;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class ValidValueJSONParserTest extends BaseJSONParserTestCase {

	@Test
	public void testReadNextChar() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("1}");

		jsonParser.readNextChar();

		Assert.assertEquals(1, jsonParser.index);
		Assert.assertEquals('1', jsonParser.lastChar);

		jsonParser.readNextChar();

		Assert.assertEquals(2, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);
	}

	@Test
	public void testReadNextCharTwice() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("1");

		jsonParser.readNextChar();

		Assert.assertEquals(1, jsonParser.index);
		Assert.assertEquals('1', jsonParser.lastChar);

		jsonParser.readNextChar();

		Assert.assertEquals(1, jsonParser.index);
		Assert.assertEquals('1', jsonParser.lastChar);
	}

	@Test
	public void testReadValueArrayEmpty() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("[]}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		Assert.assertEquals(3, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Object[] objects = (Object[])value;

		Assert.assertEquals(Arrays.toString(objects), 0, objects.length);
	}

	@Test
	public void testReadValueArrayObject() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields(
			"[{\"f1\":\"v1\"},{\"f1\":\"v2\"},{\"f1\":\"v3\"}]}");
		jsonParser.readNextChar();

		String[] strings = Stream.of(
			(Object[])jsonParser.readValue()
		).map(
			String.class::cast
		).toArray(
			String[]::new
		);

		Assert.assertEquals(38, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("{\"f1\":\"v1\"}", strings[0]);
		Assert.assertEquals("{\"f1\":\"v2\"}", strings[1]);
		Assert.assertEquals("{\"f1\":\"v3\"}", strings[2]);
	}

	@Test
	public void testReadValueArrayStringOneElement() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("[\"ab\"]}");
		jsonParser.readNextChar();

		String[] strings = Stream.of(
			(Object[])jsonParser.readValue()
		).map(
			String.class::cast
		).toArray(
			String[]::new
		);

		Assert.assertEquals(7, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("ab", strings[0]);
	}

	@Test
	public void testReadValueBooleanFalse() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("false}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		Assert.assertEquals(6, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals(false, value);
	}

	@Test
	public void testReadValueBooleanTrue() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("true}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		Assert.assertEquals(5, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals(true, value);
	}

	@Test
	public void testReadValueNull() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("null}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		Assert.assertEquals(5, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertNull(value);
	}

	@Test
	public void testReadValueNumberNegative() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("-2147483648}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		Assert.assertEquals(12, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("-2147483648", value);
	}

	@Test
	public void testReadValueNumberOneDigit() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("1}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		Assert.assertEquals(2, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("1", value);
	}

	@Test
	public void testReadValueNumberTwoDigits() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("12}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		Assert.assertEquals(3, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("12", value);
	}

	@Test
	public void testReadValueObjectEmpty() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("{}}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		String string = (String)value;

		Assert.assertEquals(3, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("{}", string);
	}

	@Test
	public void testReadValueObjectOneField() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("{\"a1\":\"w1\"}}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		String string = (String)value;

		Assert.assertEquals(12, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("{\"a1\":\"w1\"}", string);
	}

	@Test
	public void testReadValueObjectSelfNested() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("{\"f1\":\"v1\",\"f11\":{\"f1\":\"vv1\"}}}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		String string = (String)value;

		Assert.assertEquals(31, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("{\"f1\":\"v1\",\"f11\":{\"f1\":\"vv1\"}}", string);
	}

	@Test
	public void testReadValueString() {
		JSONParser jsonParser = new MyDTOJSONParser();

		jsonParser.initFields("\"ab\"}");
		jsonParser.readNextChar();

		Object value = jsonParser.readValue();

		Assert.assertEquals(5, jsonParser.index);
		Assert.assertEquals('}', jsonParser.lastChar);

		Assert.assertEquals("ab", value);
	}

}