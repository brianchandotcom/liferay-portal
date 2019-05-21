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
import com.liferay.portal.search.aggregation.metrics.AvgAggregation;
import com.liferay.portal.search.aggregation.metrics.AvgAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseAvgAggregationTestCase extends BaseIndexingTestCase {

	@Test
	public void testAvgAggregation() throws Exception {
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 2));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 4));

		AvgAggregation avgAggregation = aggregations.avg("avg", Field.PRIORITY);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						avgAggregation));

				indexingTestHelper.search();

				AvgAggregationResult avgAggregationResult =
					indexingTestHelper.getAggregationResult(avgAggregation);

				Assert.assertEquals(3, avgAggregationResult.getValue(), 0);
			});
	}

}