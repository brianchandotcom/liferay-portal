/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.logging;

import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.opensearch2.internal.OpenSearchIndexSearcher;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search.CountSearchRequestExecutorImpl;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search.SearchSearchRequestExecutorImpl;
import com.liferay.portal.search.test.rule.logging.ExpectedLogMethodTestRule;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.logging.ExpectedLog;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class OpenSearchIndexSearcherLoggingTest extends BaseIndexingTestCase {

	@ClassRule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			ExpectedLogMethodTestRule.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

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
		expectedClass = OpenSearchIndexSearcher.class,
		expectedLevel = ExpectedLog.Level.INFO,
		expectedLog = "The search engine processed"
	)
	@Test
	public void testIndexerSearchCountLogs() {
		searchCount(createSearchContext(), new MatchAllQuery());
	}

	@ExpectedLog(
		expectedClass = OpenSearchIndexSearcher.class,
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
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

}