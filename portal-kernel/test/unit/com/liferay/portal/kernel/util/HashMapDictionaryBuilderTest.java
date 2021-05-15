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
public class HashMapDictionaryBuilderTest {

	@Test
	public void testHashMapDictionaryBuilder() {
		Dictionary<String, Integer> dictionary1 = new HashMapDictionary<>();

		dictionary1.put("One", 1);
		dictionary1.put("Three", 3);
		dictionary1.put("Two", 2);

		Dictionary<String, Integer> dictionary2 = HashMapDictionaryBuilder.put(
			"One", 1
		).put(
			"Three", 3
		).put(
			"Two", 2
		).build();

		Assert.assertEquals(dictionary1.toString(), dictionary2.toString());
	}

	@Test
	public void testNullValues() {
		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"Hello", null
			).build();

		Assert.assertEquals(dictionary.toString(), 1, dictionary.size());

		dictionary = HashMapDictionaryBuilder.<String, Object>put(
			"Hello", () -> null
		).build();

		Assert.assertEquals(dictionary.toString(), 0, dictionary.size());
	}

	@Test
	public void testPutAll() {
		Map<String, Integer> map = new HashMap<>();

		map.put("One", 1);
		map.put("Three", 3);
		map.put("Two", 2);

		HashMapDictionary<String, Integer> dictionary1 =
			new HashMapDictionary<>();

		dictionary1.putAll(map);

		HashMapDictionary<String, Integer> dictionary2 =
			HashMapDictionaryBuilder.putAll(
				map
			).build();

		Assert.assertEquals(dictionary1.toString(), dictionary2.toString());
	}

	@Test
	public void testPutAllDictionary() {
		Dictionary<String, Integer> dictionary1 = new Hashtable<>();

		dictionary1.put("One", 1);
		dictionary1.put("Three", 3);
		dictionary1.put("Two", 2);

		HashMapDictionary<String, Integer> dictionary2 =
			HashMapDictionaryBuilder.putAll(
				dictionary1
			).build();

		Assert.assertEquals(dictionary2.size(), dictionary1.size());

		for (Enumeration<String> enumeration = dictionary1.keys();
			 enumeration.hasMoreElements();) {

			String key = enumeration.nextElement();

			Assert.assertEquals(dictionary2.get(key), dictionary1.get(key));
		}
	}

	@Test
	public void testUnsafeFunction() {
		List<String> list = ListUtil.fromArray("hello  ", "  world");

		Dictionary<String, String> dictionary1 = new HashMapDictionary<>();

		for (String s : list) {
			dictionary1.put(s, StringUtil.trim(s.toLowerCase()));
		}

		Dictionary<String, String> dictionary2 = HashMapDictionaryBuilder.put(
			list, s -> StringUtil.trim(s.toLowerCase())
		).build();

		Assert.assertEquals(dictionary1.toString(), dictionary2.toString());
	}

	@Test
	public void testUnsafeSuppliers() {
		_testUnsafeSupplierKey(false, 2);
		_testUnsafeSupplierKey(true, 3);

		_testUnsafeSupplierValue(false, 2);
		_testUnsafeSupplierValue(true, 3);

		Dictionary<String, Integer> dictionary1 = new HashMapDictionary<>();

		String s1 = "Hello World";

		String[] array1 = StringUtil.split(s1, ' ');

		dictionary1.put(s1, array1.length);

		String s2 = "Hello World Hello World";

		String[] array2 = StringUtil.split(s2, ' ');

		dictionary1.put(s2, array2.length);

		Dictionary<String, Integer> dictionary2 = HashMapDictionaryBuilder.put(
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

		Assert.assertEquals(dictionary1.toString(), dictionary2.toString());
	}

	private void _testUnsafeSupplierKey(
		boolean allowVegatables, int expectedSize) {

		Dictionary<String, String> dictionary = HashMapDictionaryBuilder.put(
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

		Assert.assertEquals(
			dictionary.toString(), expectedSize, dictionary.size());
	}

	private void _testUnsafeSupplierValue(
		boolean allowVegatables, int expectedSize) {

		Dictionary<String, String> dictionary = HashMapDictionaryBuilder.put(
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

		Assert.assertEquals(
			dictionary.toString(), expectedSize, dictionary.size());
	}

}