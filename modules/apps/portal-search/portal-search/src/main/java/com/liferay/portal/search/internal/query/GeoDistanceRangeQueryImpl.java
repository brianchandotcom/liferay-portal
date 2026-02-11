/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.GeoDistanceRangeQuery;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.RangeTermQuery;
import com.liferay.portal.search.query.geolocation.ShapeRelation;

/**
 * @author Michael C. Han
 */
public class GeoDistanceRangeQueryImpl
	extends GeoDistanceRangeQuery {

	public GeoDistanceRangeQueryImpl(
		String field, boolean includesLower, boolean includesUpper,
		GeoDistance lowerBoundGeoDistance, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance upperBoundGeoDistance) {

		_field = field;
		_includesLower = includesLower;
		_includesUpper = includesUpper;

		_setOperators(includesLower, includesUpper);

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

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Object getLowerBound() {
		return _lowerBound;
	}

	@Override
	public RangeTermQuery.Operator getLowerBoundOperator() {
		return _lowerBoundOperator;
	}

	@Override
	public Object getUpperBound() {
		return _upperBound;
	}

	@Override
	public Operator getUpperBoundOperator() {
		return _upperBoundOperator;
	}

	@Override
	public boolean isIncludesLower() {
		return _includesLower;
	}

	@Override
	public boolean isIncludesUpper() {
		return _includesUpper;
	}

	@Override
	public void setLowerBound(Object lowerBound) {
		_lowerBound = lowerBound;
	}

	@Override
	public void setUpperBound(Object upperBound) {
		_upperBound = upperBound;
	}

	private String _toString() {
		return StringBundler.concat(
			"{(", _lowerBound, _lowerBoundOperator, _field, _upperBoundOperator,
			_upperBound, "), ", super.toString(), "}");
	}

	private void _setOperators(boolean includesLower, boolean includesUpper) {
		if (includesLower) {
			_lowerBoundOperator = Operator.GTE;
		}
		else {
			_lowerBoundOperator = Operator.GT;
		}

		if (includesUpper) {
			_upperBoundOperator = Operator.LTE;
		}
		else {
			_upperBoundOperator = Operator.LT;
		}
	}

	private final String _field;
	private final boolean _includesLower;
	private final boolean _includesUpper;
	private Object _lowerBound;
	private Operator _lowerBoundOperator;
	private Object _upperBound;
	private Operator _upperBoundOperator;

}