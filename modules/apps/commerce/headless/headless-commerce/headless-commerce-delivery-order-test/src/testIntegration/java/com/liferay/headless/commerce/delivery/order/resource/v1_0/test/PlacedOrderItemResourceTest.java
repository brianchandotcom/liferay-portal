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

package com.liferay.headless.commerce.delivery.order.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.headless.commerce.delivery.order.client.dto.v1_0.PlacedOrderItem;
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
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class PlacedOrderItemResourceTest
	extends BasePlacedOrderItemResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(), "business", 1, _serviceContext);

		_commerceCurrency = _commerceCurrencyLocalService.addCommerceCurrency(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), BigDecimal.ONE, new HashMap<>(), 2,
			2, "HALF_EVEN", false, RandomTestUtil.nextDouble(), true);

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			RandomTestUtil.randomString(), testGroup.getGroupId(),
			RandomTestUtil.randomString(),
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			testGroup.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED);

		_commerceOrderLocalService.updateCommerceOrder(_commerceOrder);

		_commercePriceList =
			_commercePriceListLocalService.addCommercePriceList(
				RandomTestUtil.randomString(), testGroup.getGroupId(),
				_user.getUserId(), _commerceCurrency.getCommerceCurrencyId(),
				true, CommercePriceListConstants.TYPE_PRICE_LIST, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.nextDouble(), 1,
				1, 2022, 12, 0, 0, 0, 0, 0, 0, true, _serviceContext);
	}

	@Ignore
	@Override
	@Test
	public void testGetPlacedOrderPlacedOrderItemsPageWithPagination()
		throws Exception {

		super.testGetPlacedOrderPlacedOrderItemsPageWithPagination();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"productId", "quantity", "sku", "skuId", "subscription"
		};
	}

	@Override
	protected PlacedOrderItem randomPlacedOrderItem() throws Exception {
		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_commerceCPInstances.add(cpInstance);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		_commerceCPDefinitions.add(cpDefinition);

		return new PlacedOrderItem() {
			{
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				productId = cpDefinition.getCProductId();
				quantity = RandomTestUtil.randomInt(1, 100);
				sku = cpInstance.getSku();
				skuId = cpInstance.getCPInstanceId();
				subscription = false;
				valid = true;
			}
		};
	}

	@Override
	protected PlacedOrderItem testGetPlacedOrderItem_addPlacedOrderItem()
		throws Exception {

		return _addCommerceOrderItem(randomPlacedOrderItem());
	}

	@Override
	protected PlacedOrderItem
			testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
				Long placedOrderId, PlacedOrderItem placedOrderItem)
		throws Exception {

		return _addCommerceOrderItem(placedOrderItem);
	}

	@Override
	protected Long testGetPlacedOrderPlacedOrderItemsPage_getPlacedOrderId()
		throws Exception {

		return _commerceOrder.getCommerceOrderId();
	}

	@Override
	protected PlacedOrderItem testGraphQLPlacedOrderItem_addPlacedOrderItem()
		throws Exception {

		return _addCommerceOrderItem(randomPlacedOrderItem());
	}

	private PlacedOrderItem _addCommerceOrderItem(
			PlacedOrderItem placedOrderItem)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getCommerceAccount(
				_accountEntry.getAccountEntryId());

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), _commerceOrder.getCommerceOrderId(),
				placedOrderItem.getSkuId(), null, placedOrderItem.getQuantity(),
				placedOrderItem.getQuantity(),
				new TestCommerceContext(
					_commerceCurrency, _commerceChannel, _user, testGroup,
					commerceAccount, _commerceOrder),
				_serviceContext);

		_commerceOrderItems.add(commerceOrderItem);

		return new PlacedOrderItem() {
			{
				id = commerceOrderItem.getCommerceOrderItemId();
				name = commerceOrderItem.getName();
				productId = commerceOrderItem.getCProductId();
				quantity = commerceOrderItem.getQuantity();
				sku = commerceOrderItem.getSku();
				skuId = commerceOrderItem.getCPInstanceId();
				subscription = false;
				valid = true;
			}
		};
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private final List<CPDefinition> _commerceCPDefinitions = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<CPInstance> _commerceCPInstances = new ArrayList<>();

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@DeleteAfterTestRun
	private final List<CommerceOrderItem> _commerceOrderItems =
		new ArrayList<>();

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private CommercePriceList _commercePriceList;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}