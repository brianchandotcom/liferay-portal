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

package com.liferay.commerce.discount.web.internal.util;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.util.comparator.CommerceDiscountCreateDateComparator;
import com.liferay.commerce.discount.util.comparator.CommerceDiscountRelCreateDateComparator;
import com.liferay.commerce.discount.util.comparator.CommerceDiscountRuleCreateDateComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountPortletUtil {

	public static OrderByComparator<CommerceDiscount>
		getCommerceDiscountOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceDiscount> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceDiscountCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceDiscountRel>
		getCommerceDiscountRelOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceDiscountRel> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceDiscountRelCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceDiscountRule>
		getCommerceDiscountRuleOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceDiscountRule> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceDiscountRuleCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceDiscountSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(
				Field.CREATE_DATE + "_sortable", reverse);
		}

		return sort;
	}

}