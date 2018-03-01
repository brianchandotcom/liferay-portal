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

package com.liferay.commerce.vat.util;

import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.util.comparator.CommerceVatNumberCreateDateComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVatNumberUtil {

	public static OrderByComparator<CommerceVatNumber>
		getCommerceVatNumberOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceVatNumber> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceVatNumberCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceVatNumberSort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, orderByAsc);
		}

		return sort;
	}

}