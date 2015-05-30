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

package com.liferay.portal.kernel.search.filter;

import com.liferay.portal.kernel.search.geolocation.GeoDistance;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;

/**
 * @author Michael C. Han
 */
public class GeoDistanceRangeFilter extends RangeTermFilter {

	public GeoDistanceRangeFilter(
		String field, boolean includesLower, boolean includesUpper,
		GeoDistance lowerBoundGeoDistance, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance upperBoundGeoDistance) {

		super(field, includesLower, includesUpper);

		_lowerBoundGeoDistance = lowerBoundGeoDistance;
		_pinGeoLocationPoint = pinGeoLocationPoint;
		_upperBoundGeoDistance = upperBoundGeoDistance;
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	public GeoDistance getLowerBoundGeoDistance() {
		return _lowerBoundGeoDistance;
	}

	public GeoLocationPoint getPinGeoLocationPoint() {
		return _pinGeoLocationPoint;
	}

	@Override
	public int getSortOrder() {
		return 110;
	}

	public GeoDistance getUpperBoundGeoDistance() {
		return _upperBoundGeoDistance;
	}

	private final GeoDistance _lowerBoundGeoDistance;
	private final GeoLocationPoint _pinGeoLocationPoint;
	private final GeoDistance _upperBoundGeoDistance;

}