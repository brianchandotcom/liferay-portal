/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.search.test;

import com.liferay.calendar.constants.CalendarBookingConstants;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.test.util.HitsAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Wade Cao
 */
public abstract class BaseCalendarIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
		user = UserTestUtil.addUser();

		indexedFieldsFixture = createIndexedFieldsFixture();

		UserTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Calendar addCalendar(
			LocalizedValuesMap nameLocalizedValuesMap,
			LocalizedValuesMap descriptionLocalizedValuesMap,
			ServiceContext serviceContext)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				group.getGroupId(), serviceContext);

		return calendarLocalService.addCalendar(
			serviceContext.getUserId(), group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			nameLocalizedValuesMap.getValues(),
			descriptionLocalizedValuesMap.getValues(), StringPool.UTC,
			RandomTestUtil.randomInt(0, 255), false, false, false,
			serviceContext);
	}

	protected CalendarBooking addCalendarBooking(
			LocalizedValuesMap titleLocalizedValuesMap, Calendar calendar,
			ServiceContext serviceContext)
		throws PortalException {

		long startTime = DateUtil.newTime() + RandomTestUtil.randomInt();

		long endTime = startTime + Time.HOUR;

		return calendarBookingLocalService.addCalendarBooking(
			serviceContext.getUserId(), calendar.getCalendarId(), new long[0],
			CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT, 0,
			titleLocalizedValuesMap.getValues(), Collections.emptyMap(), null,
			startTime, endTime, false, null, 0, "email", 0, "email",
			serviceContext);
	}

	protected IndexedFieldsFixture createIndexedFieldsFixture() {
		return new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected SearchContext getSearchContext(String keywords, Locale locale) {
		SearchContext searchContext = new SearchContext();

		try {
			searchContext.setCompanyId(TestPropsValues.getCompanyId());
			searchContext.setUserId(TestPropsValues.getUserId());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setKeywords(keywords);
		searchContext.setLocale(Objects.requireNonNull(locale));

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(StringPool.STAR);

		return searchContext;
	}

	protected ServiceContext getServiceContext() throws PortalException {
		return ServiceContextTestUtil.getServiceContext(
			group.getGroupId(), TestPropsValues.getUserId());
	}

	protected Hits search(SearchContext searchContext) {
		try {
			return _indexer.search(searchContext);
		}
		catch (SearchException searchException) {
			throw new RuntimeException(searchException);
		}
	}

	protected Document searchOnlyOne(String keywords, Locale locale) {
		return HitsAssert.assertOnlyOne(
			search(getSearchContext(keywords, locale)));
	}

	protected SearchResponse searchOnlyOneSearchResponse(
		String keywords, Locale locale) {

		SearchContext searchContext = getSearchContext(keywords, locale);

		searchRequestBuilderFactory.builder(
			searchContext
		).fetchSourceIncludes(
			new String[] {"*_sortable"}
		).build();

		search(searchContext);

		SearchResponse searchResponse =
			(SearchResponse)searchContext.getAttribute("search.response");

		HitsAssert.assertOnlyOne(searchResponse.getSearchHits());

		return searchResponse;
	}

	protected void setIndexerClass(Class<?> clazz) {
		_indexer = indexerRegistry.getIndexer(clazz);
	}

	@Inject
	protected CalendarBookingLocalService calendarBookingLocalService;

	@Inject
	protected CalendarLocalService calendarLocalService;

	protected Group group;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected IndexerRegistry indexerRegistry;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	protected User user;

	private Indexer<?> _indexer;

}