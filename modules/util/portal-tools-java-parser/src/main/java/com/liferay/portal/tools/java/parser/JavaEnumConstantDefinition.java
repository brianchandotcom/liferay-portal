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

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaEnumConstantDefinition extends BaseJavaTerm {

	public JavaEnumConstantDefinition(
		String name, List<JavaAnnotation> javaAnnotations) {

		_name = new JavaSimpleValue(name);
		_javaAnnotations = javaAnnotations;
	}

	public void setHasBody(boolean hasBody) {
		_hasBody = hasBody;
	}

	public void setParameterValueJavaExpressions(
		List<JavaExpression> parameterValueJavaExpressions) {

		_parameterValueJavaExpressions = parameterValueJavaExpressions;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		String originalIndent = indent;
		String originalSuffix = suffix;

		if (_hasBody) {
			suffix = " {";
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < _javaAnnotations.size(); i++) {
			if (i == 0) {
				appendNewLine(
					sb, _javaAnnotations.get(i), indent, prefix, "",
					maxLineLength);

				prefix = StringPool.BLANK;
			}
			else {
				appendNewLine(
					sb, _javaAnnotations.get(i), indent, maxLineLength);
			}
		}

		if (_parameterValueJavaExpressions == null) {
			appendNewLine(sb, _name, indent, prefix, suffix, maxLineLength);
		}
		else {
			appendNewLine(sb, _name, indent, prefix, "(", maxLineLength);

			indent = "\t" + getIndent(getLastLine(sb));

			append(
				sb, _parameterValueJavaExpressions, indent, "", ")" + suffix,
				maxLineLength);
		}

		if (_hasBody) {
			sb.append("\n");
			sb.append(NESTED_CODE_BLOCK);
			sb.append("\n");
			sb.append(originalIndent);
			sb.append("}");
			sb.append(originalSuffix);
		}

		return sb.toString();
	}

	protected static final String NESTED_CODE_BLOCK =
		"${JAVA_ENUM_CONSTANT_DEFINITION_NESTED_CODE_BLOCK}";

	private boolean _hasBody;
	private final List<JavaAnnotation> _javaAnnotations;
	private final JavaSimpleValue _name;
	private List<JavaExpression> _parameterValueJavaExpressions;

}