/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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