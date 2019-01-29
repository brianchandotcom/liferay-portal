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

package com.liferay.portal.search.elasticsearch6.internal.geolocation;

import com.liferay.portal.search.geolocation.CircleShapeBuilder;
import com.liferay.portal.search.geolocation.EnvelopeShapeBuilder;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeometryCollectionShapeBuilder;
import com.liferay.portal.search.geolocation.LineStringShapeBuilder;
import com.liferay.portal.search.geolocation.MultiPointShapeBuilder;
import com.liferay.portal.search.geolocation.MultiPolygonShapeBuilder;
import com.liferay.portal.search.geolocation.MultipleLineStringShapeBuilder;
import com.liferay.portal.search.geolocation.Orientation;
import com.liferay.portal.search.geolocation.PointShapeBuilder;
import com.liferay.portal.search.geolocation.PolygonShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeBuilderTranslator;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.geo.builders.CircleBuilder;
import org.elasticsearch.common.geo.builders.EnvelopeBuilder;
import org.elasticsearch.common.geo.builders.GeometryCollectionBuilder;
import org.elasticsearch.common.geo.builders.LineStringBuilder;
import org.elasticsearch.common.geo.builders.MultiLineStringBuilder;
import org.elasticsearch.common.geo.builders.MultiPointBuilder;
import org.elasticsearch.common.geo.builders.MultiPolygonBuilder;
import org.elasticsearch.common.geo.builders.PointBuilder;
import org.elasticsearch.common.geo.builders.PolygonBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilder;

import org.locationtech.jts.geom.Coordinate;

/**
 * @author Michael C. Han
 */
public class ElasticsearchShapeBuilderTranslator
	implements ShapeBuilderTranslator<ShapeBuilder<?, ?>> {

	@Override
	public ShapeBuilder<?, ?> translate(CircleShapeBuilder circleShapeBuilder) {
		CircleBuilder circleBuilder = new CircleBuilder();

		com.liferay.portal.search.geolocation.Coordinate center =
			circleShapeBuilder.getCenter();

		circleBuilder.center(
			new Coordinate(center.getX(), center.getY(), center.getZ()));

		GeoDistance geoDistance = circleShapeBuilder.getRadius();

		circleBuilder.radius(
			geoDistance.getDistance(),
			String.valueOf(geoDistance.getDistanceUnit()));

		circleBuilder.coordinates(
			translate(circleShapeBuilder.getCoordinates()));

		return circleBuilder;
	}

	@Override
	public ShapeBuilder<?, ?> translate(
		EnvelopeShapeBuilder envelopeShapeBuilder) {

		com.liferay.portal.search.geolocation.Coordinate topLeft =
			envelopeShapeBuilder.getTopLeft();

		com.liferay.portal.search.geolocation.Coordinate bottomRight =
			envelopeShapeBuilder.getBottomRight();

		EnvelopeBuilder envelopeBuilder = new EnvelopeBuilder(
			new Coordinate(topLeft.getX(), topLeft.getY(), topLeft.getZ()),
			new Coordinate(
				bottomRight.getX(), bottomRight.getY(), bottomRight.getZ()));

		envelopeBuilder.coordinates(
			translate(envelopeShapeBuilder.getCoordinates()));

		return envelopeBuilder;
	}

	@Override
	public ShapeBuilder<?, ?> translate(
		GeometryCollectionShapeBuilder geometryCollectionShapeBuilder) {

		GeometryCollectionBuilder geometryCollectionBuilder =
			new GeometryCollectionBuilder();

		List<com.liferay.portal.search.geolocation.ShapeBuilder> shapeBuilders =
			geometryCollectionShapeBuilder.getShapeBuilders();

		shapeBuilders.forEach(
			shapeBuilder -> {
				ShapeBuilder elasticsearchShapeBuilder = shapeBuilder.accept(
					this);

				geometryCollectionBuilder.shape(elasticsearchShapeBuilder);
			});

		geometryCollectionBuilder.coordinates(
			translate(geometryCollectionShapeBuilder.getCoordinates()));

		return geometryCollectionBuilder;
	}

	@Override
	public ShapeBuilder<?, ?> translate(
		LineStringShapeBuilder lineStringShapeBuilder) {

		List<Coordinate> elasticsearchCoordinates = translate(
			lineStringShapeBuilder.getCoordinates());

		LineStringBuilder lineStringBuilder = new LineStringBuilder(
			elasticsearchCoordinates);

		return lineStringBuilder;
	}

	@Override
	public ShapeBuilder<?, ?> translate(
		MultipleLineStringShapeBuilder multipleLineStringShapeBuilder) {

		MultiLineStringBuilder multiLineStringBuilder =
			new MultiLineStringBuilder();

		List<LineStringShapeBuilder> lineStringShapeBuilders =
			multipleLineStringShapeBuilder.getLineStringShapeBuilders();

		lineStringShapeBuilders.forEach(
			lineStringShapeBuilder -> multiLineStringBuilder.linestring(
				(LineStringBuilder)lineStringShapeBuilder.accept(this)));

		multiLineStringBuilder.coordinates(
			translate(multipleLineStringShapeBuilder.getCoordinates()));

		return multiLineStringBuilder;
	}

	@Override
	public ShapeBuilder<?, ?> translate(
		MultiPointShapeBuilder multiPointShapeBuilder) {

		List<Coordinate> elasticsearchCoordinates = translate(
			multiPointShapeBuilder.getCoordinates());

		MultiPointBuilder multiPointBuilder = new MultiPointBuilder(
			elasticsearchCoordinates);

		return multiPointBuilder;
	}

	@Override
	public ShapeBuilder<?, ?> translate(
		MultiPolygonShapeBuilder multiPolygonShapeBuilder) {

		MultiPolygonBuilder multiPolygonBuilder = new MultiPolygonBuilder(
			translate(multiPolygonShapeBuilder.getOrientation()));

		List<PolygonShapeBuilder> polygonShapeBuilders =
			multiPolygonShapeBuilder.getPolygonShapeBuilders();

		polygonShapeBuilders.forEach(
			polygonShapeBuilder -> multiPolygonBuilder.polygon(
				(PolygonBuilder)polygonShapeBuilder.accept(this)));

		multiPolygonBuilder.coordinates(
			translate(multiPolygonShapeBuilder.getCoordinates()));

		return multiPolygonBuilder;
	}

	@Override
	public ShapeBuilder<?, ?> translate(PointShapeBuilder pointShapeBuilder) {
		List<com.liferay.portal.search.geolocation.Coordinate> coordinates =
			pointShapeBuilder.getCoordinates();

		PointBuilder pointBuilder = new PointBuilder();

		coordinates.forEach(
			coordinate -> pointBuilder.coordinate(
				new Coordinate(
					coordinate.getX(), coordinate.getY(), coordinate.getZ())));

		return pointBuilder;
	}

	@Override
	public ShapeBuilder<?, ?> translate(
		PolygonShapeBuilder polygonShapeBuilder) {

		ShapeBuilder.Orientation orientation = translate(
			polygonShapeBuilder.getOrientation());

		LineStringShapeBuilder lineStringShapeBuilder =
			polygonShapeBuilder.getShell();

		PolygonBuilder polygonBuilder = new PolygonBuilder(
			(LineStringBuilder)lineStringShapeBuilder.accept(this),
			orientation);

		List<LineStringShapeBuilder> holesLineStringShapeBuilders =
			polygonShapeBuilder.getHolesLineStringShapeBuilders();

		holesLineStringShapeBuilders.forEach(
			holesLineStringShapeBuilder -> polygonBuilder.hole(
				(LineStringBuilder)holesLineStringShapeBuilder.accept(this)));

		polygonBuilder.coordinates(
			translate(polygonShapeBuilder.getCoordinates()));

		return polygonBuilder;
	}

	protected List<Coordinate> translate(
		List<com.liferay.portal.search.geolocation.Coordinate> coordinates) {

		List<Coordinate> elasticsearchCoordinates = new ArrayList<>(
			coordinates.size());

		coordinates.forEach(
			coordinate -> elasticsearchCoordinates.add(
				new Coordinate(
					coordinate.getX(), coordinate.getY(), coordinate.getZ())));

		return elasticsearchCoordinates;
	}

	protected ShapeBuilder.Orientation translate(Orientation orientation) {
		if (orientation == Orientation.LEFT) {
			return ShapeBuilder.Orientation.LEFT;
		}
		else if (orientation == Orientation.RIGHT) {
			return ShapeBuilder.Orientation.RIGHT;
		}

		throw new IllegalArgumentException(
			"Invalid Orientation: " + orientation);
	}

}