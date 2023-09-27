/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryReplenishmentItemImpl
	extends CommerceInventoryReplenishmentItemBaseImpl {

	@Override
	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws PortalException {

		return CommerceInventoryWarehouseLocalServiceUtil.
			getCommerceInventoryWarehouse(getCommerceInventoryWarehouseId());
	}

}