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
public abstract class BaseJavaExpression
	extends BaseJavaTerm implements JavaExpression {

	@Override
	public JavaExpression getChainedJavaExpression() {
		return _chainedJavaExpression;
	}

	@Override
	public boolean hasSurroundingParentheses() {
		return _hasSurroundingParentheses;
	}

	@Override
	public void setChainedJavaExpression(JavaExpression chainedJavaExpression) {
		if (_chainedJavaExpression == null) {
			_chainedJavaExpression = chainedJavaExpression;
		}
		else {
			_chainedJavaExpression.setChainedJavaExpression(
				chainedJavaExpression);
		}
	}

	@Override
	public void setHasSurroundingParentheses(
		boolean hasSurroundingParentheses) {

		_hasSurroundingParentheses = hasSurroundingParentheses;
	}

	@Override
	public void setSurroundingParentheses() {
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
			if (!hasSurroundingParentheses()) {
				return getString(
					indent, prefix, suffix, maxLineLength, forceLineBreak);
			}

			return getString(
				indent, prefix + "(", ")" + suffix, maxLineLength,
				forceLineBreak);
		}

		StringBundler sb = new StringBundler();

		if (hasSurroundingParentheses()) {
			sb.append(
				getString(
					indent, prefix + "(", ").", maxLineLength, forceLineBreak));
		}
		else {
			sb.append(
				getString(indent, prefix, ".", maxLineLength, forceLineBreak));
		}

		indent = adjustIndent(sb, "\t" + getIndent(getLastLine(sb)));

		if (forceLineBreak) {
			appendWithLineBreak(
				sb, _chainedJavaExpression, indent, "", suffix, maxLineLength);
		}
		else {
			append(
				sb, _chainedJavaExpression, indent, "", suffix, maxLineLength,
				false);
		}

		return sb.toString();
	}

	protected abstract String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak);

	private JavaExpression _chainedJavaExpression;
	private boolean _hasSurroundingParentheses;

}