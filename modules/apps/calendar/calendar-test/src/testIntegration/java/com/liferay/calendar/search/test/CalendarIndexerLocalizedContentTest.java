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

import java.util.Map;
import java.util.stream.Stream;

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

		String word1 = "新規";
		String word2 = "作成";
		String prefix1 = "新";
		String prefix2 = "作";

		Stream.of(
			word1, word2, prefix1, prefix2
		).forEach(
			keywords -> {
				Document document = searchOnlyOne(keywords, LocaleUtil.JAPAN);

				FieldValuesAssert.assertFieldValues(
					nameMap, "name", document, keywords);

				FieldValuesAssert.assertFieldValues(
					descriptionMap, "description", document, keywords);
			}
		);
	}

	@Test
	public void testJapaneseNameFullWordOnly() throws Exception {
		String full = "新規作成";
		String partial1 = "新大阪";
		String partial2 = "作戦大成功";

		String originalName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		String description = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		Stream.of(
			full, partial1, partial2
		).forEach(
			name -> addCalendar(
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
				})
		);

		Map<String, String> nameMap = HashMapBuilder.put(
			"name", originalName
		).put(
			"name_en_US", originalName
		).put(
			"name_ja_JP", full
		).build();

		String word1 = "新規";
		String word2 = "作成";

		Stream.of(
			word1, word2
		).forEach(
			keywords -> {
				Document document = searchOnlyOne(keywords, LocaleUtil.JAPAN);

				FieldValuesAssert.assertFieldValues(
					nameMap, "name", document, keywords);
			}
		);
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