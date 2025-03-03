/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.WarehouseItem;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class WarehouseItemResourceTest
	extends BaseWarehouseItemResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId()));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		for (Long warehouseItemId : _warehouseItemIds) {
			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
				_commerceInventoryWarehouseItemLocalService.
					fetchCommerceInventoryWarehouseItem(warehouseItemId);

			if (commerceInventoryWarehouseItem == null) {
				continue;
			}

			_commerceInventoryWarehouseItemLocalService.
				deleteCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItem);
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetWarehouseItemsUpdatedPageWithPagination()
		throws Exception {

		super.testGetWarehouseItemsUpdatedPageWithPagination();
	}

	@Override
	@Test
	public void testPatchWarehouseItem() throws Exception {
		WarehouseItem warehouseItem = _addWarehouseItem(randomWarehouseItem());

		warehouseItem.setQuantity(BigDecimal.TEN);

		String newUnitOfMeasureKey = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		warehouseItem.setUnitOfMeasureKey(newUnitOfMeasureKey);

		warehouseItemResource.patchWarehouseItem(
			warehouseItem.getId(), warehouseItem);

		WarehouseItem getWarehouseItem = warehouseItemResource.getWarehouseItem(
			warehouseItem.getId());

		Assert.assertEquals(BigDecimal.TEN, getWarehouseItem.getQuantity());
		Assert.assertEquals(
			newUnitOfMeasureKey, getWarehouseItem.getUnitOfMeasureKey());
	}

	@Override
	@Test
	public void testPatchWarehouseItemByExternalReferenceCode()
		throws Exception {

		WarehouseItem warehouseItem = _addWarehouseItem(randomWarehouseItem());

		warehouseItem.setQuantity(BigDecimal.TEN);

		String newUnitOfMeasureKey = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		warehouseItem.setUnitOfMeasureKey(newUnitOfMeasureKey);

		warehouseItemResource.patchWarehouseItemByExternalReferenceCode(
			warehouseItem.getExternalReferenceCode(), warehouseItem);

		WarehouseItem getWarehouseItem =
			warehouseItemResource.getWarehouseItemByExternalReferenceCode(
				warehouseItem.getExternalReferenceCode());

		Assert.assertEquals(BigDecimal.TEN, getWarehouseItem.getQuantity());
		Assert.assertEquals(
			newUnitOfMeasureKey, getWarehouseItem.getUnitOfMeasureKey());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"quantity", "unitOfMeasureKey"};
	}

	@Override
	protected WarehouseItem randomWarehouseItem() throws Exception {
		return new WarehouseItem() {
			{
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				modifiedDate = RandomTestUtil.nextDate();
				quantity = BigDecimal.ONE;
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				unitOfMeasureKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				warehouseExternalReferenceCode =
					_commerceInventoryWarehouse.getExternalReferenceCode();
				warehouseId =
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId();
			}
		};
	}

	@Override
	protected WarehouseItem testDeleteWarehouseItem_addWarehouseItem()
		throws Exception {

		return _addWarehouseItem(randomWarehouseItem());
	}

	@Override
	protected WarehouseItem
			testDeleteWarehouseItemByExternalReferenceCode_addWarehouseItem()
		throws Exception {

		return _addWarehouseItem(randomWarehouseItem());
	}

	@Override
	protected WarehouseItem
			testGetWarehouseByExternalReferenceCodeWarehouseItemsPage_addWarehouseItem(
				String externalReferenceCode, WarehouseItem warehouseItem)
		throws Exception {

		WarehouseItem postWarehouseItem =
			warehouseItemResource.
				postWarehouseByExternalReferenceCodeWarehouseItem(
					externalReferenceCode, warehouseItem);

		_warehouseItemIds.add(postWarehouseItem.getId());

		return postWarehouseItem;
	}

	@Override
	protected String
			testGetWarehouseByExternalReferenceCodeWarehouseItemsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceInventoryWarehouse.getExternalReferenceCode();
	}

	@Override
	protected WarehouseItem
			testGetWarehouseIdWarehouseItemsPage_addWarehouseItem(
				Long id, WarehouseItem warehouseItem)
		throws Exception {

		WarehouseItem postWarehouseItem =
			warehouseItemResource.postWarehouseIdWarehouseItem(
				id, warehouseItem);

		_warehouseItemIds.add(postWarehouseItem.getId());

		return postWarehouseItem;
	}

	@Override
	protected Long testGetWarehouseIdWarehouseItemsPage_getId()
		throws Exception {

		return _commerceInventoryWarehouse.getCommerceInventoryWarehouseId();
	}

	@Override
	protected WarehouseItem testGetWarehouseItem_addWarehouseItem()
		throws Exception {

		return _addWarehouseItem(randomWarehouseItem());
	}

	@Override
	protected WarehouseItem
			testGetWarehouseItemByExternalReferenceCode_addWarehouseItem()
		throws Exception {

		return _addWarehouseItem(randomWarehouseItem());
	}

	@Override
	protected WarehouseItem testGetWarehouseItemsUpdatedPage_addWarehouseItem(
			WarehouseItem warehouseItem)
		throws Exception {

		return _addWarehouseItem(warehouseItem);
	}

	@Override
	protected WarehouseItem testGraphQLWarehouseItem_addWarehouseItem()
		throws Exception {

		return _addWarehouseItem(randomWarehouseItem());
	}

	@Override
	protected WarehouseItem
			testPostWarehouseByExternalReferenceCodeWarehouseItem_addWarehouseItem(
				WarehouseItem warehouseItem)
		throws Exception {

		return _addWarehouseItem(warehouseItem);
	}

	@Override
	protected WarehouseItem testPostWarehouseIdWarehouseItem_addWarehouseItem(
			WarehouseItem warehouseItem)
		throws Exception {

		return _addWarehouseItem(warehouseItem);
	}

	@Override
	protected WarehouseItem
			testPostWarehouseItemByExternalReferenceCode_addWarehouseItem(
				WarehouseItem warehouseItem)
		throws Exception {

		return _addWarehouseItem(warehouseItem);
	}

	@Override
	protected WarehouseItem
			testPutWarehouseItemByExternalReferenceCode_addWarehouseItem()
		throws Exception {

		return _addWarehouseItem(randomWarehouseItem());
	}

	private WarehouseItem _addWarehouseItem(WarehouseItem warehouseItem)
		throws Exception {

		WarehouseItem postWarehouseItem =
			warehouseItemResource.postWarehouseIdWarehouseItem(
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				warehouseItem);

		_warehouseItemIds.add(postWarehouseItem.getId());

		return postWarehouseItem;
	}

	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@Inject
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	private final List<Long> _warehouseItemIds = new ArrayList<>();

}