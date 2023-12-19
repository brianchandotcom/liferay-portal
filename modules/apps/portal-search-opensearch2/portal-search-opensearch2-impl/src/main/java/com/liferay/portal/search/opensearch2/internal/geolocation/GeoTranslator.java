/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.geolocation;

import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoDistanceType;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.GeoExecType;
import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

import org.opensearch.client.opensearch._types.GeoBounds;
import org.opensearch.client.opensearch._types.GeoHashLocation;
import org.opensearch.client.opensearch._types.GeoLocation;
import org.opensearch.client.opensearch._types.LatLonGeoLocation;
import org.opensearch.client.opensearch._types.TopLeftBottomRightGeoBounds;
import org.opensearch.client.opensearch._types.query_dsl.GeoExecution;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class GeoTranslator {

	public GeoBounds toGeoBounds(
		GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		TopLeftBottomRightGeoBounds tlbr = TopLeftBottomRightGeoBounds.of(
			topLeftBottomRightGeoBounds ->
				topLeftBottomRightGeoBounds.bottomRight(
					translateGeoLocationPoint(bottomRightGeoLocationPoint)
				).topLeft(
					translateGeoLocationPoint(topLeftGeoLocationPoint)
				));

		return GeoBounds.of(geoBounds -> geoBounds.tlbr(tlbr));
	}

	public String toStringWithUnit(GeoDistance geoDistance) {
		org.opensearch.client.opensearch._types.DistanceUnit distanceUnit =
			translateDistanceUnit(geoDistance.getDistanceUnit());

		return geoDistance.getDistance() + distanceUnit.jsonValue();
	}

	public org.opensearch.client.opensearch._types.DistanceUnit
		translateDistanceUnit(DistanceUnit distanceUnit) {

		if (distanceUnit == DistanceUnit.CENTIMETERS) {
			return org.opensearch.client.opensearch._types.DistanceUnit.
				Centimeters;
		}
		else if (distanceUnit == DistanceUnit.FEET) {
			return org.opensearch.client.opensearch._types.DistanceUnit.Feet;
		}
		else if (distanceUnit == DistanceUnit.INCHES) {
			return org.opensearch.client.opensearch._types.DistanceUnit.Inches;
		}
		else if (distanceUnit == DistanceUnit.KILOMETERS) {
			return org.opensearch.client.opensearch._types.DistanceUnit.
				Kilometers;
		}
		else if (distanceUnit == DistanceUnit.METERS) {
			return org.opensearch.client.opensearch._types.DistanceUnit.Meters;
		}
		else if (distanceUnit == DistanceUnit.MILES) {
			return org.opensearch.client.opensearch._types.DistanceUnit.Miles;
		}
		else if (distanceUnit == DistanceUnit.MILLIMETERS) {
			return org.opensearch.client.opensearch._types.DistanceUnit.
				Millimeters;
		}
		else if (distanceUnit == DistanceUnit.YARDS) {
			return org.opensearch.client.opensearch._types.DistanceUnit.Yards;
		}

		throw new IllegalArgumentException(
			"Invalid distance unit: " + distanceUnit);
	}

	public org.opensearch.client.opensearch._types.GeoDistanceType
		translateGeoDistanceType(GeoDistanceType geoDistanceType) {

		if (geoDistanceType == GeoDistanceType.ARC) {
			return org.opensearch.client.opensearch._types.GeoDistanceType.Arc;
		}
		else if (geoDistanceType == GeoDistanceType.PLANE) {
			return org.opensearch.client.opensearch._types.GeoDistanceType.
				Plane;
		}

		throw new IllegalArgumentException(
			"Invalid geodistance type: " + geoDistanceType);
	}

	public GeoExecution translateGeoExecType(GeoExecType geoExecType) {
		if (geoExecType == GeoExecType.INDEXED) {
			return GeoExecution.Indexed;
		}
		else if (geoExecType == GeoExecType.MEMORY) {
			return GeoExecution.Memory;
		}

		throw new IllegalArgumentException(
			"Invalid geoexec type " + geoExecType);
	}

	public GeoLocation translateGeoLocationPoint(
		com.liferay.portal.kernel.search.geolocation.GeoLocationPoint
			geoLocationPoint) {

		return GeoLocation.of(
			geolocation -> geolocation.latlon(
				LatLonGeoLocation.of(
					latLonGeoLocation -> latLonGeoLocation.lat(
						geoLocationPoint.getLatitude()
					).lon(
						geoLocationPoint.getLongitude()
					))));
	}

	public GeoLocation translateGeoLocationPoint(
		GeoLocationPoint geoLocationPoint) {

		if (geoLocationPoint.getGeoHash() != null) {
			return GeoLocation.of(
				geolocation -> geolocation.geohash(
					GeoHashLocation.of(
						geoHashLocation -> geoHashLocation.geohash(
							geoLocationPoint.getGeoHash()))));
		}
		else if ((geoLocationPoint.getLatitude() != null) &&
				 (geoLocationPoint.getLongitude() != null)) {

			return GeoLocation.of(
				geolocation -> geolocation.latlon(
					LatLonGeoLocation.of(
						latLonGeoLocation -> latLonGeoLocation.lat(
							geoLocationPoint.getLatitude()
						).lon(
							geoLocationPoint.getLongitude()
						))));
		}

		throw new UnsupportedOperationException();
	}

	public org.opensearch.client.opensearch._types.query_dsl.GeoValidationMethod
		translateGeoValidationMethod(GeoValidationMethod geoValidationMethod) {

		if (geoValidationMethod == GeoValidationMethod.COERCE) {
			return org.opensearch.client.opensearch._types.query_dsl.
				GeoValidationMethod.Coerce;
		}
		else if (geoValidationMethod == GeoValidationMethod.IGNORE_MALFORMED) {
			return org.opensearch.client.opensearch._types.query_dsl.
				GeoValidationMethod.IgnoreMalformed;
		}
		else if (geoValidationMethod == GeoValidationMethod.STRICT) {
			return org.opensearch.client.opensearch._types.query_dsl.
				GeoValidationMethod.Strict;
		}

		throw new IllegalArgumentException(
			"Invalid geo validation method " + geoValidationMethod);
	}

}