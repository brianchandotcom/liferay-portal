/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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