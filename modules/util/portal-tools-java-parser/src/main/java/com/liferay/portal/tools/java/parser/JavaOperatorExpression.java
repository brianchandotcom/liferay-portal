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

import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JavaOperatorExpression extends JavaExpression {

	public JavaOperatorExpression(JavaOperator javaOperator) {
		_javaOperator = javaOperator;
	}

	@Override
	public String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);
		sb.append(prefix);

		if (_leftHandJavaExpression != null) {
			if (_rightHandJavaExpression != null) {
				if (Objects.equals(
						append(
							sb, _leftHandJavaExpression, indent, "",
							" " + _javaOperator.getValue(), maxLineLength,
							false),
						APPENDED_NEW_LINE)) {

					indent += "\t";
				}

				if (forceLineBreak ||
					!(_rightHandJavaExpression instanceof
						JavaOperatorExpression)) {

					append(
						sb, _rightHandJavaExpression, indent, " ", suffix,
						maxLineLength);
				}
				else {
					append(
						sb, _rightHandJavaExpression, indent, " ", suffix,
						maxLineLength, false);
				}
			}
			else {
				append(
					sb, _leftHandJavaExpression, indent, "",
					_javaOperator.getValue() + suffix, maxLineLength, false);
			}
		}
		else {
			append(
				sb, _rightHandJavaExpression, indent, _javaOperator.getValue(),
				suffix, maxLineLength, false);
		}

		return sb.toString();
	}

	public void setLeftHandJavaExpression(
		JavaExpression leftHandJavaExpression) {

		_leftHandJavaExpression = leftHandJavaExpression;
	}

	public void setRightHandJavaExpression(
		JavaExpression rightHandJavaExpression) {

		_rightHandJavaExpression = rightHandJavaExpression;
	}

	private final JavaOperator _javaOperator;
	private JavaExpression _leftHandJavaExpression;
	private JavaExpression _rightHandJavaExpression;

}