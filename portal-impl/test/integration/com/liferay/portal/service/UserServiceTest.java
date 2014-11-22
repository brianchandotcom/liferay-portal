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

import com.liferay.portal.NoSuchUserException;
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
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
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
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 */
@RunWith(Enclosed.class)
public class UserServiceTest {

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenCompanySecurityStrangersWithMXDisabled {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Test(expected = ReservedUserEmailAddressException.class)
		public void shouldNotAddUser() throws Exception {
			Field field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

			Object value = field.get(null);

			String name = PrincipalThreadLocal.getName();

			try {
				field.set(null, Boolean.FALSE);

				PrincipalThreadLocal.setName(0);

				UserTestUtil.addUser(true);
			}
			finally {
				field.set(null, value);

				PrincipalThreadLocal.setName(name);
			}
		}

		@Test(expected = ReservedUserEmailAddressException.class)
		public void shouldNotUpdateEmailAddress() throws Exception {
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
			}
			finally {
				field.set(null, value);

				PrincipalThreadLocal.setName(name);
			}
		}

		@Test(expected = ReservedUserEmailAddressException.class)
		public void shouldNotUpdateUser() throws Exception {
			Field field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

			Object value = field.get(null);

			String name = PrincipalThreadLocal.getName();

			try {
				field.set(null, Boolean.FALSE);

				User user = UserTestUtil.addUser(false);

				PrincipalThreadLocal.setName(user.getUserId());

				UserTestUtil.updateUser(user);
			}
			finally {
				field.set(null, value);

				PrincipalThreadLocal.setName(name);
			}
		}

	}

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenGettingUserByEmailAddress {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Test(expected = NoSuchUserException.class)
		public void shouldFailIfUserDeleted() throws Exception {
			User user = UserTestUtil.addUser(true);

			UserServiceUtil.deleteUser(user.getUserId());

			UserServiceUtil.getUserByEmailAddress(
				TestPropsValues.getCompanyId(), user.getEmailAddress());
		}

		@Test
		public void shouldReturnUserIfPresent() throws Exception {
			User user = UserTestUtil.addUser(true);

			User retrievedUser = UserServiceUtil.getUserByEmailAddress(
				TestPropsValues.getCompanyId(), user.getEmailAddress());

			Assert.assertEquals(user, retrievedUser);
		}

	}

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenGroupAdminUnsetsGroupUsers {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = GroupTestUtil.addGroup();

			_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);
		}

		@Test
		public void shouldUnsetGroupAdmin() throws Exception {
			User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _groupAdminUser, groupAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupAdminUser.getUserId()));
		}

		@Test
		public void shouldUnsetGroupOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _groupAdminUser, groupOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupOwnerUser.getUserId()));
		}

		@Test
		public void shouldUnsetOrganizationAdmin() throws Exception {
			User organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			_unsetOrganizationUsers(
				_organization.getOrganizationId(), _groupAdminUser,
				organizationAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					organizationAdminUser.getUserId()));
		}

		@Test
		public void shouldUnsetOrganizationOwner() throws Exception {
			User organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);

			_unsetOrganizationUsers(
				_organization.getOrganizationId(), _groupAdminUser,
				organizationOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					organizationOwnerUser.getUserId()));
		}

		private Group _group;
		private User _groupAdminUser;
		private Organization _organization;

	}

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenGroupOwnerUnsetsGroupUsers {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = GroupTestUtil.addGroup();

			_groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_organizationGroupUser = UserTestUtil.addGroupOwnerUser(
				_organization.getGroup());
		}

		@Test
		public void shouldUnsetGroupAdmin() throws Exception {
			User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _groupOwnerUser, groupAdminUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupAdminUser.getUserId()));
		}

		@Test
		public void shouldUnsetGroupOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _groupOwnerUser, groupOwnerUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupOwnerUser.getUserId()));
		}

		@Test
		public void shouldUnsetOrganizationAdmin() throws Exception {
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
		public void shouldUnsetOrganizationOwner() throws Exception {
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

		private Group _group;
		private User _groupOwnerUser;
		private Organization _organization;
		private User _organizationGroupUser;

	}

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenOrganizationAdminUnsetsUsersForNonSiteOrganization {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization();

			_organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			_organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);
		}

		@Test
		public void shouldUnsetOrganizationAdmin() throws Exception {
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
		public void shouldUnsetOrganizationOwner() throws Exception {
			_unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationAdminUser,
				_organizationOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					_organizationOwnerUser.getUserId()));
		}

		private Organization _organization;
		private User _organizationAdminUser;
		private User _organizationOwnerUser;

	}

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenOrganizationAdminUnsetsUsersForSiteOrganization {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Before
		public void setUp() throws Exception {
			Organization organization = OrganizationTestUtil.addOrganization(
				true);

			_group = organization.getGroup();

			_organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				organization);
		}

		@Test
		public void shouldUnsetSiteAdmin() throws Exception {
			User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _organizationAdminUser, groupAdminUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupAdminUser.getUserId()));
		}

		@Test
		public void shouldUnsetSiteOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _organizationAdminUser, groupOwnerUser);

			Assert.assertTrue(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupOwnerUser.getUserId()));
		}

		private Group _group;
		private User _organizationAdminUser;

	}

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenOrganizationOwnerUnsetsUsersForNonSiteOrganization {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization();

			_organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);
		}

		@Test
		public void shouldUnsetOrganizationAdmin() throws Exception {
			User organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			_unsetOrganizationUsers(
				_organization.getOrganizationId(), _organizationOwnerUser,
				organizationAdminUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasOrganizationUser(
					_organization.getOrganizationId(),
					organizationAdminUser.getUserId()));
		}

		@Test
		public void shouldUnsetOrganizationOwner() throws Exception {
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

		private Organization _organization;
		private User _organizationOwnerUser;

	}

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenOrganizationOwnerUnsetsUsersForSiteOrganization {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Before
		public void setUp() throws Exception {
			Organization organization = OrganizationTestUtil.addOrganization(
				true);

			_group = organization.getGroup();

			_organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				organization);
		}

		@Test
		public void shouldUnsetSiteAdmin() throws Exception {
			User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _organizationOwnerUser, groupAdminUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupAdminUser.getUserId()));
		}

		@Test
		public void shouldUnsetSiteOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			_unsetGroupUsers(
				_group.getGroupId(), _organizationOwnerUser, groupOwnerUser);

			Assert.assertFalse(
				UserLocalServiceUtil.hasGroupUser(
					_group.getGroupId(), groupOwnerUser.getUserId()));
		}

		private Group _group;
		private User _organizationOwnerUser;

	}

	@ExecutionTestListeners(
		listeners = {
			ResetDatabaseExecutionTestListener.class,
			SynchronousMailExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenPortalSendsPasswordEmail {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Before
		public void setUp() throws Exception {
			_user = UserTestUtil.addUser();
		}

		@Test
		public void shouldSendNewPasswordEmailByEmailAddress()
			throws Exception {

			givenThatCompanySendsNewPassword();

			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = UserServiceUtil.sendPasswordByEmailAddress(
				_user.getCompanyId(), _user.getEmailAddress());

			Assert.assertTrue(sentPassword);
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

			boolean sentPassword = UserServiceUtil.sendPasswordByScreenName(
				_user.getCompanyId(), _user.getScreenName());

			Assert.assertTrue(sentPassword);
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

			boolean sentPassword = UserServiceUtil.sendPasswordByUserId(
				_user.getUserId());

			Assert.assertTrue(sentPassword);
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

			boolean sentPassword = UserServiceUtil.sendPasswordByEmailAddress(
				_user.getCompanyId(), _user.getEmailAddress());

			Assert.assertFalse(sentPassword);
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

			boolean sentPassword = UserServiceUtil.sendPasswordByScreenName(
				_user.getCompanyId(), _user.getScreenName());

			Assert.assertFalse(sentPassword);
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

			boolean sentPassword = UserServiceUtil.sendPasswordByUserId(
				_user.getUserId());

			Assert.assertFalse(sentPassword);
			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_reset_body.tmpl"));
		}

		protected void givenThatCompanySendsNewPassword() throws Exception {
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