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

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class OrderResourceTest extends BaseOrderResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, serviceContext);

		_commerceCurrency = _commerceCurrencyLocalService.addCommerceCurrency(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), BigDecimal.ONE,
			RandomTestUtil.randomLocaleStringMap(), 2, 2, "HALF_EVEN", false,
			RandomTestUtil.nextDouble(), true);

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			RandomTestUtil.randomString(), testGroup.getGroupId(),
			RandomTestUtil.randomString(),
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), serviceContext);

		_country = _countryLocalService.addCountry(
			"XY", "XYZ", true, true, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), true, true, false, serviceContext);

		_region = _regionLocalService.addRegion(
			_country.getCountryId(), true, RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), RandomTestUtil.randomString(),
			serviceContext);

		_orderAddress = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), serviceContext);
	}

	@Test
	public void testGetOrderWithNestedFields() throws Exception {
		OrderResource orderResource = OrderResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "orderItems,orderItems.shippingAddress"
		).build();

		Order expectedOrder = orderResource.postOrder(
			_randomOrderWithNestedFields());

		Order actualOrder = orderResource.getOrder(expectedOrder.getId());

		assertEquals(expectedOrder, actualOrder);

		OrderItem[] expectedOrderItems = expectedOrder.getOrderItems();

		OrderItem[] actualOrderItems = actualOrder.getOrderItems();

		Assert.assertEquals(
			Arrays.toString(actualOrderItems), expectedOrderItems.length,
			actualOrderItems.length);
		Assert.assertNotNull(actualOrderItems[0].getShippingAddress());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteOrder() throws Exception {
		super.testGraphQLDeleteOrder();
	}

	@Override
	@Test
	public void testPatchOrder() throws Exception {
		Order postOrder = orderResource.postOrder(randomOrder());

		Order randomPatchOrder = randomPatchOrder();

		orderResource.patchOrder(postOrder.getId(), randomPatchOrder);

		Order expectedOrder = postOrder.clone();

		BeanTestUtil.copyProperties(randomPatchOrder, expectedOrder);

		Order getOrder = orderResource.getOrder(postOrder.getId());

		assertEquals(expectedOrder, getOrder);
		assertValid(getOrder);
	}

	@Override
	@Test
	public void testPatchOrderByExternalReferenceCode() throws Exception {
		Order postOrder = orderResource.postOrder(randomOrder());

		Order randomPatchOrder = randomPatchOrder();

		orderResource.patchOrderByExternalReferenceCode(
			postOrder.getExternalReferenceCode(), randomPatchOrder);

		Order expectedOrder = postOrder.clone();

		BeanTestUtil.copyProperties(randomPatchOrder, expectedOrder);

		Order getOrder = orderResource.getOrderByExternalReferenceCode(
			randomPatchOrder.getExternalReferenceCode());

		assertEquals(expectedOrder, getOrder);
		assertValid(getOrder);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"currencyCode", "paymentMethod", "printedNote",
			"purchaseOrderNumber"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"channelId", "orderId"};
	}

	@Override
	protected Order randomOrder() throws Exception {
		return new Order() {
			{
				accountExternalReferenceCode =
					_accountEntry.getExternalReferenceCode();
				accountId = _accountEntry.getAccountEntryId();
				advanceStatus = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				billingAddressId = _orderAddress.getAddressId();
				channelExternalReferenceCode =
					_commerceChannel.getExternalReferenceCode();
				channelId = _commerceChannel.getCommerceChannelId();
				couponCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				currencyCode = _commerceCurrency.getCode();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				paymentMethod = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				purchaseOrderNumber = RandomTestUtil.randomString();
				requestedDeliveryDate = RandomTestUtil.nextDate();
				shippingAddressId = _orderAddress.getAddressId();
			}
		};
	}

	@Override
	protected Order testDeleteOrder_addOrder() throws Exception {
		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testDeleteOrderByExternalReferenceCode_addOrder()
		throws Exception {

		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testGetOrder_addOrder() throws Exception {
		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testGetOrderByExternalReferenceCode_addOrder()
		throws Exception {

		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testGetOrdersPage_addOrder(Order order) throws Exception {
		return orderResource.postOrder(order);
	}

	@Override
	protected Order testGraphQLOrder_addOrder() throws Exception {
		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testPostOrder_addOrder(Order order) throws Exception {
		return orderResource.postOrder(order);
	}

	private OrderItem _randomOrderItem() throws Exception {
		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId());

		return new OrderItem() {
			{
				bookedQuantityId = RandomTestUtil.randomLong();
				deliveryGroup = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				orderExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderId = RandomTestUtil.randomLong();
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				quantity = RandomTestUtil.randomInt();
				shippedQuantity = RandomTestUtil.randomInt();
				shippingAddressId = _orderAddress.getAddressId();
				skuId = _cpInstance.getCPInstanceId();
				subscription = RandomTestUtil.randomBoolean();
			}
		};
	}

	private Order _randomOrderWithNestedFields() throws Exception {
		Order order = randomOrder();

		OrderItem orderItem = _randomOrderItem();

		orderItem.setOrderId(order.getId());

		order.setOrderItems(new OrderItem[] {orderItem});

		return order;
	}

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static AddressLocalService _addressLocalService;

	@Inject
	private static CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private static CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private static CountryLocalService _countryLocalService;

	@Inject
	private static RegionLocalService _regionLocalService;

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private Country _country;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private Address _orderAddress;

	@DeleteAfterTestRun
	private Region _region;

	@DeleteAfterTestRun
	private User _user;

}