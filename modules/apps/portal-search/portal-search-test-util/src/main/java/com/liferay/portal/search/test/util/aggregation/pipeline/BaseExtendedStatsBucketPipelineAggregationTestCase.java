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

package com.liferay.portal.search.test.util.aggregation.pipeline;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseExtendedStatsBucketPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testStatsBucketPipeline() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		PipelineAggregation pipelineAggregation =
			aggregations.extendedStatsBucket(
				"extended_stats_bucket", "histogram>sum");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						searchRequestBuilder.addAggregation(
							aggregationFixture.
								getDefaultHistogramAggregation());
						searchRequestBuilder.addPipelineAggregation(
							pipelineAggregation);
					});

				indexingTestHelper.search();

				ExtendedStatsAggregationResult extendedStatsAggregationResult =
					indexingTestHelper.getAggregationResult(
						pipelineAggregation);

				Assert.assertEquals(
					"Avg summed in buckets", 42,
					extendedStatsAggregationResult.getAvg(), 0);
				Assert.assertEquals(
					"Total count in buckets", 5,
					extendedStatsAggregationResult.getCount(), 0);
				Assert.assertEquals(
					"Max summed priority in buckets", 85,
					extendedStatsAggregationResult.getMax(), 0);
				Assert.assertEquals(
					"Min summed priority in buckets", 10,
					extendedStatsAggregationResult.getMin(), 0);
				Assert.assertEquals(
					"Summed priority in buckets", 210,
					extendedStatsAggregationResult.getSum(), 0);
				Assert.assertEquals(
					"Std deviation of summed priority in buckets",
					27.313000567495326,
					extendedStatsAggregationResult.getStdDeviation(), 0);
				Assert.assertEquals(
					"Sum of squares of summed priority in buckets", 12550,
					extendedStatsAggregationResult.getSumOfSquares(), 0);
				Assert.assertEquals(
					"Variance of summed priority in buckets", 746,
					extendedStatsAggregationResult.getVariance(), 0);
			});
	}

}