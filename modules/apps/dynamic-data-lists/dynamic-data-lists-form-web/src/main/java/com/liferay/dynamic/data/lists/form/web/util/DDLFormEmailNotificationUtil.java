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

package com.liferay.dynamic.data.lists.form.web.util;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.Locale;

/**
 * @author Rafael Praxedes
 */
public class DDLFormEmailNotificationUtil {

	public static String getDefaultEmailFromAddress(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
	}

	public static String getDefaultEmailFromName(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
	}

	public static String getDefaultEmailToAddress(DDLRecordSet recordSet) {
		String emailToAddress = StringPool.BLANK;

		User userFormCreator = UserLocalServiceUtil.fetchUser(
			recordSet.getUserId());

		if (userFormCreator != null) {
			emailToAddress = userFormCreator.getEmailAddress();
		}

		return emailToAddress;
	}

	public static String getDefaultSubject(DDLRecordSet recordSet)
		throws PortalException {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Locale locale = ddmStructure.getDDMForm().getDefaultLocale();

		return LanguageUtil.format(
			locale, "new-x-form-submitted", recordSet.getName(locale), false);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormEmailNotificationUtil.class);

}