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

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public abstract class BaseJavaTerm implements JavaTerm {

	@Override
	public Position getEndPosition() {
		return _endPosition;
	}

	@Override
	public Position getStartPosition() {
		return _startPosition;
	}

	@Override
	public void setEndPosition(Position endPosition) {
		_endPosition = endPosition;
	}

	@Override
	public void setStartPosition(Position startPosition) {
		_startPosition = startPosition;
	}

	@Override
	public String toString() {
		return toString(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, -1);
	}

	@Override
	public abstract String toString(
		String indent, String prefix, String suffix, int maxLineLength);

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		return toString(indent, prefix, suffix, maxLineLength);
	}

	protected String adjustIndent(StringBundler sb, String indent) {
		String s = sb.toString();

		String lastLine = StringUtil.trim(_getLastLine(s));

		if (lastLine.endsWith("&") || lastLine.endsWith("|") ||
			lastLine.endsWith("^")) {

			int x = s.length();

			while (true) {
				x = s.lastIndexOf(CharPool.OPEN_PARENTHESIS, x - 1);

				if (x == -1) {
					return _getLeadingWhitespace(s);
				}

				if (SourceUtil.getLevel(s.substring(x)) != 0) {
					continue;
				}

				int y = s.lastIndexOf('\n', x) + 1;

				String linePart = s.substring(y, x);

				int z = linePart.length();

				while (true) {
					z = linePart.lastIndexOf(CharPool.OPEN_PARENTHESIS, z - 1);

					if (z == -1) {
						return _getLeadingWhitespace(s.substring(y, x));
					}

					if (SourceUtil.getLevel(linePart.substring(z)) != 0) {
						return _convertToWhitespace(s.substring(y, z + 1));
					}
				}
			}
		}

		if (lastLine.startsWith("while (")) {
			return indent + "\t\t";
		}

		return indent;
	}

	protected String append(
		StringBundler sb, JavaTerm javaTerm, String indent, int maxLineLength) {

		return append(sb, javaTerm, indent, maxLineLength, true);
	}

	protected String append(
		StringBundler sb, JavaTerm javaTerm, String indent, int maxLineLength,
		boolean newLine) {

		return append(
			sb, javaTerm, indent, StringPool.BLANK, StringPool.BLANK,
			maxLineLength, newLine);
	}

	protected String append(
		StringBundler sb, JavaTerm javaTerm, String indent, String prefix,
		String suffix, int maxLineLength) {

		return append(
			sb, javaTerm, indent, prefix, suffix, maxLineLength, true);
	}

	protected String append(
		StringBundler sb, JavaTerm javaTerm, String indent, String prefix,
		String suffix, int maxLineLength, boolean newLine) {

		if (appendSingleLine(sb, javaTerm, prefix, suffix, maxLineLength)) {
			return indent;
		}

		if (newLine) {
			sb = _stripTrailingWhitespace(sb);

			int beforeLineBreakCount = StringUtil.count(
				sb.toString(), CharPool.NEW_LINE);

			if (Validator.isNull(sb.toString())) {
				sb.append(
					javaTerm.toString(
						StringUtil.replaceFirst(indent, "\t", ""), prefix,
						suffix, maxLineLength));
			}
			else {
				appendNewLine(
					sb, javaTerm, indent, prefix, suffix, maxLineLength);
			}

			int afterLineBreakCount = StringUtil.count(
				sb.toString(), CharPool.NEW_LINE);

			for (int i = 0; i < (afterLineBreakCount - beforeLineBreakCount);
				 i++) {

				indent = "\t" + indent;
			}

			return indent;
		}

		return appendWithLineBreak(
			sb, javaTerm, indent, prefix, suffix, maxLineLength);
	}

	protected String append(
		StringBundler sb, List<? extends JavaTerm> list, String indent,
		int maxLineLength) {

		return append(
			sb, list, indent, StringPool.BLANK, StringPool.BLANK,
			maxLineLength);
	}

	protected String append(
		StringBundler sb, List<? extends JavaTerm> list, String indent,
		String prefix, String suffix, int maxLineLength) {

		return append(
			sb, list, StringPool.COMMA_AND_SPACE, indent, prefix, suffix,
			maxLineLength);
	}

	protected String append(
		StringBundler sb, List<? extends JavaTerm> list, String delimeter,
		String indent, String prefix, String suffix, int maxLineLength) {

		if ((list.isEmpty() && Validator.isNull(prefix) &&
			 Validator.isNull(suffix)) ||
			appendSingleLine(
				sb, list, delimeter, prefix, suffix, maxLineLength)) {

			return indent;
		}

		int beforeLineBreakCount = StringUtil.count(
			sb.toString(), CharPool.NEW_LINE);

		String lastLine = getLastLine(sb);

		sb = _stripTrailingWhitespace(sb);

		if (Validator.isNull(StringUtil.trim(lastLine))) {
			appendNewLine(
				sb, list, delimeter, lastLine, prefix, suffix, maxLineLength);
		}
		else {
			appendNewLine(
				sb, list, delimeter, indent, prefix, suffix, maxLineLength);
		}

		int afterLineBreakCount = StringUtil.count(
			sb.toString(), CharPool.NEW_LINE);

		for (int i = 0; i < (afterLineBreakCount - beforeLineBreakCount); i++) {
			indent = "\t" + indent;
		}

		return indent;
	}

	protected void appendNewLine(
		StringBundler sb, JavaTerm javaTerm, String indent, int maxLineLength) {

		appendNewLine(
			sb, javaTerm, indent, StringPool.BLANK, StringPool.BLANK,
			maxLineLength);
	}

	protected void appendNewLine(
		StringBundler sb, JavaTerm javaTerm, String indent, String prefix,
		String suffix, int maxLineLength) {

		sb = _stripTrailingWhitespace(sb);

		if (sb.index() > 0) {
			indent = adjustIndent(sb, indent);

			sb.append("\n");
		}

		sb.append(javaTerm.toString(indent, prefix, suffix, maxLineLength));
	}

	protected void appendNewLine(
		StringBundler sb, List<? extends JavaTerm> list, String indent,
		int maxLineLength) {

		appendNewLine(
			sb, list, indent, StringPool.BLANK, StringPool.BLANK,
			maxLineLength);
	}

	protected void appendNewLine(
		StringBundler sb, List<? extends JavaTerm> list, String indent,
		String prefix, String suffix, int maxLineLength) {

		appendNewLine(
			sb, list, StringPool.COMMA_AND_SPACE, indent, prefix, suffix,
			maxLineLength);
	}

	protected void appendNewLine(
		StringBundler sb, List<? extends JavaTerm> list, String delimeter,
		String indent, String prefix, String suffix, int maxLineLength) {

		sb = _stripTrailingWhitespace(sb);

		if (sb.index() > 0) {
			sb.append("\n");
		}

		sb.append(indent);

		String newLinePrefix = prefix;

		for (int i = 0;; i++) {
			JavaTerm javaTerm = list.get(i);

			if (i == 1) {
				newLinePrefix = _convertToWhitespace(prefix);

				prefix = StringPool.BLANK;
			}

			if (i == (list.size() - 1)) {
				if (!appendSingleLine(
						sb, javaTerm, prefix, suffix, maxLineLength)) {

					appendNewLine(
						sb, javaTerm, indent, newLinePrefix, suffix,
						maxLineLength);
				}

				return;
			}

			if (appendSingleLine(
					sb, javaTerm, prefix, delimeter, maxLineLength)) {

				continue;
			}

			sb = _stripTrailingWhitespace(sb);

			sb.append("\n");
			sb.append(indent);
			sb.append(newLinePrefix);

			if (!appendSingleLine(
					sb, javaTerm, prefix, delimeter, maxLineLength)) {

				appendNewLine(
					sb, javaTerm, indent, newLinePrefix,
					StringUtil.trimTrailing(delimeter), maxLineLength);

				sb.append("\n");
				sb.append(indent);
				sb.append(newLinePrefix);
			}
		}
	}

	protected boolean appendSingleLine(
		StringBundler sb, JavaTerm javaTerm, int maxLineLength) {

		return appendSingleLine(
			sb, javaTerm, StringPool.BLANK, StringPool.BLANK, maxLineLength);
	}

	protected boolean appendSingleLine(
		StringBundler sb, JavaTerm javaTerm, String prefix, String suffix,
		int maxLineLength) {

		return _appendSingleLine(
			sb, javaTerm.toString(), prefix, suffix, maxLineLength);
	}

	protected boolean appendSingleLine(
		StringBundler sb, List<? extends JavaTerm> list, int maxLineLength) {

		return appendSingleLine(
			sb, list, StringPool.BLANK, StringPool.BLANK, maxLineLength);
	}

	protected boolean appendSingleLine(
		StringBundler sb, List<? extends JavaTerm> list, String delimeter,
		int maxLineLength) {

		return appendSingleLine(
			sb, list, delimeter, StringPool.BLANK, StringPool.BLANK,
			maxLineLength);
	}

	protected boolean appendSingleLine(
		StringBundler sb, List<? extends JavaTerm> list, String prefix,
		String suffix, int maxLineLength) {

		return appendSingleLine(
			sb, list, StringPool.COMMA_AND_SPACE, prefix, suffix,
			maxLineLength);
	}

	protected boolean appendSingleLine(
		StringBundler sb, List<? extends JavaTerm> list, String delimeter,
		String prefix, String suffix, int maxLineLength) {

		return _appendSingleLine(
			sb, ListUtil.toString(list, StringPool.BLANK, delimeter), prefix,
			suffix, maxLineLength);
	}

	protected String appendWithLineBreak(
		StringBundler sb, JavaTerm javaTerm, String indent, String prefix,
		String suffix, int maxLineLength) {

		indent = StringUtil.replaceFirst(indent, "\t", "");

		String lastLine = getLastLine(sb);

		if (Validator.isNull(StringUtil.trim(lastLine))) {
			sb = _stripTrailingWhitespace(sb);

			if (sb.index() > 0) {
				sb.append("\n");
			}

			String s = javaTerm.toString(
				indent, prefix, suffix, maxLineLength, true);

			sb.append(s);
		}
		else {
			String javaTermContent = javaTerm.toString(
				indent, "", suffix, maxLineLength, true);

			javaTermContent = StringUtil.replaceFirst(
				javaTermContent, indent, prefix);

			String firstLineJavaTermContent = _getFirstLine(javaTermContent);

			String s = lastLine + firstLineJavaTermContent;

			lastLine = StringUtil.trim(lastLine);

			if ((getLineLength(s) > maxLineLength) ||
				((lastLine.endsWith("=") || lastLine.endsWith(")")) &&
				 (firstLineJavaTermContent.endsWith(".") ||
				  firstLineJavaTermContent.matches("\\s*\\([^\\)]+\\)?")))) {

				appendNewLine(
					sb, javaTerm, "\t" + indent, prefix, suffix, maxLineLength);
			}
			else {
				sb.append(javaTermContent);
			}
		}

		indent = "\t" + getIndent(getLastLine(sb));

		if (javaTerm instanceof JavaType) {
			return _trimTrailingSpaces(indent);
		}

		return indent;
	}

	protected String getIndent(String s) {
		StringBundler sb = new StringBundler(s.length());

		for (char c : s.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				return sb.toString();
			}

			sb.append(c);
		}

		return s;
	}

	protected String getLastLine(StringBundler sb) {
		return _getLastLine(sb.toString());
	}

	protected int getLineLength(String line) {
		int lineLength = 0;

		int tabLength = 4;

		for (char c : line.toCharArray()) {
			if (c == CharPool.TAB) {
				for (int i = 0; i < tabLength; i++) {
					lineLength++;
				}

				tabLength = 4;
			}
			else {
				lineLength++;

				tabLength--;

				if (tabLength <= 0) {
					tabLength = 4;
				}
			}
		}

		return lineLength;
	}

	private boolean _appendSingleLine(
		StringBundler sb, String s, String prefix, String suffix,
		int maxLineLength) {

		int index = sb.index();

		sb.append(prefix);
		sb.append(s);
		sb.append(suffix);

		if ((maxLineLength == -1) ||
			(getLineLength(getLastLine(sb)) <= maxLineLength)) {

			return true;
		}

		if (suffix.length() > 0) {
			sb.setIndex(sb.index() - 1);

			sb.append(StringUtil.trimTrailing(suffix));

			if (getLineLength(getLastLine(sb)) <= maxLineLength) {
				return true;
			}
		}

		sb.setIndex(index);

		return false;
	}

	private String _convertToWhitespace(String s) {
		StringBundler sb = new StringBundler(s.length());

		int i = getLineLength(s);

		while (i >= 4) {
			sb.append(StringPool.TAB);

			i -= 4;
		}

		for (int j = 0; j < i; j++) {
			sb.append(StringPool.SPACE);
		}

		return sb.toString();
	}

	private String _getFirstLine(String s) {
		int x = s.indexOf("\n");

		if (x != -1) {
			return s.substring(0, x);
		}

		return s;
	}

	private String _getLastLine(String s) {
		int x = s.lastIndexOf("\n");

		if (x != -1) {
			return s.substring(x + 1);
		}

		return s;
	}

	private String _getLeadingWhitespace(String s) {
		StringBundler sb = new StringBundler();

		for (char c : s.toCharArray()) {
			if (Character.isWhitespace(c)) {
				sb.append(c);
			}
			else {
				return sb.toString();
			}
		}

		return s;
	}

	private StringBundler _stripTrailingWhitespace(StringBundler sb) {
		while (true) {
			if (sb.index() == 0) {
				return sb;
			}

			String trimmedLastAppend = StringUtil.trimTrailing(
				sb.stringAt(sb.index() - 1));

			if (Validator.isNotNull(trimmedLastAppend)) {
				sb.setStringAt(trimmedLastAppend, sb.index() - 1);

				return sb;
			}

			sb.setIndex(sb.index() - 1);
		}
	}

	private String _trimTrailingSpaces(String s) {
		while (true) {
			if (s.charAt(s.length() - 1) != CharPool.SPACE) {
				return s;
			}

			s = s.substring(0, s.length() - 1);
		}
	}

	private Position _endPosition;
	private Position _startPosition;

}