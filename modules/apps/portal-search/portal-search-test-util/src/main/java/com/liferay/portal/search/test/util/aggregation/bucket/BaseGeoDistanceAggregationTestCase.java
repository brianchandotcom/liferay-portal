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
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregationResult;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.internal.aggregation.bucket.GeoDistanceAggregationImpl;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BaseGeoDistanceAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testGeoDistance() throws Exception {
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

		GeoDistanceAggregation geoDistanceAggregation =
			new GeoDistanceAggregationImpl(
				"geo_distance", Field.GEO_LOCATION,
				new GeoLocationPoint(52.3760, 4.894));

		geoDistanceAggregation.addRange(new Range(null, 100000.0));
		geoDistanceAggregation.addRange(new Range(null, 100000.0, 300000.0));
		geoDistanceAggregation.addRange(new Range(300000.0, null));

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						geoDistanceAggregation));

				indexingTestHelper.search();

				GeoDistanceAggregationResult geoDistanceAggregationResult =
					indexingTestHelper.getAggregationResult(
						geoDistanceAggregation);

				List<Bucket> buckets = new ArrayList<>(
					geoDistanceAggregationResult.getBuckets());

				Assert.assertEquals(buckets.toString(), 3, buckets.size());

				assertBucket(buckets.get(0), "*-100000.0", 3);
				assertBucket(buckets.get(1), "100000.0-300000.0", 1);
				assertBucket(buckets.get(2), "300000.0-*", 2);
			});
	}

	protected void assertBucket(
		Bucket bucket, String expectedKey, long expectedCount) {

		Assert.assertEquals(expectedKey, bucket.getKey());
		Assert.assertEquals(expectedCount, bucket.getDocCount());
	}

}