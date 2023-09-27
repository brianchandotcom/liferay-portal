/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;

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
			languageKeyPattern, _taglibLanguageKeyPattern1,
			_taglibLanguageKeyPattern2, _taglibLanguageKeyPattern3);
	}

	private static final Pattern _taglibLanguageKeyPattern1 = Pattern.compile(
		"(?:confirmation|label|(?:M|m)essage|message key|names|title)=\"[^A-Z" +
			"<=%\\[\\s]+\"");
	private static final Pattern _taglibLanguageKeyPattern2 = Pattern.compile(
		StringBundler.concat(
			"(aui:)(?:input|select|field-wrapper) (?!.*label=(?:'|\").*",
			"(?:'|\\\").*name=\"[^<=%\\[\\s]+\")(?!.*name=\"[^<=%\\[\\s]+\".*",
			"title=(?:'|\").+(?:'|\"))(?!.*name=\"[^<=%\\[\\s]+\".*type=\"",
			"hidden\").*name=\"([^<=%\\[\\s]+)\""));
	private static final Pattern _taglibLanguageKeyPattern3 = Pattern.compile(
		"(liferay-ui:)(?:input-resource) .*id=\"([^<=%\\[\\s]+)\"(?!.*title=" +
			"(?:'|\").+(?:'|\"))");

}