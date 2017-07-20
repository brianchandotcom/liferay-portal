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

package com.liferay.commerce.cart.content.web.internal.util;

import com.liferay.commerce.cart.model.CommerceCartItem;
import com.liferay.commerce.cart.util.comparator.CommerceCartItemModifiedDateComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartContentPortletUtil {

	public static OrderByComparator<CommerceCartItem>
		getCommerceCartItemOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceCartItem> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new CommerceCartItemModifiedDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}