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

package com.liferay.portlet.admin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Calendar;
import java.util.List;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class AdminUtil {

	public static String getUpdateUserPassword(
		ActionRequest actionRequest, long userId) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		return getUpdateUserPassword(request, userId);
	}

	public static String getUpdateUserPassword(
		HttpServletRequest request, long userId) {

		String password = PortalUtil.getUserPassword(request);

		if (userId != PortalUtil.getUserId(request)) {
			password = StringPool.BLANK;
		}

		if (password == null) {
			password = StringPool.BLANK;
		}

		return password;
	}

	public static User updateUser(
			ActionRequest actionRequest, long userId, String screenName,
			String emailAddress, long facebookId, String openId,
			String languageId, String timeZoneId, String greeting,
			String comments, String smsSn, String aimSn, String facebookSn,
			String icqSn, String jabberSn, String mySpaceSn, String skypeSn,
			String twitterSn, String ymSn)
		throws PortalException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		return updateUser(
			request, userId, screenName, emailAddress, facebookId, openId,
			languageId, timeZoneId, greeting, comments, smsSn, aimSn,
			facebookSn, icqSn, jabberSn, mySpaceSn, skypeSn, twitterSn, ymSn);
	}

	public static User updateUser(
			HttpServletRequest request, long userId, String screenName,
			String emailAddress, long facebookId, String openId,
			String languageId, String timeZoneId, String greeting,
			String comments, String smsSn, String aimSn, String facebookSn,
			String icqSn, String jabberSn, String mySpaceSn, String skypeSn,
			String twitterSn, String ymSn)
		throws PortalException {

		String password = getUpdateUserPassword(request, userId);

		User user = UserLocalServiceUtil.getUserById(userId);

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
			userId, password, StringPool.BLANK, StringPool.BLANK,
			user.isPasswordReset(), user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), screenName, emailAddress, facebookId,
			openId, languageId, timeZoneId, greeting, comments,
			contact.getFirstName(), contact.getMiddleName(),
			contact.getLastName(), contact.getPrefixId(), contact.getSuffixId(),
			contact.isMale(), birthdayMonth, birthdayDay, birthdayYear, smsSn,
			aimSn, facebookSn, icqSn, jabberSn, mySpaceSn, skypeSn, twitterSn,
			ymSn, contact.getJobTitle(), groupIds, organizationIds, roleIds,
			userGroupRoles, userGroupIds, serviceContext);
	}

}