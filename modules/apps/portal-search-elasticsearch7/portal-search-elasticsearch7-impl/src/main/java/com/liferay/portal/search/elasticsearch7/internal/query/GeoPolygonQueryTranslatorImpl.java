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

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.elasticsearch7.internal.geolocation.GeoLocationPointTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.geolocation.GeoValidationMethodTranslator;
import com.liferay.portal.search.query.GeoPolygonQuery;

import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = GeoPolygonQueryTranslator.class)
public class GeoPolygonQueryTranslatorImpl
	implements GeoPolygonQueryTranslator {

	@Override
	public QueryBuilder translate(GeoPolygonQuery geoPolygonQuery) {
		GeoPolygonQueryBuilder geoPolygonQueryBuilder =
			QueryBuilders.geoPolygonQuery(
				geoPolygonQuery.getField(),
				TransformUtil.transform(
					geoPolygonQuery.getGeoLocationPoints(),
					GeoLocationPointTranslator::translate));

		if (geoPolygonQuery.getGeoValidationMethod() != null) {
			geoPolygonQueryBuilder.setValidationMethod(
				_geoValidationMethodTranslator.translate(
					geoPolygonQuery.getGeoValidationMethod()));
		}

		if (geoPolygonQuery.getIgnoreUnmapped() != null) {
			geoPolygonQueryBuilder.ignoreUnmapped(
				geoPolygonQuery.getIgnoreUnmapped());
		}

		return geoPolygonQueryBuilder;
	}

	private final GeoValidationMethodTranslator _geoValidationMethodTranslator =
		new GeoValidationMethodTranslator();

}