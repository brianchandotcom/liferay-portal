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

package com.liferay.portal.search.elasticsearch7.internal.logging;

import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.HealthExpectations;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.ElasticsearchEngineAdapterFixture;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search.CountSearchRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search.MultisearchSearchRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search.SearchSearchRequestExecutorImpl;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.test.util.logging.ExpectedLogTestRule;

import java.util.logging.Level;

import org.elasticsearch.cluster.health.ClusterHealthStatus;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class ElasticsearchSearchEngineAdapterLoggingTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterLoggingTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		ElasticsearchEngineAdapterFixture elasticsearchEngineAdapterFixture =
			new ElasticsearchEngineAdapterFixture() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);
				}
			};

		elasticsearchEngineAdapterFixture.setUp();

		waitForElasticsearchToStart(_elasticsearchFixture);

		_searchEngineAdapter =
			elasticsearchEngineAdapterFixture.getSearchEngineAdapter();
	}

	@Test
	public void testCountSearchRequestExecutorLogs() {
		expectedLogTestRule.configure(
			CountSearchRequestExecutorImpl.class, Level.FINE);

		expectedLogTestRule.expectMessage("The search engine processed");

		_searchEngineAdapter.execute(
			new CountSearchRequest() {
				{
					setIndexNames("_all");
					setQuery(new MatchAllQuery());
				}
			});
	}

	@Test
	public void testMultisearchSearchRequestExecutorLogs() {
		expectedLogTestRule.configure(
			MultisearchSearchRequestExecutorImpl.class, Level.FINE);

		expectedLogTestRule.expectMessage("The search engine processed");

		_searchEngineAdapter.execute(
			new MultisearchSearchRequest() {
				{
					addSearchSearchRequest(
						new SearchSearchRequest() {
							{
								setIndexNames("_all");
								setQuery(new MatchAllQuery());
							}
						});
				}
			});
	}

	@Test
	public void testSearchSearchRequestExecutorLogs() {
		expectedLogTestRule.configure(
			SearchSearchRequestExecutorImpl.class, Level.FINE);

		expectedLogTestRule.expectMessage("The search engine processed");

		_searchEngineAdapter.execute(
			new SearchSearchRequest() {
				{
					setIndexNames("_all");
					setQuery(new MatchAllQuery());
				}
			});
	}

	@Rule
	public ExpectedLogTestRule expectedLogTestRule = ExpectedLogTestRule.none();

	protected void waitForElasticsearchToStart(
		ElasticsearchFixture elasticsearchFixture) {

		elasticsearchFixture.getClusterHealthResponse(
			new HealthExpectations() {
				{
					setActivePrimaryShards(0);
					setActiveShards(0);
					setNumberOfDataNodes(1);
					setNumberOfNodes(1);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	private static ElasticsearchFixture _elasticsearchFixture;

	private SearchEngineAdapter _searchEngineAdapter;

}