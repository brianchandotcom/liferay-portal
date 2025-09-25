/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.geolocation;

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