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

package com.liferay.portal.search.elasticsearch6.internal.filter;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.GeoDistanceFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceRangeFilter;
import com.liferay.portal.kernel.search.geolocation.GeoDistance;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.elasticsearch6.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class GeoDistanceFilterTest extends BaseIndexingTestCase {

	@Test
	public void testGeoDistanceFilter() throws Exception {
		index(33.9987, -117.8129);
		index(34.0003, -117.8127);

		GeoLocationPoint geoLocationPoint = new GeoLocationPoint(
			33.9977, -117.8145);

		assertCountWithinDistance(2, 500.0, geoLocationPoint);

		assertCountWithinDistance(1, 250.0, geoLocationPoint);

		assertCountWithinDistance(0, 100.0, geoLocationPoint);
	}

	@Test
	public void testGeoDistanceRangeFilter() throws Exception {
		index(33.9987, -117.8129);
		index(34.0003, -117.8127);

		GeoLocationPoint geoLocationPoint = new GeoLocationPoint(
			33.9977, -117.8145);

		assertCountWithinDistanceRange(2, 100.0, 600.0, geoLocationPoint);

		assertCountWithinDistanceRange(1, 160.0, 300.0, geoLocationPoint);

		assertCountWithinDistanceRange(0, 50.0, 150.0, geoLocationPoint);
	}

	protected void assertCount(int expected, Filter filter) throws Exception {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(filter);

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> Assert.assertEquals(
						indexingTestHelper.getRequestString(), expected,
						hits.getLength()));
			});
	}

	protected void assertCountWithinDistance(
			int expected, double distance, GeoLocationPoint geoLocationPoint)
		throws Exception {

		assertCount(
			expected,
			new GeoDistanceFilter(
				FIELD, geoLocationPoint, new GeoDistance(distance)));
	}

	protected void assertCountWithinDistanceRange(
			int expected, double fromDistance, double toDistance,
			GeoLocationPoint geoLocationPoint)
		throws Exception {

		assertCount(
			expected,
			new GeoDistanceRangeFilter(
				FIELD, true, true, new GeoDistance(fromDistance),
				geoLocationPoint, new GeoDistance(toDistance)));
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	protected void index(double latitude, double longitude) throws Exception {
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(
				FIELD, latitude, longitude));
	}

	@Override
	protected void setPreBooleanFilter(Filter filter, Query query) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(filter, BooleanClauseOccur.MUST);

		query.setPreBooleanFilter(booleanFilter);
	}

	protected static final String FIELD = Field.GEO_LOCATION;

}