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

package com.liferay.journal.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "indexer.class.name=com.liferay.journal.model.JournalArticle",
	service = ModelSummaryContributor.class
)
public class JournalArticleModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			document.get("defaultLanguageId"));

		Locale snippetLocale = _getSnippetLocale(document, locale);

		String localizedTitleName = Field.getLocalizedName(locale, Field.TITLE);

		if ((snippetLocale == null) &&
			(document.getField(localizedTitleName) == null)) {

			snippetLocale = defaultLocale;
		}
		else {
			snippetLocale = locale;
		}

		String title = document.get(
			snippetLocale, Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
			Field.TITLE);

		if (Validator.isBlank(title) && !snippetLocale.equals(defaultLocale)) {
			title = document.get(
				defaultLocale,
				Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
				Field.TITLE);
		}

		String content = _getDDMContentSummary(document, snippetLocale);

		if (Validator.isBlank(content) &&
			!snippetLocale.equals(defaultLocale)) {

			content = _getDDMContentSummary(document, defaultLocale);
		}

		content = HtmlUtil.unescape(
			StringUtil.replace(content, "<br />", StringPool.NEW_LINE));

		Summary summary = new Summary(snippetLocale, title, content);

		summary.setMaxContentLength(200);

		return summary;
	}

	private String _getDDMContentSummary(
		Document document, Locale snippetLocale) {

		String content = StringPool.BLANK;

		try {
			content = document.get(
				snippetLocale,
				Field.SNIPPET + StringPool.UNDERLINE + Field.DESCRIPTION,
				Field.DESCRIPTION);

			if (!Validator.isBlank(content)) {
				return content;
			}

			content = document.get(
				snippetLocale,
				Field.SNIPPET + StringPool.UNDERLINE + Field.CONTENT,
				Field.CONTENT);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return content;
	}

	private Locale _getSnippetLocale(Document document, Locale locale) {
		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String localizedAssetCategoryTitlesName =
			prefix +
				Field.getLocalizedName(locale, Field.ASSET_CATEGORY_TITLES);
		String localizedContentName =
			prefix + Field.getLocalizedName(locale, Field.CONTENT);
		String localizedDescriptionName =
			prefix + Field.getLocalizedName(locale, Field.DESCRIPTION);
		String localizedTitleName =
			prefix + Field.getLocalizedName(locale, Field.TITLE);

		if ((document.getField(localizedAssetCategoryTitlesName) != null) ||
			(document.getField(localizedContentName) != null) ||
			(document.getField(localizedDescriptionName) != null) ||
			(document.getField(localizedTitleName) != null)) {

			return locale;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleModelSummaryContributor.class);

}