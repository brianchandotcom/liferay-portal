/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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