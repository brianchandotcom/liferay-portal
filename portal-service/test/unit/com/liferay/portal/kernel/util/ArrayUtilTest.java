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
 * @author Roberto Díaz
 */
public class ArrayUtilTest {

	@Test
	public void testContainsAllBooleanArray() throws Exception {
		boolean[] array1 = {true};
		boolean[] array2 = {true, false};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllByteArray() throws Exception {
		byte[] array1 = {1, 2};
		byte[] array2= {1, 2, 3};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllCharArray() throws Exception {
		char[] array1 = {'a', 'b'};
		char[] array2 = {'a', 'b', 'c'};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllDoubleArray() throws Exception {
		double[] array1 = {1.5D, 2.5D};
		double[] array2 = {1.5D, 2.5D, 3.5D};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllFloatArray() throws Exception {
		float[] array1 = {1.5f, 2.5f};
		float[] array2 = {1.5f, 2.5f, 3.5f};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllIntArray() throws Exception {
		int[] array1 = {1, 2};
		int[] array2 = {1, 2, 3};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllLongArray() throws Exception {
		long[] array1 = {1L, 2L};
		long[] array2 = {1L, 2L, 3L};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllShortArray() throws Exception {
		short[] array1 = {1, 2};
		short[] array2 = {1, 2, 3};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllUserArray() throws Exception {
		User brian = new User("brian", 20);
		User julio = new User("julio", 20);
		User sergio = new User("sergio", 20);

		User[] array1 = {julio, sergio};
		User[] array2 = {brian, julio, sergio};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

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
	public void testIsEmptyBooleanArray() {
		boolean[] array = new boolean[] {true, true};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyByteArray() {
		byte[] array = new byte[] {1, 2};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyCharArray() {
		char[] array = new char[] {1, 2};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyDoubleArray() {
		double[] array = new double[] {1d, 2d};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyBooleanArray() {
		boolean[] array = new boolean[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyByteArray() {
		byte[] array = new byte[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyCharArray() {
		char[] array = new char[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyDoubleArray() {
		double[] array = new double[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyFloatArray() {
		float[] array = new float[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyIntArray() {
		int[] array = new int[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyLongArray() {
		long[] array = new long[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyShortArray() {
		short[] array = new short[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyEmptyUserArray() {
		User[] array = new User[] {};

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyFloatArray() {
		float[] array = new float[] {1f, 2f};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyIntArray() {
		int[] array = new int[] {1, 2};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyLongArray() {
		long[] array = new long[] {1L, 2L};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullBooleanArray() {
		boolean[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullByteArray() {
		byte[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullCharArray() {
		char[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullDoubleArray() {
		double[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullFloatArray() {
		float[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullIntArray() {
		int[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullLongArray() {
		long[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullShortArray() {
		short[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyNullUserArray() {
		Object[] array = null;

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyShortArray() {
		short[] array = new short[] {1, 2};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testIsEmptyUserArray() {
		User brian = new User("brian", 20);
		User julio = new User("julio", 20);
		User sergio = new User("sergio", 20);

		User[] array = {brian, julio, sergio};

		Assert.assertFalse(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testReverseBooleanArray() throws Exception {
		boolean[] array = new boolean[] {true, true, false};

		ArrayUtil.reverse(array);

		Assert.assertFalse(array[0]);
		Assert.assertTrue(array[1]);
		Assert.assertTrue(array[2]);
	}

	@Test
	public void testReverseCharArray() throws Exception {
		char[] array = new char[] {'a', 'b', 'c'};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new char[] {'c', 'b', 'a'}, array);
	}

	@Test
	public void testReverseDoubleArray() throws Exception {
		double[] array = new double[] {111.0, 222.0, 333.0};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new double[] {333.0, 222.0, 111.0}, array, 0);
	}

	@Test
	public void testReverseIntArray() throws Exception {
		int[] array = new int[] {111, 222, 333};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new int[] {333, 222, 111}, array);
	}

	@Test
	public void testReverseLongArray() throws Exception {
		long[] array = new long[] {111, 222, 333};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new long[] {333, 222, 111}, array);
	}

	@Test
	public void testReverseShortArray() throws Exception {
		short[] array = new short[] {111, 222, 333};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new short[] {333, 222, 111}, array);
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