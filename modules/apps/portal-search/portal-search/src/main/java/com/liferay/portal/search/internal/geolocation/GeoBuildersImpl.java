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

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.CircleShape;
import com.liferay.portal.search.geolocation.CircleShapeBuilder;
import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.EnvelopeShape;
import com.liferay.portal.search.geolocation.EnvelopeShapeBuilder;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.geolocation.GeometryCollectionShapeBuilder;
import com.liferay.portal.search.geolocation.LineStringShape;
import com.liferay.portal.search.geolocation.LineStringShapeBuilder;
import com.liferay.portal.search.geolocation.MultiLineStringShapeBuilder;
import com.liferay.portal.search.geolocation.MultiPointShape;
import com.liferay.portal.search.geolocation.MultiPointShapeBuilder;
import com.liferay.portal.search.geolocation.MultiPolygonShape;
import com.liferay.portal.search.geolocation.MultiPolygonShapeBuilder;
import com.liferay.portal.search.geolocation.Orientation;
import com.liferay.portal.search.geolocation.PointShape;
import com.liferay.portal.search.geolocation.PointShapeBuilder;
import com.liferay.portal.search.geolocation.PolygonShape;
import com.liferay.portal.search.geolocation.PolygonShapeBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = GeoBuilders.class)
public class GeoBuildersImpl implements GeoBuilders {

	@Override
	public CircleShape circleShape(
		Coordinate centerCoordinate, GeoDistance radiusGeoDistance) {

		return circleShapeBuilder(
		).center(
			centerCoordinate
		).radius(
			radiusGeoDistance
		).build();
	}

	@Override
	public CircleShapeBuilder circleShapeBuilder() {
		return new CircleShapeImpl.CircleShapeBuilderImpl();
	}

	@Override
	public Coordinate coordinate(double x, double y) {
		return new CoordinateImpl(x, y);
	}

	@Override
	public Coordinate coordinate(double x, double y, double z) {
		return new CoordinateImpl(x, y, z);
	}

	@Override
	public EnvelopeShape envelopeShape(
		Coordinate topLeftCoordinate, Coordinate bottomRightCoordinate) {

		return envelopeShapeBuilder(
		).topLeft(
			topLeftCoordinate
		).bottomRight(
			bottomRightCoordinate
		).build();
	}

	@Override
	public EnvelopeShapeBuilder envelopeShapeBuilder() {
		return new EnvelopeShapeImpl.EnvelopeShapeBuilderImpl();
	}

	@Override
	public GeoDistance geoDistance(double distance) {
		return new GeoDistanceImpl(distance);
	}

	@Override
	public GeoDistance geoDistance(double distance, DistanceUnit distanceUnit) {
		return new GeoDistanceImpl(distance, distanceUnit);
	}

	@Override
	public GeoLocationPoint geoLocationPoint(
		double latitude, double longitude) {

		return GeoLocationPointImpl.fromLatitudeLongitude(latitude, longitude);
	}

	@Override
	public GeoLocationPoint geoLocationPoint(long geoHash) {
		return GeoLocationPointImpl.fromGeoHashLong(geoHash);
	}

	@Override
	public GeoLocationPoint geoLocationPoint(String geoHash) {
		return GeoLocationPointImpl.fromGeoHash(geoHash);
	}

	@Override
	public GeometryCollectionShapeBuilder geometryCollectionShapeBuilder() {
		return new GeometryCollectionShapeImpl.
			GeometryCollectionShapeBuilderImpl();
	}

	@Override
	public LineStringShape lineStringShape(List<Coordinate> coordinates) {
		return lineStringShapeBuilder(
		).coordinates(
			coordinates
		).build();
	}

	@Override
	public LineStringShapeBuilder lineStringShapeBuilder() {
		return new LineStringShapeImpl.LineStringShapeBuilderImpl();
	}

	@Override
	public MultiLineStringShapeBuilder multiLineStringShapeBuilder() {
		return new MultiLineStringShapeImpl.MultiLineStringShapeBuilderImpl();
	}

	@Override
	public MultiPointShape multiPointShape(List<Coordinate> coordinates) {
		return multiPointShapeBuilder(
		).coordinates(
			coordinates
		).build();
	}

	@Override
	public MultiPointShapeBuilder multiPointShapeBuilder() {
		return new MultiPointShapeImpl.MultiPointShapeBuilderImpl();
	}

	@Override
	public MultiPolygonShape multiPolygonShape(Orientation orientation) {
		return multiPolygonShapeBuilder(
		).orientation(
			orientation
		).build();
	}

	@Override
	public MultiPolygonShapeBuilder multiPolygonShapeBuilder() {
		return new MultiPolygonShapeImpl.MultiPolygonShapeBuilderImpl();
	}

	@Override
	public PointShape pointShape(Coordinate coordinate) {
		return pointShapeBuilder(
		).addCoordinate(
			coordinate
		).build();
	}

	@Override
	public PointShapeBuilder pointShapeBuilder() {
		return new PointShapeImpl.PointShapeBuilderImpl();
	}

	@Override
	public PolygonShape polygonShape(
		LineStringShape shellLineStringShape, Orientation orientation) {

		return polygonShapeBuilder(
		).shell(
			shellLineStringShape
		).orientation(
			orientation
		).build();
	}

	@Override
	public PolygonShapeBuilder polygonShapeBuilder() {
		return new PolygonShapeImpl.PolygonShapeBuilderImpl();
	}

}