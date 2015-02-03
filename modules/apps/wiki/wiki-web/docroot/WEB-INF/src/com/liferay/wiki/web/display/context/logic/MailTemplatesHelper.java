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

package com.liferay.wiki.web.display.context.logic;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.Company;
import com.liferay.wiki.settings.WikiSettings;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ivan Zaera
 */
public class MailTemplatesHelper {

	public MailTemplatesHelper(WikiRequestHelper wikiRequestHelper) {
		_wikiRequestHelper = wikiRequestHelper;

		_wikiSettings = wikiRequestHelper.getWikiSettings();
	}

	public Map<String, String> getEmailFromDefinitionTerms() {
		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-company-id-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-company-mx-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-company-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$PAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-email-address-of-the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PAGE_USER_NAME$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(), "the-user-who-added-the-page"));

		definitionTerms.put(
			"[$PORTLET_NAME$]",
			HtmlUtil.escape(_wikiRequestHelper.getPortletTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-site-name-associated-with-the-wiki"));

		return definitionTerms;
	}

	public Map<String, String> getEmailNotificationDefinitionTerms() {
		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-company-id-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-company-mx-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-company-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$DIFFS_URL$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-url-of-the-page-comparing-this-page-content-with-the-" +
					"previous-version"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]",
			HtmlUtil.escape(_wikiSettings.getEmailFromAddress()));
		definitionTerms.put(
			"[$FROM_NAME$]", HtmlUtil.escape(_wikiSettings.getEmailFromName()));
		definitionTerms.put(
			"[$NODE_NAME$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-node-in-which-the-page-was-added"));
		definitionTerms.put(
			"[$PAGE_CONTENT$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(), "the-page-content"));
		definitionTerms.put(
			"[$PAGE_DATE_UPDATE$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-date-of-the-modifications"));
		definitionTerms.put(
			"[$PAGE_DIFFS$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-page-content-compared-with-the-previous-version-page-" +
					"content"));
		definitionTerms.put(
			"[$PAGE_ID$]",
			LanguageUtil.get(_wikiRequestHelper.getLocale(), "the-page-id"));
		definitionTerms.put(
			"[$PAGE_SUMMARY$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-summary-of-the-page-or-the-modifications"));
		definitionTerms.put(
			"[$PAGE_TITLE$]",
			LanguageUtil.get(_wikiRequestHelper.getLocale(), "the-page-title"));
		definitionTerms.put(
			"[$PAGE_URL$]",
			LanguageUtil.get(_wikiRequestHelper.getLocale(), "the-page-url"));
		definitionTerms.put(
			"[$PAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-email-address-of-the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PAGE_USER_NAME$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(), "the-user-who-added-the-page"));

		Company company = _wikiRequestHelper.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$PORTLET_NAME$]", _wikiRequestHelper.getPortletTitle());
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-site-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				_wikiRequestHelper.getLocale(),
				"the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	private final WikiRequestHelper _wikiRequestHelper;
	private final WikiSettings _wikiSettings;

}