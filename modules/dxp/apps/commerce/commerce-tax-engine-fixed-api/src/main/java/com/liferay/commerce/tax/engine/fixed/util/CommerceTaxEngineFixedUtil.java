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

package com.liferay.commerce.tax.engine.fixed.util;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.util.comparator.CPTaxCategoryNameComparator;
import com.liferay.commerce.tax.engine.fixed.util.comparator.CommerceTaxFixedRateAddressRelCreateDateComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxEngineFixedUtil {

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

	public static OrderByComparator<CPTaxCategory>
		getCPTaxCategoryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPTaxCategory> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new CPTaxCategoryNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}