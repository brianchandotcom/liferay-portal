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
import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaClassDefinition extends BaseJavaTerm {

	public void setClassJavaType(JavaType classJavaType) {
		_classJavaType = classJavaType;
	}

	public void setExtendedClassJavaType(JavaType extendedClassJavaType) {
		_extendedClassJavaType = extendedClassJavaType;
	}

	public void setImplementedClassJavaTypes(
		List<JavaType> implementedClassJavaTypes) {

		_implementedClassJavaTypes = implementedClassJavaTypes;
	}

	public void setJavaAnnotations(List<JavaAnnotation> javaAnnotations) {
		_javaAnnotations = javaAnnotations;
	}

	public void setModifiers(List<JavaSimpleValue> modifiers) {
		_modifiers = modifiers;
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
			append(sb, _modifiers, " ", indent, prefix, " ", -1);

			prefix = StringPool.BLANK;
		}

		appendSingleLine(sb, _classJavaType, prefix + "class ", "", -1);

		if (_extendedClassJavaType != null) {
			appendSingleLine(sb, _extendedClassJavaType, " extends ", "", -1);
		}

		if (_implementedClassJavaTypes != null) {
			appendSingleLine(
				sb, _implementedClassJavaTypes, " implements ", "", -1);
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
		sb.append("class ");

		if (_extendedClassJavaType != null) {
			indent = append(sb, _classJavaType, indent, maxLineLength, false);

			if (_implementedClassJavaTypes != null) {
				appendNewLine(
					sb, _extendedClassJavaType, indent, "extends ", " ",
					maxLineLength);
				append(
					sb, _implementedClassJavaTypes, indent, "implements ",
					suffix, maxLineLength);
			}
			else {
				appendNewLine(
					sb, _extendedClassJavaType, indent, "extends ", suffix,
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

	private JavaType _classJavaType;
	private JavaType _extendedClassJavaType;
	private List<JavaType> _implementedClassJavaTypes;
	private List<JavaAnnotation> _javaAnnotations;
	private List<JavaSimpleValue> _modifiers;

}