/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.search.suggest;

import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Josef Sustacek
 */
public class KeywordsSuggestionHolder {

	public KeywordsSuggestionHolder(
		String suggestedKeywords, String originalKeywords) {

		this(suggestedKeywords, originalKeywords, _KEYWORDS_DELIMETER_REGEXP);
	}

	public KeywordsSuggestionHolder(
		String suggestedKeywords, String originalKeywords,
		String keywordsDelimiterRegexp) {

		Pattern keywordsDelimiterRegexpPattern = Pattern.compile(
			keywordsDelimiterRegexp);

		if (Validator.isBlank(suggestedKeywords)) {
			_suggestedKeywords = Collections.emptyList();
		}
		else {
			_suggestedKeywords = Arrays.asList(
				keywordsDelimiterRegexpPattern.split(suggestedKeywords));
		}

		if (Validator.isNull(originalKeywords)) {
			_originalKeywords = Collections.emptyList();
		}
		else {
			_originalKeywords = Arrays.asList(
				keywordsDelimiterRegexpPattern.split(originalKeywords));
		}
	}

	public List<String> getSuggestedKeywords() {
		return _suggestedKeywords;
	}

	public boolean hasChanged(String suggestedKeyword) {
		return !_originalKeywords.contains(suggestedKeyword);
	}

	private static final String _KEYWORDS_DELIMETER_REGEXP = "[ ]+";

	private final List<String> _originalKeywords;
	private final List<String> _suggestedKeywords;

}