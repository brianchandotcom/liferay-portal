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
public class GeoDistanceSortImpl extends SortImpl implements GeoDistanceSort {

	public GeoDistanceSortImpl(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(SortVisitor<T> sortVisitor) {
		return sortVisitor.visit(this);
	}

	public void addGeoLocationPoints(GeoLocationPoint... geoLocationPoints) {
		Collections.addAll(_geoLocationPoints, geoLocationPoints);
	}

	public DistanceUnit getDistanceUnit() {
		return _distanceUnit;
	}

	public String getField() {
		return _field;
	}

	public GeoDistanceType getGeoDistanceType() {
		return _geoDistanceType;
	}

	public List<GeoLocationPoint> getGeoLocationPoints() {
		return Collections.unmodifiableList(_geoLocationPoints);
	}

	public GeoValidationMethod getGeoValidationMethod() {
		return _geoValidationMethod;
	}

	public NestedSort getNestedSort() {
		return _nestedSort;
	}

	public SortMode getSortMode() {
		return _sortMode;
	}

	public void setDistanceUnit(DistanceUnit distanceUnit) {
		_distanceUnit = distanceUnit;
	}

	public void setGeoDistanceType(GeoDistanceType geoDistanceType) {
		_geoDistanceType = geoDistanceType;
	}

	public void setGeoValidationMethod(
		GeoValidationMethod geoValidationMethod) {

		_geoValidationMethod = geoValidationMethod;
	}

	public void setNestedSort(NestedSort nestedSort) {
		_nestedSort = nestedSort;
	}

	public void setSortMode(SortMode sortMode) {
		_sortMode = sortMode;
	}

	private DistanceUnit _distanceUnit;
	private final String _field;
	private GeoDistanceType _geoDistanceType;
	private List<GeoLocationPoint> _geoLocationPoints = new ArrayList<>();
	private GeoValidationMethod _geoValidationMethod;
	private NestedSort _nestedSort;
	private SortMode _sortMode;

}