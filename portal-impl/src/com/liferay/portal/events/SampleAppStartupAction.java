/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.events;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PwdGenerator;

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
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	protected void doRun(long companyId) throws Exception {
		if (UserLocalServiceUtil.fetchUserByScreenName(companyId, "paul") !=
				null) {

			return;
		}

		long creatorUserId = 0;
		boolean autoPassword = false;

		String password1 = PwdGenerator.getPassword();

		String password2 = password1;

		boolean autoScreenName = false;
		String screenName = "paul";
		String emailAddress = "paul@liferay.com";

		Locale locale = LocaleUtil.US;
		String firstName = "Paul";
		String middleName = StringPool.BLANK;
		String lastName = "Smith";
		long prefixListTypeId = 0;
		long suffixListTypeId = 0;
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
			autoScreenName, screenName, emailAddress, locale, firstName,
			middleName, lastName, prefixListTypeId, suffixListTypeId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			UserConstants.TYPE_REGULAR, groupIds, organizationIds, roleIds,
			userGroupIds, sendEmail, serviceContext);

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
			autoScreenName, screenName, emailAddress, locale, firstName,
			middleName, lastName, prefixListTypeId, suffixListTypeId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			UserConstants.TYPE_REGULAR, groupIds, organizationIds, roleIds,
			userGroupIds, sendEmail, serviceContext);

		if (_log.isDebugEnabled()) {
			_log.debug(
				janeUser.getFullName() + " was created with user id " +
					janeUser.getUserId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SampleAppStartupAction.class);

}