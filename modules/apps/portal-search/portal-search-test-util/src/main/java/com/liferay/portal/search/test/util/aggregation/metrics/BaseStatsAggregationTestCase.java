/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.metrics;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.StatsAggregation;
import com.liferay.portal.search.aggregation.metrics.StatsAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseStatsAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testStatsAggregation() throws Exception {
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 50));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 100));

		StatsAggregation statsAggregation = aggregations.stats(
			"stats", Field.PRIORITY);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						statsAggregation));

				indexingTestHelper.search();

				StatsAggregationResult statsAggregationResult =
					indexingTestHelper.getAggregationResult(statsAggregation);

				Assert.assertEquals(75, statsAggregationResult.getAvg(), 0);
				Assert.assertEquals(2, statsAggregationResult.getCount());
				Assert.assertEquals(100, statsAggregationResult.getMax(), 0);
				Assert.assertEquals(50, statsAggregationResult.getMin(), 0);
				Assert.assertEquals(150, statsAggregationResult.getSum(), 0);
			});
	}

}