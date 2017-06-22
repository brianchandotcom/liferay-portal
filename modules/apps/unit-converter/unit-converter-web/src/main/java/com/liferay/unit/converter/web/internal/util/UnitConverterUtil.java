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

package com.liferay.unit.converter.web.internal.util;

import com.liferay.unit.converter.web.internal.model.UnitConverter;

/**
 * @author James Lefeu
 */
public class UnitConverterUtil {

	public static final int TEMPERATURE_CELSIUS = 1;

	public static final int TEMPERATURE_FAHRENHEIHT = 2;

	public static double convertArea(int fromId, int toId, double fromValue) {
		return (fromValue / _AREA[fromId]) * _AREA[toId];
	}

	public static double convertLength(int fromId, int toId, double fromValue) {
		return (fromValue / _LENGTH[fromId]) * _LENGTH[toId];
	}

	public static double convertMass(int fromId, int toId, double fromValue) {
		return (fromValue / _MASS[fromId]) * _MASS[toId];
	}

	public static double convertTemperature(
		int fromId, int toId, double fromValue) {

		return _fromTemperature(toId, _toTemperature(fromId, fromValue));
	}

	public static double convertVolume(int fromId, int toId, double fromValue) {
		return (fromValue / _VOLUME[fromId]) * _VOLUME[toId];
	}

	public static UnitConverter getUnitConverter(
		int type, int fromId, int toId, double fromValue) {

		double toValue = 0;

		if (type == 0) {
			toValue = convertLength(fromId, toId, fromValue);
		}
		else if (type == 1) {
			toValue = convertArea(fromId, toId, fromValue);
		}
		else if (type == 2) {
			toValue = convertVolume(fromId, toId, fromValue);
		}
		else if (type == 3) {
			toValue = convertMass(fromId, toId, fromValue);
		}
		else if (type == 4) {
			toValue = convertTemperature(fromId, toId, fromValue);
		}

		return new UnitConverter(type, fromId, toId, fromValue, toValue);
	}

	private static final double _fromTemperature(int toId, double fromValue) {
		if (toId == 0) {

			// Kelvin

			return fromValue;
		}
		else if (toId == 1) {

			// Celsius

			return fromValue - 273.15;
		}
		else if (toId == 2) {

			// Fahrenheit

			return (1.8 * fromValue) - 459.67;
		}
		else if (toId == 3) {

			// Rankine

			return 1.8 * fromValue;
		}
		else if (toId == 4) {

			// R?aumure

			return .8 * (fromValue - 273.15);
		}
		else {
			return 0;
		}
	}

	private static final double _toTemperature(int fromId, double fromValue) {
		if (fromId == 0) {

			// Kelvin

			return fromValue;
		}
		else if (fromId == 1) {

			// Celsius

			return fromValue + 273.15;
		}
		else if (fromId == 2) {

			// Fahrenheit

			return .5555555555 * (fromValue + 459.67);
		}
		else if (fromId == 3) {

			// Rankine

			return .5555555555 * fromValue;
		}
		else if (fromId == 4) {

			// R?aumure

			return (1.25 * fromValue) + 273.15;
		}
		else {
			return 0;
		}
	}

	private static final double[] _AREA = new double[] {
		1.0,				// Square Kilometer
		1000000.0,			// Square Meter
		10000000000.0,		// Square Centimeter
		1000000000000.0,	// Square Millimeter
		10763910,			// Square Foot
		1550003000,			// Square Inch
		1195990,			// Square Yard
		0.3861022,			// Square Mile
		100,				// Hectare
		247.1054,			// Acre
	};

	private static final double[] _LENGTH = new double[] {
		1.0,				// Meter
		1000.0,				// Millimeter
		100.0,				// Centimeter
		0.001,				// Kilometer
		3.28084,			// Foot
		39.37008,			// Inch
		1.093613,			// Yard
		0.000621,			// Mile
		2.187227,			// Cubit
		4.374453,			// Talent
		13.12336			// Handbreath
	};

	private static final double[] _MASS = new double[] {
		1.0,				// Kilogram
		2.204623,			// Pound
		0.00110,			// Ton
		0.02939497,			// Talent
		1.763698,			// Mina
		88.18491,			// Shekel
		132.2774,			// Pim
		176.2698,			// Beka
		1763.698,			// Gerah
	};

	private static final double[] _VOLUME = new double[] {
		1.0,				// Liter
		1000,				// Cubic Centimeter
		61.02374,			// Cubic Inch (Liquid Measure)
		1.816166,			// Pint (Dry Measure)
		0.004729599,		// Cor (Homer)
		0.009459198,		// Lethek
		0.04729599,			// Ephah
		0.141888,			// Seah
		0.4729599,			// Omer
		0.851328,			// Cab
		0.04402868,			// Bath
		0.2641721,			// Hin
		3.170065,			// Log
	};

}