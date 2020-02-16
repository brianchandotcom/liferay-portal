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

/**
 * @author Hugo Huijser
 */
public class JavaEnhancedForStatement extends BaseJavaLoopStatement {

	public JavaEnhancedForStatement(
		JavaExpression collectionJavaExpression,
		JavaVariableDefinition javaVariableDefinition) {

		_collectionJavaExpression = collectionJavaExpression;
		_javaVariableDefinition = javaVariableDefinition;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = appendLabelName(indent);

		if (sb.index() > 0) {
			sb.append("\n");
		}

		sb.append(indent);

		indent = "\t" + indent;

		indent = append(
			sb, _javaVariableDefinition, indent, prefix + "for (", " : ",
			maxLineLength);

		append(
			sb, _collectionJavaExpression, indent, "", ")" + suffix,
			maxLineLength);

		return sb.toString();
	}

	private final JavaExpression _collectionJavaExpression;
	private final JavaVariableDefinition _javaVariableDefinition;

}