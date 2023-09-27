/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.origin.locator.internal.util;

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
			Math.pow(Math.sin(longitudeDifference / 2), 2) *
				Math.cos(latitude1) * Math.cos(latitude2);

		double b = Math.pow(Math.sin(latitudeDifference / 2), 2) + a;

		double c = 2 * Math.asin(Math.sqrt(b));

		return _EARTH_RADIUS * c;
	}

	private static final double _EARTH_RADIUS = 6372.8;

}