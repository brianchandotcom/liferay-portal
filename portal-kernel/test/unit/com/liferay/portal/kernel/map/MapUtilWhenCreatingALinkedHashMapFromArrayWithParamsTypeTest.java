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

package com.liferay.portal.kernel.map;

import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 * @author Manuel de la Peña
 * @author Péter Borkuti
 */
public class MapUtilWhenCreatingALinkedHashMapFromArrayWithParamsTypeTest {

	@Test
	public void testShouldReturnMapWithBoolean() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:true:boolean"});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(true));
		Assert.assertTrue(map.get("one") instanceof Boolean);

		map = MapUtil.toLinkedHashMap(
			new String[] {"one:true:" + Boolean.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(true));
		Assert.assertTrue(map.get("one") instanceof Boolean);
	}

	@Test
	public void testShouldReturnMapWithComposite() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:" + Byte.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue((byte)1));
		Assert.assertTrue(map.get("one") instanceof Byte);

		map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:" + Float.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue((float)1));
		Assert.assertTrue(map.get("one") instanceof Float);
	}

	@Test
	public void testShouldReturnMapWithDouble() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1.0:double"});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(1.0D));
		Assert.assertTrue(map.get("one") instanceof Double);

		map = MapUtil.toLinkedHashMap(
			new String[] {"one:1.0:" + Double.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(1.0D));
		Assert.assertTrue(map.get("one") instanceof Double);
	}

	@Test
	public void testShouldReturnMapWithInteger() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:int"});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(1));
		Assert.assertTrue(map.get("one") instanceof Integer);

		map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:" + Integer.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(1));
		Assert.assertTrue(map.get("one") instanceof Integer);
	}

	@Test
	public void testShouldReturnMapWithLong() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:long"});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(1L));
		Assert.assertTrue(map.get("one") instanceof Long);

		map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:" + Long.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(1L));
		Assert.assertTrue(map.get("one") instanceof Long);
	}

	@Test
	public void testShouldReturnMapWithShort() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:short"});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue((short)1));
		Assert.assertTrue(map.get("one") instanceof Short);

		map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:" + Short.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue((short)1));
		Assert.assertTrue(map.get("one") instanceof Short);
	}

	@Test
	public void testShouldReturnMapWithString() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:X:" + String.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue("X"));
		Assert.assertTrue(map.get("one") instanceof String);
	}

}