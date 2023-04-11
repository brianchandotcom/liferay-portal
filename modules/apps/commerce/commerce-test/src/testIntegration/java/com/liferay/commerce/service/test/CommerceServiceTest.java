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

package com.liferay.commerce.service.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian I. Kim
 */
@RunWith(Arquillian.class)
public class CommerceServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = CompanyLocalServiceUtil.getCompany(_group.getCompanyId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_commerceChannel = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, _group.getGroupId(), "Test Channel",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = UserTestUtil.addUser(_company);
	}

	@After
	public void tearDown() throws Exception {
		_commerceOrderLocalService.deleteCommerceOrders(
			_commerceChannel.getGroupId());

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddB2BCommerceOrderByBusinessAccountWithBuyerRole()
		throws Exception {

		frutillaRule.scenario(
			"Adding a new order"
		).given(
			"A user"
		).when(
			"The user has a buyer role"
		).and(
			"The user attempts to add an order using a business account"
		).then(
			"The order should be successfully created"
		);

		Role role = _addBuyerRole();

		User user = UserTestUtil.addUser(_company);

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		AccountEntry accountEntry =
			CommerceAccountTestUtil.addBusinessAccountEntry(
				_serviceContext.getUserId(), "Test Business Account", null,
				null, new long[] {_user.getUserId()}, null, _serviceContext);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), accountEntry.getAccountEntryGroupId(),
			role.getRoleId());

		_commerceOrderService.addCommerceOrder(
			_commerceChannel.getGroupId(), accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(), 0);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddB2BCommerceOrderByBusinessAccountWithoutBuyerRole()
		throws Exception {

		frutillaRule.scenario(
			"Adding a new order"
		).given(
			"A user"
		).when(
			"The user does not have a buyer role"
		).and(
			"The user attempts to add an order using a business account"
		).then(
			"The order should not be created"
		);

		User user = UserTestUtil.addUser(_company);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		AccountEntry accountEntry =
			CommerceAccountTestUtil.addBusinessAccountEntry(
				_serviceContext.getUserId(), "Test Business Account", null,
				null, new long[] {_user.getUserId()}, null, _serviceContext);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		_commerceOrderService.addCommerceOrder(
			_commerceChannel.getGroupId(), accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(), 0);
	}

	@Test
	public void testAddB2CCommerceOrderByGuestAccount() throws Exception {
		frutillaRule.scenario(
			"Adding a new order"
		).given(
			"A user"
		).when(
			"The user attempts to add a B2C order as a guest"
		).then(
			"The order should be successfully created"
		);

		User user = _company.getGuestUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		AccountEntry accountEntry =
			_accountEntryLocalService.getGuestAccountEntry(
				_company.getCompanyId());

		_commerceOrderService.addCommerceOrder(
			_commerceChannel.getGroupId(), accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(), 0);
	}

	@Test
	public void testAddB2CCommerceOrderByPersonalAccount() throws Exception {
		frutillaRule.scenario(
			"Adding a new order"
		).given(
			"A user"
		).when(
			"The user attempts to add a B2C order using a personal account"
		).then(
			"The order should be successfully created"
		);

		User user = UserTestUtil.addUser(_company);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		AccountEntry accountEntry =
			CommerceAccountTestUtil.getPersonAccountEntry(user.getUserId());

		_commerceOrderService.addCommerceOrder(
			_commerceChannel.getGroupId(), accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(), 0);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private Role _addBuyerRole() throws Exception {
		Role role = _roleLocalService.addRole(
			_user.getUserId(), null, 0, "Test Buyer",
			HashMapBuilder.put(
				_serviceContext.getLocale(), "Test Buyer"
			).build(),
			null, 1, null, _serviceContext);

		_resourcePermissionLocalService.addResourcePermission(
			_serviceContext.getCompanyId(),
			CommerceOrderConstants.RESOURCE_NAME, 1,
			String.valueOf(role.getCompanyId()), role.getRoleId(),
			"ADD_COMMERCE_ORDER");

		return role;
	}

	private static Company _company;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceOrderService _commerceOrderService;

	@DeleteAfterTestRun
	private Group _group;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}