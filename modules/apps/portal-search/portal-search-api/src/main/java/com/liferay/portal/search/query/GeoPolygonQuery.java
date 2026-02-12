/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class GeoPolygonQuery extends Query {

	public GeoPolygonQuery(String field) {
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