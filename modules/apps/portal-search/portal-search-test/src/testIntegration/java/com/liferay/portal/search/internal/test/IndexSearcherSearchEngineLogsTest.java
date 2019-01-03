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

package com.liferay.portal.search.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class IndexSearcherSearchEngineLogsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_indexer = _indexerRegistry.getIndexer(User.class);
	}

	@Test
	public void testCountSearchEngineAdapterLogs() throws Exception {
		String keyword = "countSearchAdapterTestUser";

		SearchContext searchContext = _addUser(keyword);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.search.elasticsearch6.internal." +
						"search.engine.adapter.search." +
							"CountSearchRequestExecutorImpl",
					Level.DEBUG))
		{

			long cnt = _indexer.searchCount(searchContext);

			_assertLogDebug(captureAppender, "searchAdapterCount", (int)cnt);
		}
	}

	@Test
	public void testSearchCountEngineLogs() throws Exception {
		String keyword = "searchCountTestUser";

		SearchContext searchContext = _addUser(keyword);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.search.elasticsearch6.internal." +
						"ElasticsearchIndexSearcher",
					Level.INFO))
		{

			long cnt = _indexer.searchCount(searchContext);

			_assertLogInfo(captureAppender, "searchCount", (int)cnt);
		}
	}

	@Test
	public void testSearchEngineAdapterLogs() throws Exception {
		String keyword = "searchAdapterTestUser";

		SearchContext searchContext = _addUser(keyword);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.search.elasticsearch6.internal." +
						"search.engine.adapter.search." +
							"SearchSearchRequestExecutorImpl",
					Level.DEBUG))
		{

			Hits hits = _indexer.search(searchContext);

			_assertLogDebug(
				captureAppender, hits.toString(), hits.getDocs().length);
		}
	}

	@Test
	public void testSearchEngineLogs() throws Exception {
		String keyword = "searchTestUser";

		SearchContext searchContext = _addUser(keyword);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.search.elasticsearch6.internal." +
						"ElasticsearchIndexSearcher",
					Level.INFO))
		{

			Hits hits = _indexer.search(searchContext);

			_assertLogInfo(
				captureAppender, hits.toString(), hits.getDocs().length);
		}
	}

	private SearchContext _addUser(String keyword) throws Exception,
		PortalException {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keyword);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(Field.ANY);

		User user = UserTestUtil.addUser(
			keyword, LocaleUtil.getDefault(), keyword, keyword,
			new long[] {TestPropsValues.getGroupId()});

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		_users.add(user);

		return searchContext;
	}

	private void _assertLogDebug(
		CaptureAppender captureAppender, String describ, int count) {

		Assert.assertEquals(describ, 1, count);

		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(loggingEvents.toString(), 1, loggingEvents.size());
	}

	private void _assertLogInfo(
		CaptureAppender captureAppender, String describ, int count) {

		Assert.assertEquals(describ, 1, count);

		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(loggingEvents.toString(), 2, loggingEvents.size());
	}

	@Inject
	private static IndexerRegistry _indexerRegistry;

	private Indexer<User> _indexer;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}