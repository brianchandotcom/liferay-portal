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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchContainerResults;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 */
public class BlogsUtil {

	public static final String DISPLAY_STYLE_ABSTRACT = "abstract";

	public static final String DISPLAY_STYLE_FULL_CONTENT = "full-content";

	public static final String DISPLAY_STYLE_TITLE = "title";

	public static CommentManager getCommentManager() {
		return (CommentManager)PortalBeanLocatorUtil.locate(
			CommentManager.class.getName());
	}

	public static int getCommentsCount(BlogsEntry entry) {
		CommentManager commentManager = getCommentManager();

		return commentManager.getCommentsCount(
			BlogsEntry.class.getName(), entry.getEntryId());
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms =
			new LinkedHashMap<String, String>();

		definitionTerms.put(
			"[$BLOGS_ENTRY_CONTENT$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-blog-entry-content"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_CREATE_DATE$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-date-the-blog-entry-was-created"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_DESCRIPTION$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-blog-entry-description"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-name-of-the-site-where-the-blog-entry-was-created"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_STATUS_BY_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-user-who-updated-the-blog-entry"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_TITLE$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-blog-entry-title"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_UPDATE_COMMENT$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-comment-of-the-user-who-updated-the-blog-entry"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-blog-entry"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_USER_PORTRAIT_URL$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-portrait-url-of-the-user-who-added-the-blog-entry"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-blog-entry"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_USER_URL$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-public-site-url-of-the-user-who-added-the-blog-entry"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_URL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-blog-entry-url"));
		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-blog"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-blog"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-blog"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$PORTLET_NAME$]", PortalUtil.getPortletTitle(portletRequest));
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-blog"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	public static Map<String, String> getEmailFromDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms =
			new LinkedHashMap<String, String>();

		definitionTerms.put(
			"[$BLOGS_ENTRY_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-blog-entry"));
		definitionTerms.put(
			"[$BLOGS_ENTRY_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-blog-entry"));
		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-blog"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-blog"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-blog"));
		definitionTerms.put(
			"[$PORTLET_NAME$]", PortalUtil.getPortletTitle(portletRequest));
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-blog"));

		return definitionTerms;
	}

	public static SearchContainerResults<AssetEntry> getSearchContainerResults(
			SearchContainer<?> searchContainer)
		throws PortalException {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(
			BlogsEntry.class.getName(), searchContainer);

		assetEntryQuery.setExcludeZeroViewCount(false);
		assetEntryQuery.setVisible(Boolean.TRUE);

		int total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

		assetEntryQuery.setEnd(searchContainer.getEnd());
		assetEntryQuery.setStart(searchContainer.getStart());

		List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(
			assetEntryQuery);

		return new SearchContainerResults<AssetEntry>(assetEntries, total);
	}

	public static String getUrlTitle(long entryId, String title) {
		if (title == null) {
			return String.valueOf(entryId);
		}

		title = StringUtil.toLowerCase(title.trim());

		if (Validator.isNull(title) || Validator.isNumber(title) ||
			title.equals("rss")) {

			title = String.valueOf(entryId);
		}
		else {
			title = FriendlyURLNormalizerUtil.normalize(
				title, _friendlyURLPattern);
		}

		return ModelHintsUtil.trimString(
			BlogsEntry.class.getName(), "urlTitle", title);
	}

	private static final Pattern _friendlyURLPattern = Pattern.compile(
		"[^a-z0-9_-]");

}