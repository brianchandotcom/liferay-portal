/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.metrics;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregationResult;
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BasePercentilesAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testPercentilesAggregation() throws Exception {
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 10));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 20));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 30));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 40));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 50));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 60));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 70));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 80));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 90));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 100));

		PercentilesAggregation percentilesAggregation =
			aggregations.percentiles("percentiles", Field.PRIORITY);

		percentilesAggregation.setPercentilesMethod(PercentilesMethod.HDR);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						percentilesAggregation));

				indexingTestHelper.search();

				PercentilesAggregationResult percentilesAggregationResult =
					indexingTestHelper.getAggregationResult(
						percentilesAggregation);

				Map<Double, Double> percentilesMap =
					percentilesAggregationResult.getPercentiles();

				double[] percents = {1, 5, 25, 50, 75, 95, 99};
				double[] values = {
					10, 10, 30.0078125, 50.0234375, 80.0546875, 100.0546875,
					100.0546875
				};

				for (int i = 0; i < percents.length; i++) {
					Assert.assertEquals(
						values[i], percentilesMap.get(percents[i]), 0);
				}
			});
	}

}