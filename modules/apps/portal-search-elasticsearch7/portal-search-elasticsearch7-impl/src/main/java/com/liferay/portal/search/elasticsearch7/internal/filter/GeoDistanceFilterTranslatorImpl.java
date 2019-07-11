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

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.search.filter.GeoDistanceFilter;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;

import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = GeoDistanceFilterTranslator.class)
public class GeoDistanceFilterTranslatorImpl
	implements GeoDistanceFilterTranslator {

	@Override
	public QueryBuilder translate(GeoDistanceFilter geoDistanceFilter) {
		GeoDistanceQueryBuilder geoDistanceQueryBuilder =
			QueryBuilders.geoDistanceQuery(geoDistanceFilter.getField());

		geoDistanceQueryBuilder.distance(
			String.valueOf(geoDistanceFilter.getGeoDistance()));

		GeoLocationPoint pinGeoLocationPoint =
			geoDistanceFilter.getPinGeoLocationPoint();

		geoDistanceQueryBuilder.point(
			pinGeoLocationPoint.getLatitude(),
			pinGeoLocationPoint.getLongitude());

		return geoDistanceQueryBuilder;
	}

}