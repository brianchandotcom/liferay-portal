/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.pipeline;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseStatsBucketPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testStatsBucketPipeline() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		StatsBucketPipelineAggregation statsBucketPipelineAggregation =
			aggregations.statsBucket("stats_bucket", "histogram>sum");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						searchRequestBuilder.addAggregation(
							aggregationFixture.
								getDefaultHistogramAggregation());
						searchRequestBuilder.addPipelineAggregation(
							statsBucketPipelineAggregation);
					});

				indexingTestHelper.search();

				StatsBucketPipelineAggregationResult
					statsBucketPipelineAggregationResult =
						indexingTestHelper.getAggregationResult(
							statsBucketPipelineAggregation);

				Assert.assertEquals(
					"Avg summged in buckets", 42,
					statsBucketPipelineAggregationResult.getAvg(), 0);
				Assert.assertEquals(
					"Total count in buckets", 5,
					statsBucketPipelineAggregationResult.getCount(), 0);
				Assert.assertEquals(
					"Max summed priority in buckets", 85,
					statsBucketPipelineAggregationResult.getMax(), 0);
				Assert.assertEquals(
					"Min summed priority in buckets", 10,
					statsBucketPipelineAggregationResult.getMin(), 0);
				Assert.assertEquals(
					"Summed priority in buckets", 210,
					statsBucketPipelineAggregationResult.getSum(), 0);
			});
	}

}