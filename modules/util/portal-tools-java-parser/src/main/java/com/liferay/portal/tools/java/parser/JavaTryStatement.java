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
public class JavaTryStatement extends BaseJavaTerm {

	public void setResourceJavaVariableDefinitions(
		List<JavaVariableDefinition> resourceJavaVariableDefinitions) {

		_resourceJavaVariableDefinitions = resourceJavaVariableDefinitions;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		if (_resourceJavaVariableDefinitions == null) {
			return StringBundler.concat(indent, prefix, "try", suffix);
		}

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		append(
			sb, _resourceJavaVariableDefinitions, "; ", indent,
			prefix + "try (", ")" + suffix, maxLineLength);

		return sb.toString();
	}

	private List<JavaVariableDefinition> _resourceJavaVariableDefinitions;

}