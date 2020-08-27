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

package com.liferay.commerce.inventory.model.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehouseImpl
	extends CommerceInventoryWarehouseBaseImpl {

	public CommerceInventoryWarehouseImpl() {
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
		getCommerceInventoryWarehouseItems() {

		return CommerceInventoryWarehouseItemLocalServiceUtil.
			getCommerceInventoryWarehouseItems(
				getCommerceInventoryWarehouseId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
	}

	@Override
	public boolean isGeolocated() {
		if ((getLatitude() == 0) && (getLongitude() == 0)) {
			return false;
		}

		return true;
	}

}