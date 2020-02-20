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