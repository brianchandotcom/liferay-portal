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

/**
 * @author Riccardo Alberti
 */
public class CommerceOrderUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn("CommerceOrder", "subtotalWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "subtotalDiscountWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "subtotalDiscountPctLev1WithTax",
			"DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "subtotalDiscountPctLev2WithTax",
			"DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "subtotalDiscountPctLev3WithTax",
			"DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "subtotalDiscountPctLev4WithTax",
			"DECIMAL(30,16)");

		addColumn("CommerceOrder", "shippingWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "shippingDiscountWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "shippingDiscountPctLev1WithTax",
			"DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "shippingDiscountPctLev2WithTax",
			"DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "shippingDiscountPctLev3WithTax",
			"DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "shippingDiscountPctLev4WithTax",
			"DECIMAL(30,16)");

		addColumn("CommerceOrder", "totalWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "totalDiscountWithTaxAmount", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "totalDiscountPctLev1WithTax", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "totalDiscountPctLev2WithTax", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "totalDiscountPctLev3WithTax", "DECIMAL(30,16)");

		addColumn(
			"CommerceOrder", "totalDiscountPctLev4WithTax", "DECIMAL(30,16)");
	}

}