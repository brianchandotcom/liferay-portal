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
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.arquillian.core.spi.ObserverMethod;

/**
 * @author Matthew Tambara
 */
public class ObserverImpl implements ObserverMethod {

	public static ObserverImpl of(Object extension, Method method) {
		return new ObserverImpl(extension, method);
	}

	@Override
	public int compareTo(ObserverMethod observerMethod) {
		if (observerMethod == null) {
			return 1;
		}

		Method method = getMethod();

		String methodName = method.getName();

		method = observerMethod.getMethod();

		return methodName.compareTo(method.getName());
	}

	@Override
	public Method getMethod() {
		return _method;
	}

	@Override
	public Type getType() {
		return _method.getGenericParameterTypes()[0];
	}

	@Override
	public boolean invoke(Manager manager, Object event) {
		try {
			Object[] arguments = _resolveArguments(manager, event);

			if (_containsNull(arguments)) {
				return false;
			}

			if (!_method.isAccessible()) {
				_method.setAccessible(true);
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

	private ObserverImpl(Object target, Method method) {
		_target = target;
		_method = method;
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
		Method method = getMethod();

		Class<?>[] argumentTypes = method.getParameterTypes();

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