/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.exception.NoSuchGroupRelException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.account.service.AccountGroupRelService;
import com.liferay.account.service.persistence.AccountGroupPersistence;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.service.test.util.AccountGroupTestUtil;
import com.liferay.account.service.test.util.UserRoleTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class AccountGroupRelServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.account.service"));

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry();
		_accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAddAccountGroupRel() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.addAccountGroupRel(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				AccountActionKeys.ASSIGN_ACCOUNTS, exception.getMessage(),
				_user.getUserId());
		}

		_updateAccountGroup(RandomTestUtil.randomLong());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.addAccountGroupRel(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_updateAccountGroup(TestPropsValues.getCompanyId());

		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ASSIGN_ACCOUNTS, AccountGroup.class.getName(),
			_user.getUserId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.addAccountGroupRel(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId());
		}
	}

	@Test
	public void testAddAccountGroupRels() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.addAccountGroupRels(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				new long[] {_accountEntry.getAccountEntryId()});

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				AccountActionKeys.ASSIGN_ACCOUNTS, exception.getMessage(),
				_user.getUserId());
		}

		_updateAccountGroup(RandomTestUtil.randomLong());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.addAccountGroupRels(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				new long[] {_accountEntry.getAccountEntryId()});

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_updateAccountGroup(TestPropsValues.getCompanyId());

		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ASSIGN_ACCOUNTS, AccountGroup.class.getName(),
			_user.getUserId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.addAccountGroupRels(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				new long[] {_accountEntry.getAccountEntryId()});
		}
	}

	@Test
	public void testDeleteAccountGroupRel() throws Exception {
		AccountGroupRel accountGroupRel =
			_accountGroupRelLocalService.addAccountGroupRel(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.deleteAccountGroupRel(
				accountGroupRel.getAccountGroupRelId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				AccountActionKeys.ASSIGN_ACCOUNTS, exception.getMessage(),
				_user.getUserId());
		}

		_updateAccountGroup(RandomTestUtil.randomLong());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.deleteAccountGroupRel(
				accountGroupRel.getAccountGroupRelId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_updateAccountGroup(TestPropsValues.getCompanyId());

		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ASSIGN_ACCOUNTS, AccountGroup.class.getName(),
			_user.getUserId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.deleteAccountGroupRel(
				accountGroupRel.getAccountGroupRelId());
		}
	}

	@Test
	public void testDeleteAccountGroupRels() throws Exception {
		_accountGroupRelLocalService.addAccountGroupRel(
			_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.deleteAccountGroupRels(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				new long[] {_accountEntry.getAccountEntryId()});

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				AccountActionKeys.ASSIGN_ACCOUNTS, exception.getMessage(),
				_user.getUserId());
		}

		_updateAccountGroup(RandomTestUtil.randomLong());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.deleteAccountGroupRels(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				new long[] {_accountEntry.getAccountEntryId()});

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_updateAccountGroup(TestPropsValues.getCompanyId());

		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.ASSIGN_ACCOUNTS, AccountGroup.class.getName(),
			_user.getUserId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.deleteAccountGroupRels(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				new long[] {_accountEntry.getAccountEntryId()});
		}
	}

	@Test
	public void testFetchAccountGroupRel() throws Exception {
		Assert.assertNull(
			_accountGroupRelService.fetchAccountGroupRel(
				RandomTestUtil.randomLong(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId()));

		_accountGroupRelLocalService.addAccountGroupRel(
			_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.fetchAccountGroupRel(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				AccountActionKeys.VIEW_ACCOUNTS, exception.getMessage(),
				_user.getUserId());
		}

		_updateAccountGroup(RandomTestUtil.randomLong());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.fetchAccountGroupRel(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_updateAccountGroup(TestPropsValues.getCompanyId());

		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.VIEW_ACCOUNTS, AccountGroup.class.getName(),
			_user.getUserId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.fetchAccountGroupRel(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId());
		}
	}

	@Test
	public void testGetAccountGroupRel() throws Exception {
		Assert.assertThrows(
			NoSuchGroupRelException.class,
			() -> _accountGroupRelService.getAccountGroupRel(
				RandomTestUtil.randomLong()));

		AccountGroupRel accountGroupRel =
			_accountGroupRelLocalService.addAccountGroupRel(
				_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.getAccountGroupRel(
				accountGroupRel.getAccountGroupRelId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				AccountActionKeys.VIEW_ACCOUNTS, exception.getMessage(),
				_user.getUserId());
		}

		_updateAccountGroup(RandomTestUtil.randomLong());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.getAccountGroupRel(
				accountGroupRel.getAccountGroupRelId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_updateAccountGroup(TestPropsValues.getCompanyId());

		UserRoleTestUtil.addResourcePermission(
			AccountActionKeys.VIEW_ACCOUNTS, AccountGroup.class.getName(),
			_user.getUserId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_accountGroupRelService.getAccountGroupRel(
				accountGroupRel.getAccountGroupRelId());
		}
	}

	private void _assertMessage(String actionId, String message, long userId) {
		Assert.assertTrue(
			message.contains(
				StringBundler.concat(
					"User ", userId, " must have ", actionId,
					" permission for")));
	}

	private void _updateAccountGroup(long companyId) {
		_accountGroup.setCompanyId(companyId);

		_accountGroup = _accountGroupPersistence.update(_accountGroup);
	}

	private AccountEntry _accountEntry;
	private AccountGroup _accountGroup;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private AccountGroupPersistence _accountGroupPersistence;

	@Inject
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Inject
	private AccountGroupRelService _accountGroupRelService;

	private User _user;

}