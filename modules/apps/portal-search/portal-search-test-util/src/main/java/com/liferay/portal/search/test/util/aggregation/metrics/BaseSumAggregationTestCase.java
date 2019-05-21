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
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseSumAggregationTestCase extends BaseIndexingTestCase {

	@Test
	public void testSumAggregation() throws Exception {
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 1));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 5));

		SumAggregation sumAggregation = aggregations.sum("sum", Field.PRIORITY);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						sumAggregation));

				indexingTestHelper.search();

				SumAggregationResult sumAggregationResult =
					indexingTestHelper.getAggregationResult(sumAggregation);

				Assert.assertEquals(6, sumAggregationResult.getValue(), 0);
			});
	}

}