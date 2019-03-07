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

package com.liferay.portal.vulcan.util;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Víctor Galán
 */
public class LocalizedMapUtilTest {

	@Test
	public void testMerge() {

		// Null map

		Map<Locale, String> map = LocalizedMapUtil.merge(
			null, new AbstractMap.SimpleEntry<>(Locale.US, "value"));

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("value", map.get(Locale.US));

		// Null entry

		map = LocalizedMapUtil.merge(
			new HashMap<Locale, String>() {
				{
					put(Locale.US, "value");
				}
			},
			null);

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("value", map.get(Locale.US));

		// Entry value null

		map = LocalizedMapUtil.merge(
			new HashMap<Locale, String>() {
				{
					put(Locale.US, "value");
				}
			},
			new AbstractMap.SimpleEntry<>(Locale.US, null));

		Assert.assertEquals(map.toString(), 0, map.size());
		Assert.assertNull(map.get(Locale.US));

		// Merge map

		map = LocalizedMapUtil.merge(
			new HashMap<Locale, String>() {
				{
					put(Locale.US, "value");
				}
			},
			new AbstractMap.SimpleEntry<>(Locale.UK, "anotherValue"));

		Assert.assertEquals(map.toString(), 2, map.size());
		Assert.assertEquals("value", map.get(Locale.US));
		Assert.assertEquals("anotherValue", map.get(Locale.UK));
	}

}