/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.admin.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
public class EmailConfigurationUtil {

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		String fromAddress = HtmlUtil.escape(emailFromAddress);
		String fromName = HtmlUtil.escape(emailFromName);

		String toAddress = LanguageUtil.get(
			locale, "the-address-of-the-email-recipient");
		String toName = LanguageUtil.get(
			locale, "the-name-of-the-email-recipient");

		ResourceBundle resourceBundle = getResourceBundle(locale);

		String pageURL = LanguageUtil.get(resourceBundle, "the-report-url");

		String reportName = LanguageUtil.get(
			resourceBundle, "the-name-of-the-report");

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put("[$FROM_ADDRESS$]", fromAddress);
		definitionTerms.put("[$FROM_NAME$]", fromName);

		definitionTerms.put("[$TO_ADDRESS$]", toAddress);
		definitionTerms.put("[$TO_NAME$]", toName);

		definitionTerms.put("[$PAGE_URL$]", pageURL);
		definitionTerms.put("[$REPORT_NAME$]", reportName);

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle()));

		return definitionTerms;
	}

	public static ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, EmailConfigurationUtil.class);
	}

}