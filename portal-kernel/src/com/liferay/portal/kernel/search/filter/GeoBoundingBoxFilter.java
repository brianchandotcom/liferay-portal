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

import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;

/**
 * @author Michael C. Han
 */
public class GeoBoundingBoxFilter extends BaseFilter {

	public GeoBoundingBoxFilter(
		String field, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		_field = field;
		_topLeftGeoLocationPoint = topLeftGeoLocationPoint;
		_bottomRightGeoLocationPoint = bottomRightGeoLocationPoint;
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	public GeoLocationPoint getBottomRightGeoLocationPoint() {
		return _bottomRightGeoLocationPoint;
	}

	public String getField() {
		return _field;
	}

	@Override
	public int getSortOrder() {
		return 120;
	}

	public GeoLocationPoint getTopLeftGeoLocationPoint() {
		return _topLeftGeoLocationPoint;
	}

	private final GeoLocationPoint _bottomRightGeoLocationPoint;
	private final String _field;
	private final GeoLocationPoint _topLeftGeoLocationPoint;

}