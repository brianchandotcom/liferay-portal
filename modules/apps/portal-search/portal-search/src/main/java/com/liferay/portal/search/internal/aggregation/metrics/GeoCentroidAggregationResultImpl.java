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

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.metrics.GeoCentroidAggregationResult;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.internal.aggregation.BaseAggregationResult;

/**
 * @author Michael C. Han
 */
public class GeoCentroidAggregationResultImpl
	extends BaseAggregationResult implements GeoCentroidAggregationResult {

	public GeoCentroidAggregationResultImpl(
		String name, GeoLocationPoint centroid, long count) {

		super(name);

		_centroid = centroid;
		_count = count;
	}

	@Override
	public GeoLocationPoint getCentroid() {
		return _centroid;
	}

	@Override
	public long getCount() {
		return _count;
	}

	private final GeoLocationPoint _centroid;
	private final long _count;

}