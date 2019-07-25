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

package com.liferay.portal.search.solr7.internal.logging;

import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.solr7.internal.SolrIndexingFixture;
import com.liferay.portal.search.solr7.internal.search.engine.adapter.search.CountSearchRequestExecutorImpl;
import com.liferay.portal.search.solr7.internal.search.engine.adapter.search.SearchSearchRequestExecutorImpl;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.logging.ExpectedLogTestRule;

import java.util.logging.Level;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Bryan Engler
 */
public class SolrSearchEngineAdapterLoggingTest extends BaseIndexingTestCase {

	@Test
	public void testCountSearchRequestExecutorLogs() {
		expectedLogTestRule.configure(
			CountSearchRequestExecutorImpl.class, Level.FINE);

		expectedLogTestRule.expectMessage("The search engine processed");

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

	@Test
	public void testSearchSearchRequestExecutorLogs() {
		expectedLogTestRule.configure(
			SearchSearchRequestExecutorImpl.class, Level.FINE);

		expectedLogTestRule.expectMessage("The search engine processed");

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

	@Rule
	public ExpectedLogTestRule expectedLogTestRule = ExpectedLogTestRule.none();

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

}