/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.item.selector.web.internal.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.util.comparator.CPDefinitionDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionNameComparator;
import com.liferay.commerce.product.util.comparator.CPOptionModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPSpecificationOptionModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPSpecificationOptionTitleComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class CPItemSelectorViewUtil {

	public static OrderByComparator<CPDefinition>
		getCPDefinitionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinition> orderByComparator = null;

		if (orderByCol.equals("title")) {
			orderByComparator = CPDefinitionNameComparator.getInstance(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = CPDefinitionModifiedDateComparator.getInstance(
				orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = CPDefinitionDisplayDateComparator.getInstance(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPDefinitionSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create(
				Field.DISPLAY_DATE + "_sortable", reverse);
		}
		else if (orderByCol.equals("modified-date")) {
			sort = SortFactoryUtil.create(
				Field.MODIFIED_DATE + "_sortable", reverse);
		}
		else if (orderByCol.equals("name")) {
			sort = SortFactoryUtil.create(
				Field.NAME, Sort.STRING_TYPE, reverse);
		}

		return sort;
	}

	public static OrderByComparator<CPOption> getCPOptionOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPOption> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = CPOptionModifiedDateComparator.getInstance(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPOptionSort(String orderByCol, String orderByType) {
		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (Objects.equals(orderByCol, "modified-date")) {
			sort = SortFactoryUtil.create(
				Field.MODIFIED_DATE + "_sortable", reverse);
		}
		else if (Objects.equals(orderByCol, "name")) {
			sort = SortFactoryUtil.create(
				Field.NAME, Sort.STRING_TYPE, reverse);
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

		if (orderByCol.equals("modified-date")) {
			orderByComparator =
				CPSpecificationOptionModifiedDateComparator.getInstance(
					orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator =
				CPSpecificationOptionTitleComparator.getInstance(orderByAsc);
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

		if (Objects.equals(orderByCol, "modified-date")) {
			sort = SortFactoryUtil.create(
				Field.MODIFIED_DATE + "_sortable", reverse);
		}
		else if (Objects.equals(orderByCol, "priority")) {
			sort = SortFactoryUtil.create(
				Field.PRIORITY + "_sortable", reverse);
		}
		else if (Objects.equals(orderByCol, "title")) {
			sort = SortFactoryUtil.create(
				Field.TITLE, Sort.STRING_TYPE, reverse);
		}

		return sort;
	}

}