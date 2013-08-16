/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ExpandoValueConversionTest extends TestCase {

	@Test
	public void testBoolean1() {
		try {
			_converter.convertType(ExpandoColumnConstants.BOOLEAN, "true");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testBoolean2() {
		try {
			_converter.convertType(ExpandoColumnConstants.BOOLEAN, "false");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testBoolean3() {
		try {
			_converter.convertType(ExpandoColumnConstants.BOOLEAN, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testBooleanArray1() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "true");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testBooleanArray2() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "false,true");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testBooleanArray3() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "other,false");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testBooleanArray4() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "[false,true]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testBooleanArray5() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "[other,false]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testBooleanArray6() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "[\"false\",true]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testBooleanArray7() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "[\"other\",false]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testBooleanArray8() {
		try {
			Collection<String> booleans = new ArrayList<String>();

			booleans.add("true");
			booleans.add("false");

			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, booleans);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testBooleanArray9() {
		try {
			Collection<String> booleans = new ArrayList<String>();

			booleans.add("true");
			booleans.add("other");

			_converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, booleans);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDate1() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DATE, System.currentTimeMillis());
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDate2() {
		try {
			_converter.convertType(ExpandoColumnConstants.DATE, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDateArray1() {
		try {
			String[] dates = new String[] {
				String.valueOf(System.currentTimeMillis()),
				String.valueOf(System.currentTimeMillis())
			};

			_converter.convertType(ExpandoColumnConstants.DATE_ARRAY, dates);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDateArray2() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY, "1376510136750");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDateArray3() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY,
				"1376510136750, 1376510136751");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDateArray4() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY,
				"[1376510136750, 1376510136751]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDateArray5() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY,
				"1376510136750, other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDateArray6() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY,
				"[1376510136750, other]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDateArray7() {
		try {
			_converter.convertType(ExpandoColumnConstants.DATE_ARRAY, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDateArray8() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY, 1376510136750L);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDateArray9() {
		try {
			long[] dates = new long[] {1376510136750L, 1376510136560L};

			_converter.convertType(ExpandoColumnConstants.DATE_ARRAY, dates);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDateArray10() {
		try {
			int[] dates = new int[] {1376510136, 1376510136};

			_converter.convertType(ExpandoColumnConstants.DATE_ARRAY, dates);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDouble1() {
		try {
			_converter.convertType(ExpandoColumnConstants.DOUBLE, "-456.23");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDouble2() {
		try {
			_converter.convertType(ExpandoColumnConstants.DOUBLE, "345.4");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDouble3() {
		try {
			_converter.convertType(ExpandoColumnConstants.DOUBLE, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDoubleArray1() {
		try {
			_converter.convertType(ExpandoColumnConstants.DOUBLE_ARRAY, "13.4");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDoubleArray2() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "345.67,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDoubleArray3() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "other,23.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDoubleArray4() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "[56.6567,0.0000345]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDoubleArray5() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "[0.34,other]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDoubleArray6() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "[\"34.67\",12.45]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDoubleArray7() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "[\"other\",34.65]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testDoubleArray8() {
		try {
			Collection<String> doubles = new ArrayList<String>();

			doubles.add(String.valueOf(Double.MAX_VALUE));
			doubles.add(String.valueOf(Integer.MAX_VALUE));

			_converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, doubles);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDoubleArray9() {
		try {
			Collection<String> booleans = new ArrayList<String>();

			booleans.add("12.5");
			booleans.add("other");

			_converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, booleans);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testFloat1() {
		try {
			_converter.convertType(ExpandoColumnConstants.FLOAT, "-456.23");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testFloat2() {
		try {
			_converter.convertType(ExpandoColumnConstants.FLOAT, "345.4");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testFloat3() {
		try {
			_converter.convertType(ExpandoColumnConstants.FLOAT, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testFloatArray1() {
		try {
			_converter.convertType(ExpandoColumnConstants.FLOAT_ARRAY, "13.4");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testFloatArray2() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "345.67,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testFloatArray3() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "other,23.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testFloatArray4() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "[56.6567,0.0000345]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testFloatArray5() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "[0.34,other]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testFloatArray6() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "[\"34.67\",12.45]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testFloatArray7() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "[\"other\",34.65]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testFloatArray8() {
		try {
			Collection<String> floats = new ArrayList<String>();

			floats.add(String.valueOf(Float.MAX_VALUE));
			floats.add(String.valueOf(Integer.MAX_VALUE));

			_converter.convertType(ExpandoColumnConstants.FLOAT_ARRAY, floats);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testFloatArray9() {
		try {
			Collection<String> floats = new ArrayList<String>();

			floats.add(String.valueOf(Double.MAX_VALUE));
			floats.add(String.valueOf(Integer.MAX_VALUE));

			_converter.convertType(ExpandoColumnConstants.FLOAT_ARRAY, floats);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testInteger1() {
		try {
			_converter.convertType(ExpandoColumnConstants.INTEGER, "456");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testInteger2() {
		try {
			_converter.convertType(ExpandoColumnConstants.INTEGER, "-345");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testInteger3() {
		try {
			_converter.convertType(ExpandoColumnConstants.INTEGER, "13.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testIntegerArray1() {
		try {
			_converter.convertType(ExpandoColumnConstants.INTEGER_ARRAY, "13");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testIntegerArray2() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "345,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testIntegerArray3() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "675,23.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testIntegerArray4() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "[56,1]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testIntegerArray5() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "[0,56.23]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testIntegerArray6() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "[\"34\",12]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testIntegerArray7() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "[\"34.5\",34]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testIntegerArray8() {
		try {
			Collection<String> ints = new ArrayList<String>();

			ints.add("-345");
			ints.add(String.valueOf(Integer.MAX_VALUE));

			_converter.convertType(ExpandoColumnConstants.INTEGER_ARRAY, ints);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testIntegerArray9() {
		try {
			Collection<String> ints = new ArrayList<String>();

			ints.add(String.valueOf(Double.MAX_VALUE));
			ints.add(String.valueOf(Integer.MAX_VALUE));

			_converter.convertType(ExpandoColumnConstants.INTEGER_ARRAY, ints);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLong1() {
		try {
			_converter.convertType(ExpandoColumnConstants.LONG, "456");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testLong2() {
		try {
			_converter.convertType(ExpandoColumnConstants.LONG, "-345");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testLong3() {
		try {
			_converter.convertType(ExpandoColumnConstants.LONG, "13.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLongArray1() {
		try {
			_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, "13");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testLongArray2() {
		try {
			_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, "345,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testLongArray3() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "675,23.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLongArray4() {
		try {
			_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, "[56,1]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testLongArray5() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "[0,56.23]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLongArray6() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "[\"34\",12]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testLongArray7() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "[\"34.5\",34]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLongArray8() {
		try {
			Collection<String> longs = new ArrayList<String>();

			longs.add("-345");
			longs.add(String.valueOf(Long.MAX_VALUE));

			_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, longs);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testLongArray9() {
		try {
			Collection<String> longs = new ArrayList<String>();

			longs.add(String.valueOf(Double.MAX_VALUE));
			longs.add(String.valueOf(Long.MAX_VALUE));

			_converter.convertType(ExpandoColumnConstants.LONG_ARRAY, longs);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testNumber1() {
		try {
			_converter.convertType(ExpandoColumnConstants.NUMBER, "456");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testNumber2() {
		try {
			_converter.convertType(ExpandoColumnConstants.NUMBER, "-345");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testNumber3() {
		try {
			_converter.convertType(ExpandoColumnConstants.NUMBER, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testNumberArray1() {
		try {
			_converter.convertType(ExpandoColumnConstants.NUMBER_ARRAY, "13");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testNumberArray2() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "345,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testNumberArray3() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "675.345,other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testNumberArray4() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "[56,1]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testNumberArray5() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "[0,other]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testNumberArray6() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "[\"34\",12]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testNumberArray7() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "[\"other\",34]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testNumberArray8() {
		try {
			Collection<String> numbers = new ArrayList<String>();

			numbers.add("-345");
			numbers.add(String.valueOf(Double.MAX_VALUE));

			_converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, numbers);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testNumberArray9() {
		try {
			Collection<String> numbers = new ArrayList<String>();

			numbers.add(String.valueOf(Double.MAX_VALUE));
			numbers.add(String.valueOf(Long.MAX_VALUE));

			_converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, numbers);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testShort1() {
		try {
			_converter.convertType(ExpandoColumnConstants.SHORT, "456");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testShort2() {
		try {
			_converter.convertType(ExpandoColumnConstants.SHORT, "-345");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testShort3() {
		try {
			_converter.convertType(ExpandoColumnConstants.SHORT, "12344535");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testShortArray1() {
		try {
			_converter.convertType(ExpandoColumnConstants.SHORT_ARRAY, "13");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testShortArray2() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "345,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testShortArray3() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "675,12344535");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testShortArray4() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "[56,1]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testShortArray5() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "[0,12344535]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testShortArray6() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "[\"34\",12]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testShortArray7() {
		try {
			_converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "[\"12344535\",34]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testShortArray8() {
		try {
			Collection<String> shorts = new ArrayList<String>();

			shorts.add("-345");
			shorts.add(String.valueOf(Short.MAX_VALUE));

			_converter.convertType(ExpandoColumnConstants.SHORT_ARRAY, shorts);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testShortArray9() {
		try {
			Collection<String> shorts = new ArrayList<String>();

			shorts.add(String.valueOf(Double.MAX_VALUE));
			shorts.add(String.valueOf(Short.MAX_VALUE));

			_converter.convertType(ExpandoColumnConstants.SHORT_ARRAY, shorts);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	private Converter _converter = new Converter();

	private class Converter extends ExpandoValueLocalServiceImpl {

		@Override
		public <T> T convertType(int type, Object data) {
			return super.convertType(type, data);
		}

	}

}