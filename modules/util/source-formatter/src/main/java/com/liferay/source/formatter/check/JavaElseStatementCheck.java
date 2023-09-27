/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.ArrayList;
import java.util.Collections;
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

		outerLoop:
		while (matcher1.find()) {
			List<String> ifStatementCodeBlocks = _getIfStatementCodeBlock(
				javaTermContent, matcher1.start());

			if (ListUtil.isEmpty(ifStatementCodeBlocks)) {
				continue;
			}

			String exitStatementType = null;

			for (String ifStatementCodeBlock : ifStatementCodeBlocks) {
				exitStatementType = null;

				Matcher matcher2 = _exitStatementPattern.matcher(
					ifStatementCodeBlock);

				while (matcher2.find()) {
					if (ToolsUtil.isInsideQuotes(
							ifStatementCodeBlock, matcher2.start())) {

						continue;
					}

					String s = ifStatementCodeBlock.substring(
						0, matcher2.start());

					if (getLevel(s, "{", "}") == 1) {
						exitStatementType = matcher2.group(1);

						break;
					}
				}

				if (exitStatementType == null) {
					continue outerLoop;
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

			addMessage(
				fileName,
				StringBundler.concat(
					"Else statement is not needed because of the '",
					exitStatementType, "' statement on line ",
					javaTerm.getLineNumber(x)),
				javaTerm.getLineNumber(matcher1.start() + 3));
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

	private List<String> _getElseIfStatementCodeBlock(
		String content, int closeCurlyBracePos, int x) {

		int openCurlyBracePos = content.indexOf(
			StringPool.OPEN_CURLY_BRACE, closeCurlyBracePos + 1);

		if (openCurlyBracePos == -1) {
			return Collections.emptyList();
		}

		String statement = StringUtil.trim(
			content.substring(closeCurlyBracePos + 1, openCurlyBracePos));

		if (!statement.startsWith("else if")) {
			return Collections.emptyList();
		}

		closeCurlyBracePos = _getCloseCurlyBracePos(content, openCurlyBracePos);

		if (closeCurlyBracePos == -1) {
			return Collections.emptyList();
		}

		List<String> elseIfStatementCodeBlocks = new ArrayList<>();

		elseIfStatementCodeBlocks.add(
			content.substring(openCurlyBracePos, closeCurlyBracePos + 1));

		if (closeCurlyBracePos == x) {
			return elseIfStatementCodeBlocks;
		}

		List<String> nestedElseIfStatementCodeBlocks =
			_getElseIfStatementCodeBlock(content, closeCurlyBracePos, x);

		if (nestedElseIfStatementCodeBlocks.isEmpty()) {
			return Collections.emptyList();
		}

		elseIfStatementCodeBlocks.addAll(nestedElseIfStatementCodeBlocks);

		return elseIfStatementCodeBlocks;
	}

	private List<String> _getIfStatementCodeBlock(String content, int x) {
		Matcher matcher = _ifStatementPattern.matcher(content);

		while (matcher.find()) {
			int closeCurlyBracePos = _getCloseCurlyBracePos(
				content, matcher.start());

			if (ToolsUtil.isInsideQuotes(content, matcher.start()) ||
				(closeCurlyBracePos > x)) {

				continue;
			}

			String s = StringUtil.trim(content.substring(0, matcher.start()));

			if (s.endsWith("else")) {
				continue;
			}

			List<String> ifStatementCodeBlocks = new ArrayList<>();

			ifStatementCodeBlocks.add(
				content.substring(matcher.start(), closeCurlyBracePos + 1));

			if (closeCurlyBracePos != x) {
				List<String> elseIfStatementCodeBlocks =
					_getElseIfStatementCodeBlock(
						content, closeCurlyBracePos, x);

				if (elseIfStatementCodeBlocks.isEmpty()) {
					continue;
				}

				ifStatementCodeBlocks.addAll(elseIfStatementCodeBlocks);
			}

			return ifStatementCodeBlocks;
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