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

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.test.MailServiceTestUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 */
@RunWith(Enclosed.class)
public class UserServiceTest {

	public static class WhenCompanySecurityStrangersWithMXDisabled {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Test(expected = UserEmailAddressException.MustNotUseCompanyMx.class)
		public void shouldNotAddUser() throws Exception {
			String name = PrincipalThreadLocal.getName();

			try {
				PropsUtil.set(
					PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX,
					Boolean.FALSE.toString());

				PrincipalThreadLocal.setName(0);

				UserTestUtil.addUser(true);
			}
			finally {
				PrincipalThreadLocal.setName(name);
			}
		}

		@Test(expected = UserEmailAddressException.MustNotUseCompanyMx.class)
		public void shouldNotUpdateEmailAddress() throws Exception {
			String name = PrincipalThreadLocal.getName();

			try {
				PropsUtil.set(
					PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX,
					Boolean.FALSE.toString());

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
				PrincipalThreadLocal.setName(name);
			}
		}

		@Test(expected = UserEmailAddressException.MustNotUseCompanyMx.class)
		public void shouldNotUpdateUser() throws Exception {
			String name = PrincipalThreadLocal.getName();

			User user = UserTestUtil.addUser(false);

			try {
				PropsUtil.set(
					PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX,
					Boolean.FALSE.toString());

				PrincipalThreadLocal.setName(user.getUserId());

				UserTestUtil.updateUser(user);
			}
			finally {
				PrincipalThreadLocal.setName(name);

				UserLocalServiceUtil.deleteUser(user);
			}
		}

	}

	public static class WhenGettingUserByEmailAddress {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

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

			try {
				User retrievedUser = UserServiceUtil.getUserByEmailAddress(
					TestPropsValues.getCompanyId(), user.getEmailAddress());

				Assert.assertEquals(user, retrievedUser);
			}
			finally {
				UserLocalServiceUtil.deleteUser(user);
			}
		}

	}

	public static class WhenGroupAdminUnsetsGroupUsers {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = GroupTestUtil.addGroup();

			_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);
		}

		@Test
		public void shouldUnsetGroupAdmin() throws Exception {
			User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			try {
				_unsetGroupUsers(
					_group.getGroupId(), _groupAdminUser, groupAdminUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasGroupUser(
						_group.getGroupId(), groupAdminUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(groupAdminUser);
			}
		}

		@Test
		public void shouldUnsetGroupOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			try {
				_unsetGroupUsers(
					_group.getGroupId(), _groupAdminUser, groupOwnerUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasGroupUser(
						_group.getGroupId(), groupOwnerUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(groupOwnerUser);
			}
		}

		@Test
		public void shouldUnsetOrganizationAdmin() throws Exception {
			User organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			try {
				_unsetOrganizationUsers(
					_organization.getOrganizationId(), _groupAdminUser,
					organizationAdminUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasOrganizationUser(
						_organization.getOrganizationId(),
						organizationAdminUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(organizationAdminUser);
			}
		}

		@Test
		public void shouldUnsetOrganizationOwner() throws Exception {
			User organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);

			try {
				_unsetOrganizationUsers(
					_organization.getOrganizationId(), _groupAdminUser,
					organizationOwnerUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasOrganizationUser(
						_organization.getOrganizationId(),
						organizationOwnerUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(organizationOwnerUser);
			}
		}

		@After
		public void tearDown() throws PortalException {
			UserLocalServiceUtil.deleteUser(_groupAdminUser);

			GroupLocalServiceUtil.deleteGroup(_group);

			OrganizationLocalServiceUtil.deleteOrganization(_organization);
		}

		private Group _group;
		private User _groupAdminUser;
		private Organization _organization;

	}

	public static class WhenGroupOwnerUnsetsGroupUsers {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

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

			try {
				_unsetGroupUsers(
					_group.getGroupId(), _groupOwnerUser, groupAdminUser);

				Assert.assertFalse(
					UserLocalServiceUtil.hasGroupUser(
						_group.getGroupId(), groupAdminUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(groupAdminUser);
			}
		}

		@Test
		public void shouldUnsetGroupOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			try {
				_unsetGroupUsers(
					_group.getGroupId(), _groupOwnerUser, groupOwnerUser);

				Assert.assertFalse(
					UserLocalServiceUtil.hasGroupUser(
						_group.getGroupId(), groupOwnerUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(groupOwnerUser);
			}
		}

		@Test
		public void shouldUnsetOrganizationAdmin() throws Exception {
			User organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);

			try {
				_unsetOrganizationUsers(
					_organization.getOrganizationId(), _organizationGroupUser,
					organizationAdminUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasOrganizationUser(
						_organization.getOrganizationId(),
						organizationAdminUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(organizationAdminUser);
			}
		}

		@Test
		public void shouldUnsetOrganizationOwner() throws Exception {
			User organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);

			try {
				_unsetOrganizationUsers(
					_organization.getOrganizationId(), _organizationGroupUser,
					organizationOwnerUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasOrganizationUser(
						_organization.getOrganizationId(),
						organizationOwnerUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(organizationOwnerUser);
			}
		}

		@After
		public void tearDown() throws PortalException {
			UserLocalServiceUtil.deleteUser(_organizationGroupUser);

			UserLocalServiceUtil.deleteUser(_groupOwnerUser);

			GroupLocalServiceUtil.deleteGroup(_group);

			OrganizationLocalServiceUtil.deleteOrganization(_organization);
		}

		private Group _group;
		private User _groupOwnerUser;
		private Organization _organization;
		private User _organizationGroupUser;

	}

	public static class WhenOrganizationAdminUnsetsUsersForNonSiteOrganization {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

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

			try {
				_unsetOrganizationUsers(
					_organization.getOrganizationId(), _organizationAdminUser,
					otherOrganizationAdminUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasOrganizationUser(
						_organization.getOrganizationId(),
						otherOrganizationAdminUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(otherOrganizationAdminUser);
			}
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

		@After
		public void tearDown() throws PortalException {
			UserLocalServiceUtil.deleteUser(_organizationAdminUser);
			UserLocalServiceUtil.deleteUser(_organizationOwnerUser);

			OrganizationLocalServiceUtil.deleteOrganization(_organization);
		}

		private Organization _organization;
		private User _organizationAdminUser;
		private User _organizationOwnerUser;

	}

	public static class WhenOrganizationAdminUnsetsUsersForSiteOrganization {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = _organization.getGroup();

			_organizationAdminUser = UserTestUtil.addOrganizationAdminUser(
				_organization);
		}

		@Test
		public void shouldUnsetSiteAdmin() throws Exception {
			User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			try {
				_unsetGroupUsers(
					_group.getGroupId(), _organizationAdminUser,
					groupAdminUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasGroupUser(
						_group.getGroupId(), groupAdminUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(groupAdminUser);
			}
		}

		@Test
		public void shouldUnsetSiteOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			try {
				_unsetGroupUsers(
					_group.getGroupId(), _organizationAdminUser,
					groupOwnerUser);

				Assert.assertTrue(
					UserLocalServiceUtil.hasGroupUser(
						_group.getGroupId(), groupOwnerUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(groupOwnerUser);
			}
		}

		@After
		public void tearDown() throws PortalException {
			UserLocalServiceUtil.deleteUser(_organizationAdminUser);

			OrganizationLocalServiceUtil.deleteOrganization(_organization);
		}

		private Group _group;
		private Organization _organization;
		private User _organizationAdminUser;

	}

	public static class WhenOrganizationOwnerUnsetsUsersForNonSiteOrganization {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

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

			try {
				_unsetOrganizationUsers(
					_organization.getOrganizationId(), _organizationOwnerUser,
					organizationAdminUser);

				Assert.assertFalse(
					UserLocalServiceUtil.hasOrganizationUser(
						_organization.getOrganizationId(),
						organizationAdminUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(organizationAdminUser);
			}
		}

		@Test
		public void shouldUnsetOrganizationOwner() throws Exception {
			User otherOrganizationOwnerUser =
				UserTestUtil.addOrganizationOwnerUser(_organization);

			try {
				_unsetOrganizationUsers(
					_organization.getOrganizationId(), _organizationOwnerUser,
					otherOrganizationOwnerUser);

				Assert.assertFalse(
					UserLocalServiceUtil.hasOrganizationUser(
						_organization.getOrganizationId(),
						otherOrganizationOwnerUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(otherOrganizationOwnerUser);
			}
		}

		@After
		public void tearDown() throws PortalException {
			UserLocalServiceUtil.deleteUser(_organizationOwnerUser);

			OrganizationLocalServiceUtil.deleteOrganization(_organization);
		}

		private Organization _organization;
		private User _organizationOwnerUser;

	}

	public static class WhenOrganizationOwnerUnsetsUsersForSiteOrganization {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_organization = OrganizationTestUtil.addOrganization(true);

			_group = _organization.getGroup();

			_organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
				_organization);
		}

		@Test
		public void shouldUnsetSiteAdmin() throws Exception {
			User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

			try {
				_unsetGroupUsers(
					_group.getGroupId(), _organizationOwnerUser,
					groupAdminUser);

				Assert.assertFalse(
					UserLocalServiceUtil.hasGroupUser(
						_group.getGroupId(), groupAdminUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(groupAdminUser);
			}
		}

		@Test
		public void shouldUnsetSiteOwner() throws Exception {
			User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

			try {
				_unsetGroupUsers(
					_group.getGroupId(), _organizationOwnerUser,
					groupOwnerUser);

				Assert.assertFalse(
					UserLocalServiceUtil.hasGroupUser(
						_group.getGroupId(), groupOwnerUser.getUserId()));
			}
			finally {
				UserLocalServiceUtil.deleteUser(groupOwnerUser);
			}
		}

		@After
		public void tearDown() throws PortalException {
			UserLocalServiceUtil.deleteUser(_organizationOwnerUser);

			OrganizationLocalServiceUtil.deleteOrganization(_organization);
		}

		private Group _group;
		private Organization _organization;
		private User _organizationOwnerUser;

	}

	@Sync
	public static class WhenPortalSendsPasswordEmail {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				SynchronousMailTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_user = UserTestUtil.addUser();
		}

		@Test
		public void shouldSendNewPasswordEmailByEmailAddress()
			throws Exception {

			PortletPreferences portletPreferences =
				givenThatCompanySendsNewPassword();

			try {
				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				boolean sentPassword =
					UserServiceUtil.sendPasswordByEmailAddress(
						_user.getCompanyId(), _user.getEmailAddress());

				Assert.assertTrue(sentPassword);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
				Assert.assertTrue(
					MailServiceTestUtil.lastMailMessageContains(
						"email_password_sent_body.tmpl"));
			}
			finally {
				restorePortletPreferences(portletPreferences);
			}
		}

		@Test
		public void shouldSendNewPasswordEmailByScreenName() throws Exception {
			PortletPreferences portletPreferences =
				givenThatCompanySendsNewPassword();

			try {
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
			finally {
				restorePortletPreferences(portletPreferences);
			}
		}

		@Test
		public void shouldSendNewPasswordEmailByUserId() throws Exception {
			PortletPreferences portletPreferences =
				givenThatCompanySendsNewPassword();

			try {
				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				boolean sentPassword = UserServiceUtil.sendPasswordByUserId(
					_user.getUserId());

				Assert.assertTrue(sentPassword);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
				Assert.assertTrue(
					MailServiceTestUtil.lastMailMessageContains(
						"email_password_sent_body.tmpl"));
			}
			finally {
				restorePortletPreferences(portletPreferences);
			}
		}

		@Test
		public void shouldSendResetLinkEmailByEmailAddress() throws Exception {
			PortletPreferences portletPreferences =
				givenThatCompanySendsResetPasswordLink();

			try {
				int initialInboxSize = MailServiceTestUtil.getInboxSize();

				boolean sentPassword =
					UserServiceUtil.sendPasswordByEmailAddress(
						_user.getCompanyId(), _user.getEmailAddress());

				Assert.assertFalse(sentPassword);
				Assert.assertEquals(
					initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
				Assert.assertTrue(
					MailServiceTestUtil.lastMailMessageContains(
						"email_password_reset_body.tmpl"));
			}
			finally {
				restorePortletPreferences(portletPreferences);
			}
		}

		@Test
		public void shouldSendResetLinkEmailByScreenName() throws Exception {
			PortletPreferences portletPreferences =
				givenThatCompanySendsResetPasswordLink();

			try {
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
			finally {
				restorePortletPreferences(portletPreferences);
			}
		}

		@Test
		public void shouldSendResetLinkEmailByUserId() throws Exception {
			PortletPreferences portletPreferences =
				givenThatCompanySendsResetPasswordLink();

			try {
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
			finally {
				restorePortletPreferences(portletPreferences);
			}
		}

		@After
		public void tearDown() throws PortalException {
			UserLocalServiceUtil.deleteUser(_user);
		}

		protected PortletPreferences givenThatCompanySendsNewPassword()
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

			return portletPreferences;
		}

		protected PortletPreferences givenThatCompanySendsResetPasswordLink()
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

			return portletPreferences;
		}

		protected void restorePortletPreferences(
				PortletPreferences portletPreferences)
			throws Exception {

			portletPreferences.reset(PropsKeys.COMPANY_SECURITY_SEND_PASSWORD);
			portletPreferences.reset(
				PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK);

			portletPreferences.store();
		}

		private User _user;

	}

	public static class WhenUpdatingUser {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Test
		public void shouldNotRemoveChildGroupAssociation() throws Exception {
			User user = UserTestUtil.addUser(true);

			List<Group> groups = new ArrayList<>();

			Group parentGroup = GroupTestUtil.addGroup();

			groups.add(parentGroup);

			Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

			childGroup.setMembershipRestriction(
				GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS);

			GroupLocalServiceUtil.updateGroup(childGroup);

			groups.add(childGroup);

			GroupLocalServiceUtil.addUserGroups(user.getUserId(), groups);

			user = _updateUser(user);

			Assert.assertEquals(groups, user.getGroups());
		}

		private User _updateUser(User user) throws Exception {
			Contact contact = user.getContact();

			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

			birthdayCal.setTime(contact.getBirthday());

			int birthdayMonth = birthdayCal.get(Calendar.MONTH);
			int birthdayDay = birthdayCal.get(Calendar.DATE);
			int birthdayYear = birthdayCal.get(Calendar.YEAR);

			long[] groupIds = null;
			long[] organizationIds = null;
			long[] roleIds = null;
			List<UserGroupRole> userGroupRoles = null;
			long[] userGroupIds = null;
			ServiceContext serviceContext = new ServiceContext();

			return UserServiceUtil.updateUser(
				user.getUserId(), user.getPassword(), StringPool.BLANK,
				StringPool.BLANK, user.isPasswordReset(),
				user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
				user.getScreenName(), user.getEmailAddress(),
				user.getFacebookId(), user.getOpenId(), user.getLanguageId(),
				user.getTimeZoneId(), user.getGreeting(), user.getComments(),
				contact.getFirstName(), contact.getMiddleName(),
				contact.getLastName(), contact.getPrefixId(),
				contact.getSuffixId(), contact.isMale(), birthdayMonth,
				birthdayDay, birthdayYear, contact.getSmsSn(),
				contact.getFacebookSn(), contact.getJabberSn(),
				contact.getSkypeSn(), contact.getTwitterSn(),
				contact.getJobTitle(), groupIds, organizationIds, roleIds,
				userGroupRoles, userGroupIds, serviceContext);
		}

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