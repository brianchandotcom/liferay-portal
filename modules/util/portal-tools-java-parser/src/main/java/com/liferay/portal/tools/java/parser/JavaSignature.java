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

	private List<JavaExpression> _exceptionJavaExpressions;
	private List<JavaType> _genericJavaTypes;
	private String _indent;
	private List<JavaParameter> _javaParameters;
	private List<JavaSimpleValue> _modifiers;
	private final JavaSimpleValue _objectName;
	private JavaType _returnJavaType;

}