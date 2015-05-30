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
		String fieldName, GeoLocationPoint topLeft,
		GeoLocationPoint bottomRight) {

		_fieldName = fieldName;
		_topLeft = topLeft;
		_bottomRight = bottomRight;
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	public GeoLocationPoint getBottomRight() {
		return _bottomRight;
	}

	public String getFieldName() {
		return _fieldName;
	}

	@Override
	public int getSortOrder() {
		return 120;
	}

	public GeoLocationPoint getTopLeft() {
		return _topLeft;
	}

	private final GeoLocationPoint _bottomRight;
	private final String _fieldName;
	private final GeoLocationPoint _topLeft;

}