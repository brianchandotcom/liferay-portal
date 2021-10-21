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

package com.liferay.commerce.inventory.service.mvcc.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceInventoryMVCCTest {

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
	}

	@Test(expected = MVCCException.class)
	public void testReplenishmentItemMVCC() throws Exception {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				RandomTestUtil.randomString(), true, _serviceContext);

		CPInstance cpInstance =
			CommerceInventoryTestUtil.addRandomCPInstanceSku(
				_group.getGroupId());

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_commerceInventoryReplenishmentItemLocalService.
				addCommerceInventoryReplenishmentItem(
					_user.getUserId(),
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					cpInstance.getSku(), new Date(), 10);

		_commerceInventoryReplenishmentItemLocalService.
			updateCommerceInventoryReplenishmentItem(
				commerceInventoryReplenishmentItem.
					getCommerceInventoryReplenishmentItemId(),
				commerceInventoryReplenishmentItem.getAvailabilityDate(), 15,
				commerceInventoryReplenishmentItem.getMvccVersion());

		_commerceInventoryReplenishmentItemLocalService.
			updateCommerceInventoryReplenishmentItem(
				commerceInventoryReplenishmentItem.
					getCommerceInventoryReplenishmentItemId(),
				commerceInventoryReplenishmentItem.getAvailabilityDate(), 20,
				commerceInventoryReplenishmentItem.getMvccVersion());
	}

	@Test(expected = MVCCException.class)
	public void testWarehouseItemMVCC() throws Exception {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				RandomTestUtil.randomString(), true, _serviceContext);

		CPInstance cpInstance =
			CommerceInventoryTestUtil.addRandomCPInstanceSku(
				_group.getGroupId());

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				addCommerceInventoryWarehouseItem(
					_user.getUserId(),
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					cpInstance.getSku(), 1);

		_commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItem.getUserId(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				1, commerceInventoryWarehouse.getMvccVersion());

		_commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItem.getUserId(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				1, commerceInventoryWarehouse.getMvccVersion());
	}

	@Test(expected = MVCCException.class)
	public void testWarehouseMVCC() throws Exception {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				RandomTestUtil.randomString(), true, _serviceContext);

		_commerceInventoryWarehouseLocalService.
			updateCommerceInventoryWarehouse(
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				commerceInventoryWarehouse.getName(), "New Description OK",
				commerceInventoryWarehouse.isActive(),
				commerceInventoryWarehouse.getStreet1(),
				commerceInventoryWarehouse.getStreet2(),
				commerceInventoryWarehouse.getStreet3(),
				commerceInventoryWarehouse.getCity(),
				commerceInventoryWarehouse.getZip(),
				commerceInventoryWarehouse.getCommerceRegionCode(),
				commerceInventoryWarehouse.getCommerceRegionCode(),
				commerceInventoryWarehouse.getLatitude(),
				commerceInventoryWarehouse.getLongitude(),
				commerceInventoryWarehouse.getMvccVersion(), _serviceContext);

		_commerceInventoryWarehouseLocalService.
			updateCommerceInventoryWarehouse(
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				commerceInventoryWarehouse.getName(), "New Description KO",
				commerceInventoryWarehouse.isActive(),
				commerceInventoryWarehouse.getStreet1(),
				commerceInventoryWarehouse.getStreet2(),
				commerceInventoryWarehouse.getStreet3(),
				commerceInventoryWarehouse.getCity(),
				commerceInventoryWarehouse.getZip(),
				commerceInventoryWarehouse.getCommerceRegionCode(),
				commerceInventoryWarehouse.getCommerceRegionCode(),
				commerceInventoryWarehouse.getLatitude(),
				commerceInventoryWarehouse.getLongitude(),
				commerceInventoryWarehouse.getMvccVersion(), _serviceContext);
	}

	private static User _user;

	@Inject
	private CommerceInventoryReplenishmentItemLocalService
		_commerceInventoryReplenishmentItemLocalService;

	@Inject
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@Inject
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}