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

package com.liferay.commerce.availability.range.web.internal.util;

import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.util.comparator.CommerceAvailabilityRangePriorityComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAvailabilityRangeUtil {

	public static OrderByComparator<CommerceAvailabilityRange>
		getCommerceAvailabilityRangeOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceAvailabilityRange> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CommerceAvailabilityRangePriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}