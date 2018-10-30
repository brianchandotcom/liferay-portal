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
public class JavaType extends BaseJavaTerm {

	public JavaType(String name, int arrayDimension) {
		_name = new JavaSimpleValue(name);
		_arrayDimension = arrayDimension;
	}

	public void setGenericJavaTypes(List<JavaType> genericJavaTypes) {
		_genericJavaTypes = genericJavaTypes;
	}

	public void setLowerBoundJavaTypes(List<JavaType> lowerBoundJavaTypes) {
		_lowerBoundJavaTypes = lowerBoundJavaTypes;
	}

	public void setUpperBoundJavaTypes(List<JavaType> upperBoundJavaTypes) {
		_upperBoundJavaTypes = upperBoundJavaTypes;
	}

	public void setVarargs(boolean varargs) {
		_varargs = varargs;
	}

	private final int _arrayDimension;
	private List<JavaType> _genericJavaTypes;
	private List<JavaType> _lowerBoundJavaTypes;
	private final JavaSimpleValue _name;
	private List<JavaType> _upperBoundJavaTypes;
	private boolean _varargs;

}