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
public class JavaArrayDeclarator extends BaseJavaExpression {

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