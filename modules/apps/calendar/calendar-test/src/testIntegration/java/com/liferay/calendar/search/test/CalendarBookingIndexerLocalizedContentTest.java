/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
@Sync
public class CalendarBookingIndexerLocalizedContentTest
	extends BaseCalendarIndexerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setIndexerClass(CalendarBooking.class);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		String originalName = "entity name";
		String japaneseName = "新規作成";

		String description = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalName);
					put(LocaleUtil.JAPAN, japaneseName);
				}
			},
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalName);
					put(LocaleUtil.JAPAN, japaneseName);
				}
			},
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, description);
					put(LocaleUtil.JAPAN, description);
				}
			});

		Map<String, String> titleMap = HashMapBuilder.put(
			"title_en_US", originalName
		).put(
			"title_ja_JP", japaneseName
		).build();

		for (String keyword : Arrays.asList("新規", "作成", "新", "作")) {
			assertFieldValues("title", LocaleUtil.JAPAN, titleMap, keyword);
		}
	}

	@Test
	public void testJapaneseTitleFullWordOnly() throws Exception {
		String description = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		for (String title : Arrays.asList("新規作成", "新大阪", "作戦大成功")) {
			addCalendarBooking(
				new LocalizedValuesMap() {
					{
						put(LocaleUtil.JAPAN, title);
					}
				},
				new LocalizedValuesMap() {
					{
						put(LocaleUtil.US, description);
						put(LocaleUtil.HUNGARY, description);
					}
				},
				new LocalizedValuesMap() {
					{
						put(LocaleUtil.US, description);
						put(LocaleUtil.HUNGARY, description);
					}
				});
		}

		Map<String, String> titleMap = HashMapBuilder.put(
			"title_ja_JP", "新規作成"
		).build();

		for (String keyword : Arrays.asList("新規", "作成")) {
			assertFieldValues("title", LocaleUtil.JAPAN, titleMap, keyword);
		}
	}

	protected CalendarBooking addCalendarBooking(
		LocalizedValuesMap titleLocalizedValuesMap,
		LocalizedValuesMap nameLocalizedValuesMap,
		LocalizedValuesMap descriptionLocalizedValuesMap) {

		try {
			ServiceContext serviceContext = getServiceContext();

			Calendar calendar = addCalendar(
				nameLocalizedValuesMap, descriptionLocalizedValuesMap,
				serviceContext);

			return addCalendarBooking(
				titleLocalizedValuesMap, calendar, serviceContext);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> titleStrings,
		String searchTerm) {

		Document document = searchOnlyOne(searchTerm, locale);

		FieldValuesAssert.assertFieldValues(
			titleStrings, prefix, document, searchTerm);
	}

}