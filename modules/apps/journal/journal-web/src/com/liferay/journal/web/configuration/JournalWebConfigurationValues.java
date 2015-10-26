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

package com.liferay.journal.web.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Eduardo Garcia
 */
public class JournalWebConfigurationValues {

	public static final int CHECK_INTERVAL = GetterUtil.getInteger(
		JournalWebConfigurationUtil.get("check.interval"));

	public static final String DEFAULT_DISPLAY_VIEW = GetterUtil.getString(
		JournalWebConfigurationUtil.get("default.display.view"));

	public static String[] DISPLAY_VIEWS = JournalWebConfigurationUtil.getArray(
		"display.views");

	public static final Boolean JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID =
		GetterUtil.getBoolean(
			JournalWebConfigurationUtil.get(
				"journal.article.force.autogenerate.id"));

	public static String[] JOURNAL_ARTICLE_FORM_ADD =
		JournalWebConfigurationUtil.getArray("journal.article.form.add");

	public static String[] JOURNAL_ARTICLE_FORM_DEFAULT_VALUES =
		JournalWebConfigurationUtil.getArray(
			"journal.article.form.default.values");

	public static String[] JOURNAL_ARTICLE_FORM_UPDATE =
		JournalWebConfigurationUtil.getArray("journal.article.form.update");

	public static final String JOURNAL_ARTICLE_TEMPLATE_LANGUAGE_CONTENT =
		"journal.article.template.language.content";

	public static final boolean JOURNAL_ARTICLES_SEARCH_WITH_INDEX =
		GetterUtil.getBoolean(
			JournalWebConfigurationUtil.get(
				"journal.articles.search.with.index"));

	public static final Boolean JOURNAL_FEED_FORCE_AUTOGENERATE_ID =
		GetterUtil.getBoolean(
			JournalWebConfigurationUtil.get(
				"journal.feed.force.autogenerate.id"));

	public static final Boolean PUBLISH_TO_LIVE_BY_DEFAULT =
		GetterUtil.getBoolean(
			JournalWebConfigurationUtil.get("publish.to.live.by.default"));

	public static final Boolean PUBLISH_VERSION_HISTORY_BY_DEFAULT =
		GetterUtil.getBoolean(
			JournalWebConfigurationUtil.get(
				"publish.version.history.by.default"));

}