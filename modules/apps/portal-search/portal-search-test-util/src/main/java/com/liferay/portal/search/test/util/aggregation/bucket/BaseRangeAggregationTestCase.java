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
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
import com.liferay.portal.search.aggregation.bucket.RangeAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseRangeAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testKeyedRanges() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		RangeAggregation rangeAggregation = getAggregation();

		rangeAggregation.addRange(new Range(11.0, null));
		rangeAggregation.addRange(new Range(0.0, 11.0));

		rangeAggregation.setKeyed(true);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						rangeAggregation));

				indexingTestHelper.search();

				RangeAggregationResult rangeAggregationResult =
					indexingTestHelper.getAggregationResult(rangeAggregation);

				List<Bucket> buckets = new ArrayList<>(
					rangeAggregationResult.getBuckets());

				Assert.assertEquals("Num buckets", 2, buckets.size());

				assertBucket(buckets.get(0), "0.0-11.0", 10);
				assertBucket(buckets.get(1), "11.0-*", 10);
			});
	}

	@Test
	public void testRanges() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		RangeAggregation rangeAggregation = getAggregation();

		rangeAggregation.addRange(new Range("< 6", null, 6.0));
		rangeAggregation.addRange(new Range(">=6, <=10", 6.0, 11.0));
		rangeAggregation.addRange(new Range("> 10", 11.0, null));

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						rangeAggregation));

				indexingTestHelper.search();

				RangeAggregationResult rangeAggregationResult =
					indexingTestHelper.getAggregationResult(rangeAggregation);

				List<Bucket> buckets = new ArrayList<>(
					rangeAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 3, buckets.size());

				assertBucket(buckets.get(0), "< 6", 5);
				assertBucket(buckets.get(1), ">=6, <=10", 5);
				assertBucket(buckets.get(2), "> 10", 10);
			});
	}

	protected void assertBucket(
		Bucket bucket, String expectedKey, long expectedCount) {

		Assert.assertEquals(expectedKey, bucket.getKey());
		Assert.assertEquals(expectedCount, bucket.getDocCount());
	}

	protected RangeAggregation getAggregation() {
		return aggregations.range("range", Field.PRIORITY);
	}

}