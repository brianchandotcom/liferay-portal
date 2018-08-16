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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.auth.DefaultScreenNameValidator;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.auth.ScreenNameValidator;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.auth.ScreenNameValidatorFactory;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

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
 * @author Drew Brokke
 */
@RunWith(Enclosed.class)
public class UserServiceTest {

	public static class WhenAddingOrRemovingPasswordPolicyUsers {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setUserId(TestPropsValues.getUserId());

			_defaultPasswordPolicy = PasswordPolicyTestUtil.addPasswordPolicy(
				serviceContext, true);

			_defaultPasswordPolicy.setChangeable(true);
			_defaultPasswordPolicy.setChangeRequired(true);

			_defaultPasswordPolicy =
				PasswordPolicyLocalServiceUtil.updatePasswordPolicy(
					_defaultPasswordPolicy);

			_testPasswordPolicy = PasswordPolicyTestUtil.addPasswordPolicy(
				serviceContext);

			_testPasswordPolicy.setChangeable(false);
			_testPasswordPolicy.setChangeRequired(false);

			_testPasswordPolicy =
				PasswordPolicyLocalServiceUtil.updatePasswordPolicy(
					_testPasswordPolicy);
		}

		@Test
		public void shouldRemovePasswordResetIfPolicyDoesNotAllowChanging()
			throws Exception {

			_user = UserTestUtil.addUser();

			Assert.assertEquals(
				_defaultPasswordPolicy, _user.getPasswordPolicy());

			Assert.assertTrue(_user.isPasswordReset());

			long[] users = {_user.getUserId()};

			UserLocalServiceUtil.addPasswordPolicyUsers(
				_testPasswordPolicy.getPasswordPolicyId(), users);

			_user = UserLocalServiceUtil.getUser(_user.getUserId());

			Assert.assertFalse(_user.isPasswordReset());
		}

		@After
		public void tearDown() throws Exception {
			_defaultPasswordPolicy.setDefaultPolicy(false);

			PasswordPolicyLocalServiceUtil.updatePasswordPolicy(
				_defaultPasswordPolicy);
		}

		@DeleteAfterTestRun
		private PasswordPolicy _defaultPasswordPolicy;

		@DeleteAfterTestRun
		private PasswordPolicy _testPasswordPolicy;

		@DeleteAfterTestRun
		private User _user;

	}

	public static class WhenAddingUserWithDefaultSitesEnabled {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_group = GroupTestUtil.addGroup();

			UnicodeProperties properties = new UnicodeProperties();

			properties.put(
				PropsKeys.ADMIN_DEFAULT_GROUP_NAMES,
				_group.getDescriptiveName());

			_organization = OrganizationTestUtil.addOrganization(true);

			Group organizationGroup = _organization.getGroup();

			properties.put(
				PropsKeys.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES,
				organizationGroup.getDescriptiveName());

			CompanyLocalServiceUtil.updatePreferences(
				_group.getCompanyId(), properties);

			UnicodeProperties typeSettingsProperties =
				_group.getTypeSettingsProperties();

			_siteRole = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

			typeSettingsProperties.put(
				"defaultSiteRoleIds", String.valueOf(_siteRole.getRoleId()));

			GroupLocalServiceUtil.updateGroup(
				_group.getGroupId(), typeSettingsProperties.toString());

			_user = UserTestUtil.addUser();
		}

		@Test
		public void shouldInheritDefaultOrganizationSiteMembership() {
			Group organizationGroup = _organization.getGroup();

			long organizationGroupId = organizationGroup.getGroupId();

			Assert.assertTrue(
				ArrayUtil.contains(_user.getGroupIds(), organizationGroupId));
		}

		@Test
		public void shouldInheritDefaultSiteRolesFromDefaultSite()
			throws Exception {

			Assert.assertTrue(
				ArrayUtil.contains(_user.getGroupIds(), _group.getGroupId()));

			List<UserGroupRole> userGroupRoles =
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(
					_user.getUserId(), _group.getGroupId());

			Assert.assertEquals(
				userGroupRoles.toString(), 1, userGroupRoles.size());

			UserGroupRole userGroupRole = userGroupRoles.get(0);

			Assert.assertEquals(
				_siteRole.getRoleId(), userGroupRole.getRoleId());
		}

		@DeleteAfterTestRun
		private Group _group;

		@DeleteAfterTestRun
		private Organization _organization;

		@DeleteAfterTestRun
		private Role _siteRole;

		@DeleteAfterTestRun
		private User _user;

	}

	public static class WhenAddingUserWithNumericScreenName {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Test
		public void shouldAddUser() throws Exception {
			long numericScreenName = RandomTestUtil.nextLong();

			_user = UserTestUtil.addUser(String.valueOf(numericScreenName));

			Assert.assertEquals(
				String.valueOf(numericScreenName), _user.getScreenName());
		}

		@Test
		public void shouldAddUserWhenScreenNameMatchesExistingGroupId()
			throws Exception {

			_group = GroupTestUtil.addGroup();

			_user = UserTestUtil.addUser(String.valueOf(_group.getGroupId()));

			Assert.assertEquals(
				String.valueOf(_group.getGroupId()), _user.getScreenName());
		}

		@Test(expected = UserScreenNameException.MustNotBeNumeric.class)
		public void shouldThrowException() throws Exception {
			PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC = false;

			UserTestUtil.addUser(String.valueOf(RandomTestUtil.nextLong()));
		}

		@After
		public void tearDown() throws Exception {
			PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_ALLOW_NUMERIC));
		}

		@DeleteAfterTestRun
		private Group _group;

		@DeleteAfterTestRun
		private User _user;

	}

	public static class WhenAddingUserWithSpecialCharactersScreenName {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_screenNameValidator = ScreenNameValidatorFactory.getInstance();

			if (_screenNameValidator instanceof DefaultScreenNameValidator) {
				_originalSpecialCharacters = ReflectionTestUtil.getFieldValue(
					_screenNameValidator, _FIELD_KEY);

				ReflectionTestUtil.setFieldValue(
					_screenNameValidator, _FIELD_KEY, _SPECIAL_CHARACTERS);
			}
		}

		@Test
		public void shouldNormalizeTheFriendlyURL() throws Exception {
			User user1 = UserTestUtil.addUser("contains-hyphens");

			_users.add(user1);

			Assert.assertEquals("/contains-hyphens", _getFriendlyURL(user1));

			User user2 = UserTestUtil.addUser("contains.periods");

			_users.add(user2);

			Assert.assertEquals("/contains.periods", _getFriendlyURL(user2));

			User user3 = UserTestUtil.addUser("contains_underscores");

			_users.add(user3);

			Assert.assertEquals(
				"/contains_underscores", _getFriendlyURL(user3));

			User user4 = UserTestUtil.addUser("contains'apostrophes");

			_users.add(user4);

			Assert.assertEquals(
				"/contains-apostrophes", _getFriendlyURL(user4));

			User user5 = UserTestUtil.addUser("contains#pounds");

			_users.add(user5);

			Assert.assertEquals("/contains-pounds", _getFriendlyURL(user5));
		}

		@After
		public void tearDown() throws Exception {
			if (_screenNameValidator instanceof DefaultScreenNameValidator) {
				ReflectionTestUtil.setFieldValue(
					_screenNameValidator, _FIELD_KEY,
					_originalSpecialCharacters);
			}
		}

		private String _getFriendlyURL(User user) {
			Group group = user.getGroup();

			return group.getFriendlyURL();
		}

		private static final String _FIELD_KEY = "_specialChars";

		private static final String _SPECIAL_CHARACTERS = "-._\\'#";

		private String _originalSpecialCharacters;
		private ScreenNameValidator _screenNameValidator;

		@DeleteAfterTestRun
		private final List<User> _users = new ArrayList();

	}

	public static class WhenCallingGetGtUsersMethods {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				PermissionCheckerTestRule.INSTANCE);

		@Test
		public void testGetGtCompanyUsers() throws Exception {
			for (int i = 0; i < 10; i++) {
				_users.add(UserTestUtil.addUser());
			}

			int size = 5;

			_assert(
				size,
				gtUserId -> UserServiceUtil.getGtCompanyUsers(
					gtUserId, TestPropsValues.getCompanyId(), size));
		}

		@Test
		public void testGetGtOrganizationUsers() throws Exception {
			_organization = OrganizationTestUtil.addOrganization();

			for (int i = 0; i < 10; i++) {
				_users.add(
					UserTestUtil.addOrganizationUser(
						_organization, RoleConstants.ORGANIZATION_USER));
			}

			int size = 5;

			_assert(
				size,
				gtUserId -> UserServiceUtil.getGtOrganizationUsers(
					gtUserId, _organization.getOrganizationId(), size));
		}

		@Test
		public void testGetGtUserGroupUsers() throws Exception {
			_userGroup = UserGroupTestUtil.addUserGroup();

			long[] userIds = new long[10];

			for (int i = 0; i < userIds.length; i++) {
				User user = UserTestUtil.addUser();

				_users.add(user);

				userIds[i] = user.getUserId();
			}

			UserLocalServiceUtil.setUserGroupUsers(
				_userGroup.getUserGroupId(), userIds);

			int size = 5;

			_assert(
				size,
				gtUserId -> UserServiceUtil.getGtUserGroupUsers(
					gtUserId, _userGroup.getUserGroupId(), size));
		}

		private void _assert(
				int size,
				UnsafeFunction<Long, List<User>, Exception> unsafeFunction)
			throws Exception {

			List<User> users = unsafeFunction.apply(0L);

			Assert.assertFalse(users.isEmpty());
			Assert.assertEquals(users.toString(), size, users.size());

			User lastUser = users.get(users.size() - 1);

			users = unsafeFunction.apply(lastUser.getUserId());

			Assert.assertFalse(users.isEmpty());
			Assert.assertEquals(users.toString(), size, users.size());

			long previousUserId = 0;

			for (User user : users) {
				long userId = user.getUserId();

				Assert.assertTrue(userId > lastUser.getUserId());
				Assert.assertTrue(userId > previousUserId);

				previousUserId = userId;
			}
		}

		@DeleteAfterTestRun
		private Organization _organization;

		@DeleteAfterTestRun
		private UserGroup _userGroup;

		@DeleteAfterTestRun
		private final List<User> _users = new ArrayList<>();

	}

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

		@DeleteAfterTestRun
		private Group _group;

		@DeleteAfterTestRun
		private User _groupAdminUser;

		@DeleteAfterTestRun
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

		@DeleteAfterTestRun
		private Group _group;

		@DeleteAfterTestRun
		private User _groupOwnerUser;

		@DeleteAfterTestRun
		private Organization _organization;

		@DeleteAfterTestRun
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

		@DeleteAfterTestRun
		private Organization _organization;

		@DeleteAfterTestRun
		private User _organizationAdminUser;

		@DeleteAfterTestRun
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

		private Group _group;

		@DeleteAfterTestRun
		private Organization _organization;

		@DeleteAfterTestRun
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

		@DeleteAfterTestRun
		private Organization _organization;

		@DeleteAfterTestRun
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

		private Group _group;

		@DeleteAfterTestRun
		private Organization _organization;

		@DeleteAfterTestRun
		private User _organizationOwnerUser;

	}

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

		@DeleteAfterTestRun
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