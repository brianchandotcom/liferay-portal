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
import com.liferay.portal.search.aggregation.pipeline.MaxBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MaxBucketPipelineAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseMaxBucketPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testMaxBucketPipeline() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		MaxBucketPipelineAggregation maxBucketPipelineAggregation =
			aggregations.maxBucket("max_bucket", "histogram>sum");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						searchRequestBuilder.addAggregation(
							aggregationFixture.
								getDefaultHistogramAggregation());
						searchRequestBuilder.addPipelineAggregation(
							maxBucketPipelineAggregation);
					});

				indexingTestHelper.search();

				MaxBucketPipelineAggregationResult
					maxBucketPipelineAggregationResult =
						indexingTestHelper.getAggregationResult(
							maxBucketPipelineAggregation);

				Assert.assertEquals(
					"Max summed priority in buckets", 85,
					maxBucketPipelineAggregationResult.getValue(), 0);
			});
	}

}