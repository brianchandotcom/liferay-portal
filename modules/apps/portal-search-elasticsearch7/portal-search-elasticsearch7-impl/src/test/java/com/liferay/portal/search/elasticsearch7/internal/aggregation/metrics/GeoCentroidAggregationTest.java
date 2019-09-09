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

package com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.GeoCentroidAggregation;
import com.liferay.portal.search.aggregation.metrics.GeoCentroidAggregationResult;
import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.test.util.aggregation.metrics.BaseGeoCentroidAggregationTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public class GeoCentroidAggregationTest
	extends BaseGeoCentroidAggregationTestCase {

	@Override
	@Test
	public void testGeoCentroidAggregation() throws Exception {
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 52.374081, 4.912350));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 52.369219, 4.901618));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 52.371667, 4.914722));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 51.222900, 4.405200));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 48.861111, 2.336389));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 48.860000, 2.327000));

		GeoCentroidAggregation geoBoundsAggregation = aggregations.geoCentroid(
			"geoCentroid", Field.GEO_LOCATION);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						geoBoundsAggregation));

				indexingTestHelper.search();

				GeoCentroidAggregationResult geoCentroidAggregationResult =
					indexingTestHelper.getAggregationResult(
						geoBoundsAggregation);

				Assert.assertNotNull(geoCentroidAggregationResult);

				Assert.assertEquals(6, geoCentroidAggregationResult.getCount());

				GeoLocationPoint geoLocationPoint =
					geoCentroidAggregationResult.getCentroid();

				Assert.assertEquals(
					51.00982965203002, geoLocationPoint.getLatitude(), 0);
				Assert.assertEquals(
					3.9662131341174245, geoLocationPoint.getLongitude(), 0);
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

}