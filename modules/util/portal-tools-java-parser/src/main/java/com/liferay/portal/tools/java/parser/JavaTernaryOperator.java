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

/**
 * @author Hugo Huijser
 */
public class JavaTernaryOperator extends BaseJavaExpression {

	public JavaTernaryOperator(
		JavaExpression conditionJavaExpression,
		JavaExpression trueValueJavaExpression,
		JavaExpression falseValueJavaExpression) {

		_conditionJavaExpression = conditionJavaExpression;
		_trueValueJavaExpression = trueValueJavaExpression;
		_falseValueJavaExpression = falseValueJavaExpression;
	}

	@Override
	public boolean hasSurroundingParentheses() {
		if (getChainedJavaExpression() != null) {
			return true;
		}

		return super.hasSurroundingParentheses();
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);
		sb.append(prefix);

		append(sb, _conditionJavaExpression, indent, "", " ? ", maxLineLength);
		append(sb, _trueValueJavaExpression, indent, "", " : ", maxLineLength);
		append(
			sb, _falseValueJavaExpression, indent, "", suffix, maxLineLength);

		return sb.toString();
	}

	private final JavaExpression _conditionJavaExpression;
	private final JavaExpression _falseValueJavaExpression;
	private final JavaExpression _trueValueJavaExpression;

}