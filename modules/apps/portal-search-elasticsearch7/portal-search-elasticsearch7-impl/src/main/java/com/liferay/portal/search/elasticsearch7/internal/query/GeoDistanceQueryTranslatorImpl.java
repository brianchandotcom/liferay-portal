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

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.elasticsearch7.internal.query.geolocation.GeoValidationMethodTranslator;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.GeoDistanceQuery;

import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = GeoDistanceQueryTranslator.class)
public class GeoDistanceQueryTranslatorImpl
	implements GeoDistanceQueryTranslator {

	@Override
	public QueryBuilder translate(GeoDistanceQuery geoDistanceQuery) {
		GeoDistanceQueryBuilder geoDistanceQueryBuilder =
			QueryBuilders.geoDistanceQuery(geoDistanceQuery.getField());

		geoDistanceQueryBuilder.distance(
			String.valueOf(geoDistanceQuery.getGeoDistance()));

		GeoLocationPoint pinGeoLocationPoint =
			geoDistanceQuery.getPinGeoLocationPoint();

		geoDistanceQueryBuilder.point(
			pinGeoLocationPoint.getLatitude(),
			pinGeoLocationPoint.getLongitude());

		if (geoDistanceQuery.getGeoValidationMethod() != null) {
			geoDistanceQueryBuilder.setValidationMethod(
				_geoValidationMethodTranslator.translate(
					geoDistanceQuery.getGeoValidationMethod()));
		}

		if (geoDistanceQuery.getIgnoreUnmapped() != null) {
			geoDistanceQueryBuilder.ignoreUnmapped(
				geoDistanceQuery.getIgnoreUnmapped());
		}

		return geoDistanceQueryBuilder;
	}

	private final GeoValidationMethodTranslator _geoValidationMethodTranslator =
		new GeoValidationMethodTranslator();

}