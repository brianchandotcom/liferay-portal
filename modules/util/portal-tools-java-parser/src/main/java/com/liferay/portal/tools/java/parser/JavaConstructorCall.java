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