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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.InvocationException;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.arquillian.core.spi.ObserverMethod;

/**
 * @author Matthew Tambara
 */
public class ObserverImpl
	implements ObserverMethod, Comparable<ObserverMethod> {

	public static ObserverImpl of(Object extension, Method method) {
		return new ObserverImpl(extension, method);
	}

	@Override
	public int compareTo(ObserverMethod observerMethod) {
		if (observerMethod == null) {
			return 1;
		}

		Integer integer1 = _getPrecedence(getMethod());
		Integer integer2 = _getPrecedence(observerMethod.getMethod());

		return integer2.compareTo(integer1);
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

	private Integer _getPrecedence(Method method) {
		for (Annotation[] annotations : method.getParameterAnnotations()) {
			for (Annotation annotation : annotations) {
				if (annotation.annotationType() == Observes.class) {
					return ((Observes)annotation).precedence();
				}
			}
		}

		return 0;
	}

	private Object[] _resolveArguments(Manager manager, Object event) {
		Class<?>[] argumentTypes = getMethod().getParameterTypes();

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