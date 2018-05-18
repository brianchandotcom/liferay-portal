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

package com.liferay.commerce.product.options.web.internal.util;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.util.comparator.CPOptionCategoryModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPOptionCategoryPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPOptionCategoryTitleComparator;
import com.liferay.commerce.product.util.comparator.CPOptionValueNameComparator;
import com.liferay.commerce.product.util.comparator.CPOptionValuePriorityComparator;
import com.liferay.commerce.product.util.comparator.CPSpecificationOptionModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPSpecificationOptionTitleComparator;
import com.liferay.portal.kernel.search.Field;
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

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new CPOptionCategoryModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("group")) {
			orderByComparator = new CPOptionCategoryTitleComparator(orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CPOptionCategoryPriorityComparator(
				orderByAsc);
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
		else if (orderByCol.equals("name")) {
			orderByComparator = new CPOptionValueNameComparator(orderByAsc);
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

		if (Objects.equals(orderByCol, "name")) {
			sort = SortFactoryUtil.create(
				Field.NAME, Sort.STRING_TYPE, reverse);
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

		if (orderByCol.equals("label")) {
			orderByComparator = new CPSpecificationOptionTitleComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new CPSpecificationOptionModifiedDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPSpecificationOptionSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (Objects.equals(orderByCol, "label")) {
			sort = SortFactoryUtil.create(
				Field.TITLE, Sort.STRING_TYPE, reverse);
		}
		else if (Objects.equals(orderByCol, "modified-date")) {
			sort = SortFactoryUtil.create(
				Field.MODIFIED_DATE + "_sortable", reverse);
		}

		return sort;
	}

}