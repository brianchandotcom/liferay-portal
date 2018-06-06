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

package com.liferay.commerce.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.internal.test.util.CommerceTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.math.BigDecimal;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceCheckoutTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(), PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUserCheckout() throws Exception {
		CommerceOrder commerceOrder = CommerceTestUtil.addUserCommerceOrder(
			_group.getGroupId());

		CommerceTestUtil.addCheckoutDetailsToUserOrder(
			commerceOrder, commerceOrder.getUserId());

		commerceOrder = CommerceTestUtil.checkoutOrder(commerceOrder);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, commerceOrder.getStatus());

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		BigDecimal expectedSubTotal = BigDecimal.ZERO;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CPInstance cpInstance = commerceOrderItem.getCPInstance();

			BigDecimal price = cpInstance.getPrice();

			int quantity = commerceOrderItem.getQuantity();

			BigDecimal totalItemPrice = price.multiply(
				BigDecimal.valueOf(quantity));

			expectedSubTotal = expectedSubTotal.add(totalItemPrice);
		}

		BigDecimal actualSubTotal = commerceOrder.getSubtotal();

		Assert.assertEquals(
			expectedSubTotal.doubleValue(), actualSubTotal.doubleValue(),
			0.0001);

		BigDecimal expectedTotal = expectedSubTotal.add(
			commerceOrder.getShippingPrice());

		BigDecimal actualTotal = commerceOrder.getTotal();

		Assert.assertEquals(
			expectedTotal.doubleValue(), actualTotal.doubleValue(), 0.0001);
	}

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private Group _group;

}