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
import com.liferay.commerce.internal.upgrade.v4_9_0.util.CommerceOrderTable;

/**
 * @author Riccardo Alberti
 */
public class CommerceOrderUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"subtotalWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"subtotalDiscountWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"subtotalDiscountPctLev1WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"subtotalDiscountPctLev2WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"subtotalDiscountPctLev3WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"subtotalDiscountPctLev4WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"shippingWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"shippingDiscountWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"shippingDiscountPctLev1WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"shippingDiscountPctLev2WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"shippingDiscountPctLev3WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"shippingDiscountPctLev4WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"totalWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"totalDiscountWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"totalDiscountPctLev1WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"totalDiscountPctLev2WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"totalDiscountPctLev3WithTax", "DECIMAL(30,16)");

		addColumn(
			CommerceOrderTable.class, CommerceOrderTable.TABLE_NAME,
			"totalDiscountPctLev4WithTax", "DECIMAL(30,16)");
	}

}