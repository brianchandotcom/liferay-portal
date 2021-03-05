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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.elasticsearch7.internal.ElasticsearchIndexSearcher;
import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search.CountSearchRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search.SearchSearchRequestExecutorImpl;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.logging.ExpectedLog;
import com.liferay.portal.search.test.util.logging.ExpectedLogMethodTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			ExpectedLogMethodTestRule.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@ExpectedLog(
		expectedClass = CountSearchRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.FINE,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testCountSearchRequestExecutorLogsViaIndexer() {
		searchCount(createSearchContext(), new MatchAllQuery());
	}

	@ExpectedLog(
		expectedClass = ElasticsearchIndexSearcher.class,
		expectedLevel = ExpectedLog.Level.INFO,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testIndexerSearchCountLogs() {
		searchCount(createSearchContext(), new MatchAllQuery());
	}

	@ExpectedLog(
		expectedClass = ElasticsearchIndexSearcher.class,
		expectedLevel = ExpectedLog.Level.INFO,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testIndexerSearchLogs() {
		search(createSearchContext());
	}

	@ExpectedLog(
		expectedClass = SearchSearchRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.FINEST, expectedLog = "Search query:"
	)
	@Test
	public void testSearchSearchRequestExecutorLogsPrettyPrintedString() {
		search(createSearchContext());
	}

	@ExpectedLog(
		expectedClass = SearchSearchRequestExecutorImpl.class,
		expectedLevel = ExpectedLog.Level.FINE,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testSearchSearchRequestExecutorLogsViaIndexer() {
		search(createSearchContext());
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

}