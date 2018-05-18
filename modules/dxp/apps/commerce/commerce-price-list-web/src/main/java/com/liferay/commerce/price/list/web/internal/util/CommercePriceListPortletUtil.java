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

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.util.comparator.CommercePriceEntryCreateDateComparator;
import com.liferay.commerce.price.list.util.comparator.CommercePriceListCreateDateComparator;
import com.liferay.commerce.price.list.util.comparator.CommercePriceListDisplayDateComparator;
import com.liferay.commerce.price.list.util.comparator.CommercePriceListPriorityComparator;
import com.liferay.commerce.price.list.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

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

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, reverse);
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
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CommercePriceListPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommercePriceListSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, reverse);
		}
		else if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create("display-date", reverse);
		}
		else if (orderByCol.equals("priority")) {
			sort = SortFactoryUtil.create(Field.PRIORITY, reverse);
		}

		return sort;
	}

	public static OrderByComparator<CommerceTierPriceEntry>
		getCommerceTierPriceEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceTierPriceEntry> orderByComparator = null;

		if (orderByCol.equals("minQuantity")) {
			orderByComparator = new CommerceTierPriceEntryMinQuantityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceTierPriceEntrySort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, reverse);
		}

		return sort;
	}

}