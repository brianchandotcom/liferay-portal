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

package com.liferay.commerce.price.list.internal.upgrade.v2_1_0;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.internal.upgrade.base.BaseCommercePriceListUpgradeProcess;
import com.liferay.commerce.price.list.internal.upgrade.v2_1_0.util.CommercePriceListTable;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListUpgradeProcess
	extends BaseCommercePriceListUpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		addColumn(
			CommercePriceListTable.class, CommercePriceListTable.TABLE_NAME,
			"type_", "VARCHAR(75)");

		addColumn(
			CommercePriceListTable.class, CommercePriceListTable.TABLE_NAME,
			"catalogBasePriceList", "BOOLEAN");

		runSQL(
			"UPDATE CommercePriceList SET type_ = '" +
				CommercePriceListConstants.TYPE_PRICE_LIST + "'");

		runSQL("UPDATE CommercePriceList SET catalogBasePriceList = [$FALSE$]");
	}

}