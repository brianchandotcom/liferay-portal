/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

/**
 * @author Michael C. Han
 */
public abstract class GeoBoundsAggregationResult extends AggregationResult {

	public GeoBoundsAggregationResult(String name) {
		super(name);
	}

	public abstract GeoLocationPoint getBottomRight();

	public abstract GeoLocationPoint getTopLeft();

	public abstract void setBottomRight(GeoLocationPoint geoLocationPoint);

	public abstract void setTopLeft(GeoLocationPoint geoLocationPoint);

}