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

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.DuplicateAccountEntryIdException;
import com.liferay.account.exception.DuplicateAccountEntryUserRelException;
import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.exception.NoSuchEntryUserRelException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountEntryUserRelModel;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountEntryUserRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAddAccountEntryUserRel1() throws Exception {
		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), _user.getUserId());

		Assert.assertNotNull(accountEntryUserRel);
		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				accountEntryUserRel.getPrimaryKey()));
	}

	@Test(expected = DuplicateAccountEntryUserRelException.class)
	public void testAddAccountEntryUserRel1ThrowsDuplicateAccountEntryUserRelException()
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());
	}

	@Test(expected = NoSuchEntryException.class)
	public void testAddAccountEntryUserRel1ThrowsNoSuchEntryException()
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId() + RandomTestUtil.nextLong(),
			_user.getUserId());
	}

	@Test(expected = NoSuchUserException.class)
	public void testAddAccountEntryUserRel1ThrowsNoSuchUserException()
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(),
			_user.getUserId() + RandomTestUtil.nextLong());
	}

	@Test
	public void testAddAccountEntryUserRel2() throws Exception {
		_testAddAccountEntryUserRel2(
			_accountEntry.getAccountEntryId(), _accountEntry.getCompanyId());
	}

	@Test
	public void testAddAccountEntryUserRel2WithDefaultAccountEntryId()
		throws Exception {

		_testAddAccountEntryUserRel2(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			TestPropsValues.getCompanyId());
	}

	@Test
	public void testAddAccountEntryUserRel2WithInvalidAccountEntryId()
		throws Exception {

		long invalidAccountEntryId = RandomTestUtil.nextLong();

		try {
			_addAccountEntryUserRel(invalidAccountEntryId);

			Assert.fail();
		}
		catch (NoSuchEntryException noSuchEntryException) {
			String message = noSuchEntryException.getMessage();

			Assert.assertTrue(
				message.contains(
					"No AccountEntry exists with the primary key " +
						invalidAccountEntryId));
		}

		Assert.assertNull(
			_userLocalService.fetchUserByScreenName(
				TestPropsValues.getCompanyId(), _userInfo.screenName));
	}

	@Test
	public void testAddAccountEntryUserRel2WithInvalidUserInfo()
		throws Exception {

		String invalidEmailAddress = "liferay";

		_userInfo.emailAddress = invalidEmailAddress;

		try {
			_addAccountEntryUserRel(_accountEntry.getAccountEntryId());

			Assert.fail();
		}
		catch (UserEmailAddressException.MustValidate
					userEmailAddressException) {

			String message = userEmailAddressException.getMessage();

			Assert.assertTrue(
				message.contains(
					"Email name address " + invalidEmailAddress +
						" must validate with"));
		}

		Assert.assertNull(
			_userLocalService.fetchUserByScreenName(
				TestPropsValues.getCompanyId(), _userInfo.screenName));
	}

	@Test
	public void testAddAccountEntryUserRels() throws Exception {
		_users.add(UserTestUtil.addUser());
		_users.add(UserTestUtil.addUser());

		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			_accountEntry.getAccountEntryId(),
			ListUtil.toLongArray(_users, User.USER_ID_ACCESSOR));

		Assert.assertEquals(
			2,
			_accountUserRetriever.getAccountUsersCount(
				_accountEntry.getAccountEntryId()));

		List<User> accountUsers = _accountUserRetriever.getAccountUsers(
			_accountEntry.getAccountEntryId());

		Assert.assertTrue(accountUsers.containsAll(_users));
		Assert.assertTrue(_users.containsAll(accountUsers));
	}

	@Test
	public void testDeleteAccountEntryUserRels() throws Exception {
		AccountEntryUserRel accountEntryUserRel = _addAccountEntryUserRel(
			_accountEntry.getAccountEntryId());

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(
					_accountEntry.getAccountEntryId());

		Assert.assertEquals(
			accountEntryUserRels.toString(), 1, accountEntryUserRels.size());

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			_accountEntry.getAccountEntryId(),
			new long[] {accountEntryUserRel.getAccountUserId()});

		accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(
					_accountEntry.getAccountEntryId());

		Assert.assertEquals(
			accountEntryUserRels.toString(), 0, accountEntryUserRels.size());
	}

	@Test(expected = NoSuchEntryUserRelException.class)
	public void testDeleteAccountEntryUserRelsThrowsNoSuchEntryUserRelException()
		throws Exception {

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			_accountEntry.getAccountEntryId(), new long[] {_user.getUserId()});
	}

	@Test
	public void testGetAccountEntryUserRelsByAccountEntryId() throws Exception {
		_users.add(UserTestUtil.addUser());
		_users.add(UserTestUtil.addUser());
		_users.add(UserTestUtil.addUser());

		for (User user : _users) {
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId());
		}

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(
					_accountEntry.getAccountEntryId());

		long[] expectedUserIds = ListUtil.toLongArray(
			_users, User.USER_ID_ACCESSOR);

		Arrays.sort(expectedUserIds);

		long[] actualUserIds = ListUtil.toLongArray(
			accountEntryUserRels, AccountEntryUserRelModel::getAccountUserId);

		Arrays.sort(actualUserIds);

		Assert.assertArrayEquals(expectedUserIds, actualUserIds);
	}

	@Test
	public void testUpdateAccountEntryUserRels() throws Exception {
		long userId = _user.getUserId();

		// Add account entries for a user

		long[] addAccountEntryIds1 = _addAccountEntries(3);
		long[] deleteAccountEntryIds1 = _addAccountEntries(2);

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			addAccountEntryIds1, deleteAccountEntryIds1, userId);

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(userId);

		Assert.assertEquals(
			accountEntryUserRels.toString(), 3, accountEntryUserRels.size());

		// Add and delete account entries for a user

		long[] addAccountEntryIds2 = _addAccountEntries(5);
		long[] deleteAccountEntryIds2 = {addAccountEntryIds1[0]};

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			addAccountEntryIds2, deleteAccountEntryIds2, userId);

		List<Long> expectedAccountEntryIdsList = new ArrayList<>(
			ListUtil.fromArray(addAccountEntryIds2));

		expectedAccountEntryIdsList.add(addAccountEntryIds1[1]);
		expectedAccountEntryIdsList.add(addAccountEntryIds1[2]);

		accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(userId);

		List<Long> actualAccountEntryIds = TransformUtil.transform(
			accountEntryUserRels, AccountEntryUserRelModel::getAccountEntryId);

		Assert.assertEquals(
			ListUtil.sort(expectedAccountEntryIdsList),
			ListUtil.sort(actualAccountEntryIds));

		// Delete all account entries for a user

		long[] addAccountEntryIds3 = new long[0];
		long[] deleteAccountEntryIds3 = ArrayUtil.toLongArray(
			expectedAccountEntryIdsList);

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			addAccountEntryIds3, deleteAccountEntryIds3, userId);

		accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(userId);

		Assert.assertEquals(
			accountEntryUserRels.toString(), 1, accountEntryUserRels.size());

		AccountEntryUserRel accountEntryUserRel = accountEntryUserRels.get(0);

		Assert.assertEquals(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			accountEntryUserRel.getAccountEntryId());
	}

	@Test(expected = DuplicateAccountEntryIdException.class)
	public void testUpdateAccountEntryUserRelsThrowsDuplicateAccountEntryIdException()
		throws Exception {

		long[] accountEntryIds = _addAccountEntries(3);

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			accountEntryIds, accountEntryIds, _user.getUserId());
	}

	private long[] _addAccountEntries(int count) throws Exception {
		long[] accountEntryIds = new long[count];

		for (int i = 0; i < count; i++) {
			AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
				_accountEntryLocalService);

			_accountEntries.add(accountEntry);

			accountEntryIds[i] = accountEntry.getAccountEntryId();
		}

		return accountEntryIds;
	}

	private AccountEntryUserRel _addAccountEntryUserRel(long accountEntryId)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountEntryId, TestPropsValues.getUserId(),
				_userInfo.screenName, _userInfo.emailAddress, _userInfo.locale,
				_userInfo.firstName, _userInfo.middleName, _userInfo.lastName,
				_userInfo.prefixId, _userInfo.suffixId);

		_users.add(
			_userLocalService.getUser(accountEntryUserRel.getAccountUserId()));

		return accountEntryUserRel;
	}

	private void _testAddAccountEntryUserRel2(
			long accountEntryId, long expectedCompanyId)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel = _addAccountEntryUserRel(
			accountEntryId);

		User user = _userLocalService.fetchUser(
			accountEntryUserRel.getAccountUserId());

		Assert.assertNotNull(user);
		Assert.assertEquals(expectedCompanyId, user.getCompanyId());
		Assert.assertEquals(
			accountEntryUserRel.getAccountUserId(), user.getUserId());

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountUsers(
				accountEntryId, user.getScreenName(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "screenName", false);

		List<User> users = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(users.get(0), user);
	}

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountUserRetriever _accountUserRetriever;

	@DeleteAfterTestRun
	private User _user;

	private final UserInfo _userInfo = new UserInfo();

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

	private class UserInfo {

		public String emailAddress =
			RandomTestUtil.randomString() + "@liferay.com";
		public String firstName = RandomTestUtil.randomString();
		public String lastName = RandomTestUtil.randomString();
		public Locale locale = LocaleThreadLocal.getDefaultLocale();
		public String middleName = RandomTestUtil.randomString();
		public long prefixId = RandomTestUtil.randomLong();
		public String screenName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());
		public long suffixId = RandomTestUtil.randomLong();

	}

}