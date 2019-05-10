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

import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.ShapeRelation;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface GeoDistanceRangeQuery extends RangeTermQuery {

	public GeoDistance getLowerBoundGeoDistance();

	public GeoLocationPoint getPinGeoLocationPoint();

	public ShapeRelation getShapeRelation();

	@Override
	public int getSortOrder();

	public GeoDistance getUpperBoundGeoDistance();

	public void setShapeRelation(ShapeRelation shapeRelation);

}