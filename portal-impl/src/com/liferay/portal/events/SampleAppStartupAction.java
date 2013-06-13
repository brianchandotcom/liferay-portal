/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Calendar;
import java.util.Locale;

/**
 * <p>
 * This class can be used to populate an empty database programmatically. This
 * allows a developer to create sample data without relying on native SQL.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SampleAppStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			long companyId = GetterUtil.getLong(ids[0]);

			doRun(companyId);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void doRun(long companyId) throws Exception {
		if (UserLocalServiceUtil.fetchUserByScreenName(
				companyId, "paul") != null) {

			return;
		}

		long creatorUserId = 0;
		boolean autoPassword = false;
		String password1 = "test";
		String password2 = password1;
		boolean autoScreenName = false;
		String screenName = "paul";
		String emailAddress = "paul@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = Locale.US;
		String firstName = "Paul";
		String middleName = StringPool.BLANK;
		String lastName = "Smith";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		User paulUser = UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		if (_log.isDebugEnabled()) {
			_log.debug(
				paulUser.getFullName() + " was created with user id " +
					paulUser.getUserId());
		}

		screenName = "jane";
		emailAddress = "jane@liferay.com";
		firstName = "Jane";

		User janeUser = UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		if (_log.isDebugEnabled()) {
			_log.debug(
				janeUser.getFullName() + " was created with user id " +
					janeUser.getUserId());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SampleAppStartupAction.class);

}