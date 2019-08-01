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

package com.liferay.portal.search.elasticsearch6.internal.query;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.search.elasticsearch6.internal.geolocation.ElasticsearchShapeTranslator;
import com.liferay.portal.search.geolocation.Shape;
import com.liferay.portal.search.query.GeoShapeQuery;
import com.liferay.portal.search.query.geolocation.ShapeRelation;
import com.liferay.portal.search.query.geolocation.SpatialStrategy;

import java.io.IOException;

import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = GeoShapeQueryTranslator.class)
public class GeoShapeQueryTranslatorImpl implements GeoShapeQueryTranslator {

	@Override
	public QueryBuilder translate(GeoShapeQuery geoShapeQuery) {
		GeoShapeQueryBuilder geoShapeQueryBuilder = translateQuery(
			geoShapeQuery);

		if (geoShapeQuery.getIgnoreUnmapped() != null) {
			geoShapeQueryBuilder.ignoreUnmapped(
				geoShapeQuery.getIgnoreUnmapped());
		}

		if (geoShapeQuery.getShapeRelation() != null) {
			geoShapeQueryBuilder.relation(
				translate(geoShapeQuery.getShapeRelation()));
		}

		if (geoShapeQuery.getSpatialStrategy() != null) {
			geoShapeQueryBuilder.strategy(
				translate(geoShapeQuery.getSpatialStrategy()));
		}

		return geoShapeQueryBuilder;
	}

	protected org.elasticsearch.common.geo.ShapeRelation translate(
		ShapeRelation shapeRelation) {

		if (shapeRelation == ShapeRelation.CONTAINS) {
			return org.elasticsearch.common.geo.ShapeRelation.CONTAINS;
		}

		if (shapeRelation == ShapeRelation.DISJOINT) {
			return org.elasticsearch.common.geo.ShapeRelation.DISJOINT;
		}

		if (shapeRelation == ShapeRelation.INTERSECTS) {
			return org.elasticsearch.common.geo.ShapeRelation.INTERSECTS;
		}

		if (shapeRelation == ShapeRelation.WITHIN) {
			return org.elasticsearch.common.geo.ShapeRelation.WITHIN;
		}

		throw new IllegalArgumentException(
			"Invalid ShapeRelation: " + shapeRelation);
	}

	protected org.elasticsearch.common.geo.SpatialStrategy translate(
		SpatialStrategy spatialStrategy) {

		if (spatialStrategy == SpatialStrategy.RECURSIVE) {
			return org.elasticsearch.common.geo.SpatialStrategy.RECURSIVE;
		}

		if (spatialStrategy == SpatialStrategy.TERM) {
			return org.elasticsearch.common.geo.SpatialStrategy.TERM;
		}

		throw new IllegalArgumentException(
			"Invalid SpatialStrategy: " + spatialStrategy);
	}

	protected GeoShapeQueryBuilder translateQuery(GeoShapeQuery geoShapeQuery) {
		if (geoShapeQuery.getIndexedShapeId() != null) {
			GeoShapeQueryBuilder geoShapeQueryBuilder =
				QueryBuilders.geoShapeQuery(
					geoShapeQuery.getField(), geoShapeQuery.getIndexedShapeId(),
					geoShapeQuery.getIndexedShapeType());

			if (geoShapeQuery.getIndexedShapeIndex() != null) {
				geoShapeQueryBuilder.indexedShapeIndex(
					geoShapeQuery.getIndexedShapeIndex());
			}

			if (geoShapeQuery.getIndexedShapePath() != null) {
				geoShapeQueryBuilder.indexedShapePath(
					geoShapeQuery.getIndexedShapePath());
			}

			if (geoShapeQuery.getIndexedShapeRouting() != null) {
				geoShapeQueryBuilder.indexedShapeRouting(
					geoShapeQuery.getIndexedShapeRouting());
			}

			return geoShapeQueryBuilder;
		}

		try {
			Shape shape = geoShapeQuery.getShape();

			return QueryBuilders.geoShapeQuery(
				geoShapeQuery.getField(),
				shape.accept(_elasticsearchShapeTranslator));
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private final ElasticsearchShapeTranslator _elasticsearchShapeTranslator =
		new ElasticsearchShapeTranslator();

}