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

package com.liferay.commerce.product.options.web.internal.util;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.util.comparator.CPOptionCategoryPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPOptionCategoryTitleComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionCategoriesPortletUtil {

	public static OrderByComparator<CPOptionCategory>
		getCPOptionCategoryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPOptionCategory> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CPOptionCategoryPriorityComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new CPOptionCategoryTitleComparator(orderByAsc);
		}

		return orderByComparator;
	}

}