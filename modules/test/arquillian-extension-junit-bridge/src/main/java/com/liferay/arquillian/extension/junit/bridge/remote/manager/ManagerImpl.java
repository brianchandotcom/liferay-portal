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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.event.ManagerStarted;
import org.jboss.arquillian.core.api.event.ManagerStopping;
import org.jboss.arquillian.core.spi.Extension;
import org.jboss.arquillian.core.spi.HashObjectStore;
import org.jboss.arquillian.core.spi.InjectionPoint;
import org.jboss.arquillian.core.spi.InvocationException;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.arquillian.core.spi.NonManagedObserver;
import org.jboss.arquillian.core.spi.ObserverMethod;
import org.jboss.arquillian.core.spi.context.AbstractContext;
import org.jboss.arquillian.core.spi.context.ApplicationContext;
import org.jboss.arquillian.core.spi.context.ObjectStore;

/**
 * @author Matthew Tambara
 */
public class ManagerImpl implements Manager {

	public ManagerImpl() {
		_applicationContext = new ApplicationContextImpl();

		_extensions.addAll(
			_createExtensions(
				LiferayArquillianJUnitBridgeExtension.getObservers()));

		_applicationContext.activate();
	}

	@Override
	public <T> void bind(
		Class<? extends Annotation> scope, Class<T> type, T instance) {

		ObjectStore objectStore = _applicationContext.getObjectStore();

		objectStore.add(type, instance);
	}

	@Override
	public void fire(Object event) {
		fire(event, null);
	}

	@Override
	public <T> void fire(T event, NonManagedObserver<T> nonManagedObserver) {
		List<ObserverMethod> observers = new ArrayList<>();

		Class<?> eventClass = event.getClass();

		for (Extension extension : _extensions) {
			for (ObserverMethod observerMethod : extension.getObservers()) {
				Type type = observerMethod.getType();

				Class<?> clazz = (Class<?>)type;

				if (clazz.isAssignableFrom(eventClass)) {
					observers.add(observerMethod);
				}
			}
		}

		if ((nonManagedObserver == null) && observers.isEmpty()) {
			return;
		}

		boolean activatedApplicationContext = false;

		try {
			if (!_applicationContext.isActive()) {
				_applicationContext.activate();

				activatedApplicationContext = true;
			}

			_proceed(observers, nonManagedObserver, event);
		}
		catch (InvocationException ie) {
			_throwException(ie.getCause());
		}
		finally {
			if (activatedApplicationContext && _applicationContext.isActive()) {
				_applicationContext.deactivate();
			}
		}
	}

	@Override
	public <T> T getContext(Class<T> type) {
		if (type.equals(ApplicationContext.class)) {
			return type.cast(_applicationContext);
		}

		return null;
	}

	@Override
	public void inject(Object obj) {
		_inject(new ExtensionImpl(obj));
	}

	@Override
	public <T> T resolve(Class<T> type) {
		if (!_applicationContext.isActive()) {
			return null;
		}

		ObjectStore objectStore = _applicationContext.getObjectStore();

		T object = objectStore.get(type);

		if (object != null) {
			return object;
		}

		return null;
	}

	@Override
	public void shutdown() {
		fire(new ManagerStopping());

		synchronized (this) {
			_applicationContext.clearAll();

			_extensions.clear();

			if (_eventStack != null) {
				_eventStack.remove();
			}
		}
	}

	@Override
	public void start() {
		fire(new ManagerStarted());

		_applicationContext.activate();
	}

	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

	private List<Extension> _createExtensions(
		Collection<Class<?>> extensionClasses) {

		List<Extension> created = new ArrayList<>();

		for (Class<?> extensionClass : extensionClasses) {
			Extension extension = new ExtensionImpl(
				_createInstance(extensionClass));

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

	private Class<?> _getType(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>)type;
		}
		else if (type instanceof ParameterizedType) {
			return _getType(
				((ParameterizedType)type).getActualTypeArguments()[0]);
		}

		return null;
	}

	private void _inject(Extension extension) {
		for (InjectionPoint injectionPoint : extension.getInjectionPoints()) {
			injectionPoint.set(
				new InstanceImpl<>(
					_getType(injectionPoint.getType()),
					injectionPoint.getScope(), this));
		}
	}

	private <T> void _proceed(
		List<ObserverMethod> observers,
		NonManagedObserver<T> nonManagedObserver, T event) {

		for (ObserverMethod observer : observers) {
			observer.invoke(this, event);
		}

		if (nonManagedObserver != null) {
			inject(nonManagedObserver);

			nonManagedObserver.fired(event);
		}
	}

	private final ApplicationContext _applicationContext;
	private ThreadLocal<Stack<Object>> _eventStack;
	private final List<Extension> _extensions = new ArrayList<>();

	private class ApplicationContextImpl
		extends AbstractContext<String> implements ApplicationContext {

		@Override
		public void activate() {
			super.activate(_APP_CONTEXT_ID);
		}

		@Override
		public void destroy() {
			super.destroy(_APP_CONTEXT_ID);
		}

		@Override
		public Class<? extends Annotation> getScope() {
			return ApplicationScoped.class;
		}

		@Override
		protected ObjectStore createNewObjectStore() {
			return new HashObjectStore();
		}

		private static final String _APP_CONTEXT_ID = "app";

	}

}