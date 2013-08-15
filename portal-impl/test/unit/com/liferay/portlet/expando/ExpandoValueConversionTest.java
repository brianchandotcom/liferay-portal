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
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ExpandoValueConversionTest extends TestCase {

	@Test
	public void test_BOOLEAN_1() {
		try {
			boolean converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN, "true");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_BOOLEAN_2() {
		try {
			boolean converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN, "false");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_BOOLEAN_3() {
		try {
			boolean converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_1() {
		try {
			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "true");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_2() {
		try {
			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "false,true");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_3() {
		try {
			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "other,false");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_4() {
		try {
			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "[false,true]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_5() {
		try {
			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "[other,false]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_6() {
		try {
			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "[\"false\",true]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_7() {
		try {
			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, "[\"other\",false]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_8() {
		try {
			Collection<String> booleans = new ArrayList<String>();

			booleans.add("true");
			booleans.add("false");

			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, booleans);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_BOOLEAN_ARRAY_9() {
		try {
			Collection<String> booleans = new ArrayList<String>();

			booleans.add("true");
			booleans.add("other");

			boolean[] converted = _converter.convertType(
				ExpandoColumnConstants.BOOLEAN_ARRAY, booleans);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DATE_1() {
		try {
			Date now = new Date();

			Date converted = _converter.convertType(
				ExpandoColumnConstants.DATE, now.getTime());
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DATE_2() {
		try {
			Date converted = _converter.convertType(
				ExpandoColumnConstants.DATE, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DATE_ARRAY_1() {
		try {
			String[] dates = new String[] {
				String.valueOf(new Date().getTime()),
				String.valueOf(System.currentTimeMillis())
			};

			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY, dates);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DATE_ARRAY_2() {
		try {
			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY, "1376510136750");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DATE_ARRAY_3() {
		try {
			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY,
				"1376510136750, 1376510136751");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DATE_ARRAY_4() {
		try {
			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY,
				"[1376510136750, 1376510136751]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DATE_ARRAY_5() {
		try {
			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY,
				"1376510136750, other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DATE_ARRAY_6() {
		try {
			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY,
				"[1376510136750, other]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DATE_ARRAY_7() {
		try {
			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DATE_ARRAY_8() {
		try {
			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY, 1376510136750L);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DATE_ARRAY_9() {
		try {
			long[] dates = new long[] {1376510136750L, 1376510136560L};

			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY, dates);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DATE_ARRAY_10() {
		try {
			int[] dates = new int[] {1376510136, 1376510136};

			Date[] converted = _converter.convertType(
				ExpandoColumnConstants.DATE_ARRAY, dates);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DOUBLE_1() {
		try {
			double converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE, "-456.23");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DOUBLE_2() {
		try {
			double converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE, "345.4");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DOUBLE_3() {
		try {
			double converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_1() {
		try {
			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "13.4");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_2() {
		try {
			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "345.67,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_3() {
		try {
			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "other,23.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_4() {
		try {
			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "[56.6567,0.0000345]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_5() {
		try {
			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "[0.34,other]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_6() {
		try {
			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "[\"34.67\",12.45]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_7() {
		try {
			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, "[\"other\",34.65]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_8() {
		try {
			Collection<String> doubles = new ArrayList<String>();

			doubles.add(String.valueOf(Double.MAX_VALUE));
			doubles.add(String.valueOf(Integer.MAX_VALUE));

			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, doubles);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_DOUBLE_ARRAY_9() {
		try {
			Collection<String> booleans = new ArrayList<String>();

			booleans.add("12.5");
			booleans.add("other");

			double[] converted = _converter.convertType(
				ExpandoColumnConstants.DOUBLE_ARRAY, booleans);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_FLOAT_1() {
		try {
			float converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT, "-456.23");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_FLOAT_2() {
		try {
			float converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT, "345.4");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_FLOAT_3() {
		try {
			float converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_FLOAT_ARRAY_1() {
		try {
			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "13.4");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_FLOAT_ARRAY_2() {
		try {
			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "345.67,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_FLOAT_ARRAY_3() {
		try {
			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "other,23.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_FLOAT_ARRAY_4() {
		try {
			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "[56.6567,0.0000345]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_FLOAT_ARRAY_5() {
		try {
			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "[0.34,other]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_FLOAT_ARRAY_6() {
		try {
			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "[\"34.67\",12.45]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_FLOAT_ARRAY_7() {
		try {
			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, "[\"other\",34.65]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_FLOAT_ARRAY_8() {
		try {
			Collection<String> floats = new ArrayList<String>();

			floats.add(String.valueOf(Float.MAX_VALUE));
			floats.add(String.valueOf(Integer.MAX_VALUE));

			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, floats);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_FLOAT_ARRAY_9() {
		try {
			Collection<String> floats = new ArrayList<String>();

			floats.add(String.valueOf(Double.MAX_VALUE)); // Infinity
			floats.add(String.valueOf(Integer.MAX_VALUE));

			float[] converted = _converter.convertType(
				ExpandoColumnConstants.FLOAT_ARRAY, floats);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_INTEGER_1() {
		try {
			int converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER, "456");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_INTEGER_2() {
		try {
			int converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER, "-345");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_INTEGER_3() {
		try {
			int converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER, "13.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_INTEGER_ARRAY_1() {
		try {
			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "13");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_INTEGER_ARRAY_2() {
		try {
			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "345,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_INTEGER_ARRAY_3() {
		try {
			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "675,23.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_INTEGER_ARRAY_4() {
		try {
			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "[56,1]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_INTEGER_ARRAY_5() {
		try {
			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "[0,56.23]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_INTEGER_ARRAY_6() {
		try {
			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "[\"34\",12]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_INTEGER_ARRAY_7() {
		try {
			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, "[\"34.5\",34]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_INTEGER_ARRAY_8() {
		try {
			Collection<String> ints = new ArrayList<String>();

			ints.add("-345");
			ints.add(String.valueOf(Integer.MAX_VALUE));

			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, ints);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_INTEGER_ARRAY_9() {
		try {
			Collection<String> ints = new ArrayList<String>();

			ints.add(String.valueOf(Double.MAX_VALUE));
			ints.add(String.valueOf(Integer.MAX_VALUE));

			int[] converted = _converter.convertType(
				ExpandoColumnConstants.INTEGER_ARRAY, ints);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_LONG_1() {
		try {
			long converted = _converter.convertType(
				ExpandoColumnConstants.LONG, "456");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_LONG_2() {
		try {
			long converted = _converter.convertType(
				ExpandoColumnConstants.LONG, "-345");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_LONG_3() {
		try {
			long converted = _converter.convertType(
				ExpandoColumnConstants.LONG, "13.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_LONG_ARRAY_1() {
		try {
			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "13");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_LONG_ARRAY_2() {
		try {
			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "345,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_LONG_ARRAY_3() {
		try {
			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "675,23.4");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_LONG_ARRAY_4() {
		try {
			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "[56,1]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_LONG_ARRAY_5() {
		try {
			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "[0,56.23]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_LONG_ARRAY_6() {
		try {
			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "[\"34\",12]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_LONG_ARRAY_7() {
		try {
			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, "[\"34.5\",34]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_LONG_ARRAY_8() {
		try {
			Collection<String> longs = new ArrayList<String>();

			longs.add("-345");
			longs.add(String.valueOf(Long.MAX_VALUE));

			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, longs);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_LONG_ARRAY_9() {
		try {
			Collection<String> longs = new ArrayList<String>();

			longs.add(String.valueOf(Double.MAX_VALUE));
			longs.add(String.valueOf(Long.MAX_VALUE));

			long[] converted = _converter.convertType(
				ExpandoColumnConstants.LONG_ARRAY, longs);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_NUMBER_1() {
		try {
			Number converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER, "456");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_NUMBER_2() {
		try {
			Number converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER, "-345");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_NUMBER_3() {
		try {
			Number converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER, "other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_NUMBER_ARRAY_1() {
		try {
			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "13");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_NUMBER_ARRAY_2() {
		try {
			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "345,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_NUMBER_ARRAY_3() {
		try {
			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "675.345,other");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_NUMBER_ARRAY_4() {
		try {
			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "[56,1]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_NUMBER_ARRAY_5() {
		try {
			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "[0,other]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_NUMBER_ARRAY_6() {
		try {
			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "[\"34\",12]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_NUMBER_ARRAY_7() {
		try {
			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, "[\"other\",34]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_NUMBER_ARRAY_8() {
		try {
			Collection<String> numbers = new ArrayList<String>();

			numbers.add("-345");
			numbers.add(String.valueOf(Double.MAX_VALUE));

			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, numbers);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_NUMBER_ARRAY_9() {
		try {
			Collection<String> numbers = new ArrayList<String>();

			numbers.add(String.valueOf(Double.MAX_VALUE));
			numbers.add(String.valueOf(Long.MAX_VALUE));

			Number[] converted = _converter.convertType(
				ExpandoColumnConstants.NUMBER_ARRAY, numbers);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_SHORT_1() {
		try {
			short converted = _converter.convertType(
				ExpandoColumnConstants.SHORT, "456");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_SHORT_2() {
		try {
			short converted = _converter.convertType(
				ExpandoColumnConstants.SHORT, "-345");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_SHORT_3() {
		try {
			short converted = _converter.convertType(
				ExpandoColumnConstants.SHORT, "12344535");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_SHORT_ARRAY_1() {
		try {
			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "13");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_SHORT_ARRAY_2() {
		try {
			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "345,56");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_SHORT_ARRAY_3() {
		try {
			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "675,12344535");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_SHORT_ARRAY_4() {
		try {
			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "[56,1]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_SHORT_ARRAY_5() {
		try {
			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "[0,12344535]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_SHORT_ARRAY_6() {
		try {
			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "[\"34\",12]");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_SHORT_ARRAY_7() {
		try {
			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, "[\"12344535\",34]");

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void test_SHORT_ARRAY_8() {
		try {
			Collection<String> shorts = new ArrayList<String>();

			shorts.add("-345");
			shorts.add(String.valueOf(Short.MAX_VALUE));

			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, shorts);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void test_SHORT_ARRAY_9() {
		try {
			Collection<String> shorts = new ArrayList<String>();

			shorts.add(String.valueOf(Double.MAX_VALUE));
			shorts.add(String.valueOf(Short.MAX_VALUE));

			short[] converted = _converter.convertType(
				ExpandoColumnConstants.SHORT_ARRAY, shorts);

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