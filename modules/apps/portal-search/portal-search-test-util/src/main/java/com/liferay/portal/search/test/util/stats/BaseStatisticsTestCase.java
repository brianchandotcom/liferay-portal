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

package com.liferay.portal.search.test.util.stats;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.search.internal.stats.StatsRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.stats.StatsResponseBuilderImpl;
import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsRequestBuilder;
import com.liferay.portal.search.stats.StatsRequestBuilderFactory;
import com.liferay.portal.search.stats.StatsResponse;
import com.liferay.portal.search.stats.StatsResponseBuilder;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public abstract class BaseStatisticsTestCase extends BaseIndexingTestCase {

	@Test
	public void testStats() throws Exception {
		addDocuments(31);

		String field = STAT_SORTABLE_FIELD;

		Stats stats = new Stats() {
			{
				setCount(true);
				setField(field);
				setMax(true);
				setMean(true);
				setMin(true);
				setSum(true);
				setSumOfSquares(true);
			}
		};

		StatsResults expectedStatsResults = new StatsResults(field) {
			{
				setCount(31);
				setMax(31);
				setMean(16);
				setMin(1);
				setSum(496);
				setSumOfSquares(10416);
			}
		};

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.addStats(stats));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> {
						Map<String, StatsResults> statsResultsMap =
							hits.getStatsResults();

						Assert.assertNotNull(statsResultsMap);

						StatsResults statsResults = statsResultsMap.get(field);

						Assert.assertNotNull(statsResults);

						Assert.assertEquals(
							toString(expectedStatsResults),
							toString(statsResults));
					});
			});
	}

	@Test
	public void testStatsAgainstSearchCount() throws Exception {
		addDocuments(31);

		String field = STAT_SORTABLE_FIELD;

		Stats stats = new Stats() {
			{
				setCount(true);
				setField(field);
			}
		};

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.addStats(stats));

				indexingTestHelper.searchCount();

				indexingTestHelper.verify(hits -> Assert.assertNull(hits));

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						Map<String, StatsResponse> map =
							searchResponse.getStatsResponseMap();

						Assert.assertTrue(map.isEmpty());
					});
			});
	}

	@Test
	public void testStatsRequest() throws Exception {
		addDocuments(31);

		String field = STAT_SORTABLE_FIELD;

		StatsRequestBuilder statsRequestBuilder =
			_statsRequestBuilderFactory.getStatsRequestBuilder();

		StatsRequest statsRequest = statsRequestBuilder.cardinality(
			true
		).count(
			true
		).field(
			field
		).max(
			true
		).mean(
			true
		).min(
			true
		).missing(
			true
		).sum(
			true
		).sumOfSquares(
			true
		).build();

		StatsResponseBuilder statsResponseBuilder =
			new StatsResponseBuilderImpl();

		StatsResponse expectedStatsResponse = statsResponseBuilder.cardinality(
			31
		).count(
			31
		).field(
			field
		).max(
			31
		).mean(
			16
		).min(
			1
		).sum(
			496
		).sumOfSquares(
			10416
		).build();

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequest -> searchRequest.statsRequests(statsRequest));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						Map<String, StatsResponse> map =
							searchResponse.getStatsResponseMap();

						Assert.assertNotNull(map);

						StatsResponse statsResponse = map.get(field);

						Assert.assertNotNull(statsResponse);

						Assert.assertEquals(
							toString(expectedStatsResponse),
							toString(statsResponse));
					});
			});
	}

	@Test
	public void testStatsRequestAgainstSearchCount() throws Exception {
		addDocuments(31);

		String field = STAT_SORTABLE_FIELD;

		StatsRequestBuilder statsRequestBuilder =
			_statsRequestBuilderFactory.getStatsRequestBuilder();

		StatsRequest statsRequest = statsRequestBuilder.cardinality(
			true
		).field(
			field
		).build();

		StatsResponseBuilder statsResponseBuilder =
			new StatsResponseBuilderImpl();

		StatsResponse expectedStatsResponse = statsResponseBuilder.cardinality(
			31
		).field(
			field
		).build();

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.statsRequests(
						statsRequest));

				indexingTestHelper.searchCount();

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						Map<String, StatsResponse> map =
							searchResponse.getStatsResponseMap();

						Assert.assertNotNull(map);

						StatsResponse statsResponse = map.get(field);

						Assert.assertNotNull(statsResponse);

						Assert.assertEquals(
							toString(expectedStatsResponse),
							toString(statsResponse));
					});
			});
	}

	protected static String toString(StatsResponse statsResponse) {
		return String.valueOf(
			new LinkedHashMap<String, Object>() {
				{
					put("cardinality", statsResponse.getCardinality());
					put("count", statsResponse.getCount());
					put("field", statsResponse.getField());
					put("max", statsResponse.getMax());
					put("mean", statsResponse.getMean());
					put("min", statsResponse.getMin());
					put("missing", statsResponse.getMissing());
					put(
						"standardDeviation",
						statsResponse.getStandardDeviation());
					put("sum", statsResponse.getSum());
					put("sumOfSquares", statsResponse.getSumOfSquares());
				}
			});
	}

	protected static String toString(StatsResults statsResults) {
		return String.valueOf(
			new LinkedHashMap<String, Object>() {
				{
					put("count", statsResults.getCount());
					put("field", statsResults.getField());
					put("max", statsResults.getMax());
					put("mean", statsResults.getMean());
					put("min", statsResults.getMin());
					put("missing", statsResults.getMissing());
					put(
						"standardDeviation",
						statsResults.getStandardDeviation());
					put("sum", statsResults.getSum());
					put("sumOfSquares", statsResults.getSumOfSquares());
				}
			});
	}

	protected void addDocuments(int count) throws Exception {
		final String field = STAT_FIELD;

		for (int i = 1; i <= count; i++) {
			addDocument(DocumentCreationHelpers.singleNumberSortable(field, i));
		}
	}

	protected static final String STAT_FIELD = Field.PRIORITY;

	protected static final String STAT_SORTABLE_FIELD =
		STAT_FIELD + "_Number_sortable";

	private final StatsRequestBuilderFactory _statsRequestBuilderFactory =
		new StatsRequestBuilderFactoryImpl();

}