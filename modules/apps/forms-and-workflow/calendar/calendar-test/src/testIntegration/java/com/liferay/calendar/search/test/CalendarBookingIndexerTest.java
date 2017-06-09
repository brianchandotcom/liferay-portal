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
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.search.CalendarBookingIndexer;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
@Sync
public class CalendarBookingIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		setUpSearchContext(_group, _user);

		_indexer = new CalendarBookingIndexer();
	}

	@Test
	public void testIndexedFields() throws Exception {
		setUpSearchContext(_group, TestPropsValues.getUser());

		String originalTitle = "entity title";
		String translatedTitle = "entitas neve";

		addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalTitle);
					put(LocaleUtil.HUNGARY, translatedTitle);
				}
			});

		String searchTerm = "nev";

		assertSearchHitsLength(searchTerm, 1, LocaleUtil.HUNGARY);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		setUpSearchContext(_group, TestPropsValues.getUser());

		String title = "新規作成";

		addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.JAPAN, title);
				}
			});

		String word1 = "新規";
		String word2 = "作成";
		String prefix1 = "新";
		String prefix2 = "作";

		Stream<String> searchTerms = Stream.of(word1, word2, prefix1, prefix2);

		searchTerms.forEach(
			searchTerm -> {
				assertSearchHitsLength(searchTerm, 1, LocaleUtil.JAPAN);
			});
	}

	@Test
	public void testJapaneseTitleFullWordOnly() throws Exception {
		setUpSearchContext(_group, TestPropsValues.getUser());

		String full = "新規作成";
		String partial1 = "新大阪";
		String partial2 = "作戦大成功";

		Stream<String> titles = Stream.of(full, partial1, partial2);

		titles.forEach(
			title -> {
				addCalendarBooking(
					new LocalizedValuesMap() {
						{
							put(LocaleUtil.JAPAN, title);
						}
					});
			});

		String word1 = "新規";
		String word2 = "作成";

		Stream<String> searchTerms = Stream.of(word1, word2);

		searchTerms.forEach(
			searchTerm -> {
				assertSearchHitsLength(searchTerm, 1, LocaleUtil.JAPAN);
			});
	}

	@Test
	public void testSearch() throws Exception {
		setUpSearchContext(_group, TestPropsValues.getUser());

		String title = RandomTestUtil.randomString();

		addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, title);
				}
			});

		assertSearchHitsLength(title, 1, LocaleUtil.US);
	}

	@Test
	public void testSearchNotAdmin() throws Exception {
		setUpSearchContext(_group, _user);

		String title = RandomTestUtil.randomString();

		addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, title);
				}
			});

		assertSearchHitsLength(title, 1, LocaleUtil.US);
	}

	protected static SearchContext getSearchContext(Group group, User user)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setUserId(user.getUserId());

		return searchContext;
	}

	protected void addCalendarBooking(LocalizedValuesMap titleMap) {
		try {
			ServiceContext serviceContext = new ServiceContext();

			CalendarResource calendarResource =
				CalendarResourceUtil.getGroupCalendarResource(
					_group.getGroupId(), serviceContext);

			Calendar calendar = CalendarLocalServiceUtil.addCalendar(
				_user.getUserId(), _group.getGroupId(),
				calendarResource.getCalendarResourceId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), StringPool.UTC,
				RandomTestUtil.randomInt(0, 255), false, false, false,
				serviceContext);

			long startTime = DateUtil.newTime() + RandomTestUtil.randomInt();

			long endTime = startTime + Time.HOUR;

			HashMap<Locale, String> hashMap = new HashMap<>();

			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT, 0,
				titleMap.getValues(), hashMap, null, startTime, endTime, false,
				null, 0, "email", 0, "email", serviceContext);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	protected void assertSearchHitsLength(
		final String keywords, final int expectedLength, Locale locale) {

		Locale currentLocale = _searchContext.getLocale();

		try {
			_searchContext.setLocale(locale);
			_searchContext.setKeywords(keywords);

			Hits hits = _indexer.search(_searchContext);

			Assert.assertEquals(
				hits.toString(), expectedLength, hits.getLength());
		}
		catch (SearchException se) {
			throw new RuntimeException(se);
		}
		finally {
			_searchContext.setLocale(currentLocale);
		}
	}

	protected void setUpSearchContext(Group group, User user) throws Exception {
		_searchContext = getSearchContext(_group, _user);
	}

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<?> _indexer;
	private SearchContext _searchContext;

	@DeleteAfterTestRun
	private User _user;

}