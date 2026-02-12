/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

/**
 * @author Michael C. Han
 */
public class GeoBoundsAggregationResult extends AggregationResult {

	public GeoBoundsAggregationResult(
		String name, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		super(name);

		_topLeftGeoLocationPoint = topLeftGeoLocationPoint;
		_bottomRightGeoLocationPoint = bottomRightGeoLocationPoint;
	}

	public GeoLocationPoint getBottomRight() {
		return _bottomRightGeoLocationPoint;
	}

	public GeoLocationPoint getTopLeft() {
		return _topLeftGeoLocationPoint;
	}

	public void setBottomRight(GeoLocationPoint geoLocationPoint) {
		_bottomRightGeoLocationPoint = geoLocationPoint;
	}

	public void setTopLeft(GeoLocationPoint geoLocationPoint) {
		_topLeftGeoLocationPoint = geoLocationPoint;
	}

	private GeoLocationPoint _bottomRightGeoLocationPoint;
	private GeoLocationPoint _topLeftGeoLocationPoint;

}