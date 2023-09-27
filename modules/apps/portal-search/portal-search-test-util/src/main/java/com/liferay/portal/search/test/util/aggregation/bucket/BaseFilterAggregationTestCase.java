/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.bucket;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseFilterAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testFilter() {
		index("SomeUser1", 1);
		index("SomeUser1", 2);
		index("SomeUser1", 3);
		index("SomeUser2", 4);
		index("SomeUser2", 5);
		index("SomeUser2", 6);
		index("SomeUser2", 7);

		FilterAggregation filterAggregation = aggregations.filter(
			"filter", queries.term(Field.USER_NAME, "SomeUser1"));

		SumAggregation sumAggregation = aggregations.sum("sum", Field.PRIORITY);

		filterAggregation.addChildAggregation(sumAggregation);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						filterAggregation));

				indexingTestHelper.search();

				FilterAggregationResult filterAggregationResult =
					indexingTestHelper.getAggregationResult(filterAggregation);

				Assert.assertEquals(
					"Filtered aggregation results", 3,
					filterAggregationResult.getDocCount());

				SumAggregationResult sumAggregationResult =
					indexingTestHelper.getChildAggregationResult(
						filterAggregationResult, sumAggregation);

				Assert.assertEquals(
					"Sum of priority in results", 6,
					sumAggregationResult.getValue(), 0);
			});
	}

	protected void index(String userName, int priority) {
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, userName);
				document.addNumber(Field.PRIORITY, priority);
			});
	}

}