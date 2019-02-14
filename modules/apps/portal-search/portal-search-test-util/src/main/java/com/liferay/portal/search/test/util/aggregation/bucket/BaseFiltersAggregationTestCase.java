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

package com.liferay.portal.search.test.util.aggregation.bucket;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.BucketAggregationResult;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregation;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregationResult;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseFiltersAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testFilter() throws Exception {
		index("SomeUser1", 1);
		index("SomeUser1", 2);
		index("SomeUser1", 3);
		index("SomeUser2", 4);
		index("SomeUser2", 5);
		index("SomeUser2", 6);
		index("SomeUser2", 7);

		FiltersAggregation filtersAggregation = aggregations.filters(
			"filter", Field.USER_NAME);

		filtersAggregation.addKeyedQuery(
			"SomeUser1", queries.term(Field.USER_NAME, "SomeUser1"));

		filtersAggregation.addKeyedQuery(
			"SomeUser2", queries.term(Field.USER_NAME, "SomeUser2"));

		SumAggregation sumAggregation = aggregations.sum("sum", Field.PRIORITY);

		filtersAggregation.addChildAggregation(sumAggregation);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						filtersAggregation));

				indexingTestHelper.search();

				FiltersAggregationResult filtersAggregationResult =
					indexingTestHelper.getAggregationResult(filtersAggregation);

				assertBucket(
					"SomeUser1", 3, 6, filtersAggregationResult, sumAggregation,
					indexingTestHelper);

				assertBucket(
					"SomeUser2", 4, 22, filtersAggregationResult,
					sumAggregation, indexingTestHelper);

				Collection<Bucket> buckets =
					filtersAggregationResult.getBuckets();

				Assert.assertEquals("Number of buckets", 2, buckets.size());
			});
	}

	protected void assertBucket(
		String userName, int size, int sum,
		BucketAggregationResult bucketAggregationResult,
		SumAggregation sumAggregation, IndexingTestHelper indexingTestHelper) {

		Bucket bucket = bucketAggregationResult.getBucket(userName);

		Assert.assertEquals("bucket size", size, bucket.getDocCount());

		SumAggregationResult sumAggregationResult =
			indexingTestHelper.getChildAggregationResult(
				bucket, sumAggregation);

		Assert.assertEquals(
			"sum of priorities of " + userName, sum,
			sumAggregationResult.getValue(), 0);
	}

	protected void index(String userName, int priority) {
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, userName);
				document.addNumber(Field.PRIORITY, priority);
			});
	}

}