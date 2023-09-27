/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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