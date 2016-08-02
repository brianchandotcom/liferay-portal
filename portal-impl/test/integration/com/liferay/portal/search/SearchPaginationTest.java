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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
@Sync
public class SearchPaginationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		int initialUsersCount = 0;

		do {
			_randomLastName = RandomTestUtil.randomString(10);

			Hits hits = getHits(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			initialUsersCount = hits.getLength();
		}
		while (initialUsersCount > 0);

		for (int i = 0; i < _USERS_COUNT; i++) {
			User user = UserTestUtil.addUser(
				RandomTestUtil.randomString(
					NumericStringRandomizerBumper.INSTANCE),
				LocaleUtil.getDefault(), RandomTestUtil.randomString(),
				_randomLastName, new long[] {TestPropsValues.getGroupId()});

			_users.add(user);
		}

		Collections.sort(
			_users,
			new Comparator<User>() {

				@Override
				public int compare(User user1, User user2) {
					String screenName1 = user1.getScreenName();
					String screenName2 = user2.getScreenName();

					return screenName1.compareTo(screenName2);
				}

			});
	}

	@Test
	public void testResultsWhenTotalLessThanStartAndDeltaIsBiggerThanTotal()
		throws Exception {

		testResults(10, 20, _USERS_COUNT, 0);
	}

	@Test
	public void testResultsWhenTotalLessThanStartAndDeltaIsOne()
		throws Exception {

		testResults(10, 11, 1, 4);
	}

	@Test
	public void testResultsWhenTotalLessThanStartAndDeltaIsThree()
		throws Exception {

		testResults(10, 13, 2, 3);
	}

	@Test
	public void testSearchWithOneResult() throws Exception {
		Hits hits = getSearchWithOneResult(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(1, hits.getLength());
	}

	@Test
	public void testSearchWithOneResultWhenTotalEqualsStart() throws Exception {
		Hits hits = getSearchWithOneResult(_USERS_COUNT, 2 * _USERS_COUNT);

		Assert.assertEquals(1, hits.getLength());
	}

	@Test
	public void testSearchWithOneResultWhenTotalLessThanStart()
		throws Exception {

		Hits hits = getSearchWithOneResult(1000, 1000 + _USERS_COUNT);

		Assert.assertEquals(1, hits.getLength());
	}

	@Test
	public void testSearchWithoutResults() throws Exception {
		Hits hits = getSearchWithoutResults(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(0, hits.getLength());
	}

	@Test
	public void testSearchWithoutResultsWhenTotalEqualsStart()
		throws Exception {

		Hits hits = getSearchWithoutResults(_USERS_COUNT, 2 * _USERS_COUNT);

		Assert.assertEquals(0, hits.getLength());
	}

	@Test
	public void testSearchWithoutResultsWhenTotalLessThanStart()
		throws Exception {

		Hits hits = getSearchWithoutResults(1000, 1000 + _USERS_COUNT);

		Assert.assertEquals(0, hits.getLength());
	}

	@Test
	public void testSearchWithoutResultsWhenTotalLessThanStartAndDeltaIsOne()
		throws Exception {

		Hits hits = getSearchWithoutResults(1000, 1001);

		Assert.assertEquals(0, hits.getLength());
		Assert.assertEquals(0, hits.getDocs().length);
	}

	@Test
	public void testSearchWithResults() throws Exception {
		Hits hits = getHits(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(_USERS_COUNT, hits.getLength());
		Assert.assertEquals(5, hits.getDocs().length);
	}

	@Test
	public void testSearchWithResultsWhenTotalEqualsStart() throws Exception {
		Hits hits = getHits(_USERS_COUNT, 2 * _USERS_COUNT);

		Assert.assertEquals(_USERS_COUNT, hits.getLength());
		Assert.assertEquals(_USERS_COUNT, hits.getDocs().length);
	}

	@Test
	public void testSearchWithResultsWhenTotalLessThanStart() throws Exception {
		Hits hits = getHits(1000, 1000 + _USERS_COUNT);

		Assert.assertEquals(_USERS_COUNT, hits.getLength());
	}

	@Test
	public void testSearchWithResultsWhenTotalLessThanStartAndDeltaIsOne()
		throws Exception {

		Hits hits = getHits(1000, 1001);

		Assert.assertEquals(_USERS_COUNT, hits.getLength());
		Assert.assertEquals(1, hits.getDocs().length);
	}

	protected Hits getHits(int start, int end) throws Exception {
		return getHits(_randomLastName, start, end);
	}

	protected Hits getHits(String keyword, int start, int end)
		throws Exception {

		Indexer<User> indexer = IndexerRegistryUtil.getIndexer(User.class);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {TestPropsValues.getGroupId()});
		searchContext.setKeywords(keyword);

		QueryConfig queryConfig = new QueryConfig();

		searchContext.setQueryConfig(queryConfig);

		searchContext.setSorts(new Sort("screenName", false));
		searchContext.setStart(start);

		return indexer.search(searchContext);
	}

	protected Hits getSearchWithOneResult(int start, int end) throws Exception {
		User user = _users.get(0);

		return getHits(user.getFirstName(), start, end);
	}

	protected Hits getSearchWithoutResults(int start, int end)
		throws Exception {

		return getHits("invalidKeyword", start, end);
	}

	protected void testResults(
			int start, int end, int expectedTotal,
			int expectedRecalculatedStart)
		throws Exception {

		Hits hits = getHits(start, end);

		Assert.assertEquals(expectedTotal, hits.getDocs().length);

		List<User> returnedUsers = new ArrayList<>();

		for (int i = 0; i < hits.getDocs().length; i++) {
			Document doc = hits.doc(i);

			long userId = GetterUtil.getLong(doc.get(Field.USER_ID));

			returnedUsers.add(UserLocalServiceUtil.getUser(userId));
		}

		StringBundler sb = new StringBundler(13);

		sb.append("{end=");
		sb.append(end);
		sb.append(", expectedRecalculatedStart=");
		sb.append(expectedRecalculatedStart);
		sb.append(", expectedTotal=");
		sb.append(expectedTotal);
		sb.append(", returnedUsers=");
		sb.append(returnedUsers);
		sb.append(", start=");
		sb.append(start);
		sb.append(", _users=");
		sb.append(_users);
		sb.append("}");

		Assert.assertEquals(
			sb.toString(),
			_users.subList(
				expectedRecalculatedStart,
				expectedRecalculatedStart + hits.getDocs().length),
			returnedUsers);
	}

	private static final int _USERS_COUNT = 5;

	private String _randomLastName;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}