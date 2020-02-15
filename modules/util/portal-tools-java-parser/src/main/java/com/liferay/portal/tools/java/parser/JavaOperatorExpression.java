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
public class JavaOperatorExpression extends BaseJavaExpression {

	public JavaOperatorExpression(JavaOperator javaOperator) {
		_javaOperator = javaOperator;
	}

	public JavaOperator getJavaOperator() {
		return _javaOperator;
	}

	public JavaExpression getLeftHandJavaExpression() {
		return _leftHandJavaExpression;
	}

	public JavaExpression getRightHandJavaExpression() {
		return _rightHandJavaExpression;
	}

	@Override
	public boolean hasSurroundingParentheses() {
		if (getChainedJavaExpression() != null) {
			return true;
		}

		return super.hasSurroundingParentheses();
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
	public void setSurroundingParentheses() {
		_setSurroundingParentheses(_leftHandJavaExpression);
		_setSurroundingParentheses(_rightHandJavaExpression);

		_setSurroundingParenthesesForInstanceofStatement();
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

	private boolean _equalsValueJavaExpression(
		JavaInstanceofStatement javaInstanceofStatement1,
		JavaInstanceofStatement javaInstanceofStatement2) {

		JavaExpression valueJavaExpression1 =
			javaInstanceofStatement1.getValueJavaExpression();
		JavaExpression valueJavaExpression2 =
			javaInstanceofStatement2.getValueJavaExpression();

		return Objects.equals(
			valueJavaExpression1.toString(), valueJavaExpression2.toString());
	}

	private void _setSurroundingParentheses(JavaExpression javaExpression) {
		if (javaExpression == null) {
			return;
		}

		JavaOperator.Category category = _javaOperator.getCategory();

		if (!category.equals(JavaOperator.Category.ASSIGNMENT) &&
			(javaExpression instanceof JavaTernaryOperator)) {

			javaExpression.setHasSurroundingParentheses(true);

			return;
		}

		if (!(javaExpression instanceof JavaOperatorExpression)) {
			return;
		}

		JavaOperatorExpression javaOperatorExpression =
			(JavaOperatorExpression)javaExpression;

		JavaOperator javaOperator = javaOperatorExpression.getJavaOperator();

		JavaOperator.Category javaExpressionCategory =
			javaOperator.getCategory();

		if (javaExpressionCategory.equals(JavaOperator.Category.UNARY) ||
			javaOperator.equals(JavaOperator.UNARY_BITWISE_OPERATOR)) {

			if (!category.equals(JavaOperator.Category.UNARY) &&
				!_javaOperator.equals(JavaOperator.UNARY_BITWISE_OPERATOR)) {

				javaExpression.setHasSurroundingParentheses(false);
			}

			return;
		}

		if (category.equals(JavaOperator.Category.ASSIGNMENT)) {
			javaExpression.setHasSurroundingParentheses(false);

			return;
		}

		if (category.equals(JavaOperator.Category.BITWISE) ||
			category.equals(JavaOperator.Category.RELATIONAL)) {

			javaExpression.setHasSurroundingParentheses(true);

			return;
		}

		if (category.equals(JavaOperator.Category.CONDITIONAL)) {
			if (javaOperator.equals(_javaOperator)) {
				javaExpression.setHasSurroundingParentheses(false);
			}
			else {
				javaExpression.setHasSurroundingParentheses(true);
			}
		}
	}

	private void _setSurroundingParenthesesForInstanceofStatement() {
		if ((_leftHandJavaExpression != null) &&
			(_leftHandJavaExpression instanceof JavaInstanceofStatement)) {

			if (_rightHandJavaExpression == null) {
				_leftHandJavaExpression.setHasSurroundingParentheses(true);

				return;
			}

			if (_rightHandJavaExpression instanceof JavaInstanceofStatement) {
				if (!_equalsValueJavaExpression(
						(JavaInstanceofStatement)_leftHandJavaExpression,
						(JavaInstanceofStatement)_rightHandJavaExpression)) {

					_leftHandJavaExpression.setHasSurroundingParentheses(true);
					_rightHandJavaExpression.setHasSurroundingParentheses(true);
				}

				return;
			}

			if (!(_rightHandJavaExpression instanceof JavaOperatorExpression)) {
				_leftHandJavaExpression.setHasSurroundingParentheses(true);

				return;
			}

			JavaExpression rightHandNeighborJavaExpression =
				_rightHandJavaExpression;

			while (true) {
				if (rightHandNeighborJavaExpression.
						hasSurroundingParentheses()) {

					_leftHandJavaExpression.setHasSurroundingParentheses(true);

					return;
				}

				if (rightHandNeighborJavaExpression instanceof
						JavaInstanceofStatement) {

					if (!_equalsValueJavaExpression(
							(JavaInstanceofStatement)_leftHandJavaExpression,
							(JavaInstanceofStatement)
								rightHandNeighborJavaExpression)) {

						_leftHandJavaExpression.setHasSurroundingParentheses(
							true);
					}

					return;
				}

				if (!(rightHandNeighborJavaExpression instanceof
						JavaOperatorExpression)) {

					_leftHandJavaExpression.setHasSurroundingParentheses(true);

					return;
				}

				JavaOperatorExpression javaOperatorExpression =
					(JavaOperatorExpression)rightHandNeighborJavaExpression;

				rightHandNeighborJavaExpression =
					javaOperatorExpression.getLeftHandJavaExpression();

				if (rightHandNeighborJavaExpression == null) {
					_leftHandJavaExpression.setHasSurroundingParentheses(true);

					return;
				}
			}
		}

		if ((_rightHandJavaExpression != null) &&
			(_rightHandJavaExpression instanceof JavaInstanceofStatement)) {

			if ((_leftHandJavaExpression == null) ||
				!(_leftHandJavaExpression instanceof JavaOperatorExpression)) {

				_rightHandJavaExpression.setHasSurroundingParentheses(true);

				return;
			}

			JavaExpression leftHandNeighborJavaExpression =
				_leftHandJavaExpression;

			while (true) {
				if (leftHandNeighborJavaExpression.
						hasSurroundingParentheses()) {

					_rightHandJavaExpression.setHasSurroundingParentheses(true);

					return;
				}

				if (leftHandNeighborJavaExpression instanceof
						JavaInstanceofStatement) {

					if (!_equalsValueJavaExpression(
							(JavaInstanceofStatement)_rightHandJavaExpression,
							(JavaInstanceofStatement)
								leftHandNeighborJavaExpression)) {

						_rightHandJavaExpression.setHasSurroundingParentheses(
							true);
					}

					return;
				}

				if (!(leftHandNeighborJavaExpression instanceof
						JavaOperatorExpression)) {

					_rightHandJavaExpression.setHasSurroundingParentheses(true);

					return;
				}

				JavaOperatorExpression javaOperatorExpression =
					(JavaOperatorExpression)leftHandNeighborJavaExpression;

				leftHandNeighborJavaExpression =
					javaOperatorExpression.getRightHandJavaExpression();

				if (leftHandNeighborJavaExpression == null) {
					_rightHandJavaExpression.setHasSurroundingParentheses(true);

					return;
				}
			}
		}
	}

	private final JavaOperator _javaOperator;
	private JavaExpression _leftHandJavaExpression;
	private JavaExpression _rightHandJavaExpression;

}