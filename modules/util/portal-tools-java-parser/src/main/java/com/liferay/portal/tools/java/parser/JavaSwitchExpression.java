/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Alan Huang
 */
public class JavaSwitchExpression extends BaseJavaExpression {

	public JavaSwitchExpression(JavaExpression switchJavaExpression) {
		_switchJavaExpression = switchJavaExpression;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		String originalIndent = indent;

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		append(
			sb, _switchJavaExpression, indent, prefix + "switch (", ") ",
			maxLineLength);

		sb.append("{\n");
		sb.append(NESTED_CODE_BLOCK);
		sb.append("\n");
		sb.append(originalIndent);
		sb.append("}");
		sb.append(suffix);

		return sb.toString();
	}

	protected static final String NESTED_CODE_BLOCK =
		"${JAVA_SWITCH_EXPRESSION_NESTED_CODE_BLOCK}";

	private final JavaExpression _switchJavaExpression;

}