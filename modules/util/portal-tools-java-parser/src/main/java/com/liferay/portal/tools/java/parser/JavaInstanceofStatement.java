/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Hugo Huijser
 */
public class JavaInstanceofStatement extends BaseJavaExpression {

	public JavaInstanceofStatement(JavaExpression valueJavaExpression) {
		_valueJavaExpression = valueJavaExpression;
	}

	public JavaExpression getValueJavaExpression() {
		return _valueJavaExpression;
	}

	public void setClassJavaType(JavaType classJavaType) {
		_classJavaType = classJavaType;
	}

	public void setJavaVariableDefinition(
		JavaVariableDefinition javaVariableDefinition) {

		_javaVariableDefinition = javaVariableDefinition;
	}

	@Override
	public void setSurroundingParentheses() {
		if (_valueJavaExpression instanceof JavaOperatorExpression) {
			_valueJavaExpression.setHasSurroundingParentheses(true);
		}
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		indent = append(
			sb, _valueJavaExpression, indent, "", " instanceof ",
			maxLineLength);

		if (_classJavaType != null) {
			append(sb, _classJavaType, indent, "", suffix, maxLineLength);
		}
		else if (_javaVariableDefinition != null) {
			append(
				sb, _javaVariableDefinition, indent, "", suffix, maxLineLength);
		}

		return sb.toString();
	}

	private JavaType _classJavaType;
	private JavaVariableDefinition _javaVariableDefinition;
	private final JavaExpression _valueJavaExpression;

}