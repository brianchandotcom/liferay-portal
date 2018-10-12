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

package com.liferay.bean.portlet.cdi.extension.internal;

import java.lang.reflect.Method;

/**
 * @author Neil Griffin
 */
public class ScannedMethod {

	public ScannedMethod(Class<?> clazz, MethodType methodType, Method method) {
		_clazz = clazz;
		_methodType = methodType;
		_method = method;
	}

	public Class<?> getClazz() {
		return _clazz;
	}

	public Method getMethod() {
		return _method;
	}

	public MethodType getMethodType() {
		return _methodType;
	}

	public int getOrdinal() {
		return _methodType.getOrdinal(_method);
	}

	public String[] getPortletNames() {
		return _methodType.getPortletNames(_method);
	}

	private final Class<?> _clazz;
	private final Method _method;
	private final MethodType _methodType;

}