/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

/**
 * @author Bryan Engler
 * @author Joshua Cords
 */
public class ObjectEntryModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			document.get(Field.DEFAULT_LANGUAGE_ID));

		Summary summary = new Summary(
			_getTitle(defaultLocale, document, locale),
			_getContent(defaultLocale, document, locale));

		summary.setMaxContentLength(200);

		return summary;
	}

	private String _getContent(
		Locale defaultLocale, Document document, Locale locale) {

		String content = _getLocalizedHighlightedContent(document, locale);

		if (!Validator.isBlank(content)) {
			return content;
		}

		if ((defaultLocale != null) && !defaultLocale.equals(locale)) {
			content = _getLocalizedHighlightedContent(document, defaultLocale);
		}

		if (!Validator.isBlank(content)) {
			return content;
		}

		return document.get("objectEntryContent");
	}

	private String _getLocalizedHighlightedContent(
		Document document, Locale locale) {

		if (locale == null) {
			return StringPool.BLANK;
		}

		String localizedNestedValueSnippetName = StringBundler.concat(
			Field.SNIPPET, StringPool.UNDERLINE,
			Field.getLocalizedName(locale, "nestedFieldArray.value"));

		String content = document.get(localizedNestedValueSnippetName);

		if (!Validator.isBlank(content)) {
			return content;
		}

		return document.get(
			Field.getLocalizedName(locale, "objectEntryContent"));
	}

	private String _getTitle(
		Locale defaultLocale, Document document, Locale locale) {

		String title = document.get(
			locale, "snippet_objectEntryTitle", "objectEntryTitle");

		if (!Validator.isBlank(title)) {
			return title;
		}

		if ((defaultLocale != null) && !defaultLocale.equals(locale)) {
			title = document.get(
				defaultLocale, "snippet_objectEntryTitle", "objectEntryTitle");

			if (!Validator.isBlank(title)) {
				return title;
			}
		}

		title = document.get("snippet_objectEntryTitle");

		if (!Validator.isBlank(title)) {
			return title;
		}

		title = document.get("objectEntryTitle");

		if (!Validator.isBlank(title)) {
			return title;
		}

		return document.get(Field.ENTRY_CLASS_PK);
	}

}