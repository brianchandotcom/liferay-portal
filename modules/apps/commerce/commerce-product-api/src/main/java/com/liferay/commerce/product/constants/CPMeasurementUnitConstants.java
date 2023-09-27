/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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