/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.web.internal.util;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.comparator.CommerceCurrencyPriorityComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCurrencyUtil {

	public static OrderByComparator<CommerceCurrency>
		getCommerceCurrencyOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceCurrency> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = CommerceCurrencyPriorityComparator.getInstance(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceCurrencySort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("priority")) {
			sort = SortFactoryUtil.create(
				Field.PRIORITY, Sort.INT_TYPE, reverse);
		}

		return sort;
	}

}