/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface GeoBoundsAggregationResult extends AggregationResult {

	public GeoLocationPoint getBottomRight();

	public GeoLocationPoint getTopLeft();

	public void setBottomRight(GeoLocationPoint geoLocationPoint);

	public void setTopLeft(GeoLocationPoint geoLocationPoint);

}