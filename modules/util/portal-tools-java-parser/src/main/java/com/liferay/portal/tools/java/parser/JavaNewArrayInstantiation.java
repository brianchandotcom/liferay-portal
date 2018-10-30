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

/**
 * @author Hugo Huijser
 */
public class JavaNewArrayInstantiation extends JavaExpression {

	public void setArrayDeclarator(JavaArrayDeclarator arrayDeclarator) {
		_arrayDeclarator = arrayDeclarator;
	}

	public void setInitialArray(JavaArray initialArray) {
		_initialArray = initialArray;
	}

	private JavaArrayDeclarator _arrayDeclarator;
	private JavaArray _initialArray;

}