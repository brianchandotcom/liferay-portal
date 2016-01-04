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

package com.liferay.portal.search.unit.test.stats;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.unit.test.BaseIndexingTestCase;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public abstract class BaseStatisticsTestCase extends BaseIndexingTestCase {

	protected static String toString(StatsResults statsResults) {
		StringBundler sb = new StringBundler(19);

		sb.append("{count=");
		sb.append(statsResults.getCount());
		sb.append(", field=");
		sb.append(statsResults.getField());
		sb.append(", max=");
		sb.append(statsResults.getMax());
		sb.append(", mean=");
		sb.append(statsResults.getMean());
		sb.append(", min=");
		sb.append(statsResults.getMin());
		sb.append(", missing=");
		sb.append(statsResults.getMissing());
		sb.append(", standardDeviation=");
		sb.append(statsResults.getStandardDeviation());
		sb.append(", sum=");
		sb.append(statsResults.getSum());
		sb.append(", sumOfSquares=");
		sb.append(statsResults.getSumOfSquares());
		sb.append("}");

		return sb.toString();
	}

	protected void addDocuments(int count) throws Exception {
		final String field = STAT_FIELD;

		for (int i = 1; i <= count; i++) {
			final int value = i;

			addDocument(
				new DocumentCreationHelper() {

					@Override
					public void populate(Document document) {
						document.addNumberSortable(field, value);
					}

				});
		}
	}

	protected void assertStats() throws Exception {
		String field = STAT_SORTABLE_FIELD;

		SearchContext searchContext = createSearchContext();

		Stats stats = new Stats();

		stats.setCount(true);
		stats.setField(field);
		stats.setMax(true);
		stats.setMean(true);
		stats.setMin(true);
		stats.setSum(true);
		stats.setSumOfSquares(true);

		searchContext.addStats(stats);

		Hits hits = search(searchContext);

		Map<String, StatsResults> statsResultsMap = hits.getStatsResults();

		Assert.assertNotNull(statsResultsMap);

		StatsResults statsResults = statsResultsMap.get(field);

		Assert.assertNotNull(statsResults);

		StatsResults expectedStatsResults = new StatsResults(field);

		expectedStatsResults.setCount(31);
		expectedStatsResults.setMax(31);
		expectedStatsResults.setMean(16);
		expectedStatsResults.setMin(1);
		expectedStatsResults.setSum(496);
		expectedStatsResults.setSumOfSquares(10416);

		Assert.assertEquals(
			toString(expectedStatsResults), toString(statsResults));
	}

	protected void testGetStats() throws Exception {
		addDocuments(31);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					assertStats();

					return null;
				}

			});
	}

	protected static final String STAT_FIELD = Field.PRIORITY;

	protected static final String STAT_SORTABLE_FIELD =
		STAT_FIELD + "_Number_sortable";

}