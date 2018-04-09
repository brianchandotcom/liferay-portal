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

package com.liferay.commerce.tax.engine.fixed.util;

import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.util.comparator.CommerceTaxCategoryNameComparator;
import com.liferay.commerce.tax.engine.fixed.util.comparator.CommerceTaxFixedRateAddressRelCreateDateComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxEngineFixedUtil {

	public static OrderByComparator<CommerceTaxCategory>
		getCommerceTaxCategoryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceTaxCategory> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new CommerceTaxCategoryNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceTaxFixedRateAddressRel>
		getCommerceTaxFixedRateAddressRelOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator =
			null;

		if (orderByCol.equals("create-date")) {
			orderByComparator =
				new CommerceTaxFixedRateAddressRelCreateDateComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}