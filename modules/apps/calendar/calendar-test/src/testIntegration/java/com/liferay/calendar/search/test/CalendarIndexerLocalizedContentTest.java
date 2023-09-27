/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;

import java.util.Arrays;
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
public class CalendarIndexerLocalizedContentTest
	extends BaseCalendarIndexerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setIndexerClass(Calendar.class);
	}

	@Test
	public void testJapaneseName() throws Exception {
		String originalName = "entity name";
		String japaneseName = "新規作成";

		String description = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		addCalendar(
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

		Map<String, String> nameMap = HashMapBuilder.put(
			"name", originalName
		).put(
			"name_en_US", originalName
		).put(
			"name_ja_JP", japaneseName
		).build();

		Map<String, String> descriptionMap = HashMapBuilder.put(
			"description", description
		).put(
			"description_en_US", description
		).put(
			"description_ja_JP", description
		).build();

		for (String keyword : Arrays.asList("新規", "作成", "新", "作")) {
			Document document = searchOnlyOne(keyword, LocaleUtil.JAPAN);

			FieldValuesAssert.assertFieldValues(
				nameMap, "name", document, keyword);

			FieldValuesAssert.assertFieldValues(
				descriptionMap, "description", document, keyword);
		}
	}

	@Test
	public void testJapaneseNameFullWordOnly() throws Exception {
		String full = "新規作成";

		String originalName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		String description = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		for (String name : Arrays.asList(full, "新大阪", "作戦大成功")) {
			addCalendar(
				new LocalizedValuesMap() {
					{
						put(LocaleUtil.US, originalName);
						put(LocaleUtil.JAPAN, name);
					}
				},
				new LocalizedValuesMap() {
					{
						put(LocaleUtil.US, description);
						put(LocaleUtil.JAPAN, description);
					}
				});
		}

		Map<String, String> nameMap = HashMapBuilder.put(
			"name", originalName
		).put(
			"name_en_US", originalName
		).put(
			"name_ja_JP", full
		).build();

		for (String keyword : Arrays.asList("新規", "作成")) {
			Document document = searchOnlyOne(keyword, LocaleUtil.JAPAN);

			FieldValuesAssert.assertFieldValues(
				nameMap, "name", document, keyword);
		}
	}

	protected Calendar addCalendar(
		LocalizedValuesMap nameMap, LocalizedValuesMap descriptionMap) {

		try {
			return addCalendar(nameMap, descriptionMap, getServiceContext());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

}