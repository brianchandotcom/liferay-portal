/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.sort;

import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.GeoDistanceType;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.geolocation.GeoValidationMethod;
import com.liferay.portal.search.sort.GeoDistanceSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.SortMode;
import com.liferay.portal.search.sort.SortVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class GeoDistanceSortImpl
	extends BaseSortImpl implements GeoDistanceSort {

	public GeoDistanceSortImpl(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(SortVisitor<T> sortVisitor) {
		return sortVisitor.visit(this);
	}

	@Override
	public void addGeoLocationPoints(GeoLocationPoint... geoLocationPoints) {
		Collections.addAll(_geoLocationPoints, geoLocationPoints);
	}

	@Override
	public DistanceUnit getDistanceUnit() {
		return _distanceUnit;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public GeoDistanceType getGeoDistanceType() {
		return _geoDistanceType;
	}

	@Override
	public List<GeoLocationPoint> getGeoLocationPoints() {
		return Collections.unmodifiableList(_geoLocationPoints);
	}

	@Override
	public GeoValidationMethod getGeoValidationMethod() {
		return _geoValidationMethod;
	}

	@Override
	public NestedSort getNestedSort() {
		return _nestedSort;
	}

	@Override
	public SortMode getSortMode() {
		return _sortMode;
	}

	@Override
	public void setDistanceUnit(DistanceUnit distanceUnit) {
		_distanceUnit = distanceUnit;
	}

	@Override
	public void setGeoDistanceType(GeoDistanceType geoDistanceType) {
		_geoDistanceType = geoDistanceType;
	}

	@Override
	public void setGeoValidationMethod(
		GeoValidationMethod geoValidationMethod) {

		_geoValidationMethod = geoValidationMethod;
	}

	@Override
	public void setNestedSort(NestedSort nestedSort) {
		_nestedSort = nestedSort;
	}

	@Override
	public void setSortMode(SortMode sortMode) {
		_sortMode = sortMode;
	}

	private DistanceUnit _distanceUnit;
	private final String _field;
	private GeoDistanceType _geoDistanceType;
	private final List<GeoLocationPoint> _geoLocationPoints = new ArrayList<>();
	private GeoValidationMethod _geoValidationMethod;
	private NestedSort _nestedSort;
	private SortMode _sortMode;

}