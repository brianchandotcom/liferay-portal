/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Alan Huang
 */
public class JavaGuardedPattern extends BaseJavaExpression {

	public JavaGuardedPattern(
		JavaExpression guardJavaExpression, JavaTerm patternJavaTerm) {

		_guardJavaExpression = guardJavaExpression;
		_patternJavaTerm = patternJavaTerm;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		indent = append(
			sb, _patternJavaTerm, indent, "", " when ", maxLineLength);

		append(sb, _guardJavaExpression, indent, "", suffix, maxLineLength);

		return sb.toString();
	}

	private final JavaExpression _guardJavaExpression;
	private final JavaTerm _patternJavaTerm;

}