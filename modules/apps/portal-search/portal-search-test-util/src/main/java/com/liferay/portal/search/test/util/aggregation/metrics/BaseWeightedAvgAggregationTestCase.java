/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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