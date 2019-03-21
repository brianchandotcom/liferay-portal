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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaCatchStatement extends BaseJavaTerm {

	public JavaCatchStatement(
		List<JavaSimpleValue> modifiers, String parameterName,
		List<JavaType> parameterJavaTypes) {

		_modifiers = modifiers;
		_parameterName = new JavaSimpleValue(parameterName);
		_parameterJavaTypes = parameterJavaTypes;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		prefix = prefix + "catch (";

		if (ListUtil.isNotEmpty(_modifiers)) {
			appendSingleLine(sb, _modifiers, " ", prefix, " ", maxLineLength);

			prefix = StringPool.BLANK;
		}

		indent = append(
			sb, _parameterJavaTypes, " | ", indent, prefix, " ", maxLineLength);

		append(sb, _parameterName, indent, "", ")" + suffix, maxLineLength);

		return sb.toString();
	}

	private final List<JavaSimpleValue> _modifiers;
	private final List<JavaType> _parameterJavaTypes;
	private final JavaSimpleValue _parameterName;

}