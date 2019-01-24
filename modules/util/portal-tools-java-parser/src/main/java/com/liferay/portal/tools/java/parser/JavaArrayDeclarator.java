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
public class JavaArrayDeclarator extends JavaExpression {

	public JavaArrayDeclarator(
		String className, List<JavaExpression> dimensionValueJavaExpressions) {

		_className = new JavaSimpleValue(className);
		_dimensionValueJavaExpressions = dimensionValueJavaExpressions;
	}

	public void setGenericJavaTypes(List<JavaType> genericJavaTypes) {
		_genericJavaTypes = genericJavaTypes;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);
		sb.append(prefix);

		append(sb, _className, indent, maxLineLength);

		if (_genericJavaTypes != null) {
			append(sb, _genericJavaTypes, indent, "<", ">", maxLineLength);
		}

		int index = sb.index();

		outerLoop:
		while (true) {
			for (int i = 0; i < _dimensionValueJavaExpressions.size(); i++) {
				String expressionSuffix = "]";

				if (i == (_dimensionValueJavaExpressions.size() - 1)) {
					expressionSuffix += suffix;
				}

				if (!appendSingleLine(
						sb, _dimensionValueJavaExpressions.get(i), "[",
						expressionSuffix, maxLineLength)) {

					sb.setIndex(index);

					indent = "\t" + indent;

					break outerLoop;
				}
			}

			return sb.toString();
		}

		sb.append("\n");
		sb.append(indent);

		for (int i = 0; i < _dimensionValueJavaExpressions.size(); i++) {
			String expressionSuffix = "]";

			if (i == (_dimensionValueJavaExpressions.size() - 1)) {
				expressionSuffix += suffix;
			}

			append(
				sb, _dimensionValueJavaExpressions.get(i), indent, "[",
				expressionSuffix, maxLineLength);
		}

		return sb.toString();
	}

	private final JavaSimpleValue _className;
	private final List<JavaExpression> _dimensionValueJavaExpressions;
	private List<JavaType> _genericJavaTypes;

}