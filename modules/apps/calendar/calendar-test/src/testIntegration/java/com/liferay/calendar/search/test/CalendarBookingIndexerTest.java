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
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.HitsAssert;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarBookingIndexerTest extends BaseCalendarIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setGroup(calendarFixture.addGroup());
		setIndexerClass(CalendarBooking.class);
		setUser(calendarFixture.addUser());
	}

	@Test
	public void testBasicSearch() throws Exception {
		String title = RandomTestUtil.randomString();

		addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, title);
				}
			});

		calendarSearchFixture.searchOnlyOne(title, LocaleUtil.US);
	}

	@Test
	public void testMultiLocale() throws Exception {
		String originalTitle = "entity title";
		String translatedTitle = "entitas neve";

		addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalTitle);
					put(LocaleUtil.HUNGARY, translatedTitle);
				}
			});

		calendarSearchFixture.searchOnlyOne("nev", LocaleUtil.HUNGARY);
	}

	@Test
	public void testTrash() throws Exception {
		String title = RandomTestUtil.randomString();

		CalendarBooking calendarBooking = addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, title);
				}
			});

		calendarBookingLocalService.moveCalendarBookingToTrash(
			TestPropsValues.getUserId(), calendarBooking);

		HitsAssert.assertNoHits(
			calendarSearchFixture.search(
				calendarSearchFixture.getSearchContext(title, LocaleUtil.US)));

		HitsAssert.assertOnlyOne(
			calendarSearchFixture.search(
				withStatusInTrash(
					calendarSearchFixture.getSearchContext(
						title, LocaleUtil.US))));
	}

	protected CalendarBooking addCalendarBooking(
		LocalizedValuesMap titleLocalizedValuesMap) {

		try {
			ServiceContext serviceContext = calendarFixture.getServiceContext();

			Calendar calendar = calendarFixture.addCalendar(
				new LocalizedValuesMap() {
					{
						put(
							LocaleUtil.getSiteDefault(),
							RandomTestUtil.randomString());
					}
				},
				new LocalizedValuesMap(), serviceContext);

			return calendarFixture.addCalendarBooking(
				titleLocalizedValuesMap, calendar, serviceContext);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	protected SearchContext withStatusInTrash(SearchContext searchContext) {
		searchContext.setAttribute(
			Field.STATUS, new int[] {WorkflowConstants.STATUS_IN_TRASH});

		return searchContext;
	}

}