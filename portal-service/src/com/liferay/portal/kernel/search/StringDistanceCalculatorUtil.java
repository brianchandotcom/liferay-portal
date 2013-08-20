/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Michael C. Han
 */
public class StringDistanceCalculatorUtil {

	public static float getDistance(String string1, String string2) {
		return getStringDistanceCalculator().getDistance(string1, string2);
	}

	public static StringDistanceCalculator getStringDistanceCalculator() {
		PortalRuntimePermission.checkGetBeanProperty(
			StringDistanceCalculatorUtil.class);

		return _stringDistanceCalculator;
	}

	public void setStringDistanceCalculator(
		StringDistanceCalculator stringDistanceCalculator) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_stringDistanceCalculator = stringDistanceCalculator;
	}

	private static StringDistanceCalculator _stringDistanceCalculator;

}