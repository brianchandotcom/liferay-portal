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

import com.liferay.portal.search.geolocation.Coordinate;

/**
 * @author Michael C. Han
 */
public class CoordinateImpl implements Coordinate {

	public double getX() {
		return _x;
	}

	public double getY() {
		return _y;
	}

	public double getZ() {
		return _z;
	}

	protected CoordinateImpl(double x, double y) {
		_x = x;
		_y = y;

		_z = 0;
	}

	protected CoordinateImpl(double x, double y, double z) {
		_x = x;
		_y = y;
		_z = z;
	}

	private final double _x;
	private final double _y;
	private final double _z;

}