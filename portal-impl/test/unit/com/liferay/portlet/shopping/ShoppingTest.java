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
package com.liferay.portlet.shopping;

import com.liferay.portlet.shopping.model.impl.ShoppingItemImpl;

import jodd.bean.BeanUtil;

import jodd.typeconverter.Convert;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ShoppingTest {

	@Test
	public void testShoppingItemBeanProperty_InfiniteStock() {
		ShoppingItemImpl shoppingItemImpl = new ShoppingItemImpl();

		Object beanValue = BeanUtil.getProperty(
			shoppingItemImpl, "infiniteStock");

		boolean value = Convert.toBooleanValue(beanValue, false);

		Assert.assertFalse(value);

		shoppingItemImpl.setStockQuantity(
			ShoppingItemImpl.STOCK_QUANTITY_INFINITE_STOCK);

		beanValue = BeanUtil.getProperty(shoppingItemImpl, "infiniteStock");

		value = Convert.toBooleanValue(beanValue, false);

		Assert.assertTrue(value);
	}

}