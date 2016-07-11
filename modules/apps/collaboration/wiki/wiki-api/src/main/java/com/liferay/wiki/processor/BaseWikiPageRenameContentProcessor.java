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

package com.liferay.wiki.processor;

import com.liferay.portal.kernel.util.StringParser;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Roberto Díaz
 * @author Daniel Sanz
 */
public class BaseWikiPageRenameContentProcessor
	implements WikiPageRenameContentProcessor {

	@Override
	public String processContent(
		long nodeId, String originalTitle, String newTitle,
		String originalContent) {

		return runRegexps(originalContent, originalTitle, newTitle);
	}

	protected String runRegexp(
		String content, String regexp, String replacement) {

		Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(content);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(sb, replacement);
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	protected String runRegexps(String content, String title, String newTitle) {
		for (Map.Entry<String, String> entry : regexps.entrySet()) {
			String regexp = entry.getKey();
			String replacement = entry.getValue();

			regexp = regexp.replaceAll(
				"@old_title@",
				Matcher.quoteReplacement(StringParser.escapeRegex(title)));

			replacement = replacement.replaceAll(
				"@new_title@",
				Matcher.quoteReplacement(StringParser.escapeRegex(newTitle)));

			content = runRegexp(content, regexp, replacement);
		}

		return content;
	}

	protected Map<String, String> regexps = new LinkedHashMap<>();

}