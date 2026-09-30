/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JavaRecordPattern extends BaseJavaExpression {

	public JavaRecordPattern(
		JavaType javaType, List<JavaTerm> componentJavaTerms) {

		_javaType = javaType;
		_componentJavaTerms = componentJavaTerms;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		indent = append(
			sb, _javaType, indent, prefix, "(", maxLineLength, false);

		if (forceLineBreak) {
			appendNewLine(
				sb, _componentJavaTerms, indent, "", ")" + suffix,
				maxLineLength);
		}
		else {
			append(
				sb, _componentJavaTerms, indent, "", ")" + suffix,
				maxLineLength);
		}

		return sb.toString();
	}

	private final List<JavaTerm> _componentJavaTerms;
	private final JavaType _javaType;

}