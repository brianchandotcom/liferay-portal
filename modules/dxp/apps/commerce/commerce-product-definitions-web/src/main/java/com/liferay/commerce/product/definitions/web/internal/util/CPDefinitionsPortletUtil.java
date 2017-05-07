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

package com.liferay.commerce.product.definitions.web.internal.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.comparator.CPDefinitionCreateDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionRelCreateDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionRelNameComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionRelPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionValueRelPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionValueRelTitleComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceCreateDateComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceSkuComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionsPortletUtil {

	public static OrderByComparator<CPDefinitionOptionRel>
		getCPDefinitionOptionRelOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinitionOptionRel> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CPDefinitionOptionRelCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new CPDefinitionOptionRelTitleComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CPDefinitionOptionRelPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPDefinitionOptionRelSort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (Objects.equals(orderByCol, "title")) {
			sort = new Sort("title", Sort.STRING_TYPE, orderByAsc);
		}
		else if (Objects.equals(orderByCol, "create-date")) {
			sort = new Sort(Field.CREATE_DATE, true);
		}
		else if (Objects.equals(orderByCol, "priority")) {
			sort = new Sort("priority", Sort.INT_TYPE, orderByAsc);
		}

		return sort;
	}

	public static OrderByComparator<CPDefinitionOptionValueRel>
		getCPDefinitionOptionValueRelOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator =
				new CPDefinitionOptionValueRelPriorityComparator(orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new CPDefinitionOptionValueRelTitleComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPDefinitionOptionValueRelSort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (Objects.equals(orderByCol, "title")) {
			sort = new Sort("title", Sort.STRING_TYPE, orderByAsc);
		}
		else if (Objects.equals(orderByCol, "priority")) {
			sort = new Sort("priority", Sort.INT_TYPE, orderByAsc);
		}

		return sort;
	}

	public static OrderByComparator<CPDefinition>
		getCPDefinitionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinition> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new CPDefinitionModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CPDefinitionDisplayDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new CPDefinitionTitleComparator(orderByAsc);
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

		if (Objects.equals(orderByCol, "title")) {
			sort = new Sort("title", Sort.STRING_TYPE, orderByAsc);
		}
		else if (Objects.equals(orderByCol, "modified-date")) {
			sort = new Sort(Field.MODIFIED_DATE, orderByAsc);
		}
		else if (Objects.equals(orderByCol, "display-date")) {
			sort = new Sort("display-date", orderByAsc);
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

		if (orderByCol.equals("sku")) {
			orderByComparator = new CPInstanceSkuComparator(orderByAsc);
		}
		else if (orderByCol.equals("create-date")) {
			orderByComparator = new CPInstanceCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CPInstanceDisplayDateComparator(orderByAsc);
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

		if (Objects.equals(orderByCol, "sku")) {
			sort = new Sort("sku", Sort.STRING_TYPE, orderByAsc);
		}
		else if (Objects.equals(orderByCol, "create-date")) {
			sort = new Sort(Field.MODIFIED_DATE, true);
		}
		else if (Objects.equals(orderByCol, "display-date")) {
			sort = new Sort("display-date", true);
		}

		return sort;
	}

}