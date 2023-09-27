/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.pipeline;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregationResult;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseBucketScriptPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testBucketScript() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		HistogramAggregation histogramAggregation =
			aggregationFixture.getDefaultHistogramAggregation();

		Script script = scripts.script("params.sum * 50");

		BucketScriptPipelineAggregation bucketScriptPipelineAggregation =
			aggregations.bucketScript("bucket_script", script);

		bucketScriptPipelineAggregation.addBucketPath("sum", "sum");

		histogramAggregation.addPipelineAggregation(
			bucketScriptPipelineAggregation);

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

				Assert.assertEquals("Number of buckets", 5, buckets.size());

				assertBucket(buckets.get(0), "0.0", 4, 500.0);
				assertBucket(buckets.get(1), "5.0", 5, 1750.0);
				assertBucket(buckets.get(2), "10.0", 5, 3000.0);
				assertBucket(buckets.get(3), "15.0", 5, 4250.0);
				assertBucket(buckets.get(4), "20.0", 1, 1000.0);
			});
	}

	protected void assertBucket(
		Bucket bucket, String expectedKey, long expectedCount,
		Double bucketScriptValue) {

		Assert.assertEquals(expectedKey, bucket.getKey());
		Assert.assertEquals(expectedCount, bucket.getDocCount());

		if (bucketScriptValue != null) {
			Map<String, AggregationResult> childrenAggregationResults =
				bucket.getChildrenAggregationResults();

			BucketScriptPipelineAggregationResult
				bucketScriptPipelineAggregationResult =
					(BucketScriptPipelineAggregationResult)
						childrenAggregationResults.get("bucket_script");

			Assert.assertNotNull(bucketScriptPipelineAggregationResult);

			Assert.assertEquals(
				"Bucket script value", bucketScriptValue,
				bucketScriptPipelineAggregationResult.getValue(), 0);
		}
	}

}