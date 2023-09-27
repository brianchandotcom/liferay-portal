/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class CountrySearchKeywordsTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		CountrySearchKeywords countrySearchKeywords = new CountrySearchKeywords(
			"us", null);

		Assert.assertEquals(
			String.valueOf(
				JSONUtil.put(
					"countryCode", "us"
				).put(
					"countryName", "United States"
				).put(
					"keywords", JSONFactoryUtil.createJSONArray()
				)),
			String.valueOf(countrySearchKeywords.toJSONObject(LocaleUtil.US)));
	}

	@Test
	public void testToJSONObjectWithSearchKeywords() {
		CountrySearchKeywords countrySearchKeywords = new CountrySearchKeywords(
			"us",
			Arrays.asList(
				new SearchKeyword("liferay", 1, 3600, 2882L),
				new SearchKeyword("liferay inc", 1, 755, 855L),
				new SearchKeyword("liferay portal", 1, 556, 850L),
				new SearchKeyword("what is liferay", 1, 390, 312L),
				new SearchKeyword("liferay india", 1, 390, 312L),
				new SearchKeyword(
					"liferay development services", 1, 390, 310L)));

		Assert.assertEquals(
			JSONUtil.put(
				"countryCode", "us"
			).put(
				"countryName", "United States"
			).put(
				"keywords",
				JSONUtil.putAll(
					JSONUtil.put(
						"keyword", "liferay"
					).put(
						"position", 1
					).put(
						"searchVolume", 3600
					).put(
						"traffic", 2882
					),
					JSONUtil.put(
						"keyword", "liferay inc"
					).put(
						"position", 1
					).put(
						"searchVolume", 755
					).put(
						"traffic", 855
					),
					JSONUtil.put(
						"keyword", "liferay portal"
					).put(
						"position", 1
					).put(
						"searchVolume", 556
					).put(
						"traffic", 850
					),
					JSONUtil.put(
						"keyword", "what is liferay"
					).put(
						"position", 1
					).put(
						"searchVolume", 390
					).put(
						"traffic", 312
					),
					JSONUtil.put(
						"keyword", "liferay india"
					).put(
						"position", 1
					).put(
						"searchVolume", 390
					).put(
						"traffic", 312
					))
			).toString(),
			String.valueOf(countrySearchKeywords.toJSONObject(LocaleUtil.US)));
	}

}