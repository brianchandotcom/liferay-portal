/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.List;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class GeoBuilders {

	public static final GeoBuilders INSTANCE = new GeoBuilders();

	public CircleShape circleShape(
		Coordinate centerCoordinate, GeoDistance radiusGeoDistance) {

		return circleShapeBuilder(
		).center(
			centerCoordinate
		).radius(
			radiusGeoDistance
		).build();
	}

	public CircleShapeBuilder circleShapeBuilder() {
		return new CircleShapeBuilder();
	}

	public Coordinate coordinate(double x, double y) {
		return new Coordinate(x, y);
	}

	public Coordinate coordinate(double x, double y, double z) {
		return new Coordinate(x, y, z);
	}

	public EnvelopeShape envelopeShape(
		Coordinate topLeftCoordinate, Coordinate bottomRightCoordinate) {

		return envelopeShapeBuilder(
		).topLeft(
			topLeftCoordinate
		).bottomRight(
			bottomRightCoordinate
		).build();
	}

	public EnvelopeShapeBuilder envelopeShapeBuilder() {
		return new EnvelopeShapeBuilder();
	}

	public GeoDistance geoDistance(double distance) {
		return new GeoDistance(distance);
	}

	public GeoDistance geoDistance(double distance, DistanceUnit distanceUnit) {
		return new GeoDistance(distance, distanceUnit);
	}

	public GeoLocationPoint geoLocationPoint(
		double latitude, double longitude) {

		return GeoLocationPoint.fromLatitudeLongitude(latitude, longitude);
	}

	public GeoLocationPoint geoLocationPoint(long geoHash) {
		return GeoLocationPoint.fromGeoHashLong(geoHash);
	}

	public GeoLocationPoint geoLocationPoint(String geoHash) {
		return GeoLocationPoint.fromGeoHash(geoHash);
	}

	public GeometryCollectionShapeBuilder geometryCollectionShapeBuilder() {
		return new GeometryCollectionShapeBuilder();
	}

	public LineStringShape lineStringShape(List<Coordinate> coordinates) {
		return lineStringShapeBuilder(
		).coordinates(
			coordinates
		).build();
	}

	public LineStringShapeBuilder lineStringShapeBuilder() {
		return new LineStringShapeBuilder();
	}

	public MultiLineStringShapeBuilder multiLineStringShapeBuilder() {
		return new MultiLineStringShapeBuilder();
	}

	public MultiPointShape multiPointShape(List<Coordinate> coordinates) {
		return multiPointShapeBuilder(
		).coordinates(
			coordinates
		).build();
	}

	public MultiPointShapeBuilder multiPointShapeBuilder() {
		return new MultiPointShapeBuilder();
	}

	public MultiPolygonShape multiPolygonShape(Orientation orientation) {
		return multiPolygonShapeBuilder(
		).orientation(
			orientation
		).build();
	}

	public MultiPolygonShapeBuilder multiPolygonShapeBuilder() {
		return new MultiPolygonShapeBuilder();
	}

	public PointShape pointShape(Coordinate coordinate) {
		return pointShapeBuilder(
		).addCoordinate(
			coordinate
		).build();
	}

	public PointShapeBuilder pointShapeBuilder() {
		return new PointShapeBuilder();
	}

	public PolygonShape polygonShape(
		LineStringShape shellLineStringShape, Orientation orientation) {

		return polygonShapeBuilder(
		).shell(
			shellLineStringShape
		).orientation(
			orientation
		).build();
	}

	public PolygonShapeBuilder polygonShapeBuilder() {
		return new PolygonShapeBuilder();
	}

	private GeoBuilders() {
	}

}