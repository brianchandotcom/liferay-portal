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

package com.liferay.portlet.invitation.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InvitationUtil {

	public static Map<String, String> getEmailDefinitionTerms(
			PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms =
			new LinkedHashMap<String, String>();

		definitionTerms.put(
			"[$FROM_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-address-of-the-email-sender"));
		definitionTerms.put(
			"[$FROM_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-sender"));
		definitionTerms.put(
			"[$PAGE_URL$]",
			PortalUtil.getLayoutFullURL(
				themeDisplay.getLayout(), themeDisplay));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		return definitionTerms;
	}

	public static Map<Locale, String> getEmailMessageBodyMap(
		PortletPreferences preferences) {

		return LocalizationUtil.getLocalizationMap(
			preferences, "emailMessageBody",
			PropsKeys.INVITATION_EMAIL_MESSAGE_BODY);
	}

	public static int getEmailMessageMaxRecipients() {
		return GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INVITATION_EMAIL_MAX_RECIPIENTS));
	}

	public static Map<Locale, String> getEmailMessageSubjectMap(
		PortletPreferences preferences) {

		return LocalizationUtil.getLocalizationMap(
			preferences, "emailMessageSubject",
			PropsKeys.INVITATION_EMAIL_MESSAGE_SUBJECT);
	}

}