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

package com.liferay.portal.search.elasticsearch.internal.filter;

import com.liferay.portal.kernel.search.filter.GeoBoundingBoxFilter;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.elasticsearch.filter.GeoBoundingBoxFilterTranslator;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoBoundingBoxFilterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = GeoBoundingBoxFilterTranslator.class)
public class GeoBoundingBoxFilterTranslatorImpl
	implements GeoBoundingBoxFilterTranslator {

	@Override
	public FilterBuilder translate(GeoBoundingBoxFilter geoBoundingBoxFilter) {
		GeoBoundingBoxFilterBuilder geoBoundingBoxFilterBuilder =
			FilterBuilders.geoBoundingBoxFilter(
				geoBoundingBoxFilter.getField());

		GeoLocationPoint bottomRightGeoLocationPoint =
			geoBoundingBoxFilter.getBottomRightGeoLocationPoint();

		GeoPoint bottomRightGeoPoint = new GeoPoint(
			bottomRightGeoLocationPoint.getLatitude(),
			bottomRightGeoLocationPoint.getLongitude());

		geoBoundingBoxFilterBuilder.bottomRight(bottomRightGeoPoint);

		if (geoBoundingBoxFilter.isCached() != null) {
			geoBoundingBoxFilterBuilder.cache(geoBoundingBoxFilter.isCached());
		}

		GeoLocationPoint topLeftGeoLocationPoint =
			geoBoundingBoxFilter.getTopLeftGeoLocationPoint();

		GeoPoint topLeftGeoPoint = new GeoPoint(
			topLeftGeoLocationPoint.getLatitude(),
			topLeftGeoLocationPoint.getLongitude());

		geoBoundingBoxFilterBuilder.topLeft(topLeftGeoPoint);

		return geoBoundingBoxFilterBuilder;
	}

}