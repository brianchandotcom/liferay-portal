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

package com.liferay.shopping.upgrade.v1_0_0;

/**
 * @author Philip Jones
 */
public class UpgradeKernelPackage
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage {

	@Override
	protected String[][] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	private static final String[][] _CLASS_NAMES = new String[][] {
		{
			"com.liferay.portlet.shopping.model.ShoppingCart",
			"com.liferay.shopping.model.ShoppingCart"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingCartItem",
			"com.liferay.shopping.model.ShoppingCartItem"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingCategory",
			"com.liferay.shopping.model.ShoppingCategory"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingCoupon",
			"com.liferay.shopping.model.ShoppingCoupon"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingItem",
			"com.liferay.shopping.model.ShoppingItem"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingItemField",
			"com.liferay.shopping.model.ShoppingItemField"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingItemPrice",
			"com.liferay.shopping.model.ShoppingItemPrice"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingOrder",
			"com.liferay.shopping.model.ShoppingOrder"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingOrderItem",
			"com.liferay.shopping.model.ShoppingOrderItem"
		}
	};

	private static final String[][] _RESOURCE_NAMES = new String[][] {
		{"com.liferay.portlet.shopping", "com.liferay.shopping"}
	};

}