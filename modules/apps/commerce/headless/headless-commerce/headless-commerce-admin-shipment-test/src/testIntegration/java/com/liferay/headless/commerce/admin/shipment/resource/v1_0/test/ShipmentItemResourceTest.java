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

package com.liferay.headless.commerce.admin.shipment.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceShipmentItemLocalServiceUtil;
import com.liferay.commerce.service.CommerceShipmentLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.shipment.client.dto.v1_0.ShipmentItem;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class ShipmentItemResourceTest extends BaseShipmentItemResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testCompany.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		BigDecimal value = BigDecimal.valueOf(RandomTestUtil.nextDouble());

		_commerceOrder = CommerceTestUtil.createCommerceOrderForShipping(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId(), value);

		BigDecimal price = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_commerceOrder.getGroupId(), price);

		CPInstanceLocalServiceUtil.updateCPInstance(_cpInstance);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse,
			_cpInstance.getSku(), 100);

		_commerceShipment =
			CommerceShipmentLocalServiceUtil.addCommerceShipment(
				_commerceOrder.getCommerceOrderId(), _serviceContext);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"quantity", "warehouseId"};
	}

	@Override
	protected ShipmentItem randomShipmentItem() throws Exception {
		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				_commerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), 1);

		return new ShipmentItem() {
			{
				createDate = RandomTestUtil.nextDate();
				modifiedDate = RandomTestUtil.nextDate();
				orderItemId = commerceOrderItem.getCommerceOrderItemId();
				quantity = commerceOrderItem.getQuantity();
				userName = commerceOrderItem.getUserName();
				warehouseId =
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId();
			}
		};
	}

	@Override
	protected ShipmentItem testDeleteShipmentItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem testGetShipmentItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem testGetShipmentItemsPage_addShipmentItem(
			Long shipmentId, ShipmentItem shipmentItem)
		throws Exception {

		return _addShipmentItem(shipmentId, shipmentItem.getOrderItemId());
	}

	@Override
	protected Long testGetShipmentItemsPage_getShipmentId() throws Exception {
		return _commerceShipment.getCommerceShipmentId();
	}

	@Override
	protected ShipmentItem testGraphQLShipmentItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem testPatchShipmentItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem testPostShipmentItem_addShipmentItem(
			ShipmentItem shipmentItem)
		throws Exception {

		return _addShipmentItem(shipmentItem);
	}

	private ShipmentItem _addShipmentItem() throws Exception {
		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				_commerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), 1);

		return _addShipmentItem(
			_commerceShipment.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId());
	}

	private ShipmentItem _addShipmentItem(
			long shipmentId, long commerceOrderItemId)
		throws Exception {

		_commerceShipmentItem =
			CommerceShipmentItemLocalServiceUtil.addCommerceShipmentItem(
				shipmentId, commerceOrderItemId,
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				1, _serviceContext);

		_commerceShipmentItems.add(_commerceShipmentItem);

		return _toShipmentItem(_commerceShipmentItem);
	}

	private ShipmentItem _addShipmentItem(ShipmentItem shipmentItem)
		throws Exception {

		return _addShipmentItem(
			_commerceShipment.getCommerceShipmentId(),
			shipmentItem.getOrderItemId());
	}

	private ShipmentItem _toShipmentItem(
		CommerceShipmentItem commerceShipmentItem) {

		return new ShipmentItem() {
			{
				createDate = commerceShipmentItem.getCreateDate();
				id = commerceShipmentItem.getCommerceShipmentItemId();
				modifiedDate = commerceShipmentItem.getModifiedDate();
				orderItemId = commerceShipmentItem.getCommerceOrderItemId();
				quantity = commerceShipmentItem.getQuantity();
				shipmentId = commerceShipmentItem.getCommerceShipmentId();
				userName = commerceShipmentItem.getUserName();
				warehouseId =
					commerceShipmentItem.getCommerceInventoryWarehouseId();
			}
		};
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private CommerceShipment _commerceShipment;

	@DeleteAfterTestRun
	private CommerceShipmentItem _commerceShipmentItem;

	@DeleteAfterTestRun
	private final List<CommerceShipmentItem> _commerceShipmentItems =
		new ArrayList<>();

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}