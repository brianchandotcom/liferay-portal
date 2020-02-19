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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class LinkedHashMapBuilderTest {

	@Test
	public void testLinkedHashMapBuilder() {
		Map<String, Integer> map1 = new HashMap<>();

		map1.put("One", 1);
		map1.put("Three", 3);
		map1.put("Two", 2);

		Map<String, Integer> map2 = LinkedHashMapBuilder.put(
			"One", 1
		).put(
			"Three", 3
		).put(
			"Two", 2
		).build();

		Assert.assertEquals(map1, map2);
	}

	@Test
	public void testNullValues() {
		Map<String, Object> map = LinkedHashMapBuilder.<String, Object>put(
			"Hello", null
		).build();

		Assert.assertEquals(map.toString(), 1, map.size());

		map = LinkedHashMapBuilder.<String, Object>put(
			"Hello", () -> null
		).build();

		Assert.assertEquals(map.toString(), 0, map.size());
	}

	@Test
	public void testPutAll() {
		Map<String, Integer> map = new HashMap<>();

		map.put("One", 1);
		map.put("Three", 3);
		map.put("Two", 2);

		Assert.assertEquals(
			map,
			LinkedHashMapBuilder.putAll(
				map
			).build());
	}

	@Test
	public void testPutAllAfterPut() {
		Map<String, Integer> map1 = new HashMap<>();

		map1.put("One", 1);
		map1.put("Three", 3);
		map1.put("Two", 2);

		Map<String, Integer> map2 = LinkedHashMapBuilder.put(
			"Four", 4
		).putAll(
			map1
		).build();

		Assert.assertEquals(Integer.valueOf(4), map2.get("Four"));

		_assertContainsAll(map1, map2);
	}

	@Test
	public void testUnsafeFunction() {
		List<String> list = ListUtil.fromArray("hello  ", "  world");

		Map<String, String> map1 = new HashMap<>();

		for (String s : list) {
			map1.put(s, StringUtil.trim(s.toLowerCase()));
		}

		Map<String, String> map2 = LinkedHashMapBuilder.put(
			list, s -> StringUtil.trim(s.toLowerCase())
		).build();

		Assert.assertEquals(map1, map2);
	}

	@Test
	public void testUnsafeSuppliers() {
		_testUnsafeSupplierKey(false, 2);
		_testUnsafeSupplierKey(true, 3);

		_testUnsafeSupplierValue(false, 2);
		_testUnsafeSupplierValue(true, 3);

		Map<String, Integer> map1 = new HashMap<>();

		String s1 = "Hello World";

		String[] array1 = StringUtil.split(s1, ' ');

		map1.put(s1, array1.length);

		String s2 = "Hello World Hello World";

		String[] array2 = StringUtil.split(s2, ' ');

		map1.put(s2, array2.length);

		Map<String, Integer> map2 = LinkedHashMapBuilder.put(
			s1,
			() -> {
				String[] array = StringUtil.split(s1, ' ');

				return array.length;
			}
		).put(
			s2,
			() -> {
				String[] array = StringUtil.split(s2, ' ');

				return array.length;
			}
		).build();

		Assert.assertEquals(map1, map2);
	}

	private <K, V> void _assertContainsAll(Map<K, V> map1, Map<K, V> map2) {
		Set<Map.Entry<K, V>> entries = map1.entrySet();

		Stream<Map.Entry<K, V>> stream = entries.stream();

		Assert.assertTrue(
			map2.toString(),
			stream.allMatch(
				entry -> Objects.equals(
					entry.getValue(), map2.get(entry.getKey()))));
	}

	private void _testUnsafeSupplierKey(
		boolean allowVegatables, int expectedSize) {

		Map<String, String> map = LinkedHashMapBuilder.put(
			"Apple", "Fruit"
		).put(
			"Banana", "Fruit"
		).put(
			() -> {
				if (allowVegatables) {
					return "Carrot";
				}

				return null;
			},
			"Vegetable"
		).build();

		Assert.assertEquals(map.toString(), expectedSize, map.size());
	}

	private void _testUnsafeSupplierValue(
		boolean allowVegatables, int expectedSize) {

		Map<String, String> map = LinkedHashMapBuilder.put(
			"Apple", "Fruit"
		).put(
			"Banana", "Fruit"
		).put(
			"Carrot",
			() -> {
				if (allowVegatables) {
					return "Vegetable";
				}

				return null;
			}
		).build();

		Assert.assertEquals(map.toString(), expectedSize, map.size());
	}

}