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
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class OrderItemResourceTest extends BaseOrderItemResourceTestCase {

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

		_commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			testGroup.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());
	}

	@Ignore
	@Override
	@Test
	public void testDeleteOrderItem() throws Exception {
		super.testDeleteOrderItem();
	}

	@Ignore
	@Override
	@Test
	public void testDeleteOrderItemByExternalReferenceCode() throws Exception {
		super.testDeleteOrderItemByExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteOrderItem() throws Exception {
		super.testGraphQLDeleteOrderItem();
	}

	@Override
	@Test
	public void testPatchOrderItem() throws Exception {
		OrderItem postOrderItem = orderItemResource.postOrderIdOrderItem(
			_commerceOrder.getCommerceOrderId(), randomOrderItem());

		OrderItem randomPatchOrderItem = randomPatchOrderItem();

		orderItemResource.patchOrderItem(
			postOrderItem.getId(), randomPatchOrderItem);

		OrderItem expectedOrderItem = postOrderItem.clone();

		BeanTestUtil.copyProperties(randomPatchOrderItem, expectedOrderItem);

		OrderItem getOrderItem = orderItemResource.getOrderItem(
			postOrderItem.getId());

		assertEquals(expectedOrderItem, getOrderItem);
		assertValid(getOrderItem);
	}

	@Override
	@Test
	public void testPatchOrderItemByExternalReferenceCode() throws Exception {
		OrderItem postOrderItem =
			orderItemResource.postOrderByExternalReferenceCodeOrderItem(
				_commerceOrder.getExternalReferenceCode(), randomOrderItem());

		OrderItem randomPatchOrderItem = randomPatchOrderItem();

		orderItemResource.patchOrderItemByExternalReferenceCode(
			postOrderItem.getExternalReferenceCode(), randomPatchOrderItem);

		OrderItem expectedOrderItem = postOrderItem.clone();

		BeanTestUtil.copyProperties(randomPatchOrderItem, expectedOrderItem);

		OrderItem getOrderItem =
			orderItemResource.getOrderItemByExternalReferenceCode(
				postOrderItem.getExternalReferenceCode());

		assertEquals(expectedOrderItem, getOrderItem);
		assertValid(getOrderItem);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"quantity"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"sku"};
	}

	@Override
	protected OrderItem randomOrderItem() throws Exception {
		CPInstance commerceCPInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_commerceCPInstances.add(commerceCPInstance);

		return new OrderItem() {
			{
				bookedQuantityId = RandomTestUtil.randomLong();
				deliveryGroup = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountManuallyAdjusted = RandomTestUtil.randomBoolean();
				externalReferenceCode = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				orderExternalReferenceCode =
					_commerceOrder.getExternalReferenceCode();
				orderId = _commerceOrder.getCommerceOrderId();
				priceManuallyAdjusted = RandomTestUtil.randomBoolean();
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				quantity = RandomTestUtil.randomInt(1, 100);
				requestedDeliveryDate = RandomTestUtil.nextDate();
				shippedQuantity = RandomTestUtil.randomInt();
				shippingAddressId = RandomTestUtil.randomLong();
				sku = commerceCPInstance.getSku();
				skuExternalReferenceCode =
					commerceCPInstance.getExternalReferenceCode();
				skuId = commerceCPInstance.getCPInstanceId();
				subscription = RandomTestUtil.randomBoolean();
				unitOfMeasure = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected OrderItem testDeleteOrderItem_addOrderItem() throws Exception {
		return orderItemResource.postOrderIdOrderItem(
			_commerceOrder.getCommerceOrderId(), randomOrderItem());
	}

	@Override
	protected OrderItem
			testDeleteOrderItemByExternalReferenceCode_addOrderItem()
		throws Exception {

		return orderItemResource.postOrderByExternalReferenceCodeOrderItem(
			_commerceOrder.getExternalReferenceCode(), randomOrderItem());
	}

	@Override
	protected OrderItem
			testGetOrderByExternalReferenceCodeOrderItemsPage_addOrderItem(
				String externalReferenceCode, OrderItem orderItem)
		throws Exception {

		return orderItemResource.postOrderByExternalReferenceCodeOrderItem(
			externalReferenceCode, orderItem);
	}

	@Override
	protected String
			testGetOrderByExternalReferenceCodeOrderItemsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceOrder.getExternalReferenceCode();
	}

	@Override
	protected OrderItem testGetOrderIdOrderItemsPage_addOrderItem(
			Long id, OrderItem orderItem)
		throws Exception {

		return orderItemResource.postOrderIdOrderItem(id, orderItem);
	}

	@Override
	protected Long testGetOrderIdOrderItemsPage_getId() throws Exception {
		return _commerceOrder.getCommerceOrderId();
	}

	@Override
	protected OrderItem testGetOrderItem_addOrderItem() throws Exception {
		return orderItemResource.postOrderIdOrderItem(
			_commerceOrder.getCommerceOrderId(), randomOrderItem());
	}

	@Override
	protected OrderItem testGetOrderItemByExternalReferenceCode_addOrderItem()
		throws Exception {

		return orderItemResource.postOrderByExternalReferenceCodeOrderItem(
			_commerceOrder.getExternalReferenceCode(), randomOrderItem());
	}

	@Override
	protected OrderItem testGetOrderItemsPage_addOrderItem(OrderItem orderItem)
		throws Exception {

		return orderItemResource.postOrderIdOrderItem(
			_commerceOrder.getCommerceOrderId(), orderItem);
	}

	@Override
	protected OrderItem testGraphQLOrderItem_addOrderItem() throws Exception {
		return orderItemResource.postOrderIdOrderItem(
			_commerceOrder.getCommerceOrderId(), randomOrderItem());
	}

	@Override
	protected OrderItem
			testPostOrderByExternalReferenceCodeOrderItem_addOrderItem(
				OrderItem orderItem)
		throws Exception {

		return orderItemResource.postOrderByExternalReferenceCodeOrderItem(
			_commerceOrder.getExternalReferenceCode(), orderItem);
	}

	@Override
	protected OrderItem testPostOrderIdOrderItem_addOrderItem(
			OrderItem orderItem)
		throws Exception {

		return orderItemResource.postOrderIdOrderItem(
			_commerceOrder.getCommerceOrderId(), orderItem);
	}

	@Override
	protected OrderItem testPutOrderItem_addOrderItem() throws Exception {
		return orderItemResource.postOrderIdOrderItem(
			_commerceOrder.getCommerceOrderId(), randomOrderItem());
	}

	@Override
	protected OrderItem testPutOrderItemByExternalReferenceCode_addOrderItem()
		throws Exception {

		return orderItemResource.postOrderByExternalReferenceCodeOrderItem(
			_commerceOrder.getExternalReferenceCode(), randomOrderItem());
	}

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private static CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private final List<CPInstance> _commerceCPInstances = new ArrayList<>();

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private User _user;

}