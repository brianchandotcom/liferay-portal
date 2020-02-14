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
public class JavaMethodReference extends JavaExpression {

	public JavaMethodReference(
		String methodName, JavaExpression referenceJavaExpression,
		List<JavaType> genericJavaTypes) {

		_referenceJavaExpression = referenceJavaExpression;
		_genericJavaTypes = genericJavaTypes;

		_methodName = new JavaSimpleValue(methodName);
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		if (_genericJavaTypes != null) {
			append(sb, _referenceJavaExpression, indent, maxLineLength);
			append(sb, _genericJavaTypes, indent, "<", ">::", maxLineLength);
		}
		else {
			append(
				sb, _referenceJavaExpression, indent, "", "::", maxLineLength);
		}

		append(sb, _methodName, indent, "", suffix, maxLineLength);

		return sb.toString();
	}

	private final List<JavaType> _genericJavaTypes;
	private final JavaSimpleValue _methodName;
	private final JavaExpression _referenceJavaExpression;

}