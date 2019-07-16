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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

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

		return getUpdateUserPassword(
			PortalUtil.getHttpServletRequest(actionRequest), userId);
	}

	public static String getUpdateUserPassword(
		HttpServletRequest httpServletRequest, long userId) {

		String password = PortalUtil.getUserPassword(httpServletRequest);

		if (userId != PortalUtil.getUserId(httpServletRequest)) {
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
			String comments, String smsSn, String facebookSn, String jabberSn,
			String skypeSn, String twitterSn)
		throws PortalException {

		return updateUser(
			PortalUtil.getHttpServletRequest(actionRequest), userId, screenName,
			emailAddress, facebookId, openId, languageId, timeZoneId, greeting,
			comments, smsSn, facebookSn, jabberSn, skypeSn, twitterSn);
	}

	public static User updateUser(
			HttpServletRequest httpServletRequest, long userId,
			String screenName, String emailAddress, long facebookId,
			String openId, String languageId, String timeZoneId,
			String greeting, String comments, String smsSn, String facebookSn,
			String jabberSn, String skypeSn, String twitterSn)
		throws PortalException {

		String password = getUpdateUserPassword(httpServletRequest, userId);

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
			facebookSn, jabberSn, skypeSn, twitterSn, contact.getJobTitle(),
			groupIds, organizationIds, roleIds, userGroupRoles, userGroupIds,
			serviceContext);
	}

}