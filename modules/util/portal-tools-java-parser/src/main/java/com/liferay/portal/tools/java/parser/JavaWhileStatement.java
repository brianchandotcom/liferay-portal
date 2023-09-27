/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Hugo Huijser
 */
public class JavaWhileStatement extends BaseJavaLoopStatement {

	public JavaWhileStatement(JavaExpression conditionJavaExpression) {
		_conditionJavaExpression = conditionJavaExpression;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = appendLabelName(indent);

		appendNewLine(
			sb, _conditionJavaExpression, indent, "while (", ")" + suffix,
			maxLineLength);

		return sb.toString();
	}

	private final JavaExpression _conditionJavaExpression;

}