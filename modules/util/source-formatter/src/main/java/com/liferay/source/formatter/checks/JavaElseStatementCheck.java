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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaTerm;

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

		Matcher matcher = _elseStatementPattern.matcher(javaTermContent);

		while (matcher.find()) {
			if (!_isRedundantElseStatement(javaTermContent, matcher.start())) {
				continue;
			}

			String s = matcher.group(3);

			if (!s.matches("\t}\n")) {
				continue;
			}

			String replacement = StringBundler.concat(
				"\n\n", matcher.group(1),
				StringUtil.replace(
					StringUtil.trimTrailing(matcher.group(2)), "\n\t", "\n"),
				"\n");

			return StringUtil.replaceFirst(
				javaTermContent, matcher.group(), replacement, matcher.start());
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private boolean _isRedundantElseStatement(String content, int x) {
		int y = x;

		while (true) {
			y = content.lastIndexOf("{", y - 1);

			if (ToolsUtil.isInsideQuotes(content, y)) {
				continue;
			}

			String s = content.substring(y, x);

			if (getLevel(s, "{", "}") != 0) {
				continue;
			}

			int z = s.lastIndexOf("\treturn");

			if (z == -1) {
				return false;
			}

			s = s.substring(z);

			if (getLevel(s, "{", "}") != -1) {
				return false;
			}

			for (int i = getLineNumber(content, y);; i--) {
				String line = StringUtil.trim(getLine(content, i));

				if (line.startsWith("if (")) {
					return true;
				}

				if (line.startsWith("else if (")) {
					x = y - 1;

					break;
				}
			}
		}
	}

	private final Pattern _elseStatementPattern = Pattern.compile(
		"\t+else \\{\n\t(\t+)(return[ ;\n].*?)(.\\}.)", Pattern.DOTALL);

}