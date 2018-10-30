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
public class JavaClassCall extends JavaExpression {

	public JavaClassCall(String className) {
		_className = new JavaSimpleValue(className);
	}

	public void setGenericTypes(List<JavaType> genericTypes) {
		_genericTypes = genericTypes;
	}

	public void setParameterValues(List<JavaExpression> parameterValues) {
		_parameterValues = parameterValues;
	}

	private final JavaSimpleValue _className;
	private List<JavaType> _genericTypes;
	private List<JavaExpression> _parameterValues;

}