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

package com.liferay.portal.search.query;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

import java.util.Set;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface GeoPolygonQuery extends Query {

	public void addGeoLocationPoint(GeoLocationPoint geoLocationPoint);

	public String getField();

	public Set<GeoLocationPoint> getGeoLocationPoints();

	public GeoValidationMethod getGeoValidationMethod();

	public Boolean getIgnoreUnmapped();

	public int getSortOrder();

	public void setGeoValidationMethod(GeoValidationMethod geoValidationMethod);

	public void setIgnoreUnmapped(Boolean ignoreUnmapped);

}