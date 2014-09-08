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

package com.liferay.portlet.expando;

import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.service.impl.ExpandoValueLocalServiceImpl;

import java.util.ArrayList;
import java.util.Collection;

import jodd.typeconverter.TypeConversionException;

import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ExpandoValueConversionTest {

	@Test
	public void testBoolean1() {
		_converter.convertType(ExpandoColumnConstants.BOOLEAN, "true");
	}

	@Test
	public void testBoolean2() {
		_converter.convertType(ExpandoColumnConstants.BOOLEAN, "false");
	}

	@Test(expected = TypeConversionException.class)
	public void testBoolean3() {
		_converter.convertType(ExpandoColumnConstants.BOOLEAN, "other");
	}

	@Test
	public void testBooleanArray1() {
		_converter.convertType(ExpandoColumnConstants.BOOLEAN_ARRAY, "true");
	}

	@Test
	public void testBooleanArray2() {
		_converter.convertType(
			ExpandoColumnConstants.BOOLEAN_ARRAY, "false,true");
	}

	@Test(expected = TypeConversionException.class)
	public void testBooleanArray3() {
		_converter.convertType(
			ExpandoColumnConstants.BOOLEAN_ARRAY, "other,false");
	}

	@Test
	public void testBooleanArray4() {
		_converter.convertType(
			ExpandoColumnConstants.BOOLEAN_ARRAY, "[false,true]");
	}

	@Test(expected = TypeConversionException.class)
	public void testBooleanArray5() {
		_converter.convertType(
			ExpandoColumnConstants.BOOLEAN_ARRAY, "[other,false]");
	}

	@Test
	public void testBooleanArray6() {
		_converter.convertType(
			ExpandoColumnConstants.BOOLEAN_ARRAY, "[\"false\",true]");
	}

	@Test(expected = TypeConversionException.class)
	public void testBooleanArray7() {
		_converter.convertType(
			ExpandoColumnConstants.BOOLEAN_ARRAY, "[\"other\",false]");
	}

	@Test
	public void testBooleanArray8() {
		Collection<String> booleans = new ArrayList<String>();

		booleans.add("true");
		booleans.add("false");

		_converter.convertType(
			ExpandoColumnConstants.BOOLEAN_ARRAY, booleans);
	}

	@Test(expected = TypeConversionException.class)
	public void testBooleanArray9() {
		Collection<String> booleans = new ArrayList<String>();

		booleans.add("true");
		booleans.add("other");

		_converter.convertType(
			ExpandoColumnConstants.BOOLEAN_ARRAY, booleans);
	}

	@Test
	public void testDate1() {
		_converter.convertType(
			ExpandoColumnConstants.DATE, System.currentTimeMillis());
	}

	@Test(expected = NumberFormatException.class)
	public void testDate2() {
		_converter.convertType(ExpandoColumnConstants.DATE, "other");
	}

	@Test
	public void testDateArray1() {
		String[] dates = new String[] {
			String.valueOf(System.currentTimeMillis()),
			String.valueOf(System.currentTimeMillis())
		};

		_converter.convertType(ExpandoColumnConstants.DATE_ARRAY, dates);
	}

	@Test
	public void testDateArray2() {
		_converter.convertType(
			ExpandoColumnConstants.DATE_ARRAY, "1376510136750");
	}

	@Test
	public void testDateArray3() {
		_converter.convertType(
			ExpandoColumnConstants.DATE_ARRAY,
			"1376510136750, 1376510136751");
	}

	@Test
	public void testDateArray4() {
		_converter.convertType(
			ExpandoColumnConstants.DATE_ARRAY,
			"[1376510136750, 1376510136751]");
	}

	@Test(expected = NumberFormatException.class)
	public void testDateArray5() {
		_converter.convertType(
			ExpandoColumnConstants.DATE_ARRAY,
			"1376510136750, other");
	}

	@Test(expected = NumberFormatException.class)
	public void testDateArray6() {
		_converter.convertType(
			ExpandoColumnConstants.DATE_ARRAY,
			"[1376510136750, other]");
	}

	@Test(expected = NumberFormatException.class)
	public void testDateArray7() {
		_converter.convertType(ExpandoColumnConstants.DATE_ARRAY, "other");
	}

	@Test
	public void testDateArray8() {
		_converter.convertType(
			ExpandoColumnConstants.DATE_ARRAY, 1376510136750L);
	}

	@Test
	public void testDateArray9() {
		long[] dates = new long[] {1376510136750L, 1376510136560L};

		_converter.convertType(ExpandoColumnConstants.DATE_ARRAY, dates);
	}

	@Test(expected = ClassCastException.class)
	public void testDateArray10() {
		int[] dates = new int[] {1376510136, 1376510136};

		_converter.convertType(ExpandoColumnConstants.DATE_ARRAY, dates);
	}

	@Test
	public void testDouble1() {
		_converter.convertType(ExpandoColumnConstants.DOUBLE, "-456.23");
	}

	@Test
	public void testDouble2() {
		_converter.convertType(ExpandoColumnConstants.DOUBLE, "345.4");
	}

	@Test(expected = TypeConversionException.class)
	public void testDouble3() {
		_converter.convertType(ExpandoColumnConstants.DOUBLE, "other");
	}

	@Test
	public void testDoubleArray1() {
		_converter.convertType(ExpandoColumnConstants.DOUBLE_ARRAY, "13.4");
	}

	@Test
	public void testDoubleArray2() {
		_converter.convertType(
			ExpandoColumnConstants.DOUBLE_ARRAY, "345.67,56");
	}

	@Test(expected = TypeConversionException.class)
	public void testDoubleArray3() {
		_converter.convertType(
			ExpandoColumnConstants.DOUBLE_ARRAY, "other,23.4");
	}

	@Test
	public void testDoubleArray4() {
		_converter.convertType(
			ExpandoColumnConstants.DOUBLE_ARRAY, "[56.6567,0.0000345]");
	}

	@Test(expected = TypeConversionException.class)
	public void testDoubleArray5() {
		_converter.convertType(
			ExpandoColumnConstants.DOUBLE_ARRAY, "[0.34,other]");
	}

	@Test
	public void testDoubleArray6() {
		_converter.convertType(
			ExpandoColumnConstants.DOUBLE_ARRAY, "[\"34.67\",12.45]");
	}

	@Test(expected = TypeConversionException.class)
	public void testDoubleArray7() {
		_converter.convertType(
			ExpandoColumnConstants.DOUBLE_ARRAY, "[\"other\",34.65]");
	}

	@Test
	public void testDoubleArray8() {
		Collection<String> doubles = new ArrayList<String>();

		doubles.add(String.valueOf(Double.MAX_VALUE));
		doubles.add(String.valueOf(Integer.MAX_VALUE));

		_converter.convertType(
			ExpandoColumnConstants.DOUBLE_ARRAY, doubles);
	}

	@Test(expected = TypeConversionException.class)
	public void testDoubleArray9() {
		Collection<String> booleans = new ArrayList<String>();

		booleans.add("12.5");
		booleans.add("other");

		_converter.convertType(
			ExpandoColumnConstants.DOUBLE_ARRAY, booleans);
	}

	@Test
	public void testFloat1() {
		_converter.convertType(ExpandoColumnConstants.FLOAT, "-456.23");
	}

	@Test
	public void testFloat2() {
		_converter.convertType(ExpandoColumnConstants.FLOAT, "345.4");
	}

	@Test(expected = TypeConversionException.class)
	public void testFloat3() {
		_converter.convertType(ExpandoColumnConstants.FLOAT, "other");
	}

	@Test
	public void testFloatArray1() {
		_converter.convertType(ExpandoColumnConstants.FLOAT_ARRAY, "13.4");
	}

	@Test
	public void testFloatArray2() {
		_converter.convertType(
			ExpandoColumnConstants.FLOAT_ARRAY, "345.67,56");
	}

	@Test(expected = TypeConversionException.class)
	public void testFloatArray3() {
		_converter.convertType(
			ExpandoColumnConstants.FLOAT_ARRAY, "other,23.4");
	}

	@Test
	public void testFloatArray4() {
		_converter.convertType(
			ExpandoColumnConstants.FLOAT_ARRAY, "[56.6567,0.0000345]");
	}

	@Test(expected = TypeConversionException.class)
	public void testFloatArray5() {
		_converter.convertType(
			ExpandoColumnConstants.FLOAT_ARRAY, "[0.34,other]");
	}

	@Test
	public void testFloatArray6() {
		_converter.convertType(
			ExpandoColumnConstants.FLOAT_ARRAY, "[\"34.67\",12.45]");
	}

	@Test(expected = TypeConversionException.class)
	public void testFloatArray7() {
		_converter.convertType(
			ExpandoColumnConstants.FLOAT_ARRAY, "[\"other\",34.65]");
	}

	@Test
	public void testFloatArray8() {
		Collection<String> floats = new ArrayList<String>();

		floats.add(String.valueOf(Float.MAX_VALUE));
		floats.add(String.valueOf(Integer.MAX_VALUE));

		_converter.convertType(ExpandoColumnConstants.FLOAT_ARRAY, floats);
	}

	@Test
	public void testFloatArray9() {
		Collection<String> floats = new ArrayList<String>();

		floats.add(String.valueOf(Double.MAX_VALUE));
		floats.add(String.valueOf(Integer.MAX_VALUE));

		_converter.convertType(ExpandoColumnConstants.FLOAT_ARRAY, floats);
	}

	@Test
	public void testInteger1() {
		_converter.convertType(ExpandoColumnConstants.INTEGER, "456");
	}

	@Test
	public void testInteger2() {
		_converter.convertType(ExpandoColumnConstants.INTEGER, "-345");
	}

	@Test(expected = TypeConversionException.class)
	public void testInteger3() {
		_converter.convertType(ExpandoColumnConstants.INTEGER, "13.4");
	}

	@Test
	public void testIntegerArray1() {
		_converter.convertType(ExpandoColumnConstants.INTEGER_ARRAY, "13");
	}

	@Test
	public void testIntegerArray2() {
		_converter.convertType(
			ExpandoColumnConstants.INTEGER_ARRAY, "345,56");
	}

	@Test(expected = TypeConversionException.class)
	public void testIntegerArray3() {
		_converter.convertType(
			ExpandoColumnConstants.INTEGER_ARRAY, "675,23.4");
	}

	@Test
	public void testIntegerArray4() {
		_converter.convertType(ExpandoColumnConstants.INTEGER_ARRAY, "[56,1]");
	}

	@Test(expected = TypeConversionException.class)
	public void testIntegerArray5() {
		_converter.convertType(
			ExpandoColumnConstants.INTEGER_ARRAY, "[0,56.23]");
	}

	@Test
	public void testIntegerArray6() {
		_converter.convertType(
			ExpandoColumnConstants.INTEGER_ARRAY, "[\"34\",12]");
	}

	@Test(expected = TypeConversionException.class)
	public void testIntegerArray7() {
		_converter.convertType(
			ExpandoColumnConstants.INTEGER_ARRAY, "[\"34.5\",34]");
	}

	@Test
	public void testIntegerArray8() {
		Collection<String> ints = new ArrayList<String>();

		ints.add("-345");
		ints.add(String.valueOf(Integer.MAX_VALUE));

		_converter.convertType(ExpandoColumnConstants.INTEGER_ARRAY, ints);
	}

	@Test(expected = TypeConversionException.class)
	public void testIntegerArray9() {
		Collection<String> ints = new ArrayList<String>();

		ints.add(String.valueOf(Double.MAX_VALUE));
		ints.add(String.valueOf(Integer.MAX_VALUE));

		_converter.convertType(ExpandoColumnConstants.INTEGER_ARRAY, ints);
	}

	@Test
	public void testLong1() {
		_converter.convertType(ExpandoColumnConstants.LONG, "456");
	}

	@Test
	public void testLong2() {
		_converter.convertType(ExpandoColumnConstants.LONG, "-345");
	}

	@Test(expected = TypeConversionException.class)
	public void testLong3() {
		_converter.convertType(ExpandoColumnConstants.LONG, "13.4");
	}

	@Test
	public void testLongArray1() {
		_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, "13");
	}

	@Test
	public void testLongArray2() {
		_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, "345,56");
	}

	@Test(expected = TypeConversionException.class)
	public void testLongArray3() {
		_converter.convertType(
			ExpandoColumnConstants.LONG_ARRAY, "675,23.4");
	}

	@Test
	public void testLongArray4() {
		_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, "[56,1]");
	}

	@Test(expected = TypeConversionException.class)
	public void testLongArray5() {
		_converter.convertType(
			ExpandoColumnConstants.LONG_ARRAY, "[0,56.23]");
	}

	@Test
	public void testLongArray6() {
		_converter.convertType(
			ExpandoColumnConstants.LONG_ARRAY, "[\"34\",12]");
	}

	@Test(expected = TypeConversionException.class)
	public void testLongArray7() {
		_converter.convertType(
			ExpandoColumnConstants.LONG_ARRAY, "[\"34.5\",34]");
	}

	@Test
	public void testLongArray8() {
		Collection<String> longs = new ArrayList<String>();

		longs.add("-345");
		longs.add(String.valueOf(Long.MAX_VALUE));

		_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, longs);
	}

	@Test(expected = TypeConversionException.class)
	public void testLongArray9() {
		Collection<String> longs = new ArrayList<String>();

		longs.add(String.valueOf(Double.MAX_VALUE));
		longs.add(String.valueOf(Long.MAX_VALUE));

		_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, longs);
	}

	@Test
	public void testNumber1() {
		_converter.convertType(ExpandoColumnConstants.NUMBER, "456");
	}

	@Test
	public void testNumber2() {
		_converter.convertType(ExpandoColumnConstants.NUMBER, "-345");
	}

	@Test(expected = TypeConversionException.class)
	public void testNumber3() {
		_converter.convertType(ExpandoColumnConstants.NUMBER, "other");
	}

	@Test
	public void testNumberArray1() {
		_converter.convertType(ExpandoColumnConstants.NUMBER_ARRAY, "13");
	}

	@Test
	public void testNumberArray2() {
		_converter.convertType(
			ExpandoColumnConstants.NUMBER_ARRAY, "345,56");
	}

	@Test(expected = TypeConversionException.class)
	public void testNumberArray3() {
		_converter.convertType(
			ExpandoColumnConstants.NUMBER_ARRAY, "675.345,other");
	}

	@Test
	public void testNumberArray4() {
		_converter.convertType(ExpandoColumnConstants.NUMBER_ARRAY, "[56,1]");
	}

	@Test(expected = TypeConversionException.class)
	public void testNumberArray5() {
		_converter.convertType(
			ExpandoColumnConstants.NUMBER_ARRAY, "[0,other]");
	}

	@Test
	public void testNumberArray6() {
		_converter.convertType(
			ExpandoColumnConstants.NUMBER_ARRAY, "[\"34\",12]");
	}

	@Test(expected = TypeConversionException.class)
	public void testNumberArray7() {
		_converter.convertType(
			ExpandoColumnConstants.NUMBER_ARRAY, "[\"other\",34]");
	}

	@Test
	public void testNumberArray8() {
		Collection<String> numbers = new ArrayList<String>();

		numbers.add("-345");
		numbers.add(String.valueOf(Double.MAX_VALUE));

		_converter.convertType(
			ExpandoColumnConstants.NUMBER_ARRAY, numbers);
	}

	@Test
	public void testNumberArray9() {
		Collection<String> numbers = new ArrayList<String>();

		numbers.add(String.valueOf(Double.MAX_VALUE));
		numbers.add(String.valueOf(Long.MAX_VALUE));

		_converter.convertType(
			ExpandoColumnConstants.NUMBER_ARRAY, numbers);
	}

	@Test
	public void testShort1() {
		_converter.convertType(ExpandoColumnConstants.SHORT, "456");
	}

	@Test
	public void testShort2() {
		_converter.convertType(ExpandoColumnConstants.SHORT, "-345");
	}

	@Test(expected = TypeConversionException.class)
	public void testShort3() {
		_converter.convertType(ExpandoColumnConstants.SHORT, "12344535");
	}

	@Test
	public void testShortArray1() {
		_converter.convertType(ExpandoColumnConstants.SHORT_ARRAY, "13");
	}

	@Test
	public void testShortArray2() {
		_converter.convertType(ExpandoColumnConstants.SHORT_ARRAY, "345,56");
	}

	@Test(expected = TypeConversionException.class)
	public void testShortArray3() {
		_converter.convertType(
			ExpandoColumnConstants.SHORT_ARRAY, "675,12344535");
	}

	@Test
	public void testShortArray4() {
		_converter.convertType(ExpandoColumnConstants.SHORT_ARRAY, "[56,1]");
	}

	@Test(expected = TypeConversionException.class)
	public void testShortArray5() {
		_converter.convertType(
			ExpandoColumnConstants.SHORT_ARRAY, "[0,12344535]");
	}

	@Test
	public void testShortArray6() {
		_converter.convertType(
			ExpandoColumnConstants.SHORT_ARRAY, "[\"34\",12]");
	}

	@Test(expected = TypeConversionException.class)
	public void testShortArray7() {
		_converter.convertType(
			ExpandoColumnConstants.SHORT_ARRAY, "[\"12344535\",34]");
	}

	@Test
	public void testShortArray8() {
		Collection<String> shorts = new ArrayList<String>();

		shorts.add("-345");
		shorts.add(String.valueOf(Short.MAX_VALUE));

		_converter.convertType(ExpandoColumnConstants.SHORT_ARRAY, shorts);
	}

	@Test(expected = TypeConversionException.class)
	public void testShortArray9() {
		Collection<String> shorts = new ArrayList<String>();

		shorts.add(String.valueOf(Double.MAX_VALUE));
		shorts.add(String.valueOf(Short.MAX_VALUE));

		_converter.convertType(ExpandoColumnConstants.SHORT_ARRAY, shorts);
	}

	private Converter _converter = new Converter();

	private class Converter extends ExpandoValueLocalServiceImpl {

		@Override
		public <T> T convertType(int type, Object data) {
			return super.convertType(type, data);
		}

	}

}