/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

/**
 * @author Michael C. Han
 */
public class GeoDistanceQuery extends Query {

	public GeoDistanceQuery(
		String field, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance geoDistance) {

		_field = field;
		_pinGeoLocationPoint = pinGeoLocationPoint;
		_geoDistance = geoDistance;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public GeoDistance getGeoDistance() {
		return _geoDistance;
	}

	public GeoValidationMethod getGeoValidationMethod() {
		return _geoValidationMethod;
	}

	public Boolean getIgnoreUnmapped() {
		return _ignoreUnmapped;
	}

	public GeoLocationPoint getPinGeoLocationPoint() {
		return _pinGeoLocationPoint;
	}

	public int getSortOrder() {
		return 100;
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
	private final GeoDistance _geoDistance;
	private GeoValidationMethod _geoValidationMethod;
	private Boolean _ignoreUnmapped;
	private final GeoLocationPoint _pinGeoLocationPoint;

}