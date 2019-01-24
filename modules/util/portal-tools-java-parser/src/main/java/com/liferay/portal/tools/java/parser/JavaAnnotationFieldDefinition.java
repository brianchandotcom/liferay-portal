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
public class JavaAnnotationFieldDefinition extends BaseJavaTerm {

	public JavaAnnotationFieldDefinition(
		List<JavaAnnotation> javaAnnotations, JavaSignature javaSignature) {

		_javaAnnotations = javaAnnotations;
		_javaSignature = javaSignature;
	}

	public void setDefaultJavaExpression(JavaExpression defaultJavaExpression) {
		_defaultJavaExpression = defaultJavaExpression;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

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

		if (_defaultJavaExpression == null) {
			appendNewLine(
				sb, _javaSignature, indent, prefix, suffix, maxLineLength);
		}
		else {
			appendNewLine(
				sb, _javaSignature, indent, prefix, " ", maxLineLength);

			indent = "\t" + trimTrailingSpaces(getIndent(getLastLine(sb)));

			append(
				sb, _defaultJavaExpression, indent, "default ", suffix,
				maxLineLength);
		}

		return sb.toString();
	}

	private JavaExpression _defaultJavaExpression;
	private final List<JavaAnnotation> _javaAnnotations;
	private final JavaSignature _javaSignature;

}