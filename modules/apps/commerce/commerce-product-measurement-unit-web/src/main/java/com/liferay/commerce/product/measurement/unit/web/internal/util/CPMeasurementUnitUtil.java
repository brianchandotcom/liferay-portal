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

package com.liferay.commerce.product.measurement.unit.web.internal.util;

import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.util.comparator.CPMeasurementUnitPriorityComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPMeasurementUnitUtil {

	public static OrderByComparator<CPMeasurementUnit>
		getCPMeasurementUnitOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPMeasurementUnit> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CPMeasurementUnitPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}