/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CouponCode;
import com.liferay.petra.string.StringBundler;
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

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_serviceContext.getUserId(), "Test Business Account", null, null,
			null, null, _serviceContext);

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
				_accountEntry.getAccountEntryId(), -1, -1, null);

		for (CommerceOrder commerceOrder : commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(
				commerceOrder.getCommerceOrderId());
		}

		if (_accountEntry != null) {
			_accountEntryLocalService.deleteAccountEntry(_accountEntry);
		}
	}

	@Override
	@Test
	public void testDeleteCart() throws Exception {
		Cart cart = testDeleteCart_addCart();

		assertHttpResponseStatusCode(
			204, cartResource.deleteCartHttpResponse(cart.getId()));
	}

	@Override
	@Test
	public void testGetCartPaymentURL() throws Exception {
		Cart cart = randomCart();

		String callbackURL = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/o/commerce-payment?groupId=",
				_commerceChannel.getGroupId(), "&nextStep=", callbackURL,
				"&uuid=", cart.getOrderUUID()),
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
		return _accountEntry.getAccountEntryId();
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
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(), 0);

		return _commerceOrder;
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

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