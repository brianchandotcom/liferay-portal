/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaClassDefinition extends BaseJavaTerm {

	public JavaClassDefinition(
		String type, List<JavaAnnotation> javaAnnotations,
		List<JavaSimpleValue> modifiers, JavaType classJavaType) {

		_type = type;
		_javaAnnotations = javaAnnotations;
		_modifiers = modifiers;
		_classJavaType = classJavaType;
	}

	public void setExtendedClassJavaTypes(
		List<JavaType> extendedClassJavaTypes) {

		_extendedClassJavaTypes = extendedClassJavaTypes;
	}

	public void setImplementedClassJavaTypes(
		List<JavaType> implementedClassJavaTypes) {

		_implementedClassJavaTypes = implementedClassJavaTypes;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		if (!_javaAnnotations.isEmpty()) {
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

			sb.append("\n");
		}

		sb.append(indent);

		indent = "\t" + indent;

		int index = sb.index();

		if (!_modifiers.isEmpty()) {
			append(
				sb, _modifiers, " ", indent, prefix, " ", NO_MAX_LINE_LENGTH);

			prefix = StringPool.BLANK;
		}

		appendSingleLine(
			sb, _classJavaType, StringBundler.concat(prefix, _type, " "), "",
			NO_MAX_LINE_LENGTH);

		if (_extendedClassJavaTypes != null) {
			appendSingleLine(
				sb, _extendedClassJavaTypes, " extends ", "",
				NO_MAX_LINE_LENGTH);
		}

		if (_implementedClassJavaTypes != null) {
			appendSingleLine(
				sb, _implementedClassJavaTypes, " implements ", "",
				NO_MAX_LINE_LENGTH);
		}

		sb.append(suffix);

		if ((maxLineLength == -1) ||
			(getLineLength(getLastLine(sb)) <= maxLineLength)) {

			return sb.toString();
		}

		sb.setIndex(index);

		if (!_modifiers.isEmpty()) {
			append(sb, _modifiers, " ", indent, prefix, " ", maxLineLength);

			prefix = StringPool.BLANK;
		}

		sb.append(prefix);
		sb.append(_type);
		sb.append(" ");

		if (_extendedClassJavaTypes != null) {
			indent = append(sb, _classJavaType, indent, maxLineLength, false);

			if (_implementedClassJavaTypes != null) {
				appendNewLine(
					sb, _extendedClassJavaTypes, indent, "extends ", " ",
					maxLineLength);
				append(
					sb, _implementedClassJavaTypes, indent, "implements ",
					suffix, maxLineLength);
			}
			else {
				appendNewLine(
					sb, _extendedClassJavaTypes, indent, "extends ", suffix,
					maxLineLength);
			}

			return sb.toString();
		}

		if (_implementedClassJavaTypes != null) {
			indent = append(sb, _classJavaType, indent, maxLineLength, false);

			appendNewLine(
				sb, _implementedClassJavaTypes, indent, "implements ", suffix,
				maxLineLength);
		}
		else {
			append(
				sb, _classJavaType, indent, "", suffix, maxLineLength, false);
		}

		return sb.toString();
	}

	private final JavaType _classJavaType;
	private List<JavaType> _extendedClassJavaTypes;
	private List<JavaType> _implementedClassJavaTypes;
	private final List<JavaAnnotation> _javaAnnotations;
	private final List<JavaSimpleValue> _modifiers;
	private final String _type;

}