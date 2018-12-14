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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaCatchStatement extends BaseJavaTerm {

	public void setModifiers(List<JavaSimpleValue> modifiers) {
		_modifiers = modifiers;
	}

	public void setParameterName(String parameterName) {
		_parameterName = new JavaSimpleValue(parameterName);
	}

	public void setParameterTypeNames(
		List<JavaSimpleValue> parameterTypeNames) {

		_parameterTypeNames = parameterTypeNames;
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

		append(
			sb, _parameterTypeNames, " | ", indent, prefix,
			" " + _parameterName.toString("", "", ")" + suffix, -1),
			maxLineLength);

		return sb.toString();
	}

	private List<JavaSimpleValue> _modifiers;
	private JavaSimpleValue _parameterName;
	private List<JavaSimpleValue> _parameterTypeNames;

}