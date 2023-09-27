/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.filter;

import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class GeoPolygonFilter extends BaseFilter {

	public GeoPolygonFilter(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
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

	@Override
	public int getSortOrder() {
		return 140;
	}

	private final String _field;
	private final Set<GeoLocationPoint> _geoLocationPoints = new HashSet<>();

}