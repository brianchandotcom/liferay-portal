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

package com.liferay.commerce.shipping.engine.fixed.util;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.util.comparator.CommerceShippingFixedOptionPriorityComparator;
import com.liferay.commerce.shipping.engine.fixed.util.comparator.CommerceShippingFixedOptionRelCommerceCountryIdComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingEngineFixedUtil {

	public static OrderByComparator<CommerceShippingFixedOption>
		getCommerceShippingFixedOptionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceShippingFixedOption> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator =
				new CommerceShippingFixedOptionPriorityComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceShippingFixedOptionRel>
		getCommerceShippingFixedOptionRelOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator =
			null;

		if (orderByCol.equals("country")) {
			orderByComparator =
				new CommerceShippingFixedOptionRelCommerceCountryIdComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}