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

import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.GeoPolygonQuery;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class GeoPolygonQueryImpl
	extends BaseQueryImpl implements GeoPolygonQuery {

	public GeoPolygonQueryImpl(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public void addGeoLocationPoint(GeoLocationPoint geoLocationPoint) {
		_geoLocationPoints.add(geoLocationPoint);
	}

	public String getField() {
		return _field;
	}

	public Set<GeoLocationPoint> getGeoLocationPoints() {
		return Collections.unmodifiableSet(_geoLocationPoints);
	}

	public GeoValidationMethod getGeoValidationMethod() {
		return _geoValidationMethod;
	}

	public Boolean getIgnoreUnmapped() {
		return _ignoreUnmapped;
	}

	public int getSortOrder() {
		return 140;
	}

	public void setGeoValidationMethod(
		GeoValidationMethod geoValidationMethod) {

		_geoValidationMethod = geoValidationMethod;
	}

	public void setIgnoreUnmapped(Boolean ignoreUnmapped) {
		_ignoreUnmapped = ignoreUnmapped;
	}

	private static final long serialVersionUID = 1L;

	private final String _field;
	private final Set<GeoLocationPoint> _geoLocationPoints = new HashSet<>();
	private GeoValidationMethod _geoValidationMethod;
	private Boolean _ignoreUnmapped;

}