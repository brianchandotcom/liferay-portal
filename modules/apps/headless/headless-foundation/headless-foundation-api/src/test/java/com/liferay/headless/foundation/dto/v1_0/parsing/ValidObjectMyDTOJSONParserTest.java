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

import com.liferay.headless.foundation.dto.v1_0.AnotherDTO;
import com.liferay.headless.foundation.dto.v1_0.MyDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class ValidObjectMyDTOJSONParserTest extends BaseJSONParserTestCase {

	@Test
	public void testEmpty() throws Exception {
		String fileContent = getFileContent("object/empty.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertNull(myDTO.getF1());
		Assert.assertNull(myDTO.getF2());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testEmptyWhitespace() throws Exception {
		String fileContent = getFileContent("object/empty-whitespace.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertNull(myDTO.getF1());
		Assert.assertNull(myDTO.getF2());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldArrayDate() throws Exception {
		String fileContent = getFileContent("object/field-array-date.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		Assert.assertArrayEquals(
			myDTO.getF14().toString(),
			new Date[] {
				dateFormat.parse("2018-12-31T23:23:59Z"),
				dateFormat.parse("2018-12-30T23:23:59Z"),
				dateFormat.parse("2018-12-29T23:23:59Z")
			},
			myDTO.getF14());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldArrayObject() throws Exception {
		String fileContent = getFileContent("object/field-array-object.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		AnotherDTO[] anotherDTOS = myDTO.getF10();

		Assert.assertEquals("w1", anotherDTOS[0].getA1());
		Assert.assertEquals("w2", anotherDTOS[1].getA1());
		Assert.assertEquals("w3", anotherDTOS[2].getA1());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldArrayObjectSelfNested() throws Exception {
		String fileContent = getFileContent(
			"object/field-array-object-self-nested.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		MyDTO[] nestedMyDTOS = myDTO.getF12();

		Assert.assertNotNull(nestedMyDTOS);

		Assert.assertEquals("v1", myDTO.getF1());
		Assert.assertEquals("vv1", nestedMyDTOS[0].getF1());
		Assert.assertEquals("vv2", nestedMyDTOS[1].getF1());
		Assert.assertEquals("vv3", nestedMyDTOS[2].getF1());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldArrayString() throws Exception {
		String fileContent = getFileContent("object/field-array-string.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertArrayEquals(
			Arrays.toString(myDTO.getF5()), new String[] {"v1", "v2", "v3"},
			myDTO.getF5());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldArrayStringEmpty() throws Exception {
		String fileContent = getFileContent(
			"object/field-array-string-empty.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertArrayEquals(
			Arrays.toString(myDTO.getF5()), new String[0], myDTO.getF5());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldArrayStringNull() throws Exception {
		String fileContent = getFileContent(
			"object/field-array-string-null.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertNull(myDTO.getF5());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldArrayStringOneElement() throws Exception {
		String fileContent = getFileContent(
			"object/field-array-string-one-element.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertArrayEquals(
			Arrays.toString(myDTO.getF5()), new String[] {"v1"}, myDTO.getF5());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldBooleanFalse() throws Exception {
		String fileContent = getFileContent("object/field-boolean-false.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertFalse(myDTO.getF2());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldBooleanNull() throws Exception {
		String fileContent = getFileContent("object/field-boolean-null.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertNull(myDTO.getF2());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldBooleanTrue() throws Exception {
		String fileContent = getFileContent("object/field-boolean-true.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertTrue(myDTO.getF2());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldDate() throws Exception {
		String fileContent = getFileContent("object/field-date.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		Assert.assertEquals(
			dateFormat.parse("2018-12-31T23:23:59Z"), myDTO.getF13());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldDateNull() throws Exception {
		String fileContent = getFileContent("object/field-date-null.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		Assert.assertNull(myDTO.getF13());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldIntegerNegative() throws Exception {
		String fileContent = getFileContent(
			"object/field-integer-negative.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertEquals((Integer)(-2147483648), myDTO.getF4());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldIntegerNull() throws Exception {
		String fileContent = getFileContent("object/field-integer-null.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertNull(myDTO.getF4());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldIntegerPositive() throws Exception {
		String fileContent = getFileContent(
			"object/field-integer-positive.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertEquals((Integer)2147483647, myDTO.getF4());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldIntegerZero() throws Exception {
		String fileContent = getFileContent("object/field-integer-zero.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertEquals((Integer)0, myDTO.getF4());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldLongNegative() throws Exception {
		String fileContent = getFileContent("object/field-long-negative.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertEquals((Long)(-9223372036854775808L), myDTO.getF3());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldLongNull() throws Exception {
		String fileContent = getFileContent("object/field-long-null.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertNull(myDTO.getF3());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldLongPositive() throws Exception {
		String fileContent = getFileContent("object/field-long-positive.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertEquals((Long)9223372036854775807L, myDTO.getF3());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldLongZero() throws Exception {
		String fileContent = getFileContent("object/field-long-zero.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertEquals((Long)0L, myDTO.getF3());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldObject() throws Exception {
		String fileContent = getFileContent("object/field-object.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		AnotherDTO anotherDTO = myDTO.getF9();

		Assert.assertNotNull(anotherDTO);

		Assert.assertEquals("w1", anotherDTO.getA1());
		Assert.assertEquals(true, anotherDTO.getA2());
		Assert.assertEquals((Long)9223372036854775807L, anotherDTO.getA3());
		Assert.assertEquals((Integer)(-2147483648), anotherDTO.getA4());
		Assert.assertArrayEquals(
			new String[] {"w1", "w2", "w3"}, anotherDTO.getA5());
		Assert.assertArrayEquals(
			new Boolean[] {true, false, null}, anotherDTO.getA6());
		Assert.assertArrayEquals(
			new Long[] {-9223372036854775808L, 0L, 9223372036854775807L},
			anotherDTO.getA7());
		Assert.assertArrayEquals(
			new Integer[] {-2147483648, 0, 2147483647}, anotherDTO.getA8());

		MyDTO a9MyDTO = anotherDTO.getA9();

		Assert.assertEquals("u1", a9MyDTO.getF1());

		MyDTO[] nestedMyDTO = anotherDTO.getA10();

		Assert.assertNotNull(nestedMyDTO);

		Assert.assertEquals("t1", nestedMyDTO[0].getF1());
		Assert.assertEquals("t2", nestedMyDTO[1].getF1());
		Assert.assertEquals("t3", nestedMyDTO[2].getF1());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldObjectArrayString() throws Exception {
		String fileContent = getFileContent(
			"object/field-object-array-string.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		AnotherDTO anotherDTO = myDTO.getF9();

		Assert.assertNotNull(anotherDTO);

		Assert.assertArrayEquals(
			new String[] {"w1", "w2", "w3"}, anotherDTO.getA5());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldObjectEmpty() throws Exception {
		String fileContent = getFileContent("object/field-object-empty.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		Assert.assertNotNull(myDTO.getF9());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldObjectEmptyFieldString() throws Exception {
		String fileContent = getFileContent(
			"object/field-object-empty-field-string.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		Assert.assertNotNull(myDTO.getF9());
		Assert.assertEquals("v1", myDTO.getF1());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldObjectNull() throws Exception {
		String fileContent = getFileContent("object/field-object-null.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		Assert.assertNull(myDTO.getF9());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldObjectOneField() throws Exception {
		AnotherDTO expectedAnotherDTO = new AnotherDTO();

		expectedAnotherDTO.setA1("w1");

		String fileContent = getFileContent(
			"object/field-object-one-field.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		AnotherDTO anotherDTO = myDTO.getF9();

		Assert.assertNotNull(anotherDTO);

		Assert.assertEquals(expectedAnotherDTO.getA1(), anotherDTO.getA1());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldObjectSelfNested() throws Exception {
		String fileContent = getFileContent(
			"object/field-object-self-nested.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);

		MyDTO nestedMyDTO = myDTO.getF11();

		Assert.assertNotNull(nestedMyDTO);

		Assert.assertEquals("v1", myDTO.getF1());
		Assert.assertEquals("vv1", nestedMyDTO.getF1());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldString() throws Exception {
		String fileContent = getFileContent("object/field-string.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertEquals("v1", myDTO.getF1());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

	@Test
	public void testFieldStringNull() throws Exception {
		String fileContent = getFileContent("object/field-string-null.json");

		MyDTO myDTO = MyDTO.toMyDto(fileContent);

		Assert.assertNotNull(myDTO);
		Assert.assertNull(myDTO.getF1());

		assertJacksonSerializationMatches(myDTO, fileContent);
	}

}