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

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.query.GeoShapeQuery;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.geolocation.ShapeRelation;
import com.liferay.portal.search.query.geolocation.SpatialStrategy;

/**
 * @author Michael C. Han
 */
public class GeoShapeQueryImpl extends BaseQueryImpl implements GeoShapeQuery {

	public GeoShapeQueryImpl(String field, ShapeBuilder shapeBuilder) {
		_field = field;
		_shapeBuilder = shapeBuilder;

		_indexedShapeId = null;
		_indexedShapeType = null;
	}

	public GeoShapeQueryImpl(
		String field, String indexedShapeId, String indexedShapeType) {

		_field = field;
		_indexedShapeId = indexedShapeId;
		_indexedShapeType = indexedShapeType;

		_shapeBuilder = null;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public Boolean getIgnoreUnmapped() {
		return _ignoreUnmapped;
	}

	public String getIndexedShapeId() {
		return _indexedShapeId;
	}

	public String getIndexedShapeIndex() {
		return _indexedShapeIndex;
	}

	public String getIndexedShapePath() {
		return _indexedShapePath;
	}

	public String getIndexedShapeRouting() {
		return _indexedShapeRouting;
	}

	public String getIndexedShapeType() {
		return _indexedShapeType;
	}

	public ShapeBuilder getShapeBuilder() {
		return _shapeBuilder;
	}

	public ShapeRelation getShapeRelation() {
		return _shapeRelation;
	}

	public SpatialStrategy getSpatialStrategy() {
		return _spatialStrategy;
	}

	public void setIgnoreUnmapped(Boolean ignoreUnmapped) {
		_ignoreUnmapped = ignoreUnmapped;
	}

	public void setIndexedShapeIndex(String indexedShapeIndex) {
		_indexedShapeIndex = indexedShapeIndex;
	}

	public void setIndexedShapePath(String indexedShapePath) {
		_indexedShapePath = indexedShapePath;
	}

	public void setIndexedShapeRouting(String indexedShapeRouting) {
		_indexedShapeRouting = indexedShapeRouting;
	}

	public void setShapeRelation(ShapeRelation shapeRelation) {
		_shapeRelation = shapeRelation;
	}

	public void setSpatialStrategy(SpatialStrategy spatialStrategy) {
		_spatialStrategy = spatialStrategy;
	}

	private static final long serialVersionUID = 1L;

	private final String _field;
	private Boolean _ignoreUnmapped;
	private final String _indexedShapeId;
	private String _indexedShapeIndex;
	private String _indexedShapePath;
	private String _indexedShapeRouting;
	private final String _indexedShapeType;
	private final ShapeBuilder _shapeBuilder;
	private ShapeRelation _shapeRelation;
	private SpatialStrategy _spatialStrategy;

}