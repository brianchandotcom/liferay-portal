/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.GeoBoundingBoxQuery;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.geolocation.GeoExecType;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

/**
 * @author Michael C. Han
 */
public class GeoBoundingBoxQueryImpl
	extends BaseQueryImpl implements GeoBoundingBoxQuery {

	public GeoBoundingBoxQueryImpl(
		String field, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		_field = field;
		_topLeftGeoLocationPoint = topLeftGeoLocationPoint;
		_bottomRightGeoLocationPoint = bottomRightGeoLocationPoint;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public GeoLocationPoint getBottomRightGeoLocationPoint() {
		return _bottomRightGeoLocationPoint;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public GeoExecType getGeoExecType() {
		return _geoExecType;
	}

	@Override
	public GeoValidationMethod getGeoValidationMethod() {
		return _geoValidationMethod;
	}

	@Override
	public Boolean getIgnoreUnmapped() {
		return _ignoreUnmapped;
	}

	@Override
	public int getSortOrder() {
		return 120;
	}

	@Override
	public GeoLocationPoint getTopLeftGeoLocationPoint() {
		return _topLeftGeoLocationPoint;
	}

	@Override
	public void setGeoExecType(GeoExecType geoExecType) {
		_geoExecType = geoExecType;
	}

	@Override
	public void setGeoValidationMethod(
		GeoValidationMethod geoValidationMethod) {

		_geoValidationMethod = geoValidationMethod;
	}

	@Override
	public void setIgnoreUnmapped(Boolean ignoreUnmapped) {
		_ignoreUnmapped = ignoreUnmapped;
	}

	private static final long serialVersionUID = 1L;

	private final GeoLocationPoint _bottomRightGeoLocationPoint;
	private final String _field;
	private GeoExecType _geoExecType;
	private GeoValidationMethod _geoValidationMethod;
	private Boolean _ignoreUnmapped;
	private final GeoLocationPoint _topLeftGeoLocationPoint;

}