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

package com.liferay.portal.search.elasticsearch6.internal.geolocation;

import com.liferay.portal.search.geolocation.DistanceUnit;

/**
 * @author Michael C. Han
 */
public class DistanceUnitTranslator {

	public org.elasticsearch.common.unit.DistanceUnit translate(
		DistanceUnit distanceUnit) {

		if (distanceUnit == DistanceUnit.CENTIMETERS) {
			return org.elasticsearch.common.unit.DistanceUnit.CENTIMETERS;
		}
		else if (distanceUnit == DistanceUnit.FEET) {
			return org.elasticsearch.common.unit.DistanceUnit.FEET;
		}
		else if (distanceUnit == DistanceUnit.INCHES) {
			return org.elasticsearch.common.unit.DistanceUnit.INCH;
		}
		else if (distanceUnit == DistanceUnit.KILOMETERS) {
			return org.elasticsearch.common.unit.DistanceUnit.KILOMETERS;
		}
		else if (distanceUnit == DistanceUnit.METERS) {
			return org.elasticsearch.common.unit.DistanceUnit.METERS;
		}
		else if (distanceUnit == DistanceUnit.MILES) {
			return org.elasticsearch.common.unit.DistanceUnit.MILES;
		}
		else if (distanceUnit == DistanceUnit.MILLIMETERS) {
			return org.elasticsearch.common.unit.DistanceUnit.MILLIMETERS;
		}
		else if (distanceUnit == DistanceUnit.YARDS) {
			return org.elasticsearch.common.unit.DistanceUnit.YARD;
		}

		throw new IllegalArgumentException(
			"Invalid distance unit: " + distanceUnit);
	}

}