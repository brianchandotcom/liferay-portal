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

import com.liferay.portal.kernel.search.filter.GeoDistanceFilter;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.elasticsearch.filter.GeoDistanceFilterTranslator;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoDistanceFilterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = GeoDistanceFilterTranslator.class)
public class GeoDistanceFilterTranslatorImpl
	implements GeoDistanceFilterTranslator {

	@Override
	public FilterBuilder translate(GeoDistanceFilter geoDistanceFilter) {
		GeoDistanceFilterBuilder geoDistanceFilterBuilder =
			FilterBuilders.geoDistanceFilter(geoDistanceFilter.getFieldName());

		geoDistanceFilterBuilder.cache(geoDistanceFilter.isCached());

		GeoLocationPoint pinGLP = geoDistanceFilter.getPinLocation();

		geoDistanceFilterBuilder.point(
			pinGLP.getLatitude(), pinGLP.getLongitude());

		geoDistanceFilterBuilder.distance(
			geoDistanceFilter.getGeoDistance().toString());

		return geoDistanceFilterBuilder;
	}

}