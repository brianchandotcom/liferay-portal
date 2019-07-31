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

import com.liferay.portal.kernel.search.filter.GeoDistanceRangeFilter;
import com.liferay.portal.kernel.search.geolocation.GeoDistance;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = GeoDistanceRangeFilterTranslator.class)
public class GeoDistanceRangeFilterTranslatorImpl
	implements GeoDistanceRangeFilterTranslator {

	@Override
	public QueryBuilder translate(
		GeoDistanceRangeFilter geoDistanceRangeFilter) {

		GeoDistanceQueryBuilder geoDistanceQueryBuilder =
			new GeoDistanceQueryBuilder(geoDistanceRangeFilter.getField());

		GeoDistance geoDistance =
			geoDistanceRangeFilter.getUpperBoundGeoDistance();

		geoDistanceQueryBuilder.distance(
			String.valueOf(geoDistance.getDistance()),
			DistanceUnit.fromString(
				String.valueOf(geoDistance.getDistanceUnit())));

		GeoLocationPoint geoLocationPoint =
			geoDistanceRangeFilter.getPinGeoLocationPoint();

		geoDistanceQueryBuilder.point(
			new GeoPoint(
				geoLocationPoint.getLatitude(),
				geoLocationPoint.getLongitude()));

		return geoDistanceQueryBuilder;
	}

}