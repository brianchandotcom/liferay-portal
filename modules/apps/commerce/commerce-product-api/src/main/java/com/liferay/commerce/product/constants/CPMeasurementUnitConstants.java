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

package com.liferay.commerce.product.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CPMeasurementUnitConstants {

	public static final int TYPE_DIMENSION = 0;

	public static final int TYPE_UNIT = 2;

	public static final int TYPE_WEIGHT = 1;

	public static final Map<Integer, String> typesMap =
		Collections.unmodifiableMap(
			HashMapBuilder.put(
				TYPE_DIMENSION, "Dimensions"
			).put(
				TYPE_UNIT, "Unit"
			).put(
				TYPE_WEIGHT, "Weight"
			).build());

}