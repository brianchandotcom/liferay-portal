/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.geolocation.Shape;
import com.liferay.portal.search.query.GeoShapeQuery;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.geolocation.ShapeRelation;
import com.liferay.portal.search.query.geolocation.SpatialStrategy;

/**
 * @author Michael C. Han
 */
public class GeoShapeQueryImpl extends BaseQueryImpl implements GeoShapeQuery {

	public GeoShapeQueryImpl(String field, Shape shape) {
		_field = field;
		_shape = shape;

		_indexedShapeId = null;
		_indexedShapeType = null;
	}

	public GeoShapeQueryImpl(
		String field, String indexedShapeId, String indexedShapeType) {

		_field = field;
		_indexedShapeId = indexedShapeId;
		_indexedShapeType = indexedShapeType;

		_shape = null;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Boolean getIgnoreUnmapped() {
		return _ignoreUnmapped;
	}

	@Override
	public String getIndexedShapeId() {
		return _indexedShapeId;
	}

	@Override
	public String getIndexedShapeIndex() {
		return _indexedShapeIndex;
	}

	@Override
	public String getIndexedShapePath() {
		return _indexedShapePath;
	}

	@Override
	public String getIndexedShapeRouting() {
		return _indexedShapeRouting;
	}

	@Override
	public String getIndexedShapeType() {
		return _indexedShapeType;
	}

	@Override
	public Shape getShape() {
		return _shape;
	}

	@Override
	public ShapeRelation getShapeRelation() {
		return _shapeRelation;
	}

	@Override
	public SpatialStrategy getSpatialStrategy() {
		return _spatialStrategy;
	}

	@Override
	public void setIgnoreUnmapped(Boolean ignoreUnmapped) {
		_ignoreUnmapped = ignoreUnmapped;
	}

	@Override
	public void setIndexedShapeIndex(String indexedShapeIndex) {
		_indexedShapeIndex = indexedShapeIndex;
	}

	@Override
	public void setIndexedShapePath(String indexedShapePath) {
		_indexedShapePath = indexedShapePath;
	}

	@Override
	public void setIndexedShapeRouting(String indexedShapeRouting) {
		_indexedShapeRouting = indexedShapeRouting;
	}

	@Override
	public void setShapeRelation(ShapeRelation shapeRelation) {
		_shapeRelation = shapeRelation;
	}

	@Override
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
	private final Shape _shape;
	private ShapeRelation _shapeRelation;
	private SpatialStrategy _spatialStrategy;

}