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

package com.liferay.commerce.shipment.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.exception.CommerceShipmentStatusException;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentItemLocalServiceUtil;
import com.liferay.commerce.service.CommerceShipmentLocalServiceUtil;
import com.liferay.commerce.shipment.test.util.CommerceShipmentTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alec Sloan
 */
@RunWith(Arquillian.class)
public class CommerceShipmentItemTest {

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, _group.getGroupId(), "Test Channel",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), serviceContext);

		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceShipment = CommerceShipmentTestUtil.createEmptyOrderShipment(
			_group.getGroupId(), _commerceOrder.getCommerceOrderId());

		_commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceOrder.getCommerceAccount(), _commerceOrder);

		_commerceShipmentItem =
			CommerceShipmentTestUtil.addCommerceShipmentItem(
				_commerceContext,
				CPTestUtil.addCPInstanceWithRandomSku(_group.getGroupId()),
				_group.getGroupId(), _user.getUserId(),
				_commerceOrder.getCommerceOrderId(),
				_commerceShipment.getCommerceShipmentId(), 2, 1);
	}

	@Test
	public void testAddCommerceShipmentItem() throws Exception {
		frutillaRule.scenario(
			"Create a Shipment Item for an Order"
		).given(
			"A Group"
		).and(
			"An Order Item"
		).and(
			"A CPInstance"
		).when(
			"A Shipment Item is added to a shipment"
		).then(
			"That shipment should contain the shipment Item"
		);

		List<CommerceShipmentItem> commerceShipmentItems =
			_commerceShipmentItemLocalService.getCommerceShipmentItems(
				_commerceShipment.getCommerceShipmentId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			commerceShipmentItems.toString(), 1, commerceShipmentItems.size());

		CommerceShipmentItem actualCommerceShipmentItem =
			commerceShipmentItems.get(0);

		Assert.assertEquals(_commerceShipmentItem, actualCommerceShipmentItem);

		_resetCommerceShipment();
	}

	@Test(expected = CommerceShipmentStatusException.class)
	public void testAddCommerceShipmentItemOnShippedShipment()
		throws Exception {

		frutillaRule.scenario(
			"Try to add a new Shipment Item after a shipment is marked as " +
				"shipped"
		).given(
			"A Group"
		).and(
			"A ShipmentItem"
		).and(
			"A CPInstance"
		).when(
			"A Shipment is marked as shipped"
		).then(
			"Shipment Items should not be able to be added"
		);

		_commerceShipment.setStatus(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		_commerceShipment =
			CommerceShipmentLocalServiceUtil.updateCommerceShipment(
				_commerceShipment);

		CommerceShipmentTestUtil.addCommerceShipmentItem(
			_commerceContext,
			CPTestUtil.addCPInstanceWithRandomSku(_group.getGroupId()),
			_group.getGroupId(), _user.getUserId(),
			_commerceOrder.getCommerceOrderId(),
			_commerceShipment.getCommerceShipmentId(), 1, 1);

		Assert.assertEquals(
			_commerceShipment.getStatus(),
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		_resetCommerceShipment();
	}

	@Test
	public void testDeleteShipmentItemAndDoNotRestoreStockQuantity()
		throws Exception {

		frutillaRule.scenario(
			"Delete a Shipment Item after a shipment is marked as shipped " +
				"and do not restock the sku"
		).given(
			"A Group"
		).and(
			"A ShipmentItem"
		).and(
			"A CPInstance"
		).and(
			"A Shipment is marked as shipped"
		).when(
			"The Shipment Item is deleted and we do not restock the item"
		).then(
			"Our inventory should contain not that item quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		CommerceShipmentItem commerceShipmentItem =
			CommerceShipmentTestUtil.addCommerceShipmentItem(
				_commerceContext, cpInstance, _group.getGroupId(),
				_user.getUserId(), _commerceOrder.getCommerceOrderId(),
				_commerceShipment.getCommerceShipmentId(), 1, 1);

		_commerceShipment.setStatus(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		_commerceShipment =
			CommerceShipmentLocalServiceUtil.updateCommerceShipment(
				_commerceShipment);

		CommerceShipmentItemLocalServiceUtil.deleteCommerceShipmentItem(
			commerceShipmentItem, false);

		int actualCPInstanceStockQuantity =
			_commerceInventoryEngine.getStockQuantity(
				_user.getCompanyId(), cpInstance.getSku());

		Assert.assertNotEquals(1, actualCPInstanceStockQuantity);

		_resetCommerceShipment();
	}

	@Test
	public void testDeleteShipmentItemAndRestoreStockQuantity()
		throws Exception {

		frutillaRule.scenario(
			"Delete a Shipment Item after a shipment is marked as shipped " +
				"and restock the sku"
		).given(
			"A Group"
		).and(
			"A ShipmentItem"
		).and(
			"A CPInstance"
		).and(
			"A Shipment is marked as shipped"
		).when(
			"The Shipment Item is deleted and we restock the item"
		).then(
			"Our inventory should contain the original item quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		CommerceShipmentItem commerceShipmentItem =
			CommerceShipmentTestUtil.addCommerceShipmentItem(
				_commerceContext, cpInstance, _commerceChannel.getGroupId(),
				_user.getUserId(), _commerceOrder.getCommerceOrderId(),
				_commerceShipment.getCommerceShipmentId(), 1, 1);

		_commerceShipment.setStatus(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		_commerceShipment =
			CommerceShipmentLocalServiceUtil.updateCommerceShipment(
				_commerceShipment);

		CommerceShipmentItemLocalServiceUtil.deleteCommerceShipmentItem(
			commerceShipmentItem, true);

		int actualCPInstanceStockQuantity =
			_commerceInventoryEngine.getStockQuantity(
				_user.getCompanyId(), _commerceChannel.getGroupId(),
				cpInstance.getSku());

		Assert.assertEquals(1, actualCPInstanceStockQuantity);

		_resetCommerceShipment();
	}

	@Test(expected = CommerceShipmentStatusException.class)
	public void testUpdateCommerceShipmentItemOnShippedShipment()
		throws Exception {

		frutillaRule.scenario(
			"Try to increase a Shipment Item's quantity after a shipment is " +
				"marked as shipped"
		).given(
			"A Group"
		).and(
			"A ShipmentItem"
		).when(
			"A Shipment is marked as shipped"
		).then(
			"Shipment Items should not be able to be added"
		);

		_commerceShipment.setStatus(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		_commerceShipment =
			CommerceShipmentLocalServiceUtil.updateCommerceShipment(
				_commerceShipment);

		CommerceShipmentItem newCommerceShipmentItem =
			CommerceShipmentItemLocalServiceUtil.updateCommerceShipmentItem(
				_commerceShipmentItem.getCommerceShipmentItemId(), 2);

		Assert.assertEquals(
			_commerceShipment.getStatus(),
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);
		Assert.assertEquals(
			_commerceShipmentItem.getQuantity(),
			newCommerceShipmentItem.getQuantity());

		_resetCommerceShipment();
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private void _resetCommerceShipment() throws Exception {
		CommerceShipmentLocalServiceUtil.deleteCommerceShipment(
			_commerceShipment, true);

		CommerceShipmentLocalServiceUtil.addCommerceShipment(_commerceShipment);
	}

	private static User _user;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	private CommerceContext _commerceContext;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceInventoryEngine _commerceInventoryEngine;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private CommerceShipment _commerceShipment;

	@DeleteAfterTestRun
	private CommerceShipmentItem _commerceShipmentItem;

	@Inject
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	private Group _group;

}