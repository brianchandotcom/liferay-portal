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
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

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
public class CommercePaymentSubscriptionsTest {

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_user.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), serviceContext);

		CommerceAccountTestUtil.addCommerceAccountGroupAndAccountRel(
			_user.getCompanyId(), RandomTestUtil.randomString(),
			CommerceAccountConstants.ACCOUNT_GROUP_TYPE_STATIC,
			_commerceAccount.getCommerceAccountId(), serviceContext);

		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());
	}

	@After
	public void tearDown() {
		_commerceInventoryWarehouseItemLocalService.
			deleteCommerceInventoryWarehouseItemsByCompanyId(
				_user.getCompanyId());
	}

	@Test
	public void testPaymentSubscription() throws Exception {
		_commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			_commerceOrder, _user.getUserId(), true, false);

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

		_commerceSubscriptionEngine.suspendRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_SUSPENDED,
			commerceSubscriptionEntry.getSubscriptionStatus());

		_commerceSubscriptionEngine.activateRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE,
			commerceSubscriptionEntry.getSubscriptionStatus());

		_commerceSubscriptionEngine.cancelRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		Assert.assertEquals(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_CANCELLED,
			commerceSubscriptionEntry.getSubscriptionStatus());
	}

	private static User _user;

	private CommerceAccount _commerceAccount;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	@Inject
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	private Group _group;

}