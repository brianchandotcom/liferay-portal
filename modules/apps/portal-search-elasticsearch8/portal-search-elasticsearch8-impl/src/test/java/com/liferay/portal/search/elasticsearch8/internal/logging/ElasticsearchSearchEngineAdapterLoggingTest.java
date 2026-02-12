/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.logging;

import co.elastic.clients.elasticsearch._types.HealthStatus;

import com.liferay.portal.kernel.search.generic.MatchAllQuery;
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
import com.liferay.portal.search.test.util.logging.ExpectedLog;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
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

	@ExpectedLog(
		expectedClass = CountSearchRequestExecutor.class,
		expectedLevel = ExpectedLog.Level.FINE,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testCountSearchRequestExecutorLogs() {
		_searchEngineAdapter.execute(
			new CountSearchRequest() {
				{
					setIndexNames("_all");
					setQuery(new MatchAllQuery());
				}
			});
	}

	@ExpectedLog(
		expectedClass = MultisearchSearchRequestExecutor.class,
		expectedLevel = ExpectedLog.Level.FINE,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testMultisearchSearchRequestExecutorLogs() {
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

	@ExpectedLog(
		expectedClass = SearchSearchRequestExecutor.class,
		expectedLevel = ExpectedLog.Level.FINE,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testSearchSearchRequestExecutorLogs() {
		_searchEngineAdapter.execute(
			new SearchSearchRequest() {
				{
					setIndexNames("_all");
					setQuery(new MatchAllQuery());
				}
			});
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

	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;

	private ElasticsearchEngineAdapterFixture
		_elasticsearchEngineAdapterFixture;
	private SearchEngineAdapter _searchEngineAdapter;

}