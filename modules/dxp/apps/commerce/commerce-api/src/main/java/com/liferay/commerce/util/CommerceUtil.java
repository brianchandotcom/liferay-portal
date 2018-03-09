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

package com.liferay.commerce.util;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.util.comparator.CommerceAddressCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceAddressRestrictionCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceCountryPriorityComparator;
import com.liferay.commerce.util.comparator.CommercePriceEntryCreateDateComparator;
import com.liferay.commerce.util.comparator.CommercePriceListCreateDateComparator;
import com.liferay.commerce.util.comparator.CommercePriceListDisplayDateComparator;
import com.liferay.commerce.util.comparator.CommercePriceListPriorityComparator;
import com.liferay.commerce.util.comparator.CommerceRegionNameComparator;
import com.liferay.commerce.util.comparator.CommerceRegionPriorityComparator;
import com.liferay.commerce.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.commerce.util.comparator.CommerceWarehouseCityComparator;
import com.liferay.commerce.util.comparator.CommerceWarehouseItemQuantityComparator;
import com.liferay.commerce.util.comparator.CommerceWarehouseItemWarehouseNameComparator;
import com.liferay.commerce.util.comparator.CommerceWarehouseNameComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceUtil {

	public static OrderByComparator<CommerceAddress>
		getCommerceAddressOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceAddress> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceAddressCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceAddressRestriction>
		getCommerceAddressRestrictionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceAddressRestriction> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator =
				new CommerceAddressRestrictionCreateDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceAddressSort(
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

	public static OrderByComparator<CommerceCountry>
		getCommerceCountryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceCountry> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CommerceCountryPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort[] getCommerceOrderSorts(
		String orderByCol, String orderByType) {

		if (Validator.isNull(orderByCol) || Validator.isNull(orderByType)) {
			return SortFactoryUtil.getDefaultSorts();
		}

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		if (orderByCol.equals("create-date")) {
			return new Sort[] {
				SortFactoryUtil.create(
					Field.CREATE_DATE + "_sortable", reverse),
				SortFactoryUtil.create(null, Sort.SCORE_TYPE, false)
			};
		}
		else if (orderByCol.equals("order-id")) {
			return new Sort[] {
				SortFactoryUtil.create(
					Field.ENTRY_CLASS_PK + "_Number_sortable", reverse),
				SortFactoryUtil.create(null, Sort.SCORE_TYPE, false)
			};
		}
		else if (orderByCol.equals("total")) {
			return new Sort[] {
				SortFactoryUtil.create("total_Number_sortable", reverse),
				SortFactoryUtil.create(null, Sort.SCORE_TYPE, false)
			};
		}
		else {
			return SortFactoryUtil.getDefaultSorts();
		}
	}

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

	public static OrderByComparator<CommerceRegion>
		getCommerceRegionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceRegion> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new CommerceRegionNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CommerceRegionPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
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

	public static OrderByComparator<CommerceWarehouseItem>
		getCommerceWarehouseItemOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceWarehouseItem> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator =
				new CommerceWarehouseItemWarehouseNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("quantity")) {
			orderByComparator = new CommerceWarehouseItemQuantityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceWarehouse>
		getCommerceWarehouseOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceWarehouse> orderByComparator = null;

		if (orderByCol.equals("city")) {
			orderByComparator = new CommerceWarehouseCityComparator(orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new CommerceWarehouseNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}