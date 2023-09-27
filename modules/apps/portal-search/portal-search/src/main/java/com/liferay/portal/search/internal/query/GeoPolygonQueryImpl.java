/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public void addGeoLocationPoint(GeoLocationPoint geoLocationPoint) {
		_geoLocationPoints.add(geoLocationPoint);
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Set<GeoLocationPoint> getGeoLocationPoints() {
		return Collections.unmodifiableSet(_geoLocationPoints);
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
		return 140;
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

	private final String _field;
	private final Set<GeoLocationPoint> _geoLocationPoints = new HashSet<>();
	private GeoValidationMethod _geoValidationMethod;
	private Boolean _ignoreUnmapped;

}