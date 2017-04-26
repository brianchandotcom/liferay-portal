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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPLanguageKeysCheck extends LanguageKeysCheck {

	public JSPLanguageKeysCheck() throws Exception {
	}

	@Override
	protected List<Pattern> getPatterns() {
		return Arrays.asList(
			languageKeyPattern, _taglibLanguageKeyPattern1,
			_taglibLanguageKeyPattern2, _taglibLanguageKeyPattern3);
	}

	private final Pattern _taglibLanguageKeyPattern1 = Pattern.compile(
		"(?:confirmation|label|(?:M|m)essage|message key|names|title)=\"[^A-Z" +
			"<=%\\[\\s]+\"");
	private final Pattern _taglibLanguageKeyPattern2 = Pattern.compile(
		"(aui:)(?:input|select|field-wrapper) (?!.*label=(?:'|\").*(?:'|\").*" +
			"name=\"[^<=%\\[\\s]+\")(?!.*name=\"[^<=%\\[\\s]+\".*title=" +
				"(?:'|\").+(?:'|\"))(?!.*name=\"[^<=%\\[\\s]+\".*type=\"" +
					"hidden\").*name=\"([^<=%\\[\\s]+)\"");
	private final Pattern _taglibLanguageKeyPattern3 = Pattern.compile(
		"(liferay-ui:)(?:input-resource) .*id=\"([^<=%\\[\\s]+)\"(?!.*title=" +
			"(?:'|\").+(?:'|\"))");

}