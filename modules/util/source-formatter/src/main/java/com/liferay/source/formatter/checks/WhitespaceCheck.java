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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class WhitespaceCheck extends BaseFileCheck {

	public void setAllowLeadingSpaces(String allowLeadingSpaces) {
		_allowLeadingSpaces = GetterUtil.getBoolean(allowLeadingSpaces);
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		content = _trimContent(fileName, content);

		content = StringUtil.replace(content, "\n\n\n", "\n\n");

		if (content.endsWith(StringPool.RETURN)) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	protected String formatIncorrectSyntax(String line, String regex) {
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(line);

		if (!matcher.find()) {
			return line;
		}

		if (ToolsUtil.isInsideQuotes(line, matcher.start(1))) {
			return line;
		}

		String whitespace = matcher.group(2);

		if (whitespace.length() > 0) {
			return line;
		}

		return line.substring(0, matcher.start(2)) + StringPool.SPACE +
			line.substring(matcher.start(2));
	}

	protected String formatIncorrectSyntax(
		String line, String incorrectSyntax, String correctSyntax,
		boolean lineStart) {

		if (lineStart) {
			if (line.startsWith(incorrectSyntax)) {
				line = StringUtil.replaceFirst(
					line, incorrectSyntax, correctSyntax);
			}

			return line;
		}

		for (int x = -1;;) {
			x = line.indexOf(incorrectSyntax, x + 1);

			if (x == -1) {
				return line;
			}

			if (!ToolsUtil.isInsideQuotes(line, x)) {
				line = StringUtil.replaceFirst(
					line, incorrectSyntax, correctSyntax, x);
			}
		}
	}

	protected String formatWhitespace(
		String line, String linePart, boolean javaSource) {

		String originalLinePart = linePart;

		linePart = formatIncorrectSyntax(linePart, "catch(", "catch (", true);
		linePart = formatIncorrectSyntax(linePart, "else{", "else {", true);
		linePart = formatIncorrectSyntax(
			linePart, "else if(", "else if (", true);
		linePart = formatIncorrectSyntax(linePart, "for(", "for (", true);
		linePart = formatIncorrectSyntax(linePart, "if(", "if (", true);
		linePart = formatIncorrectSyntax(linePart, "while(", "while (", true);
		linePart = formatIncorrectSyntax(linePart, "List <", "List<", false);

		if (javaSource) {
			linePart = formatIncorrectSyntax(linePart, " ...", "...", false);
			linePart = formatIncorrectSyntax(linePart, " [", "[", false);
			linePart = formatIncorrectSyntax(linePart, "{ ", "{", false);
			linePart = formatIncorrectSyntax(linePart, " }", "}", false);
			linePart = formatIncorrectSyntax(linePart, " )", ")", false);
			linePart = formatIncorrectSyntax(linePart, "( ", "(", false);
			linePart = formatIncorrectSyntax(linePart, "){", ") {", false);
			linePart = formatIncorrectSyntax(linePart, "]{", "] {", false);
			linePart = formatIncorrectSyntax(linePart, "\\w(( ?)=)");
			linePart = formatIncorrectSyntax(linePart, "(=( ?))\\w");
			linePart = formatIncorrectSyntax(linePart, "for \\(.*(( ?):)");
			linePart = formatIncorrectSyntax(linePart, "for \\(.*(:( ?)).+");
		}

		if (!linePart.startsWith("##")) {
			for (int x = 0;;) {
				x = linePart.indexOf(StringPool.DOUBLE_SPACE, x + 1);

				if (x == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(linePart, x)) {
					continue;
				}

				linePart = StringUtil.replaceFirst(
					linePart, StringPool.DOUBLE_SPACE, StringPool.SPACE, x);
			}
		}

		if (!javaSource) {
			line = StringUtil.replace(line, originalLinePart, linePart);

			return formatIncorrectSyntax(
				line, StringPool.SPACE + StringPool.TAB, StringPool.TAB, false);
		}

		if (!line.contains(StringPool.DOUBLE_SLASH)) {
			while (linePart.contains(StringPool.TAB)) {
				linePart = StringUtil.replaceLast(
					linePart, StringPool.TAB, StringPool.SPACE);
			}
		}

		if (line.contains(StringPool.DOUBLE_SLASH)) {
			line = StringUtil.replace(line, originalLinePart, linePart);

			return formatIncorrectSyntax(
				line, StringPool.SPACE + StringPool.TAB, StringPool.TAB, false);
		}

		int pos = linePart.indexOf(") ");

		if ((pos != -1) && ((pos + 2) < linePart.length()) &&
			!linePart.contains(StringPool.AT) &&
			!ToolsUtil.isInsideQuotes(linePart, pos)) {

			String linePart2 = linePart.substring(pos + 2);

			if (Character.isLetter(linePart2.charAt(0)) &&
				!linePart2.startsWith("default") &&
				!linePart2.startsWith("instanceof") &&
				!linePart2.startsWith("return") &&
				!linePart2.startsWith("throws")) {

				linePart = StringUtil.replaceFirst(
					linePart, StringPool.SPACE, StringPool.BLANK, pos);
			}
		}

		pos = linePart.indexOf(" (");

		if ((pos != -1) && !linePart.contains(StringPool.EQUAL) &&
			!ToolsUtil.isInsideQuotes(linePart, pos) &&
			(linePart.startsWith("private ") ||
			 linePart.startsWith("protected ") ||
			 linePart.startsWith("public "))) {

			linePart = StringUtil.replaceFirst(linePart, " (", "(", pos);
		}

		for (int x = -1;;) {
			int posComma = linePart.indexOf(CharPool.COMMA, x + 1);
			int posSemicolon = linePart.indexOf(CharPool.SEMICOLON, x + 1);

			if ((posComma == -1) && (posSemicolon == -1)) {
				break;
			}

			x = Math.min(posComma, posSemicolon);

			if (x == -1) {
				x = Math.max(posComma, posSemicolon);
			}

			if (ToolsUtil.isInsideQuotes(linePart, x)) {
				continue;
			}

			if (linePart.length() > (x + 1)) {
				char nextChar = linePart.charAt(x + 1);

				if ((nextChar != CharPool.APOSTROPHE) &&
					(nextChar != CharPool.CLOSE_PARENTHESIS) &&
					(nextChar != CharPool.SPACE) &&
					(nextChar != CharPool.STAR)) {

					linePart = StringUtil.insert(
						linePart, StringPool.SPACE, x + 1);
				}
			}

			if (x > 0) {
				char previousChar = linePart.charAt(x - 1);

				if (previousChar == CharPool.SPACE) {
					linePart = linePart.substring(0, x - 1).concat(
						linePart.substring(x));
				}
			}
		}

		line = StringUtil.replace(line, originalLinePart, linePart);

		return formatIncorrectSyntax(
			line, StringPool.SPACE + StringPool.TAB, StringPool.TAB, false);
	}

	protected boolean isAllowLeadingSpaces(String fileName) {
		return _allowLeadingSpaces;
	}

	protected String trimLine(String fileName, String line) {
		if (line.trim().length() == 0) {
			return StringPool.BLANK;
		}

		line = StringUtil.trimTrailing(line);

		if (isAllowLeadingSpaces(fileName) || line.startsWith(" *")) {
			return line;
		}

		while (line.matches("^\t*" + StringPool.FOUR_SPACES + ".*")) {
			line = StringUtil.replaceFirst(
				line, StringPool.FOUR_SPACES, StringPool.TAB);
		}

		while (line.startsWith(StringPool.SPACE)) {
			line = StringUtil.replaceFirst(
				line, CharPool.SPACE, StringPool.BLANK);
		}

		return line;
	}

	private String _trimContent(String fileName, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				sb.append(trimLine(fileName, line));
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	private boolean _allowLeadingSpaces;

}