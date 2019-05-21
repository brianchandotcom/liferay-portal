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
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.MovingFunctionPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MovingFunctionPipelineAggregationResult;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.test.util.aggregation.AggregationAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Map;

import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseMovingFunctionPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testMovingFunction() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		HistogramAggregation histogramAggregation =
			aggregationFixture.getDefaultHistogramAggregation();

		Script script = scripts.script("MovingFunctions.unweightedAvg(values)");

		MovingFunctionPipelineAggregation movingFunctionPipelineAggregation =
			aggregations.movingFunction("moving_fn", script, "sum", 5);

		histogramAggregation.addPipelineAggregation(
			movingFunctionPipelineAggregation);

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
					"[0.0=4, 5.0=5, 10.0=5, 15.0=5, 20.0=1]",
					histogramAggregationResult);

				AggregationAssert.assertBucketValues(
					"NaN, 10.0, 22.5, 35.0, 47.5", this::getMovingFunctionValue,
					histogramAggregationResult);
			});
	}

	protected double getMovingFunctionValue(Bucket bucket) {
		Map<String, AggregationResult> childrenAggregationResults =
			bucket.getChildrenAggregationResults();

		MovingFunctionPipelineAggregationResult
			movingFunctionPipelineAggregationResult =
				(MovingFunctionPipelineAggregationResult)
					childrenAggregationResults.get("moving_fn");

		return movingFunctionPipelineAggregationResult.getValue();
	}

}