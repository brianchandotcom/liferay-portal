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

package com.liferay.arquillian.extension.junit.bridge.remote.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jboss.arquillian.core.spi.InvocationException;

/**
 * @author Matthew Tambara
 */
public class Observer implements Comparable<Observer> {

	public Observer(Object target, Method method) {
		_target = target;
		_method = method;
	}

	@Override
	public int compareTo(Observer observer) {
		if (observer == null) {
			return 1;
		}

		String methodName = _method.getName();

		Method method = observer._method;

		return methodName.compareTo(method.getName());
	}

	public Type getType() {
		return _method.getGenericParameterTypes()[0];
	}

	public boolean invoke(Manager manager, Object event) {
		try {
			Object[] arguments = _resolveArguments(manager, event);

			if (_containsNull(arguments)) {
				return false;
			}

			_method.invoke(_target, arguments);

			return true;
		}
		catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				Throwable throwable =
					((InvocationTargetException)e).getTargetException();

				if (throwable instanceof InvocationException) {
					throw (InvocationException)throwable;
				}

				throw new InvocationException(throwable);
			}

			throw new InvocationException(e);
		}
	}

	private boolean _containsNull(Object[] arguments) {
		for (Object argument : arguments) {
			if (argument == null) {
				return true;
			}
		}

		return false;
	}

	private Object[] _resolveArguments(Manager manager, Object event) {
		Class<?>[] argumentTypes = _method.getParameterTypes();

		int numberOfArguments = argumentTypes.length;

		Object[] arguments = new Object[numberOfArguments];

		arguments[0] = event;

		for (int i = 1; i < numberOfArguments; i++) {
			Class<?> argumentType = argumentTypes[i];

			arguments[i] = manager.resolve(argumentType);
		}

		return arguments;
	}

	private final Method _method;
	private final Object _target;

}