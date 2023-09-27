/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.metrics.GeoBoundsAggregationResult;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.internal.aggregation.BaseAggregationResult;

/**
 * @author Michael C. Han
 */
public class GeoBoundsAggregationResultImpl
	extends BaseAggregationResult implements GeoBoundsAggregationResult {

	public GeoBoundsAggregationResultImpl(
		String name, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		super(name);

		_topLeftGeoLocationPoint = topLeftGeoLocationPoint;
		_bottomRightGeoLocationPoint = bottomRightGeoLocationPoint;
	}

	@Override
	public GeoLocationPoint getBottomRight() {
		return _bottomRightGeoLocationPoint;
	}

	@Override
	public GeoLocationPoint getTopLeft() {
		return _topLeftGeoLocationPoint;
	}

	@Override
	public void setBottomRight(GeoLocationPoint geoLocationPoint) {
		_bottomRightGeoLocationPoint = geoLocationPoint;
	}

	@Override
	public void setTopLeft(GeoLocationPoint geoLocationPoint) {
		_topLeftGeoLocationPoint = geoLocationPoint;
	}

	private GeoLocationPoint _bottomRightGeoLocationPoint;
	private GeoLocationPoint _topLeftGeoLocationPoint;

}