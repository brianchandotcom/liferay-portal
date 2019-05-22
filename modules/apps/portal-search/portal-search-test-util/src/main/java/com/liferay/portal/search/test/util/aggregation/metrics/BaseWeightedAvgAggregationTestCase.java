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

package com.liferay.portal.search.test.util.aggregation.metrics;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregation;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseWeightedAvgAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testWeightedAverage() {
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 1));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 2));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 3));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 4));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 5));

		WeightedAvgAggregation weightedAvgAggregation =
			aggregations.weightedAvg(
				"weighted_avg", Field.PRIORITY, Field.PRIORITY);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						weightedAvgAggregation));

				indexingTestHelper.search();

				WeightedAvgAggregationResult weightedAvgAggregationResult =
					indexingTestHelper.getAggregationResult(
						weightedAvgAggregation);

				Assert.assertEquals(
					3.66666666666666666666665,
					weightedAvgAggregationResult.getValue(), 0);
			});
	}

}