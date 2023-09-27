/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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