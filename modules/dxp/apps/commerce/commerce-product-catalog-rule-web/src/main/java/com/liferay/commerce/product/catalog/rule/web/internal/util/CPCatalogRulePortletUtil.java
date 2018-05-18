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