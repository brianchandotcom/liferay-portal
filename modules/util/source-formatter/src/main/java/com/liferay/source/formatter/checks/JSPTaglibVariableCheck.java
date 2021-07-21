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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Alan Huang
 */
public class JSPTaglibVariableCheck extends BaseJSPTermsCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		return _formatTaglibVariable(fileName, content);
	}

	private String _formatTaglibVariable(String fileName, String content)
		throws IOException {

		Matcher matcher = _taglibVariablePattern.matcher(content);

		while (matcher.find()) {
			int x = matcher.start(1);

			StringBundler sb = new StringBundler();

			int lineNumber = getLineNumber(content, matcher.end());

			String indent = matcher.group(3);

			while (true) {
				String line = getLine(content, lineNumber);

				if ((line == null) ||
					(Validator.isNotNull(line) && !line.startsWith(indent))) {

					break;
				}

				sb.append(line);
				sb.append("\n");

				lineNumber++;
			}

			String nextTags = sb.toString();

			String s = matcher.group(1);

			String[] variableDefinitions = s.split(";\n");

			for (String variableDefinition : variableDefinitions) {
				x = x + variableDefinition.length() + 2;

				String[] array = variableDefinition.split(" = ", 2);

				String variableTypeAndName = array[0];

				int y = variableTypeAndName.lastIndexOf(CharPool.SPACE);

				String variableName = variableTypeAndName.substring(y + 1);

				if (!nextTags.contains("<%= " + variableName + " %>")) {
					continue;
				}

				s = content.substring(x);

				String taglibValue = array[1];

				if (hasVariableReference(
						s, taglibValue,
						s.lastIndexOf("<%= " + variableName + " %>"))) {

					continue;
				}

				if (!taglibValue.contains("\n") &&
					(taglibValue.contains("\\\"") ||
					 (taglibValue.contains(StringPool.APOSTROPHE) &&
					  taglibValue.contains(StringPool.QUOTE)))) {

					if (!variableName.startsWith("taglib") &&
						(_getVariableCount(content, variableName) == 2) &&
						nextTags.contains("<%= " + variableName + " %>")) {

						addMessage(
							fileName,
							"Variable '" + variableName +
								"' should start with 'taglib'",
							getLineNumber(content, matcher.start(1)));
					}

					continue;
				}

				populateContentsMap(fileName, content);

				String newContent = null;

				if (taglibValue.startsWith("{")) {
					String typeName = StringUtil.trimLeading(
						variableTypeAndName.substring(0, y));

					if (typeName.endsWith("[][]") || !typeName.endsWith("[]")) {
						continue;
					}

					newContent = StringUtil.replaceFirst(
						content, "<%= " + variableName + " %>",
						StringBundler.concat(
							"<%= new ", typeName, " ", taglibValue, " %>"),
						matcher.end());
				}
				else {
					newContent = StringUtil.replaceFirst(
						content, "<%= " + variableName + " %>",
						"<%= " + taglibValue + " %>", matcher.end());
				}

				y = newContent.indexOf(variableDefinition, matcher.start());

				Set<String> checkedFileNames = new HashSet<>();
				Set<String> includeFileNames = new HashSet<>();

				if (hasUnusedJSPTerm(
						fileName, newContent, "\\W" + variableName + "\\W",
						getLineNumber(newContent, y), "variable",
						checkedFileNames, includeFileNames, getContentsMap())) {

					if (!taglibValue.contains("\n")) {
						return StringUtil.replaceFirst(
							newContent, variableDefinition + ";\n",
							StringPool.BLANK, matcher.start());
					}

					addMessage(
						fileName,
						StringBundler.concat(
							"No need to declare variable '", variableName,
							"', inline inside the tag."),
						getLineNumber(content, matcher.start(4)));
				}
			}
		}

		return content;
	}

	private int _getVariableCount(String content, String variableName) {
		int count = 0;

		Pattern pattern = Pattern.compile("\\W" + variableName + "\\W");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			int x = matcher.start() + 1;

			if (isJavaSource(content, x)) {
				if (!ToolsUtil.isInsideQuotes(content, x)) {
					count++;
				}

				continue;
			}

			if (isJavaSource(content, x, true)) {
				count++;
			}
		}

		return count;
	}

	private static final Pattern _taglibVariablePattern = Pattern.compile(
		"\n(((\t*)([\\w<>\\[\\],\\? ]+) (\\w+) = (((?!;\n).)*);\n)+)\\s*%>\n",
		Pattern.DOTALL);

}