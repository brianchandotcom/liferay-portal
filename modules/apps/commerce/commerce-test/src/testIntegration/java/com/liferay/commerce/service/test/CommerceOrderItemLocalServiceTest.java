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

package com.liferay.commerce.service.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.exception.ProductBundleException;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.product.CommerceProductTestUtil;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPInstanceConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPOptionLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionValueLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class CommerceOrderItemLocalServiceTest {

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

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		try {
			_accountEntry = CommerceAccountTestUtil.addPersonAccountEntry(
				_user.getUserId(), _serviceContext);
		}
		catch (Exception exception) {
			_accountEntry = CommerceAccountTestUtil.getPersonAccountEntry(
				_user.getUserId());
		}

		_commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
			null, RandomTestUtil.randomString(), _commerceCurrency.getCode(),
			LocaleUtil.US.getDisplayLanguage(), _serviceContext);

		_commerceContext = new TestCommerceContext(
			null, _commerceCurrency, _commerceChannel, _user, _group, null);
	}

	@After
	public void tearDown() throws Exception {
		List<CommerceInventoryBookedQuantity>
			commerceInventoryBookedQuantities =
				_commerceBookedQuantityLocalService.
					getCommerceInventoryBookedQuantities(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				commerceInventoryBookedQuantities) {

			_commerceBookedQuantityLocalService.
				deleteCommerceInventoryBookedQuantity(
					commerceInventoryBookedQuantity);
		}

		for (CommerceOrderItem commerceOrderItem : _commerceOrderItems) {
			_commerceOrderItemLocalService.deleteCommerceOrderItem(
				_user.getUserId(), commerceOrderItem);
		}
	}

	@Test
	public void testAddCommerceOrderItem() throws Exception {
		frutillaRule.scenario(
			"Add a SKU (product instance) to an order"
		).given(
			"A group"
		).and(
			"A user"
		).and(
			"A published SKU"
		).when(
			"There is availability for the SKU"
		).then(
			"I should be able to add the SKU to an order"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		_cpInstances.add(cpInstance);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				cpInstance.getSku(), 2));

		Assert.assertNotNull(_commerceCurrency);

		Assert.assertNotNull(_accountEntry);

		_commerceChannelRel = CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId(), 0);

		_commerceOrders.add(commerceOrder);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), null, 1, 0, 0, _commerceContext,
				_serviceContext);

		_commerceOrderItems.add(commerceOrderItem);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 1, commerceOrderItems.size());

		CommerceOrderItem actualCommerceOrderItem = commerceOrderItems.get(0);

		Assert.assertEquals(
			commerceOrderItem.getCommerceOrderItemId(),
			actualCommerceOrderItem.getCommerceOrderItemId());
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testAddCommerceOrderItemWithDraftCPDefinition()
		throws Exception {

		frutillaRule.scenario(
			"Add a SKU (product instance) to an order"
		).given(
			"A group"
		).and(
			"A user"
		).and(
			"A SKU linked to an unpublished product"
		).when(
			"There is availability for the SKU"
		).then(
			"I should be able to add the SKU to an order"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId(), false, true,
			WorkflowConstants.ACTION_SAVE_DRAFT);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpDefinition.getCPDefinitionId(), CPInstanceConstants.DEFAULT_SKU);

		_cpInstances.add(cpInstance);

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				cpInstance.getSku(), 2));

		Assert.assertNotNull(_commerceCurrency);

		Assert.assertNotNull(_accountEntry);

		_commerceChannelRel = CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId(), 0);

		_commerceOrders.add(commerceOrder);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), null, 1, 0, 0, _commerceContext,
				_serviceContext);

		_commerceOrderItems.add(commerceOrderItem);
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testAddCommerceOrderItemWithDraftCPInstance() throws Exception {
		frutillaRule.scenario(
			"Add a SKU (product instance) to an order"
		).given(
			"A group"
		).and(
			"A user"
		).and(
			"An unpublished SKU"
		).when(
			"There is availability for the SKU"
		).then(
			"I should not be able to add the SKU to an order"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		_cpInstances.add(cpInstance);

		_cpInstanceLocalService.updateStatus(
			_user.getUserId(), cpInstance.getCPInstanceId(),
			WorkflowConstants.STATUS_DRAFT);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				cpInstance.getSku(), 2));

		Assert.assertNotNull(_commerceCurrency);

		Assert.assertNotNull(_accountEntry);

		_commerceChannelRel = CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId(), 0);

		_commerceOrders.add(commerceOrder);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), null, 1, 0, 0, _commerceContext,
				_serviceContext);

		_commerceOrderItems.add(commerceOrderItem);
	}

	@Test
	public void testAddProductBundleDynamicOptionLinkedToSKUAlreadyInOrder()
		throws Exception {

		frutillaRule.scenario(
			"Add a product bundle that is linked to a cpInstance already " +
				"present in the order"
		).given(
			"An order with an orderItem containing a cpInstance"
		).when(
			"I add a bundle with an option value linked to the same cpInstance"
		).then(
			"The order is updated adding 2 orderItems. 1 for the bundle and " +
				"1 for the option. Price and quantities of orderItems shall " +
					"be updated accordingly."
		);

		_assertAddProductBundleLinkedToSKUAlreadyInOrder(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC);
	}

	@Test
	public void testAddProductBundleStaticOptionLinkedToSKUAlreadyInOrder()
		throws Exception {

		frutillaRule.scenario(
			"Add a product bundle that is linked to a cpInstance already " +
				"present in the order"
		).given(
			"An order with an orderItem containing a cpInstance"
		).when(
			"I add a bundle with an option value linked to the same cpInstance"
		).then(
			"The order is updated adding 2 orderItems. 1 for the bundle and " +
				"1 for the option. Price and quantities of orderItems shall " +
					"be updated accordingly."
		);

		_assertAddProductBundleLinkedToSKUAlreadyInOrder(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC);
	}

	@Test
	public void testAddProductBundleWithBackOrderAllowedChildSKU()
		throws Exception {

		frutillaRule.scenario(
			"Add a product bundle to an order with child SKUs"
		).given(
			"A product bundle with 2 approved child SKUs"
		).and(
			"all child SKUs are unavailable but back orders are allowed"
		).when(
			"User adds product bundle to an order"
		).then(
			"Action should succeed"
		);

		_addProductBundleWithUnavailableChildSKU(true);
	}

	@Test
	public void testAddProductBundleWithDynamicOption() throws Exception {
		frutillaRule.scenario(
			"Add a product bundle with dynamic price option linked to a SKU " +
				"to an order"
		).given(
			"A catalog with 2 cpInstances"
		).and(
			"A product bundle with a dynamic-price option with values linked " +
				"to the cpInstances"
		).when(
			"I add the bundle to an order"
		).then(
			"I should have 2 orderItems in the order. 1 for the bundle and 1 " +
				"for the selected value of the option with the correct " +
					"quantities"
		);

		_addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC);
	}

	@Test
	public void testAddProductBundleWithDynamicOptionWithSameSKU()
		throws Exception {

		frutillaRule.scenario(
			"Add a product bundle with dynamic price option linked to a SKU " +
				"to an order"
		).given(
			"A catalog with 2 cpInstances"
		).and(
			"A product bundle with a dynamic-price option with values linked " +
				"to the same cpInstance"
		).when(
			"I add the bundle to an order"
		).then(
			"I should have 2 orderItems in the order. 1 for the bundle and 1 " +
				"for the selected value of the option with the correct " +
					"quantities"
		);

		_addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, true);
	}

	@Test
	public void testAddProductBundleWithStaticOptionWithNoSKU()
		throws Exception {

		frutillaRule.scenario(
			"Add a product bundle with static price option not linked to a " +
				"SKU to an order"
		).given(
			"A product bundle with a static-price option with values linked " +
				"to the cpInstances"
		).when(
			"I add the bundle to an order"
		).then(
			"I should have 1 orderItem in the order with final price as the " +
				"sum of bundle price and option price"
		);

		Assert.assertNotNull(_accountEntry);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_commerceChannelRel = CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId(), 0);

		_commerceOrders.add(commerceOrder);

		String option1Key = RandomTestUtil.randomString();
		BigDecimal option1Price = BigDecimal.valueOf(100);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, option1Key, _toValueKey(option1Key), option1Price,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 2));

		String option2Key = FriendlyURLNormalizerUtil.normalize(
			RandomTestUtil.randomString());
		BigDecimal option2Price = BigDecimal.valueOf(200);
		int option2Quantity = 3;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, option2Key, _toValueKey(option2Key), option2Price,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, option2Quantity));

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			true);

		_addOptions(
			bundleCPDefinition, false, false,
			commerceOptionValues.subList(0, 1));
		_addOptions(
			bundleCPDefinition, true, true, commerceOptionValues.subList(1, 2));

		CPInstance bundleCPInstance = _buildProductBundleSingleOptionCPInstance(
			bundleCPDefinition.getCPDefinitionId(), _toValueKey(option2Key));

		_cpInstances.add(bundleCPInstance);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				bundleCPInstance.getGroupId());

		_commercePriceEntries.add(
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, bundleCPDefinition.getCProductId(),
				bundleCPInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), BigDecimal.ZERO));

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				bundleCPInstance.getSku(), 100));

		int quantity = 1;

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				bundleCPInstance.getCPInstanceId(), null, quantity, 0, 0,
				_commerceContext, _serviceContext);

		_commerceOrderItems.add(commerceOrderItem);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 1, commerceOrderItems.size());

		CommerceOrderItem bundleOrderItem = _getOrderItemByCPInstanceId(
			bundleCPInstance.getCPInstanceId(), false, commerceOrderItems);

		Assert.assertEquals(
			commerceOrderItem.getCommerceOrderItemId(),
			bundleOrderItem.getCommerceOrderItemId());

		Assert.assertEquals(quantity, bundleOrderItem.getQuantity());

		Assert.assertEquals(option2Price, bundleOrderItem.getFinalPrice());

		CommerceOrder retrievedOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			bundleOrderItem.getFinalPrice(), retrievedOrder.getTotal());
	}

	@Test
	public void testAddProductBundleWithStaticOptionWithSameSKU()
		throws Exception {

		frutillaRule.scenario(
			"Add a product bundle with static price option linked to a SKU " +
				"to an order"
		).given(
			"A catalog with 2 cpInstances"
		).and(
			"A product bundle with a static-price option with values linked " +
				"to the same cpInstance"
		).when(
			"I add the bundle to an order"
		).then(
			"I should have 2 orderItems in the order. 1 for the bundle and 1 " +
				"for the selected value of the option with the correct " +
					"quantities"
		);

		_addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, true);
	}

	@Test
	public void testAddProductBundleWithStaticOptionWithSKU() throws Exception {
		frutillaRule.scenario(
			"Add a product bundle with static price option linked to a SKU " +
				"to an order"
		).given(
			"A catalog with 2 cpInstances"
		).and(
			"A product bundle with a static-price option with values linked " +
				"to the cpInstances"
		).when(
			"I add the bundle to an order"
		).then(
			"I should have 2 orderItems in the order. 1 for the bundle and 1 " +
				"for the selected value of the option with the correct " +
					"quantities"
		);

		_addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC);
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testAddProductBundleWithUnavailableChildSKU() throws Exception {
		frutillaRule.scenario(
			"Add a product bundle to an order with one of the child SKUs " +
				"being unavailable"
		).given(
			"A product bundle with 2 child SKUs"
		).and(
			"all SKUs are in approved state"
		).but(
			"child SKUs are unavailable in the inventory"
		).when(
			"User adds product bundle to an order"
		).then(
			"Action should fail with appropriate exception"
		);

		_addProductBundleWithUnavailableChildSKU(false);
	}

	@Test
	public void testCRUDCPInstanceLinkedByProductBundle() throws Exception {
		frutillaRule.scenario(
			"Add multiple times a product that is also linked to an option " +
				"in a product bundle already in the order and then delete it"
		).given(
			"An order with a product bundle (2 orderItems)"
		).when(
			"I add the same CPinstance that is linked to an option the bundle"
		).then(
			"Another order item shall be created with the correct quantity. " +
				"Bundle related items are not modified, even after deletion " +
					"of the new order item"
		);

		CommerceOrder commerceOrder = _addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceOrderItem commerceOrderItem1 = commerceOrderItems.get(0);

		int originalQuantity1 = commerceOrderItem1.getQuantity();

		CommerceOrderItem commerceOrderItem2 = commerceOrderItems.get(1);

		int originalQuantity2 = commerceOrderItem2.getQuantity();

		CPInstance cpInstance;

		if (commerceOrderItem1.getParentCommerceOrderItemId() == 0) {
			cpInstance = commerceOrderItem2.fetchCPInstance();
		}
		else {
			cpInstance = commerceOrderItem1.fetchCPInstance();
		}

		_commerceOrderItems.add(
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), null, 1, 0, 0, _commerceContext,
				_serviceContext));

		commerceOrderItems = commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.size(), 3, commerceOrderItems.size());

		CommerceOrderItem commerceOrderItem3 =
			_commerceOrderItemLocalService.addOrUpdateCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), "[]", 1, 0, 0, _commerceContext,
				_serviceContext);

		commerceOrderItems = commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.size(), 3, commerceOrderItems.size());

		Assert.assertEquals(
			originalQuantity1, commerceOrderItem1.getQuantity());
		Assert.assertEquals(
			originalQuantity2, commerceOrderItem2.getQuantity());

		Assert.assertEquals(2, commerceOrderItem3.getQuantity());

		_commerceOrderItemLocalService.deleteCommerceOrderItem(
			_user.getUserId(), commerceOrderItem3.getCommerceOrderItemId());

		commerceOrderItems = commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.size(), 2, commerceOrderItems.size());

		Assert.assertEquals(
			originalQuantity1, commerceOrderItem1.getQuantity());
		Assert.assertEquals(
			originalQuantity2, commerceOrderItem2.getQuantity());
	}

	@Test(expected = ProductBundleException.class)
	public void testDeleteChildOrderItemProductBundle() throws Exception {
		frutillaRule.scenario(
			"Deleting a child order item of a product bundle is not allowed"
		).given(
			"An order with a product bundle (2 orderItems)"
		).when(
			"I delete a child order item"
		).then(
			"An exception shall be raised"
		);

		CommerceOrder commerceOrder = _addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceOrderItem commerceOrderItem1 = commerceOrderItems.get(0);

		if (commerceOrderItem1.getParentCommerceOrderItemId() == 0) {
			CommerceOrderItem commerceOrderItem2 = commerceOrderItems.get(1);

			_assertDeleteOrderItem(commerceOrderItem2);
		}
		else {
			_assertDeleteOrderItem(commerceOrderItem1);
		}
	}

	@Test
	public void testDeleteProductBundleWithOptionWithSKU() throws Exception {
		frutillaRule.scenario(
			"Delete a product bundle with an option linked to a SKU from an " +
				"order"
		).given(
			"An order with a product bundle (2 orderItems)"
		).when(
			"I delete the bundle"
		).then(
			"The bundle order item and the child order item should be deleted"
		);

		CommerceOrder commerceOrder = _addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceOrderItem commerceOrderItem1 = commerceOrderItems.get(0);

		if (commerceOrderItem1.getParentCommerceOrderItemId() == 0) {
			_assertDeleteOrderItem(commerceOrderItem1);
		}
		else {
			CommerceOrderItem commerceOrderItem2 = commerceOrderItems.get(1);

			_assertDeleteOrderItem(commerceOrderItem2);
		}
	}

	@Test(expected = ProductBundleException.class)
	public void testUpdateChildOrderItemProductBundle() throws Exception {
		frutillaRule.scenario(
			"Update the product quantity of a child order item of a product " +
				"bundle is not allowed"
		).given(
			"An order with a product bundle (2 orderItems)"
		).when(
			"I change the quantity of the child order item"
		).then(
			"An exception shall be raised"
		);

		CommerceOrder commerceOrder = _addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceOrderItem commerceOrderItem1 = commerceOrderItems.get(0);
		CommerceOrderItem commerceOrderItem2 = commerceOrderItems.get(1);

		if (commerceOrderItem1.getParentCommerceOrderItemId() == 0) {
			_assertUpdateOrderItem(3, commerceOrderItem2, commerceOrderItem1);
		}
		else {
			_assertUpdateOrderItem(3, commerceOrderItem1, commerceOrderItem2);
		}
	}

	@Test
	public void testUpdateProductBundleWithDynamicOptionWithSKU()
		throws Exception {

		frutillaRule.scenario(
			"Update the product quantity of a product bundle with an option " +
				"linked to a SKU to an order"
		).given(
			"An order with a product bundle (2 orderItems)"
		).when(
			"I change the quantity of the bundle"
		).then(
			"The quantity of the bundle should be update and the quantities " +
				"of the child order items shall be updated according to the " +
					"option set up"
		);

		CommerceOrder commerceOrder = _addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceOrderItem commerceOrderItem1 = commerceOrderItems.get(0);
		CommerceOrderItem commerceOrderItem2 = commerceOrderItems.get(1);

		if (commerceOrderItem1.getParentCommerceOrderItemId() == 0) {
			_assertUpdateOrderItem(3, commerceOrderItem1, commerceOrderItem2);
		}
		else {
			_assertUpdateOrderItem(3, commerceOrderItem2, commerceOrderItem1);
		}
	}

	@Test
	public void testUpdateProductBundleWithStaticOptionWithSKU()
		throws Exception {

		frutillaRule.scenario(
			"Update the product quantity of a product bundle with an option " +
				"linked to a SKU to an order"
		).given(
			"An order with a product bundle (2 orderItems)"
		).when(
			"I change the quantity of the bundle"
		).then(
			"The quantity of the bundle should be update and the quantities " +
				"of the child order items shall be updated according to the " +
					"option set up"
		);

		CommerceOrder commerceOrder = _addProductBundleWithOptionLinkedToSKU(
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC);

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrderItemLocalService.getCommerceOrderItems(
				commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		CommerceOrderItem commerceOrderItem1 = commerceOrderItems.get(0);
		CommerceOrderItem commerceOrderItem2 = commerceOrderItems.get(1);

		if (commerceOrderItem1.getParentCommerceOrderItemId() == 0) {
			_assertUpdateOrderItem(3, commerceOrderItem1, commerceOrderItem2);
		}
		else {
			_assertUpdateOrderItem(3, commerceOrderItem2, commerceOrderItem1);
		}
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private void _addOptions(
			CPDefinition cpDefinition, boolean linkToProduct,
			boolean skuContributor,
			List<CommerceOptionValue> commerceOptionValues)
		throws Exception {

		for (CommerceOptionValue commerceOptionValue : commerceOptionValues) {
			CPOption cpOption = CPOptionLocalServiceUtil.addCPOption(
				null, _serviceContext.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				CPTestUtil.getDefaultDDMFormFieldType(skuContributor),
				RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
				skuContributor, commerceOptionValue.getOptionKey(),
				_serviceContext);

			_cpOptions.add(cpOption);

			CPOptionValue cpOptionValue =
				CPOptionValueLocalServiceUtil.addCPOptionValue(
					cpOption.getCPOptionId(),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomDouble(),
					commerceOptionValue.getOptionValueKey(), _serviceContext);

			_cpOptionValues.add(cpOptionValue);

			CPDefinitionOptionRel cpDefinitionOptionRel =
				CPDefinitionOptionRelLocalServiceUtil.addCPDefinitionOptionRel(
					cpDefinition.getCPDefinitionId(), cpOption.getCPOptionId(),
					cpOption.getNameMap(), cpOption.getDescriptionMap(),
					cpOption.getDDMFormFieldTypeName(), 0.0, false, false,
					cpOption.isSkuContributor(), true,
					commerceOptionValue.getPriceType(), _serviceContext);

			_cpDefinitionOptionRels.add(cpDefinitionOptionRel);

			if (!linkToProduct) {
				continue;
			}

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				CPDefinitionOptionValueRelLocalServiceUtil.
					getCPDefinitionOptionValueRels(
						cpDefinitionOptionRel.getCPDefinitionOptionRelId());

			CPDefinitionOptionValueRel optionCPDefinitionOptionValueRel =
				cpDefinitionOptionValueRels.get(0);

			CPDefinitionOptionValueRelLocalServiceUtil.
				updateCPDefinitionOptionValueRel(
					optionCPDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId(),
					cpOptionValue.getNameMap(), cpOptionValue.getPriority(),
					commerceOptionValue.getOptionValueKey(),
					commerceOptionValue.getCPInstanceId(),
					commerceOptionValue.getQuantity(), false,
					commerceOptionValue.getPrice(), _serviceContext);
		}
	}

	private CommerceOrder _addProductBundleWithOptionLinkedToSKU(
			String priceType)
		throws Exception {

		return _addProductBundleWithOptionLinkedToSKU(priceType, false);
	}

	private CommerceOrder _addProductBundleWithOptionLinkedToSKU(
			String priceType, boolean useSameCPInstanceForOptions)
		throws Exception {

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		BigDecimal option1Price = BigDecimal.valueOf(100);

		CPInstance optionSKU1 =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				_commerceCatalog.getGroupId());

		_cpInstances.add(optionSKU1);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				optionSKU1.getGroupId());

		CPDefinition option1CPDefinition = optionSKU1.getCPDefinition();

		CommercePriceEntry optionSKU1PriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, option1CPDefinition.getCProductId(),
				optionSKU1.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), option1Price);

		_commercePriceEntries.add(optionSKU1PriceEntry);

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, optionSKU1.getSku(),
			100);

		BigDecimal option2Price = BigDecimal.valueOf(200);

		CPInstance optionSKU2 =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				_commerceCatalog.getGroupId());

		_cpInstances.add(optionSKU2);

		CPDefinition option2CPDefinition = optionSKU2.getCPDefinition();

		CommercePriceEntry optionSKU2PriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, option2CPDefinition.getCProductId(),
				optionSKU2.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), option2Price);

		_commercePriceEntries.add(optionSKU2PriceEntry);

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, optionSKU2.getSku(),
			100);

		Assert.assertNotNull(_commerceCurrency);

		Assert.assertNotNull(_accountEntry);

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId(), 0);

		_commerceOrders.add(commerceOrder);

		String option1Key = FriendlyURLNormalizerUtil.normalize(
			RandomTestUtil.randomString());

		BigDecimal option1DeltaPrice = BigDecimal.ZERO;
		BigDecimal option2DeltaPrice = BigDecimal.ZERO;

		if (priceType.equals(CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC)) {
			option1DeltaPrice = option1Price;
			option2DeltaPrice = option2Price;
		}

		if (useSameCPInstanceForOptions) {
			optionSKU2 = optionSKU1;
		}

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				optionSKU1.getCPInstanceId(), option1Key,
				_toValueKey(option1Key), option1DeltaPrice, priceType, 2));

		String option2Key = FriendlyURLNormalizerUtil.normalize(
			FriendlyURLNormalizerUtil.normalize(RandomTestUtil.randomString()));
		int option2Quantity = 3;

		CommerceOptionValue testCommerceOptionValue =
			CommerceProductTestUtil.getCommerceOptionValue(
				optionSKU2.getCPInstanceId(), option2Key,
				_toValueKey(option2Key), option2DeltaPrice, priceType,
				option2Quantity);

		commerceOptionValues.add(testCommerceOptionValue);

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			true);

		_addOptions(
			bundleCPDefinition, false, false,
			commerceOptionValues.subList(0, 1));
		_addOptions(
			bundleCPDefinition, true, true, commerceOptionValues.subList(1, 2));

		CPInstance bundleCPInstance = _buildProductBundleSingleOptionCPInstance(
			bundleCPDefinition.getCPDefinitionId(), _toValueKey(option2Key));

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, bundleCPDefinition.getCProductId(),
			bundleCPInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), BigDecimal.ZERO);

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse,
			bundleCPInstance.getSku(), 100);

		int quantity = 1;

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				bundleCPInstance.getCPInstanceId(),
				"[" + testCommerceOptionValue.toJSON() + "]", quantity, 0, 0,
				_commerceContext, _serviceContext);

		_commerceOrderItems.add(commerceOrderItem);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 2, commerceOrderItems.size());

		CommerceOrderItem bundleOrderItem = _getOrderItemByCPInstanceId(
			bundleCPInstance.getCPInstanceId(), false, commerceOrderItems);

		CommerceOrderItem optionOrderItem = _getOrderItemByCPInstanceId(
			optionSKU2.getCPInstanceId(), true, commerceOrderItems);

		Assert.assertEquals(
			commerceOrderItem.getCommerceOrderItemId(),
			bundleOrderItem.getCommerceOrderItemId());

		Assert.assertEquals(quantity, bundleOrderItem.getQuantity());

		Assert.assertEquals(option2Quantity, optionOrderItem.getQuantity());

		BigDecimal expectedOrderFinalPrice = option2Price;

		if (priceType.equals(CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC)) {
			expectedOrderFinalPrice = optionSKU2PriceEntry.getPrice();

			if (useSameCPInstanceForOptions) {
				expectedOrderFinalPrice = optionSKU1PriceEntry.getPrice();
			}
		}

		expectedOrderFinalPrice = expectedOrderFinalPrice.multiply(
			BigDecimal.valueOf(option2Quantity));

		expectedOrderFinalPrice = expectedOrderFinalPrice.add(
			bundleOrderItem.getFinalPrice());

		CommerceOrder retrievedOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commerceOrder.getCommerceOrderId());

		Assert.assertEquals(expectedOrderFinalPrice, retrievedOrder.getTotal());

		return retrievedOrder;
	}

	private void _addProductBundleWithUnavailableChildSKU(
			boolean backOrderAllowed)
		throws Exception {

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(20),
			"cpInstance1SKU");

		_cpInstances.add(cpInstance1);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_commerceChannelRel = CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				cpInstance1.getSku(), 0));

		if (backOrderAllowed) {
			CommerceTestUtil.updateBackOrderCPDefinitionInventory(
				cpInstance1.getCPDefinition());
		}

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(30),
			"cpInstance2SKU");

		_cpInstances.add(cpInstance2);

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				cpInstance2.getSku(), 1));

		CPOption dynamicPriceTypeCPOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		_cpOptions.add(dynamicPriceTypeCPOption);

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(),
				cpInstance1.getCPInstanceId(),
				dynamicPriceTypeCPOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
				BigDecimal.valueOf(50), 1, true, true, _serviceContext);

		_cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);

		_cpDefinitionOptionValueRels.add(
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(),
				cpInstance2.getCPInstanceId(),
				dynamicPriceTypeCPOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
				BigDecimal.valueOf(100), 1, true, true, _serviceContext));

		_cpInstanceLocalService.buildCPInstances(
			bundleCPDefinition.getCPDefinitionId(), _serviceContext);

		List<CPInstance> bundleCPInstances =
			bundleCPDefinition.getCPInstances();

		Assert.assertEquals(
			bundleCPInstances.toString(), 2, bundleCPInstances.size());

		CPInstance bundleCPInstanceWithUnavailableChildSKU =
			_getBundleCPInstanceWithUnavailableChildSKU(
				cpDefinitionOptionValueRel.getCPDefinitionOptionRelId(),
				bundleCPInstances, cpInstance1);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				bundleCPInstanceWithUnavailableChildSKU.getGroupId());

		_commercePriceEntries.add(
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, bundleCPDefinition.getCProductId(),
				bundleCPInstanceWithUnavailableChildSKU.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), BigDecimal.ZERO));

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				bundleCPInstanceWithUnavailableChildSKU.getSku(), 1));

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId(), 0);

		_commerceOrders.add(commerceOrder);

		_commerceOrderItems.add(
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				bundleCPInstanceWithUnavailableChildSKU.getCPInstanceId(), null,
				1, 0, 1, _commerceContext, _serviceContext));

		commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder.getCommerceOrderId());

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 2, commerceOrderItems.size());

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (commerceOrderItem.hasParentCommerceOrderItem()) {
				Assert.assertEquals(
					cpInstance1.getCPInstanceId(),
					commerceOrderItem.getCPInstanceId());
			}
			else {
				Assert.assertEquals(
					bundleCPInstanceWithUnavailableChildSKU.getCPInstanceId(),
					commerceOrderItem.getCPInstanceId());
			}
		}
	}

	private CommerceOrder _assertAddProductBundleLinkedToSKUAlreadyInOrder(
			String priceType)
		throws Exception {

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance optionSKU1 =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				_commerceCatalog.getGroupId());

		_cpInstances.add(optionSKU1);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				optionSKU1.getGroupId());

		CPDefinition option1CPDefinition = optionSKU1.getCPDefinition();

		CommercePriceEntry optionSKU1PriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, option1CPDefinition.getCProductId(),
				optionSKU1.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(),
				BigDecimal.valueOf(111));

		_commercePriceEntries.add(optionSKU1PriceEntry);

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				optionSKU1.getSku(), 100));

		_commerceChannelRel = CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId(), 0);

		_commerceOrders.add(commerceOrder);

		int nonbundleQuantity = 10;

		_commerceOrderItems.add(
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				optionSKU1.getCPInstanceId(), null, nonbundleQuantity, 0, 0,
				_commerceContext, _serviceContext));

		BigDecimal option1Price = BigDecimal.valueOf(100);
		BigDecimal option2Price = BigDecimal.valueOf(200);

		CPInstance optionSKU2 =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				_commerceCatalog.getGroupId());

		_cpInstances.add(optionSKU2);

		CPDefinition option2CPDefinition = optionSKU2.getCPDefinition();

		_commercePriceEntries.add(
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, option2CPDefinition.getCProductId(),
				optionSKU2.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), option2Price));

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				optionSKU2.getSku(), 100));

		String option1Key = FriendlyURLNormalizerUtil.normalize(
			RandomTestUtil.randomString());

		BigDecimal option1DeltaPrice = BigDecimal.ZERO;
		BigDecimal option2DeltaPrice = BigDecimal.ZERO;

		if (priceType.equals(CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC)) {
			option1DeltaPrice = option1Price;
			option2DeltaPrice = option2Price;
		}

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();
		int option1Quantity = 2;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				optionSKU1.getCPInstanceId(), option1Key,
				_toValueKey(option1Key), option1DeltaPrice, priceType,
				option1Quantity));

		String option2Key = FriendlyURLNormalizerUtil.normalize(
			RandomTestUtil.randomString());
		int option2Quantity = 3;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				optionSKU2.getCPInstanceId(), option2Key,
				_toValueKey(option2Key), option2DeltaPrice, priceType,
				option2Quantity));

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			true);

		_addOptions(
			bundleCPDefinition, true, true, commerceOptionValues.subList(0, 1));
		_addOptions(
			bundleCPDefinition, false, false,
			commerceOptionValues.subList(1, 2));

		CPInstance bundleCPInstance = _buildProductBundleSingleOptionCPInstance(
			bundleCPDefinition.getCPDefinitionId(), _toValueKey(option1Key));

		_cpInstances.add(bundleCPInstance);

		_commercePriceEntries.add(
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, bundleCPDefinition.getCProductId(),
				bundleCPInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), BigDecimal.ZERO));

		_commerceInventoryWarehouseItems.add(
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_user.getUserId(), _commerceInventoryWarehouse,
				bundleCPInstance.getSku(), 100));

		int quantity = 1;

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), commerceOrder.getCommerceOrderId(),
				bundleCPInstance.getCPInstanceId(), null, quantity, 0, 0,
				_commerceContext, _serviceContext);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 3, commerceOrderItems.size());

		CommerceOrderItem nonbundleOrderItem = _getOrderItemByCPInstanceId(
			optionSKU1.getCPInstanceId(), false, commerceOrderItems);

		CommerceOrderItem bundleOrderItem = _getOrderItemByCPInstanceId(
			bundleCPInstance.getCPInstanceId(), false, commerceOrderItems);

		CommerceOrderItem optionOrderItem = _getOrderItemByCPInstanceId(
			optionSKU1.getCPInstanceId(), true, commerceOrderItems);

		Assert.assertEquals(
			commerceOrderItem.getCommerceOrderItemId(),
			bundleOrderItem.getCommerceOrderItemId());

		Assert.assertEquals(quantity, bundleOrderItem.getQuantity());

		Assert.assertEquals(option1Quantity, optionOrderItem.getQuantity());

		Assert.assertEquals(
			nonbundleQuantity, nonbundleOrderItem.getQuantity());

		BigDecimal expectedOrderFinalPrice = option1Price;

		if (priceType.equals(CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC)) {
			expectedOrderFinalPrice = optionSKU1PriceEntry.getPrice();
		}

		expectedOrderFinalPrice = expectedOrderFinalPrice.multiply(
			BigDecimal.valueOf(option1Quantity));

		expectedOrderFinalPrice = expectedOrderFinalPrice.add(
			bundleOrderItem.getFinalPrice());

		BigDecimal nonbundleFinalPrice = optionSKU1PriceEntry.getPrice();

		nonbundleFinalPrice = nonbundleFinalPrice.multiply(
			BigDecimal.valueOf(nonbundleQuantity));

		expectedOrderFinalPrice = expectedOrderFinalPrice.add(
			nonbundleFinalPrice);

		CommerceOrder retrievedOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commerceOrder.getCommerceOrderId());

		Assert.assertEquals(expectedOrderFinalPrice, retrievedOrder.getTotal());

		return commerceOrder;
	}

	private void _assertDeleteOrderItem(CommerceOrderItem bundleOrderItem)
		throws Exception {

		long commerceOrderId = bundleOrderItem.getCommerceOrderId();

		_commerceOrderItemLocalService.deleteCommerceOrderItem(
			_user.getUserId(), bundleOrderItem);

		CommerceOrder retrieveOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		List<CommerceOrderItem> commerceOrderItems =
			retrieveOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 0, commerceOrderItems.size());
	}

	private void _assertUpdateOrderItem(
			int factor, CommerceOrderItem bundleOrderItem,
			CommerceOrderItem childOrderItem)
		throws Exception {

		int originalBundleQuantity = bundleOrderItem.getQuantity();

		bundleOrderItem =
			_commerceOrderItemLocalService.updateCommerceOrderItem(
				_user.getUserId(), bundleOrderItem.getCommerceOrderItemId(),
				originalBundleQuantity * factor, _commerceContext,
				_serviceContext);

		Assert.assertEquals(
			originalBundleQuantity * factor, bundleOrderItem.getQuantity());

		int originalChildQuantity = childOrderItem.getQuantity();

		CommerceOrderItem updatedChildOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				childOrderItem.getCommerceOrderItemId());

		Assert.assertEquals(
			originalChildQuantity * factor,
			updatedChildOrderItem.getQuantity());

		BigDecimal originalBundlePrice = bundleOrderItem.getFinalPrice();

		BigDecimal expectedBundlePrice = originalBundlePrice.multiply(
			BigDecimal.valueOf(factor));

		Assert.assertEquals(
			expectedBundlePrice, bundleOrderItem.getFinalPrice());

		BigDecimal originalChildPrice = childOrderItem.getFinalPrice();

		BigDecimal expectedChildPrice = originalChildPrice.multiply(
			BigDecimal.valueOf(factor));

		Assert.assertEquals(
			expectedChildPrice, updatedChildOrderItem.getFinalPrice());
	}

	private CPInstance _buildProductBundleSingleOptionCPInstance(
			long cpDefinitionId, String key)
		throws Exception {

		_cpInstanceLocalService.buildCPInstances(
			cpDefinitionId, _serviceContext);

		List<CPInstance> bundleCPDefinitionApprovedCPInstances =
			_cpInstanceLocalService.getCPDefinitionApprovedCPInstances(
				cpDefinitionId);

		CPInstance cpInstance = null;

		for (CPInstance bundleCPInstance :
				bundleCPDefinitionApprovedCPInstances) {

			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpInstanceCPDefinitionOptionRelsMap =
					_cpInstanceHelper.getCPInstanceCPDefinitionOptionRelsMap(
						bundleCPInstance.getCPInstanceId());

			for (Map.Entry
					<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
						cpDefinitionOptionRel1 :
							cpInstanceCPDefinitionOptionRelsMap.entrySet()) {

				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
					cpDefinitionOptionRel1.getValue();

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					cpDefinitionOptionValueRels.get(0);

				String cpDefinitionOptionValueRelKey =
					cpDefinitionOptionValueRel.getKey();

				if (StringUtil.equalsIgnoreCase(
						cpDefinitionOptionValueRelKey, key)) {

					cpInstance = bundleCPInstance;
				}
			}
		}

		Assert.assertNotNull(
			"Instance with option value key " + key, cpInstance);

		return cpInstance;
	}

	private CPInstance _getBundleCPInstanceWithUnavailableChildSKU(
			long cpDefinitionOptionRelId, List<CPInstance> bundleCPInstances,
			CPInstance unavailableCPInstance)
		throws Exception {

		for (CPInstance bundleCPInstance : bundleCPInstances) {
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelLocalService.
					getCPInstanceCPDefinitionOptionValueRel(
						cpDefinitionOptionRelId,
						bundleCPInstance.getCPInstanceId());

			if (Objects.equals(
					cpDefinitionOptionValueRel.getCPInstanceUuid(),
					unavailableCPInstance.getCPInstanceUuid())) {

				return bundleCPInstance;
			}
		}

		return null;
	}

	private CommerceOrderItem _getOrderItemByCPInstanceId(
		long cpInstanceId, boolean insideBundle,
		List<CommerceOrderItem> commerceOrderItems) {

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (commerceOrderItem.getCPInstanceId() == cpInstanceId) {
				if (insideBundle) {
					if (commerceOrderItem.getParentCommerceOrderItemId() != 0) {
						return commerceOrderItem;
					}
				}
				else {
					return commerceOrderItem;
				}
			}
		}

		return null;
	}

	private String _toValueKey(String optionKey) {
		return "value-key-for-" + optionKey;
	}

	private AccountEntry _accountEntry;

	@Inject
	private CommerceInventoryBookedQuantityLocalService
		_commerceBookedQuantityLocalService;

	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceChannelRel _commerceChannelRel;

	private CommerceContext _commerceContext;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@DeleteAfterTestRun
	private List<CommerceInventoryWarehouseItem>
		_commerceInventoryWarehouseItems = new ArrayList<>();

	@Inject
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	private final List<CommerceOrderItem> _commerceOrderItems =
		new ArrayList<>();

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private List<CommerceOrder> _commerceOrders = new ArrayList<>();

	@DeleteAfterTestRun
	private List<CommercePriceEntry> _commercePriceEntries = new ArrayList<>();

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@DeleteAfterTestRun
	private List<CPDefinitionOptionRel> _cpDefinitionOptionRels =
		new ArrayList<>();

	@Inject
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@DeleteAfterTestRun
	private List<CPDefinitionOptionValueRel> _cpDefinitionOptionValueRels =
		new ArrayList<>();

	@Inject
	private CPInstanceHelper _cpInstanceHelper;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@DeleteAfterTestRun
	private List<CPInstance> _cpInstances = new ArrayList<>();

	@DeleteAfterTestRun
	private List<CPOption> _cpOptions = new ArrayList<>();

	@DeleteAfterTestRun
	private List<CPOptionValue> _cpOptionValues = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}