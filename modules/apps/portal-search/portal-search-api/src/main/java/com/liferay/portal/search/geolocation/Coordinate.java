/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

/**
 * @author Michael C. Han
 */
public class Coordinate {

	public Coordinate(double x, double y) {
		_x = x;
		_y = y;

		_z = 0;
	}

	public Coordinate(double x, double y, double z) {
		_x = x;
		_y = y;
		_z = z;
	}

	public double getX() {
		return _x;
	}

	public double getY() {
		return _y;
	}

	public double getZ() {
		return _z;
	}

	private final double _x;
	private final double _y;
	private final double _z;

}