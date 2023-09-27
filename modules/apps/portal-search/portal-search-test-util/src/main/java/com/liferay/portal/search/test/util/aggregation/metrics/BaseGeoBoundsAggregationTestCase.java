/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.metrics;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.metrics.GeoBoundsAggregation;
import com.liferay.portal.search.aggregation.metrics.GeoBoundsAggregationResult;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseGeoBoundsAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testGeoBoundsAggregation() throws Exception {
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 48.861111, 2.336389));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				Field.GEO_LOCATION, 48.860000, 2.327000));

		GeoBoundsAggregation geoBoundsAggregation = aggregations.geoBounds(
			"geoBounds", Field.GEO_LOCATION);

		geoBoundsAggregation.setWrapLongitude(true);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						geoBoundsAggregation));

				indexingTestHelper.search();

				GeoBoundsAggregationResult geoBoundsAggregationResult =
					indexingTestHelper.getAggregationResult(
						geoBoundsAggregation);

				Assert.assertNotNull(geoBoundsAggregationResult);

				GeoLocationPoint bottomRightGeoLocationPoint =
					geoBoundsAggregationResult.getBottomRight();

				Assert.assertNotNull(bottomRightGeoLocationPoint);

				Assert.assertEquals(
					48.85999997612089,
					bottomRightGeoLocationPoint.getLatitude(), 0);
				Assert.assertEquals(
					2.3363889567553997,
					bottomRightGeoLocationPoint.getLongitude(), 0);

				GeoLocationPoint topLeftGeoLocationPoint =
					geoBoundsAggregationResult.getTopLeft();

				Assert.assertNotNull(topLeftGeoLocationPoint);

				Assert.assertEquals(
					48.86111099738628, topLeftGeoLocationPoint.getLatitude(),
					0);
				Assert.assertEquals(
					2.3269999679178, topLeftGeoLocationPoint.getLongitude(), 0);
			});
	}

}