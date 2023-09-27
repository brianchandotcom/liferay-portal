/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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