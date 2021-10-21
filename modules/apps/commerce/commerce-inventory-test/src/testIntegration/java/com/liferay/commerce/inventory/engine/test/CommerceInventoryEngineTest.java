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

package com.liferay.commerce.inventory.engine.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryWarehouseException;
import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryWarehouseItemException;
import com.liferay.commerce.inventory.exception.NoSuchInventoryBookedQuantityException;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
public class CommerceInventoryEngineTest {

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
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			StringPool.BLANK, _group.getGroupId(),
			_group.getName(_serviceContext.getLanguageId()) + " Portal",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null, StringPool.BLANK,
			_serviceContext);

		_cpInstance1 = CommerceInventoryTestUtil.addRandomCPInstanceSku(
			_group.getGroupId());
		_cpInstance2 = CommerceInventoryTestUtil.addRandomCPInstanceSku(
			_group.getGroupId());
	}

	@After
	public void tearDown() throws Exception {
		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(_group.getCompanyId());

		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				commerceInventoryWarehouses) {

			_commerceInventoryWarehouseLocalService.
				deleteCommerceInventoryWarehouse(commerceInventoryWarehouse);
		}
	}

	@Test(expected = DuplicateCommerceInventoryWarehouseItemException.class)
	public void testAddMultipleItemsWithSameSkuToWarehouse() throws Exception {
		frutillaRule.scenario(
			"It should not be possible to add multiple items with same SKU"
		).given(
			"1 active warehouse"
		).when(
			"The same SKU is added twice to the same warehouse"
		).then(
			"An exception is raised"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouseActive =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				true, _serviceContext);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouseActive.
					getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), 1);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouseActive.
					getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), 1);
	}

	@Test
	public void testBookedQuantityFromMultipleWarehouses() throws Exception {
		frutillaRule.scenario(
			"When the same warehouse item is added to 2 active warehouse the " +
				"maximum bookable quantity is equal to the sum of the stock " +
					"in both warehouses"
		).given(
			"2 active warehouses"
		).and(
			"A product sku in different quantities added to the warehouses"
		).when(
			"I retrieve the stock quantity after a booking quantity is added"
		).then(
			"The stock quantity shall take into consideration only the " +
				"remaining stock"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouse1 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		CommerceInventoryWarehouse commerceInventoryWarehouse2 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		int warehouse1ItemQuantity = 5;
		int warehouse2ItemQuantity = 5;

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), warehouse1ItemQuantity);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse2.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), warehouse2ItemQuantity);

		int companyStockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _cpInstance1.getSku());

		Assert.assertEquals(
			warehouse1ItemQuantity + warehouse2ItemQuantity,
			companyStockQuantity);

		int bookedQuantity = 7;

		_commerceBookedQuantityLocalService.addCommerceBookedQuantity(
			_user.getUserId(), _cpInstance1.getSku(), bookedQuantity, null,
			Collections.emptyMap());

		int remainingCompanyStockQuantity =
			_commerceInventoryEngine.getStockQuantity(
				_group.getCompanyId(), _cpInstance1.getSku());

		Assert.assertEquals(
			companyStockQuantity - bookedQuantity,
			remainingCompanyStockQuantity);
	}

	@Test
	public void testConsumeBookedQuantityFromMultipleWarehouses()
		throws Exception {

		frutillaRule.scenario(
			"When the same warehouse item is added to 2 active warehouse the " +
				"maximum bookable quantity is equal to the sum of the stock " +
					"in both warehouses"
		).given(
			"2 active warehouses"
		).and(
			"A product sku in different quantities added to the warehouses"
		).when(
			"I retrieve the stock quantity after a booking quantity is added"
		).then(
			"The stock quantity shall take into consideration only the " +
				"remaining stock"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouse1 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		CommerceInventoryWarehouse commerceInventoryWarehouse2 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		int warehouse1ItemQuantity = 5;
		int warehouse2ItemQuantity = 5;

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), warehouse1ItemQuantity);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse2.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), warehouse2ItemQuantity);

		int companyStockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _cpInstance1.getSku());

		Assert.assertEquals(
			warehouse1ItemQuantity + warehouse2ItemQuantity,
			companyStockQuantity);

		int bookedQuantity = 12;

		CommerceInventoryBookedQuantity commerceBookedQuantity =
			_commerceBookedQuantityLocalService.addCommerceBookedQuantity(
				_user.getUserId(), _cpInstance1.getSku(), bookedQuantity, null,
				Collections.emptyMap());

		int consumedQuantity = 0;

		int quantity = 3;

		_commerceInventoryEngine.consumeQuantity(
			_user.getUserId(),
			commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
			_cpInstance1.getSku(), quantity,
			commerceBookedQuantity.getCommerceInventoryBookedQuantityId(),
			Collections.emptyMap());

		int remainingCompanyStockQuantity =
			_commerceInventoryEngine.getStockQuantity(
				_group.getCompanyId(), _cpInstance1.getSku());

		consumedQuantity += quantity;

		Assert.assertEquals(
			companyStockQuantity - bookedQuantity,
			remainingCompanyStockQuantity);

		quantity = 10;

		_commerceInventoryEngine.consumeQuantity(
			_user.getUserId(),
			commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
			_cpInstance1.getSku(), quantity,
			commerceBookedQuantity.getCommerceInventoryBookedQuantityId(),
			Collections.emptyMap());

		remainingCompanyStockQuantity =
			_commerceInventoryEngine.getStockQuantity(
				_group.getCompanyId(), _cpInstance1.getSku());

		consumedQuantity += quantity;

		Assert.assertEquals(
			companyStockQuantity - consumedQuantity,
			remainingCompanyStockQuantity);
	}

	@Test(expected = NoSuchInventoryBookedQuantityException.class)
	public void testConsumeQuantity() throws Exception {
		frutillaRule.scenario(
			"When the booked quantity is consumed also the DB record shall " +
				"be deleted"
		).given(
			"1 warehouse item"
		).and(
			"Some booked quantity of that item"
		).when(
			"The quantity is consumed"
		).then(
			"The booked quantity record shall not be present"
		);

		_commerceInventoryWarehouseItem1 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_commerceChannel.getCommerceChannelId(), _cpInstance1.getSku(),
				10, _serviceContext);

		int bookQuantity = 5;

		CommerceInventoryBookedQuantity commerceBookedQuantity =
			_commerceBookedQuantityLocalService.addCommerceBookedQuantity(
				_user.getUserId(), _cpInstance1.getSku(), bookQuantity, null,
				Collections.emptyMap());

		int stockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _commerceChannel.getGroupId(),
			_cpInstance1.getSku());

		Assert.assertEquals(
			_commerceInventoryWarehouseItem1.getQuantity() - bookQuantity,
			stockQuantity);

		_commerceInventoryEngine.consumeQuantity(
			_user.getUserId(),
			_commerceInventoryWarehouseItem1.getCommerceInventoryWarehouseId(),
			_cpInstance1.getSku(), bookQuantity,
			commerceBookedQuantity.getCommerceInventoryBookedQuantityId(),
			Collections.emptyMap());

		stockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _commerceChannel.getGroupId(),
			_cpInstance1.getSku());

		Assert.assertEquals(
			_commerceInventoryWarehouseItem1.getQuantity() - bookQuantity,
			stockQuantity);

		_commerceBookedQuantityLocalService.getCommerceInventoryBookedQuantity(
			commerceBookedQuantity.getCommerceInventoryBookedQuantityId());
	}

	@Test
	public void testConsumeQuantityFromMultipleWarehouses() throws Exception {
		frutillaRule.scenario(
			"When the same warehouse item is added to 2 active warehouse the " +
				"maximum consumable quantity is equal to the sum of the " +
					"stock in both warehouses"
		).given(
			"2 active warehouses"
		).and(
			"A product sku in different quantities added to the warehouses"
		).when(
			"I retrieve the stock quantity after a consuming quantity is added"
		).then(
			"The stock quantity shall take into consideration only the " +
				"remaining stock"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouse1 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		CommerceInventoryWarehouse commerceInventoryWarehouse2 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		int warehouse1ItemQuantity = 5;
		int warehouse2ItemQuantity = 5;

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), warehouse1ItemQuantity);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse2.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), warehouse2ItemQuantity);

		int companyStockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _cpInstance1.getSku());

		Assert.assertEquals(
			warehouse1ItemQuantity + warehouse2ItemQuantity,
			companyStockQuantity);

		int quantity = 7;

		_commerceInventoryEngine.consumeQuantity(
			_user.getUserId(),
			commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
			_cpInstance1.getSku(), quantity, 0, Collections.emptyMap());

		int remainingCompanyStockQuantity =
			_commerceInventoryEngine.getStockQuantity(
				_group.getCompanyId(), _cpInstance1.getSku());

		Assert.assertEquals(
			warehouse1ItemQuantity + warehouse2ItemQuantity - quantity,
			remainingCompanyStockQuantity);
	}

	@Test(expected = DuplicateCommerceInventoryWarehouseException.class)
	public void testCreateMultipleWarehousesWithSameAttributes()
		throws Exception {

		frutillaRule.scenario(
			"It should not be possible to create multiple warehouses with " +
				"same attributes"
		).given(
			"One warehouse is created with external reference"
		).when(
			"Another warehouse with same attributes is created"
		).then(
			"An exception shall be raised"
		);

		String name = RandomTestUtil.randomString();

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.
				addCommerceInventoryWarehouseWithExternalReferenceCode(
					_user.getGroupId(), name);

		_commerceInventoryWarehouseLocalService.addCommerceInventoryWarehouse(
			commerceInventoryWarehouse.getExternalReferenceCode(),
			commerceInventoryWarehouse.getName(),
			commerceInventoryWarehouse.getDescription(),
			commerceInventoryWarehouse.isActive(),
			commerceInventoryWarehouse.getStreet1(),
			commerceInventoryWarehouse.getStreet2(),
			commerceInventoryWarehouse.getStreet3(),
			commerceInventoryWarehouse.getCity(),
			commerceInventoryWarehouse.getZip(),
			commerceInventoryWarehouse.getCommerceRegionCode(),
			commerceInventoryWarehouse.getCountryTwoLettersISOCode(),
			commerceInventoryWarehouse.getLatitude(),
			commerceInventoryWarehouse.getLongitude(),
			ServiceContextTestUtil.getServiceContext(_user.getGroupId()));
	}

	@Test
	public void testCreateWarehouse() throws Exception {
		frutillaRule.scenario(
			"It should be possible to create a warehouse"
		).given(
			"One warehouse is created"
		).when(
			"The list of warehouses is retrieved for the company"
		).then(
			"The retrieved warehouse name is equal to the created one"
		);

		String name = RandomTestUtil.randomString();

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.
				addCommerceInventoryWarehouseWithExternalReferenceCode(
					_user.getGroupId(), name);

		Assert.assertEquals(name, commerceInventoryWarehouse.getName());

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(_user.getCompanyId());

		CommerceInventoryWarehouse retrievedCommerceInventoryWarehouse =
			commerceInventoryWarehouses.get(0);

		Assert.assertEquals(
			commerceInventoryWarehouse.getName(),
			retrievedCommerceInventoryWarehouse.getName());
	}

	@Test
	public void testGetStockFromInactiveWarehouseUsingChannel()
		throws Exception {

		frutillaRule.scenario(
			"When the same warehouse item is added to one active warehouse " +
				"and an inactive warehouse associated to a channel, only the " +
					"active warehouse items shall be considered"
		).given(
			"An active warehouse"
		).and(
			"An inactive warehouse with associated channel"
		).and(
			"A product sku in different quantities added to the warehouses"
		).when(
			"I retrieve the stock quantity"
		).then(
			"The stock quantity shall take into consideration only the " +
				"active warehouse stock"
		);

		CommerceInventoryWarehouse inactiveCommerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				false, _serviceContext);

		CommerceInventoryWarehouse activeCommerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				true, _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			inactiveCommerceInventoryWarehouse.
				getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId(), _serviceContext);

		int inactiveWarehouseQuantity = 5;
		int activeWarehouseQuantity = 10;

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				inactiveCommerceInventoryWarehouse.
					getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), inactiveWarehouseQuantity);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				activeCommerceInventoryWarehouse.
					getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), activeWarehouseQuantity);

		int companyStockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _cpInstance1.getSku());

		Assert.assertEquals(activeWarehouseQuantity, companyStockQuantity);

		int channelStockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _commerceChannel.getGroupId(),
			_cpInstance1.getSku());

		Assert.assertEquals(0, channelStockQuantity);
	}

	@Test
	public void testGetStockQuantities() throws Exception {
		frutillaRule.scenario(
			"Stock quantities shall be correctly retrieved given the SKUs"
		).given(
			"2 warehouse items with different SKUs"
		).when(
			"I retrieve the stock quantity of the items"
		).then(
			"The correct value shall be returned"
		);

		_commerceInventoryWarehouseItem1 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_commerceChannel.getCommerceChannelId(), _cpInstance1.getSku(),
				10, _serviceContext);

		_commerceInventoryWarehouseItem2 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_commerceChannel.getCommerceChannelId(), _cpInstance2.getSku(),
				15, _serviceContext);

		List<String> skuList = new ArrayList<>();

		skuList.add(_cpInstance1.getSku());
		skuList.add(_cpInstance2.getSku());

		Map<String, Integer> stockQuantities =
			_commerceInventoryEngine.getStockQuantities(
				_group.getCompanyId(), _commerceChannel.getGroupId(), skuList);

		Set<String> set = stockQuantities.keySet();

		Iterator<String> iterator = set.iterator();

		while (iterator.hasNext()) {
			String sku = (String)iterator.next();

			if (Objects.equals(sku, _cpInstance1.getSku())) {
				Assert.assertEquals(
					(int)_commerceInventoryWarehouseItem1.getQuantity(),
					(int)stockQuantities.get(sku));
			}

			if (Objects.equals(sku, _cpInstance2.getSku())) {
				Assert.assertEquals(
					(int)_commerceInventoryWarehouseItem2.getQuantity(),
					(int)stockQuantities.get(sku));
			}
		}
	}

	@Test
	public void testGetStockQuantitiesForInactiveWarehouse() throws Exception {
		frutillaRule.scenario(
			"It shall not be possible to retrieve stocks from an inactive " +
				"warehouse"
		).given(
			"One inactive warehouse is created"
		).when(
			"A product is added to the warehouse"
		).then(
			"The retrieved stock quantity shall not contain the inactive " +
				"warehouse stocks"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				false, _serviceContext);

		int quantity = 10;

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), quantity);

		int stockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _cpInstance1.getSku());

		Assert.assertEquals(0, stockQuantity);
	}

	@Test
	public void testGetStockQuantitiesForInactiveWarehouseUsingChannel()
		throws Exception {

		frutillaRule.scenario(
			"It shall not be possible to retrieve stocks from an inactive " +
				"warehouse using a commerce channel to the warehouse"
		).given(
			"One inactive warehouse is created"
		).when(
			"A product is added to the warehouse"
		).then(
			"The retrieved stock quantity shall not contain the inactive " +
				"warehouse stocks"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				false, _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId(), _serviceContext);

		int quantity = 10;

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), quantity);

		int stockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _commerceChannel.getGroupId(),
			_cpInstance1.getSku());

		Assert.assertEquals(0, stockQuantity);
	}

	@Test
	public void testGetStockQuantitiesForInactiveWarehouseUsingCPChannel()
		throws Exception {

		frutillaRule.scenario(
			"It shall not be possible to retrieve stocks from an inactive " +
				"warehouse using a commerce channel to the warehouse item"
		).given(
			"One inactive warehouse is created"
		).when(
			"A product is added to the warehouse"
		).then(
			"The retrieved stock quantity shall not contain the inactive " +
				"warehouse stocks"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				false, _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CPDefinition.class.getName(), _cpInstance1.getCPDefinitionId(),
			_commerceChannel.getCommerceChannelId(), _serviceContext);

		int quantity = 10;

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), quantity);

		int stockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _commerceChannel.getGroupId(),
			_cpInstance1.getSku());

		Assert.assertEquals(0, stockQuantity);
	}

	@Test
	public void testGetStockQuantity() throws Exception {
		frutillaRule.scenario(
			"The stock quantity of an item in an active warehouse is " +
				"correctly retrieved"
		).given(
			"A warehouse item added to an active warehouse with a channel"
		).when(
			"I get the stock quantity"
		).then(
			"The stock quantity is correctly retrieved"
		);

		_commerceInventoryWarehouseItem1 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_commerceChannel.getCommerceChannelId(), _cpInstance1.getSku(),
				10, _serviceContext);

		int stockQuantity = _commerceInventoryEngine.getStockQuantity(
			_group.getCompanyId(), _commerceChannel.getGroupId(),
			_cpInstance1.getSku());

		Assert.assertEquals(
			_commerceInventoryWarehouseItem1.getQuantity(), stockQuantity);
	}

	@Test
	public void testGetWarehouse() throws Exception {
		frutillaRule.scenario(
			"It should be possible to filter warehouses based on their status"
		).given(
			"1 active and 1 inactive warehouse"
		).when(
			"I search by company, groupId and status"
		).then(
			"The correct warehouses are retrieved"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouseActive =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				true, _serviceContext);

		CommerceInventoryWarehouse commerceInventoryWarehouseInactive =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				false, _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouseActive.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId(), _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouseInactive.
				getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId(), _serviceContext);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_user.getGroupId());

		List<CommerceInventoryWarehouse> activeWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					serviceContext.getCompanyId(),
					_commerceChannel.getGroupId(), true);
		List<CommerceInventoryWarehouse> inactiveWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					serviceContext.getCompanyId(),
					_commerceChannel.getGroupId(), false);

		Assert.assertEquals(
			activeWarehouses.toString(), 1, activeWarehouses.size());
		Assert.assertEquals(
			inactiveWarehouses.toString(), 1, inactiveWarehouses.size());

		Assert.assertEquals(
			commerceInventoryWarehouseActive, activeWarehouses.get(0));
		Assert.assertEquals(
			commerceInventoryWarehouseInactive, inactiveWarehouses.get(0));
	}

	@Test
	public void testGetWarehouseBySku() throws Exception {
		frutillaRule.scenario(
			"It should be possible to search warehouses by SKUs only for " +
				"active warehouses"
		).given(
			"1 active and 1 inactive warehouse"
		).when(
			"I search by groupId and SKUs"
		).then(
			"The correct warehouses are retrieved"
		);

		CommerceInventoryWarehouse commerceInventoryWarehouse1 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				true, _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId(), _serviceContext);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), 1);

		List<CommerceInventoryWarehouse> expectedWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), _cpInstance1.getSku());

		Assert.assertEquals(
			expectedWarehouses.toString(), 1, expectedWarehouses.size());

		CommerceInventoryWarehouse retrievedWarehouse = expectedWarehouses.get(
			0);

		Assert.assertEquals(commerceInventoryWarehouse1, retrievedWarehouse);

		CommerceInventoryWarehouse commerceInventoryWarehouse2 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				false, _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse2.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId(), _serviceContext);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse2.getCommerceInventoryWarehouseId(),
				_cpInstance1.getSku(), 1);

		expectedWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), _cpInstance1.getSku());

		Assert.assertEquals(
			expectedWarehouses.toString(), 1, expectedWarehouses.size());

		retrievedWarehouse = expectedWarehouses.get(0);

		Assert.assertEquals(commerceInventoryWarehouse1, retrievedWarehouse);

		CommerceInventoryWarehouse commerceInventoryWarehouse3 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				true, _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse3.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId(), _serviceContext);

		_commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				_user.getUserId(),
				commerceInventoryWarehouse3.getCommerceInventoryWarehouseId(),
				_cpInstance2.getSku(), 1);

		expectedWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), _cpInstance1.getSku());

		Assert.assertEquals(
			expectedWarehouses.toString(), 1, expectedWarehouses.size());

		expectedWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), _cpInstance2.getSku());

		Assert.assertEquals(
			expectedWarehouses.toString(), 1, expectedWarehouses.size());

		retrievedWarehouse = expectedWarehouses.get(0);

		Assert.assertEquals(commerceInventoryWarehouse3, retrievedWarehouse);
	}

	@Test
	public void testGetWarehouseItemByDate() throws Exception {
		frutillaRule.scenario(
			"It should be possible to filter warehousesItes based on their " +
				"modified date"
		).given(
			"1 warehouse item"
		).when(
			"I search by company and date range"
		).then(
			"The warehouse item is retrieved"
		);

		Date startDate = new Date();

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				_commerceChannel.getCommerceChannelId(), _cpInstance1.getSku(),
				10, _serviceContext);

		Date endDate = new Date();

		int countWarehouseItems =
			_commerceInventoryWarehouseItemLocalService.
				getCommerceInventoryWarehouseItemsCountByModifiedDate(
					_group.getCompanyId(), startDate, endDate);

		Assert.assertEquals(1, countWarehouseItems);

		List<CommerceInventoryWarehouseItem> warehouseItems =
			_commerceInventoryWarehouseItemLocalService.
				getCommerceInventoryWarehouseItemsByModifiedDate(
					_group.getCompanyId(), startDate, endDate,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			warehouseItems.get(0), commerceInventoryWarehouseItem);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static User _user;

	@Inject
	private CommerceInventoryBookedQuantityLocalService
		_commerceBookedQuantityLocalService;

	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private CommerceInventoryEngine _commerceInventoryEngine;

	private CommerceInventoryWarehouseItem _commerceInventoryWarehouseItem1;
	private CommerceInventoryWarehouseItem _commerceInventoryWarehouseItem2;

	@Inject
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@Inject
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	private CPInstance _cpInstance1;
	private CPInstance _cpInstance2;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

}