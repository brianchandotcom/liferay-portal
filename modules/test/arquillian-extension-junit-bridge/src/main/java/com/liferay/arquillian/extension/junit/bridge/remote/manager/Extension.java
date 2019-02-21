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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventPoint;

/**
 * @author Matthew Tambara
 */
public class Extension {

	public Extension(Object target) {
		_injectionPoints = _injections(target);
		_observers = _observers(target);
	}

	public List<EventPoint> getEventPoints() {
		return Collections.<EventPoint>emptyList();
	}

	public List<InjectionPoint> getInjectionPoints() {
		return Collections.unmodifiableList(_injectionPoints);
	}

	public List<Observer> getObservers() {
		return Collections.unmodifiableList(_observers);
	}

	private static List<InjectionPoint> _injections(Object target) {
		List<InjectionPoint> injectionPoints = new ArrayList<>();

		Class<?> clazz = target.getClass();

		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (_isInjectionPoint(field)) {
					field.setAccessible(true);

					injectionPoints.add(new InjectionPoint(target, field));
				}
			}

			clazz = clazz.getSuperclass();
		}

		return injectionPoints;
	}

	private static boolean _isInjectionPoint(Field field) {
		if (field.isAnnotationPresent(Inject.class)) {
			Class<?> type = field.getType();

			if (type.equals(Instance.class)) {
				return true;
			}
		}

		return false;
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

	private static List<Observer> _observers(Object target) {
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

	private final List<InjectionPoint> _injectionPoints;
	private final List<Observer> _observers;

}