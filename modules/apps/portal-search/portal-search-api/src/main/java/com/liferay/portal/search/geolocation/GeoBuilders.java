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

package com.liferay.portal.search.geolocation;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@ProviderType
public interface GeoBuilders {

	public CircleShape circleShape(
		Coordinate centerCoordinate, GeoDistance radiusGeoDistance);

	public CircleShapeBuilder circleShapeBuilder();

	public Coordinate coordinate(double x, double y);

	public Coordinate coordinate(double x, double y, double z);

	public EnvelopeShape envelopeShape(
		Coordinate topLeftCoordinate, Coordinate bottomRightCoordinate);

	public EnvelopeShapeBuilder envelopeShapeBuilder();

	public GeoDistance geoDistance(double distance);

	public GeoDistance geoDistance(double distance, DistanceUnit distanceUnit);

	public GeoLocationPoint geoLocationPoint(double latitude, double longitude);

	public GeoLocationPoint geoLocationPoint(long geoHash);

	public GeoLocationPoint geoLocationPoint(String geoHash);

	public GeometryCollectionShapeBuilder geometryCollectionShapeBuilder();

	public LineStringShape lineStringShape(List<Coordinate> coordinates);

	public LineStringShapeBuilder lineStringShapeBuilder();

	public MultiLineStringShapeBuilder multiLineStringShapeBuilder();

	public MultiPointShape multiPointShape(List<Coordinate> coordinates);

	public MultiPointShapeBuilder multiPointShapeBuilder();

	public MultiPolygonShape multiPolygonShape(Orientation orientation);

	public MultiPolygonShapeBuilder multiPolygonShapeBuilder();

	public PointShape pointShape(Coordinate coordinate);

	public PointShapeBuilder pointShapeBuilder();

	public PolygonShape polygonShape(
		LineStringShape shellLineStringShape, Orientation orientation);

	public PolygonShapeBuilder polygonShapeBuilder();

}