/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.elasticsearch7.internal.geolocation.GeoLocationPointTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.geolocation.GeoExecTypeTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.geolocation.GeoValidationMethodTranslator;
import com.liferay.portal.search.query.GeoBoundingBoxQuery;

import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = GeoBoundingBoxQueryTranslator.class)
public class GeoBoundingBoxQueryTranslatorImpl
	implements GeoBoundingBoxQueryTranslator {

	@Override
	public QueryBuilder translate(GeoBoundingBoxQuery geoBoundingBoxQuery) {
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder =
			QueryBuilders.geoBoundingBoxQuery(geoBoundingBoxQuery.getField());

		geoBoundingBoxQueryBuilder.setCorners(
			GeoLocationPointTranslator.translate(
				geoBoundingBoxQuery.getTopLeftGeoLocationPoint()),
			GeoLocationPointTranslator.translate(
				geoBoundingBoxQuery.getBottomRightGeoLocationPoint()));

		if (geoBoundingBoxQuery.getGeoExecType() != null) {
			geoBoundingBoxQueryBuilder.type(
				_geoExecTypeTranslator.translate(
					geoBoundingBoxQuery.getGeoExecType()));
		}

		if (geoBoundingBoxQuery.getGeoValidationMethod() != null) {
			geoBoundingBoxQueryBuilder.setValidationMethod(
				_geoValidationMethodTranslator.translate(
					geoBoundingBoxQuery.getGeoValidationMethod()));
		}

		if (geoBoundingBoxQuery.getIgnoreUnmapped() != null) {
			geoBoundingBoxQueryBuilder.ignoreUnmapped(
				geoBoundingBoxQuery.getIgnoreUnmapped());
		}

		return geoBoundingBoxQueryBuilder;
	}

	private final GeoExecTypeTranslator _geoExecTypeTranslator =
		new GeoExecTypeTranslator();
	private final GeoValidationMethodTranslator _geoValidationMethodTranslator =
		new GeoValidationMethodTranslator();

}