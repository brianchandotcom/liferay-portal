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
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * @author Matthew Tambara
 */
public class Observer {

	public static List<Observer> getObservers(Object target) {
		List<Observer> observers = new ArrayList<>();

		Class<?> clazz = target.getClass();

		while (clazz != null) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (_isObserverMethod(method)) {
					method.setAccessible(true);

					observers.add(new Observer(target, method));
				}
			}

			clazz = clazz.getSuperclass();
		}

		return observers;
	}

	public Type getType() {
		return _method.getGenericParameterTypes()[0];
	}

	public void invoke(Manager manager, Object event)
		throws ReflectiveOperationException {

		Object[] arguments = _resolveArguments(manager, event);

		if (_containsNull(arguments)) {
			return;
		}

		_method.invoke(_target, arguments);
	}

	private static boolean _isObserverMethod(Method method) {
		Annotation[][] annotations = method.getParameterAnnotations();

		if ((method.getParameterTypes().length < 1) ||
			(annotations.length < 1)) {

			return false;
		}

		for (Annotation annotation : annotations[0]) {
			if (annotation.annotationType() == Observes.class) {
				return true;
			}
		}

		return false;
	}

	private Observer(Object target, Method method) {
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