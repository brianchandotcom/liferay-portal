/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JavaSignature extends BaseJavaTerm {

	public JavaSignature(
		String objectName, List<JavaSimpleValue> modifiers,
		JavaType returnJavaType, List<JavaType> genericJavaTypes,
		List<JavaParameter> javaParameters, List<JavaType> exceptionJavaTypes) {

		_objectName = new JavaSimpleValue(objectName);
		_modifiers = modifiers;
		_returnJavaType = returnJavaType;
		_genericJavaTypes = genericJavaTypes;
		_javaParameters = javaParameters;
		_exceptionJavaTypes = exceptionJavaTypes;
	}

	public String getIndent() {
		return _indent;
	}

	public void setIndent(String indent) {
		_indent = indent;
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
			if (_exceptionJavaTypes.isEmpty()) {
				if ((_genericJavaTypes == null) &&
					((_returnJavaType == null) ||
					 Objects.equals(_returnJavaType.toString(), "void"))) {

					appendSingleLine(
						sb, _objectName, "", "()" + suffix, NO_MAX_LINE_LENGTH);
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

					appendSingleLine(
						sb, _objectName, "", "() ", NO_MAX_LINE_LENGTH);
				}
				else {
					appendNewLine(
						sb, _objectName, indent + "\t", "", "() ",
						maxLineLength);
				}

				appendNewLine(
					sb, _exceptionJavaTypes, indent, "throws ", suffix,
					maxLineLength);

				return sb.toString();
			}

			append(
				sb, _exceptionJavaTypes, indent, "throws ", suffix,
				maxLineLength);

			return sb.toString();
		}

		if (_exceptionJavaTypes.isEmpty()) {
			if (appendSingleLine(sb, _objectName, "", "(", maxLineLength)) {
				append(
					sb, _javaParameters, indent, "", ")" + suffix,
					maxLineLength);

				return sb.toString();
			}

			if ((_genericJavaTypes == null) && (_returnJavaType == null)) {
				appendSingleLine(sb, _objectName, "", "(", NO_MAX_LINE_LENGTH);
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
						sb, _exceptionJavaTypes, " throws ", suffix,
						maxLineLength)) {

					return sb.toString();
				}

				appendNewLine(
					sb, _exceptionJavaTypes, indent, "throws ", suffix,
					maxLineLength);

				return sb.toString();
			}

			appendNewLine(
				sb, _javaParameters, indent + "\t", "", ")", maxLineLength);
			appendNewLine(
				sb, _exceptionJavaTypes, indent, "throws ", suffix,
				maxLineLength);

			return sb.toString();
		}

		if ((_genericJavaTypes == null) && (_returnJavaType == null)) {
			appendSingleLine(sb, _objectName, " ", "(", NO_MAX_LINE_LENGTH);
		}
		else {
			appendNewLine(
				sb, _objectName, indent + "\t", "", "(", maxLineLength);

			append(
				sb, _javaParameters, indent + "\t\t", "", ")", maxLineLength);

			appendNewLine(
				sb, _exceptionJavaTypes, indent, "throws ", suffix,
				maxLineLength);
		}

		return sb.toString();
	}

	private final List<JavaType> _exceptionJavaTypes;
	private final List<JavaType> _genericJavaTypes;
	private String _indent;
	private final List<JavaParameter> _javaParameters;
	private final List<JavaSimpleValue> _modifiers;
	private final JavaSimpleValue _objectName;
	private final JavaType _returnJavaType;

}