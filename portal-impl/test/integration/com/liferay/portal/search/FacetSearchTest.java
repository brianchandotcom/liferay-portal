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

package com.liferay.portal.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.FacetedSearcher;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.config.FacetConfigurationUtil;
import com.liferay.portal.kernel.search.facet.util.FacetFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;

import java.text.Format;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class FacetSearchTest {

	@Before
	public void setUp() throws Exception {
		int initialUsersCount;

		_facetConfiguration = StringUtil.read(
			getClass().getClassLoader(),
			"com/liferay/portal/search/dependencies/facet-configurations.json",
			true);

		do {
			_randomLastName = RandomTestUtil.randomString(10);

			initialUsersCount = searchCount(null, null, -1);
		}
		while (initialUsersCount > 0);

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MINUTE, -1);

		addUsers(_USER_COUNT_LAST_24_HRS, calendar.getTime());

		calendar.add(Calendar.DATE, -3);

		addUsers(_USER_COUNT_LAST_WEEK, calendar.getTime());

		calendar.add(Calendar.MONTH, -1);
		calendar.add(Calendar.DATE, 7);

		addUsers(_USER_COUNT_LAST_MONTH, calendar.getTime());

		calendar.add(Calendar.MONTH, 2);
		calendar.add(Calendar.YEAR, -1);

		addUsers(_USER_COUNT_LAST_YEAR, calendar.getTime());

		calendar.add(Calendar.MONTH, 5);
		calendar.add(Calendar.YEAR, -2);

		addUsers(_USER_COUNT_LAST_TWO_YEAR, calendar.getTime());

		_dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);
	}

	@After
	public void tearDown() throws Exception {
		for (User user : _users) {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testSearchLast24Hrs() throws Exception {
		Calendar calendar = Calendar.getInstance();

		searchCount(
			CalendarUtil.getGTDate(calendar), CalendarUtil.getLTDate(calendar),
			_USER_COUNT_LAST_24_HRS);
	}

	@Test
	public void testSearchLastMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();

		Date endDate = CalendarUtil.getLTDate(calendar);

		calendar.add(Calendar.MONTH, -1);

		searchCount(
			CalendarUtil.getGTDate(calendar), endDate,
			_USER_COUNT_LAST_24_HRS + _USER_COUNT_LAST_WEEK +
				_USER_COUNT_LAST_MONTH);
	}

	@Test
	public void testSearchLastTwoYears() throws Exception {
		Calendar calendar = Calendar.getInstance();

		Date endDate = CalendarUtil.getLTDate(calendar);

		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);

		calendar.add(Calendar.YEAR, -2);

		searchCount(
			CalendarUtil.getGTDate(calendar), endDate,
			_USER_COUNT_LAST_24_HRS + _USER_COUNT_LAST_WEEK +
				_USER_COUNT_LAST_MONTH + _USER_COUNT_LAST_YEAR +
				_USER_COUNT_LAST_TWO_YEAR);
	}

	@Test
	public void testSearchLastWeek() throws Exception {
		Calendar calendar = Calendar.getInstance();

		Date endDate = CalendarUtil.getLTDate(calendar);

		calendar.add(Calendar.WEEK_OF_YEAR, -1);

		searchCount(
			CalendarUtil.getGTDate(calendar), endDate,
			_USER_COUNT_LAST_24_HRS + _USER_COUNT_LAST_WEEK);
	}

	@Test
	public void testSearchLastYear() throws Exception {
		Calendar calendar = Calendar.getInstance();

		Date endDate = CalendarUtil.getLTDate(calendar);

		calendar.add(Calendar.YEAR, -1);

		searchCount(
			CalendarUtil.getGTDate(calendar), endDate,
			_USER_COUNT_LAST_24_HRS + _USER_COUNT_LAST_WEEK +
				_USER_COUNT_LAST_MONTH + _USER_COUNT_LAST_YEAR);
	}

	@Test
	public void testSearchUserSummaryFields() throws Exception {
		SearchContext searchContext = createSearchContext();

		searchContext.setAttribute("entryClassName", User.class.getName());

		Indexer indexer = FacetedSearcher.getInstance();

		Hits hits = indexer.search(searchContext);

		for (Document doc : hits.getDocs()) {
			Assert.assertNotNull(doc.get("firstName"));
			Assert.assertNotNull(doc.get("middleName"));
			Assert.assertNotNull(doc.get("lastName"));
		}
	}

	protected void addUsers(int count, Date modifiedDate) throws Exception {
		for (int i = 0; i < count; i ++) {
			User user = UserTestUtil.addUser(
				RandomTestUtil.randomString(), false,
				RandomTestUtil.randomString(), _randomLastName,
				new long[]{TestPropsValues.getGroupId()});

			user.setModifiedDate(modifiedDate);

			user = UserLocalServiceUtil.updateUser(user);

			_users.add(user);
		}
	}

	protected SearchContext createSearchContext() throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setKeywords(_randomLastName);
		searchContext.setStart(QueryUtil.ALL_POS);

		Facet assetEntriesFacet = new AssetEntriesFacet(searchContext);

		assetEntriesFacet.setStatic(true);

		searchContext.addFacet(assetEntriesFacet);

		Facet scopeFacet = new ScopeFacet(searchContext);

		scopeFacet.setStatic(true);

		searchContext.addFacet(scopeFacet);

		List<FacetConfiguration> facetConfigurations =
			FacetConfigurationUtil.load(_facetConfiguration);

		for (FacetConfiguration facetConfiguration : facetConfigurations) {
			Facet facet = FacetFactoryUtil.create(
				searchContext, facetConfiguration);

			if (facet == null) {
				continue;
			}

			searchContext.addFacet(facet);
		}

		return searchContext;
	}

	protected int searchCount(Date startDate, Date endDate, int expectedCount)
		throws Exception {

		SearchContext searchContext = createSearchContext();

		searchContext.setAttribute("entryClassName", User.class.getName());

		if ((startDate != null) && (endDate != null)) {
			searchContext.setAttribute(
				"modified", "[" + _dateFormat.format(startDate) + " TO " +
					_dateFormat.format(endDate) + "]");
		}

		Indexer indexer = FacetedSearcher.getInstance();

		Hits hits = indexer.search(searchContext);

		if (expectedCount > 0) {
			Assert.assertEquals(expectedCount, hits.getLength());
		}

		return hits.getLength();
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";

	private static final int _USER_COUNT_LAST_24_HRS = 2;

	private static final int _USER_COUNT_LAST_MONTH = 2;

	private static final int _USER_COUNT_LAST_TWO_YEAR = 2;

	private static final int _USER_COUNT_LAST_WEEK = 2;

	private static final int _USER_COUNT_LAST_YEAR = 2;

	private Format _dateFormat;
	private String _facetConfiguration;
	private String _randomLastName;
	private List<User> _users = new ArrayList<User>();

}