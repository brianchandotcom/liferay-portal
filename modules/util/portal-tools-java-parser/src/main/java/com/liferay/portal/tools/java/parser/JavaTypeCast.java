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
public class JavaTypeCast extends JavaExpression {

	@Override
	public String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent += "\t";

		indent = append(
			sb, _javaTypes, " & ", indent, prefix + "(", ")", maxLineLength);

		if (forceLineBreak) {
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

	public JavaExpression getValueJavaExpression() {
		return _valueJavaExpression;
	}

	public void setJavaTypes(List<JavaType> javaTypes) {
		_javaTypes = javaTypes;
	}

	public void setValueJavaExpression(JavaExpression valueJavaExpression) {
		_valueJavaExpression = valueJavaExpression;
	}

	private List<JavaType> _javaTypes;
	private JavaExpression _valueJavaExpression;

}