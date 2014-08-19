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

package com.liferay.portal.service;

import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.listeners.ResetDatabaseExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.MailServiceTestUtil;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;

import java.lang.reflect.Field;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;



/**
 * @author Brian Wing Shun Chan
 * @author Jose M. Navarro
 */

@RunWith(Enclosed.class)
public class UserServiceTest {

	@RunWith(Enclosed.class)
	public static class WhenSendPasswordEmail {

		@ExecutionTestListeners(
			listeners = {
				MainServletExecutionTestListener.class,
				ResetDatabaseExecutionTestListener.class,
				SynchronousMailExecutionTestListener.class
			})
		@RunWith(LiferayIntegrationJUnitTestRunner.class)
		@Sync
		public static class WithResetLink {

			@Before
			public void setUp() throws Exception {
				_user = UserTestUtil.addUser();

				PortletPreferences portletPreferences =
					PrefsPropsUtil.getPreferences(_user.getCompanyId(), false);

				portletPreferences.setValue(
					PropsKeys.COMPANY_SECURITY_SEND_PASSWORD,
					Boolean.FALSE.toString());

				portletPreferences.setValue(
					PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK,
					Boolean.TRUE.toString());

				portletPreferences.store();
			}

			@Test
			public void shouldSendResetLinkEmailByEmailAddress()
				throws Exception {

				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				boolean isPasswordReset =
					UserServiceUtil.sendPasswordByEmailAddress(
						_user.getCompanyId(), _user.getEmailAddress());

				Assert.assertFalse(isPasswordReset);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			}

			@Test
			public void shouldSendResetLinkEmailByScreenName()
				throws Exception {

				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				boolean isPasswordReset =
					UserServiceUtil.sendPasswordByScreenName(
						_user.getCompanyId(), _user.getScreenName());

				Assert.assertFalse(isPasswordReset);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			}

			@Test
			public void shouldSendResetLinkEmailByUserId() throws Exception {
				boolean isPasswordReset =
					UserServiceUtil.sendPasswordByUserId(_user.getUserId());

				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				Assert.assertFalse(isPasswordReset);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			}

			private User _user;

		}

		@ExecutionTestListeners(
			listeners = {
				MainServletExecutionTestListener.class,
				ResetDatabaseExecutionTestListener.class,
				SynchronousMailExecutionTestListener.class
			})
		@RunWith(LiferayIntegrationJUnitTestRunner.class)
		@Sync
		public static class WithNewPassword {

			@Before
			public void setUp() throws Exception {
				_user = UserTestUtil.addUser();

				PortletPreferences portletPreferences =
					PrefsPropsUtil.getPreferences(_user.getCompanyId(), false);

				portletPreferences.setValue(
					PropsKeys.COMPANY_SECURITY_SEND_PASSWORD,
					Boolean.TRUE.toString());

				portletPreferences.setValue(
					PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK,
					Boolean.FALSE.toString());

				portletPreferences.store();
			}

			@Test
			public void shouldSendNewPasswordEmailByEmailAddress()
				throws Exception {

				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				boolean isPasswordSent =
					UserServiceUtil.sendPasswordByEmailAddress(
						_user.getCompanyId(), _user.getEmailAddress());

				Assert.assertTrue(isPasswordSent);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			}

			@Test
			public void shouldSendNewPasswordEmailByScreenName()
				throws Exception {

				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				boolean isPasswordSent =
					UserServiceUtil.sendPasswordByScreenName(
						_user.getCompanyId(), _user.getScreenName());

				Assert.assertTrue(isPasswordSent);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			}

			@Test
			public void shouldSendNewPasswordEmailByUserId() throws Exception {
				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				boolean isPasswordSent =
					UserServiceUtil.sendPasswordByUserId(_user.getUserId());

				Assert.assertTrue(isPasswordSent);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			}

			private User _user;

		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenCompanySecurityStrangers {

		@Test(expected = ReservedUserEmailAddressException.class)
		public void testCompanySecurityStrangersWithMX1() throws Exception {
			Field field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

			Object value = field.get(null);

			String name = PrincipalThreadLocal.getName();

			try {
				field.set(null, Boolean.FALSE);

				PrincipalThreadLocal.setName(0);

				UserServiceTest.addUser(true);
			}
			finally {
				field.set(null, value);

				PrincipalThreadLocal.setName(name);
			}
		}

		@Test(expected = ReservedUserEmailAddressException.class)
		public void testCompanySecurityStrangersWithMX2() throws Exception {
			Field field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

			Object value = field.get(null);

			String name = PrincipalThreadLocal.getName();

			try {
				field.set(null, Boolean.FALSE);

				User user = UserServiceTest.addUser(false);

				PrincipalThreadLocal.setName(user.getUserId());

				UserServiceTest.updateUser(user);
			}
			finally {
				field.set(null, value);

				PrincipalThreadLocal.setName(name);
			}
		}

		@Test(expected = ReservedUserEmailAddressException.class)
		public void testCompanySecurityStrangersWithMX3() throws Exception {
			Field field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

			Object value = field.get(null);

			String name = PrincipalThreadLocal.getName();

			try {
				field.set(null, Boolean.FALSE);

				User user = UserServiceTest.addUser(false);

				PrincipalThreadLocal.setName(user.getUserId());

				String emailAddress =
					"UserServiceTest." + RandomTestUtil.nextLong() +
						"@liferay.com";

				UserServiceUtil.updateEmailAddress(
					user.getUserId(), user.getPassword(), emailAddress,
					emailAddress, new ServiceContext());
			}
			finally {
				field.set(null, value);

				PrincipalThreadLocal.setName(name);
			}
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenCRUDOperations {

		@Test
		public void testAddUser() throws Exception {
			UserServiceTest.addUser(true);
		}

		@Test
		public void testDeleteUser() throws Exception {
			User user = UserServiceTest.addUser(true);

			UserServiceUtil.deleteUser(user.getUserId());
		}

		@Test
		public void testGetUser() throws Exception {
			User user = UserServiceTest.addUser(true);

			User retrievedUser = UserServiceUtil.getUserByEmailAddress(
				TestPropsValues.getCompanyId(), user.getEmailAddress());

			Assert.assertEquals(user, retrievedUser);
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenUnsetGroupUsers {

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = GroupTestUtil.addGroup();

			_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_organizationAdminUser =
				UserTestUtil.addOrganizationAdminUser(_organization);

			_organizationOwnerUser =
				UserTestUtil.addOrganizationOwnerUser(_organization);

			_organizationGroupUser = UserTestUtil.addGroupOwnerUser(
				_organization.getGroup());
		}

		@Test
		public void groupAdminShouldUnsetGroupAdmin() throws Exception {
			User otherGroupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			UserServiceTest.unsetGroupUsers(_group.getGroupId(),
				_groupAdminUser, otherGroupAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), otherGroupAdminUser.getUserId()));
		}

		@Test
		public void groupAdminShouldUnsetGroupOwner() throws Exception {
			UserServiceTest.unsetGroupUsers(_group.getGroupId(),
				_groupAdminUser, _groupOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupOwnerUser.getUserId()));
		}

		@Test
		public void groupAdminShouldUnsetOrganizationAdmin() throws Exception {
			UserServiceTest.unsetOrganizationUsers(
				_organization.getOrganizationId(), _groupAdminUser,
				_organizationAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					_organizationAdminUser.getUserId()));
		}

		@Test
		public void groupAdminShouldUnsetOrganizationOwner() throws Exception {
			UserServiceTest.unsetOrganizationUsers(
				_organization.getOrganizationId(), _groupAdminUser,
				_organizationOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					_organizationOwnerUser.getUserId()));
		}

		@Test
		public void groupOwnerShouldUnsetGroupAdmin() throws Exception {
			User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			UserServiceTest.unsetGroupUsers(
				_group.getGroupId(), _groupOwnerUser, groupAdminUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupAdminUser.getUserId()));
		}

		@Test
		public void groupOwnerShouldUnsetGroupOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			UserServiceTest.unsetGroupUsers(
				_group.getGroupId(), _groupOwnerUser, groupOwnerUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupOwnerUser.getUserId()));
		}

		@Test
		public void groupOwnerShouldUnsetOrganizationAdmin() throws Exception {
			User organizationAdminUser =
				UserTestUtil.addOrganizationAdminUser(_organization);

			UserServiceTest.unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationGroupUser,
				organizationAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					organizationAdminUser.getUserId()));
		}

		@Test
		public void groupOwnerShouldUnsetOrganizationOwner() throws Exception {
			User organizationOwnerUser =
				UserTestUtil.addOrganizationOwnerUser(_organization);

			UserServiceTest.unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationGroupUser,
				organizationOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					organizationOwnerUser.getUserId()));
		}

		private Organization _organization;
		private Group _group;
		private User _groupAdminUser;
		private User _groupOwnerUser;
		private User _organizationAdminUser;
		private User _organizationOwnerUser;
		private User _organizationGroupUser;

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenUnsetOrganizationUsersForNonSiteOrganization {

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization();

			_organizationAdminUser =
				UserTestUtil.addOrganizationAdminUser(_organization);

			_organizationOwnerUser =
				UserTestUtil.addOrganizationOwnerUser(_organization);
		}

		@Test
		public void organizationAdminShouldUnsetOrganizationAdmin()
			throws Exception {

			User otherOrganizationAdminUser =
				UserTestUtil.addOrganizationAdminUser(_organization);

			UserServiceTest.unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationAdminUser,
				otherOrganizationAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					otherOrganizationAdminUser.getUserId()));
		}

		@Test
		public void organizationAdminShouldUnsetOrganizationOwner()
			throws Exception {

			UserServiceTest.unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationAdminUser,
				_organizationOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					_organizationOwnerUser.getUserId()));
		}

		@Test
		public void organizationOwnerShouldUnsetOrganizationAdmin()
			throws Exception {

			UserServiceTest.unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationOwnerUser,
				_organizationAdminUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					_organizationAdminUser.getUserId()));
		}

		@Test
		public void organizationOwnerShouldUnsetOrganizationOwner()
			throws Exception {

			User otherOrganizationOwnerUser =
				UserTestUtil.addOrganizationOwnerUser(_organization);

			UserServiceTest.unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationOwnerUser,
				otherOrganizationOwnerUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					otherOrganizationOwnerUser.getUserId()));
		}

		private Organization _organization;
		private User _organizationAdminUser;
		private User _organizationOwnerUser;

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenUnsetOrganizationUsersForSiteOrganization {

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = _organization.getGroup();

			_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_organizationAdminUser =
				UserTestUtil.addOrganizationAdminUser(_organization);

			_organizationOwnerUser =
				UserTestUtil.addOrganizationOwnerUser(_organization);
		}

		@Test
		public void organizationAdminShouldUnsetSiteAdmin() throws Exception {
			UserServiceTest.unsetGroupUsers(
				_group.getGroupId(), _organizationAdminUser, _groupAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupAdminUser.getUserId()));
		}

		@Test
		public void organizationAdminShouldUnsetSiteOwner() throws Exception {
			UserServiceTest.unsetGroupUsers(
				_group.getGroupId(), _organizationAdminUser, _groupOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupOwnerUser.getUserId()));
		}

		@Test
		public void organizationOwnerShouldUnsetSiteAdmin() throws Exception {
			UserServiceTest.unsetGroupUsers(
				_group.getGroupId(), _organizationOwnerUser, _groupAdminUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupAdminUser.getUserId()));
		}

		@Test
		public void organizationOwnerShouldUnsetSiteOwner() throws Exception {
			UserServiceTest.unsetGroupUsers(
				_group.getGroupId(), _organizationOwnerUser, _groupOwnerUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupOwnerUser.getUserId()));
		}

		private Organization _organization;
		private Group _group;
		private User _groupAdminUser;
		private User _groupOwnerUser;
		private User _organizationAdminUser;
		private User _organizationOwnerUser;

	}

	protected static User addUser(boolean secure) throws Exception {
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "UserServiceTest";
		String middleName = StringPool.BLANK;
		String lastName = "UserServiceTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		ServiceContext serviceContext = new ServiceContext();

		if (secure) {
			String emailAddress =
				"UserServiceTest." + RandomTestUtil.nextLong() +
					"@liferay.com";

			return UserServiceUtil.addUser(
				TestPropsValues.getCompanyId(), autoPassword, password1,
				password2, autoScreenName, screenName, emailAddress, facebookId,
				openId, locale, firstName, middleName, lastName, prefixId,
				suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
				jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
				sendMail, serviceContext);
		}
		else {
			String emailAddress =
				"UserServiceTest." + RandomTestUtil.nextLong() + "@test.com";

			return UserLocalServiceUtil.addUser(
				TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
				autoPassword, password1, password2, autoScreenName, screenName,
				emailAddress, facebookId, openId, locale, firstName, middleName,
				lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
				userGroupIds, sendMail, serviceContext);
		}
	}

	protected static void unsetGroupUsers(
		long groupId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = new ServiceContext();

		UserServiceUtil.unsetGroupUsers(
			groupId, new long[] {objectUser.getUserId()}, serviceContext);
	}

	protected static void unsetOrganizationUsers(
		long organizationId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		UserServiceUtil.unsetOrganizationUsers(
			organizationId, new long[] {objectUser.getUserId()});
	}

	protected static User updateUser(User user) throws Exception {
		String oldPassword = StringPool.BLANK;
		String newPassword1 = StringPool.BLANK;
		String newPassword2 = StringPool.BLANK;
		Boolean passwordReset = false;
		String reminderQueryQuestion = StringPool.BLANK;
		String reminderQueryAnswer = StringPool.BLANK;
		String screenName = "TestUser" + RandomTestUtil.nextLong();
		String emailAddress =
			"UserServiceTest." + RandomTestUtil.nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String languageId = StringPool.BLANK;
		String timeZoneId = StringPool.BLANK;
		String greeting = StringPool.BLANK;
		String comments = StringPool.BLANK;
		String firstName = "UserServiceTest";
		String middleName = StringPool.BLANK;
		String lastName = "UserServiceTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String smsSn = StringPool.BLANK;
		String aimSn = StringPool.BLANK;
		String facebookSn = StringPool.BLANK;
		String icqSn = StringPool.BLANK;
		String jabberSn = StringPool.BLANK;
		String msnSn = StringPool.BLANK;
		String mySpaceSn = StringPool.BLANK;
		String skypeSn = StringPool.BLANK;
		String twitterSn = StringPool.BLANK;
		String ymSn = StringPool.BLANK;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;

		ServiceContext serviceContext = new ServiceContext();

		return UserServiceUtil.updateUser(
			user.getUserId(), oldPassword, newPassword1, newPassword2,
			passwordReset, reminderQueryQuestion, reminderQueryAnswer,
			screenName, emailAddress, facebookId, openId, languageId,
			timeZoneId, greeting, comments, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn,
			skypeSn, twitterSn, ymSn, jobTitle, groupIds, organizationIds,
			roleIds, userGroupRoles, userGroupIds, serviceContext);
	}

}