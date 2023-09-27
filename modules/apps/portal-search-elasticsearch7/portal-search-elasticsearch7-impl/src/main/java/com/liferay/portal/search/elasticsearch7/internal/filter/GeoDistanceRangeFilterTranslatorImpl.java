/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.filter;

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
@Component(service = GeoDistanceRangeFilterTranslator.class)
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