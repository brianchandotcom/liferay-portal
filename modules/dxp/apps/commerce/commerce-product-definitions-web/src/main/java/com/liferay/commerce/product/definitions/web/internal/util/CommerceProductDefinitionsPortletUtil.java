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

import com.liferay.commerce.product.definitions.web.internal.util.comparator.CommerceProductDefinitionCreateDateComparator;
import com.liferay.commerce.product.definitions.web.internal.util.comparator.CommerceProductDefinitionDisplayDateComparator;
import com.liferay.commerce.product.definitions.web.internal.util.comparator.CommerceProductDefinitionOptionRelCreateDateComparator;
import com.liferay.commerce.product.definitions.web.internal.util.comparator.CommerceProductDefinitionOptionRelNameComparator;
import com.liferay.commerce.product.definitions.web.internal.util.comparator.CommerceProductInstanceCreateDateComparator;
import com.liferay.commerce.product.definitions.web.internal.util.comparator.CommerceProductInstanceDisplayDateComparator;
import com.liferay.commerce.product.definitions.web.internal.util.comparator.CommerceProductInstanceSkuComparator;
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.model.CommerceProductInstance;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceProductDefinitionsPortletUtil {

	public static OrderByComparator<CommerceProductDefinitionOptionRel>
	getCommerceProductDefinitionOptionRelOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceProductDefinitionOptionRel>
			orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator =
				new CommerceProductDefinitionOptionRelCreateDateComparator(
					orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator =
				new CommerceProductDefinitionOptionRelNameComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceProductDefinition>
	getCommerceProductDefinitionOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceProductDefinition> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator =
				new CommerceProductDefinitionCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator =
				new CommerceProductDefinitionDisplayDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceProductInstance>
	getCommerceProductInstanceOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceProductInstance> orderByComparator = null;

		if (orderByCol.equals("sku")) {
			orderByComparator = new CommerceProductInstanceSkuComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceProductInstanceCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator =
				new CommerceProductInstanceDisplayDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

}