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

package com.liferay.shopping.model.impl;

import com.liferay.portal.configuration.ConfigurationFactoryImpl;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ShoppingItemImplTest {

	@Before
	public void setUp() {
		ConfigurationFactoryUtil.setConfigurationFactory(
			new ConfigurationFactoryImpl());
	}

	@Test
	public void testInfiniteStock() {
		ShoppingItemImpl shoppingItemImpl = new ShoppingItemImpl();

		Assert.assertFalse(shoppingItemImpl.isInfiniteStock());

		shoppingItemImpl.setStockQuantity(
			ShoppingItemImpl.STOCK_QUANTITY_INFINITE_STOCK);

		Assert.assertTrue(shoppingItemImpl.isInfiniteStock());
	}

}