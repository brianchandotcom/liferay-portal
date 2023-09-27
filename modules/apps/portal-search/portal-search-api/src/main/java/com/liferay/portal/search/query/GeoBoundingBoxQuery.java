/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.GeoExecType;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface GeoBoundingBoxQuery extends Query {

	public GeoLocationPoint getBottomRightGeoLocationPoint();

	public String getField();

	public GeoExecType getGeoExecType();

	public GeoValidationMethod getGeoValidationMethod();

	public Boolean getIgnoreUnmapped();

	public int getSortOrder();

	public GeoLocationPoint getTopLeftGeoLocationPoint();

	public void setGeoExecType(GeoExecType geoExecType);

	public void setGeoValidationMethod(GeoValidationMethod geoValidationMethod);

	public void setIgnoreUnmapped(Boolean ignoreUnmapped);

}