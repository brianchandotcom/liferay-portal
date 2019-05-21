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
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregationResult;
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BasePercentileRanksAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testPercentileRanksAggregationHDR() throws Exception {
		index(10, 10, 10, 10, 20, 30, 40, 50);

		assertPercentileRanks(
			PercentilesMethod.HDR, new double[] {10, 20, 30, 40, 50},
			"{10.0=50.0, 20.0=62.5, 30.0=75.0, 40.0=87.5, 50.0=100.0}");
	}

	@Test
	public void testPercentileRanksAggregationTDigest() throws Exception {
		index(10, 10, 10, 10, 20, 30, 40, 50);

		assertPercentileRanks(
			PercentilesMethod.TDIGEST, new double[] {10, 20, 30, 40, 50},
			"{10.0=37.5, 20.0=56.25, 30.0=68.75, 40.0=81.25, 50.0=100.0}");
	}

	protected void assertPercentileRanks(
		PercentilesMethod percentilesMethod, double[] values, String expected) {

		PercentileRanksAggregation percentileRanksAggregation =
			aggregations.percentileRanks(
				"percentileRanks", Field.PRIORITY, values);

		percentileRanksAggregation.setPercentilesMethod(percentilesMethod);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						percentileRanksAggregation));

				indexingTestHelper.search();

				PercentileRanksAggregationResult
					percentileRanksAggregationResult =
						indexingTestHelper.getAggregationResult(
							percentileRanksAggregation);

				Assert.assertEquals(
					expected,
					String.valueOf(
						percentileRanksAggregationResult.getPercentiles()));
			});
	}

	protected void index(int... values) {
		for (int value : values) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, value));
		}
	}

}