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

import com.liferay.arquillian.extension.junit.bridge.event.controller.ContainerEventController;
import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXMethodExecutor;
import com.liferay.arquillian.extension.junit.bridge.remote.observer.JUnitBridgeObserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.arquillian.core.api.event.ManagerStarted;

/**
 * @author Matthew Tambara
 */
public class Manager {

	public Manager() throws ReflectiveOperationException {
		for (Class<?> observerClass : _getObservers()) {
			Constructor<?> constructor = observerClass.getDeclaredConstructor();

			Object extension = constructor.newInstance();

			_inject(extension);

			_extensions.add(extension);
		}
	}

	public <T> void bind(Class<T> type, T instance) {
		_context.put(type, instance);
	}

	public <T> void fire(T event) {
		List<Observer> observers = new ArrayList<>();

		Class<?> eventClass = event.getClass();

		for (Object extension : _extensions) {
			for (Observer observer : Observer.getObservers(extension)) {
				Type type = observer.getType();

				Class<?> clazz = (Class<?>)type;

				if (clazz.isAssignableFrom(eventClass)) {
					observers.add(observer);
				}
			}
		}

		if (observers.isEmpty()) {
			return;
		}

		try {
			_proceed(observers, event);
		}
		catch (ReflectiveOperationException roe) {
			if (roe instanceof InvocationTargetException) {
				_throwException(roe.getCause());
			}
			else {
				_throwException(roe);
			}
		}
	}

	public <T> T resolve(Class<T> type) {
		Object object = _context.get(type);

		if (object != null) {
			return type.cast(object);
		}

		return null;
	}

	public void start() {
		fire(new ManagerStarted());
	}

	private static List<Class<?>> _getObservers() {
		URL url = Manager.class.getResource("/arquillian.remote.marker");

		if (url == null) {
			return Arrays.asList(
				ContainerEventController.class, JMXMethodExecutor.class
			);
		}

		return Collections.singletonList(JUnitBridgeObserver.class);
	}

	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

	private void _inject(Object extension) throws ReflectiveOperationException {
		for (InjectionPoint injectionPoint :
				InjectionPoint.getInjections(extension)) {

			injectionPoint.set(this);
		}
	}

	private <T> void _proceed(List<Observer> observers, T event)
		throws ReflectiveOperationException {

		for (Observer observer : observers) {
			observer.invoke(this, event);
		}
	}

	private final Map<Class<?>, Object> _context = new ConcurrentHashMap<>();
	private final List<Object> _extensions = new ArrayList<>();

}