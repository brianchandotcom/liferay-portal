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

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public abstract class BaseJavaTerm implements JavaTerm {

	@Override
	public String toString() {
		return toString(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, -1);
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		return null;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		return toString(indent, prefix, suffix, maxLineLength);
	}

	protected int append(
		StringBundler sb, JavaTerm javaTerm, String indent, int maxLineLength) {

		return append(sb, javaTerm, indent, maxLineLength, true);
	}

	protected int append(
		StringBundler sb, JavaTerm javaTerm, String indent, int maxLineLength,
		boolean newLine) {

		return append(
			sb, javaTerm, indent, StringPool.BLANK, StringPool.BLANK,
			maxLineLength, newLine);
	}

	protected int append(
		StringBundler sb, JavaTerm javaTerm, String indent, String prefix,
		String suffix, int maxLineLength) {

		return append(
			sb, javaTerm, indent, prefix, suffix, maxLineLength, true);
	}

	protected int append(
		StringBundler sb, JavaTerm javaTerm, String indent, String prefix,
		String suffix, int maxLineLength, boolean newLine) {

		if (appendSingleLine(sb, javaTerm, prefix, suffix, maxLineLength)) {
			return APPENDED_SINGLE_LINE;
		}

		sb = _stripTrailingWhitespace(sb);

		if (newLine) {
			if (Validator.isNull(sb.toString())) {
				sb.append(
					javaTerm.toString(
						indent, StringUtil.trimLeading(prefix), suffix,
						maxLineLength));
			}
			else {
				appendNewLine(
					sb, javaTerm, "\t" + indent, prefix, suffix, maxLineLength);
			}
		}
		else {
			_appendWithLineBreak(
				sb, javaTerm, indent, prefix, suffix, maxLineLength);
		}

		return APPENDED_NEW_LINE;
	}

	protected int append(
		StringBundler sb, List<? extends JavaTerm> list, String indent,
		int maxLineLength) {

		return append(
			sb, list, indent, StringPool.BLANK, StringPool.BLANK,
			maxLineLength);
	}

	protected int append(
		StringBundler sb, List<? extends JavaTerm> list, String indent,
		String prefix, String suffix, int maxLineLength) {

		return append(
			sb, list, StringPool.COMMA_AND_SPACE, indent, prefix, suffix,
			maxLineLength);
	}

	protected int append(
		StringBundler sb, List<? extends JavaTerm> list, String delimeter,
		String indent, String prefix, String suffix, int maxLineLength) {

		if (appendSingleLine(
				sb, list, delimeter, prefix, suffix, maxLineLength)) {

			return APPENDED_SINGLE_LINE;
		}

		sb = _stripTrailingWhitespace(sb);

		appendNewLine(
			sb, list, delimeter, "\t" + indent, prefix, suffix, maxLineLength);

		return APPENDED_NEW_LINE;
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

		sb.append("\n");
		sb.append(
			javaTerm.toString(
				indent, StringUtil.trimLeading(prefix), suffix, maxLineLength));
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

		sb.append("\n");
		sb.append(indent);

		for (int i = 0;; i++) {
			if (i > 0) {
				indent += _getPrefixWhitespace(prefix);

				prefix = StringPool.BLANK;
			}

			JavaTerm javaTerm = list.get(i);

			if (i == (list.size() - 1)) {
				append(
					sb, javaTerm, StringUtil.replaceFirst(indent, "\t", ""),
					prefix, StringUtil.trimTrailing(suffix), maxLineLength);

				return;
			}

			if (appendSingleLine(
					sb, javaTerm, prefix, delimeter, maxLineLength) ||
				appendSingleLine(
					sb, javaTerm, prefix, StringUtil.trimTrailing(delimeter),
					maxLineLength)) {

				continue;
			}

			if (i > 0) {
				sb = _stripTrailingWhitespace(sb);

				sb.append("\n");
				sb.append(indent);
			}

			if (appendSingleLine(
					sb, javaTerm, prefix, delimeter, maxLineLength) ||
				appendSingleLine(
					sb, javaTerm, prefix, StringUtil.trimTrailing(delimeter),
					maxLineLength)) {

				continue;
			}

			append(
				sb, javaTerm, StringUtil.replaceFirst(indent, "\t", ""), prefix,
				delimeter, maxLineLength);

			sb = _stripTrailingWhitespace(sb);

			sb.append("\n");
			sb.append(indent);
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

	protected static final int APPENDED_NEW_LINE = 0;

	protected static final int APPENDED_SINGLE_LINE = 1;

	private boolean _appendSingleLine(
		StringBundler sb, String s, String prefix, String suffix,
		int maxLineLength) {

		int index = sb.index();

		if (Validator.isNull(StringUtil.trim(_getLastLine(sb)))) {
			sb.append(StringUtil.trimLeading(prefix));
		}
		else {
			sb.append(prefix);
		}

		sb.append(s);
		sb.append(suffix);

		if ((maxLineLength == -1) ||
			(getLineLength(_getLastLine(sb)) <= maxLineLength)) {

			return true;
		}

		sb.setIndex(index);

		return false;
	}

	private void _appendWithLineBreak(
		StringBundler sb, JavaTerm javaTerm, String indent, String prefix,
		String suffix, int maxLineLength) {

		String lastLine = _getLastLine(sb);

		if (Validator.isNull(StringUtil.trim(lastLine))) {
			sb = _stripTrailingWhitespace(sb);

			if (sb.index() > 0) {
				sb.append("\n");
			}

			sb.append(
				javaTerm.toString(
					indent, StringUtil.trimLeading(prefix), suffix,
					maxLineLength, true));

			return;
		}

		String javaTermContent = javaTerm.toString(
			indent, "", suffix, maxLineLength, true);

		javaTermContent = StringUtil.replaceFirst(
			javaTermContent, indent, prefix);

		String s = lastLine + _getFirstLine(javaTermContent);

		if (getLineLength(s) <= maxLineLength) {
			sb.append(javaTermContent);

			return;
		}

		appendNewLine(
			sb, javaTerm, indent + "\t", StringUtil.trimLeading(prefix), suffix,
			maxLineLength);

		return;
	}

	private String _getFirstLine(String s) {
		int x = s.indexOf("\n");

		if (x != -1) {
			return s.substring(0, x);
		}

		return s;
	}

	private String _getLastLine(StringBundler sb) {
		String s = sb.toString();

		int x = s.lastIndexOf("\n");

		if (x != -1) {
			return s.substring(x);
		}

		return s;
	}

	private String _getPrefixWhitespace(String prefix) {
		StringBundler sb = new StringBundler(prefix.length());

		int i = getLineLength(StringUtil.trimLeading(prefix));

		while (i >= 4) {
			sb.append(StringPool.TAB);

			i -= 4;
		}

		for (int j = 0; j < i; j++) {
			sb.append(StringPool.SPACE);
		}

		return sb.toString();
	}

	private StringBundler _stripTrailingWhitespace(StringBundler sb) {
		if (sb.index() == 0) {
			return sb;
		}

		String lastAppend = sb.stringAt(sb.index() - 1);

		if (Validator.isNotNull(StringUtil.trim(lastAppend))) {
			sb.setStringAt(StringUtil.trimTrailing(lastAppend), sb.index() - 1);

			return sb;
		}

		for (int i = sb.index() - 1;; i--) {
			if (i == 0) {
				sb.setIndex(0);

				return sb;
			}

			if (Objects.equals(sb.stringAt(i), "\n")) {
				sb.setIndex(i);

				return _stripTrailingWhitespace(sb);
			}
		}
	}

}