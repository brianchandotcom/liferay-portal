/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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