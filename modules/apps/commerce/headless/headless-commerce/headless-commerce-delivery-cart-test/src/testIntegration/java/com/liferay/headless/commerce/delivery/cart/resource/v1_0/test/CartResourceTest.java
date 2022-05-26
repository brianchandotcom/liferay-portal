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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CouponCode;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class CartResourceTest extends BaseCartResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceAccount =
			_commerceAccountLocalService.addBusinessCommerceAccount(
				"Test Business Account", 0, null, null, true, null, null, null,
				_serviceContext);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		List<CommerceOrder> commerceOrders =
			_commerceOrderLocalService.getCommerceOrders(
				_commerceChannel.getGroupId(),
				_commerceAccount.getCommerceAccountId(), -1, -1, null);

		for (CommerceOrder commerceOrder : commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(
				commerceOrder.getCommerceOrderId());
		}

		if (_commerceAccount != null) {
			_commerceAccountLocalService.deleteCommerceAccount(
				_commerceAccount);
		}
	}

	@Test
	public void testDeleteCart() throws Exception {
		Cart cart = testDeleteCart_addCart();

		assertHttpResponseStatusCode(
			204, cartResource.deleteCartHttpResponse(cart.getId()));
	}

	@Test
	public void testGetCartPaymentURL() throws Exception {
		Cart cart = randomCart();

		String callbackURL = RandomTestUtil.randomString();

		StringBundler sb = new StringBundler(8);

		sb.append("http://localhost:8080/o/commerce-payment");

		sb.append("?groupId=");

		sb.append(_commerceChannel.getGroupId());

		sb.append("&uuid=");

		sb.append(cart.getOrderUUID());

		sb.append(StringPool.AMPERSAND);

		sb.append("nextStep=");

		sb.append(callbackURL);

		Assert.assertEquals(
			sb.toString(),
			cartResource.getCartPaymentURL(cart.getId(), callbackURL));
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteCart() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"account", "accountId", "billingAddressId", "couponCode",
			"orderTypeId", "paymentStatus", "shippingAddressId", "status"
		};
	}

	@Override
	protected Cart randomCart() throws Exception {
		CommerceOrder commerceOrder = _getCommerceOrder();

		return new Cart() {
			{
				account = commerceOrder.getCommerceAccountName();
				accountId = commerceOrder.getCommerceAccountId();
				billingAddressId = commerceOrder.getBillingAddressId();
				couponCode = commerceOrder.getCouponCode();
				currencyCode = _commerceCurrency.getCode();
				id = commerceOrder.getCommerceOrderId();
				orderTypeId = commerceOrder.getCommerceOrderTypeId();
				orderUUID = commerceOrder.getUuid();
				paymentStatus = commerceOrder.getPaymentStatus();
				shippingAddressId = commerceOrder.getShippingAddressId();
				status = WorkflowConstants.getStatusLabel(
					commerceOrder.getStatus());
			}
		};
	}

	protected Cart testDeleteCart_addCart() throws Exception {
		Cart cart = randomCart();

		return cartResource.postCartCheckout(cart.getId());
	}

	protected Cart testGetCart_addCart() throws Exception {
		Cart cart = randomCart();

		return cartResource.postCartCheckout(cart.getId());
	}

	protected Cart testGetChannelCartsPage_addCart(
			Long accountId, Long channelId, Cart cart)
		throws Exception {

		return cartResource.postCartCheckout(cart.getId());
	}

	protected Long testGetChannelCartsPage_getAccountId() throws Exception {
		return _commerceAccount.getCommerceAccountId();
	}

	protected Long testGetChannelCartsPage_getChannelId() throws Exception {
		return _commerceChannel.getCommerceChannelId();
	}

	protected Cart testGraphQLCart_addCart() throws Exception {
		Cart cart = randomCart();

		return cartResource.postCartCheckout(cart.getId());
	}

	protected Cart testPatchCart_addCart() throws Exception {
		return randomCart();
	}

	@Override
	protected Cart testPostCartCheckout_addCart(Cart cart) throws Exception {
		return cartResource.postCartCheckout(cart.getId());
	}

	protected Cart testPostCartCouponCode_addCart(Cart cart) throws Exception {
		CouponCode couponCode = new CouponCode() {
			{
				code = cart.getCouponCode();
			}
		};

		return cartResource.postCartCouponCode(cart.getId(), couponCode);
	}

	protected Cart testPutCart_addCart() throws Exception {
		Cart cart = randomCart();

		return cartResource.postCartCheckout(cart.getId());
	}

	private CommerceOrder _getCommerceOrder() throws Exception {
		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceAccount.getCommerceAccountId(),
			_commerceCurrency.getCommerceCurrencyId(), 0);

		return _commerceOrder;
	}

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}