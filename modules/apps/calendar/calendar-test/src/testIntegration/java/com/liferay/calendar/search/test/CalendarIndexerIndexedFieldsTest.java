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
import com.liferay.calendar.model.CalendarResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
@Sync
public class CalendarIndexerIndexedFieldsTest
	extends BaseCalendarIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setGroup(calendarFixture.addGroup());
		setIndexerClass(Calendar.class);
	}

	@Test
	public void testIndexedFields() throws Exception {
		String originalName = "entity title";
		String translatedName = "entitas neve";

		String originalDescription = "calendar description";
		String translatedDescription = "descripción del calendario";

		Calendar calendar = addCalendar(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalName);
					put(LocaleUtil.HUNGARY, translatedName);
				}
			},
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalDescription);
					put(LocaleUtil.HUNGARY, translatedDescription);
				}
			});

		Map<String, String> map = new HashMap<>();

		populateExpectedFieldValues(calendar, map);

		map.put(Field.DESCRIPTION, originalDescription);
		map.put(Field.DESCRIPTION + "_en_US", originalDescription);
		map.put(Field.DESCRIPTION + "_hu_HU", translatedDescription);
		map.put(Field.NAME, originalName);
		map.put(Field.NAME + "_en_US", originalName);
		map.put(Field.NAME + "_hu_HU", translatedName);

		String keywords = "nev";

		Document document = calendarSearchFixture.searchOnlyOne(
			keywords, LocaleUtil.HUNGARY);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(map, document, keywords);
	}

	@Test
	public void testIndexedFieldsMissingDescription() throws Exception {
		String originalName = "entity title";
		String translatedName = "título da entidade";

		Calendar calendar = addCalendar(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalName);
					put(LocaleUtil.BRAZIL, translatedName);
				}
			},
			new LocalizedValuesMap());

		Map<String, String> map = new HashMap<>();

		populateExpectedFieldValues(calendar, map);

		map.put(Field.NAME, originalName);
		map.put(Field.NAME + "_en_US", originalName);
		map.put(Field.NAME + "_pt_BR", translatedName);

		String keywords = translatedName;

		Document document = calendarSearchFixture.searchOnlyOne(
			keywords, LocaleUtil.BRAZIL);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(map, document, keywords);
	}

	protected Calendar addCalendar(
			LocalizedValuesMap nameMap, LocalizedValuesMap descriptionMap)
		throws PortalException {

		return calendarFixture.addCalendar(
			nameMap, descriptionMap, calendarFixture.getServiceContext());
	}

	protected void populateCalendarDate(
		String name, Date date, Map<String, String> map,
		DateFormat dateFormat) {

		map.put(name, dateFormat.format(date));
		map.put(name.concat("_sortable"), String.valueOf(date.getTime()));
	}

	protected void populateCalendarResource(
		CalendarResource calendarResource, Calendar calendar,
		Map<String, String> map) {

		map.put(
			"resourceName",
			StringUtil.toLowerCase(
				calendarResource.getName(LocaleUtil.US, true)));

		Map<Locale, String> nameMap = calendarResource.getNameMap();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			map.put(
				"resourceName_" + entry.getKey(),
				StringUtil.toLowerCase(entry.getValue()));
		}
	}

	protected void populateExpectedFieldValues(
			Calendar calendar, Map<String, String> map)
		throws Exception {

		map.put(Field.COMPANY_ID, String.valueOf(calendar.getCompanyId()));
		map.put(Field.DEFAULT_LANGUAGE_ID, calendar.getDefaultLanguageId());
		map.put(Field.ENTRY_CLASS_NAME, calendar.getModelClassName());
		map.put(Field.ENTRY_CLASS_PK, String.valueOf(calendar.getCalendarId()));
		map.put(Field.GROUP_ID, String.valueOf(calendar.getGroupId()));
		map.put(Field.SCOPE_GROUP_ID, String.valueOf(calendar.getGroupId()));
		map.put(Field.STAGING_GROUP, "false");
		map.put(Field.USER_ID, String.valueOf(calendar.getUserId()));
		map.put(
			Field.USER_NAME, StringUtil.toLowerCase(calendar.getUserName()));
		map.put("calendarId", String.valueOf(calendar.getCalendarId()));

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		populateCalendarDate(
			Field.CREATE_DATE, calendar.getCreateDate(), map, dateFormat);
		populateCalendarDate(
			Field.MODIFIED_DATE, calendar.getModifiedDate(), map, dateFormat);

		populateCalendarResource(calendar.getCalendarResource(), calendar, map);

		indexedFieldsFixture.populateRoleIdFields(
			calendar.getCompanyId(), calendar.getModelClassName(),
			calendar.getCalendarId(), calendar.getGroupId(), null, map);

		indexedFieldsFixture.populateUID(
			calendar.getModelClassName(), calendar.getCalendarId(), map);
	}

}