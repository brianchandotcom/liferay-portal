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
import com.liferay.commerce.constants.CommerceSubscriptionNotificationConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalServiceUtil;
import com.liferay.commerce.notification.test.util.CommerceNotificationTestUtil;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.product.model.CommerceChannelConstants;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@Ignore
@RunWith(Arquillian.class)
public class CommerceSubscriptionsNotificationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_company.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_company.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceChannelLocalService.addCommerceChannel(
			_group.getGroupId(),
			_group.getName(_serviceContext.getLanguageId()) + " Portal",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null, StringPool.BLANK,
			StringPool.BLANK, _serviceContext);

		User user = UserTestUtil.addUser(
			_user.getCompanyId(), _user.getUserId(), "businessUser",
			_serviceContext.getLocale(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			new long[] {_serviceContext.getScopeGroupId()}, _serviceContext);

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {user.getUserId()}, null, _serviceContext);

		CommerceAccountTestUtil.addCommerceAccountGroupAndAccountRel(
			_company.getCompanyId(), RandomTestUtil.randomString(),
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
	public void testSubscriptionStatusNotification() throws Exception {
		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _group.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrder = CommerceTestUtil.addCheckoutDetailsToUserOrder(
			_commerceOrder, _user.getUserId(), true);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		List<CommerceSubscriptionEntry> commerceSubscriptionEntries =
			_commerceSubscriptionEntryLocalService.
				getCommerceSubscriptionEntries(
					_company.getCompanyId(), _commerceOrder.getUserId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			commerceSubscriptionEntries.toString(), 1,
			commerceSubscriptionEntries.size());

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntries.get(0);

		_commerceSubscriptionEngine.suspendRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		_checkCommerceNotificationTemplate(
			CommerceSubscriptionNotificationConstants.SUBSCRIPTION_SUSPENDED);

		_commerceSubscriptionEngine.activateRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		_checkCommerceNotificationTemplate(
			CommerceSubscriptionNotificationConstants.SUBSCRIPTION_ACTIVATED);

		_commerceSubscriptionEngine.cancelRecurringPayment(
			commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		_checkCommerceNotificationTemplate(
			CommerceSubscriptionNotificationConstants.SUBSCRIPTION_CANCELLED);
	}

	private void _addCommerceNotificationTemplates() throws Exception {
		_commerceNotificationTemplateList = new ArrayList<>(4);

		for (String commerceSubscriptionNotification :
				_COMMERCE_SUBSCRIPTION_NOTIFICATIONS) {

			CommerceNotificationTemplate commerceNotificationTemplate =
				CommerceNotificationTestUtil.addNotificationTemplate(
					"[%ORDER_CREATOR%]", commerceSubscriptionNotification,
					_serviceContext);

			_commerceNotificationTemplateList.add(commerceNotificationTemplate);
		}
	}

	private void _checkCommerceNotificationTemplate(
			String commerceNotificationTemplateType)
		throws Exception {

		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntries(
					_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

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

	private static final String[] _COMMERCE_SUBSCRIPTION_NOTIFICATIONS = {
		CommerceSubscriptionNotificationConstants.SUBSCRIPTION_ACTIVATED,
		CommerceSubscriptionNotificationConstants.SUBSCRIPTION_CANCELLED,
		CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED,
		CommerceSubscriptionNotificationConstants.SUBSCRIPTION_SUSPENDED
	};

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

	private List<CommerceNotificationTemplate>
		_commerceNotificationTemplateList;

	@Inject
	private CommerceNotificationTemplateLocalService
		_commerceNotificationTemplateLocalService;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	@Inject
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@DeleteAfterTestRun
	private Company _company;

	private Group _group;
	private ServiceContext _serviceContext;
	private User _user;

}