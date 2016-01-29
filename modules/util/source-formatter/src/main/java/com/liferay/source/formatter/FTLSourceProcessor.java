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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		content = sortLiferayVariables(content);

		Matcher matcher = _singleParameterTagPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String replacement = match;

			String group1 = matcher.group(1);
			String group2 = matcher.group(2);

			if (group2 != null) {
				replacement = StringUtil.replaceFirst(
					replacement, group1 + StringPool.SPACE, group1);
			}

			String group3 = matcher.group(3);

			if (group3.startsWith(StringPool.SPACE)) {
				replacement = StringUtil.replaceLast(
					replacement, group3, group3.substring(1));
			}

			content = StringUtil.replace(content, match, replacement);
		}

		matcher = _multiParameterTagPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			if (match.contains("><")) {
				continue;
			}

			String strippedMatch = stripQuotes(match, CharPool.QUOTE);

			if (StringUtil.count(strippedMatch, StringPool.EQUAL) <= 1) {
				continue;
			}

			String replacement = match;

			String tabs = matcher.group(1);

			int x = -1;

			while (true) {
				x = replacement.indexOf(
					StringPool.EQUAL, x + tabs.length() + 2);

				if (x == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(replacement, x)) {
					continue;
				}

				int y = replacement.lastIndexOf(StringPool.SPACE, x);

				if (y == -1) {
					break;
				}

				replacement =
					replacement.substring(0, y) + StringPool.NEW_LINE + tabs +
						StringPool.TAB + replacement.substring(y + 1);
			}

			if (!match.equals(replacement)) {
				replacement = StringUtil.replaceLast(
					replacement, "/>", StringPool.NEW_LINE + tabs + "/>");

				content = StringUtil.replace(content, match, replacement);
			}
		}

		return formatFTL(content);
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {
			"**/journal/dependencies/template.ftl",
			"**/service/builder/dependencies/props.ftl"
		};

		return getFileNames(excludes, getIncludes());
	}

	protected String formatFTL(String content) throws Exception {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = trimLine(line, false);

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.startsWith("<#assign ")) {
					line = formatWhitespace(line, trimmedLine, true);

					line = formatIncorrectSyntax(line, "=[", "= [", false);
					line = formatIncorrectSyntax(line, "+[", "+ [", false);
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		String newContent = sb.toString();

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() - 1);
		}

		return newContent;
	}

	protected String sortLiferayVariables(String content) {
		Matcher matcher = _liferayVariablesPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			Matcher matcher2 = _liferayVariablePattern.matcher(match);

			String previousVariable = null;

			while (matcher2.find()) {
				String variable = matcher2.group();

				if (Validator.isNotNull(previousVariable) &&
					(previousVariable.compareTo(variable) > 0)) {

					String replacement = StringUtil.replaceFirst(
						match, previousVariable, variable);
					replacement = StringUtil.replaceLast(
						replacement, variable, previousVariable);

					return StringUtil.replace(content, match, replacement);
				}

				previousVariable = variable;
			}
		}

		return content;
	}

	private static final String[] _INCLUDES = new String[] {"**/*.ftl"};

	private Pattern _liferayVariablePattern = Pattern.compile(
		"^\t*<#assign liferay_.*>\n", Pattern.MULTILINE);
	private Pattern _liferayVariablesPattern = Pattern.compile(
		"(^\t*<#assign liferay_.*>\n)+", Pattern.MULTILINE);
	private Pattern _multiParameterTagPattern = Pattern.compile(
		"\n(\t*)<@.+=.+=.+/>");
	private Pattern _singleParameterTagPattern = Pattern.compile(
		"(<@[\\w\\.]+ \\w+)( )?=([^=]+?)/>");

}