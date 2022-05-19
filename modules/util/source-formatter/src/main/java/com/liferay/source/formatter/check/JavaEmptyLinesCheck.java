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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaEmptyLinesCheck extends BaseEmptyLinesCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = fixMissingEmptyLines(absolutePath, content);

		content = fixMissingEmptyLinesAroundComments(content);

		content = fixRedundantEmptyLines(content);

		content = fixMissingEmptyLineAfterSettingVariable(content);

		content = _fixRedundantEmptyLineInLambdaExpression(content);

		content = _fixIncorrectEmptyLineInsideStatement(content);

		content = _fixUpgradeClass(fileName, content);

		return content;
	}

	private String _checkFirstParameterEqual(
		String statement, String methodName) {

		int x;
		int y = -1;

		String preExpression = StringPool.BLANK;
		String expression = StringPool.BLANK;

		StringBundler sb = new StringBundler();

		while (true) {
			x = statement.indexOf(methodName, y);

			if (x == -1) {
				break;
			}

			int startPos = y + 1;

			while ((startPos < statement.length()) &&
				   (statement.charAt(startPos) == CharPool.NEW_LINE)) {

				startPos++;
			}

			y = statement.indexOf(StringPool.SEMICOLON, x);

			if (y == -1) {
				break;
			}

			if (Validator.isNull(preExpression)) {
				preExpression = statement.substring(0, y + 1);

				sb.append(preExpression);

				sb.append(StringPool.NEW_LINE);
			}
			else {
				expression = statement.substring(startPos, y + 1);
			}

			if (Validator.isNotNull(preExpression) &&
				Validator.isNotNull(expression)) {

				List<String> preExpressionParameterList =
					JavaSourceUtil.getParameterList(preExpression);
				List<String> expressionParameterList =
					JavaSourceUtil.getParameterList(expression);

				if (ListUtil.isNotEmpty(preExpressionParameterList) &&
					ListUtil.isNotEmpty(expressionParameterList) &&
					!StringUtil.equals(
						preExpressionParameterList.get(0),
						expressionParameterList.get(0))) {

					sb.append(StringPool.NEW_LINE);
				}

				sb.append(expression);
				sb.append(StringPool.NEW_LINE);

				preExpression = expression;
				expression = StringPool.BLANK;
			}

			y++;
		}

		if ((sb.index() > 0) && !statement.endsWith(StringPool.NEW_LINE)) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _fixIncorrectEmptyLineInsideStatement(String content) {
		int pos = -1;

		outerLoop:
		while (true) {
			int previousPos = pos;

			pos = content.indexOf("\n\n", pos + 1);

			if (pos == -1) {
				return content;
			}

			if (previousPos == -1) {
				continue;
			}

			String s1 = content.substring(previousPos, pos);

			if (getLevel(s1) <= 0) {
				continue;
			}

			String lineBefore = StringUtil.trim(
				getLine(content, getLineNumber(content, previousPos)));

			if (lineBefore.startsWith("//")) {
				continue;
			}

			String lineAfter = StringUtil.trim(
				getLine(content, getLineNumber(content, pos + 2)));

			if (lineAfter.startsWith("//")) {
				continue;
			}

			int x = s1.length();

			while (true) {
				x = s1.lastIndexOf("(", x - 1);

				if (x == -1) {
					break;
				}

				String s2 = s1.substring(x);

				if (getLevel(s2) > 0) {
					if (getLevel(s2, "{", "}") > 0) {
						continue outerLoop;
					}

					String s3 = StringUtil.trim(s1.substring(0, x));

					if (s3.endsWith("\ttry")) {
						continue outerLoop;
					}

					break;
				}
			}

			return StringUtil.replaceFirst(content, "\n\n", "\n", pos);
		}
	}

	private String _fixRedundantEmptyLineInLambdaExpression(String content) {
		Matcher matcher = _redundantEmptyLinePattern.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group(1)) == 0) {
				return StringUtil.replaceFirst(
					content, "\n\n", "\n", matcher.start());
			}
		}

		return content;
	}

	private String _fixUpgradeClass(String fileName, String content)
		throws IOException {

		if (!fileName.endsWith("UpgradeProcess.java")) {
			return content;
		}

		for (String methodName : _DB_PROCESS_METHODS) {
			methodName = methodName + "(";

			StringBundler sb = new StringBundler();

			try (UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(new UnsyncStringReader(content))) {

				String line = StringPool.BLANK;
				boolean makeStatementFlg = false;
				String preLine = StringPool.BLANK;

				StringBundler statementSB = new StringBundler();

				while ((line = unsyncBufferedReader.readLine()) != null) {
					String trimmedLine = StringUtil.trim(line);

					if (!trimmedLine.startsWith(methodName) &&
						!makeStatementFlg) {

						sb.append(line);
						sb.append(StringPool.NEW_LINE);

						continue;
					}
					else if (trimmedLine.startsWith(methodName)) {
						makeStatementFlg = true;
					}

					if (Validator.isNotNull(trimmedLine) &&
						preLine.endsWith(StringPool.SEMICOLON) &&
						!trimmedLine.startsWith(methodName)) {

						makeStatementFlg = false;
					}

					if (makeStatementFlg) {
						statementSB.append(line);
						statementSB.append(StringPool.NEW_LINE);
					}
					else {
						if (statementSB.index() > 0) {
							statementSB.setIndex(statementSB.index() - 1);
						}

						String statement = statementSB.toString();

						sb.append(
							_checkFirstParameterEqual(statement, methodName));

						sb.append(StringPool.NEW_LINE);
						sb.append(line);
						sb.append(StringPool.NEW_LINE);

						statementSB = new StringBundler();
					}

					preLine =
						Validator.isNotNull(trimmedLine) ? trimmedLine :
							preLine;
				}
			}

			if (sb.index() > 0) {
				sb.setIndex(sb.index() - 1);
			}

			content = sb.toString();
		}

		return content;
	}

	private static final String[] _DB_PROCESS_METHODS = {
		"addIndexes", "alterColumnName", "alterColumnType",
		"alterTableAddColumn", "alterTableDropColumn"
	};

	private static final Pattern _redundantEmptyLinePattern = Pattern.compile(
		"\n(.*)-> \\{\n\n[\t ]*(?!// )\\S");

}