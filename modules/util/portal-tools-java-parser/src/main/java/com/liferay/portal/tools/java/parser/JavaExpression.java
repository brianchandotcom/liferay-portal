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

/**
 * @author Hugo Huijser
 */
public abstract class JavaExpression extends BaseJavaTerm {

	public abstract String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak);

	public void setChainedJavaExpression(JavaExpression chainedJavaExpression) {
		if (_chainedJavaExpression == null) {
			_chainedJavaExpression = chainedJavaExpression;
		}
		else {
			_chainedJavaExpression.setChainedJavaExpression(
				chainedJavaExpression);
		}
	}

	public void setHasSurroundingParentheses(
		boolean hasSurroundingParentheses, boolean parenthesesIncludeChain) {

		_hasSurroundingParentheses = hasSurroundingParentheses;
		_parenthesesIncludeChain = parenthesesIncludeChain;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		return toString(indent, prefix, suffix, maxLineLength, false);
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		if (_chainedJavaExpression == null) {
			if (!_hasSurroundingParentheses) {
				return getString(
					indent, prefix, suffix, maxLineLength, forceLineBreak);
			}

			return getString(
				indent, prefix + "(", ")" + suffix, maxLineLength,
				forceLineBreak);
		}

		StringBundler sb = new StringBundler();

		if (_hasSurroundingParentheses) {
			if (!_parenthesesIncludeChain) {
				sb.append(
					getString(
						indent, prefix + "(", ").", maxLineLength,
						forceLineBreak));
			}
			else {
				sb.append(
					getString(
						indent, prefix + "(", ".", maxLineLength,
						forceLineBreak));
			}
		}
		else {
			sb.append(
				getString(indent, prefix, ".", maxLineLength, forceLineBreak));
		}

		if (_hasSurroundingParentheses && _parenthesesIncludeChain) {
			append(
				sb, _chainedJavaExpression, indent, "", ")" + suffix,
				maxLineLength, false);
		}
		else {
			append(
				sb, _chainedJavaExpression, indent, "", suffix, maxLineLength,
				false);
		}

		return sb.toString();
	}

	private JavaExpression _chainedJavaExpression;
	private boolean _hasSurroundingParentheses;
	private boolean _parenthesesIncludeChain;

}