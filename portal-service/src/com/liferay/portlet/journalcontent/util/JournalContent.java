/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontent.util;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticleDisplay;

/**
 * @author Raymond Augé
 */
public interface JournalContent {

	public static final String ARTICLE_SEPARATOR = "_ARTICLE_";

	public static final String LANGUAGE_SEPARATOR = "_LANGUAGE_";

	public static final String LAYOUT_SET_SEPARATOR = "_LAYOUT_SET_";

	public static final String PAGE_SEPARATOR = "_PAGE_";

	public static final String SECURE_SEPARATOR = "_SECURE_";

	public static final String TEMPLATE_SEPARATOR = "_TEMPLATE_";

	public static final String VERSION_SEPARATOR = "_VERSION_";

	public static final String VIEW_MODE_SEPARATOR = "_VIEW_MODE_";

	public void clearCache();

	public void clearCache(
		long groupId, String articleId, String ddmTemplateKey);

	public String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		String xmlRequest);

	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, String xmlRequest);

	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay);

	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay, String xmlRequest);

	public String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay);

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, double version, String ddmTemplateKey,
		String viewMode, String languageId, ThemeDisplay themeDisplay, int page,
		String xmlRequest);

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		String xmlRequest);

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, String xmlRequest);

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay);

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay, int page,
		String xmlRequest);

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay);

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay, int page);

}