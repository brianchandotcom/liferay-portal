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

import com.liferay.arquillian.extension.junit.bridge.LiferayArquillianJUnitBridgeExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.arquillian.core.api.event.ManagerStarted;
import org.jboss.arquillian.core.spi.NonManagedObserver;

/**
 * @author Matthew Tambara
 */
public class Manager {

	public Manager() throws ReflectiveOperationException {
		_extensions.addAll(
			_createExtensions(
				LiferayArquillianJUnitBridgeExtension.getObservers()));
	}

	public <T> void bind(Class<T> type, T instance) {
		_context.put(type, instance);
	}

	public void fire(Object event) {
		fire(event, null);
	}

	public <T> void fire(T event, NonManagedObserver<T> nonManagedObserver) {
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

		if ((nonManagedObserver == null) && observers.isEmpty()) {
			return;
		}

		try {
			_proceed(observers, nonManagedObserver, event);
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

	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

	private List<Object> _createExtensions(
			Collection<Class<?>> extensionClasses)
		throws ReflectiveOperationException {

		List<Object> created = new ArrayList<>();

		for (Class<?> extensionClass : extensionClasses) {
			Object extension = _createInstance(extensionClass);

			_inject(extension);

			created.add(extension);
		}

		return created;
	}

	private <T>T _createInstance(Class<T> clazz) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();

			constructor.setAccessible(true);

			return constructor.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void _inject(Object extension) throws ReflectiveOperationException {
		for (InjectionPoint injectionPoint :
				InjectionPoint.getInjections(extension)) {

			injectionPoint.set(this);
		}
	}

	private <T> void _proceed(
			List<Observer> observers, NonManagedObserver<T> nonManagedObserver,
			T event)
		throws ReflectiveOperationException {

		for (Observer observer : observers) {
			observer.invoke(this, event);
		}

		if (nonManagedObserver != null) {
			_inject(nonManagedObserver);

			nonManagedObserver.fired(event);
		}
	}

	private final Map<Class<?>, Object> _context = new ConcurrentHashMap<>();
	private final List<Object> _extensions = new ArrayList<>();

}