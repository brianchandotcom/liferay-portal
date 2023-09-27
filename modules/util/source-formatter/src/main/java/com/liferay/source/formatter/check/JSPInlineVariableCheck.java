/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPInlineVariableCheck extends BaseJSPTermsCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _inlineVariable(
			fileName, content, _chainedVariableDefinitionPattern, false);
		content = _inlineVariable(
			fileName, content, _getterVariableDefinitionPattern, false);
		content = _inlineVariable(
			fileName, content, _variableDefinitionPattern, true);

		return content;
	}

	private String _inlineVariable(
			String fileName, String content, Pattern variableDefinitionPattern,
			boolean checkSetterParameterOnly)
		throws IOException {

		Matcher variableDefinitionMatcher = variableDefinitionPattern.matcher(
			content);

		while (variableDefinitionMatcher.find()) {
			if (!isJavaSource(content, variableDefinitionMatcher.start())) {
				continue;
			}

			String variableName = variableDefinitionMatcher.group(1);

			int x = variableDefinitionMatcher.start(2);

			int y = x;

			String variableValue = null;

			while (true) {
				y = content.indexOf(StringPool.CLOSE_PARENTHESIS, y + 1);

				variableValue = content.substring(x, y + 1);

				if (getLevel(variableValue) != 0) {
					continue;
				}

				String s = content.substring(y + 1);

				if (!s.matches("(?s)\\.\\w+\\(.*")) {
					break;
				}
			}

			Pattern pattern = null;

			if (checkSetterParameterOnly) {
				pattern = Pattern.compile(
					StringBundler.concat(
						"(?i)set", variableName, "\\((", variableName, ")\\)"));
			}
			else {
				pattern = Pattern.compile(
					"[\\(,]\\s*(" + variableName + ")\\s*[,\\)]");
			}

			String s = content.substring(y + 1);

			if (!s.startsWith(";\n")) {
				continue;
			}

			Matcher matcher = pattern.matcher(s);

			x = -1;

			while (matcher.find()) {
				if (ToolsUtil.isInsideQuotes(s, matcher.start(1))) {
					continue;
				}

				x = matcher.start();

				break;
			}

			if (x == -1) {
				continue;
			}

			int z = StringUtil.indexOfAny(
				s, new String[] {"{\n", "\n}", "\t}", "%>\n"});

			if ((z < x) ||
				hasVariableReference(s, variableValue, matcher.start(1))) {

				continue;
			}

			int pos = y + matcher.start(1) - 1;

			String newContent = StringUtil.replaceFirst(
				content, variableName, variableValue, pos);

			populateContentsMap(fileName, content);

			Set<String> checkedFileNames = new HashSet<>();
			Set<String> includeFileNames = new HashSet<>();

			if (hasUnusedJSPTerm(
					fileName, newContent, "\\W" + variableName + "\\W",
					getLineNumber(
						newContent, variableDefinitionMatcher.start(1)),
					"variable", checkedFileNames, includeFileNames,
					getContentsMap())) {

				String variableDeclaration = newContent.substring(
					variableDefinitionMatcher.start(), y + 2);

				return StringUtil.replaceFirst(
					newContent, variableDeclaration, StringPool.BLANK,
					variableDefinitionMatcher.start() - 1);
			}
		}

		return content;
	}

	private static final Pattern _chainedVariableDefinitionPattern =
		Pattern.compile(
			"\n\t*[\\w<>\\[\\],\\? ]+ (\\w+) = ([^\n(]+(build|map|put)\\(\n)");
	private static final Pattern _getterVariableDefinitionPattern =
		Pattern.compile(
			"\n\t*[\\w<>\\[\\],\\? ]+ (\\w+) = ((\\w+\\.)?get\\1\\(.)",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern _variableDefinitionPattern = Pattern.compile(
		"\n\t*[\\w<>\\[\\],\\? ]+ (\\w+) = ([\\w.]+\\(.)");

}