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
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseSumBucketPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testSumBucketPipeline() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		SumBucketPipelineAggregation sumBucketPipelineAggregation =
			aggregations.sumBucket("sum_bucket", "histogram>sum");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						searchRequestBuilder.addAggregation(
							aggregationFixture.
								getDefaultHistogramAggregation());
						searchRequestBuilder.addPipelineAggregation(
							sumBucketPipelineAggregation);
					});

				indexingTestHelper.search();

				SumBucketPipelineAggregationResult
					sumBucketPipelineAggregationResult =
						indexingTestHelper.getAggregationResult(
							sumBucketPipelineAggregation);

				Assert.assertEquals(
					"Sum priority in buckets", 210,
					sumBucketPipelineAggregationResult.getValue(), 0);
			});
	}

}