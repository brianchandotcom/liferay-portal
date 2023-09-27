/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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