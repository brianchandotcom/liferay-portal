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
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.commerce.notification.test.util.CommerceNotificationTestUtil;
import com.liferay.commerce.notification.util.CommerceNotificationHelper;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
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

import java.util.Collections;
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
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class CommerceNotificationTest {

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
			StringPool.BLANK, _group.getGroupId(),
			_group.getName(_serviceContext.getLanguageId()) + " Portal",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null, StringPool.BLANK,
			_serviceContext);
	}

	@After
	public void tearDown() throws PortalException {
		if (_createdAdminRole) {
			_roleLocalService.deleteRole(_accountAdminRole.getRoleId());
		}

		if (_createdOrderManagerRole) {
			_roleLocalService.deleteRole(_orderManagerRole.getRoleId());
		}

		_commerceNotificationQueueEntryLocalService.
			deleteCommerceNotificationQueueEntries(_group.getGroupId());
	}

	@Test
	public void testAccountAdministratorRecipient() throws Exception {
		_setUpAccountAdministrator();

		_commerceNotificationTemplate =
			CommerceNotificationTestUtil.addNotificationTemplate(
				"[%ACCOUNT_ROLE_ADMINISTRATOR%]",
				CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
				_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceNotificationHelper.sendNotifications(
			_group.getGroupId(), _user.getUserId(),
			CommerceOrderConstants.ORDER_NOTIFICATION_PLACED, _commerceOrder);

		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntries(
					_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

		Assert.assertEquals(
			commerceNotificationQueueEntries.toString(), 1,
			commerceNotificationQueueEntries.size());

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			commerceNotificationQueueEntries.get(0);

		Assert.assertEquals(
			commerceNotificationQueueEntry.getTo(),
			_accountAdmin.getEmailAddress());
	}

	@Test
	public void testEmailAddressRecipient() throws Exception {
		_user.setEmailAddress("mail@mail.com");

		_userLocalService.updateUser(_user);

		_commerceNotificationTemplate =
			CommerceNotificationTestUtil.addNotificationTemplate(
				_user.getEmailAddress(),
				CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
				_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _group.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceNotificationHelper.sendNotifications(
			_group.getGroupId(), _user.getUserId(),
			CommerceOrderConstants.ORDER_NOTIFICATION_PLACED, _commerceOrder);

		int commerceNotificationQueueEntriesCount =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntriesCount(_group.getGroupId());

		Assert.assertEquals(1, commerceNotificationQueueEntriesCount);
	}

	@Test
	public void testOrderCreatorRecipient() throws Exception {
		_commerceNotificationTemplate =
			CommerceNotificationTestUtil.addNotificationTemplate(
				"[%ORDER_CREATOR%]",
				CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
				_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _group.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceNotificationHelper.sendNotifications(
			_group.getGroupId(), _user.getUserId(),
			CommerceOrderConstants.ORDER_NOTIFICATION_PLACED, _commerceOrder);

		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntries(
					_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

		Assert.assertEquals(
			commerceNotificationQueueEntries.toString(), 1,
			commerceNotificationQueueEntries.size());

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			commerceNotificationQueueEntries.get(0);

		User user = _userLocalService.getUser(_commerceOrder.getUserId());

		Assert.assertEquals(
			commerceNotificationQueueEntry.getTo(), user.getEmailAddress());
	}

	@Test
	public void testOrderManagerRecipient() throws Exception {
		_setUpAccountAdministrator();

		_setUpOrderManager();

		_commerceNotificationTemplate =
			CommerceNotificationTestUtil.addNotificationTemplate(
				"[%ACCOUNT_ROLE_ORDER_MANAGER%]",
				CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
				_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceNotificationHelper.sendNotifications(
			_group.getGroupId(), _user.getUserId(),
			CommerceOrderConstants.ORDER_NOTIFICATION_PLACED, _commerceOrder);

		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntries(
					_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

		Assert.assertEquals(
			commerceNotificationQueueEntries.toString(), 1,
			commerceNotificationQueueEntries.size());

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			commerceNotificationQueueEntries.get(0);

		Assert.assertEquals(
			commerceNotificationQueueEntry.getTo(),
			_orderManager.getEmailAddress());
	}

	@Test
	public void testOrderPlacedNotification() throws Exception {
		_commerceNotificationTemplate =
			CommerceNotificationTestUtil.addNotificationTemplate(
				"[%ORDER_CREATOR%]",
				CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
				_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _group.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceNotificationHelper.sendNotifications(
			_group.getGroupId(), _user.getUserId(),
			CommerceOrderConstants.ORDER_NOTIFICATION_PLACED, _commerceOrder);

		int commerceNotificationQueueEntriesCount =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntriesCount(_group.getGroupId());

		Assert.assertEquals(1, commerceNotificationQueueEntriesCount);
	}

	@Test
	public void testUserGroupRecipient() throws Exception {
		_setUpAccountAdministrator();

		_setUpOrderManager();

		String groupName = _setUpUserGroup();

		_commerceNotificationTemplate =
			CommerceNotificationTestUtil.addNotificationTemplate(
				"[%USER_GROUP_" + groupName + "%]",
				CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
				_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _group.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceNotificationHelper.sendNotifications(
			_group.getGroupId(), _user.getUserId(),
			CommerceOrderConstants.ORDER_NOTIFICATION_PLACED, _commerceOrder);

		int commerceNotificationQueueEntriesCount =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntriesCount(_group.getGroupId());

		Assert.assertEquals(1, commerceNotificationQueueEntriesCount);
	}

	private void _setUpAccountAdministrator() throws Exception {
		_accountAdmin = UserTestUtil.addUser(
			_user.getCompanyId(), _user.getUserId(), "businessUser",
			_serviceContext.getLocale(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			new long[] {_serviceContext.getScopeGroupId()}, _serviceContext);

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_accountAdmin.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()}, null,
			_serviceContext);

		_accountAdminRole = _roleLocalService.fetchRole(
			_company.getCompanyId(),
			CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR);

		if (_accountAdminRole == null) {
			_accountAdminRole = _roleLocalService.addRole(
				_serviceContext.getUserId(), null, 0,
				CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR,
				Collections.singletonMap(
					_serviceContext.getLocale(),
					CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR),
				Collections.emptyMap(), RoleConstants.TYPE_SITE,
				StringPool.BLANK, _serviceContext);

			_createdAdminRole = true;
		}

		_userGroupRoleLocalService.deleteUserGroupRoles(
			_accountAdmin.getUserId(),
			new long[] {_commerceAccount.getCommerceAccountGroupId()});

		_userGroupRoleLocalService.addUserGroupRoles(
			_accountAdmin.getUserId(),
			_commerceAccount.getCommerceAccountGroupId(),
			new long[] {_accountAdminRole.getRoleId()});
	}

	private void _setUpOrderManager() throws Exception {
		_orderManager = UserTestUtil.addUser(
			_user.getCompanyId(), _user.getUserId(), "businessUser",
			_serviceContext.getLocale(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			new long[] {_serviceContext.getScopeGroupId()}, _serviceContext);

		_orderManagerRole = _roleLocalService.fetchRole(
			_company.getCompanyId(), "Order Manager");

		if (_orderManagerRole == null) {
			_orderManagerRole = _roleLocalService.addRole(
				_serviceContext.getUserId(), null, 0, "Order Manager",
				Collections.singletonMap(
					_serviceContext.getLocale(), "Order Manager"),
				Collections.emptyMap(), RoleConstants.TYPE_SITE,
				StringPool.BLANK, _serviceContext);

			_createdOrderManagerRole = true;
		}

		_userGroupRoleLocalService.deleteUserGroupRoles(
			_orderManager.getUserId(),
			new long[] {_commerceAccount.getCommerceAccountGroupId()});

		_userGroupRoleLocalService.addUserGroupRoles(
			_orderManager.getUserId(),
			_commerceAccount.getCommerceAccountGroupId(),
			new long[] {_orderManagerRole.getRoleId()});
	}

	private String _setUpUserGroup() throws Exception {
		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			_user.getUserId(), _company.getCompanyId(), "Test User Group",
			RandomTestUtil.randomString(), _serviceContext);

		long[] userIds = new long[1];

		userIds[0] = _orderManager.getUserId();

		_userLocalService.addUserGroupUsers(
			userGroup.getUserGroupId(), userIds);

		return userGroup.getName();
	}

	@DeleteAfterTestRun
	private User _accountAdmin;

	private Role _accountAdminRole;
	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceNotificationHelper _commerceNotificationHelper;

	@Inject
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

	@DeleteAfterTestRun
	private CommerceNotificationTemplate _commerceNotificationTemplate;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private Company _company;

	private boolean _createdAdminRole;
	private boolean _createdOrderManagerRole;
	private Group _group;
	private User _orderManager;
	private Role _orderManagerRole;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;
	private User _user;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}