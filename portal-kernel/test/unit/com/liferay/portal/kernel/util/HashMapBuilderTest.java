/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class HashMapBuilderTest {

	@Test
	public void testHashMapBuilder() {
		Map<String, Integer> map1 = new HashMap<>();

		map1.put("One", 1);
		map1.put("Three", 3);
		map1.put("Two", 2);

		Assert.assertEquals(
			map1,
			HashMapBuilder.put(
				"One", 1
			).put(
				"Three", 3
			).put(
				"Two", 2
			).build());
	}

	@Test
	public void testNullValues() {
		Map<String, Object> map = HashMapBuilder.<String, Object>put(
			"Hello", null
		).build();

		Assert.assertEquals(map.toString(), 1, map.size());

		map = HashMapBuilder.<String, Object>put(
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
			HashMapBuilder.putAll(
				map
			).build());
	}

	@Test
	public void testPutAllAfterPut() {
		Map<String, Integer> map1 = new HashMap<>();

		map1.put("One", 1);
		map1.put("Three", 3);
		map1.put("Two", 2);

		HashMap<String, Integer> map2 = HashMapBuilder.put(
			"Four", 4
		).putAll(
			map1
		).build();

		Assert.assertEquals(Integer.valueOf(4), map2.get("Four"));

		_assertContainsAll(map1, map2);
	}

	@Test
	public void testPutAllDictionary() {
		Dictionary<String, Integer> dictionary = new Hashtable<>();

		dictionary.put("One", 1);
		dictionary.put("Three", 3);
		dictionary.put("Two", 2);

		Map<String, Integer> map = HashMapBuilder.putAll(
			dictionary
		).build();

		Assert.assertEquals(map.size(), dictionary.size());

		_assertContainsAll(map, dictionary);
	}

	@Test
	public void testPutAllDictionaryAfterPut() {
		Dictionary<String, Integer> dictionary = new Hashtable<>();

		dictionary.put("One", 1);
		dictionary.put("Three", 3);
		dictionary.put("Two", 2);

		HashMap<String, Integer> map = HashMapBuilder.put(
			"Four", 4
		).putAll(
			dictionary
		).build();

		Assert.assertEquals(Integer.valueOf(4), map.get("Four"));

		_assertContainsAll(map, dictionary);
	}

	@Test
	public void testUnsafeFunction() {
		List<String> list = ListUtil.fromArray("hello  ", "  world");

		Map<String, String> map1 = new HashMap<>();

		for (String s : list) {
			map1.put(s, StringUtil.trim(s.toLowerCase()));
		}

		Assert.assertEquals(
			map1,
			HashMapBuilder.put(
				list, s -> StringUtil.trim(s.toLowerCase())
			).build());
	}

	@Test
	public void testUnsafeSuppliers() {
		_testUnsafeSupplierKey(false, 2);
		_testUnsafeSupplierKey(true, 3);

		_testUnsafeSupplierValue(false, 2);
		_testUnsafeSupplierValue(true, 3);

		Map<String, Integer> map = new HashMap<>();

		String s1 = "Hello World";

		String[] array1 = StringUtil.split(s1, ' ');

		map.put(s1, array1.length);

		String s2 = "Hello World Hello World";

		String[] array2 = StringUtil.split(s2, ' ');

		map.put(s2, array2.length);

		Assert.assertEquals(
			map,
			HashMapBuilder.put(
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
			).build());
	}

	private <K, V> void _assertContainsAll(
		Map<K, V> map, Dictionary<K, V> dictionary) {

		for (Enumeration<? extends K> enumeration = dictionary.keys();
			 enumeration.hasMoreElements();) {

			K key = enumeration.nextElement();

			Assert.assertEquals(map.get(key), dictionary.get(key));
		}
	}

	private <K, V> void _assertContainsAll(Map<K, V> map1, Map<K, V> map2) {
		for (Map.Entry<K, V> entry : map1.entrySet()) {
			Assert.assertEquals(
				map2.toString(), entry.getValue(), map2.get(entry.getKey()));
		}
	}

	private void _testUnsafeSupplierKey(
		boolean allowVegatables, int expectedSize) {

		Map<String, String> map = HashMapBuilder.put(
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

		Map<String, String> map = HashMapBuilder.put(
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