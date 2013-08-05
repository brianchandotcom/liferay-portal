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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.AssertUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
public class ArrayUtilTest {

	@Test
	public void testFilterDoubleArray() {
		double[] array = ArrayUtil.filter(
			new double[] {0.1, 0.2, 1.2, 1.3}, _doublePredicateFilter);

		Assert.assertEquals(2, array.length);
		AssertUtils.assertEquals(new double[] {1.2, 1.3}, array);
	}

	@Test
	public void testFilterDoubleEmptyArray() {
		double[] array = ArrayUtil.filter(
			new double[0], _doublePredicateFilter);

		Assert.assertEquals(0, array.length);
		AssertUtils.assertEquals(new double[0], array);
	}

	@Test
	public void testFilterDoubleNullArray() {
		double[] array = null;

		double[] filteredArray = ArrayUtil.filter(
			array, _doublePredicateFilter);

		Assert.assertNull(filteredArray);
	}

	@Test
	public void testFilterIntegerArray() {
		int[] array = ArrayUtil.filter(
			new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, _integerPredicateFilter);

		Assert.assertEquals(5, array.length);
		Assert.assertArrayEquals(new int[] {5, 6, 7, 8, 9}, array);
	}

	@Test
	public void testFilterIntegerEmptyArray() {
		int[] array = ArrayUtil.filter(new int[0], _integerPredicateFilter);

		Assert.assertEquals(0, array.length);
		Assert.assertArrayEquals(new int[] {}, array);
	}

	@Test
	public void testFilterIntegerNullArray() {
		int[] array = null;

		int[] filteredArray = ArrayUtil.filter(array, _integerPredicateFilter);

		Assert.assertNull(filteredArray);
	}

	@Test
	public void testFilterUserArray() {
		User[] array = ArrayUtil.filter(
			new User[] {new User("james", 17), new User("john", 26)},
			_userPredicateFilter);

		Assert.assertEquals(1, array.length);

		Assert.assertEquals("john", array[0].getName());
		Assert.assertEquals(26, array[0].getAge());
	}

	@Test
	public void testFilterUserEmptyArray() {
		User[] array = ArrayUtil.filter(new User[0], _userPredicateFilter);

		Assert.assertEquals(0, array.length);
	}

	@Test
	public void testFilterUserNullArray() {
		User[] array = ArrayUtil.filter(null, _userPredicateFilter);

		Assert.assertNull(array);
	}

	@Test
	public void testReverseIntArray() throws Exception {
		int[] array = new int[] {111, 222, 333};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new int[] {333, 222, 111}, array);
	}

	@Test
	public void testReverseStringArray() throws Exception {
		String[] array = new String[] {"aaa", "bbb", "ccc"};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new String[] {"ccc", "bbb", "aaa"}, array);
	}

	@Test
	public void testToDoubleArray() throws Exception {
		List<Double> list = new ArrayList<Double>();

		list.add(1.0);
		list.add(2.0);

		double[] array = ArrayUtil.toDoubleArray(list);

		Assert.assertEquals(array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Double value = list.get(i);

			AssertUtils.assertEquals(value.doubleValue(), array[i]);
		}
	}

	@Test
	public void testToFloatArray() throws Exception {
		List<Float> list = new ArrayList<Float>();

		list.add(1.0F);
		list.add(2.0F);

		float[] array = ArrayUtil.toFloatArray(list);

		Assert.assertEquals(array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Float value = list.get(i);

			AssertUtils.assertEquals(value.floatValue(), array[i]);
		}
	}

	@Test
	public void testToIntArray() throws Exception {
		List<Integer> list = new ArrayList<Integer>();

		list.add(1);
		list.add(2);

		int[] array = ArrayUtil.toIntArray(list);

		Assert.assertEquals(array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Integer value = list.get(i);

			Assert.assertEquals(value.intValue(), array[i]);
		}
	}

	@Test
	public void testToLongArray() throws Exception {
		List<Long> list = new ArrayList<Long>();

		list.add(1L);
		list.add(2L);

		long[] array = ArrayUtil.toLongArray(list);

		Assert.assertEquals(array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Long value = list.get(i);

			Assert.assertEquals(value.longValue(), array[i]);
		}
	}

	private PredicateFilter<Double> _doublePredicateFilter =
		new PredicateFilter<Double>() {

			@Override
			public boolean filter(Double d) {
				return d >= 1.1;
			}

		};

	private PredicateFilter<Integer> _integerPredicateFilter =
		new PredicateFilter<Integer>() {

			@Override
			public boolean filter(Integer i) {
				return i >= 5;
			}

		};

	private PredicateFilter<User> _userPredicateFilter =
		new PredicateFilter<User>() {

			@Override
			public boolean filter(User user) {
				return user.getAge() > 18;
			}

		};

	private class User {

		public User(String name, int age) {
			_name = name;
			_age = age;
		}

		public int getAge() {
			return _age;
		}

		public String getName() {
			return _name;
		}

		private int _age;
		private String _name;

	}

}