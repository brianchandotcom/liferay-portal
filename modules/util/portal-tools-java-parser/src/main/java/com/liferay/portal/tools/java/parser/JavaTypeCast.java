/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaTypeCast extends BaseJavaExpression {

	public JavaTypeCast(
		List<JavaType> javaTypes, JavaExpression valueJavaExpression) {

		_javaTypes = javaTypes;
		_valueJavaExpression = valueJavaExpression;
	}

	public JavaExpression getValueJavaExpression() {
		return _valueJavaExpression;
	}

	@Override
	public boolean hasSurroundingParentheses() {
		if (getChainedJavaExpression() != null) {
			return true;
		}

		return super.hasSurroundingParentheses();
	}

	@Override
	public void setSurroundingParentheses() {
		if (_valueJavaExpression instanceof JavaTernaryOperator) {
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

		indent = append(
			sb, _javaTypes, " & ", indent, prefix + "(", ")", maxLineLength);

		if (forceLineBreak && (getChainedJavaExpression() == null)) {
			appendWithLineBreak(
				sb, _valueJavaExpression, indent, "", suffix, maxLineLength);
		}
		else {
			append(
				sb, _valueJavaExpression, indent, "", suffix, maxLineLength,
				false);
		}

		return sb.toString();
	}

	private final List<JavaType> _javaTypes;
	private final JavaExpression _valueJavaExpression;

}