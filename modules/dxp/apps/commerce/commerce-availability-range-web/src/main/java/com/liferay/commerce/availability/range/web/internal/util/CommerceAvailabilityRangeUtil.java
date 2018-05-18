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