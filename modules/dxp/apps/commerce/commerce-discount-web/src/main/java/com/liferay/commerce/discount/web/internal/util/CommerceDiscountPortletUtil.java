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