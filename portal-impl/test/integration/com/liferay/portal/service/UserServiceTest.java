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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
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

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenCompanySecurityStrangersWithMX {

		@Test(expected = ReservedUserEmailAddressException.class)
		public void testAddUser() throws Exception {
			Field field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

			Object value = field.get(null);

			String name = PrincipalThreadLocal.getName();

			try {
				field.set(null, Boolean.FALSE);

				PrincipalThreadLocal.setName(0);

				UserTestUtil.addUser(true);

				Assert.fail();
			}
			finally {
				field.set(null, value);

				PrincipalThreadLocal.setName(name);
			}
		}

		@Test(expected = ReservedUserEmailAddressException.class)
		public void testUpdateUser() throws Exception {
			Field field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

			Object value = field.get(null);

			String name = PrincipalThreadLocal.getName();

			try {
				field.set(null, Boolean.FALSE);

				User user = UserTestUtil.addUser(false);

				PrincipalThreadLocal.setName(user.getUserId());

				UserTestUtil.updateUser(user);

				Assert.fail();
			}
			finally {
				field.set(null, value);

				PrincipalThreadLocal.setName(name);
			}
		}

		@Test(expected = ReservedUserEmailAddressException.class)
		public void testUpdateEmailAddress() throws Exception {
			Field field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

			Object value = field.get(null);

			String name = PrincipalThreadLocal.getName();

			try {
				field.set(null, Boolean.FALSE);

				User user = UserTestUtil.addUser(false);

				PrincipalThreadLocal.setName(user.getUserId());

				String emailAddress =
					"UserServiceTest." + RandomTestUtil.nextLong() +
						"@liferay.com";

				UserServiceUtil.updateEmailAddress(
					user.getUserId(), user.getPassword(), emailAddress,
					emailAddress, new ServiceContext());

				Assert.fail();
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
			UserTestUtil.addUser(true);
		}

		@Test
		public void testDeleteUser() throws Exception {
			User user = UserTestUtil.addUser(true);

			UserServiceUtil.deleteUser(user.getUserId());
		}

		@Test
		public void testGetUser() throws Exception {
			User user = UserTestUtil.addUser(true);

			User retrievedUser = UserServiceUtil.getUserByEmailAddress(
				TestPropsValues.getCompanyId(), user.getEmailAddress());

			Assert.assertEquals(user, retrievedUser);
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class,
			SynchronousMailExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenSendingAPasswordEmail {

		@Before
		public void setUp() throws Exception {
			_user = UserTestUtil.addUser();
		}

		@Test
		public void shouldSendNewPasswordEmailByEmailAddress()
			throws Exception {

			givenThatCompanySendsNewPassword();

			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean isPasswordSent =
				UserServiceUtil.sendPasswordByEmailAddress(
					_user.getCompanyId(), _user.getEmailAddress());

			Assert.assertTrue(isPasswordSent);
			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_sent_body.tmpl"));
		}

		@Test
		public void shouldSendNewPasswordEmailByScreenName() throws Exception {
			givenThatCompanySendsNewPassword();

			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean isPasswordSent =
				UserServiceUtil.sendPasswordByScreenName(
					_user.getCompanyId(), _user.getScreenName());

			Assert.assertTrue(isPasswordSent);
			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_sent_body.tmpl"));
		}

		@Test
		public void shouldSendNewPasswordEmailByUserId() throws Exception {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			givenThatCompanySendsNewPassword();

			boolean isPasswordSent = UserServiceUtil.sendPasswordByUserId(
				_user.getUserId());

			Assert.assertTrue(isPasswordSent);
			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_sent_body.tmpl"));
		}

		@Test
		public void shouldSendResetLinkEmailByEmailAddress() throws Exception {
			givenThatCompanySendsResetPasswordLink();

			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean isPasswordSent =
				UserServiceUtil.sendPasswordByEmailAddress(
					_user.getCompanyId(), _user.getEmailAddress());

			Assert.assertFalse(isPasswordSent);
			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_reset_body.tmpl"));
		}

		@Test
		public void shouldSendResetLinkEmailByScreenName() throws Exception {
			givenThatCompanySendsResetPasswordLink();

			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean isPasswordSent =
				UserServiceUtil.sendPasswordByScreenName(
					_user.getCompanyId(), _user.getScreenName());

			Assert.assertFalse(isPasswordSent);
			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_reset_body.tmpl"));
		}

		@Test
		public void shouldSendResetLinkEmailByUserId() throws Exception {
			givenThatCompanySendsResetPasswordLink();

			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean isPasswordSent = UserServiceUtil.sendPasswordByUserId(
				_user.getUserId());

			Assert.assertFalse(isPasswordSent);
			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_reset_body.tmpl"));
		}

		protected void givenThatCompanySendsNewPassword()
			throws Exception {

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

		protected void givenThatCompanySendsResetPasswordLink()
			throws Exception {

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

		private User _user;

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenUnsettingGroupUsers {

		@Test
		public void groupAdminShouldUnsetGroupAdmin() throws Exception {
			User otherGroupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _groupAdminUser, otherGroupAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), otherGroupAdminUser.getUserId()));
		}

		@Test
		public void groupAdminShouldUnsetGroupOwner() throws Exception {
			_unsetGroupUsers(
				_group.getGroupId(), _groupAdminUser, _groupOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupOwnerUser.getUserId()));
		}

		@Test
		public void groupAdminShouldUnsetOrganizationAdmin() throws Exception {
			_unsetOrganizationUsers(
				_organization.getOrganizationId(), _groupAdminUser,
				_organizationAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					_organizationAdminUser.getUserId()));
		}

		@Test
		public void groupAdminShouldUnsetOrganizationOwner() throws Exception {
			_unsetOrganizationUsers(
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

			_unsetGroupUsers(
				_group.getGroupId(), _groupOwnerUser, groupAdminUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupAdminUser.getUserId()));
		}

		@Test
		public void groupOwnerShouldUnsetGroupOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _groupOwnerUser, groupOwnerUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupOwnerUser.getUserId()));
		}

		@Test
		public void groupOwnerShouldUnsetOrganizationAdmin() throws Exception {
			User organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			_unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationGroupUser,
				organizationAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					organizationAdminUser.getUserId()));
		}

		@Test
		public void groupOwnerShouldUnsetOrganizationOwner() throws Exception {
			User organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);

			_unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationGroupUser,
				organizationOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					organizationOwnerUser.getUserId()));
		}

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = GroupTestUtil.addGroup();

			_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			_organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);

			_organizationGroupUser = UserTestUtil.addGroupOwnerUser(
				_organization.getGroup());
		}

		private Group _group;
		private User _groupAdminUser;
		private User _groupOwnerUser;
		private Organization _organization;
		private User _organizationAdminUser;
		private User _organizationGroupUser;
		private User _organizationOwnerUser;

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenUnsettingOrganizationUsersForNonSiteOrganization {

		@Test
		public void organizationAdminShouldUnsetOrganizationAdmin()
			throws Exception {

			User otherOrganizationAdminUser =
				UserTestUtil.addOrganizationAdminUser(_organization);

			_unsetOrganizationUsers(
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

			_unsetOrganizationUsers(
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

			_unsetOrganizationUsers(
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

			_unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationOwnerUser,
				otherOrganizationOwnerUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					otherOrganizationOwnerUser.getUserId()));
		}

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization();

			_organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			_organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);
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
	public static class WhenUnsettingOrganizationUsersForSiteOrganization {

		@Test
		public void organizationAdminShouldUnsetSiteAdmin() throws Exception {
			_unsetGroupUsers(
				_group.getGroupId(), _organizationAdminUser, _groupAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupAdminUser.getUserId()));
		}

		@Test
		public void organizationAdminShouldUnsetSiteOwner() throws Exception {
			_unsetGroupUsers(
				_group.getGroupId(), _organizationAdminUser, _groupOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupOwnerUser.getUserId()));
		}

		@Test
		public void organizationOwnerShouldUnsetSiteAdmin() throws Exception {
			_unsetGroupUsers(
				_group.getGroupId(), _organizationOwnerUser, _groupAdminUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupAdminUser.getUserId()));
		}

		@Test
		public void organizationOwnerShouldUnsetSiteOwner() throws Exception {
			_unsetGroupUsers(
				_group.getGroupId(), _organizationOwnerUser, _groupOwnerUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), _groupOwnerUser.getUserId()));
		}

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = _organization.getGroup();

			_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			_organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);
		}

		private Group _group;
		private User _groupAdminUser;
		private User _groupOwnerUser;
		private Organization _organization;
		private User _organizationAdminUser;
		private User _organizationOwnerUser;

	}

	private static void _unsetGroupUsers(
			long groupId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = new ServiceContext();

		UserServiceUtil.unsetGroupUsers(
			groupId, new long[] {objectUser.getUserId()}, serviceContext);
	}

	private static void _unsetOrganizationUsers(
			long organizationId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		UserServiceUtil.unsetOrganizationUsers(
			organizationId, new long[] {objectUser.getUserId()});
	}

}