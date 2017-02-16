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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.Collections;
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

		newContent = StringUtil.replace(
			newContent, StringPool.DOUBLE_SPACE, StringPool.SPACE);

		newContent = sortProperties(newContent);

		newContent = formatComments(newContent);

		newContent = fixEmptyLines(newContent);

		return fixHexColors(newContent);
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = {
			"**/.ivy/**", "**/.sass-cache/**", "**/__MACOSX/**",
			"**/_partial.scss", "**/_unstyled/css/**", "**/aui/**",
			"**/aui_deprecated.css", "**/bourbon/**",
			"**/converter/dependencies/**", "**/expected/**",
			"**/frontend-editors-web/**", "**/tools/node**"
		};

		return getFileNames(excludes, getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	protected String fixEmptyLines(String content) {
		Matcher matcher = _emptyLineAfterOpenCurlyBrace.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
		}

		matcher = _emptyLineBeforeCloseCurlyBrace.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
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

	protected String formatComments(String content) {
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

	protected String sortProperties(String content) {
		Matcher matcher = _propertiesPattern.matcher(content);

		while (matcher.find()) {
			String parameters = StringUtil.trimTrailing(matcher.group());

			String newParameters = StringUtil.removeChar(
				parameters, CharPool.TAB);

			List<String> parameterList = ListUtil.fromArray(
				StringUtil.splitLines(newParameters));

			Collections.sort(parameterList, new PropertyComparator());

			String tabs = matcher.group(2);

			StringBundler sb = new StringBundler(parameterList.size() * 3);

			for (String parameter : parameterList) {
				sb.append(tabs);
				sb.append(parameter);
				sb.append("\n");
			}

			newParameters = sb.toString();

			newParameters = newParameters.substring(
				0, newParameters.length() - 1);

			content = StringUtil.replaceFirst(
				content, parameters, newParameters, matcher.start() - 1);
		}

		return content;
	}

	private static final String[] _INCLUDES = {"**/*.css", "**/*.scss"};

	private final Pattern _commentFormatPattern = Pattern.compile(
		"^-* ?(\\S.*?\\S) ?-*$");
	private final Pattern _commentPattern = Pattern.compile(
		"/\\*[\n ](.*)[\n ]\\*/");
	private final Pattern _emptyLineAfterOpenCurlyBrace = Pattern.compile(
		"\\{\n\n");
	private final Pattern _emptyLineBeforeCloseCurlyBrace = Pattern.compile(
		"\n\n\t*\\}");
	private final Pattern _hexColorPattern = Pattern.compile(
		"#([0-9a-f]+)[\\( ;,]");
	private final Pattern _propertiesPattern = Pattern.compile(
		"(^(\t*)[a-z]\\S*: .+;\n)+", Pattern.MULTILINE);

	private class PropertyComparator extends NaturalOrderStringComparator {

		@Override
		public int compare(String s1, String s2) {
			int pos1 = s1.indexOf(CharPool.COLON);
			int pos2 = s2.indexOf(CharPool.COLON);

			return super.compare(s1.substring(0, pos1), s2.substring(0, pos2));
		}

	}

}