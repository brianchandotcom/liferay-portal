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