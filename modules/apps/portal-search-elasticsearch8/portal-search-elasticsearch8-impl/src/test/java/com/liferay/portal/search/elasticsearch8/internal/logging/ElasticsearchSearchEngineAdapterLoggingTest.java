/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.logging;

import co.elastic.clients.elasticsearch._types.HealthStatus;

import com.liferay.portal.kernel.search.MatchAllQuery;
import com.liferay.portal.search.elasticsearch8.internal.connection.ClusterHealthResponseUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch8.internal.connection.HealthExpectations;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.ElasticsearchEngineAdapterFixture;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search.CountSearchRequestExecutor;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search.MultisearchSearchRequestExecutor;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search.SearchSearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class ElasticsearchSearchEngineAdapterLoggingTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchSearchEngineAdapterLoggingTest.class.
					getSimpleName()
			).build();

		elasticsearchConnectionFixture.createNode();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;
	}

	@AfterClass
	public static void tearDownClass() {
		_elasticsearchConnectionFixture.destroyNode();
	}

	@Before
	public void setUp() {
		_elasticsearchEngineAdapterFixture =
			new ElasticsearchEngineAdapterFixture() {
				{
					setElasticsearchClientResolver(
						_elasticsearchConnectionFixture);
				}
			};

		_elasticsearchEngineAdapterFixture.setUp();

		_waitForElasticsearchToStart(_elasticsearchConnectionFixture);

		_searchEngineAdapter =
			_elasticsearchEngineAdapterFixture.getSearchEngineAdapter();
	}

	@Test
	public void testCountSearchRequestExecutorLogs() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				CountSearchRequestExecutor.class.getName(),
				LoggerTestUtil.DEBUG)) {

			_searchEngineAdapter.execute(
				new CountSearchRequest() {
					{
						setIndexNames(_INDEX_NAME);
						setQuery(new MatchAllQuery());
					}
				});

			_assertSearchRequestExecutorLogEntries(logCapture.getLogEntries());
		}
	}

	@Test
	public void testMultisearchSearchRequestExecutorLogs() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				MultisearchSearchRequestExecutor.class.getName(),
				LoggerTestUtil.DEBUG)) {

			_searchEngineAdapter.execute(
				new MultisearchSearchRequest() {
					{
						addSearchSearchRequest(
							new SearchSearchRequest() {
								{
									setIndexNames(_INDEX_NAME);
									setQuery(new MatchAllQuery());
								}
							});
					}
				});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			_assertLogEntry(
				logEntries.get(0), "The search engine processed",
				LoggerTestUtil.DEBUG);
		}
	}

	@Test
	public void testSearchSearchRequestExecutorLogs() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SearchSearchRequestExecutor.class.getName(),
				LoggerTestUtil.DEBUG)) {

			_searchEngineAdapter.execute(
				new SearchSearchRequest() {
					{
						setIndexNames(_INDEX_NAME);
						setQuery(new MatchAllQuery());
					}
				});

			_assertSearchRequestExecutorLogEntries(logCapture.getLogEntries());
		}
	}

	private void _assertLogEntry(
		LogEntry logEntry, String expectedMessage, String logLevel) {

		String message = logEntry.getMessage();

		Assert.assertEquals(logLevel, logEntry.getPriority());
		Assert.assertTrue(
			message + " does not start with " + expectedMessage,
			message.startsWith(expectedMessage));
	}

	private void _assertSearchRequestExecutorLogEntries(
		List<LogEntry> logEntries) {

		Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

		_assertLogEntry(
			logEntries.get(0), "Stack trace for [" + _INDEX_NAME + "]:",
			LoggerTestUtil.INFO);
		_assertLogEntry(
			logEntries.get(1),
			"Search request string for [" + _INDEX_NAME + "]:",
			LoggerTestUtil.DEBUG);
		_assertLogEntry(
			logEntries.get(2), "The search engine processed the request in",
			LoggerTestUtil.DEBUG);
	}

	private void _waitForElasticsearchToStart(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ClusterHealthResponseUtil.getHealthResponse(
			elasticsearchClientResolver,
			new HealthExpectations() {
				{
					setActivePrimaryShards(0);
					setActiveShards(0);
					setHealthStatus(HealthStatus.Green);
					setNumberOfDataNodes(1);
					setNumberOfNodes(1);
					setUnassignedShards(0);
				}
			});
	}

	private static final String _INDEX_NAME = "_all";

	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;

	private ElasticsearchEngineAdapterFixture
		_elasticsearchEngineAdapterFixture;
	private SearchEngineAdapter _searchEngineAdapter;

}