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
public class JavaArrayElement extends BaseJavaExpression {

	public JavaArrayElement(
		JavaExpression arrayJavaExpression,
		List<JavaExpression> indexValueJavaExpressions) {

		_arrayJavaExpression = arrayJavaExpression;
		_indexValueJavaExpressions = indexValueJavaExpressions;
	}

	@Override
	public void setSurroundingParentheses() {
		if (_arrayJavaExpression instanceof JavaTypeCast) {
			_arrayJavaExpression.setHasSurroundingParentheses(true);
		}
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (_indexValueJavaExpressions.isEmpty()) {
			append(
				sb, _arrayJavaExpression, indent, prefix, suffix,
				maxLineLength);

			return sb.toString();
		}

		append(sb, _arrayJavaExpression, indent, prefix, "", maxLineLength);

		for (int i = 0; i < _indexValueJavaExpressions.size(); i++) {
			if (i == (_indexValueJavaExpressions.size() - 1)) {
				append(
					sb, _indexValueJavaExpressions.get(i), indent, "[",
					"]" + suffix, maxLineLength);
			}
			else {
				append(
					sb, _indexValueJavaExpressions.get(i), indent, "[", "]",
					maxLineLength);
			}
		}

		return sb.toString();
	}

	private final JavaExpression _arrayJavaExpression;
	private final List<JavaExpression> _indexValueJavaExpressions;

}