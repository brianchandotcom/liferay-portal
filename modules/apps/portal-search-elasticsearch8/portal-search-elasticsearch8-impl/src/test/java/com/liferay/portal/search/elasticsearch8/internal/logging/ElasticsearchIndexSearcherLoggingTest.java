/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.logging;

import com.liferay.portal.kernel.search.MatchAllQuery;
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

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

			_assertLogEntry(
				logEntries.get(0), "Stack trace for", LoggerTestUtil.INFO);
			_assertLogEntry(
				logEntries.get(1), "Search request string for",
				LoggerTestUtil.DEBUG);
			_assertLogEntry(
				logEntries.get(2), "The search engine processed the request in",
				LoggerTestUtil.DEBUG);
		}
	}

	@Test
	public void testIndexerSearchCountLogs() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchIndexSearcher.class.getName(),
				LoggerTestUtil.INFO)) {

			searchCount(createSearchContext(), new MatchAllQuery());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			_assertLogEntry(
				logEntries.get(0), "The search engine processed",
				LoggerTestUtil.INFO);
			_assertLogEntry(
				logEntries.get(1), "Searching took", LoggerTestUtil.INFO);
		}
	}

	@Test
	public void testIndexerSearchLogs() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ElasticsearchIndexSearcher.class.getName(),
				LoggerTestUtil.INFO)) {

			search(createSearchContext());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			_assertLogEntry(
				logEntries.get(0), "The search engine processed",
				LoggerTestUtil.INFO);
			_assertLogEntry(
				logEntries.get(1), "Searching took", LoggerTestUtil.INFO);
		}
	}

	@Test
	public void testSearchSearchRequestExecutorLogs() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SearchSearchRequestExecutor.class.getName(),
				LoggerTestUtil.DEBUG)) {

			search(createSearchContext());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

			_assertLogEntry(
				logEntries.get(0), "Stack trace for", LoggerTestUtil.INFO);
			_assertLogEntry(
				logEntries.get(1), "Search request string for",
				LoggerTestUtil.DEBUG);
			_assertLogEntry(
				logEntries.get(2), "The search engine processed the request in",
				LoggerTestUtil.DEBUG);
		}
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private void _assertLogEntry(
		LogEntry logEntry, String expectedMessage, String logLevel) {

		Assert.assertEquals(logLevel, logEntry.getPriority());

		String message = logEntry.getMessage();

		Assert.assertTrue(
			message + " does not start with " + expectedMessage,
			message.startsWith(expectedMessage));
	}

}