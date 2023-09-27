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
public class
	MapUtilWhenCreatingALinkedHashMapFromArrayWithInvalidParamsTypeValueTest {

	@Test(expected = NumberFormatException.class)
	public void testShouldFailWithCompositeDouble() {
		MapUtil.toLinkedHashMap(
			new String[] {"one:foo:" + Double.class.getName()});
	}

	@Test(expected = NumberFormatException.class)
	public void testShouldFailWithCompositeInteger() {
		MapUtil.toLinkedHashMap(
			new String[] {"one:foo:" + Integer.class.getName()});
	}

	@Test(expected = NumberFormatException.class)
	public void testShouldFailWithCompositeLong() {
		MapUtil.toLinkedHashMap(
			new String[] {"one:foo:" + Long.class.getName()});
	}

	@Test(expected = NumberFormatException.class)
	public void testShouldFailWithCompositeShort() {
		MapUtil.toLinkedHashMap(
			new String[] {"one:foo:" + Short.class.getName()});
	}

	@Test(expected = NumberFormatException.class)
	public void testShouldFailWithDouble() {
		MapUtil.toLinkedHashMap(new String[] {"one:foo:double"});
	}

	@Test(expected = NumberFormatException.class)
	public void testShouldFailWithInteger() {
		MapUtil.toLinkedHashMap(new String[] {"one:foo:int"});
	}

	@Test(expected = NumberFormatException.class)
	public void testShouldFailWithLong() {
		MapUtil.toLinkedHashMap(new String[] {"one:foo:long"});
	}

	@Test(expected = NumberFormatException.class)
	public void testShouldFailWithShort() {
		MapUtil.toLinkedHashMap(new String[] {"one:foo:short"});
	}

	@Test
	public void testShouldReturnMapWithBoolean() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(
			new String[] {"one:foo:boolean"});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(false));
		Assert.assertTrue(map.get("one") instanceof Boolean);

		map = MapUtil.toLinkedHashMap(
			new String[] {"one:foo:" + Boolean.class.getName()});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue(false));
		Assert.assertTrue(map.get("one") instanceof Boolean);
	}

}