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
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JavaSignature extends BaseJavaTerm {

	public JavaSignature(String objectName) {
		_objectName = new JavaSimpleValue(objectName);
	}

	public String getIndent() {
		return _indent;
	}

	public void setExceptionJavaExpressions(
		List<JavaExpression> exceptionJavaExpressions) {

		_exceptionJavaExpressions = exceptionJavaExpressions;
	}

	public void setGenericJavaTypes(List<JavaType> genericJavaTypes) {
		_genericJavaTypes = genericJavaTypes;
	}

	public void setIndent(String indent) {
		_indent = indent;
	}

	public void setJavaParameters(List<JavaParameter> javaParameters) {
		_javaParameters = javaParameters;
	}

	public void setModifiers(List<JavaSimpleValue> modifiers) {
		_modifiers = modifiers;
	}

	public void setReturnJavaType(JavaType returnJavaType) {
		_returnJavaType = returnJavaType;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (!_modifiers.isEmpty()) {
			appendSingleLine(sb, _modifiers, " ", "", " ", maxLineLength);
		}

		if (_genericJavaTypes != null) {
			indent = append(
				sb, _genericJavaTypes, indent, "<", "> ", maxLineLength);
		}

		if (_returnJavaType != null) {
			boolean newLine = false;

			if (_genericJavaTypes != null) {
				newLine = true;
			}

			indent = append(
				sb, _returnJavaType, indent, "", " ", maxLineLength, newLine);
		}

		if (_javaParameters.isEmpty()) {
			if (_exceptionJavaExpressions.isEmpty()) {
				if ((_genericJavaTypes == null) &&
					((_returnJavaType == null) ||
					 Objects.equals(_returnJavaType.toString(), "void"))) {

					appendSingleLine(sb, _objectName, "", "()" + suffix, -1);
				}
				else {
					append(
						sb, _objectName, indent, "", "()" + suffix,
						maxLineLength);
				}

				return sb.toString();
			}

			if (!appendSingleLine(sb, _objectName, "", "() ", maxLineLength)) {
				if ((_genericJavaTypes == null) &&
					((_returnJavaType == null) ||
					 Objects.equals(_returnJavaType.toString(), "void"))) {

					appendSingleLine(sb, _objectName, "", "() ", -1);
				}
				else {
					appendNewLine(
						sb, _objectName, indent + "\t", "", "() ",
						maxLineLength);
				}

				appendNewLine(
					sb, _exceptionJavaExpressions, indent, "throws ", suffix,
					maxLineLength);

				return sb.toString();
			}

			append(
				sb, _exceptionJavaExpressions, indent, "throws ", suffix,
				maxLineLength);

			return sb.toString();
		}

		if (_exceptionJavaExpressions.isEmpty()) {
			if (appendSingleLine(sb, _objectName, "", "(", maxLineLength)) {
				append(
					sb, _javaParameters, indent, "", ")" + suffix,
					maxLineLength);

				return sb.toString();
			}

			if ((_genericJavaTypes == null) &&
				((_returnJavaType == null) ||
				 Objects.equals(_returnJavaType.toString(), "void"))) {

				appendSingleLine(sb, _objectName, "", "(", -1);
				appendNewLine(
					sb, _javaParameters, indent, "", ")" + suffix,
					maxLineLength);
			}
			else {
				appendNewLine(sb, _objectName, indent, "", "(", maxLineLength);
				append(
					sb, _javaParameters, indent + "\t", "", ")" + suffix,
					maxLineLength);
			}

			return sb.toString();
		}

		if (appendSingleLine(sb, _objectName, "", "(", maxLineLength)) {
			if (appendSingleLine(sb, _javaParameters, "", ")", maxLineLength)) {
				if (appendSingleLine(
						sb, _exceptionJavaExpressions, " throws ", suffix,
						maxLineLength)) {

					return sb.toString();
				}

				appendNewLine(
					sb, _exceptionJavaExpressions, indent, "throws ", suffix,
					maxLineLength);

				return sb.toString();
			}

			appendNewLine(
				sb, _javaParameters, indent + "\t", "", ")", maxLineLength);
			appendNewLine(
				sb, _exceptionJavaExpressions, indent, "throws ", suffix,
				maxLineLength);

			return sb.toString();
		}

		if ((_genericJavaTypes == null) &&
			((_returnJavaType == null) ||
			 Objects.equals(_returnJavaType.toString(), "void"))) {

			appendSingleLine(sb, _objectName, " ", "(", -1);
		}
		else {
			appendNewLine(
				sb, _objectName, indent + "\t", "", "(", maxLineLength);

			append(
				sb, _javaParameters, indent + "\t\t", "", ")", maxLineLength);

			appendNewLine(
				sb, _exceptionJavaExpressions, indent, "throws ", suffix,
				maxLineLength);
		}

		return sb.toString();
	}

	private List<JavaExpression> _exceptionJavaExpressions;
	private List<JavaType> _genericJavaTypes;
	private String _indent;
	private List<JavaParameter> _javaParameters;
	private List<JavaSimpleValue> _modifiers;
	private final JavaSimpleValue _objectName;
	private JavaType _returnJavaType;

}