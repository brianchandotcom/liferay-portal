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

import com.liferay.petra.string.StringPool;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Georgel Pop
 */
public class MapUtilTest {

	@Test
	public void testGetWithFallbackKeyEmptyTranslation() {
		MapUtil mapUtil = new MapUtil();

		Assert.assertEquals(
			"US translated",
			mapUtil.getWithFallbackKey(
				HashMapBuilder.put(
					LocaleUtil.getDefault(), "US translated"
				).put(
					LocaleUtil.CHINA, "CN translated"
				).put(
					LocaleUtil.FRANCE, StringPool.BLANK
				).getMap(),
				LocaleUtil.FRANCE, LocaleUtil.getDefault()));
	}

	@Test
	public void testGetWithFallbackKeyExistingTranslation() {
		MapUtil mapUtil = new MapUtil();

		Map<Locale, String> localizedValueMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), "US translated"
		).put(
			LocaleUtil.CHINA, "CN translated"
		).put(
			LocaleUtil.FRANCE, "FR translated"
		).getMap();

		Assert.assertEquals(
			"US translated",
			mapUtil.getWithFallbackKey(
				localizedValueMap, LocaleUtil.getDefault(),
				LocaleUtil.getDefault()));

		Assert.assertEquals(
			"FR translated",
			mapUtil.getWithFallbackKey(
				localizedValueMap, LocaleUtil.FRANCE, LocaleUtil.getDefault()));

		Assert.assertEquals(
			"CN translated",
			mapUtil.getWithFallbackKey(
				localizedValueMap, LocaleUtil.CHINA, LocaleUtil.getDefault()));
	}

	@Test
	public void testGetWithFallbackKeyNull() {
		MapUtil mapUtil = new MapUtil();

		Assert.assertEquals(
			"US translated",
			mapUtil.getWithFallbackKey(
				HashMapBuilder.put(
					LocaleUtil.getDefault(), "US translated"
				).put(
					LocaleUtil.CHINA, "CN translated"
				).put(
					LocaleUtil.FRANCE, "FR translated"
				).getMap(),
				null, LocaleUtil.getDefault()));
	}

}