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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class LuceneIndexSearcherTest {

	@Before
	public void setUp() throws Exception {
		int initialUsersCount = 0;

		do {
			_randomLastName = RandomTestUtil.randomString(10);

			Hits hits = getHits(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			initialUsersCount = hits.getLength();
		}
		while (initialUsersCount > 0);

		for (int i = 0; i < _USERS_COUNT; i ++) {
			User user = UserTestUtil.addUser(
				RandomTestUtil.randomString(), false,
				RandomTestUtil.randomString(), _randomLastName,
				new long[] {TestPropsValues.getGroupId()});

			_users.add(user);
		}
	}

	@After
	public void tearDown() throws Exception {
		for (User user : _users) {
			UserLocalServiceUtil.deleteUser(user);
		}
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

		Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {TestPropsValues.getGroupId()});
		searchContext.setKeywords(keyword);

		QueryConfig queryConfig = new QueryConfig();

		searchContext.setQueryConfig(queryConfig);

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

		for (int i = 0; i < hits.getDocs().length; i++) {
			Document doc = hits.doc(i);

			long userId = GetterUtil.getLong(doc.get(Field.USER_ID));

			User returnedUser = UserLocalServiceUtil.getUser(userId);

			Assert.assertEquals(
				_users.get(expectedRecalculatedStart + i), returnedUser);
		}
	}

	private static final int _USERS_COUNT = 5;

	private String _randomLastName;
	private List<User> _users = new ArrayList<User>();

}