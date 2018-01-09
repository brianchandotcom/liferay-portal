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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaStringBundlerInitialCapacityCheck extends BaseJavaTermCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		return _fixInitialCapacity(javaTerm.getContent());
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private int _adjustCount(int count, String varName, List<String> parts) {
		int max = 0;
		int total = 0;

		for (String part : parts) {
			if (part.contains(varName + ".append(") &&
				part.contains("return")) {

				return -1;
			}

			int i = StringUtil.count(part, varName + ".append(");

			total += i;

			if (i > max) {
				max = i;
			}
		}

		return count - (total - max);
	}

	private String _fixInitialCapacity(String content) {
		Matcher matcher = _stringBundlerPattern.matcher(content);

		while (matcher.find()) {
			String tabs = matcher.group(1);
			String varName = matcher.group(3);

			if (matcher.group(2) == null) {
				int x = content.lastIndexOf(
					"StringBundler " + varName, matcher.start());

				String line = getLine(content, getLineCount(content, x));

				if (!line.startsWith(tabs)) {
					continue;
				}
			}

			int x = content.indexOf(
				"\n" + tabs.substring(1) + "}", matcher.end());

			if (x == -1) {
				continue;
			}

			String s = content.substring(matcher.end(), x);

			x = s.indexOf("\t" + varName + " = new StringBundler");

			if (x != -1) {
				s = s.substring(0, x);
			}

			if (_hasAppendCallInsideLoop(s, varName) ||
				s.matches("(?s).*\\W" + varName + "([,)]|\\.index\\().*")) {

				continue;
			}

			int count = _getCount(s, varName);

			if (count == -1) {
				continue;
			}

			int sbInitialCapacity = GetterUtil.getInteger(matcher.group(5));

			if ((sbInitialCapacity > count) ||
				((sbInitialCapacity != count) &&
				 !s.contains(varName + ".setIndex"))) {

				return StringUtil.replaceFirst(
					content, matcher.group(4), "(" + String.valueOf(count),
					matcher.start());
			}
		}

		return content;
	}

	private int _getCount(String s, String varName) {
		int count = StringUtil.count(s, varName + ".append(");

		int x = -1;

		while (true) {
			int y = s.indexOf("\tif (", x + 1);
			int z = s.indexOf("\ttry {", x + 1);

			if ((y != -1) && ((z == -1) || (z > y))) {
				count = _adjustCount(
					count, varName, _getIfElseStatementParts(s.substring(y)));

				x = y;
			}
			else if (z != -1) {
				count = _adjustCount(
					count, varName, _getTryCatchStatementParts(s.substring(z)));

				x = z;
			}
			else {
				return count;
			}

			if (count == -1) {
				return count;
			}
		}
	}

	private List<String> _getIfElseStatementParts(String s) {
		List<String> parts = new ArrayList<>();

		int x = -1;

		while (true) {
			x = s.indexOf("}", x + 1);

			if (x == -1) {
				return parts;
			}

			if (ToolsUtil.isInsideQuotes(s, x)) {
				continue;
			}

			String part = s.substring(0, x + 1);

			if (getLevel(part, "{", "}") != 0) {
				continue;
			}

			parts.add(part);

			s = StringUtil.trim(s.substring(x + 1));

			if (!s.startsWith("else ")) {
				return parts;
			}

			x = -1;
		}
	}

	private List<String> _getTryCatchStatementParts(String s) {
		List<String> parts = new ArrayList<>();

		int x = -1;

		while (true) {
			x = s.indexOf("}", x + 1);

			if (x == -1) {
				return parts;
			}

			if (ToolsUtil.isInsideQuotes(s, x)) {
				continue;
			}

			String part = s.substring(0, x + 1);

			if (getLevel(part, "{", "}") != 0) {
				continue;
			}

			parts.add(part);

			s = StringUtil.trim(s.substring(x + 1));

			if (!s.startsWith("catch ")) {
				return parts;
			}

			x = -1;
		}
	}

	private boolean _hasAppendCallInsideLoop(String s, String varName) {
		Matcher matcher = _loopPattern.matcher(s);

		while (matcher.find()) {
			int x = matcher.start();

			int y = x;

			while (true) {
				y = s.indexOf("}", y + 1);

				if (y == -1) {
					return true;
				}

				String insideLoop = s.substring(x, y + 1);

				if (getLevel(insideLoop, "{", "}") != 0) {
					continue;
				}

				if (insideLoop.contains(varName + ".append(")) {
					return true;
				}

				break;
			}
		}

		return false;
	}

	private final Pattern _loopPattern = Pattern.compile(
		"\t(do \\{|(for|while) \\()");
	private final Pattern _stringBundlerPattern = Pattern.compile(
		"\n(\t+)(StringBundler )?(\\w+) = new StringBundler(\\(([0-9]+)?)\\)" +
			";\n");

}