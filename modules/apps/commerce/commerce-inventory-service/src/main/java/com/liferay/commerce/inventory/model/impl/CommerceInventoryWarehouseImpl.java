/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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