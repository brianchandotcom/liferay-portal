/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.metrics;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregation;
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseExtendedStatsAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testExtendedStatsAggregation() throws Exception {
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 50));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 100));

		ExtendedStatsAggregation extendedStatsAggregation =
			aggregations.extendedStats("extendedStats", Field.PRIORITY);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						extendedStatsAggregation));

				indexingTestHelper.search();

				ExtendedStatsAggregationResult extendedStatsAggregationResult =
					indexingTestHelper.getAggregationResult(
						extendedStatsAggregation);

				Assert.assertEquals(
					75, extendedStatsAggregationResult.getAvg(), 0);
				Assert.assertEquals(
					2, extendedStatsAggregationResult.getCount());
				Assert.assertEquals(
					100, extendedStatsAggregationResult.getMax(), 0);
				Assert.assertEquals(
					50, extendedStatsAggregationResult.getMin(), 0);
				Assert.assertEquals(
					25, extendedStatsAggregationResult.getStdDeviation(), 0);
				Assert.assertEquals(
					150, extendedStatsAggregationResult.getSum(), 0);
				Assert.assertEquals(
					12500, extendedStatsAggregationResult.getSumOfSquares(), 0);
				Assert.assertEquals(
					625, extendedStatsAggregationResult.getVariance(), 0);
			});
	}

}