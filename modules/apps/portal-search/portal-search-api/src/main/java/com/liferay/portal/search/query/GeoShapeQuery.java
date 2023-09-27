/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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