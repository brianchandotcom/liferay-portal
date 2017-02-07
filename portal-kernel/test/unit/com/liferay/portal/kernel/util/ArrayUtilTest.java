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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.AssertUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 */
public class ArrayUtilTest {

	@Test
	public void testAppend() {
		Assert.assertArrayEquals(
			new boolean[] {true, false, true},
			ArrayUtil.append(new boolean[] {true, false}, true));
		Assert.assertArrayEquals(
			new boolean[] {true, false, false, false},
			ArrayUtil.append(
				new boolean[] {true, false}, new boolean[] {false, false}));
		Assert.assertArrayEquals(
			new byte[] {1, 2, 3, 4},
			ArrayUtil.append(new byte[] {1, 2, 3}, (byte)4));
		Assert.assertArrayEquals(
			new byte[] {1, 2, 3, 4, 5, 6},
			ArrayUtil.append(new byte[] {1, 2, 3}, new byte[] {4, 5, 6}));
		Assert.assertArrayEquals(
			new char[] {'a', 'b', 'c', 'd'},
			ArrayUtil.append(new char[] {'a', 'b', 'c'}, 'd'));
		Assert.assertArrayEquals(
			new char[] {'a', 'b', 'c', 'd', 'e', 'f'},
			ArrayUtil.append(
				new char[] {'a', 'b', 'c'}, new char[] {'d', 'e', 'f'}));
		Assert.assertArrayEquals(
			new double[] {1.0, 2.0, 3.0, 4.0},
			ArrayUtil.append(new double[] {1.0, 2.0, 3.0}, 4.0), 0.0001);
		Assert.assertArrayEquals(
			new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0},
			ArrayUtil.append(
				new double[] {1.0, 2.0, 3.0}, new double[] {4.0, 5.0, 6.0}),
			0.0001);
		Assert.assertArrayEquals(
			new float[] {1.0f, 2.0f, 3.0f, 4.0f},
			ArrayUtil.append(new float[] {1.0f, 2.0f, 3.0f}, 4.0f), 0.0001f);
		Assert.assertArrayEquals(
			new float[] {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f},
			ArrayUtil.append(
				new float[] {1.0f, 2.0f, 3.0f}, new float[] {4.0f, 5.0f, 6.0f}),
			0.0001f);
		Assert.assertArrayEquals(
			new int[] {1, 2, 3, 4}, ArrayUtil.append(new int[] {1, 2, 3}, 4));
		Assert.assertArrayEquals(
			new int[] {1, 2, 3, 4, 5, 6},
			ArrayUtil.append(new int[] {1, 2, 3}, new int[] {4, 5, 6}));
		Assert.assertArrayEquals(
			new long[] {1L, 2L, 3L, 4L},
			ArrayUtil.append(new long[] {1L, 2L, 3L}, 4L));
		Assert.assertArrayEquals(
			new long[] {1L, 2L, 3L, 4L, 5L, 6L},
			ArrayUtil.append(new long[] {1L, 2L, 3L}, new long[] {4L, 5L, 6L}));
		Assert.assertArrayEquals(
			new short[] {1, 2, 3, 4},
			ArrayUtil.append(new short[] {1, 2, 3}, (short)4));
		Assert.assertArrayEquals(
			new short[] {1, 2, 3, 4, 5, 6},
			ArrayUtil.append(new short[] {1, 2, 3}, new short[] {4, 5, 6}));
		Assert.assertArrayEquals(
			new Integer[] {1, 2, 3, 4, 5, 6},
			ArrayUtil.append(
				new Integer[] {1, 2}, new Integer[] {3, 4},
				new Integer[] {5, 6}));
		Assert.assertArrayEquals(
			new Integer[] {1, 2, 3, 4},
			ArrayUtil.append(new Integer[] {1, 2, 3}, 4));
		Assert.assertArrayEquals(
			new Integer[] {1, 2, 3, 4, 5, 6},
			ArrayUtil.append(new Integer[] {1, 2, 3}, new Integer[] {4, 5, 6}));
		Assert.assertArrayEquals(
			new Integer[][] {new Integer[] {1, 2, 3}, new Integer[] {4, 5, 6}},
			ArrayUtil.append(
				new Integer[][] {new Integer[] {1, 2, 3}},
				new Integer[] {4, 5, 6}));
		Assert.assertArrayEquals(
			new Integer[][] {new Integer[] {1, 2, 3}, new Integer[] {4, 5, 6}},
			ArrayUtil.append(
				new Integer[][] {new Integer[] {1, 2, 3}},
				new Integer[][] {new Integer[] {4, 5, 6}}));
	}

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
		byte[] array2 = {1, 2, 3};

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
	public void testContainsBooleanArray() throws Exception {
		boolean[] array1 = {true, true};

		Assert.assertFalse(ArrayUtil.contains(array1, false));
		Assert.assertTrue(ArrayUtil.contains(array1, true));
	}

	@Test
	public void testContainsByteArray() throws Exception {
		byte[] array = {2, 3};

		Assert.assertFalse(ArrayUtil.contains(array, (byte)1));
		Assert.assertTrue(ArrayUtil.contains(array, (byte)2));
	}

	@Test
	public void testContainsCharArray() throws Exception {
		char[] array = {'a', 'b'};

		Assert.assertFalse(ArrayUtil.contains(array, 'C'));
		Assert.assertTrue(ArrayUtil.contains(array, 'a'));
	}

	@Test
	public void testContainsDoubleArray() throws Exception {
		double[] array = {2.5D, 3.5D};

		Assert.assertFalse(ArrayUtil.contains(array, 1.5D));
		Assert.assertTrue(ArrayUtil.contains(array, 2.5D));
	}

	@Test
	public void testContainsFloatArray() throws Exception {
		float[] array = {2.5f, 3.5f};

		Assert.assertFalse(ArrayUtil.contains(array, 1.5f));
		Assert.assertTrue(ArrayUtil.contains(array, 2.5f));
	}

	@Test
	public void testContainsIntArray() throws Exception {
		int[] array = {2, 3};

		Assert.assertFalse(ArrayUtil.contains(array, 1));
		Assert.assertTrue(ArrayUtil.contains(array, 2));
	}

	@Test
	public void testContainsLongArray() throws Exception {
		long[] array = {2L, 3L};

		Assert.assertFalse(ArrayUtil.contains(array, 1L));
		Assert.assertTrue(ArrayUtil.contains(array, 2L));
	}

	@Test
	public void testContainsShortArray() throws Exception {
		short[] array = {2, 3};

		Assert.assertFalse(ArrayUtil.contains(array, (short)1));
		Assert.assertTrue(ArrayUtil.contains(array, (short)2));
	}

	@Test
	public void testContainsStringArray() throws Exception {
		String[] array = {"a", "b", null};

		Assert.assertFalse(ArrayUtil.contains(array, "c", true));
		Assert.assertFalse(ArrayUtil.contains(array, "C", false));
		Assert.assertTrue(ArrayUtil.contains(array, "a", true));
		Assert.assertTrue(ArrayUtil.contains(array, "a", false));
		Assert.assertTrue(ArrayUtil.contains(array, "A", true));
		Assert.assertTrue(ArrayUtil.contains(array, null, true));
		Assert.assertTrue(ArrayUtil.contains(array, null, false));
	}

	@Test
	public void testContainsUserArray() throws Exception {
		User brian = new User("brian", 20);
		User julio = new User("julio", 20);
		User sergio = new User("sergio", 20);

		User[] array = {julio, sergio, null};

		Assert.assertFalse(ArrayUtil.contains(array, brian));
		Assert.assertTrue(ArrayUtil.contains(array, julio));
		Assert.assertTrue(ArrayUtil.contains(array, null));
	}

	@Test
	public void testCountStringArray() {
		String[] array = {"a", "b", "c"};

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					if (string.equals("b")) {
						return true;
					}

					return false;
				}

			};

		Assert.assertEquals(1, ArrayUtil.count(array, predicateFilter));
	}

	@Test
	public void testCountStringEmptyArray() {
		String[] array = {};

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return true;
				}

			};

		Assert.assertEquals(0, ArrayUtil.count(array, predicateFilter));
	}

	@Test
	public void testCountStringNullArray() {
		String[] array = null;

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return true;
				}

			};

		Assert.assertEquals(0, ArrayUtil.count(array, predicateFilter));
	}

	@Test
	public void testFilterDoubleArray() {
		double[] array = ArrayUtil.filter(
			new double[] {0.1, 0.2, 1.2, 1.3}, _doublePredicateFilter);

		Assert.assertEquals(Arrays.toString(array), 2, array.length);
		AssertUtils.assertEquals(new double[] {1.2, 1.3}, array);
	}

	@Test
	public void testFilterDoubleEmptyArray() {
		double[] array = ArrayUtil.filter(
			new double[0], _doublePredicateFilter);

		Assert.assertEquals(Arrays.toString(array), 0, array.length);
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

		Assert.assertEquals(Arrays.toString(array), 5, array.length);
		Assert.assertArrayEquals(new int[] {5, 6, 7, 8, 9}, array);
	}

	@Test
	public void testFilterIntegerEmptyArray() {
		int[] array = ArrayUtil.filter(new int[0], _integerPredicateFilter);

		Assert.assertEquals(Arrays.toString(array), 0, array.length);
		Assert.assertArrayEquals(new int[0], array);
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

		Assert.assertEquals(Arrays.toString(array), 1, array.length);

		Assert.assertEquals("john", array[0].getName());
		Assert.assertEquals(26, array[0].getAge());
	}

	@Test
	public void testFilterUserEmptyArray() {
		User[] array = ArrayUtil.filter(new User[0], _userPredicateFilter);

		Assert.assertEquals(Arrays.toString(array), 0, array.length);
	}

	@Test
	public void testFilterUserNullArray() {
		User[] array = ArrayUtil.filter(null, _userPredicateFilter);

		Assert.assertNull(array);
	}

	@Test
	public void testIsEmptyBooleanArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((boolean[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new boolean[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new boolean[] {true, true}));
	}

	@Test
	public void testIsEmptyByteArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((byte[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new byte[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new byte[] {1, 2}));
	}

	@Test
	public void testIsEmptyCharArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((char[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new char[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new char[] {1, 2}));
	}

	@Test
	public void testIsEmptyDoubleArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((double[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new double[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new double[] {1, 2}));
	}

	@Test
	public void testIsEmptyFloatArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((float[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new float[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new float[] {1, 2}));
	}

	@Test
	public void testIsEmptyIntArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((int[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new int[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new int[] {1, 2}));
	}

	@Test
	public void testIsEmptyLongArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((long[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new long[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new long[] {1, 2}));
	}

	@Test
	public void testIsEmptyShortArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((short[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new short[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new short[] {1, 2}));
	}

	@Test
	public void testIsEmptyUserArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((User[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new User[0]));
		Assert.assertFalse(
			ArrayUtil.isEmpty(
				new User[] {
					new User("brian", 20), new User("julio", 20),
					new User("sergio", 20)
				}));
	}

	@Test
	public void testIsNotEmptyBooleanArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((boolean[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new boolean[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new boolean[] {true, true}));
	}

	@Test
	public void testIsNotEmptyByteArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((byte[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new byte[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new byte[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyCharArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((char[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new char[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new char[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyDoubleArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((double[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new double[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new double[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyFloatArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((float[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new float[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new float[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyIntArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((int[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new int[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new int[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyLongArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((long[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new long[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new long[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyShortArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((short[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new short[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new short[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyUserArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((User[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new User[0]));
		Assert.assertTrue(
			ArrayUtil.isNotEmpty(
				new User[] {
					new User("brian", 20), new User("julio", 20),
					new User("sergio", 20)
				}));
	}

	@Test
	public void testRemoveFromBooleanArray() {
		boolean[] array = {true, true, false};

		array = ArrayUtil.remove(array, false);

		Assert.assertArrayEquals(new boolean[] {true, true}, array);
	}

	@Test
	public void testRemoveFromBooleanEmptyArray() {
		boolean[] array = {};

		array = ArrayUtil.remove(array, false);

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromBooleanNullArray() {
		boolean[] array = null;

		array = ArrayUtil.remove(array, false);

		Assert.assertNull(array);
	}

	@Test
	public void testRemoveFromByteArray() {
		byte[] array = {1, 2, 3};

		array = ArrayUtil.remove(array, (byte)3);

		Assert.assertArrayEquals(new byte[] {1, 2}, array);
	}

	@Test
	public void testRemoveFromByteEmptyArray() {
		byte[] array = {};

		array = ArrayUtil.remove(array, (byte)3);

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromByteNullArray() {
		byte[] array = null;

		array = ArrayUtil.remove(array, (byte)3);

		Assert.assertNull(array);
	}

	@Test
	public void testRemoveFromCharArray() {
		char[] array = {'a', 'b', 'c'};

		array = ArrayUtil.remove(array, 'c');

		Assert.assertArrayEquals(new char[] {'a', 'b'}, array);
	}

	@Test
	public void testRemoveFromCharEmptyArray() {
		char[] array = {};

		array = ArrayUtil.remove(array, 'c');

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromCharNullArray() {
		char[] array = null;

		array = ArrayUtil.remove(array, 'c');

		Assert.assertNull(array);
	}

	@Test
	public void testRemoveFromDoubleArray() {
		double[] array = {1.0D, 2.0D, 3.0D};

		array = ArrayUtil.remove(array, 3.0D);

		Assert.assertArrayEquals(new double[] {1.0D, 2.0D}, array, 0);
	}

	@Test
	public void testRemoveFromDoubleEmptyArray() {
		double[] array = {};

		array = ArrayUtil.remove(array, 3.0D);

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromDoubleNullArray() {
		double[] array = null;

		array = ArrayUtil.remove(array, 3.0D);

		Assert.assertNull(array);
	}

	@Test
	public void testRemoveFromFloatArray() {
		float[] array = {1.5f, 2.5f, 3.5f};

		array = ArrayUtil.remove(array, 3.5f);

		Assert.assertArrayEquals(new float[] {1.5f, 2.5f}, array, 0);
	}

	@Test
	public void testRemoveFromFloatEmptyArray() {
		float[] array = {};

		array = ArrayUtil.remove(array, 3.5f);

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromFloatNullArray() {
		float[] array = null;

		array = ArrayUtil.remove(array, 3.5f);

		Assert.assertNull(array);
	}

	@Test
	public void testRemoveFromIntArray() {
		int[] array = {1, 2, 3};

		array = ArrayUtil.remove(array, (byte)3);

		Assert.assertArrayEquals(new int[] {1, 2}, array);
	}

	@Test
	public void testRemoveFromIntEmptyArray() {
		int[] array = {};

		array = ArrayUtil.remove(array, (byte)3);

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromIntNullArray() {
		int[] array = null;

		array = ArrayUtil.remove(array, (byte)3);

		Assert.assertNull(array);
	}

	@Test
	public void testRemoveFromLongArray() {
		long[] array = {1L, 2L, 3L};

		array = ArrayUtil.remove(array, 3L);

		Assert.assertArrayEquals(new long[] {1L, 2L}, array);
	}

	@Test
	public void testRemoveFromLongEmptyArray() {
		long[] array = {};

		array = ArrayUtil.remove(array, 3L);

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromLongNullArray() {
		long[] array = null;

		array = ArrayUtil.remove(array, 3L);

		Assert.assertNull(array);
	}

	@Test
	public void testRemoveFromShortArray() {
		short[] array = {1, 2, 3};

		array = ArrayUtil.remove(array, (short)3);

		Assert.assertArrayEquals(new short[] {1, 2}, array);
	}

	@Test
	public void testRemoveFromShortEmptyArray() {
		short[] array = {};

		array = ArrayUtil.remove(array, (short)3);

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromShortNullArray() {
		short[] array = null;

		array = ArrayUtil.remove(array, (short)3);

		Assert.assertNull(array);
	}

	@Test
	public void testRemoveFromStringArray() {
		String[] array = {"a", "b", "c"};

		array = ArrayUtil.remove(array, "c");

		Assert.assertArrayEquals(new String[] {"a", "b"}, array);
	}

	@Test
	public void testRemoveFromStringEmptyArray() {
		String[] array = {};

		array = ArrayUtil.remove(array, "c");

		Assert.assertTrue(ArrayUtil.isEmpty(array));
	}

	@Test
	public void testRemoveFromStringNullArray() {
		String[] array = null;

		array = ArrayUtil.remove(array, "c");

		Assert.assertNull(array);
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
	public void testSubset() {
		Assert.assertArrayEquals(
			new boolean[] {true, false},
			ArrayUtil.subset(new boolean[] {true, false, true}, 0, 2));
		Assert.assertArrayEquals(
			new byte[] {1, 2, 3},
			ArrayUtil.subset(new byte[] {1, 2, 3, 4}, 0, 3));
		Assert.assertArrayEquals(
			new char[] {'a', 'b', 'c'},
			ArrayUtil.subset(new char[] {'a', 'b', 'c', 'd'}, 0, 3));
		Assert.assertArrayEquals(
			new double[] {1.0, 2.0, 3.0},
			ArrayUtil.subset(new double[] {1.0, 2.0, 3.0, 4.0}, 0, 3), 0.0001);
		Assert.assertArrayEquals(
			new float[] {1.0f, 2.0f, 3.0f},
			ArrayUtil.subset(new float[] {1.0f, 2.0f, 3.0f, 4.0f}, 0, 3),
			0.0001f);
		Assert.assertArrayEquals(
			new int[] {1, 2, 3},
			ArrayUtil.subset(new int[] {1, 2, 3, 4}, 0, 3));
		Assert.assertArrayEquals(
			new long[] {1, 2, 3},
			ArrayUtil.subset(new long[] {1, 2, 3, 4}, 0, 3));
		Assert.assertArrayEquals(
			new short[] {1, 2, 3},
			ArrayUtil.subset(new short[] {1, 2, 3, 4}, 0, 3));
		Assert.assertArrayEquals(
			new Integer[] {1, 2, 3},
			ArrayUtil.subset(new Integer[] {1, 2, 3, 4}, 0, 3));
	}

	@Test
	public void testToDoubleArray() throws Exception {
		List<Double> list = new ArrayList<>();

		list.add(1.0);
		list.add(2.0);

		double[] array = ArrayUtil.toDoubleArray(list);

		Assert.assertEquals(list.toString(), array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Double value = list.get(i);

			AssertUtils.assertEquals(value.doubleValue(), array[i]);
		}
	}

	@Test
	public void testToFloatArray() throws Exception {
		List<Float> list = new ArrayList<>();

		list.add(1.0F);
		list.add(2.0F);

		float[] array = ArrayUtil.toFloatArray(list);

		Assert.assertEquals(list.toString(), array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Float value = list.get(i);

			AssertUtils.assertEquals(value.floatValue(), array[i]);
		}
	}

	@Test
	public void testToIntArray() throws Exception {
		List<Integer> list = new ArrayList<>();

		list.add(1);
		list.add(2);

		int[] array = ArrayUtil.toIntArray(list);

		Assert.assertEquals(list.toString(), array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Integer value = list.get(i);

			Assert.assertEquals(value.intValue(), array[i]);
		}
	}

	@Test
	public void testToLongArray() throws Exception {
		List<Long> list = new ArrayList<>();

		list.add(1L);
		list.add(2L);

		long[] array = ArrayUtil.toLongArray(list);

		Assert.assertEquals(list.toString(), array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Long value = list.get(i);

			Assert.assertEquals(value.longValue(), array[i]);
		}
	}

	@Test
	public void testUnique() {
		Assert.assertArrayEquals(
			new byte[] {1, 2, 3}, ArrayUtil.unique(new byte[] {1, 2, 3, 3, 2}));
		Assert.assertArrayEquals(
			new double[] {1.0, 2.0, 3.0},
			ArrayUtil.unique(new double[] {1.0, 2.0, 3.0, 1.0, 2.0, 3.0}),
			0.0001);
		Assert.assertArrayEquals(
			new float[] {1.0f, 2.0f, 3.0f},
			ArrayUtil.unique(new float[] {1.0f, 2.0f, 3.0f, 3.0f, 2.0f}),
			0.0001f);
		Assert.assertArrayEquals(
			new int[] {1, 2, 3}, ArrayUtil.unique(new int[] {1, 2, 3, 3, 2}));
		Assert.assertArrayEquals(
			new long[] {1L, 2L, 3L},
			ArrayUtil.unique(new long[] {1L, 2L, 3L, 3L, 2L}));
		Assert.assertArrayEquals(
			new short[] {1, 2, 3},
			ArrayUtil.unique(new short[] {1, 2, 3, 3, 2}));
		Assert.assertArrayEquals(
			new String[] {"hello", "world"},
			ArrayUtil.unique(
				new String[] {"hello", "hello", "world", "world"}));
	}

	private final PredicateFilter<Double> _doublePredicateFilter =
		new PredicateFilter<Double>() {

			@Override
			public boolean filter(Double d) {
				if (d >= 1.1) {
					return true;
				}

				return false;
			}

		};

	private final PredicateFilter<Integer> _integerPredicateFilter =
		new PredicateFilter<Integer>() {

			@Override
			public boolean filter(Integer i) {
				if (i >= 5) {
					return true;
				}

				return false;
			}

		};

	private final PredicateFilter<User> _userPredicateFilter =
		new PredicateFilter<User>() {

			@Override
			public boolean filter(User user) {
				if (user.getAge() > 18) {
					return true;
				}

				return false;
			}

		};

	private static class User {

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

		private final int _age;
		private final String _name;

	}

}