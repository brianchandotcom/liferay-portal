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

package com.liferay.portal.search.elasticsearch6.internal.logging;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.search.elasticsearch6.internal.ElasticsearchIndexingFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.LiferayIndexCreator;
import com.liferay.portal.search.elasticsearch6.internal.facet.DefaultFacetProcessor;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.ElasticsearchEngineAdapterFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class ElasticsearchLoggingTest extends BaseIndexingTestCase {

	@Test
	public void testCountSearchRequestExecutorLogs() throws Exception {
		testLoggingEvents(
			_CLASS_NAME_COUNT_SEARCH_REQUEST_EXECUTOR_IMPL, Level.FINE, 1,
			() -> _searchEngineAdapter.execute(createCountSearchRequest()));
	}

	@Test
	public void testCountSearchRequestExecutorLogsViaIndexer()
		throws Exception {

		testLoggingEvents(
			_CLASS_NAME_COUNT_SEARCH_REQUEST_EXECUTOR_IMPL, Level.FINE, 1,
			() -> searchCount(createSearchContext(), new MatchAllQuery()));
	}

	@Test
	public void testIndexerSearchCountLogs() throws Exception {
		testLoggingEvents(
			_CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER, Level.INFO, 2,
			() -> searchCount(createSearchContext(), new MatchAllQuery()));
	}

	@Test
	public void testIndexerSearchLogs() throws Exception {
		testLoggingEvents(
			_CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER, Level.INFO, 2,
			() -> search(createSearchContext()));
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
			_CLASS_NAME_MULTISEARCH_SEARCH_REQUEST_EXECUTOR_IMPL, Level.FINE, 2,
			() -> _searchEngineAdapter.execute(multisearchSearchRequest));
	}

	@Test
	public void testSearchSearchRequestExecutorLogs() throws Exception {
		testLoggingEvents(
			_CLASS_NAME_SEARCH_SEARCH_REQUEST_EXECUTOR_IMPL, Level.FINE, 1,
			() -> _searchEngineAdapter.execute(createSearchSearchRequest()));
	}

	@Test
	public void testSearchSearchRequestExecutorLogsViaIndexer()
		throws Exception {

		testLoggingEvents(
			_CLASS_NAME_SEARCH_SEARCH_REQUEST_EXECUTOR_IMPL, Level.FINE, 1,
			() -> search(createSearchContext()));
	}

	protected CountSearchRequest createCountSearchRequest() throws Exception {
		CountSearchRequest countSearchRequest = new CountSearchRequest();

		countSearchRequest.setIndexNames(getIndexNames());
		countSearchRequest.setQuery(new MatchAllQuery());

		return countSearchRequest;
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		ElasticsearchFixture elasticsearchFixture = new ElasticsearchFixture(
			getClass());

		ElasticsearchEngineAdapterFixture elasticsearchEngineAdapterFixture =
			new ElasticsearchEngineAdapterFixture(
				elasticsearchFixture, new DefaultFacetProcessor());

		_searchEngineAdapter =
			elasticsearchEngineAdapterFixture.getSearchEngineAdapter();

		return new ElasticsearchIndexingFixture(
			elasticsearchFixture, BaseIndexingTestCase.COMPANY_ID,
			new LiferayIndexCreator(elasticsearchFixture));
	}

	protected SearchSearchRequest createSearchSearchRequest() throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(getIndexNames());
		searchSearchRequest.setQuery(new MatchAllQuery());

		return searchSearchRequest;
	}

	protected String[] getIndexNames() throws PortalException {
		return new String[] {String.valueOf(BaseIndexingTestCase.COMPANY_ID)};
	}

	protected void testLoggingEvents(
			String name, Level level, long numberOfEvents,
			SearchFunction searchFunction)
		throws Exception {

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(name, level)) {

			searchFunction.execute();

			_assertLogRecords(captureHandler, numberOfEvents);
		}
	}

	private void _assertLogRecords(
		CaptureHandler captureHandler, long numberOfEvents) {

		List<LogRecord> logRecords = captureHandler.getLogRecords();

		Assert.assertEquals(
			logRecords.toString(), numberOfEvents, logRecords.size());

		for (LogRecord logRecord : logRecords) {
			String message = logRecord.getMessage();

			Assert.assertTrue(
				message,
				message.startsWith("The search engine processed ") ||
				message.startsWith("Searching "));
		}
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

	private SearchEngineAdapter _searchEngineAdapter;

	private interface SearchFunction {

		public void execute() throws Exception;

	}

}