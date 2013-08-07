/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class LayoutFriendlyURLServiceTest {

	@Test
	public void testLocalizedSiteAddLayoutFriendlyURLs() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Locale enLocale = Locale.US;
		Locale esLocale = new Locale("es", "ES");
		Locale deLocale = new Locale("de", "DE");

		Locale[] availableLocales = new Locale[] {enLocale, esLocale};

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), availableLocales, esLocale);

		String name = ServiceTestUtil.randomString();

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(enLocale, name);
		nameMap.put(esLocale, name);
		nameMap.put(deLocale, name);

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(enLocale, "/englishurl");
		friendlyURLMap.put(esLocale, "/spanishurl");
		friendlyURLMap.put(deLocale, "/germanurl");

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), false, nameMap, friendlyURLMap);

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

		Assert.assertEquals(availableLocales.length, layoutFriendlyURLs.size());

		String[] availableLanguageIds = LocaleUtil.toLanguageIds(
			availableLocales);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			if (!ArrayUtil.contains(
					availableLanguageIds, layoutFriendlyURL.getLanguageId())) {

				Assert.fail();
			}
		}
	}

	@Test
	public void testLocalizedSiteFetchLayoutFriendlyURL() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Locale enLocale = Locale.US;
		Locale esLocale = new Locale("es", "ES");
		Locale deLocale = new Locale("de", "DE");

		Locale[] availableLocales = new Locale[] {enLocale, esLocale};

		Locale defaultLocale = esLocale;

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), availableLocales, defaultLocale);

		String name = ServiceTestUtil.randomString();

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(enLocale, name);
		nameMap.put(esLocale, name);

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(enLocale, "/englishurl");
		friendlyURLMap.put(esLocale, "/spanishurl");

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), false, nameMap, friendlyURLMap);

		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(defaultLocale);

			LayoutFriendlyURL layoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.fetchLayoutFriendlyURL(
					layout.getPlid(), LocaleUtil.toLanguageId(deLocale), true);

			Assert.assertEquals("/spanishurl", layoutFriendlyURL.getFriendlyURL());
			Assert.assertEquals(
				LocaleUtil.toLanguageId(defaultLocale),
				layoutFriendlyURL.getLanguageId());
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
	}

}
