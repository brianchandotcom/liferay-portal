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
public abstract class GeoShapeQuery extends Query {

	public abstract String getField();

	public abstract Boolean getIgnoreUnmapped();

	public abstract String getIndexedShapeId();

	public abstract String getIndexedShapeIndex();

	public abstract String getIndexedShapePath();

	public abstract String getIndexedShapeRouting();

	public abstract String getIndexedShapeType();

	public abstract Shape getShape();

	public abstract ShapeRelation getShapeRelation();

	public abstract SpatialStrategy getSpatialStrategy();

	public abstract void setIgnoreUnmapped(Boolean ignoreUnmapped);

	public abstract void setIndexedShapeIndex(String indexedShapeIndex);

	public abstract void setIndexedShapePath(String indexedShapePath);

	public abstract void setIndexedShapeRouting(String indexedShapeRouting);

	public abstract void setShapeRelation(ShapeRelation shapeRelation);

	public abstract void setSpatialStrategy(SpatialStrategy spatialStrategy);

}