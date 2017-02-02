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

package com.liferay.portal.service;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class LayoutFriendlyURLServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testLocalizedSiteAddLayoutFriendlyURLs() throws Exception {
		List<Locale> availableLocales = Arrays.asList(
			LocaleUtil.US, LocaleUtil.SPAIN);

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, LocaleUtil.SPAIN);

		Map<Locale, String> nameMap = new HashMap<>();

		String name = RandomTestUtil.randomString();

		nameMap.put(LocaleUtil.GERMANY, name);
		nameMap.put(LocaleUtil.US, name);
		nameMap.put(LocaleUtil.SPAIN, name);

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.GERMANY, "/germanurl");
		friendlyURLMap.put(LocaleUtil.SPAIN, "/spanishurl");
		friendlyURLMap.put(LocaleUtil.US, "/englishurl");

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), false, nameMap, friendlyURLMap);

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

		Assert.assertEquals(
			layoutFriendlyURLs.toString(), availableLocales.size(),
			layoutFriendlyURLs.size());

		String[] availableLanguageIds = LocaleUtil.toLanguageIds(
			availableLocales);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			Assert.assertTrue(
				ArrayUtil.contains(
					availableLanguageIds, layoutFriendlyURL.getLanguageId()));
		}
	}

	@Test
	public void testLocalizedSiteFetchLayoutFriendlyURL() throws Exception {
		List<Locale> availableLocales = Arrays.asList(
			LocaleUtil.US, LocaleUtil.SPAIN);

		Locale defaultLocale = LocaleUtil.SPAIN;

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, defaultLocale);

		Map<Locale, String> nameMap = new HashMap<>();

		String name = RandomTestUtil.randomString();

		nameMap.put(LocaleUtil.SPAIN, name);
		nameMap.put(LocaleUtil.US, name);

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.SPAIN, "/spanishurl");
		friendlyURLMap.put(LocaleUtil.US, "/englishurl");

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), false, nameMap, friendlyURLMap);

		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(defaultLocale);

			LayoutFriendlyURL layoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.fetchLayoutFriendlyURL(
					layout.getPlid(),
					LocaleUtil.toLanguageId(LocaleUtil.GERMANY), true);

			Assert.assertEquals(
				"/spanishurl", layoutFriendlyURL.getFriendlyURL());
			Assert.assertEquals(
				LocaleUtil.toLanguageId(defaultLocale),
				layoutFriendlyURL.getLanguageId());
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}