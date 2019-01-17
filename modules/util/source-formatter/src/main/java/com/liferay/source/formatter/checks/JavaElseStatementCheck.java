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
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaElseStatementCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String javaTermContent = javaTerm.getContent();

		Matcher matcher1 = _elseStatementPattern.matcher(javaTermContent);

		while (matcher1.find()) {
			String ifStatementCodeBlock = _getIfStatementCodeBlock(
				javaTermContent, matcher1.start());

			if (ifStatementCodeBlock == null) {
				continue;
			}

			String exitStatementType = null;

			Matcher matcher2 = _exitStatementPattern.matcher(
				ifStatementCodeBlock);

			while (matcher2.find()) {
				if (ToolsUtil.isInsideQuotes(
						ifStatementCodeBlock, matcher2.start())) {

					continue;
				}

				String s = ifStatementCodeBlock.substring(0, matcher2.start());

				if (getLevel(s, "{", "}") == 1) {
					exitStatementType = matcher2.group(1);

					break;
				}
			}

			if (exitStatementType == null) {
				continue;
			}

			int closeCurlyBracePos = _getCloseCurlyBracePos(
				javaTermContent, matcher1.start() + 2);

			String endLine = getLine(
				javaTermContent,
				getLineNumber(javaTermContent, closeCurlyBracePos));

			if (Objects.equals(StringUtil.trim(endLine), "}")) {
				String elseStatement = javaTermContent.substring(
					matcher1.start() + 2, closeCurlyBracePos + 1);

				if (!_containsVariableName(
						javaTermContent.substring(closeCurlyBracePos),
						getVariableNames(elseStatement))) {

					int x = elseStatement.indexOf(CharPool.NEW_LINE);
					int y = elseStatement.lastIndexOf(CharPool.NEW_LINE);

					String replacement = StringPool.BLANK;

					if (x != y) {
						replacement = StringUtil.replace(
							elseStatement.substring(x, y), "\n\t", "\n");
					}

					return StringUtil.replaceFirst(
						javaTermContent, elseStatement, replacement,
						matcher1.start());
				}
			}

			int x = javaTermContent.lastIndexOf(
				exitStatementType, matcher1.start());

			int exitStatementLineNumber =
				javaTerm.getLineNumber() + getLineNumber(javaTermContent, x) -
					1;

			int lineNumber =
				javaTerm.getLineNumber() +
					getLineNumber(javaTermContent, matcher1.start());

			addMessage(
				fileName,
				StringBundler.concat(
					"Else statement is not needed because of the '",
					exitStatementType, "' statement on line ",
					exitStatementLineNumber),
				lineNumber);
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private boolean _containsVariableName(
		String s, List<String> variableNames) {

		for (String variableName : variableNames) {
			if (s.matches("(?s).*\\W" + variableName + "\\W.*")) {
				return true;
			}
		}

		return false;
	}

	private int _getCloseCurlyBracePos(String content, int x) {
		int y = x;

		while (true) {
			y = content.indexOf("}", y + 1);

			if (y == -1) {
				return -1;
			}

			if (getLevel(content.substring(x, y + 1), "{", "}") == 0) {
				return y;
			}
		}
	}

	private String _getIfStatementCodeBlock(String content, int x) {
		Matcher matcher = _ifStatementPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start()) ||
				(_getCloseCurlyBracePos(content, matcher.start()) != x)) {

				continue;
			}

			String s = StringUtil.trim(content.substring(0, matcher.start()));

			if (s.endsWith("else")) {
				return null;
			}

			return content.substring(matcher.start(), x + 1);
		}

		return null;
	}

	private static final Pattern _elseStatementPattern = Pattern.compile(
		"\\}\n\t*else \\{\n");
	private static final Pattern _exitStatementPattern = Pattern.compile(
		"\\s(break|continue|return|throw)[\\s;]");
	private static final Pattern _ifStatementPattern = Pattern.compile(
		"\\sif\\s*\\(");

}