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

package com.liferay.portal.relationship;

/**
 * @author Máté Thurzó
 */
public class Degree {

	public static Degree infinite() {
		return new Degree(Integer.MAX_VALUE);
	}

	public static Degree minusOne(Degree degree) {
		int degreeValue = degree.getDegree();

		if (degreeValue == 1) {
			throw new RuntimeException("Relationship degree cannot be 0");
		}

		return new Degree(degreeValue - 1);
	}

	public static Degree one() {
		return new Degree(1);
	}

	public static Degree parse(String string) {
		try {
			int degree = Integer.valueOf(string);

			if (degree <= 0) {
				throw new IllegalArgumentException(
					"Relationship degree cannot be 0 or less than 0");
			}

			return new Degree(degree);
		}
		catch (NumberFormatException nfe) {
			return one();
		}
	}

	public static Degree two() {
		return new Degree(2);
	}

	public int getDegree() {
		return _degree;
	}

	private Degree(int degree) {
		_degree = degree;
	}

	private final int _degree;

}