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

package com.liferay.portal.kernel.search;

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

		if (Validator.isNull(suggestedKeywords)) {
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