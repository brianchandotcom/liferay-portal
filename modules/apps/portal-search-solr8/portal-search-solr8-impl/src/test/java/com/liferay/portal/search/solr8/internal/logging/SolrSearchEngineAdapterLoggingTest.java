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

package com.liferay.portal.search.solr8.internal.logging;

import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.solr8.internal.SolrUnitTestRequirements;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.search.CountSearchRequestExecutorImpl;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.search.SearchSearchRequestExecutorImpl;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.logging.ExpectedLog;
import com.liferay.portal.search.test.util.logging.ExpectedLogMethodTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Bryan Engler
 */
public class SolrSearchEngineAdapterLoggingTest extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			ExpectedLogMethodTestRule.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Assume.assumeTrue(
			SolrUnitTestRequirements.isSolrExternallyStartedByDeveloper());
	}

	@ExpectedLog(
		expectedClass = CountSearchRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.FINE,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testCountSearchRequestExecutorLogs() {
		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		searchEngineAdapter.execute(
			new CountSearchRequest() {
				{
					setIndexNames("liferay");
					setQuery(new MatchAllQuery());
				}
			});
	}

	@Test
	public void testMultisearchSearchRequestExecutorLogs() {
		expectedException.expect(UnsupportedOperationException.class);

		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		searchEngineAdapter.execute(
			new MultisearchSearchRequest() {
				{
					addSearchSearchRequest(
						new SearchSearchRequest() {
							{
								setIndexNames("liferay");
								setQuery(new MatchAllQuery());
							}
						});
				}
			});
	}

	@ExpectedLog(
		expectedClass = SearchSearchRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.FINE,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testSearchSearchRequestExecutorLogs() {
		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		searchEngineAdapter.execute(
			new SearchSearchRequest() {
				{
					setIndexNames("liferay");
					setQuery(new MatchAllQuery());
				}
			});
	}

	@Test
	public void testSearchSearchRequestWithPortalSearchQuery() {
		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		searchEngineAdapter.execute(
			new SearchSearchRequest() {
				{
					setIndexNames("liferay");
					setQuery(queries.matchAll());
				}
			});
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

}