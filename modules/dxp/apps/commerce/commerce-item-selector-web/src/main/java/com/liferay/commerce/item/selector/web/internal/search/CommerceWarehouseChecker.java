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

import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.PortletResponse;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseChecker extends EmptyOnClickRowChecker {

	public CommerceWarehouseChecker(
		PortletResponse portletResponse, long[] checkedCommerceWarehouseIds,
		long[] disabledCommerceWarehouseIds) {

		super(portletResponse);

		_checkedCommerceWarehouseIds = SetUtil.fromArray(
			checkedCommerceWarehouseIds);
		_disabledCommerceWarehouseIds = SetUtil.fromArray(
			disabledCommerceWarehouseIds);
	}

	@Override
	public boolean isChecked(Object obj) {
		CommerceWarehouse commerceWarehouse = (CommerceWarehouse)obj;

		return _checkedCommerceWarehouseIds.contains(
			commerceWarehouse.getCommerceWarehouseId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		CommerceWarehouse commerceWarehouse = (CommerceWarehouse)obj;

		return _disabledCommerceWarehouseIds.contains(
			commerceWarehouse.getCommerceWarehouseId());
	}

	private final Set<Long> _checkedCommerceWarehouseIds;
	private final Set<Long> _disabledCommerceWarehouseIds;

}