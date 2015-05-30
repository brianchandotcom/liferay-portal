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
public class GeoDistanceFilter extends BaseFilter {

	public GeoDistanceFilter(
		String field, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance geoDistance) {

		_field = field;
		_pinGeoLocationPoint = pinGeoLocationPoint;
		_geoDistance = geoDistance;
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public GeoDistance getGeoDistance() {
		return _geoDistance;
	}

	public GeoLocationPoint getPinGeoLocationPoint() {
		return _pinGeoLocationPoint;
	}

	@Override
	public int getSortOrder() {
		return 100;
	}

	private final String _field;
	private final GeoDistance _geoDistance;
	private final GeoLocationPoint _pinGeoLocationPoint;

}