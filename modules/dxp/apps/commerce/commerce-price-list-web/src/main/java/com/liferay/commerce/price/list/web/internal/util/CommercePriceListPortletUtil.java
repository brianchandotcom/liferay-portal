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

package com.liferay.commerce.price.list.web.internal.util;

import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.util.comparator.CommercePriceEntryCreateDateComparator;
import com.liferay.commerce.util.comparator.CommercePriceListCreateDateComparator;
import com.liferay.commerce.util.comparator.CommercePriceListDisplayDateComparator;
import com.liferay.commerce.util.comparator.CommerceTierPriceEntryCreateDateComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListPortletUtil {

	public static OrderByComparator<CommercePriceEntry>
		getCommercePriceEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommercePriceEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommercePriceEntryCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommercePriceEntrySort(
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

	public static OrderByComparator<CommercePriceList>
		getCommercePriceListOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommercePriceList> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommercePriceListCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CommercePriceListDisplayDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommercePriceListSort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create("display-date", orderByAsc);
		}

		return sort;
	}

	public static OrderByComparator<CommerceTierPriceEntry>
		getCommerceTypePriceOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceTierPriceEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceTierPriceEntryCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceTypePriceSort(
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