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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class ArrayCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkInefficientAddAllCalls(
			fileName, content, _addAllArraysAsListPattern);
		_checkInefficientAddAllCalls(
			fileName, content, _addAllListUtilFromArrayPattern);

		content = _formatArrayInitializer(content);
		content = _formatCollectionsToArray(content);
		content = _formatEmptyArray(content);

		return content;
	}

	private void _checkInefficientAddAllCalls(
		String fileName, String content, Pattern pattern) {

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(content, matcher.start())) {
				addMessage(
					fileName, "Use Collections.addAll",
					getLineNumber(content, matcher.start()));
			}
		}
	}

	private String _formatArrayInitializer(String content) {
		Matcher matcher = _arrayInitializationPattern.matcher(content);

		while (matcher.find()) {
			String whitespace = matcher.group(6);

			if (!whitespace.contains(StringPool.NEW_LINE)) {
				return StringUtil.replaceFirst(
					content, matcher.group(5), StringPool.BLANK,
					matcher.start(5));
			}

			int lineNumber = getLineNumber(content, matcher.end(3));

			String line = getLine(content, lineNumber);

			if (getLineLength(line) > (getMaxLineLength() - 2)) {
				String whitespace2 = matcher.group(3);

				if (whitespace2.contains(StringPool.NEW_LINE)) {
					return content;
				}

				return StringUtil.replaceFirst(
					content, matcher.group(),
					StringBundler.concat(
						matcher.group(1), whitespace, matcher.group(4),
						matcher.group(8)));
			}

			if (matcher.group(9) == null) {
				return StringUtil.replaceFirst(
					content, matcher.group(5), StringPool.BLANK,
					matcher.start(5));
			}

			content = StringUtil.replaceFirst(
				content, matcher.group(),
				StringBundler.concat(
					matcher.group(1), matcher.group(3), matcher.group(4),
					" {\n"),
				matcher.start());

			int level = 1;

			int start = lineNumber + 1;

			int count = start;

			while (true) {
				level += getLevel(getLine(content, count), "{", "}");

				if (level != 0) {
					count++;

					continue;
				}

				for (int i = start; i <= count; i++) {
					content = StringUtil.replaceFirst(
						content, StringPool.TAB, StringPool.BLANK,
						getLineStartPos(content, i));
				}

				return content;
			}
		}

		return content;
	}

	private String _formatCollectionsToArray(String content) {
		Matcher matcher1 = _collectionsToArrayPattern.matcher(content);

		while (matcher1.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(matcher1.start()));

			if (parameterList.size() != 1) {
				continue;
			}

			String variableName = matcher1.group(1);

			Pattern pattern = Pattern.compile(
				"^(new .+)\\s*\\[" + variableName + "\\.size\\(\\)\\]$",
				Pattern.DOTALL);

			String parameter = parameterList.get(0);

			Matcher matcher2 = pattern.matcher(parameter);

			if (matcher2.find()) {
				return StringUtil.replaceFirst(
					content, parameter, matcher2.group(1) + "[0]",
					matcher1.start());
			}

			int x = parameter.indexOf("Array.newInstance(");

			if ((x == -1) || ToolsUtil.isInsideQuotes(parameter, x)) {
				continue;
			}

			parameterList = JavaSourceUtil.getParameterList(
				parameter.substring(x));

			if (parameterList.size() != 2) {
				continue;
			}

			String secondParameter = parameterList.get(1);

			if (secondParameter.equals(variableName + ".size()")) {
				return StringUtil.replaceFirst(
					content, parameter,
					StringUtil.replaceLast(parameter, secondParameter, "0"),
					matcher1.start());
			}
		}

		return content;
	}

	private String _formatEmptyArray(String content) {
		Matcher matcher = _emptyArrayPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.end(1))) {
				continue;
			}

			String replacement = StringUtil.replace(
				matcher.group(1), "[]", "[0]");

			return StringUtil.replaceFirst(
				content, matcher.group(), replacement, matcher.start());
		}

		return content;
	}

	private static final Pattern _addAllArraysAsListPattern = Pattern.compile(
		"\\.addAll\\(\\s*Arrays\\.asList\\(");
	private static final Pattern _addAllListUtilFromArrayPattern =
		Pattern.compile("\\.addAll\\(\\s*ListUtil\\.fromArray\\(");
	private static final Pattern _arrayInitializationPattern = Pattern.compile(
		"(\\W\\w+(\\[\\])+)(\\s+)(\\w+ =)((\\s+)new \\w+(\\[\\])+)( \\{(\n)?)");
	private static final Pattern _collectionsToArrayPattern = Pattern.compile(
		"(\\w+)\\.toArray\\(");
	private static final Pattern _emptyArrayPattern = Pattern.compile(
		"((\\[\\])+) \\{\\}");

}