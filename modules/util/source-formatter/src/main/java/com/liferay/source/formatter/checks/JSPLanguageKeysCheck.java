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

package com.liferay.source.formatter.checks;

import com.liferay.source.formatter.util.RegexUtil;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPLanguageKeysCheck extends LanguageKeysCheck {

	@Override
	protected List<Pattern> getPatterns() {
		return Arrays.asList(
			languageKeyPattern, _TAGLIB_LANGUAGE_KEY_PATTERN_1,
			_TAGLIB_LANGUAGE_KEY_PATTERN_2, _TAGLIB_LANGUAGE_KEY_PATTERN_3);
	}

	private static final Pattern _TAGLIB_LANGUAGE_KEY_PATTERN_1 =
		RegexUtil.getPattern(
			"(?:confirmation|label|(?:M|m)essage|message key|names|title)=" +
				"\"[^A-Z<=%\\[\\s]+\"");

	private static final Pattern _TAGLIB_LANGUAGE_KEY_PATTERN_2 =
		RegexUtil.getPattern(
			"(aui:)(?:input|select|field-wrapper) (?!.*label=(?:'|\").*" +
				"(?:'|\").*name=\"[^<=%\\[\\s]+\")(?!.*name=\"[^<=%\\[\\s]+" +
					"\".*title=(?:'|\").+(?:'|\"))(?!.*name=\"[^<=%\\[\\s]+" +
						"\".*type=\"hidden\").*name=\"([^<=%\\[\\s]+)\"");

	private static final Pattern _TAGLIB_LANGUAGE_KEY_PATTERN_3 =
		RegexUtil.getPattern(
			"(liferay-ui:)(?:input-resource) .*id=\"([^<=%\\[\\s]+)\"(?!.*" +
				"title=(?:'|\").+(?:'|\"))");

}