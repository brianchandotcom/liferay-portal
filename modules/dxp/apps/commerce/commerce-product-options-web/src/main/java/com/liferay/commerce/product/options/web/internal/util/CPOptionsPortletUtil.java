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
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.util.comparator.CPOptionCategoryPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPOptionCategoryTitleComparator;
import com.liferay.commerce.product.util.comparator.CPOptionValuePriorityComparator;
import com.liferay.commerce.product.util.comparator.CPOptionValueTitleComparator;
import com.liferay.commerce.product.util.comparator.CPSpecificationOptionTitleComparator;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CPOptionsPortletUtil {

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

	public static OrderByComparator<CPOptionValue>
		getCPOptionValueOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPOptionValue> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CPOptionValuePriorityComparator(orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new CPOptionValueTitleComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPOptionValueSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (Objects.equals(orderByCol, "title")) {
			sort = SortFactoryUtil.create("title", Sort.STRING_TYPE, reverse);
		}
		else if (Objects.equals(orderByCol, "priority")) {
			sort = SortFactoryUtil.create("priority", Sort.INT_TYPE, reverse);
		}

		return sort;
	}

	public static OrderByComparator<CPSpecificationOption>
		getCPSpecificationOptionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPSpecificationOption> orderByComparator = null;

		if (orderByCol.equals("title")) {
			orderByComparator = new CPSpecificationOptionTitleComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}