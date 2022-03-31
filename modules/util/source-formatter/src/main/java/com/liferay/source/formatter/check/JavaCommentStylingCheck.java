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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class JavaCommentStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _singleLineCommentPattern.matcher(content);

		while (matcher.find()) {
			String commentContent = matcher.group(2);

			if (commentContent.contains(StringPool.SEMICOLON) ||
				commentContent.endsWith(StringPool.COMMA) ||
				commentContent.endsWith("||") ||
				commentContent.endsWith("&&")) {

				continue;
			}

			int level = getLevel(
				commentContent, StringPool.OPEN_PARENTHESIS,
				StringPool.CLOSE_PARENTHESIS);

			if (level != 0) {
				continue;
			}

			level = getLevel(
				commentContent, StringPool.OPEN_CURLY_BRACE,
				StringPool.CLOSE_CURLY_BRACE);

			if (level != 0) {
				continue;
			}

			level = getLevel(
				commentContent, StringPool.OPEN_BRACKET,
				StringPool.CLOSE_BRACKET);

			if ((level != 0) || commentContent.matches(".+\\..+") ||
				commentContent.matches(".+=.+")) {

				continue;
			}

			if (commentContent.startsWith(StringPool.SLASH) ||
				commentContent.startsWith("\tTODO")) {

				return StringUtil.replaceFirst(
					content, commentContent, commentContent.substring(1),
					matcher.start(2));
			}

			return StringUtil.replaceFirst(
				content, matcher.group(),
				StringBundler.concat(
					matcher.group(1), StringPool.SPACE, matcher.group(2)),
				matcher.start());
		}

		return content;
	}

	private static final Pattern _singleLineCommentPattern = Pattern.compile(
		"(\n\t*//)(?! )(.+)");

}