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

package com.liferay.commerce.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryHelper;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;
import java.util.List;

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
public class CommerceDeliverySubscriptionsTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());
	}

	@Test
	public void testDeliveryAndPaymentSubscription() throws Exception {
		_commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			_commerceOrder, _user.getUserId(), true, true);

		_commerceOrder.setPaymentStatus(
			CommerceOrderConstants.PAYMENT_STATUS_PAID);

		CommerceOrderLocalServiceUtil.updateCommerceOrder(_commerceOrder);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		List<CommerceSubscriptionEntry> commerceSubscriptionEntries =
			_commerceSubscriptionEntryLocalService.
				getCommerceSubscriptionEntries(
					_user.getCompanyId(), _commerceChannel.getGroupId(),
					_user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

		Assert.assertEquals(
			commerceSubscriptionEntries.toString(), 1,
			commerceSubscriptionEntries.size());

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntries.get(0);

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE,
			commerceSubscriptionEntry.getSubscriptionStatus());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE,
			commerceSubscriptionEntry.getDeliverySubscriptionStatus());

		// Suspend

		_commerceSubscriptionEngine.suspendRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		_commerceSubscriptionEngine.suspendRecurringDelivery(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_SUSPENDED,
			commerceSubscriptionEntry.getSubscriptionStatus());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_SUSPENDED,
			commerceSubscriptionEntry.getDeliverySubscriptionStatus());

		// Re-Activate

		_commerceSubscriptionEngine.activateRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		_commerceSubscriptionEngine.activateRecurringDelivery(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE,
			commerceSubscriptionEntry.getSubscriptionStatus());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE,
			commerceSubscriptionEntry.getDeliverySubscriptionStatus());

		// Cancel

		_commerceSubscriptionEngine.cancelRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		_commerceSubscriptionEngine.cancelRecurringDelivery(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_CANCELLED,
			commerceSubscriptionEntry.getSubscriptionStatus());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_CANCELLED,
			commerceSubscriptionEntry.getDeliverySubscriptionStatus());
	}

	@Test
	public void testDeliverySubscriptionRenew() throws Exception {
		_commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			_commerceOrder, _user.getUserId(), true, true);

		_commerceOrder.setPaymentStatus(
			CommerceOrderConstants.PAYMENT_STATUS_PAID);

		CommerceOrderLocalServiceUtil.updateCommerceOrder(_commerceOrder);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		List<CommerceSubscriptionEntry> commerceSubscriptionEntries =
			_commerceSubscriptionEntryLocalService.
				getCommerceSubscriptionEntries(
					_user.getCompanyId(), _commerceChannel.getGroupId(),
					_user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

		Assert.assertEquals(
			commerceSubscriptionEntries.toString(), 1,
			commerceSubscriptionEntries.size());

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntries.get(0);

		boolean activateRecurringDelivery =
			_commerceSubscriptionEngine.activateRecurringDelivery(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		Assert.assertTrue(activateRecurringDelivery);

		// Set subscription entry to be renewable

		Calendar now = Calendar.getInstance();

		now.add(Calendar.MINUTE, -2);

		commerceSubscriptionEntry.setDeliveryNextIterationDate(now.getTime());

		_commerceSubscriptionEntryHelper.checkDeliverySubscriptionStatus(
			commerceSubscriptionEntry);

		CommerceSubscriptionEntry renewedCommerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		Assert.assertEquals(
			commerceSubscriptionEntry.getDeliveryCurrentCycle() + 1,
			renewedCommerceSubscriptionEntry.getDeliveryCurrentCycle());

		long[] groupIds = {_commerceChannel.getGroupId()};

		List<CommerceShipment> commerceShipments =
			_commerceShipmentLocalService.getCommerceShipments(
				groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			commerceShipments.toString(), 1, commerceShipments.size());
	}

	private static User _user;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	@Inject
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	@Inject
	private CommerceSubscriptionEntryHelper _commerceSubscriptionEntryHelper;

	@Inject
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	private Group _group;

}