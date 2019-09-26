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

import com.liferay.account.exception.DuplicateAccountEntryUserRelException;
import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountEntryUserRelModel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.hamcrest.CoreMatchers;

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

		_accountEntryUserRels.add(accountEntryUserRel);

		Assert.assertNotNull(accountEntryUserRel);
		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				accountEntryUserRel.getPrimaryKey()));
	}

	@Test(expected = DuplicateAccountEntryUserRelException.class)
	public void testAddAccountEntryUserRel1ThrowsDuplicateAccountEntryUserRelException()
		throws Exception {

		_accountEntryUserRels.add(
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), _user.getUserId()));

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
		UserInfo userInfo = new UserInfo();

		AccountEntryUserRel accountEntryUserRel = _addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), userInfo);

		User user = _userLocalService.fetchUser(
			accountEntryUserRel.getAccountUserId());

		Assert.assertNotNull(user);
		Assert.assertEquals(
			accountEntryUserRel.getAccountUserId(), user.getUserId());
		Assert.assertEquals(userInfo.screenName, user.getScreenName());
	}

	@Test
	public void testAddAccountEntryUserRel2UserNotCreatedWithInvalidAccountEntryId()
		throws Exception {

		long invalidAccountEntryId = RandomTestUtil.nextLong();
		UserInfo userInfo = new UserInfo();

		try {
			_addAccountEntryUserRel(invalidAccountEntryId, userInfo);

			Assert.fail();
		}
		catch (NoSuchEntryException nsee) {
			Assert.assertThat(
				nsee.getMessage(),
				CoreMatchers.containsString(
					"No AccountEntry exists with the primary key " +
						invalidAccountEntryId));
		}

		Assert.assertNull(
			_userLocalService.fetchUserByScreenName(
				TestPropsValues.getCompanyId(), userInfo.screenName));
	}

	@Test
	public void testAddAccountEntryUserRel2UserNotCreatedWithInvalidUserInfo()
		throws Exception {

		UserInfo userInfo = new UserInfo();

		String invalidEmailAddress = "liferay";

		userInfo.emailAddress = invalidEmailAddress;

		try {
			_addAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), userInfo);

			Assert.fail();
		}
		catch (UserEmailAddressException.MustValidate ueaemv) {
			Assert.assertThat(
				ueaemv.getMessage(),
				CoreMatchers.containsString(
					"Email name address " + invalidEmailAddress +
						" must validate with"));
		}

		Assert.assertNull(
			_userLocalService.fetchUserByScreenName(
				TestPropsValues.getCompanyId(), userInfo.screenName));
	}

	@Test
	public void testGetAccountEntryUserRelsByAccountEntryId() throws Exception {
		_users.add(UserTestUtil.addUser());
		_users.add(UserTestUtil.addUser());
		_users.add(UserTestUtil.addUser());

		for (User user : _users) {
			_accountEntryUserRels.add(
				_accountEntryUserRelLocalService.addAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
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

	private AccountEntryUserRel _addAccountEntryUserRel(
			long accountId, UserInfo userInfo)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountId, TestPropsValues.getUserId(), userInfo.screenName,
				userInfo.emailAddress, userInfo.locale, userInfo.firstName,
				userInfo.middleName, userInfo.lastName, userInfo.prefixId,
				userInfo.suffixId);

		_users.add(
			_userLocalService.getUser(accountEntryUserRel.getAccountUserId()));

		return accountEntryUserRel;
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountEntryUserRel> _accountEntryUserRels =
		new ArrayList<>();

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

	private static class UserInfo {

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