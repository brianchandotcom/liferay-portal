/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.metrics;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregation;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseValueCountAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testValueCountAggregation() {
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 1));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 2));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 3));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 4));
		addDocument(DocumentCreationHelpers.singleNumber(Field.PRIORITY, 5));

		ValueCountAggregation valueCountAggregation = aggregations.valueCount(
			"valueCount", Field.PRIORITY);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						valueCountAggregation));

				indexingTestHelper.search();

				ValueCountAggregationResult valueCountAggregationResult =
					indexingTestHelper.getAggregationResult(
						valueCountAggregation);

				Assert.assertEquals(
					5, valueCountAggregationResult.getValue(), 0);
			});
	}

}