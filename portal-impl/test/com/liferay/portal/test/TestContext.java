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

package com.liferay.portal.test;

import java.lang.reflect.Method;

/**
 * @author Miguel Pastor
 */
public class TestContext {
	public TestContext(Class<?> testClass) {
		_testClass = testClass;
	}

	public TestContext(Object testInstance, Method  method) {
		_testInstance = testInstance;
		_method = method;
	}

	public Method getMethod() {
		return _method;
	}

	public Class<?> getTestClass() {
		return _testClass;
	}

	public Object getTestInstance() {
		return _testInstance;
	}

	public void updateState(Object testInstance, Method method) {
		_testInstance = testInstance;
		_method = method;
	}

	private Method _method;
	private Class<?> _testClass;
	private Object _testInstance;

}