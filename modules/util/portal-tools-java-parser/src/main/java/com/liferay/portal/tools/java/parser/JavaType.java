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

	public void setGenericTypes(List<JavaType> genericTypes) {
		_genericTypes = genericTypes;
	}

	public void setLowerBounds(List<JavaType> lowerBounds) {
		_lowerBounds = lowerBounds;
	}

	public void setUpperBounds(List<JavaType> upperBounds) {
		_upperBounds = upperBounds;
	}

	public void setVarargs(boolean varargs) {
		_varargs = varargs;
	}

	private final int _arrayDimension;
	private List<JavaType> _genericTypes;
	private List<JavaType> _lowerBounds;
	private final JavaSimpleValue _name;
	private List<JavaType> _upperBounds;
	private boolean _varargs;

}