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
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaSwitchCaseStatement extends BaseJavaTerm {

	public void addDefault() {
		_hasDefault = true;
	}

	public void addSwitchCaseJavaExpression(
		JavaExpression switchCaseJavaExpression) {

		_switchCaseJavaExpressions.add(switchCaseJavaExpression);
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		for (JavaExpression switchCaseJavaExpression :
				_switchCaseJavaExpressions) {

			appendNewLine(
				sb, switchCaseJavaExpression, indent, prefix + "case ", suffix,
				maxLineLength);

			prefix = StringPool.BLANK;
		}

		if (_hasDefault) {
			if (sb.index() > 0) {
				sb.append("\n");
			}

			sb.append(prefix);
			sb.append(indent);
			sb.append("default");
			sb.append(suffix);
		}

		return sb.toString();
	}

	private boolean _hasDefault;
	private final List<JavaExpression> _switchCaseJavaExpressions =
		new ArrayList<>();

}