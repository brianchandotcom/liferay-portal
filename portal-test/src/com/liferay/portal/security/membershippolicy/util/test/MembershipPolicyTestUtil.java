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

package com.liferay.portal.security.membershippolicy.util.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class MembershipPolicyTestUtil {

	// All these methods are not using UserTestUtil.java or GroupTestUtil.java
	// because we need to call the Remote Service in order to verify the
	// Membership Policies

	public static Group addGroup() throws Exception {
		String name = RandomTestUtil.randomString();
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		return GroupServiceUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, "This is a test group",
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, true,
			true, populateServiceContext(Group.class, true));
	}

	public static Organization addOrganization() throws Exception {
		String name = RandomTestUtil.randomString();

		return OrganizationServiceUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, name,
			OrganizationConstants.TYPE_REGULAR_ORGANIZATION, 0, 0,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			false, populateServiceContext(Organization.class, true));
	}

	public static Role addRole(int type) throws Exception {
		String name = RandomTestUtil.randomString();

		return RoleServiceUtil.addRole(
			null, 0, name, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), type,
			RandomTestUtil.randomString(),
			populateServiceContext(Role.class, false));
	}

	public static User addUser(
			long[] organizationIds, long[] roleIds, long[] siteIds,
			long[] userGroupIds)
		throws Exception {

		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress =
			"UserServiceTest." + RandomTestUtil.nextLong() + "@liferay.com";
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
		boolean sendMail = false;

		ServiceContext serviceContext = new ServiceContext();

		return UserServiceUtil.addUser(
			TestPropsValues.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, siteIds,
			organizationIds, roleIds, userGroupIds, sendMail, serviceContext);
	}

	public static UserGroup addUserGroup() throws Exception {
		String name = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString(50);

		return UserGroupServiceUtil.addUserGroup(
			name, description, populateServiceContext(UserGroup.class, false));
	}

	public static void updateUser(
			User user, long[] organizationIds, long[] roleIds, long[] siteIds,
			long[] userGroupIds, List<UserGroupRole> userGroupRoles)
		throws Exception {

		long userId = user.getUserId();
		String oldPassword = user.getPassword();
		String newPassword1 = RandomTestUtil.randomString();
		String newPassword2 = newPassword1;
		boolean passwordReset = true;
		String reminderQueryQuestion = RandomTestUtil.randomString();
		String reminderQueryAnswer = RandomTestUtil.randomString();

		String screenName = RandomTestUtil.randomString();
		String emailAddress =
			"UserServiceTest." + RandomTestUtil.nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String languageId = LocaleUtil.toLanguageId(Locale.getDefault());
		String timeZoneId = RandomTestUtil.randomString();
		String greeting = RandomTestUtil.randomString();
		String comments = RandomTestUtil.randomString();
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
		String smsSn =
			"UserServiceTestSmsSn." + RandomTestUtil.nextInt() + "@liferay.com";
		String aimSn = RandomTestUtil.randomString();
		String facebookSn = RandomTestUtil.randomString();
		String icqSn = RandomTestUtil.randomString();
		String jabberSn = RandomTestUtil.randomString();
		String msnSn =
			"UserServiceTestMsnSn." + RandomTestUtil.nextInt() + "@liferay.com";
		String mySpaceSn = RandomTestUtil.randomString();
		String skypeSn = RandomTestUtil.randomString();
		String twitterSn = RandomTestUtil.randomString();
		String ymSn = RandomTestUtil.randomString();

		List<Address> addresses = new ArrayList<Address>();
		List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();
		List<Phone> phones = new ArrayList<Phone>();
		List<Website> websites = new ArrayList<Website>();
		List<AnnouncementsDelivery> announcementsDelivers =
			new ArrayList<AnnouncementsDelivery>();

		ServiceContext serviceContext = new ServiceContext();

		UserServiceUtil.updateUser(
			userId, oldPassword, newPassword1, newPassword2, passwordReset,
			reminderQueryQuestion, reminderQueryAnswer, screenName,
			emailAddress, facebookId, openId, false, null, languageId,
			timeZoneId, greeting, comments, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn,
			skypeSn, twitterSn, ymSn, jobTitle, siteIds, organizationIds,
			roleIds, userGroupRoles, userGroupIds, addresses, emailAddresses,
			phones, websites, announcementsDelivers, serviceContext);
	}

	protected static Map<String, Serializable> addExpandoMap(Class<?> clazz)
		throws PortalException {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			TestPropsValues.getCompanyId(), clazz.getName());

		expandoBridge.addAttribute("key1", false);
		expandoBridge.addAttribute("key2", false);
		expandoBridge.addAttribute("key3", false);
		expandoBridge.addAttribute("key4", false);

		Map<String, Serializable> expandoMap =
			new HashMap<String, Serializable>();

		expandoMap.put("key1", "value1");
		expandoMap.put("key2", "value2");
		expandoMap.put("key3", "value3");
		expandoMap.put("key4", "value4");

		return expandoMap;
	}

	protected static ServiceContext populateServiceContext(
			Class<?> clazz, boolean includeCategorization)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (includeCategorization) {
			AssetTag tag = AssetTagLocalServiceUtil.addTag(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				null, new ServiceContext());

			serviceContext.setAssetTagNames(new String[] {tag.getName()});

			Company company = CompanyLocalServiceUtil.getCompany(
				TestPropsValues.getCompanyId());

			serviceContext.setScopeGroupId(company.getGroupId());

			AssetVocabulary vocabulary =
				AssetVocabularyLocalServiceUtil.addVocabulary(
					TestPropsValues.getUserId(), RandomTestUtil.randomString(),
					serviceContext);

			AssetCategory category = AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				vocabulary.getVocabularyId(), serviceContext);

			serviceContext.setAssetCategoryIds(
				new long[] {category.getCategoryId()});
		}

		serviceContext.setExpandoBridgeAttributes(addExpandoMap(clazz));

		return serviceContext;
	}

}