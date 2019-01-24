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

import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JavaOperatorExpression extends JavaExpression {

	public JavaOperatorExpression(JavaOperator javaOperator) {
		_javaOperator = javaOperator;
	}

	public JavaOperator getJavaOperator() {
		return _javaOperator;
	}

	public JavaExpression getRightHandJavaExpression() {
		return _rightHandJavaExpression;
	}

	public void setLeftHandJavaExpression(
		JavaExpression leftHandJavaExpression) {

		_leftHandJavaExpression = leftHandJavaExpression;
	}

	public void setRightHandJavaExpression(
		JavaExpression rightHandJavaExpression) {

		_rightHandJavaExpression = rightHandJavaExpression;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (_leftHandJavaExpression != null) {
			if (_rightHandJavaExpression != null) {
				if (Objects.equals(
						_javaOperator.getCategory(),
						JavaOperator.Category.CONDITIONAL)) {

					if (appendSingleLine(
							sb, _leftHandJavaExpression, prefix,
							" " + _javaOperator.getValue() + " ",
							maxLineLength)) {

						append(
							sb, _rightHandJavaExpression, indent, "", suffix,
							maxLineLength);
					}
					else {
						indent = appendWithLineBreak(
							sb, _leftHandJavaExpression, indent, prefix,
							" " + _javaOperator.getValue() + " ",
							maxLineLength);

						if (Objects.equals(
								getIndent(getLastLine(sb)),
								adjustIndent(sb, indent))) {

							append(
								sb, _rightHandJavaExpression, indent, "",
								suffix, maxLineLength);
						}
						else {
							appendNewLine(
								sb, _rightHandJavaExpression, indent, "",
								suffix, maxLineLength);
						}
					}
				}
				else {
					indent = append(
						sb, _leftHandJavaExpression, indent, prefix,
						" " + _javaOperator.getValue() + " ", maxLineLength,
						false);

					boolean newLine = false;

					if (Objects.equals(
							_javaOperator.getCategory(),
							JavaOperator.Category.ASSIGNMENT)) {

						if (_rightHandJavaExpression instanceof
								JavaOperatorExpression) {

							JavaOperatorExpression javaOperatorExpression =
								(JavaOperatorExpression)
									_rightHandJavaExpression;

							JavaOperator javaOperator =
								javaOperatorExpression.getJavaOperator();

							if (!javaOperator.equals(
									JavaOperator.LOGICAL_COMPLEMENT_OPERATOR)) {

								newLine = true;
							}
						}
					}
					else if (!Objects.equals(
								_javaOperator.getCategory(),
								JavaOperator.Category.RELATIONAL)) {

						newLine = true;
					}

					if (newLine) {
						append(
							sb, _rightHandJavaExpression, indent, "", suffix,
							maxLineLength);
					}
					else {
						appendAssignValue(
							sb, _rightHandJavaExpression, indent, suffix,
							maxLineLength, forceLineBreak);
					}
				}
			}
			else {
				append(
					sb, _leftHandJavaExpression, indent, prefix,
					_javaOperator.getValue() + suffix, maxLineLength, false);
			}
		}
		else if (forceLineBreak) {
			appendWithLineBreak(
				sb, _rightHandJavaExpression, indent,
				prefix + _javaOperator.getValue(), suffix, maxLineLength);
		}
		else {
			append(
				sb, _rightHandJavaExpression, indent,
				prefix + _javaOperator.getValue(), suffix, maxLineLength,
				false);
		}

		return sb.toString();
	}

	private final JavaOperator _javaOperator;
	private JavaExpression _leftHandJavaExpression;
	private JavaExpression _rightHandJavaExpression;

}