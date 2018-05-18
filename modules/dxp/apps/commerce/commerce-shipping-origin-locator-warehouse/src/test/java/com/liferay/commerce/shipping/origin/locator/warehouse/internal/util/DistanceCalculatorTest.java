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