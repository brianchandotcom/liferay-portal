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

import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaArrayElement extends JavaExpression {

	public JavaArrayElement(JavaExpression arrayJavaExpression) {
		_arrayJavaExpression = arrayJavaExpression;
	}

	@Override
	public String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent += "\t";

		sb.append(prefix);

		append(sb, _arrayJavaExpression, indent, maxLineLength);

		for (JavaExpression indexValueJavaExpression :
				_indexValueJavaExpressions) {

			append(
				sb, indexValueJavaExpression, indent, "[", "]", maxLineLength);
		}

		sb.append(suffix);

		return sb.toString();
	}

	public void setIndexValueJavaExpressions(
		List<JavaExpression> indexValueJavaExpressions) {

		_indexValueJavaExpressions = indexValueJavaExpressions;
	}

	private final JavaExpression _arrayJavaExpression;
	private List<JavaExpression> _indexValueJavaExpressions;

}