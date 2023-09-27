/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

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