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

package com.liferay.portlet.tck.bridge.setup;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Vernon Singleton
 */
public class ServiceUtil {

	public static Group addActiveOpenGroup(long userId, String name)
		throws Exception {

		boolean active = true;
		String friendlyURL =
			"/" + StringUtil.toLowerCase(name).replaceAll(" ", "-");
		boolean siteFlag = true;
		int type = GroupConstants.TYPE_SITE_OPEN;
		boolean manualMembership = false;
		int membershipRestriction =
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;

		return GroupLocalServiceUtil.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, (String)null, 0L,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, name, type,
			manualMembership, membershipRestriction, friendlyURL, siteFlag,
			active, new ServiceContext());
	}

	public static Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		return LayoutLocalServiceUtil.addLayout(
			userId, groupId, privateLayout, parentLayoutId, name, title,
			description, type, hidden, friendlyURL, serviceContext);
	}

	public static User addUser(
			long creatorUserId, long companyId, String firstName,
			String lastName)
		throws Exception {

		boolean autoPassword = false;
		String password1 = "test";

		boolean autoScreenName = false;

		String screenName =
			StringUtil.toLowerCase(firstName) + "." +
				StringUtil.toLowerCase(lastName);

		String emailAddress = screenName + "@liferay.com";

		long facebookId = 0L;
		String openId = "";
		Locale locale = Locale.ENGLISH;
		String middleName = "";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = 1;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = "";
		long[] groupIds = new long[0];
		long[] organizationIds = new long[0];
		long[] roleIds = new long[0];
		long[] userGroupIds = new long[0];
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();

		User user;

		try {
			user = UserLocalServiceUtil.getUserByScreenName(
				companyId, screenName);
		}
		catch (NoSuchUserException nsue) {
			user = UserLocalServiceUtil.addUser(
				creatorUserId, companyId, autoPassword, password1, password1,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
				serviceContext);

			if (_log.isInfoEnabled()) {
				_log.info("Added user: " + screenName);
			}
		}

		return user;
	}

	private static final Log _log = LogFactoryUtil.getLog(ServiceUtil.class);

}