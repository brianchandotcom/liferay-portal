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

package com.liferay.commerce.address.web.internal.util;

import com.liferay.commerce.address.model.CommerceCountry;
import com.liferay.commerce.address.model.CommerceRegion;
import com.liferay.commerce.address.util.comparator.CommerceCountryNameComparator;
import com.liferay.commerce.address.util.comparator.CommerceCountryPriorityComparator;
import com.liferay.commerce.address.util.comparator.CommerceRegionNameComparator;
import com.liferay.commerce.address.util.comparator.CommerceRegionPriorityComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressUtil {

	public static OrderByComparator<CommerceCountry>
		getCommerceCountryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceCountry> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new CommerceCountryNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CommerceCountryPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceRegion>
		getCommerceRegionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceRegion> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new CommerceRegionNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CommerceRegionPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}