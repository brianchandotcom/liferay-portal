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
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregation;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseCardinalityAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testCardinalityAggregation() throws Exception {
		addDocument(
			DocumentCreationHelpers.singleKeyword(Field.USER_NAME, "a"));
		addDocument(
			DocumentCreationHelpers.singleKeyword(Field.USER_NAME, "a"));
		addDocument(
			DocumentCreationHelpers.singleKeyword(Field.USER_NAME, "b"));
		addDocument(
			DocumentCreationHelpers.singleKeyword(Field.USER_NAME, "b"));

		CardinalityAggregation cardinalityAggregation =
			aggregations.cardinality("cardinality", Field.USER_NAME);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						cardinalityAggregation));

				indexingTestHelper.search();

				CardinalityAggregationResult cardinalityAggregationResult =
					indexingTestHelper.getAggregationResult(
						cardinalityAggregation);

				Assert.assertEquals(2, cardinalityAggregationResult.getValue());
			});
	}

}