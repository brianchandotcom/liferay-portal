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

import com.liferay.portal.kernel.search.filter.GeoDistanceRangeFilter;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.elasticsearch.filter.GeoDistanceRangeFilterTranslator;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoDistanceRangeFilterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = GeoDistanceRangeFilterTranslator.class)
public class GeoDistanceRangeFilterTranslatorImpl
	implements GeoDistanceRangeFilterTranslator {

	@Override
	public FilterBuilder translate(
		GeoDistanceRangeFilter geoDistanceRangeFilter) {

		GeoDistanceRangeFilterBuilder geoDistanceRangeFilterBuilder =
			FilterBuilders.geoDistanceRangeFilter(
				geoDistanceRangeFilter.getField());

		if (geoDistanceRangeFilter.isCached() != null) {
			geoDistanceRangeFilterBuilder.cache(
				geoDistanceRangeFilter.isCached());
		}

		geoDistanceRangeFilterBuilder.from(
			String.valueOf(geoDistanceRangeFilter.getLowerBoundGeoDistance()));
		geoDistanceRangeFilterBuilder.includeLower(
			geoDistanceRangeFilter.isIncludesLower());
		geoDistanceRangeFilterBuilder.includeUpper(
			geoDistanceRangeFilter.isIncludesUpper());

		GeoLocationPoint pinGeoLocationPoint =
			geoDistanceRangeFilter.getPinGeoLocationPoint();

		geoDistanceRangeFilterBuilder.point(
			pinGeoLocationPoint.getLatitude(),
			pinGeoLocationPoint.getLongitude());

		geoDistanceRangeFilterBuilder.to(
			String.valueOf(geoDistanceRangeFilter.getUpperBoundGeoDistance()));

		return geoDistanceRangeFilterBuilder;
	}

}