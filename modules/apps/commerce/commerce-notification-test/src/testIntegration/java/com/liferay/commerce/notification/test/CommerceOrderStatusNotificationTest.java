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

package com.liferay.commerce.notification.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalServiceUtil;
import com.liferay.commerce.notification.test.util.CommerceNotificationTestUtil;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceOrderStatusNotificationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), _serviceContext);

		CommerceAccountTestUtil.addCommerceAccountGroupAndAccountRel(
			_user.getCompanyId(), RandomTestUtil.randomString(),
			CommerceAccountConstants.ACCOUNT_GROUP_TYPE_STATIC,
			_commerceAccount.getCommerceAccountId(), _serviceContext);

		_addCommerceNotificationTemplates();
	}

	@After
	public void tearDown() throws PortalException {
		for (CommerceNotificationTemplate commerceNotificationTemplate :
				_commerceNotificationTemplateList) {

			_commerceNotificationTemplateLocalService.
				deleteCommerceNotificationTemplate(
					commerceNotificationTemplate);
		}
	}

	@Test
	public void testFreeOrderPlacedNotification() throws Exception {
		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrder.setTotal(BigDecimal.ZERO);

		_commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			_commerceOrder);

		Assert.assertEquals(BigDecimal.ZERO, _commerceOrder.getTotal());

		_commercePaymentEngine.completePayment(
			_commerceOrder.getCommerceOrderId(), null,
			new MockHttpServletRequest());

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceOrderConstants.PAYMENT_STATUS_PAID,
			_commerceOrder.getPaymentStatus());
		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_PENDING,
			_commerceOrder.getOrderStatus());

		int commerceNotificationQueueEntriesCount =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntriesCount(
					_commerceChannel.getGroupId());

		Assert.assertEquals(1, commerceNotificationQueueEntriesCount);

		_checkCommerceNotificationTemplate(
			CommerceOrderConstants.ORDER_NOTIFICATION_PLACED);
	}

	@Test
	public void testOrderStatusNotifications() throws Exception {
		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			_commerceOrder, _user.getUserId(), false);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		int commerceNotificationQueueEntriesCount =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntriesCount(
					_commerceChannel.getGroupId());

		Assert.assertEquals(1, commerceNotificationQueueEntriesCount);

		_checkCommerceNotificationTemplate(
			CommerceOrderConstants.ORDER_NOTIFICATION_PLACED);

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			_user.getUserId());

		commerceNotificationQueueEntriesCount =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntriesCount(
					_commerceChannel.getGroupId());

		Assert.assertEquals(2, commerceNotificationQueueEntriesCount);

		_checkCommerceNotificationTemplate(
			CommerceOrderConstants.ORDER_NOTIFICATION_PROCESSING);

		CommerceShipment commerceShipment =
			_commerceShipmentLocalService.addCommerceShipment(
				_commerceOrder.getCommerceOrderId(), _serviceContext);

		for (CommerceOrderItem commerceOrderItem :
				_commerceOrder.getCommerceOrderItems()) {

			List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
				_commerceInventoryWarehouseLocalService.
					getCommerceInventoryWarehouses(
						_commerceChannel.getGroupId(),
						commerceOrderItem.getSku());

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				commerceInventoryWarehouses.get(0);

			_commerceShipmentItemLocalService.addCommerceShipmentItem(
				null, commerceShipment.getCommerceShipmentId(),
				commerceOrderItem.getCommerceOrderItemId(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				commerceOrderItem.getQuantity(), _serviceContext);
		}

		_commerceShipmentLocalService.updateStatus(
			commerceShipment.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		commerceNotificationQueueEntriesCount =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntriesCount(
					_commerceChannel.getGroupId());

		Assert.assertEquals(3, commerceNotificationQueueEntriesCount);

		_checkCommerceNotificationTemplate(
			CommerceOrderConstants.ORDER_NOTIFICATION_SHIPPED);

		_commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_COMPLETED,
			_user.getUserId());

		commerceNotificationQueueEntriesCount =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntriesCount(
					_commerceChannel.getGroupId());

		Assert.assertEquals(4, commerceNotificationQueueEntriesCount);

		_checkCommerceNotificationTemplate(
			CommerceOrderConstants.ORDER_NOTIFICATION_COMPLETED);
	}

	private void _addCommerceNotificationTemplates() throws Exception {
		_commerceNotificationTemplateList = new ArrayList<>(4);

		ServiceContext serviceContext = _serviceContext;

		serviceContext.setScopeGroupId(_commerceChannel.getGroupId());

		for (String commerceOrderNotification : _COMMERCE_ORDER_NOTIFICATIONS) {
			CommerceNotificationTemplate commerceNotificationTemplate =
				CommerceNotificationTestUtil.addNotificationTemplate(
					"[%ORDER_CREATOR%]", commerceOrderNotification,
					serviceContext);

			_commerceNotificationTemplateList.add(commerceNotificationTemplate);
		}
	}

	private void _checkCommerceNotificationTemplate(
			String commerceNotificationTemplateType)
		throws Exception {

		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntries(
					_commerceChannel.getGroupId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		List<String> commerceNotificationTemplateTypes = new ArrayList<>();

		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				commerceNotificationQueueEntries) {

			CommerceNotificationTemplate commerceNotificationTemplate =
				CommerceNotificationTemplateLocalServiceUtil.
					getCommerceNotificationTemplate(
						commerceNotificationQueueEntry.
							getCommerceNotificationTemplateId());

			commerceNotificationTemplateTypes.add(
				commerceNotificationTemplate.getType());
		}

		Assert.assertTrue(
			commerceNotificationTemplateTypes.contains(
				commerceNotificationTemplateType));
	}

	private static final String[] _COMMERCE_ORDER_NOTIFICATIONS = {
		CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
		CommerceOrderConstants.ORDER_NOTIFICATION_PROCESSING,
		CommerceOrderConstants.ORDER_NOTIFICATION_SHIPPED,
		CommerceOrderConstants.ORDER_NOTIFICATION_COMPLETED
	};

	private static User _user;

	private CommerceAccount _commerceAccount;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Inject
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

	private List<CommerceNotificationTemplate>
		_commerceNotificationTemplateList;

	@Inject
	private CommerceNotificationTemplateLocalService
		_commerceNotificationTemplateLocalService;

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommercePaymentEngine _commercePaymentEngine;

	@Inject
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	@Inject
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

}