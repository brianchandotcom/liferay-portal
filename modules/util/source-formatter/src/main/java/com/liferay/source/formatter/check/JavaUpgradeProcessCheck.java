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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaUpgradeProcessCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		JavaClass javaClass = (JavaClass)javaTerm;

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		String content = javaClass.getContent();

		if (!extendedClassNames.contains("UpgradeProcess")) {
			return content;
		}

		return _checkRepeatedIf(content);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _checkRepeatedIf(String content) {
		Matcher matcher = _ifPattern.matcher(content);

		loop:
		while (matcher.find()) {
			List<String> parameters = new ArrayList<>();

			int startPos = matcher.start();

			while (true) {
				int closeParenthesisIndex = content.indexOf(
					StringPool.CLOSE_PARENTHESIS, startPos);

				if (closeParenthesisIndex == -1) {
					break;
				}

				String ifExpression = content.substring(
					matcher.start() + 4, closeParenthesisIndex);

				int level = getLevel(
					ifExpression, StringPool.OPEN_PARENTHESIS,
					StringPool.CLOSE_PARENTHESIS);

				if (level == 0) {
					startPos = closeParenthesisIndex + 1;

					if (ifExpression.contains("||") ||
						ifExpression.contains("&&")) {

						return content;
					}

					parameters = JavaSourceUtil.getParameterList(ifExpression);

					break;
				}

				startPos = closeParenthesisIndex + 1;
			}

			if (ListUtil.isEmpty(parameters)) {
				return content;
			}

			int bodyStartIndex = startPos;

			while (true) {
				int closeCurlyBraceIndex = content.indexOf(
					StringPool.CLOSE_CURLY_BRACE, startPos);

				if (closeCurlyBraceIndex == -1) {
					break;
				}

				String ifBody = content.substring(
					bodyStartIndex, closeCurlyBraceIndex + 1);

				int level = getLevel(
					ifBody, StringPool.OPEN_CURLY_BRACE,
					StringPool.CLOSE_CURLY_BRACE);

				if (level == 0) {
					String nextBody = StringUtil.trimLeading(
						content.substring(closeCurlyBraceIndex + 1));

					if (nextBody.startsWith("else")) {
						continue loop;
					}

					int line =
						getLineNumber(content, closeCurlyBraceIndex + 1) -
							getLineNumber(content, startPos) -
								StringUtil.count(ifBody, "\n\n") - 1;

					Matcher bodyMatcher = _ifBodyPattern.matcher(ifBody);

					if (bodyMatcher.find()) {
						int expressionLine =
							getLineNumber(ifBody, bodyMatcher.end()) -
								getLineNumber(ifBody, bodyMatcher.start()) + 1;

						if (expressionLine != line) {
							return content;
						}

						List<String> alertParameters =
							JavaSourceUtil.getParameterList(
								bodyMatcher.group());

						if (ListUtil.isEmpty(alertParameters)) {
							continue loop;
						}

						if (StringUtil.equals(
								parameters.get(0), alertParameters.get(0)) &&
							StringUtil.equals(
								parameters.get(1), alertParameters.get(1))) {

							return StringUtil.replaceFirst(
								content,
								content.substring(
									matcher.start(), closeCurlyBraceIndex + 1),
								bodyMatcher.group(), matcher.start());
						}
					}

					break;
				}

				startPos = closeCurlyBraceIndex + 1;
			}
		}

		return content;
	}

	private static final Pattern _ifBodyPattern = Pattern.compile(
		"(alterTableAddColumn|alterTableDropColumn|alterColumnType|" +
			"alterColumnName)\\([^;]+;");
	private static final Pattern _ifPattern = Pattern.compile(
		"if \\(hasColumn(Type)?\\(");

}