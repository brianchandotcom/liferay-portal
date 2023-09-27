/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.constants.CalendarBookingConstants;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.search.facet.user.UserFacetFactory;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class CalendarFacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testBorrowedPermissions() throws Exception {
		Group group = userSearchFixture.addGroup();

		User user1 = addUser(group);

		String title = RandomTestUtil.randomString();

		addCalendarBooking(user1, group, title);

		User user2 = addUser(group);

		addCalendarBooking(user2, group, title);

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user2));

		SearchContext searchContext = getSearchContext(title);

		searchContext.setEntryClassNames(
			new String[] {CalendarBooking.class.getName()});
		searchContext.setUserId(user2.getUserId());

		Facet facet = createUserFacet(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits,
			Collections.singletonMap(String.valueOf(user2.getUserId()), 1));
	}

	protected void addCalendarBooking(User user, Group group, String title)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				group.getGroupId(), serviceContext);

		Calendar calendar = calendarLocalService.addCalendar(
			user.getUserId(), group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), StringPool.UTC,
			RandomTestUtil.randomInt(0, 255), false, false, false,
			serviceContext);

		long startTime = DateUtil.newTime() + RandomTestUtil.randomInt();

		long endTime = startTime + Time.HOUR;

		HashMap<Locale, String> hashMap = new HashMap<>();

		calendarBookingLocalService.addCalendarBooking(
			user.getUserId(), calendar.getCalendarId(), new long[0],
			CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT, 0,
			Collections.singletonMap(LocaleUtil.US, title), hashMap, null,
			startTime, endTime, false, null, 0, "email", 0, "email",
			serviceContext);
	}

	protected Facet createUserFacet(SearchContext searchContext) {
		return userFacetFactory.newInstance(searchContext);
	}

	@Inject
	protected static CalendarBookingLocalService calendarBookingLocalService;

	@Inject
	protected static CalendarLocalService calendarLocalService;

	@Inject
	protected static PermissionCheckerFactory permissionCheckerFactory;

	@Inject
	protected static UserFacetFactory userFacetFactory;

	private PermissionChecker _originalPermissionChecker;

}