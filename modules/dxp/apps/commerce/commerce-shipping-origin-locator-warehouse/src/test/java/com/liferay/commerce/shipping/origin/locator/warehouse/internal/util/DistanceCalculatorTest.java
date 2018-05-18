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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class DistanceCalculatorTest {

	@Test
	public void testGetDistance() {
		Assert.assertEquals(
			69.382,
			_distanceCalculator.getDistance(
				_LATITUDE, _LONGITUDE, 34.045886, -118.564861),
			_DELTA);
		Assert.assertEquals(
			9070.629,
			_distanceCalculator.getDistance(
				_LATITUDE, _LONGITUDE, 48.856614, 2.352222),
			_DELTA);
	}

	private static final int _DELTA = 1;

	private static final double _LATITUDE = 33.997673;

	private static final double _LONGITUDE = -117.814508;

	private static final DistanceCalculator _distanceCalculator =
		new DistanceCalculator();

}