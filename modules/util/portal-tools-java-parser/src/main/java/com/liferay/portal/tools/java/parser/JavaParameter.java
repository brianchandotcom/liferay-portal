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
public class JavaParameter extends BaseJavaTerm {

	public JavaParameter(
		String name, List<JavaAnnotation> javaAnnotations,
		List<JavaSimpleValue> modifiers, JavaType javaType) {

		_name = new JavaSimpleValue(name);
		_javaAnnotations = javaAnnotations;
		_modifiers = modifiers;
		_javaType = javaType;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		for (JavaAnnotation javaAnnotation : _javaAnnotations) {
			indent = append(sb, javaAnnotation, indent, "", " ", maxLineLength);
		}

		for (JavaSimpleValue modifier : _modifiers) {
			indent = append(sb, modifier, indent, "", " ", maxLineLength);
		}

		indent = append(sb, _javaType, indent, "", " ", maxLineLength, false);

		append(sb, _name, indent, "", suffix, maxLineLength);

		return sb.toString();
	}

	private final List<JavaAnnotation> _javaAnnotations;
	private final JavaType _javaType;
	private final List<JavaSimpleValue> _modifiers;
	private final JavaSimpleValue _name;

}