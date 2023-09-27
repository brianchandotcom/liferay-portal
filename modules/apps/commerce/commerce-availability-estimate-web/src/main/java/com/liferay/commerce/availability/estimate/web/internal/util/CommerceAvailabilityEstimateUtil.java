/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.availability.estimate.web.internal.util;

import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.util.comparator.CommerceAvailabilityEstimatePriorityComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAvailabilityEstimateUtil {

	public static OrderByComparator<CommerceAvailabilityEstimate>
		getCommerceAvailabilityEstimateOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator =
			null;

		if (orderByCol.equals("priority")) {
			orderByComparator =
				new CommerceAvailabilityEstimatePriorityComparator(orderByAsc);
		}

		return orderByComparator;
	}

}