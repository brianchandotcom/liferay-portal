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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CSSCommentsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatComments(content);
	}

	private String _formatComments(String content) {
		Matcher commentMatcher = _commentPattern.matcher(content);

		while (commentMatcher.find()) {
			Matcher commentFormatMatcher = _commentFormatPattern.matcher(
				commentMatcher.group(1));

			if (!commentFormatMatcher.find()) {
				continue;
			}

			String comment = commentFormatMatcher.group(1);

			String[] words = StringUtil.split(comment, CharPool.SPACE);

			for (int i = 1; i < words.length; i++) {
				String previousWord = words[i - 1];

				if (previousWord.endsWith(StringPool.PERIOD) ||
					previousWord.equals(StringPool.SLASH)) {

					continue;
				}

				String word = words[i];

				if ((word.length() > 1) &&
					Character.isUpperCase(word.charAt(0)) &&
					StringUtil.isLowerCase(word.substring(1))) {

					comment = StringUtil.replaceFirst(
						comment, word, StringUtil.toLowerCase(word));
				}
			}

			content = StringUtil.replaceFirst(
				content, commentMatcher.group(),
				"/* ---------- " + comment + " ---------- */");
		}

		return content;
	}

	private final Pattern _commentFormatPattern = Pattern.compile(
		"^-* ?(\\S.*?\\S) ?-*$");
	private final Pattern _commentPattern = Pattern.compile(
		"/\\*[\n ](.*)[\n ]\\*/");

}