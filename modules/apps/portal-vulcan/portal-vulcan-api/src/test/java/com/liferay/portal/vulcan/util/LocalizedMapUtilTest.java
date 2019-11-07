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

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.AbstractMap;
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
			null, new AbstractMap.SimpleEntry<>(LocaleUtil.US, "hello"));

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("hello", map.get(LocaleUtil.US));

		// Null entry

		map = LocalizedMapUtil.merge(
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.US, "hello"
			).build(),
			null);

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("hello", map.get(LocaleUtil.US));

		// Entry hello null

		map = LocalizedMapUtil.merge(
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.US, "hello"
			).build(),
			new AbstractMap.SimpleEntry<>(LocaleUtil.US, null));

		Assert.assertEquals(map.toString(), 0, map.size());
		Assert.assertNull(map.get(LocaleUtil.US));

		// Merge map

		map = LocalizedMapUtil.merge(
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.US, "hello"
			).build(),
			new AbstractMap.SimpleEntry<>(LocaleUtil.FRANCE, "bonjour"));

		Assert.assertEquals(map.toString(), 2, map.size());
		Assert.assertEquals("bonjour", map.get(LocaleUtil.FRANCE));
		Assert.assertEquals("hello", map.get(LocaleUtil.US));
	}

}