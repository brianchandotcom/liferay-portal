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

package com.liferay.portal.search.elasticsearch7.internal.geolocation;

import com.liferay.portal.search.geolocation.CircleShape;
import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.EnvelopeShape;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeometryCollectionShape;
import com.liferay.portal.search.geolocation.LineStringShape;
import com.liferay.portal.search.geolocation.MultiLineStringShape;
import com.liferay.portal.search.geolocation.MultiPointShape;
import com.liferay.portal.search.geolocation.MultiPolygonShape;
import com.liferay.portal.search.geolocation.Orientation;
import com.liferay.portal.search.geolocation.PointShape;
import com.liferay.portal.search.geolocation.PolygonShape;
import com.liferay.portal.search.geolocation.Shape;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

/**
 * @author Michael C. Han
 */
public class ElasticsearchShapeTranslator
	implements ShapeTranslator<ShapeBuilder<?, ?, ?>> {

	@Override
	public CircleBuilder translate(CircleShape circleShape) {
		GeoDistance radiusGeoDistance = circleShape.getRadius();

		return new CircleBuilder().center(
			translate(circleShape.getCenter())
		).coordinates(
			translate(circleShape.getCoordinates())
		).radius(
			radiusGeoDistance.getDistance(),
			String.valueOf(radiusGeoDistance.getDistanceUnit())
		);
	}

	@Override
	public EnvelopeBuilder translate(EnvelopeShape envelopeShape) {
		return new EnvelopeBuilder(
			translate(envelopeShape.getTopLeft()),
			translate(envelopeShape.getBottomRight())
		).coordinates(
			translate(envelopeShape.getCoordinates())
		);
	}

	@Override
	public GeometryCollectionBuilder translate(
		GeometryCollectionShape geometryCollectionShape) {

		GeometryCollectionBuilder geometryCollectionBuilder =
			new GeometryCollectionBuilder();

		geometryCollectionBuilder.coordinates(
			translate(geometryCollectionShape.getCoordinates()));

		List<Shape> shapes = geometryCollectionShape.getShapes();

		Stream<Shape> stream = shapes.stream();

		stream.map(
			this::translate
		).forEach(
			geometryCollectionBuilder::shape
		);

		return geometryCollectionBuilder;
	}

	@Override
	public LineStringBuilder translate(LineStringShape lineStringShape) {
		return new LineStringBuilder(
			translate(lineStringShape.getCoordinates()));
	}

	@Override
	public MultiLineStringBuilder translate(
		MultiLineStringShape multiLineStringShape) {

		MultiLineStringBuilder multiLineStringBuilder =
			new MultiLineStringBuilder();

		multiLineStringBuilder.coordinates(
			translate(multiLineStringShape.getCoordinates()));

		List<LineStringShape> lineStringShapes =
			multiLineStringShape.getLineStringShapes();

		Stream<LineStringShape> stream = lineStringShapes.stream();

		stream.map(
			this::translate
		).forEach(
			multiLineStringBuilder::linestring
		);

		return multiLineStringBuilder;
	}

	@Override
	public MultiPointBuilder translate(MultiPointShape multiPointShape) {
		return new MultiPointBuilder(
			translate(multiPointShape.getCoordinates()));
	}

	@Override
	public MultiPolygonBuilder translate(MultiPolygonShape multiPolygonShape) {
		MultiPolygonBuilder multiPolygonBuilder = new MultiPolygonBuilder(
			translate(multiPolygonShape.getOrientation()));

		multiPolygonBuilder.coordinates(
			translate(multiPolygonShape.getCoordinates()));

		List<PolygonShape> polygonShapes = multiPolygonShape.getPolygonShapes();

		Stream<PolygonShape> stream = polygonShapes.stream();

		stream.map(
			this::translate
		).forEach(
			multiPolygonBuilder::polygon
		);

		return multiPolygonBuilder;
	}

	@Override
	public PointBuilder translate(PointShape pointShape) {
		List<Coordinate> coordinates = pointShape.getCoordinates();

		PointBuilder pointBuilder = new PointBuilder();

		Stream<Coordinate> stream = coordinates.stream();

		stream.map(
			this::translate
		).forEach(
			pointBuilder::coordinate
		);

		return pointBuilder;
	}

	@Override
	public PolygonBuilder translate(PolygonShape polygonShape) {
		PolygonBuilder polygonBuilder = new PolygonBuilder(
			translate(polygonShape.getShell()),
			translate(polygonShape.getOrientation()));

		polygonBuilder.coordinates(translate(polygonShape.getCoordinates()));

		List<LineStringShape> holesLineStringShapes = polygonShape.getHoles();

		Stream<LineStringShape> stream = holesLineStringShapes.stream();

		stream.map(
			this::translate
		).forEach(
			polygonBuilder::hole
		);

		return polygonBuilder;
	}

	protected org.locationtech.jts.geom.Coordinate translate(
		Coordinate coordinate) {

		return new org.locationtech.jts.geom.Coordinate(
			coordinate.getX(), coordinate.getY(), coordinate.getZ());
	}

	protected List<org.locationtech.jts.geom.Coordinate> translate(
		List<Coordinate> coordinates) {

		Stream<Coordinate> stream = coordinates.stream();

		return stream.map(
			this::translate
		).collect(
			Collectors.toList()
		);
	}

	protected ShapeBuilder.Orientation translate(Orientation orientation) {
		if (orientation == Orientation.LEFT) {
			return ShapeBuilder.Orientation.LEFT;
		}

		if (orientation == Orientation.RIGHT) {
			return ShapeBuilder.Orientation.RIGHT;
		}

		throw new IllegalArgumentException(
			"Invalid Orientation: " + orientation);
	}

	protected ShapeBuilder<?, ?, ?> translate(Shape shape) {
		return shape.accept(this);
	}

}