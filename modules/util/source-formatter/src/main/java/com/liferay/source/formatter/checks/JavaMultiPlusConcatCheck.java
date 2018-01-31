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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaMultiPlusConcatCheck extends BaseJavaTermCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath) ||
			isModulesApp(absolutePath, true) ||
			absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return javaTerm.getContent();
		}

		_checkConcat(
			fileName, absolutePath, javaTerm.getContent(), fileContent);

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD, JAVA_VARIABLE};
	}

	private void _checkConcat(
			String fileName, String absolutePath, String javaTermContent,
			String fileContent)
		throws Exception {

		int x = -1;

		while (true) {
			x = javaTermContent.indexOf("+", x + 1);

			if (x == -1) {
				return;
			}

			char nextChar = javaTermContent.charAt(x + 1);

			if ((nextChar == CharPool.PLUS) || (nextChar == CharPool.EQUAL)) {
				x++;

				continue;
			}

			if (ToolsUtil.isInsideQuotes(javaTermContent, x) ||
				_isInsideAnnotation(javaTermContent, x)) {

				continue;
			}

			String line = StringUtil.trim(
				getLine(javaTermContent, getLineCount(javaTermContent, x)));

			if (line.startsWith("//") || line.startsWith("*")) {
				continue;
			}

			int startPos = _getStartPos(javaTermContent, x);

			if (startPos == -1) {
				continue;
			}

			int endPos = _getEndPos(javaTermContent, startPos);

			if (endPos < x) {
				continue;
			}

			String plusStatement = javaTermContent.substring(startPos, endPos);

			List<String> parts = _getParts(StringUtil.trim(plusStatement));

			if ((parts.size() > 3) &&
				_containsStringVariable(fileContent, parts)) {

				if (absolutePath.contains("/modules/") &&
					!absolutePath.contains("/modules/apps/") &&
					!_hasKernelOrPetraStringDependency(fileName)) {

					return;
				}

				int pos = fileContent.indexOf(plusStatement);

				addMessage(
					fileName,
					"Use method 'StringBundler.concat' when concatenating " +
						"more than 3 strings",
					"concat.markdown", getLineCount(fileContent, pos));
			}

			x = endPos;
		}
	}

	private boolean _containsStringVariable(
		String content, List<String> parts) {

		for (String part : parts) {
			if (part.startsWith(StringPool.QUOTE) ||
				part.startsWith("CharPool.") ||
				part.startsWith("File.pathSeparator") ||
				part.startsWith("File.separator") ||
				part.startsWith("StringPool.") ||
				(Validator.isVariableName(part) &&
				 content.matches("(?s).+\\sString\\s+" + part + "\\W.+"))) {

				return true;
			}
		}

		return false;
	}

	private int _getEndPos(String content, int startPos) {
		char[] endChars = {
			CharPool.CLOSE_CURLY_BRACE, CharPool.CLOSE_PARENTHESIS,
			CharPool.COMMA, CharPool.SEMICOLON
		};

		for (int i = startPos + 1;; i++) {
			char c = content.charAt(i);

			if (!ArrayUtil.contains(endChars, c) ||
				ToolsUtil.isInsideQuotes(content, i)) {

				continue;
			}

			if (getLevel(content.substring(startPos, i)) == 0) {
				return i;
			}
		}
	}

	private List<String> _getParts(String plusStatement) {
		List<String> parts = new ArrayList<>();

		int x = -1;

		while (true) {
			x = plusStatement.indexOf(CharPool.PLUS, x + 1);

			if (ToolsUtil.isInsideQuotes(plusStatement, x)) {
				continue;
			}

			if (x == -1) {
				parts.add(plusStatement);

				return parts;
			}

			if (plusStatement.charAt(x + 1) == CharPool.PLUS) {
				x++;

				continue;
			}

			String part = plusStatement.substring(0, x - 1);

			if (getLevel(part) != 0) {
				continue;
			}

			parts.add(part);

			plusStatement = StringUtil.trim(plusStatement.substring(x + 1));

			x = -1;
		}
	}

	private int _getStartPos(String content, int x) {
		char[] startChars = {
			CharPool.OPEN_CURLY_BRACE, CharPool.OPEN_PARENTHESIS,
			CharPool.COMMA, CharPool.EQUAL
		};

		for (int i = x;; i--) {
			char c = content.charAt(i);

			if ((ArrayUtil.contains(startChars, c) ||
				 Character.isWhitespace(c)) &&
				!ToolsUtil.isInsideQuotes(content, i)) {

				String s = content.substring(i + 1, x);

				if (Validator.isNotNull(s) && (getLevel(s) == 0)) {
					return i + 1;
				}
			}
		}
	}

	private boolean _hasKernelOrPetraStringDependency(String fileName)
		throws Exception {

		int x = fileName.length();

		while (true) {
			x = fileName.lastIndexOf("/", x - 1);

			if (x == -1) {
				return false;
			}

			String buildGradleFileName =
				fileName.substring(0, x + 1) + "build.gradle";

			File file = new File(buildGradleFileName);

			if (!file.exists()) {
				continue;
			}

			String content = FileUtil.read(file);

			if (content.contains("name: \"com.liferay.petra.string\"") ||
				content.contains("name: \"com.liferay.portal.kernel\"")) {

				return true;
			}

			return false;
		}
	}

	private boolean _isInsideAnnotation(String content, int x) {
		int start = x;
		int end = -1;

		while (true) {
			start = content.lastIndexOf("@", start - 1);

			if (start == -1) {
				return false;
			}

			char c = content.charAt(start - 1);

			if ((c != CharPool.TAB) && (c != CharPool.NEW_LINE)) {
				continue;
			}

			int lineCount = getLineCount(content, start);

			String line = getLine(content, lineCount);

			if (!line.contains(StringPool.OPEN_PARENTHESIS)) {
				return false;
			}

			int posPar = content.indexOf(StringPool.OPEN_PARENTHESIS, start);

			end = posPar + 1;

			String s = null;

			while (true) {
				s = content.substring(start, end);

				int level = getLevel(s);

				if (level == 0) {
					break;
				}

				end++;
			}

			if ((start < x) && (x < end)) {
				return true;
			}

			return false;
		}
	}

}