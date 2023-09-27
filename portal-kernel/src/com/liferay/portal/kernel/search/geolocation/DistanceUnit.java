/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.geolocation;

/**
 * @author Michael C. Han
 */
public enum DistanceUnit {

	CENTIMETERS("cm"), FEET("ft"), INCHES("in"), KILOMETERS("km"), METERS("m"),
	MILES("mi"), MILLIMETERS("mm"), YARDS("yd");

	public String getUnit() {
		return _unit;
	}

	@Override
	public String toString() {
		return _unit;
	}

	private DistanceUnit(String unit) {
		_unit = unit;
	}

	private final String _unit;

}