/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.logging;

import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.search.elasticsearch8.internal.ElasticsearchIndexSearcher;
import com.liferay.portal.search.elasticsearch8.internal.indexing.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search.CountSearchRequestExecutor;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search.SearchSearchRequestExecutor;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class ElasticsearchIndexSearcherLoggingTest
	extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCountSearchRequestExecutorLogsViaIndexer() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				CountSearchRequestExecutor.class.getName(),
				LoggerTestUtil.DEBUG)) {

			searchCount(createSearchContext(), new MatchAllQuery());

			_assertLogCapture(
				logCapture, "The search engine processed", LoggerTestUtil.DEBUG,
				2);
		}
	}

	@Test
	public void testIndexerSearchCountLogs() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchIndexSearcher.class.getName(),
				LoggerTestUtil.INFO)) {

			searchCount(createSearchContext(), new MatchAllQuery());

			_assertLogCapture(
				logCapture, "The search engine processed", LoggerTestUtil.INFO);
		}
	}

	@Test
	public void testIndexerSearchLogs() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchIndexSearcher.class.getName(),
				LoggerTestUtil.INFO)) {

			search(createSearchContext());

			_assertLogCapture(
				logCapture, "The search engine processed", LoggerTestUtil.INFO);
		}
	}

	@Test
	public void testSearchSearchRequestExecutorLogsExecutionTime() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SearchSearchRequestExecutor.class.getName(),
				LoggerTestUtil.DEBUG)) {

			search(createSearchContext());

			_assertLogCapture(
				logCapture, "The search engine processed the request in",
				LoggerTestUtil.DEBUG, 2);
		}
	}

	@Test
	public void testSearchSearchRequestExecutorLogsRequestString() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SearchSearchRequestExecutor.class.getName(),
				LoggerTestUtil.DEBUG)) {

			search(createSearchContext());

			_assertLogCapture(
				logCapture, "Search request string for", LoggerTestUtil.DEBUG,
				1);
		}
	}

	@Test
	public void testSearchSearchRequestExecutorLogsStackTraceInfo() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SearchSearchRequestExecutor.class.getName(),
				LoggerTestUtil.INFO)) {

			search(createSearchContext());

			_assertLogCapture(
				logCapture, "Stack trace for", LoggerTestUtil.INFO);
		}
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private void _assertLogCapture(
		LogCapture logCapture, String expectedMessage, String logLevel) {

		_assertLogCapture(logCapture, expectedMessage, logLevel, 0);
	}

	private void _assertLogCapture(
		LogCapture logCapture, String expectedMessage, String logLevel,
		int index) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertFalse(logEntries.toString(), logEntries.isEmpty());

		LogEntry logEntry = logEntries.get(index);

		Assert.assertEquals(logLevel, logEntry.getPriority());

		Assert.assertTrue(
			logEntry.getMessage(
			).startsWith(
				expectedMessage
			));
	}

}