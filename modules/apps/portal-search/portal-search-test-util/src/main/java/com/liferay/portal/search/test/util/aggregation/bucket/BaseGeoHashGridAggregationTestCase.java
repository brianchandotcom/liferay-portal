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
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseGeoHashGridAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testGeoHashGrid() throws Exception {
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 52.374081, 4.912350));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 52.369219, 4.901618));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 52.369219, 4.901618));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 51.222900, 4.405200));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 48.861111, 2.336389));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 48.860000, 2.327000));

		GeoHashGridAggregation geoHashGridAggregation =
			aggregations.geoHashGrid("geoHash", Field.GEO_LOCATION);

		geoHashGridAggregation.setPrecision(3);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						geoHashGridAggregation));

				indexingTestHelper.search();

				GeoHashGridAggregationResult geoBoundsAggregationResult =
					indexingTestHelper.getAggregationResult(
						geoHashGridAggregation);

				List<Bucket> buckets = new ArrayList<>(
					geoBoundsAggregationResult.getBuckets());

				Assert.assertEquals("Num buckets", 3, buckets.size());

				assertBucket(buckets.get(0), "u17", 3);
				assertBucket(buckets.get(1), "u09", 2);
				assertBucket(buckets.get(2), "u15", 1);
			});
	}

	protected void assertBucket(
		Bucket bucket, String expectedKey, long expectedCount) {

		Assert.assertEquals(expectedKey, bucket.getKey());
		Assert.assertEquals(expectedCount, bucket.getDocCount());
	}

}