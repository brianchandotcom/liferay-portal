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
import com.liferay.portal.search.aggregation.metrics.MinAggregation;
import com.liferay.portal.search.aggregation.metrics.MinAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseMinAggregationTestCase extends BaseIndexingTestCase {

	@Test
	public void testMinAggregation() throws Exception {
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 1));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 5));

		MinAggregation minAggregation = aggregations.min("min", Field.PRIORITY);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						minAggregation));

				indexingTestHelper.search();

				MinAggregationResult minAggregationResult =
					indexingTestHelper.getAggregationResult(minAggregation);

				Assert.assertEquals(1, minAggregationResult.getValue(), 0);
			});
	}

}