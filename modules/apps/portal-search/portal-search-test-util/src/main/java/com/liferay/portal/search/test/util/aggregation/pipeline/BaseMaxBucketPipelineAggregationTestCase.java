/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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