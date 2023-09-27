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
public class JavaConstructorCall extends BaseJavaTerm {

	public JavaConstructorCall(
		List<JavaExpression> parameterValueJavaExpressions, boolean superCall) {

		_parameterValueJavaExpressions = parameterValueJavaExpressions;
		_superCall = superCall;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		if (_superCall) {
			sb.append("super(");
		}
		else {
			sb.append("this(");
		}

		if (_parameterValueJavaExpressions.isEmpty()) {
			sb.append(")");
			sb.append(suffix);

			return sb.toString();
		}

		append(
			sb, _parameterValueJavaExpressions, indent, prefix, ")" + suffix,
			maxLineLength);

		return sb.toString();
	}

	private final List<JavaExpression> _parameterValueJavaExpressions;
	private final boolean _superCall;

}