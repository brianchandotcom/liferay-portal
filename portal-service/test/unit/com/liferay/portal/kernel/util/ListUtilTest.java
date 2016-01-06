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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Olaf Kock
 * @author Jos� Navarro
 */
public class ListUtilTest {

	@Test
	public void testCountEmptyList() {
		List<String> list = new ArrayList<>();

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return true;
				}

			};

		Assert.assertEquals(0, ListUtil.count(list, predicateFilter));
	}

	@Test
	public void testCountList() {
		List<String> list = new ArrayList<>();

		list.add("a");
		list.add("b");
		list.add("c");

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return string.equals("b");
				}

			};

		Assert.assertEquals(1, ListUtil.count(list, predicateFilter));

		predicateFilter = new PredicateFilter<String>() {

			@Override
			public boolean filter(String string) {
				return string.equals("z");
			}

		};

		Assert.assertEquals(0, ListUtil.count(list, predicateFilter));
	}

	@Test
	public void testCountNullList() {
		List<String> list = null;

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return true;
				}

			};

		Assert.assertEquals(0, ListUtil.count(list, predicateFilter));
	}

	@Test
	public void testExistsEmptyList() {
		List<String> list = new ArrayList<>();

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return true;
				}

			};

		Assert.assertFalse(ListUtil.exists(list, predicateFilter));
	}

	@Test
	public void testExistsList() {
		List<String> list = new ArrayList<>();

		list.add("a");
		list.add("bb");
		list.add("c");

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					if (string.length() == 2) {
						return true;
					}

					return false;
				}

			};

		Assert.assertTrue(ListUtil.exists(list, predicateFilter));

		predicateFilter = new PredicateFilter<String>() {

			@Override
			public boolean filter(String string) {
				return string.equals("z");
			}

		};

		Assert.assertFalse(ListUtil.exists(list, predicateFilter));
	}

	@Test
	public void testExistsNullList() {
		List<String> list = null;

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return true;
				}

			};

		Assert.assertFalse(ListUtil.exists(list, predicateFilter));
	}

	@Test
	public void testFilterWithoutOutputList() {
		List<String> expectedOutputList = new ArrayList<>();

		expectedOutputList.add("b");

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return string.equals("b");
				}

			};

		List<String> inputList = new ArrayList<>();

		inputList.add("a");
		inputList.add("b");
		inputList.add("c");

		Collection<String> actualOutputList = ListUtil.filter(
			inputList, predicateFilter);

		Assert.assertEquals(expectedOutputList, actualOutputList);
	}

	@Test
	public void testFilterWithOutputList() {
		List<String> expectedOutputList = new ArrayList<>();

		expectedOutputList.add("0");
		expectedOutputList.add("b");

		List<String> inputList = new ArrayList<>();

		inputList.add("a");
		inputList.add("b");
		inputList.add("c");

		List<String> outputList = new ArrayList<>();

		outputList.add("0");

		PredicateFilter<String> predicateFilter =
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					return string.equals("b");
				}

			};

		Collection<String> actualOutputList = ListUtil.filter(
			inputList, outputList, predicateFilter);

		Assert.assertSame(outputList, actualOutputList);
		Assert.assertEquals(expectedOutputList, actualOutputList);
	}

	@Test
	public void testRemoveEmptyElement() {
		List<String> list = new ArrayList<>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<>();

		List<String> expectedList = new ArrayList<>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveFromEmptyList() {
		List<String> list = Collections.<String>emptyList();

		List<String> removeList = new ArrayList<>();

		removeList.add("aaa");
		removeList.add("bbb");

		Assert.assertEquals(
			Collections.emptyList(),
			ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveMultipleElements() {
		List<String> list = new ArrayList<>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<>();

		removeList.add("aaa");
		removeList.add("bbb");
		removeList.add("ccc");

		Assert.assertEquals(
			Collections.emptyList(), ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveNullElement() {
		List<String> list = new ArrayList<>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> expectedList = new ArrayList<>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		List<String> removeList = null;

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveSingleElement() {
		List<String> list = new ArrayList<>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<>();

		removeList.add("aaa");

		List<String> expectedList = new ArrayList<>();

		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveWrongElement() {
		List<String> list = new ArrayList<>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<>();

		removeList.add("ddd");

		List<String> expectedList = new ArrayList<>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testSubList() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4);

		// Negative start, positive end, within range

		Assert.assertEquals(
			Arrays.asList(1, 2, 3), ListUtil.subList(list, -1, 3));

		// Negative start, positive end, out of range

		Assert.assertEquals(list, ListUtil.subList(list, -1, 5));

		// Negative start, negative end

		Assert.assertEquals(list, ListUtil.subList(list, -1, -1));

		// Proper start and end

		Assert.assertEquals(Arrays.asList(2, 3), ListUtil.subList(list, 1, 3));

		// Start is equal to end

		Assert.assertSame(
			Collections.emptyList(), ListUtil.subList(list, 1, 1));

		// Start is greater than end

		Assert.assertSame(
			Collections.emptyList(), ListUtil.subList(list, 2, 1));
	}

	@Test
	public void testToArray() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4);

		String[] array = ListUtil.toArray(
			list,
			new Accessor<Integer, String>() {

				@Override
				public String get(Integer integer) {
					return String.valueOf(integer);
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<Integer> getTypeClass() {
					return Integer.class;
				}

			});

		Assert.assertArrayEquals(new String[] {"1", "2", "3", "4"}, array);
	}

	@Test
	public void testToArrayEmpty() {
		List<Integer> list = Collections.emptyList();

		String[] array = ListUtil.toArray(
			list,
			new Accessor<Integer, String>() {

				@Override
				public String get(Integer integer) {
					return String.valueOf(integer);
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<Integer> getTypeClass() {
					return Integer.class;
				}

			});

		Assert.assertArrayEquals(new String[0], array);
	}

	@Test
	public void testToList() throws Exception {
		List<String> list = new ArrayList<>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<Object> list2 = ListUtil.toList(list);

		Assert.assertArrayEquals(
			new Object[] {"aaa", "bbb", "ccc"}, list2.toArray());
	}

	@Test
	public void testToLongArray() {
		List<String> list = Arrays.asList("1", "2", "3", "4");

		long[] array = ListUtil.toLongArray(
			list,
			new Accessor<String, Long>() {

				@Override
				public Long get(String string) {
					return Long.parseLong(string);
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<String> getTypeClass() {
					return String.class;
				}

			});

		Assert.assertArrayEquals(new long[] {1, 2, 3, 4}, array);
	}

	@Test
	public void testToLongArrayEmpty() {
		List<String> list = Collections.emptyList();

		long[] array = ListUtil.toLongArray(
			list,
			new Accessor<String, Long>() {

				@Override
				public Long get(String string) {
					return Long.parseLong(string);
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<String> getTypeClass() {
					return String.class;
				}

			});

		Assert.assertArrayEquals(new long[0], array);
	}

	@Test
	public void testToStringIntegerList() throws Exception {
		List<Integer> list = new ArrayList<>();

		list.add(111);
		list.add(222);
		list.add(333);

		Assert.assertEquals(
			"111,222,333",
			ListUtil.toString(list, StringPool.NULL, StringPool.COMMA));
	}

	@Test
	public void testToStringLongList() throws Exception {
		List<Long> list = new ArrayList<>();

		list.add(111L);
		list.add(222L);
		list.add(333L);

		Assert.assertEquals(
			"111, 222, 333",
			ListUtil.toString(
				list, StringPool.BLANK, StringPool.COMMA_AND_SPACE));
	}

	@Test
	public void testToStringStringList() throws Exception {
		List<String> list = new ArrayList<>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		Assert.assertEquals(
			"aaa.bbb.ccc",
			ListUtil.toString(list, (String)null, StringPool.PERIOD));
	}

}