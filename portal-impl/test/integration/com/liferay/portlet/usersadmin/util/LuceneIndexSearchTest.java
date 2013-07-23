/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;

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
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class LuceneIndexSearchTest {

	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < 5; i ++ ) {
			User user = UserTestUtil.addUser(
				ServiceTestUtil.randomString(), false,
				ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
				new long[]{TestPropsValues.getGroupId()});

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
	public void testSearchWithOneResult() throws Exception {
		Hits hits = getSearchWithOneResult(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertTrue(hits.getLength() == 1);
	}

	@Test
	public void testSearchWithOneResultWhenTotalEqualsStart() throws Exception {
		Hits hits = getSearchWithOneResult(5, 10);

		Assert.assertTrue(hits.getLength() == 1);
	}

	@Test
	public void testSearchWithOneResultWhenTotalSmallerThanStart()
		throws Exception {

		Hits hits = getSearchWithOneResult(1000, 1005);

		Assert.assertTrue(hits.getLength() == 1);
	}

	@Test
	public void testSearchWithoutResults() throws Exception {
		Hits hits = getSearchWithoutResults(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertTrue(hits.getLength() == 0);
	}

	@Test
	public void testSearchWithoutResultsWhenTotalEqualsStart()
		throws Exception {

		Hits hits = getSearchWithoutResults(5, 10);

		Assert.assertTrue(hits.getLength() == 0);
	}

	@Test
	public void testSearchWithoutResultsWhenTotalSmallerThanStart()
		throws Exception {

		Hits hits = getSearchWithoutResults(1000, 1005);

		Assert.assertTrue(hits.getLength() == 0);
	}

	@Test
	public void testSearchWithResults() throws Exception {
		Hits hits = getSearchWithResults(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertTrue(hits.getLength() == 5);
	}

	@Test
	public void testSearchWithResultsWhenTotalEqualsStart() throws Exception {
		Hits hits = getSearchWithResults(5, 10);

		Assert.assertTrue(hits.getLength() == 5);
	}

	@Test
	public void testSearchWithResultsWhenTotalSmallerThanStart()
		throws Exception {

		Hits hits = getSearchWithResults(1000, 1005);

		Assert.assertTrue(hits.getLength() == 5);
	}

	protected Hits getSearchWithOneResult(int start, int end) throws Exception {
		User user = _users.get(0);

		return _getSearch(start, end, user.getFirstName() );
	}

	protected Hits getSearchWithoutResults(int start, int end)
		throws Exception {

		return _getSearch(start, end, "fakeKeyword");
	}

	protected Hits getSearchWithResults(int start, int end) throws Exception {
		return _getSearch(start, end, StringPool.BLANK);
	}

	private Hits _getSearch(int start, int end, String keyword)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setGroupIds(new long[] {TestPropsValues.getGroupId()});
		searchContext.setKeywords(keyword);

		searchContext.setStart(start);
		searchContext.setEnd(end);

		QueryConfig queryConfig = new QueryConfig();

		searchContext.setQueryConfig(queryConfig);

		return indexer.search(searchContext);
	}

	List<User> _users = new ArrayList<User>();

}