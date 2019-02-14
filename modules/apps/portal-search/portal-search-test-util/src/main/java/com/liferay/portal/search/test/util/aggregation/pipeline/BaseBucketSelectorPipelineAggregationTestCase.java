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
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregationResult;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.internal.aggregation.bucket.HistogramAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.SumAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.BucketSelectorPipelineAggregationImpl;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseBucketSelectorPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testBucketSelector() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		HistogramAggregation histogramAggregation =
			new HistogramAggregationImpl("histogram", Field.PRIORITY);

		histogramAggregation.setMinDocCount(1L);
		histogramAggregation.setInterval(5.0);

		SumAggregation sumAggregation = new SumAggregationImpl(
			"sum", Field.PRIORITY);

		histogramAggregation.addChildAggregation(sumAggregation);

		Script script = new Script("painless", "params.sum > 40");

		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation =
			new BucketSelectorPipelineAggregationImpl(
				"bucket_selector", script);

		bucketSelectorPipelineAggregation.addBucketPath("sum", "sum");

		histogramAggregation.addPipelineAggregation(
			bucketSelectorPipelineAggregation);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						histogramAggregation));

				indexingTestHelper.search();

				HistogramAggregationResult histogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						histogramAggregation);

				List<Bucket> buckets = new ArrayList<>(
					histogramAggregationResult.getBuckets());

				Assert.assertEquals("Number of buckets", 2, buckets.size());

				assertBucket(buckets.get(0), "10.0", 5);
				assertBucket(buckets.get(1), "15.0", 5);
			});
	}

	protected void assertBucket(
		Bucket bucket, String expectedKey, long expectedCount) {

		Assert.assertEquals(expectedKey, bucket.getKey());
		Assert.assertEquals(expectedCount, bucket.getDocCount());
	}

}