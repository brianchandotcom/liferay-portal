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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vagner B.C
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Layout",
	service = ModelSummaryContributor.class
)
public class LayoutModelSummaryContributor implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		String localizedFieldName = Field.getLocalizedName(locale, Field.NAME);

		if (Validator.isNull(document.getField(localizedFieldName))) {
			locale = LocaleUtil.fromLanguageId(
				document.get(Field.DEFAULT_LANGUAGE_ID));
		}

		String name = document.get(
			locale, Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
			Field.TITLE);

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

		Summary summary = null;

		if (Validator.isBlank(snippet)) {
			summary = new Summary(locale, name, content);
		}
		else {
			summary = new Summary(locale, name, snippet);
		}

		summary.setMaxContentLength(200);

		return summary;
	}

	private static final String[] _ESCAPE_SAFE_HIGHLIGHTS = {
		"[@HIGHLIGHT1@]", "[@HIGHLIGHT2@]"
	};

	private static final String[] _HIGHLIGHT_TAGS = {
		HighlightUtil.HIGHLIGHT_TAG_OPEN, HighlightUtil.HIGHLIGHT_TAG_CLOSE
	};

	@Reference
	private HtmlParser _htmlParser;

}