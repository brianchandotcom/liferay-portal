/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testGetWithFallback() {
		MapUtil mapUtil = new MapUtil();

		Map<Locale, String> localizedValueMap = HashMapBuilder.put(
			LocaleUtil.BRAZIL, "lápis"
		).put(
			LocaleUtil.CHINA, "鉛筆"
		).put(
			LocaleUtil.SPAIN, StringPool.BLANK
		).put(
			LocaleUtil.getDefault(), "pencil"
		).getMap();

		Assert.assertEquals(
			"lápis",
			mapUtil.getWithFallbackKey(
				localizedValueMap, LocaleUtil.BRAZIL, LocaleUtil.getDefault()));
		Assert.assertEquals(
			"鉛筆",
			mapUtil.getWithFallbackKey(
				localizedValueMap, LocaleUtil.CHINA, LocaleUtil.getDefault()));
		Assert.assertEquals(
			"pencil",
			mapUtil.getWithFallbackKey(
				localizedValueMap, LocaleUtil.SPAIN, LocaleUtil.getDefault()));
		Assert.assertEquals(
			"pencil",
			mapUtil.getWithFallbackKey(
				localizedValueMap, LocaleUtil.getDefault(),
				LocaleUtil.getDefault()));
		Assert.assertEquals(
			"pencil",
			mapUtil.getWithFallbackKey(
				localizedValueMap, null, LocaleUtil.getDefault()));
	}

}