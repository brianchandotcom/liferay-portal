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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CSSSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = trimContent(content, false);

		newContent = fixComments(newContent);

		return fixHexColors(newContent);
	}

	protected String fixComments(String content) {
		Matcher matcher = _commentPattern.matcher(content);

		while (matcher.find()) {
			String[] words = StringUtil.split(matcher.group(1), CharPool.SPACE);

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

					content = StringUtil.replaceFirst(
						content, word, StringUtil.toLowerCase(word),
						matcher.start());
				}
			}
		}

		return content;
	}

	protected String fixHexColors(String content) {
		Matcher matcher = _hexColorPattern.matcher(content);

		while (matcher.find()) {
			String hexColor = matcher.group(1);

			if (Validator.isNumber(hexColor) || (hexColor.length() < 3)) {
				continue;
			}

			content = StringUtil.replace(
				content, hexColor, StringUtil.toUpperCase(hexColor));
		}

		return content;
	}

	@Override
	protected void format() throws Exception {
		String[] excludes = {
			"**\\.ivy\\**", "**\\.sass-cache\\**", "**\\aui_deprecated.css",
			"**\\expected\\**", "**\\js\\aui\\**", "**\\js\\editor\\**",
			"**\\js\\misc\\**", "**\\VAADIN\\**"
		};
		String[] includes = {"**\\*.css"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

	private Pattern _commentPattern = Pattern.compile("/\\* -+(.+)-+ \\*/");
	private Pattern _hexColorPattern = Pattern.compile("#([0-9a-f]+)[\\( ;,]");

}