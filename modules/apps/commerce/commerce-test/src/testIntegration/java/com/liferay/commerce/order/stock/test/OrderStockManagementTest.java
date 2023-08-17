/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.stock.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.shipment.test.util.CommerceShipmentTestUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
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
@Sync
public class OrderStockManagementTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_user.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());
	}

	@After
	public void tearDown() throws Exception {
		for (CommerceOrder commerceOrder : _commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
		}

		_commerceCurrencyLocalService.deleteCommerceCurrency(_commerceCurrency);
	}

	@Test
	public void testBackOrder() throws Exception {
		frutillaRule.scenario(
			"A not available product can be added to an order if it is " +
				"flagged as backorder enabled"
		).given(
			"A product with backorder flag enabled"
		).when(
			"I try to add the product to an order"
		).then(
			"The product will be added even if no stock is available"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			cpInstance.getCPDefinition());

		BigDecimal orderedQuantity = BigDecimal.valueOf(4);

		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), orderedQuantity);

		Assert.assertEquals(
			commerceOrderItem.toString(), orderedQuantity,
			commerceOrderItem.getQuantity());
	}

	@Test
	public void testProductAvailability() throws Exception {
		frutillaRule.scenario(
			"Product availability is updated when the product is actually " +
				"picked up"
		).given(
			"A product with stock availability"
		).when(
			"I add the product to an order"
		).and(
			"The order is 'shipped'"
		).then(
			"The product's stock availability is updated"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		BigDecimal quantity = BigDecimal.TEN;
		BigDecimal orderedQuantity = BigDecimal.valueOf(4);

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), commerceInventoryWarehouse, quantity,
				cpInstance.getSku(), StringPool.BLANK);

		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), orderedQuantity);

		Assert.assertEquals(
			commerceOrderItem.toString(), orderedQuantity,
			commerceOrderItem.getQuantity());

		commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				getCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId());

		Assert.assertTrue(
			commerceInventoryWarehouseItem.toString(),
			BigDecimalUtil.eq(
				quantity, commerceInventoryWarehouseItem.getQuantity()));

		CommerceShipmentTestUtil.createOrderShipment(
			_user.getGroupId(), commerceOrder.getCommerceOrderId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

		commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				getCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId());

		Assert.assertTrue(
			commerceInventoryWarehouseItem.toString(),
			BigDecimalUtil.eq(
				quantity.subtract(orderedQuantity),
				commerceInventoryWarehouseItem.getQuantity()));
	}

	@Test
	public void testProductAvailable() throws Exception {
		frutillaRule.scenario(
			"An available product can be added to an order"
		).given(
			"A product with stock availability"
		).when(
			"I try to add the product to an order"
		).then(
			"The product will be successfully added to the order"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse();

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), commerceInventoryWarehouse, BigDecimal.TEN,
			cpInstance.getSku(), StringPool.BLANK);

		BigDecimal orderedQuantity = BigDecimal.valueOf(2);

		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), orderedQuantity);

		Assert.assertEquals(
			commerceOrderItem.toString(), orderedQuantity,
			commerceOrderItem.getQuantity());
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testProductNotAvailable() throws Exception {
		frutillaRule.scenario(
			"A not available product cannot be added to an order"
		).given(
			"A product with no stock availability"
		).when(
			"I try to add the product to an order"
		).then(
			"The action will fail due to stock unavailability"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.valueOf(2));
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testProductNotEnoughQuantity() throws Exception {
		frutillaRule.scenario(
			"A product quantity greater than available cannot be added to an " +
				"order"
		).given(
			"A product with stock availability"
		).when(
			"I try to add the product to an order"
		).and(
			"The amount I'm trying to add is greater than the stock " +
				"availability"
		).then(
			"The action will fail due to stock unavailability"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse();

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), commerceInventoryWarehouse, BigDecimal.TEN,
			cpInstance.getSku(), StringPool.BLANK);

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.valueOf(20));
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testProductReservedQuantity() throws Exception {
		frutillaRule.scenario(
			"A product quantity greater than available-reserved cannot be " +
				"added to an order"
		).given(
			"A product with stock availability"
		).when(
			"I try to add the product to a couple of orders"
		).and(
			"The sum of the amounts added to the orders is exceeding the " +
				"total availability"
		).then(
			"The action will fail due to stock unavailability"
		);

		CommerceOrder commerceOrder1 = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder1);

		CommerceOrder commerceOrder2 = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder2);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), commerceInventoryWarehouse, BigDecimal.TEN,
			cpInstance.getSku(), StringPool.BLANK);

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder1.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.valueOf(4));

		CommerceShipmentTestUtil.createOrderShipment(
			_user.getGroupId(), commerceOrder1.getCommerceOrderId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder2.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.valueOf(8));
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static User _user;

	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private final List<CommerceOrder> _commerceOrders = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UserLocalService _userLocalService;

}