/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.test;

import java.lang.reflect.Method;

/**
 * @author Miguel Pastor
 */
public class TestContext {

	public TestContext(Class<?> clazz) {
		_clazz = clazz;
	}

	public TestContext(Object instance, Method method) {
		_instance = instance;
		_method = method;
	}

	public Class<?> getClazz() {
		return _clazz;
	}

	public Object getInstance() {
		return _instance;
	}

	public Method getMethod() {
		return _method;
	}

	public void setInstance(Object instance) {
		_instance = instance;
	}

	public void setMethod(Method method) {
		_method = method;
	}

	private Class<?> _clazz;
	private Object _instance;
	private Method _method;

}