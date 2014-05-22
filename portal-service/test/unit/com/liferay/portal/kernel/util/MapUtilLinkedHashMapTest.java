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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Peter Borkuti
 */
public class MapUtilLinkedHashMapTest {

	@Test
	public void testToLinkedHashMapBoolean() throws Exception {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:true:boolean"});

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue(true));
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.get("one") instanceof Boolean);
	}

	@Test
	public void testToLinkedHashMapDefaultDelimiter() throws Exception {
		Map<String, String> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1"});

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue("1"));
		Assert.assertTrue(map.containsKey("one"));
	}

	@Test
	public void testToLinkedHashMapDouble() throws Exception {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1.0:double"});

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue(1.0d));
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.get("one") instanceof Double);
	}

	@Test
	public void testToLinkedHashMapInteger() throws Exception {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:int"});

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue(1));
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.get("one") instanceof Integer);
	}

	@Test
	public void testToLinkedHashMapLong() throws Exception {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:long"});

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue(1l));
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.get("one") instanceof Long);
	}

	@Test
	public void testToLinkedHashMapOneLength() throws Exception {
		Map<String, String> map = MapUtil.toLinkedHashMap(
			new String[] {"one,1"}, ",");

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue("1"));
		Assert.assertTrue(map.containsKey("one"));
	}

	@Test
	public void testToLinkedHashMapOtherType() throws Exception {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:java.lang.Byte"});

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue((byte)1));
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.get("one") instanceof Byte);
	}

	@Test
	public void testToLinkedHashMapShort() throws Exception {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:1:short"});

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue((short)1));
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.get("one") instanceof Short);
	}

	@Test
	public void testToLinkedHashMapString() throws Exception {
		String type = String.class.getName();

		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:X:" + type});

		Assert.assertFalse(map.isEmpty());
		Assert.assertTrue(map.size() == 1);
		Assert.assertTrue(map.containsValue("X"));
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.get("one") instanceof String);
	}

	@Test
	public void testToLinkedHashMapZeroLength() throws Exception {
		Map<String, String> map = MapUtil.toLinkedHashMap(new String[] {});

		Assert.assertTrue(map.isEmpty());
	}

}