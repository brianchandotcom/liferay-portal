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

package com.liferay.portal.search.sort;

import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.GeoDistanceType;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.geolocation.GeoValidationMethod;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface GeoDistanceSort extends Sort {

	public void addGeoLocationPoints(GeoLocationPoint... geoLocationPoints);

	public DistanceUnit getDistanceUnit();

	public String getField();

	public GeoDistanceType getGeoDistanceType();

	public List<GeoLocationPoint> getGeoLocationPoints();

	public GeoValidationMethod getGeoValidationMethod();

	public NestedSort getNestedSort();

	public SortMode getSortMode();

	public void setDistanceUnit(DistanceUnit distanceUnit);

	public void setGeoDistanceType(GeoDistanceType geoDistanceType);

	public void setGeoValidationMethod(GeoValidationMethod geoValidationMethod);

	public void setNestedSort(NestedSort nestedSort);

	public void setSortMode(SortMode sortMode);

}