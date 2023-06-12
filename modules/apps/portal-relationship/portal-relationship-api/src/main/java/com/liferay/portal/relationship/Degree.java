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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Máté Thurzó
 *
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
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
		catch (NumberFormatException numberFormatException) {
			if (_log.isDebugEnabled()) {
				_log.debug(numberFormatException);
			}

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

	private static final Log _log = LogFactoryUtil.getLog(Degree.class);

	private final int _degree;

}