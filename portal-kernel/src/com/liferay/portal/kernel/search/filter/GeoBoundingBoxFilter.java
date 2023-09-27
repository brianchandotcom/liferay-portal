/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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