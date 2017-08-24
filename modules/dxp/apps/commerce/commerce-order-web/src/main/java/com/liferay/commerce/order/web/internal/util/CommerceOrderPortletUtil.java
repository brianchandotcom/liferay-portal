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

package com.liferay.commerce.order.web.internal.util;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.util.comparator.CommerceOrderCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceOrderItemCreateDateComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderPortletUtil {

	public static OrderByComparator<CommerceOrderItem>
		getCommerceOrderItemOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceOrderItem> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceOrderItemCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceOrder>
		getCommerceOrderOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceOrder> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceOrderCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}