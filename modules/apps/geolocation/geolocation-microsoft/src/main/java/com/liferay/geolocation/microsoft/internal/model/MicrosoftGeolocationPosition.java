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

package com.liferay.geolocation.microsoft.internal.model;

import com.liferay.geolocation.model.GeolocationPosition;

/**
 * @author Eduardo García
 */
public class MicrosoftGeolocationPosition implements GeolocationPosition {

	public MicrosoftGeolocationPosition(double latitude, double longitude) {
		_latitude = latitude;
		_longitude = longitude;
	}

	@Override
	public double getLatitude() {
		return _latitude;
	}

	@Override
	public double getLongitude() {
		return _longitude;
	}

	private final double _latitude;
	private final double _longitude;

}