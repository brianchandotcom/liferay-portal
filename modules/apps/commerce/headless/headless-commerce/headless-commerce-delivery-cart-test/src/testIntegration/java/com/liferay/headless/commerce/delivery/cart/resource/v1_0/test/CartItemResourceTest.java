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

package com.liferay.headless.commerce.delivery.cart.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CartItem;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class CartItemResourceTest extends BaseCartItemResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_serviceContext.getUserId(), "Test Business Account", null, null,
			null, null, _serviceContext);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		List<CommerceOrder> commerceOrders =
			_commerceOrderLocalService.getCommerceOrders(
				_commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(), -1, -1, null);

		for (CommerceOrder commerceOrder : commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(
				commerceOrder.getCommerceOrderId());
		}

		if (_accountEntry != null) {
			_accountEntryLocalService.deleteAccountEntry(_accountEntry);
		}
	}

	@Ignore
	@Override
	@Test
	public void testDeleteCartItem() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetCartItemsPageWithPagination() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteCartItem() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"quantity"};
	}

	@Override
	protected CartItem randomCartItem() throws Exception {
		CPInstance cpInstance = _addCPInstance();

		return new CartItem() {
			{
				quantity = 1;
				sku = cpInstance.getSku();
				skuId = cpInstance.getCPInstanceId();
			}
		};
	}

	@Override
	protected CartItem testDeleteCartItem_addCartItem() throws Exception {
		return _addCartItem();
	}

	@Override
	protected CartItem testGetCartItem_addCartItem() throws Exception {
		return _addCartItem();
	}

	@Override
	protected CartItem testGetCartItemsPage_addCartItem(
			Long cartId, CartItem cartItem)
		throws Exception {

		return cartItemResource.postCartItem(cartId, cartItem);
	}

	@Override
	protected Long testGetCartItemsPage_getCartId() throws Exception {
		CommerceOrder commerceOrder = _addCommerceOrder();

		return commerceOrder.getCommerceOrderId();
	}

	@Override
	protected CartItem testGraphQLCartItem_addCartItem() throws Exception {
		return _addCartItem();
	}

	@Override
	protected CartItem testPatchCartItem_addCartItem() throws Exception {
		return _addCartItem();
	}

	@Override
	protected CartItem testPostCartItem_addCartItem(CartItem cartItem)
		throws Exception {

		return _addCartItem(cartItem);
	}

	@Override
	protected CartItem testPutCartItem_addCartItem() throws Exception {
		return _addCartItem();
	}

	private CartItem _addCartItem() throws Exception {
		CommerceOrder commerceOrder = _addCommerceOrder();

		return cartItemResource.postCartItem(
			commerceOrder.getCommerceOrderId(), randomCartItem());
	}

	private CartItem _addCartItem(CartItem cartItem) throws Exception {
		CommerceOrder commerceOrder = _addCommerceOrder();

		return cartItemResource.postCartItem(
			commerceOrder.getCommerceOrderId(), cartItem);
	}

	private CommerceOrder _addCommerceOrder() throws Exception {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(), 0);

		return _commerceOrder;
	}

	private CPInstance _addCPInstance() throws Exception {
		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId());

		cpInstance.setPrice(BigDecimal.valueOf(RandomTestUtil.randomDouble()));

		CPInstanceLocalServiceUtil.updateCPInstance(cpInstance);

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, cpInstance.getSku(),
			10);

		_cpInstances.add(cpInstance);

		return cpInstance;
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private List<CPInstance> _cpInstances = new ArrayList<>();

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}