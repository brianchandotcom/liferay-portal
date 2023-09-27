/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.util;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.util.comparator.CommerceShippingFixedOptionPriorityComparator;
import com.liferay.commerce.shipping.engine.fixed.util.comparator.CommerceShippingFixedOptionRelCountryIdComparator;
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
				new CommerceShippingFixedOptionRelCountryIdComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}