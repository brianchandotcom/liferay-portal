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

package com.liferay.wiki.web.internal.display.context.logic;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.web.internal.display.context.util.WikiRequestHelper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Iván Zaera
 */
public class MailTemplatesHelper {

	public MailTemplatesHelper(WikiRequestHelper wikiRequestHelper) {
		_wikiRequestHelper = wikiRequestHelper;

		_wikiGroupServiceOverriddenConfiguration =
			wikiRequestHelper.getWikiGroupServiceOverriddenConfiguration();
	}

	public Map<String, String> getEmailFromDefinitionTerms() {
		Map<String, String> definitionTerms = new LinkedHashMap<>();

		ResourceBundle resourceBundle = getResourceBundle();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				resourceBundle, "the-company-id-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				resourceBundle, "the-company-mx-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				resourceBundle, "the-company-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$PAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				resourceBundle,
				"the-email-address-of-the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PAGE_USER_NAME$]",
			LanguageUtil.get(resourceBundle, "the-user-who-added-the-page"));

		definitionTerms.put(
			"[$PORTLET_NAME$]",
			HtmlUtil.escape(_wikiRequestHelper.getPortletTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				resourceBundle, "the-site-name-associated-with-the-wiki"));

		return definitionTerms;
	}

	public Map<String, String> getEmailNotificationDefinitionTerms() {
		Map<String, String> definitionTerms = new LinkedHashMap<>();

		ResourceBundle resourceBundle = getResourceBundle();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				resourceBundle, "the-company-id-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				resourceBundle, "the-company-mx-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				resourceBundle, "the-company-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$DIFFS_URL$]",
			LanguageUtil.get(
				resourceBundle,
				"the-url-of-the-page-comparing-this-page-content-with-the-" +
					"previous-version"));

		definitionTerms.put(
			"[$FROM_ADDRESS$]",
			HtmlUtil.escape(
				_wikiGroupServiceOverriddenConfiguration.emailFromAddress()));
		definitionTerms.put(
			"[$FROM_NAME$]",
			HtmlUtil.escape(
				_wikiGroupServiceOverriddenConfiguration.emailFromName()));
		definitionTerms.put(
			"[$NODE_NAME$]",
			LanguageUtil.get(
				resourceBundle, "the-node-in-which-the-page-was-added"));
		definitionTerms.put(
			"[$PAGE_CONTENT$]",
			LanguageUtil.get(resourceBundle, "the-page-content"));
		definitionTerms.put(
			"[$PAGE_DATE_UPDATE$]",
			LanguageUtil.get(resourceBundle, "the-date-of-the-modifications"));
		definitionTerms.put(
			"[$PAGE_DIFFS$]",
			LanguageUtil.get(
				resourceBundle,
				"the-page-content-compared-with-the-previous-version-page-" +
					"content"));
		definitionTerms.put(
			"[$PAGE_ID$]", LanguageUtil.get(resourceBundle, "the-page-id"));
		definitionTerms.put(
			"[$PAGE_SUMMARY$]",
			LanguageUtil.get(
				resourceBundle,
				"the-summary-of-the-page-or-the-modifications"));
		definitionTerms.put(
			"[$PAGE_TITLE$]",
			LanguageUtil.get(resourceBundle, "the-page-title"));
		definitionTerms.put(
			"[$PAGE_URL$]", LanguageUtil.get(resourceBundle, "the-page-url"));
		definitionTerms.put(
			"[$PAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				resourceBundle,
				"the-email-address-of-the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PAGE_USER_NAME$]",
			LanguageUtil.get(resourceBundle, "the-user-who-added-the-page"));

		Company company = _wikiRequestHelper.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$PORTLET_NAME$]", _wikiRequestHelper.getPortletTitle());
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				resourceBundle, "the-site-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				resourceBundle, "the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				resourceBundle, "the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	protected ResourceBundle getResourceBundle() {
		ResourceBundle bundleResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _wikiRequestHelper.getLocale(), getClass());
		ResourceBundle portalResourceBundle =
			LanguageResources.getResourceBundle(_wikiRequestHelper.getLocale());

		return new AggregateResourceBundle(
			bundleResourceBundle, portalResourceBundle);
	}

	private final WikiGroupServiceOverriddenConfiguration
		_wikiGroupServiceOverriddenConfiguration;
	private final WikiRequestHelper _wikiRequestHelper;

}