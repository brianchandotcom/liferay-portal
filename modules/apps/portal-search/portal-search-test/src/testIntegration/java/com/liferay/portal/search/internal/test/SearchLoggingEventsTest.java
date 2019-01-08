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
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SearchLoggingEventsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Assume.assumeTrue(_isSearchEngineElasticsearch());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_indexer = _indexerRegistry.getIndexer(User.class);
	}

	@Test
	public void testCountSearchRequestExecutorLogs() throws Exception {
		testLoggingEvents(
			_CLASS_NAME_COUNT_SEARCH_REQUEST_EXECUTOR_IMPL, Level.DEBUG, 1,
			() -> _searchEngineApapter.execute(createCountSearchRequest()));
	}

	@Test
	public void testCountSearchRequestExecutorLogsViaIndexer()
		throws Exception {

		testLoggingEvents(
			_CLASS_NAME_COUNT_SEARCH_REQUEST_EXECUTOR_IMPL, Level.DEBUG, 1,
			() -> _indexer.searchCount(
				SearchContextTestUtil.getSearchContext()));
	}

	@Test
	public void testIndexerSearchCountLogs() throws Exception {
		testLoggingEvents(
			_CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER, Level.INFO, 2,
			() -> _indexer.searchCount(
				SearchContextTestUtil.getSearchContext()));
	}

	@Test
	public void testIndexerSearchLogs() throws Exception {
		testLoggingEvents(
			_CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER, Level.INFO, 2,
			() -> _indexer.search(SearchContextTestUtil.getSearchContext()));
	}

	@Test
	public void testMultisearchSearchRequestExecutorLogs() throws Exception {
		MultisearchSearchRequest multisearchSearchRequest =
			new MultisearchSearchRequest();

		multisearchSearchRequest.addSearchSearchRequest(
			createSearchSearchRequest());
		multisearchSearchRequest.addSearchSearchRequest(
			createSearchSearchRequest());

		testLoggingEvents(
			_CLASS_NAME_MULTISEARCH_SEARCH_REQUEST_EXECUTOR_IMPL, Level.DEBUG,
			2, () -> _searchEngineApapter.execute(multisearchSearchRequest));
	}

	@Test
	public void testSearchSearchRequestExecutorLogs() throws Exception {
		testLoggingEvents(
			_CLASS_NAME_SEARCH_SEARCH_REQUEST_EXECUTOR_IMPL, Level.DEBUG, 1,
			() -> _searchEngineApapter.execute(createSearchSearchRequest()));
	}

	@Test
	public void testSearchSearchRequestExecutorLogsViaIndexer()
		throws Exception {

		testLoggingEvents(
			_CLASS_NAME_SEARCH_SEARCH_REQUEST_EXECUTOR_IMPL, Level.DEBUG, 1,
			() -> _indexer.search(SearchContextTestUtil.getSearchContext()));
	}

	protected CountSearchRequest createCountSearchRequest() throws Exception {
		CountSearchRequest countSearchRequest = new CountSearchRequest();

		countSearchRequest.setIndexNames(getIndexNames());
		countSearchRequest.setQuery(new MatchAllQuery());

		return countSearchRequest;
	}

	protected SearchSearchRequest createSearchSearchRequest() throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(getIndexNames());
		searchSearchRequest.setQuery(new MatchAllQuery());

		return searchSearchRequest;
	}

	protected String[] getIndexNames() throws PortalException {
		return new String[] {"liferay-" + TestPropsValues.getCompanyId()};
	}

	protected void testLoggingEvents(
			String name, Level level, long numberOfEvents,
			SearchFunction searchFunction)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(name, level)) {

			searchFunction.execute();

			_assertLoggingEvents(captureAppender, numberOfEvents);
		}
	}

	private void _assertLoggingEvents(
		CaptureAppender captureAppender, long numberOfEvents) {

		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(
			loggingEvents.toString(), numberOfEvents, loggingEvents.size());

		for (LoggingEvent loggingEvent : loggingEvents) {
			String renderedMessage = loggingEvent.getRenderedMessage();

			Assert.assertTrue(
				renderedMessage.startsWith("The search engine processed ") ||
				renderedMessage.startsWith("Searching "));
		}
	}

	private boolean _isSearchEngineElasticsearch() {
		SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine(
			SearchEngineHelperUtil.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

		return vendor.equals("Elasticsearch");
	}

	private static final String _CLASS_NAME_COUNT_SEARCH_REQUEST_EXECUTOR_IMPL =
		"com.liferay.portal.search.elasticsearch6.internal.search.engine." +
			"adapter.search.CountSearchRequestExecutorImpl";

	private static final String _CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER =
		"com.liferay.portal.search.elasticsearch6.internal." +
			"ElasticsearchIndexSearcher";

	private static final String
		_CLASS_NAME_MULTISEARCH_SEARCH_REQUEST_EXECUTOR_IMPL =
			"com.liferay.portal.search.elasticsearch6.internal.search.engine." +
				"adapter.search.MultisearchSearchRequestExecutorImpl";

	private static final String
		_CLASS_NAME_SEARCH_SEARCH_REQUEST_EXECUTOR_IMPL =
			"com.liferay.portal.search.elasticsearch6.internal.search.engine." +
				"adapter.search.SearchSearchRequestExecutorImpl";

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	private static SearchEngineAdapter _searchEngineApapter;

	private Indexer<User> _indexer;

	private interface SearchFunction {

		public void execute() throws Exception;

	}

}