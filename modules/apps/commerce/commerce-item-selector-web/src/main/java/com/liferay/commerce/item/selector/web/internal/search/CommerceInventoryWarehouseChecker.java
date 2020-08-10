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

package com.liferay.commerce.item.selector.web.internal.search;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.PortletResponse;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceInventoryWarehouseChecker extends EmptyOnClickRowChecker {

	public CommerceInventoryWarehouseChecker(
		PortletResponse portletResponse,
		long[] checkedCommerceInventoryWarehouseIds,
		long[] disabledCommerceInventoryWarehouseIds) {

		super(portletResponse);

		_checkedCommerceInventoryWarehouseIds = SetUtil.fromArray(
			checkedCommerceInventoryWarehouseIds);
		_disabledCommerceInventoryWarehouseIds = SetUtil.fromArray(
			disabledCommerceInventoryWarehouseIds);
	}

	@Override
	public boolean isChecked(Object obj) {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			(CommerceInventoryWarehouse)obj;

		return _checkedCommerceInventoryWarehouseIds.contains(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			(CommerceInventoryWarehouse)obj;

		return _disabledCommerceInventoryWarehouseIds.contains(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
	}

	private final Set<Long> _checkedCommerceInventoryWarehouseIds;
	private final Set<Long> _disabledCommerceInventoryWarehouseIds;

}