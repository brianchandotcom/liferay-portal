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

package com.liferay.commerce.shipping.origin.locator.warehouse.internal.util;

/**
 * @author Andrea Di Giorgi
 */
public class DistanceCalculator {

	public double getDistance(
		double latitude1, double longitude1, double latitude2,
		double longitude2) {

		double latitudeDifference = Math.toRadians(latitude2 - latitude1);
		double longitudeDifference = Math.toRadians(longitude2 - longitude1);

		latitude1 = Math.toRadians(latitude1);
		latitude2 = Math.toRadians(latitude2);

		double a =
			Math.pow(Math.sin(latitudeDifference / 2), 2) +
				Math.pow(Math.sin(longitudeDifference / 2), 2) *
					Math.cos(latitude1) * Math.cos(latitude2);

		double c = 2 * Math.asin(Math.sqrt(a));

		return _EARTH_RADIUS * c;
	}

	private static final double _EARTH_RADIUS = 6372.8;

}