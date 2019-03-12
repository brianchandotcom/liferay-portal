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

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.GeoDistanceRangeQuery;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.geolocation.ShapeRelation;

/**
 * @author Michael C. Han
 */
public class GeoDistanceRangeQueryImpl
	extends RangeTermQueryImpl implements GeoDistanceRangeQuery {

	public GeoDistanceRangeQueryImpl(
		String field, boolean includesLower, boolean includesUpper,
		GeoDistance lowerBoundGeoDistance, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance upperBoundGeoDistance) {

		super(field, includesLower, includesUpper);

		_lowerBoundGeoDistance = lowerBoundGeoDistance;
		_pinGeoLocationPoint = pinGeoLocationPoint;
		_upperBoundGeoDistance = upperBoundGeoDistance;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public GeoDistance getLowerBoundGeoDistance() {
		return _lowerBoundGeoDistance;
	}

	public GeoLocationPoint getPinGeoLocationPoint() {
		return _pinGeoLocationPoint;
	}

	public ShapeRelation getShapeRelation() {
		return _shapeRelation;
	}

	@Override
	public int getSortOrder() {
		return 110;
	}

	public GeoDistance getUpperBoundGeoDistance() {
		return _upperBoundGeoDistance;
	}

	public void setShapeRelation(ShapeRelation shapeRelation) {
		_shapeRelation = shapeRelation;
	}

	private static final long serialVersionUID = 1L;

	private final GeoDistance _lowerBoundGeoDistance;
	private final GeoLocationPoint _pinGeoLocationPoint;
	private ShapeRelation _shapeRelation;
	private final GeoDistance _upperBoundGeoDistance;

}