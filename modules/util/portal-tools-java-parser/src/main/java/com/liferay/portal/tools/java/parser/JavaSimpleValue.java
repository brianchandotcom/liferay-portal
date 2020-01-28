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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

/**
 * @author Hugo Huijser
 */
public class JavaSimpleValue extends JavaExpression {

	public JavaSimpleValue(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		String s = StringBundler.concat(indent, prefix, _name, suffix);

		if ((maxLineLength == -1) || (getLineLength(s) <= maxLineLength)) {
			return s;
		}

		int x = _name.length() + 1;

		while (true) {
			x = _name.lastIndexOf(".", x - 1);

			if (x == -1) {
				return StringBundler.concat(indent, prefix, _name, suffix);
			}

			if (ToolsUtil.isInsideQuotes(_name, x)) {
				continue;
			}

			String firstLine = StringBundler.concat(
				indent, prefix, _name.substring(0, x + 1));

			if (getLineLength(firstLine) > maxLineLength) {
				continue;
			}

			String secondLineIndent = "\t" + indent;

			String trimmedFirstLine = StringUtil.trim(firstLine);

			if (trimmedFirstLine.startsWith("catch (")) {
				secondLineIndent += "\t\t";
			}
			else if (trimmedFirstLine.startsWith("else if (")) {
				secondLineIndent += "\t\t";
			}
			else if (trimmedFirstLine.startsWith("extends ")) {
				secondLineIndent += "\t\t";
			}
			else if (trimmedFirstLine.startsWith("for (") &&
					 !trimmedFirstLine.endsWith(";")) {

				secondLineIndent += "\t";
			}
			else if (trimmedFirstLine.startsWith("if (")) {
				secondLineIndent += "\t";
			}
			else if (trimmedFirstLine.startsWith("implements ")) {
				secondLineIndent += "\t\t   ";
			}
			else if (trimmedFirstLine.startsWith("try (") &&
					 !trimmedFirstLine.endsWith(";")) {

				secondLineIndent += "\t";
			}
			else if (trimmedFirstLine.startsWith("while (")) {
				secondLineIndent += "\t\t";
			}

			return StringBundler.concat(
				firstLine, "\n", secondLineIndent, _name.substring(x + 1),
				suffix);
		}
	}

	private final String _name;

}