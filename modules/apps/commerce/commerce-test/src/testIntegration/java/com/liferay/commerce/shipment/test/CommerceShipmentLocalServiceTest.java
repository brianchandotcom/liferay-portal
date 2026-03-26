/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipment.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShippingFixedOption;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.shipment.test.util.CommerceShipmentTestUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Giuseppe Pelusi
 */
@RunWith(Arquillian.class)
public class CommerceShipmentLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.getPermissionCheckerMethodTestRule());

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_user.getUserId(), _group.getGroupId(), _commerceCurrency.getCode());
	}

	@After
	public void tearDown() throws Exception {
		for (CommerceOrder commerceOrder : _commerceOrders) {
			CommerceTestUtil.deleteCommerceOrder(
				commerceOrder.getCommerceOrderId());
		}

		CommerceTestUtil.deleteCommerceChannel(_commerceChannel);

		CommerceCurrencyTestUtil.deleteCommerceCurrency(_commerceCurrency);

		UserTestUtil.deleteUser(_user);

		GroupTestUtil.deleteGroup(_group);
	}

	@Test
	public void testShipmentsAreNotDuplicatedForEachOrderItem() throws Exception {
		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse();

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		for (int i = 0; i < 7; i++) {
			CPInstance cpInstance = CommerceTestUtil.createCPInstance(
				_user.getUserId(), _group.getGroupId());

			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), BigDecimal.ONE);

			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), commerceInventoryWarehouse,
				BigDecimal.valueOf(100), cpInstance.getSku(), StringPool.BLANK);
		}

		int orderStatusIndex = RandomTestUtil.randomInt(
			0, CommerceShipmentConstants.ALLOWED_ORDER_STATUSES.length - 1);

		int orderStatus =
			CommerceShipmentConstants.ALLOWED_ORDER_STATUSES[orderStatusIndex];

		commerceOrder.setOrderStatus(orderStatus);

		commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder.getCommerceOrderId());

		CommerceAddress billingCommerceAddress =
			CommerceTestUtil.addUserCommerceAddress(
				_commerceChannel.getGroupId(), _user.getUserId());
		CommerceAddress shippingCommerceAddress =
			CommerceTestUtil.addUserCommerceAddress(
				_commerceChannel.getGroupId(), _user.getUserId());

		commerceOrder.setBillingAddressId(
			billingCommerceAddress.getCommerceAddressId());
		commerceOrder.setShippingAddressId(
			shippingCommerceAddress.getCommerceAddressId());

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.nextDouble());

		CommerceShippingMethod commerceShippingMethod =
			CommerceTestUtil.addFixedRateCommerceShippingMethod(
				_user.getUserId(), _commerceChannel.getGroupId(), amount);

		commerceOrder.setCommerceShippingMethodId(
			commerceShippingMethod.getCommerceShippingMethodId());

		CommerceShippingFixedOption commerceShippingFixedOption =
			CommerceTestUtil.addCommerceShippingFixedOption(
				commerceShippingMethod, amount);

		commerceOrder.setShippingAmount(
			commerceShippingFixedOption.getAmount());
		commerceOrder.setShippingOptionName(
			commerceShippingFixedOption.getNameCurrentValue());

		_commerceOrderLocalService.updateCommerceOrder(commerceOrder);

		_commerceOrders.add(commerceOrder);

		CommerceShipment commerceShipment =
			CommerceShipmentTestUtil.createOrderShipment(
				commerceOrder.getGroupId(), commerceOrder.getCommerceOrderId(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

		List<CommerceShipment> commerceShipments =
			_commerceShipmentLocalService.getCommerceShipments(
				commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(1, commerceShipments.size());
		Assert.assertEquals(
			commerceShipment.getCommerceShipmentId(),
			commerceShipments.get(0).getCommerceShipmentId());

		int count = _commerceShipmentLocalService.getCommerceShipmentsCount(
			commerceOrder.getCommerceOrderId());

		Assert.assertEquals(1, count);
	}

	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;
	private List<CommerceOrder> _commerceOrders = new ArrayList<>();
	private Group _group;
	private User _user;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceShipmentLocalService _commerceShipmentLocalService;

}
