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
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.util.comparator.CPDefinitionDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionTitleComparator;
import com.liferay.commerce.product.util.comparator.CPOptionTitleComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

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

	public static OrderByComparator<CPOption> getCPOptionOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPOption> orderByComparator = null;

		if (orderByCol.equals("title")) {
			orderByComparator = new CPOptionTitleComparator(orderByAsc);
		}

		return orderByComparator;
	}

}