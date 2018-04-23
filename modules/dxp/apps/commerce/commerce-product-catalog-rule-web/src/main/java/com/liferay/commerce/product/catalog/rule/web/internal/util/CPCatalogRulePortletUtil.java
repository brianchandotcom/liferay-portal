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

package com.liferay.commerce.product.catalog.rule.web.internal.util;

import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.model.CPRuleUserSegmentRel;
import com.liferay.commerce.product.util.comparator.CPRuleCreateDateComparator;
import com.liferay.commerce.product.util.comparator.CPRuleUserSegmentRelCreateDateComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPCatalogRulePortletUtil {

	public static OrderByComparator<CPRule> getCPRuleOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPRule> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CPRuleCreateDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPRuleSort(String orderByCol, String orderByType) {
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

	public static OrderByComparator<CPRuleUserSegmentRel>
		getCPRuleUserSegmentRelOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPRuleUserSegmentRel> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CPRuleUserSegmentRelCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}