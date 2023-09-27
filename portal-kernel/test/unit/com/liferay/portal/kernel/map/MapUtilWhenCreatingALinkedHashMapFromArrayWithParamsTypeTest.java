/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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