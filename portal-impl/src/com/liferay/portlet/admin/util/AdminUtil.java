/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if ((userId != PortalUtil.getUserId(httpServletRequest)) ||
			(password == null)) {

			password = StringPool.BLANK;
		}

		return password;
	}

	public static User updateUser(
			ActionRequest actionRequest, long userId, String screenName,
			String emailAddress, String languageId, String timeZoneId,
			String greeting, String comments, String smsSn, String facebookSn,
			String jabberSn, String skypeSn, String twitterSn)
		throws PortalException {

		return updateUser(
			PortalUtil.getHttpServletRequest(actionRequest), userId, screenName,
			emailAddress, languageId, timeZoneId, greeting, comments, smsSn,
			facebookSn, jabberSn, skypeSn, twitterSn);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #updateUser(
	 *             HttpServletRequest, long, String,  String, String, String,
	 *             String, String, String, String, String, String, String)}
	 */
	@Deprecated
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

		return UserServiceUtil.updateUser(
			userId, password, StringPool.BLANK, StringPool.BLANK,
			user.isPasswordReset(), user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), screenName, emailAddress, facebookId,
			openId, languageId, timeZoneId, greeting, comments,
			contact.getFirstName(), contact.getMiddleName(),
			contact.getLastName(), contact.getPrefixListTypeId(),
			contact.getSuffixListTypeId(), contact.isMale(), birthdayMonth,
			birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn, skypeSn,
			twitterSn, contact.getJobTitle(), groupIds, organizationIds,
			roleIds, userGroupRoles, userGroupIds, new ServiceContext());
	}

	public static User updateUser(
			HttpServletRequest httpServletRequest, long userId,
			String screenName, String emailAddress, String languageId,
			String timeZoneId, String greeting, String comments, String smsSn,
			String facebookSn, String jabberSn, String skypeSn,
			String twitterSn)
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

		return UserServiceUtil.updateUser(
			userId, password, StringPool.BLANK, StringPool.BLANK,
			user.isPasswordReset(), user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), screenName, emailAddress, languageId,
			timeZoneId, greeting, comments, contact.getFirstName(),
			contact.getMiddleName(), contact.getLastName(),
			contact.getPrefixListTypeId(), contact.getSuffixListTypeId(),
			contact.isMale(), birthdayMonth, birthdayDay, birthdayYear, smsSn,
			facebookSn, jabberSn, skypeSn, twitterSn, contact.getJobTitle(),
			groupIds, organizationIds, roleIds, userGroupRoles, userGroupIds,
			new ServiceContext());
	}

}