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
import com.liferay.portal.search.aggregation.metrics.SumAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.aggregation.AggregationAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Map;

import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseBucketSortPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testBucketSort() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		FieldSort fieldSort = sorts.field("sum");

		String expectedBuckets = "[0.0=4, 20.0=1, 5.0=5, 10.0=5, 15.0=5]";
		String expectedBucketValues = "10.0, 20.0, 35.0, 60.0, 85.0";

		HistogramAggregation histogramAggregation =
			aggregationFixture.getDefaultHistogramAggregation();

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			aggregations.bucketSort("bucket_sort");

		bucketSortPipelineAggregation.addSortFields(fieldSort);

		histogramAggregation.addPipelineAggregation(
			bucketSortPipelineAggregation);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						histogramAggregation));

				indexingTestHelper.search();

				HistogramAggregationResult histogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						histogramAggregation);

				AggregationAssert.assertBuckets(
					expectedBuckets, histogramAggregationResult);

				AggregationAssert.assertBucketValues(
					expectedBucketValues, this::getSumValue,
					histogramAggregationResult);
			});
	}

	@Test
	public void testBucketSortWithSize() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		FieldSort fieldSort = sorts.field("sum", SortOrder.DESC);

		String expectedBuckets = "[15.0=5, 10.0=5, 5.0=5]";
		String expectedBucketValues = "85.0, 60.0, 35.0";

		HistogramAggregation histogramAggregation =
			aggregationFixture.getDefaultHistogramAggregation();

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			aggregations.bucketSort("bucket_sort");

		bucketSortPipelineAggregation.addSortFields(fieldSort);
		bucketSortPipelineAggregation.setSize(3);

		histogramAggregation.addPipelineAggregation(
			bucketSortPipelineAggregation);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						histogramAggregation));

				indexingTestHelper.search();

				HistogramAggregationResult histogramAggregationResult =
					indexingTestHelper.getAggregationResult(
						histogramAggregation);

				AggregationAssert.assertBuckets(
					expectedBuckets, histogramAggregationResult);

				AggregationAssert.assertBucketValues(
					expectedBucketValues, this::getSumValue,
					histogramAggregationResult);
			});
	}

	protected double getSumValue(Bucket bucket) {
		Map<String, AggregationResult> childrenAggregationResults =
			bucket.getChildrenAggregationResults();

		SumAggregationResult sumAggregationResult =
			(SumAggregationResult)childrenAggregationResults.get("sum");

		return sumAggregationResult.getValue();
	}

}