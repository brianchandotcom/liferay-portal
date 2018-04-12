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

package com.liferay.commerce.product.item.selector.web.internal.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.search.CPInstanceIndexer;
import com.liferay.commerce.product.util.comparator.CPDefinitionDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionTitleComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceCreateDateComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceSkuComparator;
import com.liferay.commerce.product.util.comparator.CPOptionModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPOptionTitleComparator;
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
			orderByComparator = new CPDefinitionTitleComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new CPDefinitionModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CPDefinitionDisplayDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPDefinitionSort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create("display-date", orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			sort = SortFactoryUtil.create(Field.MODIFIED_DATE, orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			sort = SortFactoryUtil.create(
				Field.TITLE, Sort.STRING_TYPE, orderByAsc);
		}

		return sort;
	}

	public static OrderByComparator<CPInstance> getCPInstanceOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPInstance> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CPInstanceCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CPInstanceDisplayDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("sku")) {
			orderByComparator = new CPInstanceSkuComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPInstanceSort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, true);
		}
		else if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create("display-date", true);
		}
		else if (orderByCol.equals("sku")) {
			sort = SortFactoryUtil.create(
				CPInstanceIndexer.FIELD_SKU, Sort.STRING_TYPE, orderByAsc);
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
			orderByComparator = new CPOptionModifiedDateComparator(orderByAsc);
		}

		if (orderByCol.equals("title")) {
			orderByComparator = new CPOptionTitleComparator(orderByAsc);
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
		else if (Objects.equals(orderByCol, "title")) {
			sort = SortFactoryUtil.create(
				Field.TITLE, Sort.STRING_TYPE, reverse);
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
			orderByComparator = new CPSpecificationOptionModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new CPSpecificationOptionTitleComparator(
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

		if (Objects.equals(orderByCol, "modified-date")) {
			sort = SortFactoryUtil.create(
				Field.MODIFIED_DATE + "_sortable", reverse);
		}
		else if (Objects.equals(orderByCol, "title")) {
			sort = SortFactoryUtil.create(
				Field.TITLE, Sort.STRING_TYPE, reverse);
		}

		return sort;
	}

}