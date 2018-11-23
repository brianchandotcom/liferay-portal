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

import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaClassCall extends JavaExpression {

	public JavaClassCall(String className) {
		_className = new JavaSimpleValue(className);
	}

	public void setGenericJavaTypes(List<JavaType> genericJavaTypes) {
		_genericJavaTypes = genericJavaTypes;
	}

	public void setParameterValueJavaExpressions(
		List<JavaExpression> parameterValueJavaExpressions) {

		_parameterValueJavaExpressions = parameterValueJavaExpressions;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (_genericJavaTypes == null) {
			if (_parameterValueJavaExpressions.isEmpty()) {
				append(
					sb, _className, indent, prefix, "()" + suffix,
					maxLineLength, !forceLineBreak);

				return sb.toString();
			}

			indent = append(
				sb, _className, indent, prefix, "(", maxLineLength,
				!forceLineBreak);
		}
		else {
			indent = append(
				sb, _className, indent, prefix, "", maxLineLength,
				!forceLineBreak);

			if (_parameterValueJavaExpressions.isEmpty()) {
				append(
					sb, _genericJavaTypes, indent, "<", ">()" + suffix,
					maxLineLength);

				return sb.toString();
			}

			indent = append(
				sb, _genericJavaTypes, indent, "<", ">(", maxLineLength);
		}

		if (forceLineBreak) {
			appendNewLine(
				sb, _parameterValueJavaExpressions, indent, "", ")" + suffix,
				maxLineLength);
		}
		else {
			append(
				sb, _parameterValueJavaExpressions, indent, "", ")" + suffix,
				maxLineLength);
		}

		return sb.toString();
	}

	private final JavaSimpleValue _className;
	private List<JavaType> _genericJavaTypes;
	private List<JavaExpression> _parameterValueJavaExpressions;

}