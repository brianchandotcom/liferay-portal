/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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