/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class GeoDistanceQuery extends Query {

	public abstract String getField();

	public abstract GeoDistance getGeoDistance();

	public abstract GeoValidationMethod getGeoValidationMethod();

	public abstract Boolean getIgnoreUnmapped();

	public abstract GeoLocationPoint getPinGeoLocationPoint();

	public abstract int getSortOrder();

	public abstract void setGeoValidationMethod(
		GeoValidationMethod geoValidationMethod);

	public abstract void setIgnoreUnmapped(Boolean ignoreUnmapped);

}