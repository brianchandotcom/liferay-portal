/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Vagner B.C
 */
public class LayoutModelSummaryContributor implements ModelSummaryContributor {

	public LayoutModelSummaryContributor(HtmlParser htmlParser) {
		_htmlParser = htmlParser;
	}

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Summary summary = null;

		String content = document.get(locale, Field.CONTENT);

		content = StringUtil.replace(
			content, _HIGHLIGHT_TAGS, _ESCAPE_SAFE_HIGHLIGHTS);

		content = _htmlParser.extractText(content);

		content = StringUtil.replace(
			content, _ESCAPE_SAFE_HIGHLIGHTS, _HIGHLIGHT_TAGS);

		snippet = document.get(
			locale, Field.SNIPPET + StringPool.UNDERLINE + Field.CONTENT);

		Set<String> highlights = new HashSet<>();

		HighlightUtil.addSnippet(document, highlights, snippet, "temp");

		content = HighlightUtil.highlight(
			content, ArrayUtil.toStringArray(highlights),
			HighlightUtil.HIGHLIGHT_TAG_OPEN,
			HighlightUtil.HIGHLIGHT_TAG_CLOSE);

		if (Validator.isBlank(snippet)) {
			summary = new Summary(locale, _getTitle(document, locale), content);
		}
		else {
			summary = new Summary(locale, _getTitle(document, locale), snippet);
		}

		summary.setMaxContentLength(200);

		return summary;
	}

	private String _getTitle(Document document, Locale locale) {
		String localizedFieldTitle = Field.getLocalizedName(
			locale, Field.TITLE);

		if (Validator.isNull(document.getField(localizedFieldTitle))) {
			return document.get(
				LocaleUtil.fromLanguageId(
					document.get(Field.DEFAULT_LANGUAGE_ID)),
				Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
				Field.TITLE);
		}

		return document.get(
			locale, Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
			Field.TITLE);
	}

	private static final String[] _ESCAPE_SAFE_HIGHLIGHTS = {
		"[@HIGHLIGHT1@]", "[@HIGHLIGHT2@]"
	};

	private static final String[] _HIGHLIGHT_TAGS = {
		HighlightUtil.HIGHLIGHT_TAG_OPEN, HighlightUtil.HIGHLIGHT_TAG_CLOSE
	};

	private final HtmlParser _htmlParser;

}