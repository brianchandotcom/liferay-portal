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

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventPoint;
import org.jboss.arquillian.core.spi.Extension;
import org.jboss.arquillian.core.spi.InjectionPoint;
import org.jboss.arquillian.core.spi.ObserverMethod;

/**
 * @author Matthew Tambara
 */
public class ExtensionImpl implements Extension {

	public static ExtensionImpl of(Object target) {
		return new ExtensionImpl(
			_injections(target, _getFieldInjectionPoints(target.getClass())),
			_observers(target, _getObserverMethods(target.getClass())));
	}

	@Override
	public List<EventPoint> getEventPoints() {
		return Collections.<EventPoint>emptyList();
	}

	@Override
	public List<InjectionPoint> getInjectionPoints() {
		return Collections.unmodifiableList(_injectionPoints);
	}

	@Override
	public List<ObserverMethod> getObservers() {
		return Collections.unmodifiableList(_observers);
	}

	private static List<Field> _getFieldInjectionPoints(Class<?> clazz) {
		List<Field> injectionPoints = new ArrayList<>();

		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (_isInjectionPoint(field)) {
					field.setAccessible(true);

					injectionPoints.add(field);
				}
			}

			clazz = clazz.getSuperclass();
		}

		return injectionPoints;
	}

	private static List<Method> _getObserverMethods(Class<?> clazz) {
		List<Method> observerMethods = new ArrayList<>();

		while (clazz != null) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (_isObserverMethod(method)) {
					method.setAccessible(true);

					observerMethods.add(method);
				}
			}

			clazz = clazz.getSuperclass();
		}

		return observerMethods;
	}

	private static List<InjectionPoint> _injections(
		Object extension, List<Field> injectionPoints) {

		List<InjectionPoint> result = new ArrayList<>();

		for (Field field : injectionPoints) {
			result.add(InjectionPointImpl.of(extension, field));
		}

		return result;
	}

	private static boolean _isInjectionPoint(Field field) {
		if (field.isAnnotationPresent(Inject.class)) {
			Class<?> type = field.getType();

			if (type.equals(Instance.class) ||
				type.equals(InstanceProducer.class)) {

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

	private static List<ObserverMethod> _observers(
		Object extension, List<Method> observerMethods) {

		List<ObserverMethod> result = new ArrayList<>();

		for (Method method : observerMethods) {
			result.add(ObserverImpl.of(extension, method));
		}

		return result;
	}

	private ExtensionImpl(
		List<InjectionPoint> injectionPoints, List<ObserverMethod> observers) {

		_injectionPoints = injectionPoints;
		_observers = observers;
	}

	private final List<InjectionPoint> _injectionPoints;
	private final List<ObserverMethod> _observers;

}