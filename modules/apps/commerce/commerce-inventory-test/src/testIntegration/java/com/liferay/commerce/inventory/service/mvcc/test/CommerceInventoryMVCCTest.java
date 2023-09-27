/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.petra.string.StringPool;
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

import java.math.BigDecimal;

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
				RandomTestUtil.randomLocaleStringMap(), true, _serviceContext);

		CPInstance cpInstance =
			CommerceInventoryTestUtil.addRandomCPInstanceSku(
				_group.getGroupId());

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_commerceInventoryReplenishmentItemLocalService.
				addCommerceInventoryReplenishmentItem(
					null, _user.getUserId(),
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					new Date(), BigDecimal.TEN, cpInstance.getSku(),
					StringPool.BLANK);

		_commerceInventoryReplenishmentItemLocalService.
			updateCommerceInventoryReplenishmentItem(
				null,
				commerceInventoryReplenishmentItem.
					getCommerceInventoryReplenishmentItemId(),
				commerceInventoryReplenishmentItem.getAvailabilityDate(),
				new BigDecimal(15),
				commerceInventoryReplenishmentItem.getMvccVersion());

		_commerceInventoryReplenishmentItemLocalService.
			updateCommerceInventoryReplenishmentItem(
				null,
				commerceInventoryReplenishmentItem.
					getCommerceInventoryReplenishmentItemId(),
				commerceInventoryReplenishmentItem.getAvailabilityDate(),
				new BigDecimal(20),
				commerceInventoryReplenishmentItem.getMvccVersion());
	}

	@Test(expected = MVCCException.class)
	public void testWarehouseItemMVCC() throws Exception {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				RandomTestUtil.randomLocaleStringMap(), true, _serviceContext);

		CPInstance cpInstance =
			CommerceInventoryTestUtil.addRandomCPInstanceSku(
				_group.getGroupId());

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				addCommerceInventoryWarehouseItem(
					StringPool.BLANK, _user.getUserId(),
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					BigDecimal.ONE, cpInstance.getSku(), StringPool.BLANK);

		_commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItem.getUserId(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				commerceInventoryWarehouse.getMvccVersion(), BigDecimal.ONE,
				StringPool.BLANK);

		_commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItem.getUserId(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				commerceInventoryWarehouse.getMvccVersion(), BigDecimal.ONE,
				StringPool.BLANK);
	}

	@Test(expected = MVCCException.class)
	public void testWarehouseMVCC() throws Exception {
		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				RandomTestUtil.randomLocaleStringMap(), true, _serviceContext);

		_commerceInventoryWarehouseLocalService.
			updateCommerceInventoryWarehouse(
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				commerceInventoryWarehouse.getNameMap(),
				RandomTestUtil.randomLocaleStringMap(),
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
				commerceInventoryWarehouse.getNameMap(),
				RandomTestUtil.randomLocaleStringMap(),
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