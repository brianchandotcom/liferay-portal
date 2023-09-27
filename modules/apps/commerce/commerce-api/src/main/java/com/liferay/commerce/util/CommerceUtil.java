/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.util.comparator.CommerceInventoryWarehouseCityComparator;
import com.liferay.commerce.inventory.util.comparator.CommerceInventoryWarehouseItemQuantityComparator;
import com.liferay.commerce.inventory.util.comparator.CommerceInventoryWarehouseItemWarehouseNameComparator;
import com.liferay.commerce.inventory.util.comparator.CommerceInventoryWarehouseNameComparator;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.util.comparator.CommerceAddressCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceAddressRestrictionCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceShipmentCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceShipmentExpectedDateComparator;
import com.liferay.commerce.util.comparator.CommerceShipmentIdComparator;
import com.liferay.commerce.util.comparator.CommerceShipmentItemCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceShipmentShippingDateComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
public class CommerceUtil {

	public static long getCommerceAccountId(CommerceContext commerceContext)
		throws PortalException {

		if (commerceContext == null) {
			return AccountConstants.ACCOUNT_ENTRY_ID_GUEST;
		}

		AccountEntry accountEntry = commerceContext.getAccountEntry();

		if (accountEntry != null) {
			return accountEntry.getAccountEntryId();
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker.isSignedIn()) {
			return 0;
		}

		return AccountConstants.ACCOUNT_ENTRY_ID_GUEST;
	}

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

	public static OrderByComparator<CommerceInventoryWarehouseItem>
		getCommerceInventoryWarehouseItemOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator =
			null;

		if (orderByCol.equals("name")) {
			orderByComparator =
				new CommerceInventoryWarehouseItemWarehouseNameComparator(
					orderByAsc);
		}
		else if (orderByCol.equals("quantity")) {
			orderByComparator =
				new CommerceInventoryWarehouseItemQuantityComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceInventoryWarehouse>
		getCommerceInventoryWarehouseOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceInventoryWarehouse> orderByComparator = null;

		if (orderByCol.equals("city")) {
			orderByComparator = new CommerceInventoryWarehouseCityComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new CommerceInventoryWarehouseNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceInventoryWarehouseSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("city")) {
			sort = SortFactoryUtil.create("city", Sort.STRING_TYPE, reverse);
		}
		else if (orderByCol.equals("name")) {
			sort = SortFactoryUtil.create(
				Field.NAME, Sort.STRING_TYPE, reverse);
		}

		return sort;
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

		return SortFactoryUtil.getDefaultSorts();
	}

	public static OrderByComparator<CommerceShipmentItem>
		getCommerceShipmentItemOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceShipmentItem> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceShipmentItemCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceShipment>
		getCommerceShipmentOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("create-date")) {
			return new CommerceShipmentCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("expected-delivery-date")) {
			return new CommerceShipmentExpectedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("shipment-number")) {
			return new CommerceShipmentIdComparator(orderByAsc);
		}
		else if (orderByCol.equals("shipping-date")) {
			return new CommerceShipmentShippingDateComparator(orderByAsc);
		}

		return null;
	}

	public static OrderByComparator<Country> getCountryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Country> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = OrderByComparatorFactoryUtil.create(
				"Country", "name", orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = OrderByComparatorFactoryUtil.create(
				"Country", "position", orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<Region> getRegionOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Region> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = OrderByComparatorFactoryUtil.create(
				"Region", "name", orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = OrderByComparatorFactoryUtil.create(
				"Region", "position", orderByAsc);
		}

		return orderByComparator;
	}

}