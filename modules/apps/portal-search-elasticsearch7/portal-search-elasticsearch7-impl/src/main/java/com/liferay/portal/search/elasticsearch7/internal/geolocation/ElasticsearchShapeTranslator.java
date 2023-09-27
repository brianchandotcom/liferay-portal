/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.geolocation;

import com.liferay.petra.function.transform.TransformUtil;
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

import org.elasticsearch.legacygeo.builders.CircleBuilder;
import org.elasticsearch.legacygeo.builders.EnvelopeBuilder;
import org.elasticsearch.legacygeo.builders.GeometryCollectionBuilder;
import org.elasticsearch.legacygeo.builders.LineStringBuilder;
import org.elasticsearch.legacygeo.builders.MultiLineStringBuilder;
import org.elasticsearch.legacygeo.builders.MultiPointBuilder;
import org.elasticsearch.legacygeo.builders.MultiPolygonBuilder;
import org.elasticsearch.legacygeo.builders.PointBuilder;
import org.elasticsearch.legacygeo.builders.PolygonBuilder;
import org.elasticsearch.legacygeo.builders.ShapeBuilder;

/**
 * @author Michael C. Han
 */
public class ElasticsearchShapeTranslator
	implements ShapeTranslator<ShapeBuilder<?, ?, ?>> {

	@Override
	public CircleBuilder translate(CircleShape circleShape) {
		GeoDistance radiusGeoDistance = circleShape.getRadius();

		return new CircleBuilder(
		).center(
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

		for (Shape shape : geometryCollectionShape.getShapes()) {
			geometryCollectionBuilder.shape(translate(shape));
		}

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

		for (LineStringShape lineStringShape :
				multiLineStringShape.getLineStringShapes()) {

			multiLineStringBuilder.linestring(translate(lineStringShape));
		}

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

		for (PolygonShape polygonShape : multiPolygonShape.getPolygonShapes()) {
			multiPolygonBuilder.polygon(translate(polygonShape));
		}

		return multiPolygonBuilder;
	}

	@Override
	public PointBuilder translate(PointShape pointShape) {
		PointBuilder pointBuilder = new PointBuilder();

		for (Coordinate coordinate : pointShape.getCoordinates()) {
			pointBuilder.coordinate(translate(coordinate));
		}

		return pointBuilder;
	}

	@Override
	public PolygonBuilder translate(PolygonShape polygonShape) {
		PolygonBuilder polygonBuilder = new PolygonBuilder(
			translate(polygonShape.getShell()),
			translate(polygonShape.getOrientation()));

		polygonBuilder.coordinates(translate(polygonShape.getCoordinates()));

		for (LineStringShape holeLineStringShape : polygonShape.getHoles()) {
			polygonBuilder.hole(translate(holeLineStringShape));
		}

		return polygonBuilder;
	}

	protected org.locationtech.jts.geom.Coordinate translate(
		Coordinate coordinate) {

		return new org.locationtech.jts.geom.Coordinate(
			coordinate.getX(), coordinate.getY(), coordinate.getZ());
	}

	protected List<org.locationtech.jts.geom.Coordinate> translate(
		List<Coordinate> coordinates) {

		return TransformUtil.transform(coordinates, this::translate);
	}

	protected org.elasticsearch.common.geo.Orientation translate(
		Orientation orientation) {

		if (orientation == Orientation.LEFT) {
			return org.elasticsearch.common.geo.Orientation.LEFT;
		}

		if (orientation == Orientation.RIGHT) {
			return org.elasticsearch.common.geo.Orientation.RIGHT;
		}

		throw new IllegalArgumentException(
			"Invalid Orientation: " + orientation);
	}

	protected ShapeBuilder<?, ?, ?> translate(Shape shape) {
		return shape.accept(this);
	}

}