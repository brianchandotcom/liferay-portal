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

package com.liferay.portal.kernel.javadoc;

import java.lang.reflect.Method;

/**
 * @author Preston Crary
 */
public class EmptyJavadocMethod extends JavadocMethod {

	public EmptyJavadocMethod(String servletContextName, Method method) {
		setServletContextName(servletContextName);

		_method = method;
	}

	@Override
	public Method getMethod() {
		return _method;
	}

	@Override
	public String getParameterComment(int index) {
		return null;
	}

	@Override
	public String[] getParameterComments() {
		return new String[0];
	}

	@Override
	public String getReturnComment() {
		return null;
	}

	@Override
	public String getThrowsComment(int index) {
		return null;
	}

	@Override
	public String[] getThrowsComments() {
		return new String[0];
	}

	private final Method _method;

}