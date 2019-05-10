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

import com.liferay.portal.search.geolocation.Shape;
import com.liferay.portal.search.query.geolocation.ShapeRelation;
import com.liferay.portal.search.query.geolocation.SpatialStrategy;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface GeoShapeQuery extends Query {

	public String getField();

	public Boolean getIgnoreUnmapped();

	public String getIndexedShapeId();

	public String getIndexedShapeIndex();

	public String getIndexedShapePath();

	public String getIndexedShapeRouting();

	public String getIndexedShapeType();

	public Shape getShape();

	public ShapeRelation getShapeRelation();

	public SpatialStrategy getSpatialStrategy();

	public void setIgnoreUnmapped(Boolean ignoreUnmapped);

	public void setIndexedShapeIndex(String indexedShapeIndex);

	public void setIndexedShapePath(String indexedShapePath);

	public void setIndexedShapeRouting(String indexedShapeRouting);

	public void setShapeRelation(ShapeRelation shapeRelation);

	public void setSpatialStrategy(SpatialStrategy spatialStrategy);

}