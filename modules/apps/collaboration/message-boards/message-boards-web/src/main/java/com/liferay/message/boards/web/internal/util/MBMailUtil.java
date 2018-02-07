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

package com.liferay.message.boards.web.internal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Sergio González
 */
public class MBMailUtil {

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$CATEGORY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-category-in-which-the-message-has-been-posted"));
		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-message-board"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-message-board"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-message-board"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			definitionTerms.put(
				"[$MAILING_LIST_ADDRESS$]",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-email-address-of-the-mailing-list"));
		}

		definitionTerms.put(
			"[$MESSAGE_BODY$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-message-body"));
		definitionTerms.put(
			"[$MESSAGE_ID$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-message-id"));
		definitionTerms.put(
			"[$MESSAGE_SUBJECT$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-message-subject"));
		definitionTerms.put(
			"[$MESSAGE_URL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-message-url"));
		definitionTerms.put(
			"[$MESSAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-message"));
		definitionTerms.put(
			"[$MESSAGE_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-message"));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-message-board"));

		if (!PropsValues.MESSAGE_BOARDS_EMAIL_BULK) {
			definitionTerms.put(
				"[$TO_ADDRESS$]",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-address-of-the-email-recipient"));
			definitionTerms.put(
				"[$TO_NAME$]",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-name-of-the-email-recipient"));
		}

		return definitionTerms;
	}

	public static Map<String, String> getEmailFromDefinitionTerms(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-message-board"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-message-board"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-message-board"));

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			definitionTerms.put(
				"[$MAILING_LIST_ADDRESS$]",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-email-address-of-the-mailing-list"));
		}

		definitionTerms.put(
			"[$MESSAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-message"));
		definitionTerms.put(
			"[$MESSAGE_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-message"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-message-board"));

		return definitionTerms;
	}

}