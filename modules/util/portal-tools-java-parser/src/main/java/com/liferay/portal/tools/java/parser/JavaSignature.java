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

import java.util.List;

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

	public void setExceptions(List<JavaExpression> exceptions) {
		_exceptions = exceptions;
	}

	public void setGenericTypes(List<JavaType> genericTypes) {
		_genericTypes = genericTypes;
	}

	public void setIndent(String indent) {
		_indent = indent;
	}

	public void setModifiers(List<JavaSimpleValue> modifiers) {
		_modifiers = modifiers;
	}

	public void setParameters(List<JavaParameter> parameters) {
		_parameters = parameters;
	}

	public void setReturnType(JavaType returnType) {
		_returnType = returnType;
	}

	private List<JavaExpression> _exceptions;
	private List<JavaType> _genericTypes;
	private String _indent;
	private List<JavaSimpleValue> _modifiers;
	private final JavaSimpleValue _objectName;
	private List<JavaParameter> _parameters;
	private JavaType _returnType;

}