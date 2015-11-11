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
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Rafael Praxedes
 */
public class DDLFormEmailNotificationUtil {

	public static String getEmailFromAddress(DDLRecordSet recordSet) {
		String defaultEmailFromAddress = PrefsPropsUtil.getString(
			recordSet.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		return GetterUtil.getString(
			recordSet.getSettingsProperty(
				"emailFromAddress", defaultEmailFromAddress));
	}

	public static String getEmailFromName(DDLRecordSet recordSet) {
		String defaultEmailFromName = PrefsPropsUtil.getString(
			recordSet.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);

		return GetterUtil.getString(
			recordSet.getSettingsProperty(
				"emailFromName", defaultEmailFromName));
	}

	public static String getEmailSubject(DDLRecordSet recordSet)
		throws PortalException {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		Locale locale = ddmForm.getDefaultLocale();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, DDLFormEmailNotificationUtil.class);

		String defaultEmailSubject = LanguageUtil.format(
			resourceBundle, "new-x-form-submitted", recordSet.getName(locale),
			false);

		return GetterUtil.getString(
			recordSet.getSettingsProperty("emailSubject", defaultEmailSubject));
	}

	public static String getEmailToAddress(DDLRecordSet recordSet) {
		String defaultEmailToAddress = StringPool.BLANK;

		User user = UserLocalServiceUtil.fetchUser(recordSet.getUserId());

		if (user != null) {
			defaultEmailToAddress = user.getEmailAddress();
		}

		return GetterUtil.getString(
			recordSet.getSettingsProperty(
				"emailToAddress", defaultEmailToAddress));
	}

	public static boolean isEmailNotificationEnabled(DDLRecordSet recordSet) {
		return GetterUtil.getBoolean(
			recordSet.getSettingsProperty(
				"sendEmailNotification", Boolean.FALSE.toString()));
	}

}