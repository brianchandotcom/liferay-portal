/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public GeoDistance getLowerBoundGeoDistance() {
		return _lowerBoundGeoDistance;
	}

	@Override
	public GeoLocationPoint getPinGeoLocationPoint() {
		return _pinGeoLocationPoint;
	}

	@Override
	public ShapeRelation getShapeRelation() {
		return _shapeRelation;
	}

	@Override
	public int getSortOrder() {
		return 110;
	}

	@Override
	public GeoDistance getUpperBoundGeoDistance() {
		return _upperBoundGeoDistance;
	}

	@Override
	public void setShapeRelation(ShapeRelation shapeRelation) {
		_shapeRelation = shapeRelation;
	}

	private static final long serialVersionUID = 1L;

	private final GeoDistance _lowerBoundGeoDistance;
	private final GeoLocationPoint _pinGeoLocationPoint;
	private ShapeRelation _shapeRelation;
	private final GeoDistance _upperBoundGeoDistance;

}