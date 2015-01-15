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

package com.liferay.portal.util.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Alberto Chaparro
 * @author Manuel de la Peña
 * @author Sampsa Sohlman
 */
public class UserTestUtil {

	public static User addCompanyAdminUser(Company company) throws Exception {
		User user = addUser();

		user.setCompanyId(company.getCompanyId());

		UserLocalServiceUtil.updateUser(user);

		Role role = RoleLocalServiceUtil.getRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);

		return user;
	}

	public static User addGroupAdminUser(Group group) throws Exception {
		return UserTestUtil.addGroupUser(
			group, RoleConstants.SITE_ADMINISTRATOR);
	}

	public static User addGroupOwnerUser(Group group) throws Exception {
		return UserTestUtil.addGroupUser(group, RoleConstants.SITE_OWNER);
	}

	public static User addGroupUser(Group group, String roleName)
		throws Exception {

		User groupUser = addUser(
			RandomTestUtil.randomString(), group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		long[] userIds = {groupUser.getUserId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, group.getGroupId(), role.getRoleId());

		return groupUser;
	}

	public static User addOmniAdminUser() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompanyByMx(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		return addCompanyAdminUser(company);
	}

	public static User addOrganizationAdminUser(Organization organization)
		throws Exception {

		return UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_ADMINISTRATOR);
	}

	public static User addOrganizationOwnerUser(Organization organization)
		throws Exception {

		return UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_OWNER);
	}

	public static User addOrganizationUser(
			Organization organization, String roleName)
		throws Exception {

		User organizationUser = addUser(
			RandomTestUtil.randomString(), organization.getGroupId());

		long[] userIds = {organizationUser.getUserId()};

		UserLocalServiceUtil.addOrganizationUsers(
			organization.getOrganizationId(), userIds);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, organization.getGroupId(), role.getRoleId());

		return organizationUser;
	}

	public static User addUser() throws Exception {
		return addUser(
			RandomTestUtil.randomString(), TestPropsValues.getGroupId());
	}

	public static User addUser(boolean secure) throws Exception {
		BaseAddUserCommand addUserCommand;
		String domain;

		if (secure) {
			addUserCommand = new AddUserCommand();
			domain = "liferay.com";
		}
		else {
			addUserCommand = new AddUserAsCreatorCommand(
				TestPropsValues.getUserId());
			domain = "test.com";
		}

		addUserCommand.autoScreenName = true;
		addUserCommand.locale = LocaleUtil.getDefault();

		User user = createUser();

		user.setCompanyId(TestPropsValues.getCompanyId());
		user.setEmailAddress(
			"UserServiceTest." + RandomTestUtil.nextLong() + "@" + domain);
		user.setScreenName(StringPool.BLANK);
		user.setFirstName("UserServiceTest");
		user.setLastName("UserServiceTest");

		return addUserCommand.copy(user, new ServiceContext());
	}

	public static User addUser(long groupId, Locale locale) throws Exception {
		return addUser(
			RandomTestUtil.randomString(), false, locale,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {groupId});
	}

	public static User addUser(
			long companyId, long userId, String screenName,
			boolean autoScreenName, Locale locale, String firstName,
			String lastName, long[] groupIds, ServiceContext serviceContext)
		throws Exception {

		User user = UserLocalServiceUtil.fetchUserByScreenName(
			companyId, screenName);

		if (user != null) {
			return user;
		}

		user = createUser();

		user.setCompanyId(companyId);
		user.setScreenName(screenName);
		user.setFirstName(firstName);
		user.setLastName(lastName);

		BaseAddUserCommand addUserCommand = new AddUserAsCreatorCommand(userId);

		addUserCommand.autoScreenName = autoScreenName;
		addUserCommand.groupIds = groupIds;
		addUserCommand.locale = locale;

		return addUserCommand.copy(user, serviceContext);
	}

	public static User addUser(
			String screenName, boolean autoScreenName, Locale locale,
			String firstName, String lastName, long[] groupIds)
		throws Exception {

		return addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			screenName, autoScreenName, locale, firstName, lastName, groupIds,
			ServiceContextTestUtil.getServiceContext());
	}

	public static User addUser(
			String screenName, boolean autoScreenName, long[] groupIds)
		throws Exception {

		return addUser(
			screenName, autoScreenName, "ServiceTestSuite", "ServiceTestSuite",
			groupIds);
	}

	public static User addUser(
			String screenName, boolean autoScreenName, String firstName,
			String lastName, long[] groupIds)
		throws Exception {

		return addUser(
			screenName, autoScreenName, LocaleUtil.getDefault(), firstName,
			lastName, groupIds);
	}

	public static User addUser(String screenName, long groupId)
		throws Exception {

		if (Validator.isNull(screenName)) {
			return addUser(null, true, new long[] {groupId});
		}
		else {
			return addUser(screenName, false, new long[] {groupId});
		}
	}

	public static User createUser() throws Exception {
		User user = UserLocalServiceUtil.createUser(0);

		user.setPasswordUnencrypted(StringPool.BLANK);
		user.setEmailAddress(
			RandomTestUtil.randomString() + RandomTestUtil.nextLong() +
				"@liferay.com");
		user.setFacebookId(0);
		user.setOpenId(StringPool.BLANK);
		user.setMiddleName(StringPool.BLANK);
		user.setJobTitle(StringPool.BLANK);

		return user;
	}

	public static User getAdminUser(long companyId) throws PortalException {
		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		List<User> users = UserLocalServiceUtil.getRoleUsers(
			role.getRoleId(), 0, 1);

		if (!users.isEmpty()) {
			return users.get(0);
		}

		return null;
	}

	public static User updateUser(User user) throws Exception {
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

	public static class AddUserAsCreatorCommand extends BaseAddUserCommand {

		public AddUserAsCreatorCommand(long creatorUserId) {
			_creatorUserId = creatorUserId;
		}

		@Override
		public User copy(User user, ServiceContext serviceContext)
			throws PortalException {

			return UserLocalServiceUtil.addUser(
				_creatorUserId, user.getCompanyId(), autoPassword,
				user.getPasswordUnencrypted(), user.getPasswordUnencrypted(),
				autoScreenName, user.getScreenName(), user.getEmailAddress(),
				user.getFacebookId(), user.getOpenId(), locale,
				user.getFirstName(), user.getMiddleName(), user.getLastName(),
				prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, user.getJobTitle(), groupIds, organizationIds,
				roleIds, userGroupIds, sendMail, serviceContext);
		}

		private final long _creatorUserId;

	}

	public static class AddUserCommand extends BaseAddUserCommand {

		@Override
		public User copy(User user, ServiceContext serviceContext)
			throws PortalException {

			return UserServiceUtil.addUser(
				user.getCompanyId(), autoPassword,
				user.getPasswordUnencrypted(), user.getPasswordUnencrypted(),
				autoScreenName, user.getScreenName(), user.getEmailAddress(),
				user.getFacebookId(), user.getOpenId(), locale,
				user.getFirstName(), user.getMiddleName(), user.getLastName(),
				prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, user.getJobTitle(), groupIds, organizationIds,
				roleIds, userGroupIds, sendMail, serviceContext);
		}

	}

	protected abstract static class BaseAddUserCommand {

		public abstract User copy(User user, ServiceContext serviceContext)
			throws PortalException;

		public boolean autoPassword = true;
		public boolean autoScreenName;
		public int birthdayDay = 1;
		public int birthdayMonth = Calendar.JANUARY;
		public int birthdayYear = 1970;
		public long[] groupIds;
		public Locale locale;
		public boolean male = true;
		public long[] organizationIds;
		public int prefixId;
		public long[] roleIds;
		public boolean sendMail;
		public int suffixId;
		public long[] userGroupIds;

	}

}