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

package com.liferay.commerce.internal.upgrade.v4_9_0;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_9_0.util.CommerceOrderItemTable;

/**
 * @author Igor Beslic
 * @author Riccardo Alberti
 */
public class CommerceOrderItemUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"parentCommerceOrderItemId", "LONG");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"unitPriceWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"promoPriceWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"discountWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"finalPriceWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"discountPctLevel1WithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"discountPctLevel2WithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"discountPctLevel3WithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"discountPctLevel4WithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderItemTable.class, CommerceOrderItemTable.TABLE_NAME,
			"commercePriceListId", "LONG");
	}

}