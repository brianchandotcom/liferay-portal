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

package com.liferay.arquillian.extension.junit.bridge.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class TestEvent implements Event {

	public TestEvent(Object target, Method method) {
		_target = target;
		_method = method;
	}

	public Method getMethod() {
		return _method;
	}

	public Object getTarget() {
		return _target;
	}

	public void invoke() throws Throwable {
		try {
			_method.invoke(_target);
		}
		catch (InvocationTargetException ite) {
			throw ite.getCause();
		}
	}

	private final Method _method;
	private final Object _target;

}