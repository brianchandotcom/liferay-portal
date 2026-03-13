/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountEntryUserRelService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AccountEntryUserRelServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry();

		_serviceContext = ServiceContextTestUtil.getServiceContext();

		_serviceContext.setRequest(new MockHttpServletRequest());

		_user = UserTestUtil.addUser();

		UserTestUtil.setUser(_user);
	}

	@Test
	public void testAssignNewUserByEmailWithCreatePermission()
		throws Exception {

		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_accountEntry.getAccountEntryId(), RandomTestUtil.randomString(),
			null, null);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		_accountRoleLocalService.associateUser(
			_accountEntry.getAccountEntryId(), accountRole.getAccountRoleId(),
			_user.getUserId());

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			accountRole.getRoleId(), AccountActionKeys.ADD_USER);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			accountRole.getRoleId(), AccountActionKeys.ASSIGN_USERS);

		_accountEntryUserRelService.addAccountEntryUserRelByEmailAddress(
			_accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString() + "@liferay.com", new long[0],
			RandomTestUtil.randomString(), _serviceContext);
	}

	@Test(expected = PrincipalException.class)
	public void testAssignNewUserByEmailWithoutCreateAssignPermission()
		throws Exception {

		_accountEntryUserRelService.addAccountEntryUserRelByEmailAddress(
			_accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString() + "@liferay.com", new long[0],
			RandomTestUtil.randomString(), _serviceContext);
	}

	@Test(expected = PrincipalException.class)
	public void testAssignNewUserByEmailWithoutCreatePermission()
		throws Exception {

		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_accountEntry.getAccountEntryId(), RandomTestUtil.randomString(),
			null, null);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		_accountRoleLocalService.associateUser(
			_accountEntry.getAccountEntryId(), accountRole.getAccountRoleId(),
			_user.getUserId());

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			accountRole.getRoleId(), AccountActionKeys.ASSIGN_USERS);

		_accountEntryUserRelService.addAccountEntryUserRelByEmailAddress(
			_accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString() + "@liferay.com", new long[0],
			RandomTestUtil.randomString(), _serviceContext);
	}

	@Test(expected = PrincipalException.class)
	public void testAssignUserByEmailWithoutAssignPermission()
		throws Exception {

		_accountEntryUserRelService.addAccountEntryUserRelByEmailAddress(
			_accountEntry.getAccountEntryId(), _user.getEmailAddress(),
			new long[0], _user.getExternalReferenceCode(), _serviceContext);
	}

	@Test
	public void testInviteUserWithInviteUserPermissions() throws Exception {
		_assertInviteUserWithPermissions(AccountActionKeys.INVITE_USER);
	}

	@Test(expected = PrincipalException.class)
	public void testInviteUserWithNoPermissions() throws Exception {
		_accountEntryUserRelService.inviteUser(
			_accountEntry.getAccountEntryId(), null,
			RandomTestUtil.randomString() + "@liferay.com", _user,
			_serviceContext);
	}

	private void _assertInviteUserWithPermissions(String actionId)
		throws Exception {

		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_accountEntry.getAccountEntryId(), RandomTestUtil.randomString(),
			null, null);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		_accountRoleLocalService.associateUser(
			_accountEntry.getAccountEntryId(), accountRole.getAccountRoleId(),
			_user.getUserId());

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			accountRole.getRoleId(), actionId);

		_accountEntryUserRelService.inviteUser(
			_accountEntry.getAccountEntryId(), null,
			RandomTestUtil.randomString() + "@liferay.com", _user,
			_serviceContext);
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountEntryUserRelService _accountEntryUserRelService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}
