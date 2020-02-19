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